package com.censo.controlador.documentos;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.dao.DocumentoDigitalizadoDao;
import com.censo.modelo.persistencia.CenDocumentosDigitalizado;
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
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ImportarDocumentosMasivo", urlPatterns = "/importarDocumentosMasivo")
public class ImportarDocumentosMasivo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, Object> respuesta = new HashMap<>();

        try {

            if (request.getParameter("complementonombre") == null || request.getParameter("complementonombre").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'complemento nombre' no encontrado para importar documento");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            CensoDao censoDao = new CensoDao();
            conex = censoDao.conectar();

            List<HashMap<String, Object>> lista = censoDao.ListarCensos(conex);

            if (lista.isEmpty()) {
            } else {
                respuesta.put("status", "fail");
                respuesta.put("message", "No se encontraros registros de censos");
            }

            DocumentoDigitalizadoDao documentoDigitalizadoDao = new DocumentoDigitalizadoDao();

            for (HashMap hash : lista) {
                try {

                    int idcenso = Integer.parseInt(hash.get("CEN_ID").toString());
                    String numerocenso = hash.get("NUMERO").toString().trim();
                    String complementonombre = request.getParameter("complementonombre");
                    String nombreDocumento = numerocenso.concat(complementonombre);

                    CenUsuario cenusuario = (CenUsuario) request.getSession().getAttribute("usuario");

                    String directorio = "C:/DocumentosDigitalizados/Censos/" + numerocenso + "/";
                    File mkdir = new File(directorio);
                    if (!mkdir.exists()) {
                        mkdir.mkdirs();
                    }

                    CenDocumentosDigitalizado cendocumentosdigitalizado
                            = documentoDigitalizadoDao.ConsultarDocumentoDigitalizadoByIdCensoNombre(conex, idcenso, nombreDocumento);
                    if (cendocumentosdigitalizado == null) {
                        String rutaRemota = "http://www.lagit.com.co/censo_sap/censos/" + nombreDocumento;
                        URL intlLogoURL = new URL(rutaRemota);

                        String ruta = directorio + nombreDocumento;
                        documentoDigitalizadoDao.writeTo(intlLogoURL.openStream(), new FileOutputStream(new File(ruta)));

                        conex.setAutoCommit(false);

                        cendocumentosdigitalizado = new CenDocumentosDigitalizado();
                        cendocumentosdigitalizado.setNombre(nombreDocumento);
                        cendocumentosdigitalizado.setRuta(ruta);
                        cendocumentosdigitalizado.setTipo(1);
                        cendocumentosdigitalizado.setReferencia_id(idcenso);
                        cendocumentosdigitalizado.setObservacion("Imagen cargada desde servidor remoto masivamente");
                        cendocumentosdigitalizado.setUsu_id(cenusuario.getId());
                        long iddocumento = documentoDigitalizadoDao.adicionarDocumentoDigitalizado(conex, cendocumentosdigitalizado);

                        if (iddocumento > 0) {
                            conex.commit();
                        } else {
                            conex.rollback();
                        }

                        System.out.println("Documento '" + nombreDocumento + "' cargado al censo no. " + numerocenso);
                    }

                } catch (MalformedURLException e) {
                    System.out.println("Error MalformedURLException --> " + e.getMessage());
                } catch (IOException e) {
                    System.out.println("Error IOException --> " + e.getMessage());
                } catch (NumberFormatException | SQLException e) {
                    System.out.println("Error Exception --> " + e.getMessage());
                }

            }

            respuesta.put("status", "success");
            respuesta.put("message", "Proceso terminado exitosamente");

        } catch (SQLException e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            respuesta.put("status", "error");
            respuesta.put("message", "Error al cargar los documentos del censo");
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
