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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ModificarVehiculo", urlPatterns = "/modificarVehiculo")
public class ModificarVehiculo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {

            //Validar parametro idvehiculo
            if (request.getParameter("idvehiculo") == null || request.getParameter("idvehiculo").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'id vehiculo' no encontrado para modificar vehiculo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            long idvehiculo = Long.parseLong(request.getParameter("idvehiculo"));

            VehiculoDao vehiculoDao = new VehiculoDao();
            conex = vehiculoDao.conectar();

            //Verificar que el vehiculo existe para modificar
            CenVehiculo cenVehiculo = vehiculoDao.ConsultarVehiculoById(conex, idvehiculo);
            if (cenVehiculo == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Vehiculo no se encuentra registrado para modificarlo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro placa
            if (request.getParameter("txtplaca") == null || request.getParameter("txtplaca").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'placa' no encontrado para modificar vehiculo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro motor
            if (request.getParameter("txtmotor") == null || request.getParameter("txtmotor").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'motor' no encontrado para modificar vehiculo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro chasis
            if (request.getParameter("txtchasis") == null || request.getParameter("txtchasis").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'chasis' no encontrado para modificar vehiculo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro serie
            if (request.getParameter("txtserie") == null || request.getParameter("txtserie").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'serie' no encontrado para modificar vehiculo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro clase vehiculo
            if (request.getParameter("cmbclasevehiculo") == null || request.getParameter("cmbclasevehiculo").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'clase vehiculo' no encontrado para modificar vehiculo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro tipo servicio
            if (request.getParameter("cmbtiposservicio") == null || request.getParameter("cmbtiposservicio").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'tipo servicio' no encontrado para modificar vehiculo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro tipo uso
            if (request.getParameter("cmbtiposuso") == null || request.getParameter("cmbtiposuso").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'tipo uso' no encontrado para modificar vehiculo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro color
            if (request.getParameter("txtcolores") == null || request.getParameter("txtcolores").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'color' no encontrado para modificar vehiculo");

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
                respuesta.put("message", "Parametro 'marca' no encontrado para modificar vehiculo");

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
                respuesta.put("message", "Parametro 'linea' no encontrado para modificar vehiculo");

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

            String placa = request.getParameter("txtplaca").toUpperCase().trim();
            String motor = request.getParameter("txtmotor").toUpperCase().trim();
            String chasis = request.getParameter("txtchasis").toUpperCase().trim();
            String serie = request.getParameter("txtserie").toUpperCase().trim();
            int claseVeh = Integer.parseInt(request.getParameter("cmbclasevehiculo"));
            int tipoServicio = Integer.parseInt(request.getParameter("cmbtiposservicio"));
            int tipoUso = Integer.parseInt(request.getParameter("cmbtiposuso"));

            long modeloveh = Long.parseLong(request.getParameter("txtmodelo"));
            String transformado = request.getParameter("cmbtransformado").toUpperCase().trim();
            String runt = request.getParameter("cmbrunt").toUpperCase().trim();
            String licenciaTransito = request.getParameter("txtlicenciatransito").toUpperCase().trim();
            Date fechaMatricula = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechamatricula")).getTime());
            long paisMatricula = Long.parseLong(request.getParameter("cmbpaismatricula"));
            long municipioMatricula = Long.parseLong(request.getParameter("cmbmunicipiomatricula"));
            String ciudaMatricula = request.getParameter("txtciudadmatricula").toUpperCase().trim();
            int tipoDocImportacion = Integer.parseInt(request.getParameter("cmbtiposimportacion"));
            String documentoImportacion = "";
            Date fechaImportacion = null;
            long paisImportacion = 0;

            if (tipoDocImportacion != 0) {
                documentoImportacion = request.getParameter("txtdocumentoimportacion").toUpperCase().trim();
                fechaImportacion = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechaimportacion")).getTime());
                paisImportacion = Long.parseLong(request.getParameter("cmbpaisimportacion"));
            }
            String soat = request.getParameter("cmbsoat").toUpperCase().trim();
            Date fechaVenSoat = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechavsoat")).getTime());
            String tecnoMecanica = request.getParameter("cmbtecnomecanica").toUpperCase().trim();
            Date fechaVenTecnomecanica = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechavtecnomecanica")).getTime());

            String observaciones = request.getParameter("txtobservaciones").toUpperCase().trim();
            int cantpersonas = (Integer.parseInt(request.getParameter("txtcantidadpersonas")));

            CenUsuario cenusuario = (CenUsuario) request.getSession().getAttribute("usuario");

            conex.setAutoCommit(false);

            CenVehiculo cenvehiculo = new CenVehiculo();
            cenvehiculo.setId(idvehiculo);
            cenvehiculo.setPlaca_veh(placa);
            cenvehiculo.setChasis(chasis);
            cenvehiculo.setSerie(serie);
            cenvehiculo.setMotor(motor);
            cenvehiculo.setClase_veh(claseVeh);
            cenvehiculo.setTipo_servicio(tipoServicio);
            cenvehiculo.setTipo_uso(tipoUso);
            cenvehiculo.setCol_id(cencolor.getId());
            cenvehiculo.setLin_id(cenlinea.getId());
            cenvehiculo.setModelo(modeloveh);
            cenvehiculo.setTransformado(transformado);
            cenvehiculo.setRunt(runt);
            cenvehiculo.setLicencia_transito(licenciaTransito);
            cenvehiculo.setFecha_matricula(fechaMatricula);
            cenvehiculo.setPai_id_matricula(paisMatricula);
            cenvehiculo.setMun_id_matricula(municipioMatricula);
            cenvehiculo.setCiudad_matricula(ciudaMatricula);
            cenvehiculo.setTipodoc_importacion(tipoDocImportacion);
            cenvehiculo.setDoc_importacion(documentoImportacion);
            cenvehiculo.setFecha_importacion(fechaImportacion);
            cenvehiculo.setPai_id_origen(paisImportacion);
            cenvehiculo.setSoat(soat);
            cenvehiculo.setFechaven_soat(fechaVenSoat);
            cenvehiculo.setTecno_mecanica(tecnoMecanica);
            cenvehiculo.setFechaven_tecno(fechaVenTecnomecanica);
            cenvehiculo.setObservaciones(observaciones);
            cenvehiculo.setEstado(1);
            boolean modificado = vehiculoDao.modificarVehiculo(conex, cenvehiculo);

            PersonaVehiculoDao personaVehiculoDao = new PersonaVehiculoDao();

            if (modificado) {

                List<HashMap<String, Object>> listapersonasVehiculo = personaVehiculoDao.ListarHashPersonasVehiculoActivasByIdVehiculo(conex, cenvehiculo.getId());

                if (!listapersonasVehiculo.isEmpty()) {

                    for (HashMap hash : listapersonasVehiculo) {
                        long idperveh = Long.parseLong(hash.get("PV_ID").toString());
                        int estadoperveh = Integer.parseInt(request.getParameter("estadoperveh" + idperveh));
                        if (estadoperveh == 2) {
                            modificado = personaVehiculoDao.anularPersonaVehiculo(conex, idperveh);
                            if (!modificado) {
                                break;
                            }
                        } else {
                            CenPersonaVehiculo cenpersonavehiculo = new CenPersonaVehiculo();
                            int tipoPersona = Integer.parseInt(request.getParameter("cmbtipospersona" + idperveh));
                            long idPersona = Long.parseLong(request.getParameter("idpersona" + idperveh));

                            cenpersonavehiculo.setTper_id(tipoPersona);
                            cenpersonavehiculo.setPer_id(idPersona);
                            cenpersonavehiculo.setUsu_id(cenusuario.getId());
                            cenpersonavehiculo.setId(idperveh);
                            modificado = personaVehiculoDao.modificarPersonaVehiculo(conex, cenpersonavehiculo);
                            if (!modificado) {
                                break;
                            }
                        }
                    }
                }
            }

            if (modificado) {

                if (cantpersonas > 0) {

                    CenPersonaVehiculo cenpersonavehiculo = new CenPersonaVehiculo();

                    for (int i = 1; i <= cantpersonas; i++) {

                        int tipoPersona = Integer.parseInt(request.getParameter("cmbtipospersona" + i));
                        long idPersona = Long.parseLong(request.getParameter("idpersona" + i));

                        cenpersonavehiculo.setTper_id(tipoPersona);
                        cenpersonavehiculo.setPer_id(idPersona);
                        cenpersonavehiculo.setVeh_id(idvehiculo);
                        cenpersonavehiculo.setUsu_id(cenusuario.getId());
                        cenpersonavehiculo.setEstado(1);
                        long idPersonaVehiculo = personaVehiculoDao.adicionarPersonaVehiculo(conex, cenpersonavehiculo);

                        modificado = idPersonaVehiculo != 0;
                        if (!modificado) {
                            break;
                        }
                    }
                }
            }

            if (modificado) {
                conex.commit();
                respuesta.put("status", "success");
                respuesta.put("message", "Vehiculo modificado exitosamente");
                respuesta.put("id", String.valueOf(idvehiculo));
            } else {
                conex.rollback();
                respuesta.put("status", "fail");
                respuesta.put("message", "Vehiculo no modificado");
            }

        } catch (IOException | NumberFormatException | SQLException | ParseException e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            respuesta.put("status", "error");
            respuesta.put("message", "Error al modificar el vehiculo");
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
