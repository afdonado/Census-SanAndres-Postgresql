package com.censo.controlador.vehiculo;

import com.censo.modelo.dao.VehiculoDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ListarVehiculos", urlPatterns = {"/listarVehiculos"})
public class ListarVehiculos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            VehiculoDao vehiculoDao = new VehiculoDao();
            conex = vehiculoDao.conectar();

            List<HashMap> datosVehiculo = vehiculoDao.ListarVehiculos(conex);

            if (!datosVehiculo.isEmpty()) {
                for (HashMap hash : datosVehiculo) {

                    String id = hash.get("VEH_ID") == null ? "" : hash.get("VEH_ID").toString();
                    String placa = hash.get("VEH_PLACA") == null ? "" : hash.get("VEH_PLACA").toString();
                    String motor = hash.get("VEH_MOTOR") == null ? "" : hash.get("VEH_MOTOR").toString();
                    String chasis = hash.get("VEH_CHASIS") == null ? "" : hash.get("VEH_CHASIS").toString();
                    String serie = hash.get("VEH_SERIE") == null ? "" : hash.get("VEH_SERIE").toString();
                    String marca = hash.get("MARCA") == null ? "" : hash.get("MARCA").toString();
                    String linea = hash.get("LINEA") == null ? "" : hash.get("LINEA").toString();
                    
                    out.println("<tr>");
                    out.println("<td>" + placa + "</td>");
                    out.println("<td>" + motor + "</td>");
                    out.println("<td>" + chasis + "</td>");
                    out.println("<td>" + serie + "</td>");
                    out.println("<td>" + marca + "</td>");
                    out.println("<td>" + linea + "</td>");
                    out.println("<td><button type=\"button\" class=\"btn btn-info btnconsultar\" name=\"btnconsultar\" data-id=\""+id+"\" title=\"Ver datos del vehiculo\"><span class=\"glyphicon glyphicon-search\">Consultar</span></button></td>");
                    out.println("<td><button type=\"button\" class=\"btn btn-danger btneditar\" name=\"btneditar\" data-id=\""+id+"\" title=\"Editar datos del vehiculo\"><span class=\"glyphicon glyphicon-edit\">Editar</span></button></td>");
                    out.println("</tr>");
                }
            }

        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al listar los vehiculos');");
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
