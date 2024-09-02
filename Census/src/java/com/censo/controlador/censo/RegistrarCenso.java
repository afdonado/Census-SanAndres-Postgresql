package com.censo.controlador.censo;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.persistencia.CenCenso;
import com.censo.modelo.persistencia.CenUsuario;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegistrarCenso", urlPatterns = "/registrarCenso")
public class RegistrarCenso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {

            CenUsuario cenusuario = (CenUsuario) request.getSession().getAttribute("usuario");
            CenCenso cencenso = new CenCenso();

            if (request.getParameter("txtnumerocenso") == null || request.getParameter("txtnumerocenso").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'numero censo' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            String numero = request.getParameter("txtnumerocenso").toUpperCase().trim();
            
            if (numero.length() < 6) {
                String prefijo = "ACS";
                numero = prefijo + ("00000".substring(0, 5 - (numero + "").length())) + numero;
            } else {
                respuesta.put("status", "error");
                respuesta.put("message", "Numero de censo no puede ser mayor a 5 digitos");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            if (request.getParameter("txtfechacenso") == null || request.getParameter("txtfechacenso").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'observaciones' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
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
            
            if (request.getParameter("idpersona") == null || request.getParameter("idpersona").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'id persona' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            if (request.getParameter("cmbtipospersona") == null || request.getParameter("cmbtipospersona").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'tipo persona' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            if (request.getParameter("txtobservaciones") == null || request.getParameter("cmbtipospersona").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'observaciones' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            
            Date fechaCenso = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechacenso")).getTime());
            Date fechaActual = new java.sql.Date(new java.util.Date().getTime());
            String hora = new java.text.SimpleDateFormat("HHmm").format(fechaActual);
            int puntoAtencion = Integer.parseInt(request.getParameter("cmbpuntosatencion"));
            long idvehiculo = Long.parseLong(request.getParameter("idvehiculo"));
            long idpersona = Long.parseLong(request.getParameter("idpersona"));
            int tipoPersona = Integer.parseInt(request.getParameter("cmbtipospersona"));
            String observaciones = request.getParameter("txtobservaciones").toUpperCase().trim();
            
            CensoDao censoDao = new CensoDao();
            conex = censoDao.conectar();
            conex.setAutoCommit(false);

            cencenso.setFecha(fechaCenso);
            cencenso.setHora(hora);
            cencenso.setPun_id(puntoAtencion);
            cencenso.setVeh_id(idvehiculo);
            cencenso.setPer_id(idpersona);
            cencenso.setTper_id(tipoPersona);
            cencenso.setUsu_id(cenusuario.getId());
            cencenso.setEstado(1);
            cencenso.setNumero(numero);
            cencenso.setObservaciones(observaciones);

            long idcenso = censoDao.adicionarCenso(conex, cencenso);

            if (idcenso > 0) {
                conex.commit();
                respuesta.put("status", "success");
                respuesta.put("message", "Censo registrado exitosamente");
                respuesta.put("id", String.valueOf(idcenso));
            } else {
                conex.rollback();
                respuesta.put("status", "fail");
                respuesta.put("message", "Censo no registrado");
            }

        } catch (SQLException | ParseException e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            respuesta.put("status", "error");
            respuesta.put("message", "Error al registrar el censo");
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
