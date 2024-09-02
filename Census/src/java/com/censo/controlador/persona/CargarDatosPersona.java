package com.censo.controlador.persona;

import com.censo.modelo.dao.PersonaDao;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CargarDatosPersona", urlPatterns = {"/cargarDatosPersona"})
public class CargarDatosPersona extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, Object> respuesta = new HashMap<>();

        try {

            if (request.getParameter("id").equals("")) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'id' no encontrado para cargar datos de la persona");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            long idpersona = Long.parseLong(request.getParameter("id"));

            PersonaDao personaDao = new PersonaDao();
            conex = personaDao.conectar();

            HashMap<String, Object> datos = personaDao.ConsultarDatosPersonaById(conex, idpersona);

            if (!datos.isEmpty()) {
                respuesta.put("status", "success");
                respuesta.put("persona", datos);
            } else {
                respuesta.put("respuesta", "fail");
                respuesta.put("message", "Persona no se encuentra registrada");
            }

        } catch (SQLException e) {
            respuesta.put("status", "error");
            respuesta.put("message", "Error al cargar los datos de la persona");
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
