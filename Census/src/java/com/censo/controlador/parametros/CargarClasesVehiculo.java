package com.censo.controlador.parametros;

import com.censo.modelo.dao.ClaseVehiculoDao;
import com.censo.modelo.persistencia.CenClaseVehiculo;
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

@WebServlet(name = "CargarClasesVehiculo", urlPatterns = {"/cargarClasesVehiculo"})
public class CargarClasesVehiculo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            ClaseVehiculoDao claseVehiculoDao = new ClaseVehiculoDao();
            conex = claseVehiculoDao.conectar();
            
            out.println("<label>Clase(*)</label>");
            out.println("<select class=\"form-control\" name=\"cmbclasevehiculo\" id=\"cmbclasevehiculo\">");
            List<CenClaseVehiculo> lista = claseVehiculoDao.ListarClasesVehiculo(conex);
            for (CenClaseVehiculo cenClaseVehiculo : lista) {
                if (cenClaseVehiculo.getId() == 1) {
                out.println("<option value=\"" + cenClaseVehiculo.getId() + "\"selected>" + cenClaseVehiculo.getDescripcion() + "</option>");
                } else {
                out.println("<option value=\"" + cenClaseVehiculo.getId() + "\">" + cenClaseVehiculo.getDescripcion() + "</option>");    
                }
            }
            out.println("</select>");
        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al cargar las clases de vehiculo');");
            out.println("location='jsp/Inicio.jsp';");
            out.println("</script>");
            e.printStackTrace();

        } finally {
            if (conex != null) {
                try {
                    conex.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
            out.close();
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
