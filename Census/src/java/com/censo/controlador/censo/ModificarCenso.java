package com.censo.controlador.censo;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.persistencia.CenCenso;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ModificarCenso", urlPatterns = "/modificarCenso")
public class ModificarCenso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Connection conex = null;
        
        try {
            CensoDao censoDao = new CensoDao();
            conex = censoDao.conectar();
            conex.setAutoCommit(false);

            boolean sw;

            //captura de datos del formulario
            Date fechaActual = new java.sql.Date(new java.util.Date().getTime());

            long idcenso = Long.parseLong(request.getParameter("idcenso"));

            String numero = request.getParameter("txtnumero").toUpperCase().trim();
            if (numero.length() < 6) {
                String prefijo = "CS";
                numero = prefijo + ("00000".substring(0, 5 - (numero + "").length())) + numero;
            }

            CenCenso cencenso = censoDao.ConsultarCensoById(conex, idcenso);
            if (cencenso != null) {
                if (cencenso.getNumero().equals(numero)) {
                    sw = true;
                } else {
                    cencenso = censoDao.ConsultarCensoByNumero(conex, numero);
                    if (cencenso != null) {
                        sw = false;
                    } else {
                        cencenso = censoDao.ConsultarCensoById(conex, idcenso);
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

                    censoDao.modificarCenso(conex, cencenso);
                    conex.commit();
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Censo Modificado');");
                    out.println("location='jsp/Censo/verCenso.jsp?idcenso=" + idcenso + "';");
                    out.println("</script>");

                } else {
                    conex.rollback();
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Censo no fue Modificado');");
                    out.println("location='jsp/Censo/verCenso.jsp?idcenso=" + idcenso + "';");
                    out.println("</script>");
                }
            } else {
                conex.rollback();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Censo no se encuentra registrado');");
                out.println("location='jsp/Censo/modificarCenso.jsp';");
                out.println("</script>");
            }

        } catch (SQLException | ParseException e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al modificar el censo');");
            out.println("location='jsp/Censo/registrarCenso.jsp';");
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
