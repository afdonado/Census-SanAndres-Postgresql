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
import javax.sql.DataSource;

@WebServlet(name = "ImportarDocumentos", urlPatterns = "/importarDocumentos")
public class ImportarDocumentos extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {
            
            conex = dataSource.getConnection();

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
                respuesta.put("message", "Censo no se encuentra registrado para cargarle documentos");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            if (request.getParameter("numero") == null || request.getParameter("numero").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'numero censo' no encontrado para modificar censo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String numerocenso = request.getParameter("numero");

            String nombreImagenVehiculo = numerocenso + "_VEH.jpg";
            String nombreImagenImpronta = numerocenso + "_IMP.jpg";
            String nombreImagenLateral = numerocenso + "_LAT.jpg";
            String nombreImagenTrasera = numerocenso + "_TRA.jpg";
            String directorio = "C:/DocumentosDigitalizados/Censos/" + numerocenso + "/";

            CenUsuario cenusuario = (CenUsuario) request.getSession().getAttribute("usuario");

            DocumentoDigitalizadoDao documentoDigitalizadoDao = new DocumentoDigitalizadoDao();
            CenDocumentosDigitalizado cendocumentosdigitalizadoVehiculo
                    = documentoDigitalizadoDao.ConsultarDocumentoDigitalizadoByIdCensoNombre(conex, idcenso, nombreImagenVehiculo);

            if (cendocumentosdigitalizadoVehiculo != null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Imagen de vehiculo " + nombreImagenVehiculo + " ya se encuentra cargada");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            File mkdir = new File(directorio);
            if (!mkdir.exists()) {
                mkdir.mkdirs();
            }

            conex.setAutoCommit(false);

            //Importar Imagen de Vehiculo
            /* URL de la cual se importan los documentos */
            String rutaRemotaVehiculo = "http://www.lagit.com.co/censo_sap/censos/" + nombreImagenVehiculo;

            URL intlLogoURLVeh = new URL(rutaRemotaVehiculo);

            /* llamamos metodo para que lea de la URL y lo escriba en el fichero pasado */
            String imagenvehiculo = directorio + nombreImagenVehiculo;
            documentoDigitalizadoDao.writeTo(intlLogoURLVeh.openStream(), new FileOutputStream(new File(imagenvehiculo)));

            cendocumentosdigitalizadoVehiculo = new CenDocumentosDigitalizado();
            cendocumentosdigitalizadoVehiculo.setNombre(nombreImagenVehiculo);
            cendocumentosdigitalizadoVehiculo.setRuta(imagenvehiculo);
            cendocumentosdigitalizadoVehiculo.setTipo(1);
            cendocumentosdigitalizadoVehiculo.setReferencia_id(idcenso);
            cendocumentosdigitalizadoVehiculo.setObservacion("Imagen cargada desde servidor remoto");
            cendocumentosdigitalizadoVehiculo.setUsu_id(cenusuario.getId());
            long iddocumentoVehiculo = documentoDigitalizadoDao.adicionarDocumentoDigitalizado(conex, cendocumentosdigitalizadoVehiculo);
            boolean cargadaVehiculo = iddocumentoVehiculo > 0;

            //Importar Imagen de Improntas
            CenDocumentosDigitalizado cendocumentosdigitalizadoImpronta
                    = documentoDigitalizadoDao.ConsultarDocumentoDigitalizadoByIdCensoNombre(conex, idcenso, nombreImagenImpronta);
            if (cendocumentosdigitalizadoImpronta == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Imagen de impronta " + nombreImagenImpronta + " ya se encuentra cargada");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String rutaRemotaImpronta = "http://www.lagit.com.co/censo_sap/censos/" + nombreImagenImpronta;

            URL intlLogoURLImp = new URL(rutaRemotaImpronta);

            String imagenimpronta = directorio + nombreImagenImpronta;
            documentoDigitalizadoDao.writeTo(intlLogoURLImp.openStream(), new FileOutputStream(new File(imagenimpronta)));

            cendocumentosdigitalizadoImpronta = new CenDocumentosDigitalizado();
            cendocumentosdigitalizadoImpronta.setNombre(nombreImagenImpronta);
            cendocumentosdigitalizadoImpronta.setRuta(imagenimpronta);
            cendocumentosdigitalizadoImpronta.setTipo(1);
            cendocumentosdigitalizadoImpronta.setReferencia_id(idcenso);
            cendocumentosdigitalizadoImpronta.setObservacion("Imagen cargada desde servidor remoto");
            cendocumentosdigitalizadoImpronta.setUsu_id(cenusuario.getId());
            long iddocumentoImpronta = documentoDigitalizadoDao.adicionarDocumentoDigitalizado(conex, cendocumentosdigitalizadoImpronta);
            boolean cargadaImpronta = iddocumentoImpronta > 0;

            //Importar Imagen de Lateral
            CenDocumentosDigitalizado cendocumentosdigitalizadoLateral
                    = documentoDigitalizadoDao.ConsultarDocumentoDigitalizadoByIdCensoNombre(conex, idcenso, nombreImagenLateral);
            if (cendocumentosdigitalizadoLateral == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Imagen lateral " + nombreImagenLateral + " ya se encuentra cargada");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String rutaRemotaLat = "http://www.lagit.com.co/censo_sap/censos/" + nombreImagenLateral;

            URL intlLogoURLLat = new URL(rutaRemotaLat);

            String imagenlateral = directorio + nombreImagenLateral;
            documentoDigitalizadoDao.writeTo(intlLogoURLLat.openStream(), new FileOutputStream(new File(imagenlateral)));

            cendocumentosdigitalizadoLateral = new CenDocumentosDigitalizado();
            cendocumentosdigitalizadoLateral.setNombre(nombreImagenLateral);
            cendocumentosdigitalizadoLateral.setRuta(imagenlateral);
            cendocumentosdigitalizadoLateral.setTipo(1);
            cendocumentosdigitalizadoLateral.setReferencia_id(idcenso);
            cendocumentosdigitalizadoLateral.setObservacion("Imagen cargada desde servidor remoto");
            cendocumentosdigitalizadoLateral.setUsu_id(cenusuario.getId());
            long iddocumentoLateral = documentoDigitalizadoDao.adicionarDocumentoDigitalizado(conex, cendocumentosdigitalizadoLateral);
            boolean cargadaLateral = iddocumentoLateral > 0;

            //Importar Imagen de trasera
            CenDocumentosDigitalizado cendocumentosdigitalizadoTrasera
                    = documentoDigitalizadoDao.ConsultarDocumentoDigitalizadoByIdCensoNombre(conex, idcenso, nombreImagenTrasera);
            if (cendocumentosdigitalizadoTrasera == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Imagen trasera " + nombreImagenLateral + " ya se encuentra cargada");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String rutaRemotaTra = "http://www.lagit.com.co/censo_sap/censos/" + nombreImagenTrasera;

            URL intlLogoURLTra = new URL(rutaRemotaTra);

            String imagentrasera = directorio + nombreImagenTrasera;
            documentoDigitalizadoDao.writeTo(intlLogoURLTra.openStream(), new FileOutputStream(new File(imagentrasera)));

            cendocumentosdigitalizadoTrasera = new CenDocumentosDigitalizado();
            cendocumentosdigitalizadoTrasera.setNombre(nombreImagenTrasera);
            cendocumentosdigitalizadoTrasera.setRuta(imagentrasera);
            cendocumentosdigitalizadoTrasera.setTipo(1);
            cendocumentosdigitalizadoTrasera.setReferencia_id(idcenso);
            cendocumentosdigitalizadoTrasera.setObservacion("Imagen cargada desde servidor remoto");
            cendocumentosdigitalizadoTrasera.setUsu_id(cenusuario.getId());
            long iddocumentoTrasera = documentoDigitalizadoDao.adicionarDocumentoDigitalizado(conex, cendocumentosdigitalizadoTrasera);
            boolean cargadaTrasera = iddocumentoTrasera > 0;

            if (cargadaVehiculo && cargadaImpronta && cargadaLateral && cargadaTrasera) {
                conex.commit();
                respuesta.put("status", "success");
                respuesta.put("message", "Documento importado exitosamente");
                //out.println("location='jsp/Documentos/ListarDocumentos.jsp?idcenso=" + idcenso + "';");
            } else {
                respuesta.put("status", "fail");
                respuesta.put("message", "Documento no importado");
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
