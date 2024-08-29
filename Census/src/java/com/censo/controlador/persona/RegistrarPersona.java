package com.censo.controlador.persona;

import com.censo.modelo.dao.PersonaDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import com.censo.modelo.persistencia.CenPersona;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "RegistrarPersona", urlPatterns = "/registrarPersona")
public class RegistrarPersona extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {
            
            PersonaDao personaDao = new PersonaDao();
            conex = personaDao.conectar();
            conex.setAutoCommit(false);

            int opcion = Integer.parseInt(request.getParameter("opcion"));
            int tipoDoc = Integer.parseInt(request.getParameter("cmbtipodoc"));
            String documento = request.getParameter("txtdocumento").toUpperCase().trim();
            String primerNombre = request.getParameter("txtprinom").toUpperCase().trim();
            String segundoNombre = request.getParameter("txtsegnom").toUpperCase().trim();
            String primerApellido = request.getParameter("txtpriape").toUpperCase().trim();
            String segundoApellido = request.getParameter("txtsegape").toUpperCase().trim();
            Date fechaNacimiento = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechanac")).getTime());
            int genero = Integer.parseInt(request.getParameter("cmbgenero"));
            long municipioDireccion = Long.parseLong(request.getParameter("cmbmunicipiodir"));
            String direccion = request.getParameter("txtdireccion").toUpperCase().trim();
            String telefono = request.getParameter("txttelefono").toUpperCase().trim();
            String mail = request.getParameter("txtmail").toUpperCase().trim();
            int grupoSanguineo = Integer.parseInt(request.getParameter("cmbgrusanguineo"));
            String numeroLicencia = request.getParameter("txtnumlicond").toUpperCase().trim();
            int categoriaLicencia = Integer.parseInt(request.getParameter("cmdcatlicencia"));
            Date fechaExpLicencia = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechaexplic")).getTime());
            Date fechaVenLicencia = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechavenlic")).getTime());

            CenPersona cenpersona = new CenPersona();
            cenpersona.setTipodocumento(tipoDoc);
            cenpersona.setDocumento(documento);
            cenpersona.setNombre1(primerNombre);
            cenpersona.setNombre2(segundoNombre);
            cenpersona.setApellido1(primerApellido);
            cenpersona.setApellido2(segundoApellido);
            cenpersona.setFechanacimiento(fechaNacimiento);
            cenpersona.setGenero(genero);
            cenpersona.setMun_id(municipioDireccion);
            cenpersona.setDireccion(direccion);
            cenpersona.setTelefono(telefono);
            cenpersona.setMail(mail);
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

            long idPersona = personaDao.adicionarPersona(conex, cenpersona);
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
        } catch (NumberFormatException | SQLException | ParseException e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al registrar la persona');");
            out.println("location='jsp/Personas/registrarPersona.jsp';");
            out.println("</script>");
            e.printStackTrace();
        } finally {
            if (conex != null) {
                try {
                    conex.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
            out.close();
        }
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
