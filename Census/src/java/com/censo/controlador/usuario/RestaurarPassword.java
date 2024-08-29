package com.censo.controlador.usuario;

import com.censo.modelo.dao.UsuarioDao;
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

@WebServlet(name = "RestaurarPassword", urlPatterns = "/restaurarPassword")
public class RestaurarPassword extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            if (!request.getParameter("idusuario").equals("") && !request.getParameter("txtdocumento").equals("")) {

                UsuarioDao usuarioDao = new UsuarioDao();
                conex = usuarioDao.conectar();
                conex.setAutoCommit(false);

                String newpassword = DigestUtils.md5Hex(request.getParameter("txtdocumento"));

                long idusuario = Long.parseLong(request.getParameter("idusuario"));

                usuarioDao.restaurarPasswordUsuario(conex, idusuario, newpassword);

                conex.commit();

                out.println("<script type=\"text/javascript\">");
                out.println("alert('Password restaurado');");
                out.println("</script>");

            } else {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Datos necesarios no proporcionados');");
                out.println("location='jsp/Inicio.jsp';");
                out.println("</script>");
            }

        } catch (SQLException e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al restaurar el password');");
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
