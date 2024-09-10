package com.censo.controlador.persona;

import com.censo.modelo.dao.Persona2017Dao;
import com.censo.modelo.dao.PersonaDao;
import com.censo.modelo.persistencia.CenPersona;
import com.censo.modelo.persistencia.Persona2017;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "VerificarDocumentoPersona2017", urlPatterns = {"/verificarDocumentoPersona2017"})
public class VerificarDocumentoPersona2017 extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, Object> respuesta = new HashMap<>();

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
            if (cenPersona == null) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fecha = LocalDate.now();
                String fechaActual = fecha.format(formatter);

                Persona2017Dao persona2017Dao = new Persona2017Dao();
                Persona2017 persona2017 = persona2017Dao.ConsultarPersona2017(conex, tipoDocumento, documento);

                if (persona2017 == null) {
                    respuesta.put("status", "success");
                    respuesta.put("message", "Tipo y numero de documento validos");
                } else {

                    String fechaNacimiento = persona2017.getFechaNacimiento() == null
                            ? fechaActual : persona2017.getFechaNacimiento().toLocalDate().format(formatter);
                    String fechaExpedicionLicencia = persona2017.getFechaExpedicion() == null
                            ? fechaActual : persona2017.getFechaExpedicion().toLocalDate().format(formatter);

                    respuesta.put("status", "success2017");
                    respuesta.put("persona", persona2017);
                    respuesta.put("fechaNacimiento", fechaNacimiento);
                    respuesta.put("fechaExpedicion", fechaExpedicionLicencia);
                    respuesta.put("message", "Tipo y numero de doumento encontrado en personas 2017");
                }

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
