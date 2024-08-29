package com.censo.controlador.parametros;

import com.censo.modelo.dao.PaisDao;
import com.censo.modelo.persistencia.CenPais;
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

@WebServlet(name = "CargarPaises", urlPatterns = {"/cargarPaises"})
public class CargarPaises extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            if (!request.getParameter("nameCombo").equals("") && !request.getParameter("label").equals("")) {

                PaisDao paisDao = new PaisDao();
                conex = paisDao.conectar();
                
                String nameCombo = request.getParameter("nameCombo");
                String label = request.getParameter("label");

                out.println("<label>"+label+"</label>");
                out.println("<select class=\"form-control\" name=\""+nameCombo+"\" id=\""+nameCombo+"\">");
                
                List<CenPais> lista = paisDao.ListarPaises(conex);
                
                for (CenPais cenPais : lista) {
                    if (cenPais.getId() == 18) {
                        out.println("<option value=\"" + cenPais.getId() + "\"selected>" + cenPais.getNombre() + "</option>");
                    } else {
                        out.println("<option value=\"" + cenPais.getId() + "\">" + cenPais.getNombre() + "</option>");
                    }
                }
                
                out.println("</select>");
                
            }
        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al cargar los paises');");
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
