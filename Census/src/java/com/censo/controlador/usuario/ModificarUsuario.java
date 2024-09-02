package com.censo.controlador.usuario;

import com.censo.modelo.dao.UsuarioDao;
import com.censo.modelo.persistencia.CenPerfilUsuario;
import com.censo.modelo.persistencia.CenUsuario;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ModificarUsuario", urlPatterns = "/modificarUsuario")
public class ModificarUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {
            
            //Validar parametro idusuario
            if (request.getParameter("idusuario") == null || request.getParameter("idusuario").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'id usuario' no encontrado para modificar usuario");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            long idusuario = Long.parseLong(request.getParameter("idusuario"));

            UsuarioDao usuarioDao = new UsuarioDao();
            conex = usuarioDao.conectar();
            
            //Verificar que el usuario existe para modificar
            CenUsuario cenusuario = usuarioDao.ConsultarUsuarioById(conex, idusuario);
            if (cenusuario == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Usuario no se encuentra registrado para modificarlo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            //Validar parametro estado
            if (request.getParameter("cmbestados") == null || request.getParameter("cmbestados").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'estado' no encontrado para modificar usuario");

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
            
            conex.setAutoCommit(false);

            boolean modificado;

            Date fechaActual = new Date(new java.util.Date().getTime());
            int estado = Integer.parseInt(request.getParameter("cmbestados"));
            int tipoDocumento = Integer.parseInt(request.getParameter("cmbtiposdocumento"));
            String documento = request.getParameter("txtdocumento");

            if (documento.isEmpty()) {
                cenusuario.setTipodocumento(0);
                cenusuario.setNumerodocumento("");
            } else {
                cenusuario.setTipodocumento(tipoDocumento);
                cenusuario.setNumerodocumento(documento);
            }

            cenusuario.setEstado(estado);
            if (estado != 1) {
                cenusuario.setFechafin(fechaActual);
            } else {
                cenusuario.setFechafin(null);
            }
            modificado = usuarioDao.modificarUsuario(conex, cenusuario);

            if (modificado) {

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

                    modificado = usuarioDao.modificarPerfilUsuario(conex, cenperfilusuario);
                } else {
                    cenperfilusuario = new CenPerfilUsuario();
                    cenperfilusuario.setPef_id(tipoperfil);
                    cenperfilusuario.setUsu_id(idusuario);
                    cenperfilusuario.setEstado(1);
                    long idPerfilUsuario = usuarioDao.adicionarPerfilUsuario(conex, cenperfilusuario);

                    modificado = idPerfilUsuario != 0;
                }
            }

            if (modificado) {
                conex.commit();
                respuesta.put("status", "success");
                respuesta.put("message", "Usuario modificado exitosamente");
                respuesta.put("id", String.valueOf(idusuario));
            } else {
                conex.rollback();
                respuesta.put("status", "fail");
                respuesta.put("message", "Usuario no modificado");
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
