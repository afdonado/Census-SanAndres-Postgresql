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

@WebServlet(name = "VerificarNombreUsuario", urlPatterns = {"/verificarNombreUsuario"})
public class VerificarNombreUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {

            UsuarioDao usuarioDao = new UsuarioDao();
            conex = usuarioDao.conectar();

            //Validar parametro nombre
            if (request.getParameter("txtnombre") == null || request.getParameter("txtnombre").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'nombre' no encontrado para registrar usuario");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String nombre = request.getParameter("txtnombre").toUpperCase().trim();

            //Verificar que el usuario no existe para registrarlo
            CenUsuario cenusuario = usuarioDao.ConsultarUsuarioByNombre(conex, nombre);
            if (cenusuario != null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Nombre de usuario no valido para registrarlo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            if (cenusuario == null) {
                conex.commit();
                respuesta.put("status", "success");
                respuesta.put("message", "Nombre usuario valido");
            } else {
                conex.rollback();
                respuesta.put("status", "fail");
                respuesta.put("message", "Nombre usuario no valido");
            }

        } catch (SQLException e) {
            respuesta.put("status", "error");
            respuesta.put("message", "Error al verificar el nombre de usuario");
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
