package com.censo.controlador.censo;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.persistencia.CenCenso;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModificarCenso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            CensoDao dao = new CensoDao();

            dao.conectar().setAutoCommit(false);

            CenCenso cencenso = new CenCenso();

            boolean sw = false;

            //captura de datos del formulario
            Date fechaActual = new java.sql.Date(new java.util.Date().getTime());

            long idcenso = Long.parseLong(request.getParameter("idcenso"));

            String numero = request.getParameter("txtnumero").toUpperCase().trim();
            if (numero.length() < 6) {
                String prefijo = "CS";
                numero = prefijo + ("00000".substring(0, 5 - (numero + "").length())) + numero;
            }

            cencenso = dao.ConsultarCensoById(idcenso);
            if (cencenso != null) {
                if (cencenso.getNumero().equals(numero)) {
                    sw = true;
                } else {
                    cencenso = dao.ConsultarCensoByNumero(numero);
                    if (cencenso != null) {
                        sw = false;
                    } else {
                        cencenso = dao.ConsultarCensoById(idcenso);
                        sw = true;
                    }
                }

                if (sw) {
                    String hora = new java.text.SimpleDateFormat("HHmm").format(fechaActual);
                    int puntoAtencion = Integer.parseInt(request.getParameter("cmbpuntoaten"));
                    long idvehiculo = Long.parseLong(request.getParameter("idvehiculo"));
                    long idpersona = Long.parseLong(request.getParameter("idpersona"));
                    int tipoPersona = Integer.parseInt(request.getParameter("cmbtipopersona"));
                    String observaciones = request.getParameter("txtobservaciones").toUpperCase().trim();
                    Date fechaCenso = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechacenso")).getTime());
                        
                    cencenso.setFecha(fechaCenso);
                    cencenso.setHora(hora);
                    cencenso.setPun_id(puntoAtencion);
                    cencenso.setVeh_id(idvehiculo);
                    cencenso.setPer_id(idpersona);
                    cencenso.setTper_id(tipoPersona);
                    cencenso.setNumero(numero);
                    cencenso.setObservaciones(observaciones);

                    dao.modificarCenso(cencenso);
                    dao.conectar().commit();
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Censo Modificado');");
                    out.println("location='jsp/Censo/verCenso.jsp?idcenso=" + idcenso + "';");
                    out.println("</script>");

                } else {
                    dao.conectar().rollback();
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Censo no fue Modificado');");
                    out.println("location='jsp/Censo/verCenso.jsp?idcenso=" + idcenso + "';");
                    out.println("</script>");
                }
            } else {
                dao.conectar().rollback();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Censo no se encuentra registrado');");
                out.println("location='jsp/Censo/modificarCenso.jsp';");
                out.println("</script>");
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
