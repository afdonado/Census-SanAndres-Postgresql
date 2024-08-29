package com.censo.controlador.sesion;

import com.censo.modelo.dao.UsuarioDao;
import com.censo.modelo.persistencia.CenUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;

@WebServlet(name = "InicioSesion", urlPatterns = "/inicioSesion")
public class InicioSesion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;
        
        try {
            
            if (request.getParameter("txtloginusuario") != null && request.getParameter("txtloginpassword") != null) {
                
                UsuarioDao usuarioDao = new UsuarioDao();
                conex = usuarioDao.conectar();     
                
                String user = request.getParameter("txtloginusuario").toUpperCase();
                String password = DigestUtils.md5Hex(request.getParameter("txtloginpassword"));
                
                CenUsuario cenusuario = usuarioDao.ConsultarUsuario(conex, user, password, 1);
                if (cenusuario != null) {
                    
                    HttpSession sessionCensus = request.getSession();
                    sessionCensus.setAttribute("usuario", cenusuario);
                    sessionCensus.setAttribute("perfil", usuarioDao.ConsultarPerfilById(conex, usuarioDao.ConsultarPerfilUsuarioByIdUsuario(conex, cenusuario.getId()).getPef_id()).getNombre());
                    sessionCensus.setAttribute("permisosUsuario", usuarioDao.ListarPermisosById(conex, cenusuario.getId()));
                    sessionCensus.setAttribute("modulosUsuario", usuarioDao.ListarModulosByUsuario(conex, cenusuario.getId()));
                    sessionCensus.setAttribute("modulos", usuarioDao.ListarModulos(conex));
                    response.sendRedirect("jsp/Inicio.jsp");
                    
                } else {
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
        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al actualizar el password');");
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
