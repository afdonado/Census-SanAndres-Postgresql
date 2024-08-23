
package com.censo.controlador.sesion;

import com.censo.modelo.dao.UsuarioDao;
import com.censo.modelo.persistencia.CenUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;

public class InicioSesion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {

            if (request.getParameter("txtloginusuario") != null && request.getParameter("txtloginpassword") != null) {
               
                UsuarioDao modelo = new UsuarioDao();
                String user = request.getParameter("txtloginusuario").toUpperCase();
                String pass = DigestUtils.md5Hex(request.getParameter("txtloginpassword"));
                
                CenUsuario cenusuario = modelo.ConsultarUsuario(user, pass, 1);
                if (cenusuario != null) {
                    
                    HttpSession sessionCensus = request.getSession();
                    sessionCensus.setAttribute("usuario", cenusuario);
                    sessionCensus.setAttribute("permisosUsuario", modelo.ListarPermisosById(cenusuario.getId()));
                    sessionCensus.setAttribute("modulosUsuario", modelo.ListarModulosByUsuario(cenusuario.getId()));
                    sessionCensus.setAttribute("modulos", modelo.ListarModulos());
                    response.sendRedirect("jsp/Inicio.jsp");
                    
                } else {
                    //response.sendRedirect("index.jsp");
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Datos incorrectos');");
                    out.println("location='index.jsp';");
                    out.println("</script>");
                }

            } else {
               out.println("<script type=\"text/javascript\">");
                out.println("alert('Digite los datos de usuario y contraseña');");
                out.println("location='index.jsp';");
                out.println("</script>");
            }
        } catch (Exception ex) {
            System.out.println("Erro en el Inicio de Sesion" + ex);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
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
