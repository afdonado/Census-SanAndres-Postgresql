package com.censo.controlador.persona;

import com.censo.modelo.dao.PersonaDao;
import com.censo.modelo.persistencia.CenPersona;
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

@WebServlet(name = "VerificarDocumentoPersona", urlPatterns = {"/verificarDocumentoPersona"})
public class VerificarDocumentoPersona extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {

            //Validar parametro tipo documento
            if (request.getParameter("tipodocumento") == null || request.getParameter("tipodocumento").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'tipo documento' no encontrado para registrar la persona");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro numero documento
            if (request.getParameter("documento") == null || request.getParameter("documento").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'numero documento' no encontrado para registrar la persona");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            int tipoDocumento = Integer.parseInt(request.getParameter("tipodocumento"));
            String documento = request.getParameter("documento").toUpperCase().trim();

            PersonaDao personaDao = new PersonaDao();
            conex = personaDao.conectar();
            
            //Verificar si existe la persona con tipo y numero de documento
            CenPersona cenPersona = personaDao.ConsultarPersona(conex, tipoDocumento, documento);
            if (cenPersona != null) {
                
                String nombreCompleto = 
                        cenPersona.getNombre1() + " " + 
                        (cenPersona.getNombre2() != null ? cenPersona.getNombre2().trim() : "") + " " + 
                        (cenPersona.getApellido1() != null ? cenPersona.getApellido1().trim() : "") + " " + 
                        (cenPersona.getApellido2() != null ? cenPersona.getApellido2().trim() : "");
                
                respuesta.put("status", "success");
                respuesta.put("message", "Tipo y numero de documento validos");
                respuesta.put("nombre", nombreCompleto);
                respuesta.put("id", String.valueOf(cenPersona.getId()));
            } else {
                respuesta.put("status", "fail");
                respuesta.put("message", "Tipo y numero de documento no validos");
            }
        } catch (SQLException e) {
            respuesta.put("status", "error");
            respuesta.put("message", "Error al verificar el numero de documento");
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
