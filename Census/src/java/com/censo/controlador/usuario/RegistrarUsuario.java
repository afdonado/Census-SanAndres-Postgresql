package com.censo.controlador.usuario;

import com.censo.modelo.dao.UsuarioDao;
import com.censo.modelo.persistencia.CenPerfilUsuario;
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
import org.apache.commons.codec.digest.DigestUtils;

@WebServlet(name = "RegistrarUsuario", urlPatterns = "/registrarUsuario")
public class RegistrarUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {

            UsuarioDao usuarioDao = new UsuarioDao();
            conex = usuarioDao.conectar();

            //Validar parametro nombre
            if (request.getParameter("txtnombre") == null || request.getParameter("txtnombre").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'nombre' no encontrado para registrar usuario");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String nombre = request.getParameter("txtnombre").toUpperCase().trim();

            //Verificar que el usuario no existe para registrarlo
            CenUsuario cenusuario = usuarioDao.ConsultarUsuarioByNombre(conex, nombre);
            if (cenusuario == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Nombre de usuario no valido para registrarlo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro password
            if (request.getParameter("txtpassword") == null || request.getParameter("txtpassword").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'password' no encontrado para registrar usuario");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro repetir password
            if (request.getParameter("txtrepetirpassword") == null || request.getParameter("txtrepetirpassword").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'repetir password' no encontrado para registrar usuario");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String password = DigestUtils.md5Hex(request.getParameter("txtpassword"));
            String repetirpassword = DigestUtils.md5Hex(request.getParameter("txtrepetirpassword"));

            //Verificar que los password coincidan
            if (!password.equals(repetirpassword)) {
                respuesta.put("status", "error");
                respuesta.put("message", "Los password no coinciden");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro tipo documento
            if (request.getParameter("cmbtiposdocumento") == null || request.getParameter("cmbtiposdocumento").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'tipo documento' no encontrado para modificar usuario");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            //Validar parametro numero documento
            if (request.getParameter("txtdocumento") == null || request.getParameter("txtdocumento").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'numero documento' no encontrado para modificar usuario");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro perfil
            if (request.getParameter("cmbperfiles") == null || request.getParameter("cmbperfiles").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'perfil' no encontrado para modificar usuario");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            String tipodocumento = request.getParameter("cmbtiposdocumento");
            String documento = request.getParameter("txtdocumento").toUpperCase().trim();
            int tipoperfil = Integer.parseInt(request.getParameter("cmbperfiles"));

            conex.setAutoCommit(false);

            boolean registrado;

            cenusuario = new CenUsuario();
            cenusuario.setNombre(nombre);
            cenusuario.setTipodocumento(Integer.parseInt(tipodocumento));
            cenusuario.setNumerodocumento(documento);
            cenusuario.setPassword(password);
            cenusuario.setEstado(1);

            long idusuario = usuarioDao.adicionarUsuario(conex, cenusuario);
            
            CenPerfilUsuario cenperfilusuario = new CenPerfilUsuario();
            
            if (idusuario > 0) {
                cenperfilusuario.setPef_id(tipoperfil);
                cenperfilusuario.setUsu_id(idusuario);
                cenperfilusuario.setEstado(1);
                long idperfilusuario = usuarioDao.adicionarPerfilUsuario(conex, cenperfilusuario);
                registrado = idperfilusuario > 0;
            } else {
                registrado = false;
            }

            if (registrado) {
                conex.commit();
                respuesta.put("status", "success");
                respuesta.put("message", "Usuario registrado exitosamente");
                respuesta.put("id", String.valueOf(idusuario));
               // out.println("location='jsp/Usuarios/verUsuario.jsp?opcion=1&id=" + idUsuario + "';");
            } else {
                conex.rollback();
                respuesta.put("status", "fail");
                respuesta.put("message", "Usuario no registrado");
            }

        } catch (IOException | NumberFormatException | SQLException e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            respuesta.put("status", "error");
            respuesta.put("message", "Error al modificar el usuario");
            e.printStackTrace();
        } finally {
            if (conex != null) {
                try {
                    conex.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
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
