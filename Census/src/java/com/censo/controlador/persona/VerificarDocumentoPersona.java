package com.censo.controlador.persona;

import com.censo.modelo.dao.PersonaDao;
import com.censo.modelo.persistencia.CenPersona;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "VerificarDocumentoPersona", urlPatterns = {"/verificarDocumentoPersona"})
public class VerificarDocumentoPersona extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            if (!request.getParameter("tipodocumento").equals("") && !request.getParameter("documento").equals("")) {

                PersonaDao personaDao = new PersonaDao();
                conex = personaDao.conectar();

                int tipodoc = Integer.parseInt(request.getParameter("tipodocumento"));
                String documento = request.getParameter("documento");

                CenPersona cenpersona = personaDao.ConsultarPersona(conex, tipodoc, documento);
                if (cenpersona != null) {
                    String nombreCompleto = cenpersona.getNombre1() + " " + (cenpersona.getNombre2() != null ? cenpersona.getNombre2().trim() : "")
                            + " " + (cenpersona.getApellido1() != null ? cenpersona.getApellido1().trim() : "")
                            + " " + (cenpersona.getApellido2() != null ? cenpersona.getApellido2().trim() : "");
                    long idpersona = cenpersona.getId();

                    
                    out.println("si," + nombreCompleto + ","+idpersona);
                } else {
                    out.println("no");
                }
            } else {
                out.println("no");
            }
        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al verificar el numero de documento');");
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
