package com.censo.controlador.parametros;

import com.censo.modelo.dao.ClaseVehiculoDao;
import com.censo.modelo.persistencia.CenClaseVehiculo;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "CargarClasesVehiculo", urlPatterns = {"/cargarClasesVehiculo"})
public class CargarClasesVehiculo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try (Connection conex = dataSource.getConnection()) {

            ClaseVehiculoDao claseVehiculoDao = new ClaseVehiculoDao();
            
            List<CenClaseVehiculo> lista = claseVehiculoDao.ListarClasesVehiculo(conex);
            
            if (!lista.isEmpty()) {
                    Gson gson = new Gson();
                    String json = gson.toJson(lista);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
            }

        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al cargar las clases de vehiculo');");
            out.println("location='jsp/Inicio.jsp';");
            out.println("</script>");
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
