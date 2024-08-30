package com.censo.controlador.vehiculo;

import com.censo.modelo.dao.TipoDocumentoDao;
import com.censo.modelo.dao.TipoPersonaDao;
import com.censo.modelo.persistencia.CenTipoDocumento;
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

@WebServlet(name = "AgregarCamposPersona", urlPatterns = {"/agregarCamposPersona"})
public class AgregarCamposPersona extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            if (!request.getParameter("nameComboTP").equals("")
                    && !request.getParameter("nameComboTD").equals("")
                    && !request.getParameter("nameTxtDocumento").equals("")
                    && !request.getParameter("nameTxtNombre").equals("")
                    && !request.getParameter("nameTxtId").equals("")) {

                TipoPersonaDao tipoPersonaDao = new TipoPersonaDao();
                TipoDocumentoDao tipoDocumentoDao = new TipoDocumentoDao();
                conex = tipoPersonaDao.conectar();

                String nameComboTP = request.getParameter("nameComboTP");
                String nameComboTD = request.getParameter("nameComboTD");
                String nameTxtDocumento = request.getParameter("nameTxtDocumento");
                String nameTxtNombre = request.getParameter("nameTxtNombre");
                String nameTxtId = request.getParameter("nameTxtId");

                out.println("<div class=\"col-sm-2 mb-3 mb-sm-0\">");
                out.println("<select class=\"form-control\" name=\"" + nameComboTP + "\" id=\"" + nameComboTP + "\">");

                List<CenTipoPersona> listaTiposPersona = tipoPersonaDao.ListarTiposPersona(conex);

                for (CenTipoPersona cenTipoPersona : listaTiposPersona) {
                    if (cenTipoPersona.getId() == 1) {
                        out.println("<option value=\"" + cenTipoPersona.getId() + "\"selected>" + cenTipoPersona.getDescripcion() + "</option>");
                    } else {
                        out.println("<option value=\"" + cenTipoPersona.getId() + "\">" + cenTipoPersona.getDescripcion() + "</option>");
                    }
                }

                out.println("</select></div>");

                out.println("<div class=\"col-sm-2 mb-3 mb-sm-0\">");
                out.println("<select class=\"form-control\" name=\"" + nameComboTD + "\" id=\"" + nameComboTD + "\">");

                List<CenTipoDocumento> listaTiposDocumento = tipoDocumentoDao.ListarTiposDocumento(conex);

                for (CenTipoDocumento cenTipoDocumento : listaTiposDocumento) {
                    if (cenTipoDocumento.getId() == 1) {
                        out.println("<option value=\"" + cenTipoDocumento.getId() + "\"selected>" + cenTipoDocumento.getDescripcion() + "</option>");
                    } else {
                        out.println("<option value=\"" + cenTipoDocumento.getId() + "\">" + cenTipoDocumento.getDescripcion() + "</option>");
                    }
                }

                out.println("</select></div>");
                out.println("<div class=\"col-sm-3 mb-3 mb-sm-0\" id=\"numeros-documeto\">");
                out.println("<input class=\"form-control\" type=\"number\" id=\"" + nameTxtDocumento + "\" name=\"" + nameTxtDocumento + "\" maxlength=\"20\" required=\"true\">");
                out.println("</div>");
                out.println("<div class=\"col-sm-5 mb-3 mb-sm-0\">");
                out.println("<input class=\"form-control\" type=\"text\" id=\""+nameTxtNombre+"\" name=\""+nameTxtNombre+"\" readonly=\"true\">");
                out.println("</div>");
                out.println("<input type=\"hidden\" id=\""+nameTxtId+"\" name=\""+nameTxtId+"\">");
                
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
