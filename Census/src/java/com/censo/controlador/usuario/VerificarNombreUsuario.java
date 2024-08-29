package com.censo.controlador.usuario;

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

@WebServlet(name = "VerificarNombreUsuario", urlPatterns = {"/verificarNombreUsuario"})
public class VerificarNombreUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            if (!request.getParameter("nomusuario").equals("")) {

                UsuarioDao usuarioDao = new UsuarioDao();
                conex = usuarioDao.conectar();

                String nomusuario = request.getParameter("nomusuario");

                CenUsuario cenusuario = usuarioDao.ConsultarUsuarioByNombre(conex, nomusuario);
                
                if (cenusuario != null) {
                    out.println("si");
                } else {
                    out.println("no");
                }
            } else {
                out.println("no");
            }
        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al verificar nombre de usuario');");
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
