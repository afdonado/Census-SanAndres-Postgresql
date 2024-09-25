package com.censo.controlador.documentos;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.dao.DocumentoDigitalizadoDao;
import com.censo.modelo.persistencia.CenCenso;
import com.censo.modelo.persistencia.CenDocumentosDigitalizado;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.RenderingHints;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import javax.sql.DataSource;

@WebServlet(name = "ListarDocumentos", urlPatterns = "/listarDocumentos")
public class ListarDocumentos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> respuesta = new HashMap<>();

        try (Connection conex = dataSource.getConnection()) {

            if (request.getParameter("idcenso") == null || request.getParameter("idcenso").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'idcenso' no encontrado para modificar censo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            CensoDao censoDao = new CensoDao();

            int idcenso = Integer.parseInt(request.getParameter("idcenso"));
            CenCenso cencenso = censoDao.ConsultarCensoById(conex, idcenso);
            if (cencenso == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Censo no se encuentra registrado para modificarlo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            DocumentoDigitalizadoDao documentoDigitalizadoDao = new DocumentoDigitalizadoDao();
            List listaDocumentos = documentoDigitalizadoDao.ListarDocumentosDigitalizados(conex, idcenso);

            if (listaDocumentos.isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "El censo no tiene documentos cargados");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            listaDocumentos = documentoDigitalizadoDao.ListarDocumentosDigitalizados(conex, idcenso);

            List<Map<String, String>> imagenes = new ArrayList<>();

            for (int i = 0; i < listaDocumentos.size(); i++) {
                CenDocumentosDigitalizado cendocumentosdigitalizado = (CenDocumentosDigitalizado) listaDocumentos.get(i);

                String nombre = cendocumentosdigitalizado.getNombre();
                String extension = nombre.substring(nombre.indexOf(".") + 1, nombre.length());
                FileInputStream archivo = null;

                try {

                    archivo = new FileInputStream(cendocumentosdigitalizado.getRuta());
                    int longitud = archivo.available();
                    byte[] data = new byte[longitud];
                    archivo.read(data, 0, longitud);

                    Map<String, String> imagenData = new HashMap<>();
                    imagenData.put("extension", extension);
                    imagenData.put("nombre", nombre);
                    imagenData.put("iddocumento", String.valueOf(cendocumentosdigitalizado.getId()));

                    if (extension.equals("jpg") || extension.equals("png") || extension.equals("JPG") || extension.equals("PNG")) {

                        BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                        byte[] imageInByteArray;
                        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                            int type = img.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : img.getType();
                            BufferedImage resizedImage = new BufferedImage(500, 500, type);
                            Graphics2D g = resizedImage.createGraphics();
                            g.drawImage(img, 0, 0, 500, 500, null);
                            g.dispose();
                            g.setComposite(AlphaComposite.Src);
                            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            ImageIO.write(resizedImage, extension, baos);
                            baos.flush();
                            imageInByteArray = baos.toByteArray();
                        }
                        String b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
                        if (!b64.isEmpty()) {
                            imagenData.put("b64", b64);
                        }
                    }

                    imagenes.add(imagenData);

                } finally {
                    if (archivo != null) {
                        archivo.close(); // Cerrar el FileInputStream para liberar el archivo
                    }
                }
            }

            if (!imagenes.isEmpty()) {
                respuesta.put("status", "success");
                respuesta.put("message", "Documentos consultados correctamente");
                respuesta.put("numerocenso", cencenso.getNumero());
                respuesta.put("imagenes", imagenes);
            }
        } catch (Exception e) {
            respuesta.put("status", "error");
            respuesta.put("message", "No se consultaron los documentos del censo");
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
