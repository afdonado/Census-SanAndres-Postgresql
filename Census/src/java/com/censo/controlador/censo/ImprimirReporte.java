package com.censo.controlador.censo;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperRunManager;

@WebServlet(name = "ImprimirReporte", urlPatterns = "/imprimirReporte")
public class ImprimirReporte extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         
        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");
        
        Map<String, String> respuesta = new HashMap<>();
        
        try (Connection conex = dataSource.getConnection()) {
            
            if (request.getParameter("idcenso").equals("")) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                respuesta.put("status", "Parametro 'idcenso' no encontrado");
                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            long idCenso = Long.parseLong(request.getParameter("idcenso"));
            
            Map parametros = new HashMap();
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
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            respuesta.put("status", "error");
            respuesta.put("message", "Error al generar reporte del censo");
            String jsonError = new Gson().toJson(respuesta);
            response.getWriter().write(jsonError);
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
