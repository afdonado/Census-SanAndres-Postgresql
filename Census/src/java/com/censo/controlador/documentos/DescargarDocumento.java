package com.censo.controlador.documentos;

import com.censo.modelo.dao.DocumentoDigitalizadoDao;
import com.censo.modelo.persistencia.CenDocumentosDigitalizado;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DescargarDocumento", urlPatterns = "/descargarDocumento")
public class DescargarDocumento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            if (request.getParameter("iddocumento") != null) {

                DocumentoDigitalizadoDao documentoDigitalizadoDao = new DocumentoDigitalizadoDao();
                conex = documentoDigitalizadoDao.conectar();

                int iddocumento = Integer.parseInt(request.getParameter("iddocumento"));

                CenDocumentosDigitalizado cendocumentosdigitalizado
                        = documentoDigitalizadoDao.ConsultarDocumentoDigitalizadoById(conex, iddocumento);

                if (cendocumentosdigitalizado != null) {

                    String ruta;

                    if (cendocumentosdigitalizado.getRuta().startsWith("..")) {
                        ruta = cendocumentosdigitalizado.getRuta().substring(3);
                    } else {
                        ruta = cendocumentosdigitalizado.getRuta().replace("/", "\\");
                    }
                    String nombre = cendocumentosdigitalizado.getNombre();
                    FileInputStream archivo = new FileInputStream(ruta);
                    int longitud = archivo.available();
                    byte[] ba = new byte[longitud];

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

                    response.setContentType(tipo);
                    response.setContentLength((int) longitud);

                    response.setHeader("Content-Disposition", "attachment; filename=\"" + nombre + "\"");
                    response.setHeader("cache-control", "no-cache");
                    try (OutputStream ar = response.getOutputStream()) {
                        archivo.read(ba);
                        ar.write(ba);
                        ar.flush();
                    }

                } else {
                    out.print("<script>alert('Censo no presenta documentos');</script>");
                }
            } else {
                out.print("<script>alert('Datos vacios para consultar el documento');</script>");
            }
        } catch (IOException | NumberFormatException | SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('No se descargaron los documentos');");
            out.println("location='jsp/Documentos/CargarDocumentos.jsp';");
            out.println("</script>");
            e.printStackTrace();
            System.out.println("Error Cargar Documentos --> " + e.getMessage());
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
