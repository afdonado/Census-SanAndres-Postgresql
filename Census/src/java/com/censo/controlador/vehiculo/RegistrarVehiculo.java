package com.censo.controlador.vehiculo;

import com.censo.modelo.dao.ColorDao;
import com.censo.modelo.dao.LineaDao;
import com.censo.modelo.dao.MarcaDao;
import com.censo.modelo.dao.PersonaVehiculoDao;
import com.censo.modelo.dao.VehiculoDao;
import com.censo.modelo.persistencia.CenColor;
import com.censo.modelo.persistencia.CenLinea;
import com.censo.modelo.persistencia.CenMarca;
import com.censo.modelo.persistencia.CenPersonaVehiculo;
import com.censo.modelo.persistencia.CenUsuario;
import com.censo.modelo.persistencia.CenVehiculo;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegistrarVehiculo", urlPatterns = "/registrarVehiculo")
public class RegistrarVehiculo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {
            
            VehiculoDao vehiculoDao = new VehiculoDao();
            conex = vehiculoDao.conectar();

            //Validar parametro placa
            if (request.getParameter("txtplaca") == null || request.getParameter("txtplaca").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'placa' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro motor
            if (request.getParameter("txtmotor") == null || request.getParameter("txtmotor").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'motor' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro chasis
            if (request.getParameter("txtchasis") == null || request.getParameter("txtchasis").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'chasis' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro serie
            if (request.getParameter("txtserie") == null || request.getParameter("txtserie").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'serie' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro clase vehiculo
            if (request.getParameter("cmbclasevehiculo") == null || request.getParameter("cmbclasevehiculo").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'clase vehiculo' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro tipo servicio
            if (request.getParameter("cmbtiposservicio") == null || request.getParameter("cmbtiposservicio").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'tipo servicio' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro tipo uso
            if (request.getParameter("cmbtiposuso") == null || request.getParameter("cmbtiposuso").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'tipo uso' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro color
            if (request.getParameter("txtcolores") == null || request.getParameter("txtcolores").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'color' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String nombreColor = request.getParameter("txtcolores").toUpperCase().trim();

            //Consultar color por nombre para obtener el id
            ColorDao colorDao = new ColorDao();
            CenColor cencolor = colorDao.ConsultarColorByNombre(conex, nombreColor);
            if (cencolor == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Color no valido");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro marca
            if (request.getParameter("txtmarcas") == null || request.getParameter("txtmarcas").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'marca' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String nombreMarca = request.getParameter("txtmarcas").toUpperCase().trim();

            //Consultar marca por nombre para obtener el id
            MarcaDao marcaDao = new MarcaDao();
            CenMarca cenmarca = marcaDao.ConsultarMarcaByNombre(conex, nombreMarca);
            if (cenmarca == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Marca no valida");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro linea
            if (request.getParameter("txtlineas") == null || request.getParameter("txtlineas").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'linea' no encontrado");
                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Consultar linea por nombre y idmarca para obtener el id
            String nombreLinea = request.getParameter("txtlineas").toUpperCase().trim();
            LineaDao lineaDao = new LineaDao();
            CenLinea cenlinea = lineaDao.ConsultarLineaByNombreIdMarca(conex, nombreLinea, cenmarca.getId());
            if (cenlinea == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Linea no valida");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro modelo
            if (request.getParameter("txtmodelo") == null || request.getParameter("txtmodelo").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'modelo' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro transformado
            if (request.getParameter("cmbtransformado") == null || request.getParameter("cmbtransformado").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'transformado' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro runt
            if (request.getParameter("cmbrunt") == null || request.getParameter("cmbrunt").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'runt' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro tecnomecanica
            if (request.getParameter("cmbtecnomecanica") == null || request.getParameter("cmbtecnomecanica").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'tecnomecanica' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            PersonaVehiculoDao daoPersonaVehiculo = new PersonaVehiculoDao();

            CenUsuario cenusuario = (CenUsuario) request.getSession().getAttribute("usuario");

            String placa = request.getParameter("txtplaca").toUpperCase().trim();
            String motor = request.getParameter("txtmotor").toUpperCase().trim();
            String chasis = request.getParameter("txtchasis").toUpperCase().trim();
            String serie = request.getParameter("txtserie").toUpperCase().trim();
            int claseVehiculo = Integer.parseInt(request.getParameter("cmbclasevehiculo"));
            int tipoServicio = Integer.parseInt(request.getParameter("cmbtiposservicio"));
            int tipoUso = Integer.parseInt(request.getParameter("cmbtiposuso"));

            long modelo = Long.parseLong(request.getParameter("txtmodelo"));
            String transformado = request.getParameter("cmbtransformado").toUpperCase().trim();

            String runt = request.getParameter("cmbrunt").toUpperCase().trim();
            Date fechaMatricula = null;
            String licenciaTransito = "";
            long paisMatricula = 0;
            long municipioMatricula = 0;
            String ciudaMatricula = "";

            if (runt.equals("S")) {
                //Validar parametro fecha matricula
                if (request.getParameter("txtfechamatricula") == null || request.getParameter("txtfechamatricula").isEmpty()) {
                    respuesta.put("status", "error");
                    respuesta.put("message", "Parametro 'fecha matricula' no encontrado");

                    String jsonError = new Gson().toJson(respuesta);
                    response.getWriter().write(jsonError);
                    return;
                }

                //Validar parametro licencia de transito
                if (request.getParameter("txtlicenciatransito") == null || request.getParameter("txtlicenciatransito").isEmpty()) {
                    respuesta.put("status", "error");
                    respuesta.put("message", "Parametro 'numero licencia transito' no encontrado");

                    String jsonError = new Gson().toJson(respuesta);
                    response.getWriter().write(jsonError);
                    return;
                }

                //Validar parametro pais matricula
                if (request.getParameter("cmbpaismatricula") == null || request.getParameter("cmbpaismatricula").isEmpty()) {
                    respuesta.put("status", "error");
                    respuesta.put("message", "Parametro 'pais matricula' no encontrado");

                    String jsonError = new Gson().toJson(respuesta);
                    response.getWriter().write(jsonError);
                    return;
                }

                licenciaTransito = request.getParameter("txtlicenciatransito").toUpperCase().trim();
                fechaMatricula = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechamatricula")).getTime());
                paisMatricula = Long.parseLong(request.getParameter("cmbpaismatricula"));

                if (paisMatricula == 18) {

                    //Validar parametro municipio matricula
                    if (request.getParameter("cmbmunicipiomatricula") == null || request.getParameter("cmbmunicipiomatricula").isEmpty()) {
                        respuesta.put("status", "error");
                        respuesta.put("message", "Parametro 'municipio matricula' no encontrado");

                        String jsonError = new Gson().toJson(respuesta);
                        response.getWriter().write(jsonError);
                        return;
                    }

                    municipioMatricula = Long.parseLong(request.getParameter("cmbmunicipiomatricula"));

                } else {

                    //Validar parametro ciudad matricula
                    if (request.getParameter("txtciudadmatricula") == null || request.getParameter("txtciudadmatricula").isEmpty()) {
                        respuesta.put("status", "error");
                        respuesta.put("message", "Parametro 'ciudad matricula' no encontrado");

                        String jsonError = new Gson().toJson(respuesta);
                        response.getWriter().write(jsonError);
                        return;
                    }

                    ciudaMatricula = request.getParameter("txtciudadmatricula").toUpperCase().trim();
                }
            }

            int tipoDocumentoImportacion = Integer.parseInt(request.getParameter("cmbtiposimportacion"));
            String documentoImportacion = "";
            Date fechaImportacion = null;
            long paisImportacion = 0;

            if (tipoDocumentoImportacion != 0) {

                //Validar parametro fecha importacion
                if (request.getParameter("txtfechaimportacion") == null || request.getParameter("txtfechaimportacion").isEmpty()) {
                    respuesta.put("status", "error");
                    respuesta.put("message", "Parametro 'fecha importacion' no encontrado");

                    String jsonError = new Gson().toJson(respuesta);
                    response.getWriter().write(jsonError);
                    return;
                }

                //Validar parametro numero documento importacion
                if (request.getParameter("txtdocumentoimportacion") == null || request.getParameter("txtdocumentoimportacion").isEmpty()) {
                    respuesta.put("status", "error");
                    respuesta.put("message", "Parametro 'numero documento importacion' no encontrado");

                    String jsonError = new Gson().toJson(respuesta);
                    response.getWriter().write(jsonError);
                    return;
                }

                //Validar parametro numero documento importacion
                if (request.getParameter("cmbpaisimportacion") == null || request.getParameter("cmbpaisimportacion").isEmpty()) {
                    respuesta.put("status", "error");
                    respuesta.put("message", "Parametro 'pais importacion' no encontrado");

                    String jsonError = new Gson().toJson(respuesta);
                    response.getWriter().write(jsonError);
                    return;
                }

                documentoImportacion = request.getParameter("txtdocumentoimportacion").toUpperCase().trim();
                fechaImportacion = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechaimportacion")).getTime());
                paisImportacion = Long.parseLong(request.getParameter("cmbpaisimportacion"));
            }

            String soat = request.getParameter("cmbsoat").toUpperCase().trim();
            Date fechaVencimientoSoat = null;
            if (soat.equals("S")) {
                //Validar parametro fecha vencimiento  soat
                if (request.getParameter("txtfechavsoat") == null || request.getParameter("txtfechavsoat").isEmpty()) {
                    respuesta.put("status", "error");
                    respuesta.put("message", "Parametro 'fecha vencimiento soat' no encontrado");

                    String jsonError = new Gson().toJson(respuesta);
                    response.getWriter().write(jsonError);
                    return;
                }

                fechaVencimientoSoat = new Date(new SimpleDateFormat("dd/MM/yyyy")
                        .parse(request.getParameter("txtfechavsoat")).getTime());
            }

            String tecnoMecanica = request.getParameter("cmbtecnomecanica").toUpperCase().trim();
            Date fechaVencimientoTecnomecanica = null;
            if (tecnoMecanica.equals("S")) {
                //Validar parametro fecha vencimiento tecnomecanica
                if (request.getParameter("txtfechavtecnomecanica") == null || request.getParameter("txtfechavtecnomecanica").isEmpty()) {
                    respuesta.put("status", "error");
                    respuesta.put("message", "Parametro 'fecha vencimiento tecnomecanica' no encontrado");

                    String jsonError = new Gson().toJson(respuesta);
                    response.getWriter().write(jsonError);
                    return;
                }

                fechaVencimientoTecnomecanica = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy")
                        .parse(request.getParameter("txtfechavtecnomecanica")).getTime());
            }

            //Validar parametro observacion
            if (request.getParameter("txtobservaciones") == null || request.getParameter("txtobservaciones").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'observaciones' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            //Validar parametro cantidad personas
            if (request.getParameter("txtcantidadpersonas") == null || request.getParameter("txtcantidadpersonas").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'personas' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            String observaciones = request.getParameter("txtobservaciones").toUpperCase().trim();

            int cantpersonas = (Integer.parseInt(request.getParameter("txtcantidadpersonas")));

            if (cantpersonas > 0) {

                try {

                    boolean registrado = false;

                    conex.setAutoCommit(false);

                    CenVehiculo cenvehiculo = new CenVehiculo();
                    cenvehiculo.setPlaca_veh(placa);
                    cenvehiculo.setChasis(chasis);
                    cenvehiculo.setSerie(serie);
                    cenvehiculo.setMotor(motor);
                    cenvehiculo.setClase_veh(claseVehiculo);
                    cenvehiculo.setTipo_servicio(tipoServicio);
                    cenvehiculo.setTipo_uso(tipoUso);
                    cenvehiculo.setCol_id(cencolor.getId());
                    cenvehiculo.setLin_id(cenlinea.getId());
                    cenvehiculo.setModelo(modelo);
                    cenvehiculo.setTransformado(transformado);
                    cenvehiculo.setRunt(runt);
                    cenvehiculo.setLicencia_transito(licenciaTransito);
                    cenvehiculo.setFecha_matricula(fechaMatricula);
                    cenvehiculo.setPai_id_matricula(paisMatricula);
                    cenvehiculo.setMun_id_matricula(municipioMatricula);
                    cenvehiculo.setCiudad_matricula(ciudaMatricula);
                    cenvehiculo.setTipodoc_importacion(tipoDocumentoImportacion);
                    cenvehiculo.setDoc_importacion(documentoImportacion);
                    cenvehiculo.setFecha_importacion(fechaImportacion);
                    cenvehiculo.setPai_id_origen(paisImportacion);
                    cenvehiculo.setSoat(soat);
                    cenvehiculo.setFechaven_soat(fechaVencimientoSoat);
                    cenvehiculo.setTecno_mecanica(tecnoMecanica);
                    cenvehiculo.setFechaven_tecno(fechaVencimientoTecnomecanica);
                    cenvehiculo.setObservaciones(observaciones);
                    cenvehiculo.setUsu_id(cenusuario.getId());
                    cenvehiculo.setEstado(1);
                    long idVehiculo = vehiculoDao.adicionarVehiculo(conex, cenvehiculo);

                    if (idVehiculo > 0) {

                        CenPersonaVehiculo cenpersonavehiculo = new CenPersonaVehiculo();

                        for (int i = 1; i <= cantpersonas; i++) {

                            int tipoPersona = Integer.parseInt(request.getParameter("cmbtipospersona" + i));
                            long idPersona = Long.parseLong(request.getParameter("idpersona" + i));

                            cenpersonavehiculo.setTper_id(tipoPersona);
                            cenpersonavehiculo.setPer_id(idPersona);
                            cenpersonavehiculo.setVeh_id(idVehiculo);
                            cenpersonavehiculo.setUsu_id(cenusuario.getId());
                            cenpersonavehiculo.setEstado(1);
                            long idPersonaVehiculo = daoPersonaVehiculo.adicionarPersonaVehiculo(conex, cenpersonavehiculo);

                            if (idPersonaVehiculo > 0) {
                                registrado = true;
                            } else {
                                registrado = false;
                                break;
                            }
                        }
                    }

                    if (registrado) {
                        conex.commit();
                        respuesta.put("status", "success");
                        respuesta.put("message", "Vehiculo registrado exitosamente");
                        respuesta.put("id", String.valueOf(idVehiculo));
                    } else {
                        conex.rollback();
                        respuesta.put("status", "fail");
                        respuesta.put("message", "Vehiculo no registrado");
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
                    respuesta.put("message", "Error al registrar el vehiculo");
                    e.printStackTrace();
                }
            }
        } catch (NumberFormatException | SQLException | ParseException e) {
            respuesta.put("status", "error");
            respuesta.put("message", "Error al registrar el vehiculo");
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
        response.getWriter().write(json);
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
