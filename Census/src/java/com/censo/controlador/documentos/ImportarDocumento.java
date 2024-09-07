package com.censo.controlador.documentos;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.dao.DocumentoDigitalizadoDao;
import com.censo.modelo.persistencia.CenDocumentosDigitalizado;
import com.censo.modelo.persistencia.CenCenso;
import com.censo.modelo.persistencia.CenUsuario;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ImportarDocumento", urlPatterns = "/importarDocumento")
public class ImportarDocumento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {

            if (request.getParameter("idcenso") == null || request.getParameter("idcenso").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'idcenso' no encontrado para modificar censo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            CensoDao censoDao = new CensoDao();
            conex = censoDao.conectar();

            int idcenso = Integer.parseInt(request.getParameter("idcenso"));
            CenCenso cencenso = censoDao.ConsultarCensoById(conex, idcenso);
            if (cencenso == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Censo no se encuentra registrado para importar documento");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            if (request.getParameter("numero") == null || request.getParameter("numero").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'numero censo' no encontrado para importar documento");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            if (request.getParameter("nombre") == null || request.getParameter("nombre").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'nombre' no encontrado para importar documento");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String numerocenso = request.getParameter("numerocenso");
            String nombre = request.getParameter("nombre");
            String directorio = "C:/DocumentosDigitalizados/Censos/" + numerocenso + "/";

            CenUsuario cenusuario = (CenUsuario) request.getSession().getAttribute("usuario");

            DocumentoDigitalizadoDao documentoDigitalizadoDao = new DocumentoDigitalizadoDao();
            CenDocumentosDigitalizado cendocumentosdigitalizado
                    = documentoDigitalizadoDao.ConsultarDocumentoDigitalizadoByIdCensoNombre(conex, idcenso, nombre);

            if (cendocumentosdigitalizado != null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Documento " + nombre + " ya se encuentra cargado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            File mkdir = new File(directorio);
            if (!mkdir.exists()) {
                mkdir.mkdirs();
            }

            conex.setAutoCommit(false);

            /* URL de la cual se importan los documentos */
            String rutaRemota = "http://www.lagit.com.co/censo_sap/censos/" + nombre;

            URL intlLogoURL = new URL(rutaRemota);

            /* llamamos metodo para que lea de la URL y lo escriba en el fichero pasado */
            String ruta = directorio + nombre;
            documentoDigitalizadoDao.writeTo(intlLogoURL.openStream(), new FileOutputStream(new File(ruta)));

            cendocumentosdigitalizado = new CenDocumentosDigitalizado();
            cendocumentosdigitalizado.setNombre(nombre);
            cendocumentosdigitalizado.setRuta(ruta);
            cendocumentosdigitalizado.setTipo(1);
            cendocumentosdigitalizado.setReferencia_id(idcenso);
            cendocumentosdigitalizado.setObservacion("Documento cargado desde servidor remoto");
            cendocumentosdigitalizado.setUsu_id(cenusuario.getId());
            long iddocumento = documentoDigitalizadoDao.adicionarDocumentoDigitalizado(conex, cendocumentosdigitalizado);
            boolean cargado = iddocumento > 0;

            if (cargado) {
                conex.commit();
                respuesta.put("status", "success");
                respuesta.put("message", "Documento '"+nombre+"' importado exitosamente");
                //out.println("location='jsp/Documentos/ListarDocumentos.jsp?idcenso=" + idcenso + "';");
            } else {
                respuesta.put("status", "fail");
                respuesta.put("message", "Documento '"+nombre+"' no importado");
            }

        } catch (MalformedURLException e) {
            respuesta.put("status", "error");
            respuesta.put("message", "Url de importacion incorrecta");
            e.printStackTrace();

        } catch (IOException e) {
            respuesta.put("status", "error");
            respuesta.put("message", "No se encontraron documentos para el numero de censo");
            e.printStackTrace();
        } catch (NumberFormatException | SQLException e) {
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
