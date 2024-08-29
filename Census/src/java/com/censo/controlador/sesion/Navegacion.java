package com.censo.controlador.sesion;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.censo.modelo.dao.UsuarioDao;
import com.censo.modelo.persistencia.CenPermiso;

@WebServlet(name = "Navegacion", urlPatterns = "/navegacion")
public class Navegacion extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String accion = request.getParameter("accion");

        UsuarioDao usuarioDao = new UsuarioDao();
/*
        try {
            CenPermiso cenpermiso = usuarioDao.ConsultarPermisoByAccion(accion);

            if (cenpermiso != null) {
                request.getRequestDispatcher("/jsp/" + cenpermiso.getUbicacion()).forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
*/
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
