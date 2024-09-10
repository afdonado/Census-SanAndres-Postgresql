package com.censo.controlador.censo;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.persistencia.CenCenso;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ModificarCenso", urlPatterns = "/modificarCenso")
public class ModificarCenso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {

            if (request.getParameter("idcenso") == null || request.getParameter("idcenso").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'idcenso' no encontrado para modificar censo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            CensoDao censoDao = new CensoDao();
            conex = censoDao.conectar();

            int idcenso = Integer.parseInt(request.getParameter("idcenso"));
            CenCenso cencenso = censoDao.ConsultarCensoById(conex, idcenso);
            if (cencenso == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Censo no se encuentra registrado para modificarlo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            if (request.getParameter("txtnumerocenso") == null || request.getParameter("txtnumerocenso").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'numero censo' no encontrado para modificar censo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String numero = request.getParameter("txtnumerocenso");
            if (!numero.equals(cencenso.getNumero())) {
                if (numero.length() < 6) {
                    String prefijo = "ACS";
                    numero = (prefijo + ("00000".substring(0, 5 - (numero + "").length())) + numero).toUpperCase().trim();
                } else {
                    respuesta.put("status", "error");
                    respuesta.put("message", "Numero de censo no puede ser mayor a 5 digitos");

                    String jsonError = new Gson().toJson(respuesta);
                    response.getWriter().write(jsonError);
                    return;
                }

                if (!numero.equals(cencenso.getNumero())) {
                    CenCenso cencensonew = censoDao.ConsultarCensoByNumero(conex, numero);
                    if (cencensonew != null) {
                        respuesta.put("status", "error");
                        respuesta.put("message", "Numero de censo no valido");

                        String jsonError = new Gson().toJson(respuesta);
                        response.getWriter().write(jsonError);
                        return;
                    }
                }
            }
            /*
            //Se debe descomentar para cuando se vaya a editar la fecha del censo tambien
            if (request.getParameter("txtfechacenso") == null || request.getParameter("txtfechacenso").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'fecha censo' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
             */

            if (request.getParameter("cmbpuntosatencion") == null || request.getParameter("cmbpuntosatencion").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'punto de atencion' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            if (request.getParameter("idvehiculo") == null || request.getParameter("idvehiculo").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'id vehiculo' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            if (request.getParameter("txtobservaciones") == null || request.getParameter("txtobservaciones").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'observaciones' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaCenso = LocalDate.parse(request.getParameter("txtfechacenso"),formatter);
            
            DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime horaActual = LocalTime.now();
            String hora = horaActual.format(formatterHora);
            
            int puntoAtencion = Integer.parseInt(request.getParameter("cmbpuntosatencion"));
            int idvehiculo = Integer.parseInt(request.getParameter("idvehiculo"));
            String observaciones = request.getParameter("txtobservaciones").toUpperCase().trim();

            conex.setAutoCommit(false);

            cencenso.setFecha(fechaCenso == null ? null : Date.valueOf(fechaCenso));
            cencenso.setHora(hora);
            cencenso.setPun_id(puntoAtencion);
            cencenso.setVeh_id(idvehiculo);
            cencenso.setNumero(numero);
            cencenso.setObservaciones(observaciones);

            boolean modificado = censoDao.modificarCenso(conex, cencenso);

            if (modificado) {
                conex.commit();
                respuesta.put("status", "success");
                respuesta.put("message", "Censo modificado exitosamente");
                respuesta.put("id", String.valueOf(idcenso));
            } else {
                conex.rollback();
                respuesta.put("status", "fail");
                respuesta.put("message", "Censo no modificado");
            }

            //} catch (SQLException | ParseException e) {
        } catch (SQLException e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            respuesta.put("status", "error");
            respuesta.put("message", "Error al modificar el censo");
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
