package com.censo.controlador.parametros;

import com.censo.modelo.dao.TipoImportacionDao;
import com.censo.modelo.persistencia.CenTipoImportacion;
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

@WebServlet(name = "CargarTiposImportacion", urlPatterns = {"/cargarTiposImportacion"})
public class CargarTiposImportacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            TipoImportacionDao tipoImportacionDao = new TipoImportacionDao();
            conex = tipoImportacionDao.conectar();
            
            out.println("<label>Tipo Importacion</label>");
            out.println("<select class=\"form-control\" name=\"cmdtiposimportacion\" id=\"cmdtiposimportacion\">");
            List<CenTipoImportacion> lista = tipoImportacionDao.ListarTiposImportacion(conex);
            for (CenTipoImportacion cenTipoImportacion : lista) {
                if (cenTipoImportacion.getId() == 1) {
                out.println("<option value=\"" + cenTipoImportacion.getId() + "\"selected>" + cenTipoImportacion.getDescripcion() + "</option>");
                } else {
                out.println("<option value=\"" + cenTipoImportacion.getId() + "\">" + cenTipoImportacion.getDescripcion() + "</option>");    
                }
            }
            out.println("</select>");
        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al cargar los tipos de importacion');");
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
