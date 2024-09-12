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
import javax.sql.DataSource;

@WebServlet(name = "ModificarVerificacion", urlPatterns = "/modificarVerificacion")
public class ModificarVerificacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {
            
            conex = dataSource.getConnection();
            
            if (request.getParameter("idverificacion") == null || request.getParameter("idverificacion").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'id verificacion' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            VerificacionDao verificacionDao = new VerificacionDao();

            int idverificacion = Integer.parseInt(request.getParameter("idverificacion"));
            CenVerificacion cenverificacion = verificacionDao.ConsultarVerificacionByIdVerificacion(conex, idverificacion);
            if (cenverificacion == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Verificacion no se encuentra registrada para modificarla");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            if (request.getParameter("documento") == null || request.getParameter("documento").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'documentos' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            if (request.getParameter("foto") == null || request.getParameter("foto").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'fotos' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            if (request.getParameter("estadoverificacion") == null || request.getParameter("estadoverificacion").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'estado verificacion' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            if (request.getParameter("observacion") == null) {
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

            int idcenso = Integer.parseInt(request.getParameter("idcenso"));
            CenCenso cencenso = censoDao.ConsultarCensoById(conex, idcenso);
            if (cencenso == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Censo no se encuentra registrado para registrar la verificacion");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String documentos = request.getParameter("documento");
            String fotos = request.getParameter("foto");
            int estado = Integer.parseInt(request.getParameter("estadoverificacion"));
            String observaciones = request.getParameter("observacion").toUpperCase().trim();

            CenUsuario cenusuarioSesion = (CenUsuario) request.getSession().getAttribute("usuario");

            conex.setAutoCommit(false);

            cenverificacion.setVerificado_doc(documentos);
            cenverificacion.setVerificado_foto(fotos);
            cenverificacion.setObservaciones(observaciones);
            cenverificacion.setEstado(estado);
            cenverificacion.setUsu_id(cenusuarioSesion.getId());
            boolean modificado = verificacionDao.modificarVerificacion(conex, cenverificacion);

            if (modificado) {
                CenHistorialVerificacion cenhistoriaverificacion = new CenHistorialVerificacion();
                cenhistoriaverificacion.setVer_id(cenverificacion.getId());
                cenhistoriaverificacion.setEstado(estado);
                cenhistoriaverificacion.setUsu_id(cenusuarioSesion.getId());
                cenhistoriaverificacion.setObservaciones(observaciones);
                long idhistorialverificacion = verificacionDao.adicionarHistorialVerificacion(conex, cenhistoriaverificacion);
                
                modificado = idhistorialverificacion > 0;
            }
            
            if (modificado) {
                conex.commit();
                respuesta.put("status", "success");
                respuesta.put("message", "Verificacion modificada exitosamente");
                respuesta.put("id", String.valueOf(idcenso));
            } else {
                conex.rollback();
                respuesta.put("status", "fail");
                respuesta.put("message", "Vehiculo no modificado");
            }

        } catch (IOException | NumberFormatException | SQLException e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            respuesta.put("status", "error");
            respuesta.put("message", "Error al modificar la modificacion");
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
