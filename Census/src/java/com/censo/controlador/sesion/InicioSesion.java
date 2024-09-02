package com.censo.controlador.sesion;

import com.censo.modelo.dao.UsuarioDao;
import com.censo.modelo.persistencia.CenUsuario;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
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

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> respuesta = new HashMap<>();

        UsuarioDao usuarioDao = new UsuarioDao();

        try (Connection conex = usuarioDao.conectar();) {

            if ((request.getParameter("txtloginusuario") == null || request.getParameter("txtloginusuario").isEmpty())
                    && (request.getParameter("txtloginpassword") == null || request.getParameter("txtloginpassword").isEmpty())) {

                respuesta.put("status", "error");
                respuesta.put("message", "Debe ingresar un nombre de usuario y contraseña validos");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String nombre = request.getParameter("txtloginusuario").toUpperCase();
            String password = DigestUtils.md5Hex(request.getParameter("txtloginpassword"));

            CenUsuario cenusuario = usuarioDao.ConsultarUsuario(conex, nombre, password, 1);
            if (cenusuario == null) {
                respuesta.put("status", "fail");
                respuesta.put("message", "Nombre de usuario o contraseña incorrectos");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            HttpSession sessionCensus = request.getSession();
            sessionCensus.setAttribute("usuario", cenusuario);
            sessionCensus.setAttribute("perfil", usuarioDao.ConsultarPerfilById(conex, usuarioDao.ConsultarPerfilUsuarioByIdUsuario(conex, cenusuario.getId()).getPef_id()).getNombre());
            sessionCensus.setAttribute("permisosUsuario", usuarioDao.ListarPermisosById(conex, cenusuario.getId()));
            sessionCensus.setAttribute("modulosUsuario", usuarioDao.ListarModulosByUsuario(conex, cenusuario.getId()));
            sessionCensus.setAttribute("modulos", usuarioDao.ListarModulos(conex));
            respuesta.put("status", "success");
            respuesta.put("redirect", "dashboard");

        } catch (SQLException e) {
            respuesta.put("status", "error");
            respuesta.put("message", "Error al iniciar sesion");
            e.printStackTrace();
        }
        String json = new Gson().toJson(respuesta);
        response.getWriter().write(json);
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
