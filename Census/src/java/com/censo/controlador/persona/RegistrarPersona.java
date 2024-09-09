package com.censo.controlador.persona;

import com.censo.modelo.dao.PersonaDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import com.censo.modelo.persistencia.CenPersona;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "RegistrarPersona", urlPatterns = "/registrarPersona")
public class RegistrarPersona extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {

            PersonaDao personaDao = new PersonaDao();
            conex = personaDao.conectar();

            //Validar parametro tipo documento
            if (request.getParameter("cmbtiposdocumento") == null || request.getParameter("cmbtiposdocumento").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'tipo documento' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro numero documento
            if (request.getParameter("txtdocumento") == null || request.getParameter("txtdocumento").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'numero documento' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            int tipoDocumento = Integer.parseInt(request.getParameter("cmbtiposdocumento"));
            String documento = request.getParameter("txtdocumento").toUpperCase().trim();

            //Verificar que el usuario no existe para registrarlo
            CenPersona cenpersona = personaDao.ConsultarPersona(conex, tipoDocumento, documento);
            if (cenpersona != null) {
                respuesta.put("status", "error");
                respuesta.put("message", "El tipo y numero de documento no son validos");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro txtprimernombre
            if (request.getParameter("txtprimernombre") == null || request.getParameter("txtprimernombre").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'primer nombre' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro txtprimerapellido
            if (request.getParameter("txtprimerapellido") == null || request.getParameter("txtprimerapellido").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'primer apellido' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro txtfechanacimiento
            if (request.getParameter("txtfechanacimiento") == null || request.getParameter("txtfechanacimiento").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'fecha de nacimiento' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaActual = LocalDate.now();

            //Validar fecha de nacimiento
            LocalDate fechaNacimiento = LocalDate.parse(request.getParameter("txtfechanacimiento"), formatter);
            Period periodo = Period.between(fechaNacimiento, fechaActual);
            if (periodo.getYears() < 16) {
                respuesta.put("status", "error");
                respuesta.put("message", "Verifique la fecha de NACIMIENTO");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro txtdireccion
            if (request.getParameter("txtdireccion") == null || request.getParameter("txtdireccion").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'direccion' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro txttelefono
            if (request.getParameter("txttelefono") == null || request.getParameter("txttelefono").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'telefono' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            conex.setAutoCommit(false);

            String primerNombre = request.getParameter("txtprimernombre").toUpperCase().trim();
            String segundoNombre = "";
            //Validar parametro txtsegundonombre
            if (request.getParameter("txtsegundonombre") != null) {
                segundoNombre = request.getParameter("txtsegundonombre").toUpperCase().trim();
            }
            String primerApellido = request.getParameter("txtprimerapellido").toUpperCase().trim();
            String segundoApellido = "";
            //Validar parametro txtsegundoapellido
            if (request.getParameter("txtsegundoapellido") != null) {
                segundoApellido = request.getParameter("txtsegundoapellido").toUpperCase().trim();
            }

            int genero = Integer.parseInt(request.getParameter("cmbgeneros"));
            int municipio = Integer.parseInt(request.getParameter("cmbmunicipios"));

            String direccion = request.getParameter("txtdireccion").toUpperCase().trim();
            String telefono = request.getParameter("txttelefono").toUpperCase().trim();
            String email = request.getParameter("txtemail").toUpperCase().trim();
            int grupoSanguineo = Integer.parseInt(request.getParameter("cmbgrupossanguineos"));
            String numeroLicencia = request.getParameter("txtnumerolicencia").toUpperCase().trim();
            int categoriaLicencia = 0;
            LocalDate fechaExpLicencia = null;
            LocalDate fechaVenLicencia = null;
            if (!numeroLicencia.equals("")) {
                categoriaLicencia = Integer.parseInt(request.getParameter("cmbcategoriaslicencia"));

                fechaExpLicencia = LocalDate.parse(request.getParameter("txtfechaexplicencia"), formatter);
                if (fechaExpLicencia.equals(fechaActual)) {
                    respuesta.put("status", "error");
                    respuesta.put("message", "Verifique la fecha de EXPEDICIÓN de licendia");

                    String jsonError = new Gson().toJson(respuesta);
                    response.getWriter().write(jsonError);
                    return;
                }

                fechaVenLicencia = LocalDate.parse(request.getParameter("txtfechavlicencia"), formatter);
                if (fechaVenLicencia.equals(fechaActual)) {
                    respuesta.put("status", "error");
                    respuesta.put("message", "Verifique la fecha de VENCIMIENTO de licendia");

                    String jsonError = new Gson().toJson(respuesta);
                    response.getWriter().write(jsonError);
                    return;
                }
            }

            cenpersona = new CenPersona();
            cenpersona.setTipodocumento(tipoDocumento);
            cenpersona.setDocumento(documento);
            cenpersona.setNombre1(primerNombre);
            cenpersona.setNombre2(segundoNombre);
            cenpersona.setApellido1(primerApellido);
            cenpersona.setApellido2(segundoApellido);
            cenpersona.setFechanacimiento(fechaNacimiento == null ? null : Date.valueOf(fechaNacimiento));
            cenpersona.setGenero(genero);
            cenpersona.setMun_id(municipio);
            cenpersona.setDireccion(direccion);
            cenpersona.setTelefono(telefono);
            cenpersona.setMail(email);
            cenpersona.setGruposanguineo(grupoSanguineo);
            if (!numeroLicencia.equals("")) {
                cenpersona.setLicenciaconduccion(numeroLicencia);
                cenpersona.setFechaexp(fechaExpLicencia == null ? null : Date.valueOf(fechaExpLicencia));
                cenpersona.setFechaven(fechaVenLicencia == null ? null : Date.valueOf(fechaVenLicencia));
                cenpersona.setCategorialicencia(categoriaLicencia);
            }

            long idpersona = personaDao.adicionarPersona(conex, cenpersona);

            if (idpersona > 0) {
                conex.commit();
                respuesta.put("status", "success");
                respuesta.put("message", "Persona registrada exitosamente");
                respuesta.put("id", String.valueOf(idpersona));
            } else {
                conex.rollback();
                respuesta.put("status", "fail");
                respuesta.put("message", "Persona no registrada");
            }

        } catch (NumberFormatException | SQLException e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            respuesta.put("status", "error");
            respuesta.put("message", "Error al registrar la persona");
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
