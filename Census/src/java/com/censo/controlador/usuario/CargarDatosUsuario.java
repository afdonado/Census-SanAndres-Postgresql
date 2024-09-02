package com.censo.controlador.usuario;

import com.censo.modelo.dao.UsuarioDao;
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

@WebServlet(name = "CargarDatosUsuario", urlPatterns = {"/cargarDatosUsuario"})
public class CargarDatosUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, Object> respuesta = new HashMap<>();

        try {

            if (!request.getParameter("id").equals("")) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'id' no encontrado para cargar datos del usuario");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            long idusuario = Long.parseLong(request.getParameter("id"));

            UsuarioDao usuarioDao = new UsuarioDao();
            conex = usuarioDao.conectar();

            HashMap<String, Object> datos = usuarioDao.ConsultarDatosUsuarioById(conex, idusuario);

            if (!datos.isEmpty()) {
                respuesta.put("status", "success");
                respuesta.put("censo", datos);
            } else {
                respuesta.put("respuesta", "fail");
                respuesta.put("message", "Usuario no se encuentra registrado");
            }

        } catch (SQLException e) {
            respuesta.put("status", "error");
            respuesta.put("message", "Error al cargar los datos del usuario");
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
