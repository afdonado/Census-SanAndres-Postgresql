package com.censo.controlador.documentos;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.dao.DocumentoDigitalizadoDao;
import com.censo.modelo.persistencia.CenCenso;
import com.censo.modelo.persistencia.CenDocumentosDigitalizado;
import com.censo.modelo.persistencia.CenUsuario;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "CargarDocumentos", urlPatterns = "/cargarDocumentos")
public class CargarDocumentos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, Object> respuesta = new HashMap<>();

        try {

            conex = dataSource.getConnection();

            if (request.getParameter("idcenso") == null || request.getParameter("idcenso").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'idcenso' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            CensoDao censoDao = new CensoDao();
            int idcenso = Integer.parseInt(request.getParameter("idcenso"));
            CenCenso cencenso = censoDao.ConsultarCensoById(conex, idcenso);
            if (cencenso == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Censo no se encuentra registrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            CenUsuario cenusuario = (CenUsuario) request.getSession().getAttribute("usuario");

            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload sfu = new ServletFileUpload(factory);

            if (!ServletFileUpload.isMultipartContent(request)) {
                return;
            }

            List items = sfu.parseRequest(request);

            int cant = items.size() - 1;

            for (int i = 0; i <= cant; i++) {
                FileItem file = (FileItem) items.get(i);
                String nombre = (String) file.getName();

                int tipo = 0;

                List<String> formatosPermitidos = Arrays.asList(".jpg", ".png", ".pdf");
                String format = nombre.substring(nombre.lastIndexOf(".")).toLowerCase();

                if (!formatosPermitidos.contains(format)) {
                    respuesta.put("status", "error");
                    respuesta.put("message", "La extensión de los documentos seleccionados no es permitida");

                    String jsonError = new Gson().toJson(respuesta);
                    response.getWriter().write(jsonError);
                    break;

                }

                if (format.equalsIgnoreCase(".jpg")) {
                    tipo = 1;
                } else if (format.equalsIgnoreCase(".png")) {
                    tipo = 2;
                } else if (format.equalsIgnoreCase(".pdf")) {
                    tipo = 3;
                }

                //Local
                //String directorio = "C:/DocumentosDigitalizados/Censos/" + cencenso.getNumero();
                //String ruta = "C:/DocumentosDigitalizados/Censos/" + cencenso.getNumero() + "/" + file.getName();
                
                //Servidor pdn
                String directorio = "/documentos/censos/" + cencenso.getNumero() + "/";
                String ruta = "/documentos/censos/" + cencenso.getNumero() + "/" + file.getName();

                File mkdir = new File(directorio);
                if (!mkdir.exists()) {
                    mkdir.mkdirs();
                }

                File saveTo = new File(ruta);
                if (saveTo.exists()) {
                    respuesta.put("status", "error");
                    respuesta.put("message", "Uno o varios documentos seleccionados ya se encuentran registrados");

                    String jsonError = new Gson().toJson(respuesta);
                    response.getWriter().write(jsonError);
                    return;
                }

                file.write(saveTo);

                conex.setAutoCommit(false);

                DocumentoDigitalizadoDao documentoDigitalizadoDao = new DocumentoDigitalizadoDao();
                CenDocumentosDigitalizado cendocumentosdigitalizado = new CenDocumentosDigitalizado();
                cendocumentosdigitalizado.setNombre(file.getName());
                cendocumentosdigitalizado.setRuta(ruta);
                cendocumentosdigitalizado.setTipo(tipo);
                cendocumentosdigitalizado.setReferencia_id(idcenso);
                cendocumentosdigitalizado.setObservacion("Imagen cargada desde cargar documentos");
                cendocumentosdigitalizado.setUsu_id(cenusuario.getId());
                long iddocumento = documentoDigitalizadoDao.adicionarDocumentoDigitalizado(conex, cendocumentosdigitalizado);

                if (iddocumento > 0) {
                    conex.commit();
                    respuesta.put("status", "success");
                    respuesta.put("message", "Documentos cargados correctamente");
                    respuesta.put("id", String.valueOf(idcenso));
                } else {
                    conex.rollback();
                    respuesta.put("status", "fail");
                    respuesta.put("message", "Documento no registrado");
                }
                conex.setAutoCommit(true);
            }
        } catch (Exception e) {
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
