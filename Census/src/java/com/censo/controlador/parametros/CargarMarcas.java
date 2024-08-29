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

@WebServlet(name = "CargarMarcas", urlPatterns = "/cargarMarcas")
public class CargarMarcas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        Connection conex = null;

        try {

            MarcaDao marcaDao = new MarcaDao();
            conex = marcaDao.conectar();

            String find = request.getParameter("marcas").toUpperCase().trim();
            
            ArrayList<String> marcas = marcaDao.ListarNombresMarcas(conex, find);
            
            String json = new Gson().toJson(marcas);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            
        } catch (IOException | SQLException e) {
            System.out.println(e);
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
