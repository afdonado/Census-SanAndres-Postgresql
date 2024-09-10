package com.censo.controlador.vehiculo;

import com.censo.modelo.dao.TipoDocumentoDao;
import com.censo.modelo.dao.TipoPersonaDao;
import com.censo.modelo.persistencia.CenTipoDocumento;
import com.censo.modelo.persistencia.CenTipoPersona;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AgregarCamposPersona", urlPatterns = {"/agregarCamposPersona"})
public class AgregarCamposPersona extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        
        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {

            if ((request.getParameter("nameComboTP") == null || request.getParameter("nameComboTP").isEmpty())
                    && (request.getParameter("nameComboTD") == null || request.getParameter("nameComboTD").isEmpty())
                    && (request.getParameter("nameTxtDocumento") == null || request.getParameter("nameTxtDocumento").isEmpty())
                    && (request.getParameter("nameTxtNombre") == null || request.getParameter("nameTxtNombre").isEmpty())
                    && (request.getParameter("nameTxtId") == null || request.getParameter("nameTxtId").isEmpty())) {

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                respuesta.put("status", "error");
                respuesta.put("message", "Parametros no encontrados");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            response.setContentType("text/html;charset=UTF-8");

            String nameComboTP = request.getParameter("nameComboTP");
            String nameComboTD = request.getParameter("nameComboTD");
            String nameTxtDocumento = request.getParameter("nameTxtDocumento");
            String nameTxtNombre = request.getParameter("nameTxtNombre");
            String nameTxtId = request.getParameter("nameTxtId");
            
            TipoPersonaDao tipoPersonaDao = new TipoPersonaDao();
            TipoDocumentoDao tipoDocumentoDao = new TipoDocumentoDao();
            conex = tipoPersonaDao.conectar();

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
            out.println("<input class=\"form-control solo-numeros\" type=\"number\" id=\"" + nameTxtDocumento + "\" name=\"" + nameTxtDocumento + "\" maxlength=\"20\" required=\"true\" style=\"text-transform: uppercase\">");
            out.println("</div>");
            out.println("<div class=\"col-sm-4 mb-3 mb-sm-0\">");
            out.println("<input class=\"form-control\" type=\"text\" id=\"" + nameTxtNombre + "\" name=\"" + nameTxtNombre + "\" readonly=\"true\">");
            out.println("</div>");
            out.println("<input type=\"hidden\" id=\"" + nameTxtId + "\" name=\"" + nameTxtId + "\">");

        } catch (SQLException e) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            respuesta.put("status", "Error al agregar campos persona asociada al vehiculo");
            String jsonError = new Gson().toJson(respuesta);
            response.getWriter().write(jsonError);
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
