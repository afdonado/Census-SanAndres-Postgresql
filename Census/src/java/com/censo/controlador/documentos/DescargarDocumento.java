package com.censo.controlador.documentos;

import com.censo.modelo.dao.DocumentoDigitalizadoDao;
import com.censo.modelo.persistencia.CenDocumentosDigitalizado;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.System.out;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DescargarDocumento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {

            DocumentoDigitalizadoDao dao = new DocumentoDigitalizadoDao();

            if (request.getParameter("iddocumento") != null) {

                String nombre = "";
                String ruta = "";
                int iddocumento = Integer.parseInt(request.getParameter("iddocumento"));

                CenDocumentosDigitalizado cendocumentosdigitalizado = dao.ConsultarDocumentoDigitalizadoById(iddocumento);

                if (cendocumentosdigitalizado != null) {

                    if (cendocumentosdigitalizado.getRuta().startsWith("..")) {
                        ruta = cendocumentosdigitalizado.getRuta().substring(3);
                    } else {
                        ruta = cendocumentosdigitalizado.getRuta().replace("/", "\\");
                    }
                    nombre = cendocumentosdigitalizado.getNombre();
                    FileInputStream archivo = new FileInputStream(ruta);
                    int longitud = archivo.available();
                    byte[] ba = new byte[longitud];

                    String format = nombre.substring(nombre.indexOf(".") + 1, nombre.length());
                    String tipo = "";
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
                    OutputStream ar = response.getOutputStream();
                    archivo.read(ba);
                    ar.write(ba);
                    ar.flush();

                    ar.close();

                } else {
                    out.print("<script>alert('Censo no presenta documentos');</script>");
                }
            } else {
                out.print("<script>alert('Datos vacios para consultar el documento');</script>");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
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
