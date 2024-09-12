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
import javax.sql.DataSource;
import org.apache.commons.codec.digest.DigestUtils;

@WebServlet(name = "VerificarPasswordActual", urlPatterns = {"/verificarPasswordActual"})
public class VerificarPasswordActual extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> respuesta = new HashMap<>();

        try (Connection conex = dataSource.getConnection()) {

            if (request.getParameter("idusuario") == null || request.getParameter("idusuario").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'id usuario' no encontrado para verificar password actual");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            int idusuario = Integer.parseInt(request.getParameter("idusuario"));

            UsuarioDao usuarioDao = new UsuarioDao();

            //Verificar que el usuario existe
            CenUsuario cenusuario = usuarioDao.ConsultarUsuarioById(conex, idusuario);
            if (cenusuario == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Usuario no se encuentra registrado para verificar el password actual");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro password actual
            if (request.getParameter("txtpasswordactual") == null || request.getParameter("txtpasswordactual").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'password' no encontrado para verificar password actual");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String passwordactual = DigestUtils.md5Hex(request.getParameter("passwordactual"));

            if (passwordactual.equals(cenusuario.getPassword())) {
                respuesta.put("status", "success");
                respuesta.put("message", "Password actual valido");
            } else {
                respuesta.put("status", "fail");
                respuesta.put("message", "Password actual no valido");
            }
        } catch (SQLException e) {
            respuesta.put("status", "error");
            respuesta.put("message", "Error al verificar password actual");
            e.printStackTrace();
        }
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
