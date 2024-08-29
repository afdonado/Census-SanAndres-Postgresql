package com.censo.controlador.parametros;

import com.censo.modelo.dao.TipoUsoDao;
import com.censo.modelo.persistencia.CenTipoUso;
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

@WebServlet(name = "CargarTiposUso", urlPatterns = {"/cargarTiposUso"})
public class CargarTiposUso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            TipoUsoDao tipoUsoDao = new TipoUsoDao();
            conex = tipoUsoDao.conectar();
            
            out.println("<label>Tipo de Uso</label>");
            out.println("<select class=\"form-control\" name=\"cmbtiposuso\" id=\"cmbtiposuso\">");
            List<CenTipoUso> lista = tipoUsoDao.ListarTiposUso(conex);
            for (CenTipoUso cenTipoUso : lista) {
                if (cenTipoUso.getId() == 1) {
                out.println("<option value=\"" + cenTipoUso.getId() + "\"selected>" + cenTipoUso.getDescripcion() + "</option>");
                } else {
                out.println("<option value=\"" + cenTipoUso.getId() + "\">" + cenTipoUso.getDescripcion() + "</option>");    
                }
            }
            out.println("</select>");
        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al cargar los tipos de uso');");
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
