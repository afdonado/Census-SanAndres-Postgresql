package com.censo.controlador.usuario;

import com.censo.modelo.dao.UsuarioDao;
import com.censo.modelo.persistencia.CenPerfilUsuario;
import com.censo.modelo.persistencia.CenUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ModificarUsuario", urlPatterns = "/modificarUsuario")
public class ModificarUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Connection conex = null;
        
        try {
            
            UsuarioDao usuarioDao = new UsuarioDao();
            conex = usuarioDao.conectar();            
            conex.setAutoCommit(false);

            Date fechaActual = new Date(new java.util.Date().getTime());

            long idusuario = Long.parseLong(request.getParameter("idusuario"));
            int estado = Integer.parseInt(request.getParameter("cmbestado"));

            CenUsuario cenusuario = usuarioDao.ConsultarUsuarioById(conex, idusuario);

            cenusuario.setEstado(estado);
            if (estado != 1) {
                cenusuario.setFechafin(fechaActual);
            } else {
                cenusuario.setFechafin(null);
            }
            usuarioDao.modificarUsuario(conex, cenusuario);

            int tipoperfil = Integer.parseInt(request.getParameter("cmbperfiles"));

            CenPerfilUsuario cenperfilusuario = usuarioDao.ConsultarPerfilUsuarioByIdUsuario(conex, idusuario);

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

                usuarioDao.modificarPerfilUsuario(conex, cenperfilusuario);
            } else {
                cenperfilusuario = new CenPerfilUsuario();
                cenperfilusuario.setPef_id(tipoperfil);
                cenperfilusuario.setUsu_id(idusuario);
                cenperfilusuario.setEstado(1);
                long idperfusu = usuarioDao.adicionarPerfilUsuario(conex, cenperfilusuario);
            }

            conex.commit();

            out.println("<script type=\"text/javascript\">");
            out.println("alert('Usuario Modificado');");
            out.println("location='jsp/Usuarios/verUsuario.jsp?idusuario="+idusuario+"';");
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
            out.println("alert('Error al modificar el usuario');");
            out.println("location='jsp/Usuarios/modificarUsuario.jsp';");
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
