package com.censo.controlador.vehiculo;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.dao.VehiculoDao;
import com.censo.modelo.persistencia.CenCenso;
import com.censo.modelo.persistencia.CenVehiculo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "VerificarVehiculo1", urlPatterns = {"/verificarVehiculo1"})
public class VerificarVehiculo1 extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            if (!request.getParameter("tiporeferencia").equals("") && !request.getParameter("valorreferencia").equals("")) {

                VehiculoDao vehiculoDao = new VehiculoDao();
                conex = vehiculoDao.conectar();

                int tipoRef = Integer.parseInt(request.getParameter("tiporeferencia"));
                String valorReferencia = request.getParameter("valorreferencia");
                int opcion = Integer.parseInt(request.getParameter("opcion"));

                CenVehiculo cenvehiculo = vehiculoDao.ConsultarVehiculoByReferencia(conex, tipoRef, valorReferencia);

                if (cenvehiculo != null) {

                    if (opcion == 2) {

                        CensoDao censoDao = new CensoDao();
                        CenCenso cencenso = censoDao.ConsultarCensoByIdVehiculo(conex, cenvehiculo.getId());

                        if (cencenso == null) {
                            out.println("si" + cenvehiculo.getId());
                        } else {
                            out.println("sicensado");
                        }
                    }
                    out.println("si");
                } else {
                    out.println("noexiste");
                }
            } else {
                out.println("no");
            }
        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al verificar el numero de censo');");
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
