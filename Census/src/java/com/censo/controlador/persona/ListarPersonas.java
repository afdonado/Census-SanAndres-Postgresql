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
import javax.sql.DataSource;

@WebServlet(name = "ListarPersonas", urlPatterns = {"/listarPersonas"})
public class ListarPersonas extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Map<String, Object> respuesta = new HashMap<>();

        try (Connection conex = dataSource.getConnection()) {

            PersonaDao personaDao = new PersonaDao();

            List<HashMap<String, Object>> lista = personaDao.ListarPersonas(conex);

            if (!lista.isEmpty()) {
                respuesta.put("status", "success");
                respuesta.put("personas", lista);
            }

        } catch (SQLException e) {
            respuesta.put("status", "error");
            respuesta.put("message", "Error al listas las personas");
            e.printStackTrace();

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
