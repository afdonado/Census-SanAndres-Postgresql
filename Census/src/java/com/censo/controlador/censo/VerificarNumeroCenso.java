package com.censo.controlador.censo;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.persistencia.CenCenso;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "VerificarNumeroCenso", urlPatterns = {"/verificarNumeroCenso"})
public class VerificarNumeroCenso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Connection conex = null;
        
        try {
            
            if (!request.getParameter("numero").equals("")) {
                
                CensoDao censoDao = new CensoDao();
                conex = censoDao.conectar();

                String numero = request.getParameter("numero");

                if (numero.length() < 6) {
                    String prefijo = "CS";
                    numero = prefijo + ("00000".substring(0, 5 - (numero + "").length())) + numero;
                }

                CenCenso cencenso = censoDao.ConsultarCensoByNumero(conex, numero);
                if (cencenso != null) {

                    String fechaCenso = new java.text.SimpleDateFormat("dd/MM/yyyy").format(cencenso.getFecha());

                    out.println("si," + cencenso.getId() + "," + fechaCenso);

                } else {
                    out.println("no");
                }
            } else {
                out.println("no");
            }
        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al verificar el numero de censo');");
            out.println("location='jsp/Inicio.jsp';");
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
