package com.censo.controlador.censo;

import com.censo.modelo.dao.Conexion;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperRunManager;

@WebServlet(name = "ImprimirReporte", urlPatterns = "/imprimirReporte")
public class ImprimirReporte extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Conexion conexion = new Conexion();
        
        try(Connection conex = conexion.conectar()) {

            long idCenso = Long.parseLong(request.getParameter("idcenso"));

            java.util.Map parametros = new HashMap();
            String direccion = this.getServletContext().getRealPath("/reportes/Censo.jasper");
            parametros.put("ID_CENSO", idCenso);

            byte[] bytes = JasperRunManager.runReportToPdf(direccion, parametros, conex);

            OutputStream outputStream = response.getOutputStream();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.addHeader("Content-Disposition", "inline;");

            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
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
