package com.censo.controlador.vehiculo;

import com.censo.modelo.dao.ClaseVehiculoDao;
import com.censo.modelo.dao.TipoServicioDao;
import com.censo.modelo.dao.VehiculoDao;
import com.censo.modelo.dao.VehiculoRuntDao;
import com.censo.modelo.persistencia.CenClaseVehiculo;
import com.censo.modelo.persistencia.CenTipoServicio;
import com.censo.modelo.persistencia.CenVehiculo;
import com.censo.modelo.persistencia.ResponseVehiculoRunt;
import com.censo.modelo.persistencia.VehiculoRunt;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "VerificarVehiculoRunt", urlPatterns = {"/verificarVehiculoRunt"})
public class VerificarVehiculoRunt extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, Object> respuesta = new HashMap<>();

        try {

            conex = dataSource.getConnection();

            if (request.getParameter("placa") == null || request.getParameter("placa").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'placa' no encontrado para consultar vehiculo en runt");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            if (request.getParameter("numerodocumento") == null || request.getParameter("numerodocumento").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'numero de documento' no encontrado para consultar vehiculo en runt");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String placa = request.getParameter("placa").toUpperCase().trim();
            String numerodocumento = request.getParameter("numerodocumento").toUpperCase().trim();

            VehiculoRuntDao vehiculoRuntDao = new VehiculoRuntDao();

            //Consultar si la placa esta o no en la tabla vehiculo_runt
            VehiculoRunt vehiculoRunt = vehiculoRuntDao.ConsultarVehiculoRuntByPlacaDocumento(conex, placa, numerodocumento);

            if (vehiculoRunt != null) {

                VehiculoDao vehiculoDao = new VehiculoDao();
                CenVehiculo cenvehiculo = vehiculoDao.ConsultarVehiculoByReferencia(conex, 1, placa);

                if (cenvehiculo != null) {
                    respuesta.put("status", "fail");
                    respuesta.put("message", "Placa no valida, ya se encuentra registrada");
                    respuesta.put("input", "#txtplacarunt");
                    respuesta.put("input", "#txtdocumentorunt");
                } else {
                    respuesta.put("status", "success");
                    respuesta.put("vehiculorunt", vehiculoRunt);
                }

            } else {

                VehiculoDao vehiculoDao = new VehiculoDao();
                CenVehiculo cenvehiculo = vehiculoDao.ConsultarVehiculoByReferencia(conex, 1, placa);

                if (cenvehiculo == null) {
                    String urlString = System.getenv("URL_RUNT_PLACA");
                    urlString = urlString.concat(numerodocumento).concat("&hho=").concat(placa);
                    URL url = new URL(urlString);

                    // Abrir conexión
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");

                    StringBuilder content;
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                        String inputLine;
                        content = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }
                    }
                    con.disconnect();

                    Gson gson = new Gson();
                    ResponseVehiculoRunt responseVR = gson.fromJson(content.toString(), ResponseVehiculoRunt.class);

                    // Validar si el dato existe
                    if (responseVR != null && !responseVR.getPlacaVehiculo().isEmpty()) {

                        VehiculoRunt.PolizaSoat vrpoliza = new VehiculoRunt.PolizaSoat();
                        if (!responseVR.getListPolizaSoat().isEmpty()) {
                            for (ResponseVehiculoRunt.PolizaSoat vr : responseVR.getListPolizaSoat()) {
                                if (vr.getEstado().equals("VIGENTE")) {
                                    vrpoliza = VehiculoRunt.PolizaSoat.builder()
                                            .numeroPoliza(responseVR.getListPolizaSoat().get(0).getNumeroPoliza())
                                            .fechaExpedicion(responseVR.getListPolizaSoat().get(0).getFechaExpedicion())
                                            .fechaInicioVigencia(responseVR.getListPolizaSoat().get(0).getFechaInicioVigencia())
                                            .fechaFinVigencia(responseVR.getListPolizaSoat().get(0).getFechaFinVigencia())
                                            .entidadSoat(responseVR.getListPolizaSoat().get(0).getEntidadSoat())
                                            .estado(responseVR.getListPolizaSoat().get(0).getEstado())
                                            .build();
                                    break;
                                }
                            }
                        } else {
                            vrpoliza = VehiculoRunt.PolizaSoat.builder().build();
                        }

                        VehiculoRunt.TecnicoMecanico vrcertificado = new VehiculoRunt.TecnicoMecanico();
                        if (!responseVR.getListCertificadoTecnicoMecanicoGases().isEmpty()) {
                            for (ResponseVehiculoRunt.CertificadoTecnicoMecanicoGases vr : responseVR.getListCertificadoTecnicoMecanicoGases()) {
                                if (vr.getVigente().equals("SI")) {
                                    vrcertificado = VehiculoRunt.TecnicoMecanico.builder()
                                            .tipoRevision(responseVR.getListCertificadoTecnicoMecanicoGases().get(0).getTipoRevision())
                                            .fechaExpedicion(responseVR.getListCertificadoTecnicoMecanicoGases().get(0).getFechaExpedicion())
                                            .fechaVigencia(responseVR.getListCertificadoTecnicoMecanicoGases().get(0).getFechaVigencia())
                                            .cdaExpide(responseVR.getListCertificadoTecnicoMecanicoGases().get(0).getCdaExpide())
                                            .vigente(responseVR.getListCertificadoTecnicoMecanicoGases().get(0).getVigente())
                                            .build();
                                    break;
                                }
                            }
                        } else {
                            vrcertificado = VehiculoRunt.TecnicoMecanico.builder().build();
                        }

                        ClaseVehiculoDao claseVehiculoDao = new ClaseVehiculoDao();
                        CenClaseVehiculo cenClaseVehiculo = claseVehiculoDao.ConsultarClaseVehiculoByDescripcion(conex, responseVR.getClaseVehiculo());

                        TipoServicioDao tipoServicioDao = new TipoServicioDao();
                        CenTipoServicio cenTipoServicio = tipoServicioDao.ConsultarTipoServicioByDescripcion(conex, responseVR.getTipoServicio());

                        DateTimeFormatter formatterEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                        vehiculoRunt = VehiculoRunt.builder()
                                .placa(responseVR.getPlacaVehiculo())
                                .licenciaTransito(responseVR.getNroLicenciaTransito())
                                .estado(responseVR.getEstadoVehiculo())
                                .tipoServicio(responseVR.getTipoServicio())
                                .claseVehiculo(responseVR.getClaseVehiculo())
                                .marca(responseVR.getInformacionGeneralVehiculo().getMarca())
                                .linea(responseVR.getInformacionGeneralVehiculo().getLinea())
                                .modelo(responseVR.getInformacionGeneralVehiculo().getModelo())
                                .color(responseVR.getInformacionGeneralVehiculo().getColor())
                                .serie(responseVR.getInformacionGeneralVehiculo().getNroSerie())
                                .motor(responseVR.getInformacionGeneralVehiculo().getNroMotor())
                                .chasis(responseVR.getInformacionGeneralVehiculo().getNroChasis())
                                .vin(responseVR.getInformacionGeneralVehiculo().getNroVin())
                                .cilindraje(responseVR.getInformacionGeneralVehiculo().getCilindraje())
                                .tipoCarroceria(responseVR.getInformacionGeneralVehiculo().getTipoCarroceria())
                                .tipoCombustible(responseVR.getInformacionGeneralVehiculo().getTipoCombustible())
                                .fechaMatriculaInicial(responseVR.getInformacionGeneralVehiculo().getFechaMatriculaInicial() == null ? ""
                                        : formatter.format(formatterEntrada.parse(responseVR.getInformacionGeneralVehiculo().getFechaMatriculaInicial())))
                                .autoridadTransito(responseVR.getInformacionGeneralVehiculo().getAutoridadTransito())
                                .gravamenesPropiedad(responseVR.getInformacionGeneralVehiculo().getGravamenesPropiedad())
                                .clasicoAntiguo(responseVR.getInformacionGeneralVehiculo().getClasicoAntiguo())
                                .repotenciado(responseVR.getInformacionGeneralVehiculo().getRepotenciado())
                                .regrabacionMotor(responseVR.getInformacionGeneralVehiculo().getRegrabacionMotor())
                                .regrabacionChasis(responseVR.getInformacionGeneralVehiculo().getRegrabacionChasis())
                                .regrabacionSerie(responseVR.getInformacionGeneralVehiculo().getRegrabacionSerie())
                                .regrabacionVin(responseVR.getInformacionGeneralVehiculo().getRegrabacionVin())
                                .capacidadCarga(responseVR.getDatosTecnicosVehiculo().getCapacidadCarga())
                                .pesoBrutoVehicular(responseVR.getDatosTecnicosVehiculo().getPesoBrutoVehicular())
                                .capacidadPasajeros(responseVR.getDatosTecnicosVehiculo().getCapacidadPasajeros())
                                .capacidadPasajerosSentados(responseVR.getDatosTecnicosVehiculo().getCapacidadPasajerosSentados())
                                .nroEjes(responseVR.getDatosTecnicosVehiculo().getNroEjes())
                                .polizaSoat(
                                        VehiculoRunt.PolizaSoat.builder()
                                                .numeroPoliza(vrpoliza.getNumeroPoliza())
                                                .fechaExpedicion(vrpoliza.getFechaExpedicion() == null ? "" : formatter.format(formatterEntrada.parse(vrpoliza.getFechaExpedicion())))
                                                .fechaInicioVigencia(vrpoliza.getFechaInicioVigencia() == null ? "" : formatter.format(formatterEntrada.parse(vrpoliza.getFechaInicioVigencia())))
                                                .fechaFinVigencia(vrpoliza.getFechaFinVigencia() == null ? "" : formatter.format(formatterEntrada.parse(vrpoliza.getFechaFinVigencia())))
                                                .entidadSoat(vrpoliza.getEntidadSoat())
                                                .estado(vrpoliza.getEstado())
                                                .build()
                                )
                                .tecnicoMecanico(
                                        VehiculoRunt.TecnicoMecanico.builder()
                                                .tipoRevision(vrcertificado.getTipoRevision())
                                                .fechaExpedicion(vrcertificado.getFechaExpedicion() == null ? "" : formatter.format(formatterEntrada.parse(vrcertificado.getFechaExpedicion())))
                                                .fechaVigencia(vrcertificado.getFechaVigencia() == null ? "" : formatter.format(formatterEntrada.parse(vrcertificado.getFechaVigencia())))
                                                .cdaExpide(vrcertificado.getCdaExpide())
                                                .vigente(vrcertificado.getVigente())
                                                .build())
                                .fechaConsulta(responseVR.getFechaConsulta())
                                .numeroDocumento(numerodocumento)
                                .claseVehiculoId(cenClaseVehiculo.getId())
                                .tipoServicioId(cenTipoServicio.getId())
                                .build();

                        conex.setAutoCommit(false);

                        boolean runt_registrado = vehiculoRuntDao.adicionarVehiculoRunt(conex, vehiculoRunt);

                        if (runt_registrado) {
                            conex.commit();
                            respuesta.put("status", "success");
                            respuesta.put("vehiculorunt", vehiculoRunt);
                        } else {
                            conex.rollback();
                        }

                    } else {
                        respuesta.put("status", "fail");
                        respuesta.put("message", "Vehiculo no encontrado en runt");
                        vehiculoRuntDao.adicionarVehiculoRunt(conex, VehiculoRunt.builder()
                                .placa(placa)
                                .numeroDocumento(numerodocumento)
                                .polizaSoat(VehiculoRunt.PolizaSoat.builder().build())
                                .tecnicoMecanico(VehiculoRunt.TecnicoMecanico.builder().build())
                                .build());
                    }
                } else {
                    respuesta.put("status", "fail");
                    respuesta.put("message", "Placa no valida, ya se encuentra registrada");
                    respuesta.put("input", "#txtplacarunt");
                    respuesta.put("input", "#txtdocumentorunt");
                }

            }

        } catch (SQLException e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            respuesta.put("status", "error");
            respuesta.put("message", "Error al verificar el vehiculo");
            e.printStackTrace();
        } finally {
            if (conex != null) {
                try {
                    conex.setAutoCommit(true);
                    conex.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }

        String json = new Gson().toJson(respuesta);

        response.getWriter()
                .write(json);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
