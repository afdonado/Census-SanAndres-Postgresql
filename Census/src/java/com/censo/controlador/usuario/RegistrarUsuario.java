package com.censo.controlador.usuario;

import com.censo.modelo.dao.UsuarioDao;
import com.censo.modelo.persistencia.CenPerfilUsuario;
import com.censo.modelo.persistencia.CenUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;

public class RegistrarUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            UsuarioDao dao = new UsuarioDao();
            CenUsuario cenusuarioSesion = (CenUsuario) request.getSession().getAttribute("usuario");
            
            dao.conectar().setAutoCommit(false);

            CenUsuario cenusuario = new CenUsuario();
            CenPerfilUsuario cenperfilusuario = new CenPerfilUsuario();
            
            boolean registrado = false;

            if (request.getParameter("txtnomusuario") != null && request.getParameter("txtpass") != null) {

                String nomusu = request.getParameter("txtnomusuario").toUpperCase().trim();
                String pass = DigestUtils.md5Hex(request.getParameter("txtpass"));
                String repass = DigestUtils.md5Hex(request.getParameter("txtreppass"));
                int tipoperfil = Integer.parseInt(request.getParameter("cmbperfiles"));

                if (pass.equals(repass)) {
                    cenusuario.setNombre(nomusu);
                    cenusuario.setPass(pass);
                    cenusuario.setEstado(1);

                    long idUsuario = dao.adicionarUsuario(cenusuario);
                    if (idUsuario > 0) {
                        cenperfilusuario.setPef_id(tipoperfil);
                        cenperfilusuario.setUsu_id(idUsuario);
                        cenperfilusuario.setEstado(1);
                        long idperfusu = dao.adicionarPerfilUsuario(cenperfilusuario);
                        if (idperfusu > 0) {
                            registrado = true;
                        } else {
                            registrado = false;
                        }
                    } else {
                        registrado = false;
                    }

                    if (registrado) {
                        dao.conectar().commit();
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Usuario Registrado');");
                        out.println("location='jsp/AdminUsuarios/registrarUsuario.jsp';");
                        out.println("</script>");
                    } else {
                        dao.conectar().rollback();
                        registrado = false;
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Usuario no Registrado');");
                        out.println("location='jsp/AdminUsuarios/registrarUsuario.jsp';");
                        out.println("</script>");
                    }

                } else {
                    dao.conectar().rollback();
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Los password no coinciden');");
                    out.println("</script>");
                }
            } else {
                dao.conectar().rollback();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Debe ingresar como minimo los datos obligatorios (*)');");
                out.println("</script>");
            }

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
