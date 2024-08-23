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

public class RegistrarPersona extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            
            PersonaDao dao = new PersonaDao();

            dao.conectar().setAutoCommit(false);
            
            CenPersona cenpersona = new CenPersona();

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

            long idPersona = dao.adicionarPersona(cenpersona);
            if (idPersona > 0) {
                dao.conectar().commit();
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
                dao.conectar().rollback();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
