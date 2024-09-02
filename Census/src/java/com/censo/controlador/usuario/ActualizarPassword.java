package com.censo.controlador.usuario;

import com.censo.modelo.dao.UsuarioDao;
import com.censo.modelo.persistencia.CenUsuario;
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
import org.apache.commons.codec.digest.DigestUtils;

@WebServlet(name = "ActualizarPassword", urlPatterns = "/actualizarPassword")
public class ActualizarPassword extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {

            if (request.getParameter("idusuario") == null || request.getParameter("idusuario").isEmpty()){ 
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'id usuario' no encontrado para actualizar password");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            long idusuario = Long.parseLong(request.getParameter("idusuario"));
            
            UsuarioDao usuarioDao = new UsuarioDao();
            conex = usuarioDao.conectar();
            
            //Verificar que el usuario existe para modificar
            CenUsuario cenusuario = usuarioDao.ConsultarUsuarioById(conex, idusuario);
            if (cenusuario == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Usuario no se encuentra registrado para actualizar el password");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            //Validar parametro password
            if (request.getParameter("txtpassword") == null || request.getParameter("txtpassword").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'password' no encontrado para actualizar password");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro repetir password
            if (request.getParameter("txtrepetirpassword") == null || request.getParameter("txtrepetirpassword").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'repetir password' no encontrado para actualizar password");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String password = DigestUtils.md5Hex(request.getParameter("txtpassword"));
            String repetirpassword = DigestUtils.md5Hex(request.getParameter("txtrepetirpassword"));

            //Verificar que los password coincidan
            if (!password.equals(repetirpassword)) {
                respuesta.put("status", "error");
                respuesta.put("message", "Los password no coinciden");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            conex.setAutoCommit(false);

            boolean modificado = usuarioDao.restaurarPasswordUsuario(conex, idusuario, password);

            if (modificado) {
                conex.commit();
                respuesta.put("status", "success");
                respuesta.put("message", "Password actualizado exitosamente");
                respuesta.put("id", String.valueOf(idusuario));
            } else {
                conex.rollback();
                respuesta.put("status", "fail");
                respuesta.put("message", "Password no actualizado");
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
            respuesta.put("message", "Error al actualizar el password");
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
