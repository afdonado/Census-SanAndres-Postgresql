package com.censo.controlador.usuario;

import com.censo.modelo.dao.UsuarioDao;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CargarDatosUsuario", urlPatterns = {"/cargarDatosUsuario"})
public class CargarDatosUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            if (!request.getParameter("id").equals("")) {

                UsuarioDao usuarioDao = new UsuarioDao();
                conex = usuarioDao.conectar();

                long idusuario = Long.parseLong(request.getParameter("id"));

                HashMap<String, String> datosUsuario = usuarioDao.ConsultarDatosUsuarioById(conex, idusuario);

                if (!datosUsuario.isEmpty()) {

                    Gson gson = new Gson();
                    String json = gson.toJson(datosUsuario);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);

                }
            }

        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al consultar los datos del usuario');");
            out.println("location='dashboard';");
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
