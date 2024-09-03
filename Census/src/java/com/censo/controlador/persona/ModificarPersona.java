package com.censo.controlador.persona;

import com.censo.modelo.dao.PersonaDao;
import com.censo.modelo.persistencia.CenPersona;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

            long idpersona = Long.parseLong(request.getParameter("idpersona"));

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
            Date fechaNacimiento = new Date(new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechanacimiento")).getTime());
            int genero = Integer.parseInt(request.getParameter("cmbgeneros"));
            long municipio = Long.parseLong(request.getParameter("cmbmunicipios"));
            String direccion = request.getParameter("txtdireccion").toUpperCase().trim();
            String telefono = request.getParameter("txttelefono").toUpperCase().trim();
            String email = request.getParameter("txtemail").toUpperCase().trim();
            int grupoSanguineo = Integer.parseInt(request.getParameter("cmbgrupossanguineos"));
            String numeroLicencia = request.getParameter("txtnumerolicencia").toUpperCase().trim();
            int categoriaLicencia = 0;
            Date fechaExpLicencia = null;
            Date fechaVenLicencia = null;
            if (!numeroLicencia.equals("")) {
                categoriaLicencia = Integer.parseInt(request.getParameter("cmbcategoriaslicencia"));
                fechaExpLicencia = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechaexplicencia")).getTime());
                fechaVenLicencia = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechavlicencia")).getTime());
            }

            conex.setAutoCommit(false);

            cenpersona.setId(idpersona);
            cenpersona.setTipodocumento(tipoDocumento);
            cenpersona.setDocumento(documento);
            cenpersona.setNombre1(primerNombre);
            cenpersona.setNombre2(segundoNombre);
            cenpersona.setApellido1(primerApellido);
            cenpersona.setApellido2(segundoApellido);
            cenpersona.setFechanacimiento(fechaNacimiento);
            cenpersona.setGenero(genero);
            cenpersona.setMun_id(municipio);
            cenpersona.setDireccion(direccion);
            cenpersona.setTelefono(telefono);
            cenpersona.setMail(email);
            cenpersona.setGruposanguineo(grupoSanguineo);
            if (!numeroLicencia.equals("")) {
                cenpersona.setLicenciaconduccion(numeroLicencia);
                cenpersona.setFechaexp(fechaExpLicencia);
                cenpersona.setFechaven(fechaVenLicencia);
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
        } catch (IOException | NumberFormatException | SQLException | ParseException e) {
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
