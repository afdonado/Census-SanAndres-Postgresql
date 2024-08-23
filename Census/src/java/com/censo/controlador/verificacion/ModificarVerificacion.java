package com.censo.controlador.verificacion;

import com.censo.modelo.dao.VerificacionDao;
import com.censo.modelo.persistencia.CenHistorialVerificacion;
import com.censo.modelo.persistencia.CenUsuario;
import com.censo.modelo.persistencia.CenVerificacion;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModificarVerificacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            
            VerificacionDao dao = new VerificacionDao();
            CenUsuario cenusuarioSesion = (CenUsuario) request.getSession().getAttribute("usuario");

            CenVerificacion cenverificacion = new CenVerificacion();
            CenHistorialVerificacion cenhistoriaverificacion = new CenHistorialVerificacion();
            
            dao.conectar().setAutoCommit(false);
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
            
            cenverificacion = dao.ConsultarVerificacionByIdCenso(idcenso);
            
            cenverificacion.setVerificado_runt(runt);
            cenverificacion.setVerificado_doc(documentos);
            cenverificacion.setVerificado_foto(fotos);
            cenverificacion.setObservaciones(observaciones);
            cenverificacion.setEstado(estado);
            cenverificacion.setUsu_id(cenusuarioSesion.getId());
            dao.modificarVerificacion(cenverificacion);
            
            cenhistoriaverificacion.setVer_id(cenverificacion.getId());
            cenhistoriaverificacion.setEstado(estado);
            cenhistoriaverificacion.setUsu_id(cenusuarioSesion.getId());
            cenhistoriaverificacion.setObservaciones(observaciones);
            dao.adicionarHistorialVerificacion(cenhistoriaverificacion);
            
            dao.conectar().commit();
        
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Verificacion Modificada');");
                out.println("parent.$('#registrarverificacion').modal('hide');");
                out.println("</script>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
