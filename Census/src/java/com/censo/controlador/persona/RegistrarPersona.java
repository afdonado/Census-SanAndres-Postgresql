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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
                respuesta.put("message", "Parametro 'tipo documento' no encontrado para registrar la persona");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            //Validar parametro numero documento
            if (request.getParameter("txtdocumento") == null || request.getParameter("txtdocumento").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'numero documento' no encontrado para registrar la persona");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            int tipoDocumento = Integer.parseInt(request.getParameter("cmbtiposdocumento"));
            String documento = request.getParameter("txtdocumento").toUpperCase().trim();

            //Verificar que el usuario no existe para registrarlo
            CenPersona cenPersona = personaDao.ConsultarPersona(conex, tipoDocumento, documento);
            if (cenPersona != null) {
                respuesta.put("status", "error");
                respuesta.put("message", "El tipo y numero de documento no son validos para registrarlos");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            //Validar parametro txtprimernombre
            if (request.getParameter("txtprimernombre") == null || request.getParameter("txtprimernombre").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'primer nombre' no encontrado para modificar la persona");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            //Validar parametro txtsegundonombre
            if (request.getParameter("txtsegundonombre") == null || request.getParameter("txtsegundonombre").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'segundo nombre' no encontrado para modificar la persona");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            //Validar parametro txtprimerapellido
            if (request.getParameter("txtprimerapellido") == null || request.getParameter("txtprimerapellido").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'primer apellido' no encontrado para modificar la persona");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            //Validar parametro txtsegundoapellido
            if (request.getParameter("txtsegundoapellido") == null || request.getParameter("txtsegundoapellido").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'segundo apellido' no encontrado para modificar la persona");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            //Validar parametro txtfechanacimiento
            if (request.getParameter("txtfechanacimiento") == null || request.getParameter("txtfechanacimiento").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'fecha de nacimiento' no encontrado para modificar la persona");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            conex.setAutoCommit(false);
            
            String primerNombre = request.getParameter("txtprimernombre").toUpperCase().trim();
            String segundoNombre = request.getParameter("txtsegundonombre").toUpperCase().trim();
            String primerApellido = request.getParameter("txtprimerapellido").toUpperCase().trim();
            String segundoApellido = request.getParameter("txtsegundoapellido").toUpperCase().trim();
            Date fechaNacimiento = new Date(new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechanacimiento")).getTime());
            int genero = Integer.parseInt(request.getParameter("cmbgeneros"));
            long municipio = Long.parseLong(request.getParameter("cmbmunicipios"));
            String direccion = request.getParameter("txtdireccion").toUpperCase().trim();
            String telefono = request.getParameter("txttelefono").toUpperCase().trim();
            String email = request.getParameter("txtemail").toUpperCase().trim();
            int grupoSanguineo = Integer.parseInt(request.getParameter("cmbgrupossanguineo"));
            String numeroLicencia = request.getParameter("txtnumerolicencia").toUpperCase().trim();
            int categoriaLicencia = 0;
            Date fechaExpLicencia = null;
            Date fechaVenLicencia = null;
            if (!numeroLicencia.equals("")) {
                categoriaLicencia = Integer.parseInt(request.getParameter("cmdcategoriaslicencia"));
                fechaExpLicencia = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechaexplicencia")).getTime());
                fechaVenLicencia = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechavenlicencia")).getTime());
            }
            
            CenPersona cenpersona = new CenPersona();
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
            if(!numeroLicencia.equals("")){
                cenpersona.setLicenciaconduccion(numeroLicencia);
                cenpersona.setFechaexp(fechaExpLicencia);
                cenpersona.setFechaven(fechaVenLicencia);
                cenpersona.setCategorialicencia(categoriaLicencia);
            }
            
            String nombreCompleto = cenpersona.getNombre1() + " " + (cenpersona.getNombre2() != null ? cenpersona.getNombre2().trim() : "") 
                    + " " + (cenpersona.getApellido1() != null ? cenpersona.getApellido1().trim() : "") 
                    + " " + (cenpersona.getApellido2() != null ? cenpersona.getApellido2().trim() : "");

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
           /* 
            int opcion = Integer.parseInt(request.getParameter("opcion"));
            
            if (idPersona > 0) {
                conex.commit();
                if (opcion == 1) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Persona Registrada');");
                        out.println("location='jsp/Personas/registrarPersona.jsp?opcion=1';");
                        out.println("</script>");
                } else {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Persona Registrada');");
                    out.println("parent.$('#registrarpersona').modal('hide');");
                    out.println("parent.document.getElementById('txtnombre').value = '"+ nombreCompleto +"';");
                    out.println("parent.document.getElementById('idpersona').value = " + idPersona + ";");
                    out.println("</script>");
                }
            } else {
                conex.rollback();
                if (opcion == 1) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Persona no Registrada');");
                    out.println("location='jsp/Personas/registrarPersona.jsp?opcion=1';");
                    out.println("</script>");
                } else {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Persona no Registrada');");
                    out.println("parent.$('#registrarpersona').modal('hide');");
                    out.println("</script>");
                }

            }
*/
        } catch (NumberFormatException | SQLException | ParseException e) {
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
