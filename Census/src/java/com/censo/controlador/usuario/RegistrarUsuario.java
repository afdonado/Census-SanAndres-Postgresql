package com.censo.controlador.usuario;

import com.censo.modelo.dao.UsuarioDao;
import com.censo.modelo.persistencia.CenPerfilUsuario;
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
import org.apache.commons.codec.digest.DigestUtils;

@WebServlet(name = "RegistrarUsuario", urlPatterns = "/registrarUsuario")
public class RegistrarUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            if (request.getParameter("nombreusuario") != null && request.getParameter("password") != null) {

                String nomusu = request.getParameter("nombreusuario").toUpperCase().trim();
                String password = DigestUtils.md5Hex(request.getParameter("password"));
                String repetirpassword = DigestUtils.md5Hex(request.getParameter("repetirpassword"));
                int tipoperfil = Integer.parseInt(request.getParameter("cmbperfiles"));

                if (password.equals(repetirpassword)) {

                    UsuarioDao usuarioDao = new UsuarioDao();
                    conex = usuarioDao.conectar();
                    conex.setAutoCommit(false);

                    CenPerfilUsuario cenperfilusuario = new CenPerfilUsuario();

                    boolean registrado;

                    CenUsuario cenusuario = new CenUsuario();
                    cenusuario.setNombre(nomusu);
                    cenusuario.setPassword(password);
                    cenusuario.setEstado(1);

                    long idUsuario = usuarioDao.adicionarUsuario(conex, cenusuario);
                    if (idUsuario > 0) {
                        cenperfilusuario.setPef_id(tipoperfil);
                        cenperfilusuario.setUsu_id(idUsuario);
                        cenperfilusuario.setEstado(1);
                        long idperfusu = usuarioDao.adicionarPerfilUsuario(conex, cenperfilusuario);
                        registrado = idperfusu > 0;
                    } else {
                        registrado = false;
                    }

                    if (registrado) {
                        conex.commit();
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Usuario Registrado');");
                        out.println("location='jsp/Usuarios/registrarUsuario.jsp';");
                        out.println("</script>");
                    } else {
                        conex.rollback();
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Usuario no Registrado');");
                        out.println("location='jsp/Usuarios/registrarUsuario.jsp';");
                        out.println("</script>");
                    }

                } else {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Los password no coinciden');");
                    out.println("</script>");
                }
            } else {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Debe ingresar como minimo los datos obligatorios (*)');");
                out.println("</script>");
            }

        } catch (IOException | NumberFormatException | SQLException e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al registrar el usuario');");
            out.println("location='jsp/Usuarios/registrarUsuario.jsp';");
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
