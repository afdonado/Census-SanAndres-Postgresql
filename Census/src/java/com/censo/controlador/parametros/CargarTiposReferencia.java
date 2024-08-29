package com.censo.controlador.parametros;

import com.censo.modelo.dao.TipoReferenciaDao;
import com.censo.modelo.persistencia.CenTipoReferencia;
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

@WebServlet(name = "CargarTiposReferencia", urlPatterns = {"/cargarTiposReferencia"})
public class CargarTiposReferencia extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            TipoReferenciaDao tipoReferenciaDao = new TipoReferenciaDao();
            conex = tipoReferenciaDao.conectar();
            
            out.println("<label>Tipo Referencia</label>");
            out.println("<select class=\"form-control\" name=\"cmdtiposreferencia\" id=\"cmdtiposreferencia\">");
            List<CenTipoReferencia> lista = tipoReferenciaDao.ListarTiposReferencia(conex);
            for (CenTipoReferencia cenTipoReferencia : lista) {
                if (cenTipoReferencia.getId() == 1) {
                out.println("<option value=\"" + cenTipoReferencia.getId() + "\"selected>" + cenTipoReferencia.getDescripcion() + "</option>");
                } else {
                out.println("<option value=\"" + cenTipoReferencia.getId() + "\">" + cenTipoReferencia.getDescripcion() + "</option>");    
                }
            }
            out.println("</select>");
        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al cargar los tipos de referencia');");
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
