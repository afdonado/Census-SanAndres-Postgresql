package com.censo.controlador.documentos;

import com.censo.modelo.dao.DocumentoDigitalizadoDao;
import com.censo.modelo.persistencia.CenDocumentosDigitalizado;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
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

@WebServlet(name = "EliminarDocumento", urlPatterns = "/eliminarDocumento")
public class EliminarDocumento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, Object> respuesta = new HashMap<>();

        try {

            conex = dataSource.getConnection();

            if (request.getParameter("iddocumento") == null || request.getParameter("iddocumento").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'iddocumento' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            DocumentoDigitalizadoDao documentoDigitalizadoDao = new DocumentoDigitalizadoDao();
            int iddocumento = Integer.parseInt(request.getParameter("iddocumento"));
            CenDocumentosDigitalizado cendocumentosdigitalizado = documentoDigitalizadoDao.ConsultarDocumentoDigitalizadoById(conex, iddocumento);
            if (cendocumentosdigitalizado == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Documento no se encuentra registrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            //condicion para no permitir eliminar un TAG o png
            if(cendocumentosdigitalizado.getTipo() == 2){
                respuesta.put("status", "error");
                respuesta.put("message", "No está permitido eliminar este documento");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String rutaArchivo = cendocumentosdigitalizado.getRuta();
            File archivo = new File(rutaArchivo);
            if (archivo.exists()) {
                // Eliminar el archivo físico
                if (archivo.delete()) {
                    // Si el archivo se elimina correctamente, eliminar el registro en la base de datos
                    conex.setAutoCommit(false);
                            
                    boolean eliminado = documentoDigitalizadoDao.eliminarDocumentoById(conex, iddocumento);
                    if (eliminado) {
                        conex.commit();
                        // Respuesta de éxito
                        respuesta.put("status", "success");
                        respuesta.put("message", "Documento eliminado correctamente");
                    } else {
                        conex.rollback();
                        // Respuesta de error si no se pudo eliminar de la base de datos
                        respuesta.put("status", "fail");
                        respuesta.put("message", "No se pudo eliminar el documento de la base de datos");
                    }
                } else {
                    // Error al eliminar el archivo físico
                    respuesta.put("status", "error");
                    respuesta.put("message", "No se pudo eliminar el archivo físico");
                }
            } else {
                // Archivo no encontrado
                respuesta.put("status", "error");
                respuesta.put("message", "El archivo no existe en el servidor");
            }

        } catch (IOException | NumberFormatException | SQLException e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            respuesta.put("status", "error");
            respuesta.put("message", "No se cargaron los documentos seleccionados");
            e.printStackTrace();
        } finally {
            if (conex != null) {
                try {
                    conex.setAutoCommit(true);
                    conex.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
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
