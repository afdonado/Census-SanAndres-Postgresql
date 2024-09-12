package com.censo.controlador.documentos;

import com.censo.modelo.dao.DocumentoDigitalizadoDao;
import com.censo.modelo.persistencia.CenDocumentosDigitalizado;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "DescargarDocumento", urlPatterns = "/descargarDocumento")
public class DescargarDocumento extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> respuesta = new HashMap<>();

        try (Connection conex = dataSource.getConnection()){

            if (request.getParameter("iddocumento") == null || request.getParameter("iddocumento").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'id documento' no encontrado para descargar");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            DocumentoDigitalizadoDao documentoDigitalizadoDao = new DocumentoDigitalizadoDao();

            int iddocumento = Integer.parseInt(request.getParameter("iddocumento"));
            CenDocumentosDigitalizado cendocumentosdigitalizado = documentoDigitalizadoDao.ConsultarDocumentoDigitalizadoById(conex, iddocumento);
            if (cendocumentosdigitalizado == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Documento no se encuentra registrado para descargar");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String nombre = cendocumentosdigitalizado.getNombre();
            
            
            String ruta;
            if (cendocumentosdigitalizado.getRuta().startsWith("..")) {
                ruta = cendocumentosdigitalizado.getRuta().substring(3);
            } else {
                ruta = cendocumentosdigitalizado.getRuta().replace("/", "\\");
            }

            String format = nombre.substring(nombre.indexOf(".") + 1, nombre.length());
            String tipo;
            if (format.equalsIgnoreCase("jpg")) {
                tipo = "image/jpg";
            } else {

                if (format.equalsIgnoreCase("pdf")) {
                    tipo = "application/pdf";
                } else {
                    tipo = "image/png";
                }

            }

            FileInputStream archivo = new FileInputStream(ruta);
            int longitud = archivo.available();
            byte[] ba = new byte[longitud];
            
            response.setContentType(tipo);
            response.setContentLength((int) longitud);

            response.setHeader("Content-Disposition", "attachment; filename=\"" + nombre + "\"");
            response.setHeader("cache-control", "no-cache");
            try (OutputStream ar = response.getOutputStream()) {
                archivo.read(ba);
                ar.write(ba);
                ar.flush();
            }

        } catch (IOException | NumberFormatException | SQLException e) {
            respuesta.put("status", "error");
            respuesta.put("message", "Error descargando el documento");
            e.printStackTrace();
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
