package com.censo.controlador.censo;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.persistencia.CenCenso;
import com.censo.modelo.persistencia.CenUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrarCenso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            CensoDao dao = new CensoDao();
            CenUsuario cenusuario = (CenUsuario) request.getSession().getAttribute("usuario");
            CenCenso cencenso = new CenCenso();
            
            dao.conectar().setAutoCommit(false);

            //captura de datos del formulario
            Date fechaActual = new java.sql.Date(new java.util.Date().getTime());
            String hora = new java.text.SimpleDateFormat("HHmm").format(fechaActual);
            String numero = request.getParameter("txtnumero").toUpperCase().trim();
            if (numero.length() < 6) {
                String prefijo = "CS";
                numero = prefijo + ("00000".substring(0, 5 - (numero + "").length())) + numero;
            }
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
            cencenso.setUsu_id(cenusuario.getId());
            cencenso.setEstado(1);
            cencenso.setNumero(numero);
            cencenso.setObservaciones(observaciones);

            long idCenso = dao.adicionarCenso(cencenso);

            if (idCenso > 0) {
                dao.conectar().commit();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Censo Registrado');");
                out.println("location='jsp/Censo/registrarCenso.jsp';");
                out.println("</script>");
            } else {
                dao.conectar().rollback();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Censo no Registrado');");
                out.println("location='jsp/Censo/registrarCenso.jsp';");
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
