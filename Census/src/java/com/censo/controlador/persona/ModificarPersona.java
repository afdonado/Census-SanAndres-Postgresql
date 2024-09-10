package com.censo.controlador.persona;

import com.censo.modelo.dao.PersonaDao;
import com.censo.modelo.persistencia.CenPersona;
import com.censo.modelo.persistencia.CenUsuario;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ModificarPersona", urlPatterns = "/modificarPersona")
public class ModificarPersona extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {
            
            //Validar parametro idpersona
            if (request.getParameter("idpersona") == null || request.getParameter("idpersona").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'id persona' no encontrado para modificar la persona");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            int idpersona = Integer.parseInt(request.getParameter("idpersona"));

            PersonaDao personaDao = new PersonaDao();
            conex = personaDao.conectar();

            //Verificar que la persona existe para modificar
            CenPersona cenpersona = personaDao.ConsultarPersonaById(conex, idpersona);
            if (cenpersona == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "La persona no se encuentra registrada para modificarla");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
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
            

            int tipoDocumento = Integer.parseInt(request.getParameter("cmbtiposdocumento"));
            String documento = request.getParameter("txtdocumento").toUpperCase().trim();
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
            
            int genero = Integer.parseInt(request.getParameter("cmbgeneros"));
            int municipio = Integer.parseInt(request.getParameter("cmbmunicipios"));
            String direccion = request.getParameter("txtdireccion").toUpperCase().trim();
            String telefono = request.getParameter("txttelefono").toUpperCase().trim();
            String email = request.getParameter("txtemail").toUpperCase().trim();
            int grupoSanguineo = Integer.parseInt(request.getParameter("cmbgrupossanguineos"));
            
            String numeroLicencia = "";
            int categoriaLicencia = 0;
            LocalDate fechaExpLicencia = null;
            LocalDate fechaVenLicencia = null;
            String licenciaConduccion = request.getParameter("cmblicenciaconduccion");
            if (licenciaConduccion.equals("S")) {

                if (request.getParameter("txtnumerolicencia") == null || request.getParameter("txtnumerolicencia").isEmpty()) {
                    respuesta.put("status", "error");
                    respuesta.put("message", "Parametro 'numero licencia conduccion' no encontrado");

                    String jsonError = new Gson().toJson(respuesta);
                    response.getWriter().write(jsonError);
                    return;
                }
                numeroLicencia = request.getParameter("cmbcategoriaslicencia").toUpperCase().trim();
                
                if (request.getParameter("cmbcategoriaslicencia") == null || request.getParameter("cmbcategoriaslicencia").isEmpty()) {
                    respuesta.put("status", "error");
                    respuesta.put("message", "Parametro 'categoria licencia conduccion' no encontrado");

                    String jsonError = new Gson().toJson(respuesta);
                    response.getWriter().write(jsonError);
                    return;
                }
                categoriaLicencia = Integer.parseInt(request.getParameter("cmbcategoriaslicencia"));
                
                if (request.getParameter("txtfechaexplicencia") == null || request.getParameter("txtfechaexplicencia").isEmpty()) {
                    respuesta.put("status", "error");
                    respuesta.put("message", "Parametro 'fecha expedición licencia conduccion' no encontrado");

                    String jsonError = new Gson().toJson(respuesta);
                    response.getWriter().write(jsonError);
                    return;
                }
                fechaExpLicencia = LocalDate.parse(request.getParameter("txtfechaexplicencia"), formatter);
                
                if (fechaExpLicencia.equals(fechaActual)) {
                    respuesta.put("status", "error");
                    respuesta.put("message", "Verifique la fecha de 'EXPEDICIÓN' de licendia");

                    String jsonError = new Gson().toJson(respuesta);
                    response.getWriter().write(jsonError);
                    return;
                }
                
                fechaVenLicencia = fechaExpLicencia.plusYears(10);
            }
            
            conex.setAutoCommit(false);

            cenpersona.setId(idpersona);
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
            cenpersona.setLicenciaconduccion(licenciaConduccion);
            if (!numeroLicencia.equals("")) {
                cenpersona.setNumerolicenciaconduccion(numeroLicencia);
                cenpersona.setFechaexp(fechaExpLicencia == null ? null : Date.valueOf(fechaExpLicencia));
                cenpersona.setFechaven(fechaVenLicencia == null ? null : Date.valueOf(fechaVenLicencia));
                cenpersona.setCategorialicencia(categoriaLicencia);
            }

            boolean modificado = personaDao.modificarPersona(conex, cenpersona);
            
            if (modificado) {
                conex.commit();
                respuesta.put("status", "success");
                respuesta.put("message", "Persona modificada exitosamente");
                respuesta.put("id", String.valueOf(idpersona));
            } else {
                conex.rollback();
                respuesta.put("status", "fail");
                respuesta.put("message", "Persona no modificada");
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
            respuesta.put("message", "Error al modificar la persona");
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
