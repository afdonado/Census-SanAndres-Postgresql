package com.censo.controlador.verificacion;

import com.censo.modelo.dao.VerificacionDao;
import com.censo.modelo.persistencia.CenHistorialVerificacion;
import com.censo.modelo.persistencia.CenUsuario;
import com.censo.modelo.persistencia.CenVerificacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ModificarVerificacion", urlPatterns = "/modificarVerificacion")
public class ModificarVerificacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            VerificacionDao verificacionDao = new VerificacionDao();
            conex = verificacionDao.conectar();

            CenUsuario cenusuarioSesion = (CenUsuario) request.getSession().getAttribute("usuario");

            CenHistorialVerificacion cenhistoriaverificacion = new CenHistorialVerificacion();

            conex.setAutoCommit(false);
            String runt = "N";
            String documentos = "N";
            String fotos = "N";

            if (request.getParameter("chkrunt") != null) {
                runt = "S";
            }

            if (request.getParameter("chkdocumentos") != null) {
                documentos = "S";
            }

            if (request.getParameter("chkfotos") != null) {
                fotos = "S";
            }

            int estado = Integer.parseInt(request.getParameter("cmbestverificacion"));
            long idcenso = Long.parseLong(request.getParameter("idcenso"));
            String observaciones = request.getParameter("txtobservaciones").toUpperCase().trim();

            CenVerificacion cenverificacion = verificacionDao.ConsultarVerificacionByIdCenso(conex, idcenso);

            cenverificacion.setVerificado_runt(runt);
            cenverificacion.setVerificado_doc(documentos);
            cenverificacion.setVerificado_foto(fotos);
            cenverificacion.setObservaciones(observaciones);
            cenverificacion.setEstado(estado);
            cenverificacion.setUsu_id(cenusuarioSesion.getId());
            verificacionDao.modificarVerificacion(conex, cenverificacion);

            cenhistoriaverificacion.setVer_id(cenverificacion.getId());
            cenhistoriaverificacion.setEstado(estado);
            cenhistoriaverificacion.setUsu_id(cenusuarioSesion.getId());
            cenhistoriaverificacion.setObservaciones(observaciones);
            verificacionDao.adicionarHistorialVerificacion(conex, cenhistoriaverificacion);

            conex.commit();

            out.println("<script type=\"text/javascript\">");
            out.println("alert('Verificacion Modificada');");
            out.println("parent.$('#registrarverificacion').modal('hide');");
            out.println("</script>");
            
        } catch (IOException | NumberFormatException | SQLException e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al modificar la verificacion');");
            out.println("location='jsp/Verificaciones/verRegistro.jsp';");
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
