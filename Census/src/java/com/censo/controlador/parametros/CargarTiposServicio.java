package com.censo.controlador.parametros;

import com.censo.modelo.dao.TipoServicioDao;
import com.censo.modelo.persistencia.CenTipoServicio;
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

@WebServlet(name = "CargarTiposServicio", urlPatterns = {"/cargarTiposServicio"})
public class CargarTiposServicio extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            TipoServicioDao tipoServicioDao = new TipoServicioDao();
            conex = tipoServicioDao.conectar();
            
            out.println("<label>Servicio(*)</label>");
            out.println("<select class=\"form-control\" name=\"cmbtiposservicio\" id=\"cmbtiposservicio\">");
            List<CenTipoServicio> lista = tipoServicioDao.ListarTiposServicio(conex);
            for (CenTipoServicio cenTipoServicio : lista) {
                if (cenTipoServicio.getId() == 1) {
                out.println("<option value=\"" + cenTipoServicio.getId() + "\"selected>" + cenTipoServicio.getDescripcion() + "</option>");
                } else {
                out.println("<option value=\"" + cenTipoServicio.getId() + "\">" + cenTipoServicio.getDescripcion() + "</option>");    
                }
            }
            out.println("</select>");
        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al cargar los tipos de servicio');");
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
