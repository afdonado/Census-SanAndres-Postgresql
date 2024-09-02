package com.censo.controlador.verificacion;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.dao.VerificacionDao;
import com.censo.modelo.persistencia.CenCenso;
import com.censo.modelo.persistencia.CenHistorialVerificacion;
import com.censo.modelo.persistencia.CenUsuario;
import com.censo.modelo.persistencia.CenVerificacion;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegistrarVerificacion", urlPatterns = "/registrarVerificacion")
public class RegistrarVerificacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {

            if (request.getParameter("chkrunt") == null || request.getParameter("chkrunt").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'runt' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            if (request.getParameter("chkdocumentos") == null || request.getParameter("chkdocumentos").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'documentos' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            if (request.getParameter("chkfotos") == null || request.getParameter("chkfotos").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'fotos' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            if (request.getParameter("cmbestadosverificacion") == null || request.getParameter("cmbestadosverificacion").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'estado verificacion' no encontrado");

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
            
            if (request.getParameter("idcenso") == null || request.getParameter("idcenso").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'idcenso' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            CensoDao censoDao = new CensoDao();
            conex = censoDao.conectar();

            long idcenso = Long.parseLong(request.getParameter("idcenso"));
            CenCenso cencenso = censoDao.ConsultarCensoById(conex, idcenso);
            if (cencenso == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Censo no se encuentra registrado para registrar la verificacion");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String runt = "S";
            String documentos = "S";
            String fotos = "S";
            int estado = Integer.parseInt(request.getParameter("cmbestadosverificacion"));
            String observaciones = request.getParameter("txtobservaciones").toUpperCase().trim();

            CenUsuario cenusuarioSesion = (CenUsuario) request.getSession().getAttribute("usuario");

            VerificacionDao verificacionDao = new VerificacionDao();
            conex.setAutoCommit(false);

            CenVerificacion cenverificacion = new CenVerificacion();
            cenverificacion.setCen_id(idcenso);
            cenverificacion.setVerificado_runt(runt);
            cenverificacion.setVerificado_doc(documentos);
            cenverificacion.setVerificado_foto(fotos);
            cenverificacion.setObservaciones(observaciones);
            cenverificacion.setEstado(estado);
            cenverificacion.setUsu_id(cenusuarioSesion.getId());
            long idverificacion = verificacionDao.adicionarVerificacion(conex, cenverificacion);

            boolean registrado = false;

            if (idverificacion > 0) {

                CenHistorialVerificacion cenhistoriaverificacion = new CenHistorialVerificacion();
                cenhistoriaverificacion.setVer_id(idverificacion);
                cenhistoriaverificacion.setEstado(estado);
                cenhistoriaverificacion.setUsu_id(cenusuarioSesion.getId());
                cenhistoriaverificacion.setObservaciones(observaciones);
                long idhistorialverificacion = verificacionDao.adicionarHistorialVerificacion(conex, cenhistoriaverificacion);

                registrado = idhistorialverificacion > 0;

            }

            if (registrado) {
                conex.commit();
                respuesta.put("status", "success");
                respuesta.put("message", "Verificacion registrada exitosamente");
                respuesta.put("id", String.valueOf(idverificacion));
            } else {
                conex.rollback();
                respuesta.put("status", "fail");
                respuesta.put("message", "Verificacion no registrada");
            }

        } catch (NumberFormatException | SQLException e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            respuesta.put("status", "error");
            respuesta.put("message", "Error al registrar la verificacion");
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
