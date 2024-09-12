package com.censo.controlador.parametros;

import com.censo.modelo.dao.MarcaDao;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "CargarMarcas", urlPatterns = "/cargarMarcas")
public class CargarMarcas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (Connection conex = dataSource.getConnection()) {

            MarcaDao marcaDao = new MarcaDao();

            String find = request.getParameter("marcas").toUpperCase().trim();
            
            ArrayList<String> marcas = marcaDao.ListarNombresMarcas(conex, find);
            
            String json = new Gson().toJson(marcas);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            
        } catch (IOException | SQLException e) {
            System.out.println(e);
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
