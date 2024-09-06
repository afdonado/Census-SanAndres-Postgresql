package com.censo.controlador.vehiculo;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.dao.ClaseVehiculoDao;
import com.censo.modelo.dao.TipoServicioDao;
import com.censo.modelo.dao.VehiculoDao;
import com.censo.modelo.dao.VehiculoRuntDao;
import com.censo.modelo.persistencia.CenCenso;
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
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "VerificarVehiculoRunt", urlPatterns = {"/verificarVehiculoRunt"})
public class VerificarVehiculoRunt extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, Object> respuesta = new HashMap<>();

        try {

            if (request.getParameter("tiporeferencia") == null || request.getParameter("tiporeferencia").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'tipo de referencia' no encontrado para verificar vehiculo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            if (request.getParameter("valorreferencia") == null || request.getParameter("valorreferencia").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'numero de referencia' no encontrado para verificar vehiculo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            int tipoReferencia = Integer.parseInt(request.getParameter("tiporeferencia"));
            String valorReferencia = request.getParameter("valorreferencia");

            VehiculoDao vehiculoDao = new VehiculoDao();
            conex = vehiculoDao.conectar();

            CenVehiculo cenvehiculo = vehiculoDao.ConsultarVehiculoByReferencia(conex, tipoReferencia, valorReferencia);
            
            VehiculoRuntDao vehiculoRuntDao = new VehiculoRuntDao();

            if (cenvehiculo != null) {

                VehiculoRunt vehiculoRunt = vehiculoRuntDao.ConsultarVehiculoByPlaca(conex, valorReferencia);
                //Consultar si esta o no en vehiculo_runt
                //si existe mostrar los datos de esa tabla y no consultar en el link del runt
                
                //Respuesta para la validacion si el vehiculo está o no registrado
                respuesta.put("status", "fail");
                if (tipoReferencia == 1) {
                    respuesta.put("message", "Placa no valida, ya se encuentra registrada");
                    respuesta.put("input", "#txtplaca");
                }

            } else {

                String urlString = "http://localhost:3001/consulta/runt/placa";
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
                if (responseVR != null && !responseVR.getRuntPlacaVO().getPlacaVehiculo().isEmpty()) {

                    VehiculoRunt.PolizaSoat vrpoliza = new VehiculoRunt.PolizaSoat();
                    for (ResponseVehiculoRunt.RuntPlacaVO.PolizaSoat vr : responseVR.getRuntPlacaVO().getListPolizaSoat()) {
                        if (vr.getEstado().equals("VIGENTE")) {
                            vrpoliza = VehiculoRunt.PolizaSoat.builder()
                                    .numeroPoliza(responseVR.getRuntPlacaVO().getListPolizaSoat().get(0).getNumeroPoliza())
                                    .fechaExpedicion(responseVR.getRuntPlacaVO().getListPolizaSoat().get(0).getFechaExpedicion())
                                    .fechaInicioVigencia(responseVR.getRuntPlacaVO().getListPolizaSoat().get(0).getFechaInicioVigencia())
                                    .fechaFinVigencia(responseVR.getRuntPlacaVO().getListPolizaSoat().get(0).getFechaFinVigencia())
                                    .entidadSoat(responseVR.getRuntPlacaVO().getListPolizaSoat().get(0).getEntidadSoat())
                                    .estado(responseVR.getRuntPlacaVO().getListPolizaSoat().get(0).getEstado())
                                    .build();
                        } else {
                            VehiculoRunt.PolizaSoat.builder().build();
                        }
                    }

                    VehiculoRunt.TecnicoMecanico vrcertificado = new VehiculoRunt.TecnicoMecanico();
                    for (ResponseVehiculoRunt.RuntPlacaVO.CertificadoTecnicoMecanicoGases vr : responseVR.getRuntPlacaVO().getListCertificadoTecnicoMecanicoGases()) {
                        if (vr.getVigente().equals("SI")) {
                            vrcertificado = VehiculoRunt.TecnicoMecanico.builder()
                                    .tipoRevision(responseVR.getRuntPlacaVO().getListCertificadoTecnicoMecanicoGases().get(0).getTipoRevision())
                                    .fechaExpedicion(responseVR.getRuntPlacaVO().getListCertificadoTecnicoMecanicoGases().get(0).getFechaExpedicion())
                                    .fechaVigencia(responseVR.getRuntPlacaVO().getListCertificadoTecnicoMecanicoGases().get(0).getFechaVigencia())
                                    .cdaExpide(responseVR.getRuntPlacaVO().getListCertificadoTecnicoMecanicoGases().get(0).getCdaExpide())
                                    .vigente(responseVR.getRuntPlacaVO().getListCertificadoTecnicoMecanicoGases().get(0).getVigente())
                                    .build();
                        } else {
                            VehiculoRunt.TecnicoMecanico.builder().build();
                        }
                    }

                    VehiculoRunt vehiculoRunt = VehiculoRunt.builder()
                            .placa(responseVR.getRuntPlacaVO().getPlacaVehiculo())
                            .licenciaTransito(responseVR.getRuntPlacaVO().getNroLicenciaTransito())
                            .estado(responseVR.getRuntPlacaVO().getEstadoVehiculo())
                            .tipoServicio(responseVR.getRuntPlacaVO().getTipoServicio())
                            .claseVehiculo(responseVR.getRuntPlacaVO().getClaseVehiculo())
                            .marca(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getMarca())
                            .linea(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getLinea())
                            .modelo(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getModelo())
                            .color(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getColor())
                            .serie(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getNroSerie())
                            .motor(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getNroMotor())
                            .chasis(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getNroChasis())
                            .vin(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getNroVin())
                            .cilindraje(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getCilindraje())
                            .tipoCarroceria(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getTipoCarroceria())
                            .tipoCombustible(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getTipoCombustible())
                            .fechaMatriculaInicial(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getFechaMatriculaInicial())
                            .autoridadTransito(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getAutoridadTransito())
                            .gravamenesPropiedad(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getGravamenesPropiedad())
                            .clasicoAntiguo(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getClasicoAntiguo())
                            .repotenciado(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getRepotenciado())
                            .regrabacionMotor(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getRegrabacionMotor())
                            .regrabacionChasis(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getRegrabacionChasis())
                            .regrabacionSerie(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getRegrabacionSerie())
                            .regrabacionVin(responseVR.getRuntPlacaVO().getInformacionGeneralVehiculo().getRegrabacionVin())
                            .capacidadCarga(responseVR.getRuntPlacaVO().getDatosTecnicosVehiculo().getCapacidadCarga())
                            .pesoBrutoVehicular(responseVR.getRuntPlacaVO().getDatosTecnicosVehiculo().getPesoBrutoVehicular())
                            .capacidadPasajeros(responseVR.getRuntPlacaVO().getDatosTecnicosVehiculo().getCapacidadPasajeros())
                            .capacidadPasajerosSentados(responseVR.getRuntPlacaVO().getDatosTecnicosVehiculo().getCapacidadPasajerosSentados())
                            .nroEjes(responseVR.getRuntPlacaVO().getDatosTecnicosVehiculo().getNroEjes())
                            .polizaSoat(
                                    VehiculoRunt.PolizaSoat.builder()
                                            .numeroPoliza(vrpoliza.getNumeroPoliza())
                                            .fechaExpedicion(vrpoliza.getFechaExpedicion())
                                            .fechaInicioVigencia(vrpoliza.getFechaInicioVigencia())
                                            .fechaFinVigencia(vrpoliza.getFechaFinVigencia())
                                            .entidadSoat(vrpoliza.getEntidadSoat())
                                            .estado(vrpoliza.getEstado())
                                            .build()
                            )
                            .tecnicoMecanico(
                                    VehiculoRunt.TecnicoMecanico.builder()
                                            .tipoRevision(vrcertificado.getTipoRevision())
                                            .fechaExpedicion(vrcertificado.getFechaExpedicion())
                                            .fechaVigencia(vrcertificado.getFechaVigencia())
                                            .cdaExpide(vrcertificado.getCdaExpide())
                                            .vigente(vrcertificado.getVigente())
                                            .build())
                            .build();

                    
                    //boolean runt_registrado = vehiculoRuntDao.adicionarVehiculoRunt(conex, vehiculoRunt);
                    boolean runt_registrado = true;

                    ClaseVehiculoDao claseVehiculoDao = new ClaseVehiculoDao();
                    CenClaseVehiculo cenClaseVehiculo = claseVehiculoDao.ConsultarClaseVehiculoByDescripcion(conex, vehiculoRunt.getClaseVehiculo());
                    if (cenClaseVehiculo != null) {
                        respuesta.put("clasevehiculoId", cenClaseVehiculo.getId());
                    }

                    TipoServicioDao tipoServicioDao = new TipoServicioDao();
                    CenTipoServicio cenTipoServicio = tipoServicioDao.ConsultarTipoServicioByDescripcion(conex, vehiculoRunt.getTipoServicio());
                    if (cenTipoServicio != null) {
                        respuesta.put("tiposervicioId", cenTipoServicio.getId());
                    }

                    if (runt_registrado) {
                        respuesta.put("status", "success");
                        respuesta.put("vehiculorunt", vehiculoRunt);
                    }

                } else {
                    respuesta.put("status", "fail");
                    respuesta.put("message", "Vehiculo no encontrado en runt");
                }

            }

        } catch (SQLException e) {
            respuesta.put("status", "error");
            respuesta.put("message", "Error al verificar el vehiculo");
            e.printStackTrace();

        } finally {
            if (conex != null) {
                try {
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
