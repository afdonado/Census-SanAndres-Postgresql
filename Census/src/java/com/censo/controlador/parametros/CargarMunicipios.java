package com.censo.controlador.parametros;

import com.censo.modelo.dao.MunicipioDao;
import com.censo.modelo.persistencia.CenMunicipio;
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

@WebServlet(name = "CargarMunicipios", urlPatterns = {"/cargarMunicipios"})
public class CargarMunicipios extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            MunicipioDao municipioDao = new MunicipioDao();
            conex = municipioDao.conectar();

            if (!request.getParameter("iddepartamento").equals("") && !request.getParameter("nameCombo").equals("")) {
                
                out.println("<label>Municipio</label>");
                out.println("<select class=\"form-control\" name=\""+request.getParameter("nameCombo")+"\" id=\""+request.getParameter("nameCombo")+"\">");
                
                List<CenMunicipio> lista = municipioDao.ListarMunicipiosByIdDepto(conex, Integer.parseInt(request.getParameter("iddepartamento")));
                
                for (CenMunicipio cenmunicipio: lista) {
                    out.println("<option value=\""+cenmunicipio.getId()+"\">"+cenmunicipio.getNombre()+"</option>");
                }
                
                out.println("</select>");
                
            }
        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al cargar los municipios');");
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
