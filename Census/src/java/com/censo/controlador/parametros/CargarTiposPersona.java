package com.censo.controlador.parametros;

import com.censo.modelo.dao.TipoPersonaDao;
import com.censo.modelo.persistencia.CenTipoPersona;
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

@WebServlet(name = "CargarTiposPersona", urlPatterns = {"/cargarTiposPersona"})
public class CargarTiposPersona extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            if (!request.getParameter("nameCombo").equals("")) {

                TipoPersonaDao tipoPersonaDao = new TipoPersonaDao();
                conex = tipoPersonaDao.conectar();
                
                String nameCombo = request.getParameter("nameCombo");

                out.println("<select class=\"form-control\" name=\""+nameCombo+"\" id=\""+nameCombo+"\">");
                
                List<CenTipoPersona> lista = tipoPersonaDao.ListarTiposPersona(conex);
                
                for (CenTipoPersona cenTipoPersona : lista) {
                    if (cenTipoPersona.getId() == 1) {
                        out.println("<option value=\"" + cenTipoPersona.getId() + "\"selected>" + cenTipoPersona.getDescripcion() + "</option>");
                    } else {
                        out.println("<option value=\"" + cenTipoPersona.getId() + "\">" + cenTipoPersona.getDescripcion() + "</option>");
                    }
                }
                
                out.println("</select>");
            }
            
        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al cargar los tipos de persona');");
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
