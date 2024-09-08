package com.censo.controlador.vehiculo;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.dao.Vehiculo2017Dao;
import com.censo.modelo.dao.VehiculoDao;
import com.censo.modelo.persistencia.CenCenso;
import com.censo.modelo.persistencia.CenVehiculo;
import com.censo.modelo.persistencia.Vehiculo2017;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "VerificarVehiculo2017", urlPatterns = {"/verificarVehiculo2017"})
public class VerificarVehiculo2017 extends HttpServlet {

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

            if (request.getParameter("opcion") == null || request.getParameter("opcion").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'opcion' no encontrado para verificar vehiculo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            int tipoReferencia = Integer.parseInt(request.getParameter("tiporeferencia"));
            String valorReferencia = request.getParameter("valorreferencia");
            int opcion = Integer.parseInt(request.getParameter("opcion"));

            VehiculoDao vehiculoDao = new VehiculoDao();
            conex = vehiculoDao.conectar();

            CenVehiculo cenvehiculo = vehiculoDao.ConsultarVehiculoByReferencia(conex, tipoReferencia, valorReferencia);

            if (cenvehiculo != null) {

                if (opcion == 1) {

                    //Respuesta para la validacion si el vehiculo está o no registrado
                    respuesta.put("status", "fail");
                    if (tipoReferencia == 1) {
                        respuesta.put("message", "Placa no valida, ya se encuentra registrada");
                        respuesta.put("input", "#txtplaca");
                    }
                    if (tipoReferencia == 2) {
                        respuesta.put("message", "Motor no valido, ya se encuentra registrado");
                        respuesta.put("input", "#txtmotor");
                    }
                    if (tipoReferencia == 3) {
                        respuesta.put("message", "Chasis no valido, ya se encuentra registrado");
                        respuesta.put("input", "#txtchasis");
                    }
                    if (tipoReferencia == 4) {
                        respuesta.put("message", "Serie no valida, ya se encuentra registrada");
                        respuesta.put("input", "#txtserie");
                    }
                }

                if (opcion == 2) {

                    //Verificar si el vehiculo ademas de registrado está censado
                    CensoDao censoDao = new CensoDao();
                    CenCenso cencenso = censoDao.ConsultarCensoByIdVehiculo(conex, cenvehiculo.getId());

                    if (cencenso == null) {
                        respuesta.put("status", "success");
                        respuesta.put("message", "Vehiculo valido");
                        respuesta.put("id", String.valueOf(cenvehiculo.getId()));
                    } else {
                        respuesta.put("status", "fail");
                        respuesta.put("message", "Vehiculo no valido, ya se encuentra censado");
                    }
                }
            } else {

                if (opcion == 1) {

                    Vehiculo2017Dao vehiculo2017Dao = new Vehiculo2017Dao();
                    Vehiculo2017 vehiculo2017 = new Vehiculo2017();

                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                    Date fechaActual = new Date(new Date().getTime());

                    if (tipoReferencia == 1) {
                        vehiculo2017 = vehiculo2017Dao.ConsultarVehiculo2017ByPlaca(conex, valorReferencia);

                        if (vehiculo2017 == null) {
                            respuesta.put("message", "Placa valida");
                            respuesta.put("input", "#txtplaca");
                        } else {
                            respuesta.put("message", "Placa encontrada en vehiculos 2017");
                        }
                    }

                    if (tipoReferencia == 2) {
                        vehiculo2017 = vehiculo2017Dao.ConsultarVehiculo2017ByMotor(conex, valorReferencia);

                        if (vehiculo2017 == null) {
                            respuesta.put("message", "Motor valido");
                            respuesta.put("input", "#txtmotor");
                        } else {
                            respuesta.put("message", "Motor encontrado en vehiculos 2017");
                        }
                    }

                    if (tipoReferencia == 3) {
                        vehiculo2017 = vehiculo2017Dao.ConsultarVehiculo2017ByChasis(conex, valorReferencia);

                        if (vehiculo2017 == null) {
                            respuesta.put("message", "Chasis valido");
                            respuesta.put("input", "#txtchasis");
                        } else {
                            respuesta.put("message", "Chasis encontrado en vehiculos 2017");
                        }
                    }

                    if (tipoReferencia == 4) {
                        vehiculo2017 = vehiculo2017Dao.ConsultarVehiculo2017ByChasis(conex, valorReferencia);

                        if (vehiculo2017 == null) {
                            respuesta.put("message", "Serie valido");
                            respuesta.put("input", "#txtserie");
                        } else {
                            respuesta.put("message", "Serie encontrada en vehiculos 2017");
                        }
                    }

                    if (vehiculo2017 != null) {

                        String fechaMatricula = vehiculo2017.getFechaMatricula() == null ? formato.format(fechaActual) : formato.format(vehiculo2017.getFechaMatricula());
                        String fechaVenSoat = vehiculo2017.getFechaVenSoat() == null ? formato.format(fechaActual) : formato.format(vehiculo2017.getFechaVenSoat());
                        String fechaVenTecnomecanica = vehiculo2017.getFechaVenTecnomecanica() == null ? formato.format(fechaActual) : formato.format(vehiculo2017.getFechaVenTecnomecanica());
                        String fechaImportacion = vehiculo2017.getFechaImportacion() == null ? formato.format(fechaActual) : formato.format(vehiculo2017.getFechaImportacion());

                        respuesta.put("status", "success2017");
                        respuesta.put("vehiculo", vehiculo2017);
                        respuesta.put("fechaMatricula", fechaMatricula);
                        respuesta.put("fechaVenSoat", fechaVenSoat);
                        respuesta.put("fechaVenTecnomecanica", fechaVenTecnomecanica);
                        respuesta.put("fechaImportacion", fechaImportacion);

                    } else {
                        respuesta.put("status", "success");
                    }

                }

                if (opcion == 2) {

                    //Verificar si el vehiculo ademas de registrado está censado
                    respuesta.put("status", "fail");
                    if (tipoReferencia == 1) {
                        respuesta.put("message", "Placa no valida, no se encuentra registrada");
                        respuesta.put("input", "#txtplaca");
                    }
                    if (tipoReferencia == 2) {
                        respuesta.put("message", "Motor no valido, no se encuentra registrado");
                        respuesta.put("input", "#txtmotor");
                    }
                    if (tipoReferencia == 3) {
                        respuesta.put("message", "Chasis no valido, no se encuentra registrado");
                        respuesta.put("input", "#txtchasis");
                    }
                    if (tipoReferencia == 4) {
                        respuesta.put("message", "Serie no valida, no se encuentra registrado");
                        respuesta.put("input", "#txtserie");
                    }
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
