package com.censo.controlador.vehiculo;

import com.censo.modelo.dao.PersonaVehiculoDao;
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

@WebServlet(name = "CargarDatosVehiculo", urlPatterns = {"/cargarDatosVehiculo"})
public class CargarDatosVehiculo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            if (!request.getParameter("id").equals("")) {

                VehiculoDao vehiculoDao = new VehiculoDao();
                conex = vehiculoDao.conectar();

                long idvehiculo = Long.parseLong(request.getParameter("id"));

                HashMap<String, String> datosVehiculo = vehiculoDao.ListarVehiculosById(conex, idvehiculo);

                if (!datosVehiculo.isEmpty()) {
                    request.setAttribute("datosVehiculo", datosVehiculo);

                    PersonaVehiculoDao personaVehiculoDao = new PersonaVehiculoDao();
                    List<HashMap> personasVehiculo = personaVehiculoDao.ListarHashPersonasVehiculoActivasByIdVehiculo(conex, idvehiculo);

                    if (!personasVehiculo.isEmpty()) {
                        request.setAttribute("personasVehiculo", personasVehiculo);                        
                    }
                    request.getRequestDispatcher("jsp/Vehiculos/verVehiculo.jsp").forward(request, response);
                }
            }

        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al listar los vehiculos');");
            out.println("location='dashboard';");
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
