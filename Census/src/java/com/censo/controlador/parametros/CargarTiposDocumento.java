package com.censo.controlador.parametros;

import com.censo.modelo.dao.TipoDocumentoDao;
import com.censo.modelo.persistencia.CenTipoDocumento;
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

@WebServlet(name = "CargarTiposDocumento", urlPatterns = {"/cargarTiposDocumento"})
public class CargarTiposDocumento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            if (!request.getParameter("nameCombo").equals("")) {

                TipoDocumentoDao tipoDocumentoDao = new TipoDocumentoDao();
                conex = tipoDocumentoDao.conectar();
                
                String nameCombo = request.getParameter("nameCombo");

                out.println("<select class=\"form-control\" name=\""+nameCombo+"\" id=\""+nameCombo+"\">");
                
                List<CenTipoDocumento> lista = tipoDocumentoDao.ListarTiposDocumento(conex);
                
                for (CenTipoDocumento cenTipoDocumento : lista) {
                    if (cenTipoDocumento.getId() == 1) {
                        out.println("<option value=\"" + cenTipoDocumento.getId() + "\"selected>" + cenTipoDocumento.getDescripcion() + "</option>");
                    } else {
                        out.println("<option value=\"" + cenTipoDocumento.getId() + "\">" + cenTipoDocumento.getDescripcion() + "</option>");
                    }
                }
                
                out.println("</select>");
            }
            
        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al cargar los tipos de documento');");
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
