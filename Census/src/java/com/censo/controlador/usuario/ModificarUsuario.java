package com.censo.controlador.usuario;

import com.censo.modelo.dao.UsuarioDao;
import com.censo.modelo.persistencia.CenPerfilUsuario;
import com.censo.modelo.persistencia.CenUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModificarUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        UsuarioDao dao = new UsuarioDao();
        
        try {
            dao.conectar().setAutoCommit(false);

            CenUsuario cenusuario = new CenUsuario();
            CenPerfilUsuario cenperfilusuario = new CenPerfilUsuario();

            Date fechaActual = new Date(new java.util.Date().getTime());

            long idusuario = Long.parseLong(request.getParameter("idusuario"));
            int estado = Integer.parseInt(request.getParameter("cmbestado"));

            cenusuario = dao.ConsultarUsuarioById(idusuario);

            cenusuario.setEstado(estado);
            if (estado != 1) {
                cenusuario.setFechafin(fechaActual);
            } else {
                cenusuario.setFechafin(null);
            }
            dao.modificarUsuario(cenusuario);

            int tipoperfil = Integer.parseInt(request.getParameter("cmbperfiles"));

            cenperfilusuario = dao.ConsultarPerfilUsuarioByIdUsuario(idusuario);

            if (cenperfilusuario != null) {
                if (cenperfilusuario.getPef_id() != tipoperfil) {
                    cenperfilusuario.setPef_id(tipoperfil);
                }

                if (estado != 1) {
                    cenperfilusuario.setFechafin(fechaActual);
                    cenperfilusuario.setEstado(2);
                } else {
                    cenperfilusuario.setFechafin(null);
                }

                dao.modificarPerfilUsuario(cenperfilusuario);
            } else {
                cenperfilusuario = new CenPerfilUsuario();
                cenperfilusuario.setPef_id(tipoperfil);
                cenperfilusuario.setUsu_id(idusuario);
                cenperfilusuario.setEstado(1);
                long idperfusu = dao.adicionarPerfilUsuario(cenperfilusuario);
            }

            dao.conectar().commit();

            out.println("<script type=\"text/javascript\">");
            out.println("alert('Usuario Modificado');");
            out.println("location='jsp/AdminUsuarios/verUsuario.jsp?idusuario="+idusuario+"';");
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
