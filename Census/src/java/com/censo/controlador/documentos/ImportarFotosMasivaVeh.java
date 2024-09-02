package com.censo.controlador.documentos;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.dao.DocumentoDigitalizadoDao;
import com.censo.modelo.persistencia.CenDocumentosDigitalizado;
import com.censo.modelo.persistencia.CenUsuario;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
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

@WebServlet(name = "ImportarFotosMasivaVeh", urlPatterns = "/importarFotosMasivaVeh")
public class ImportarFotosMasivaVeh extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, Object> respuesta = new HashMap<>();

        try {
            
            DocumentoDigitalizadoDao documentoDigitalizadoDao = new DocumentoDigitalizadoDao();
            conex = documentoDigitalizadoDao.conectar();
            
            CensoDao censoDao = new CensoDao();

            long iddocumento = 0;
            long idcenso = 0;

            List<HashMap<String, Object>> lista = censoDao.ListarCensos(conex);

            if (!lista.isEmpty()) {
                conex.setAutoCommit(false);
                for (HashMap hash : lista) {
                    try {
                        idcenso = Long.parseLong(hash.get("CEN_ID").toString());
                        String numerocenso = hash.get("NUMERO").toString().trim();

                        CenUsuario cenusuario = (CenUsuario) request.getSession().getAttribute("usuario");

                        String nombreimagenVeh = numerocenso + "_VEH.jpg";
                        String directorio = "C:/DocumentosDigitalizados/Censos/" + numerocenso + "/";

                        File mkdir = new File(directorio);
                        if (!mkdir.exists()) {
                            mkdir.mkdirs();
                        }

                        CenDocumentosDigitalizado cendocumentosdigitalizadoVeh = 
                                documentoDigitalizadoDao.ConsultarDocumentoDigitalizadoByIdCensoNombre(conex, idcenso, nombreimagenVeh);
                        if (cendocumentosdigitalizadoVeh == null) {
                            String rutaRemotaVeh = "http://www.lagit.com.co/censo_sap/censos/" + nombreimagenVeh;
                            URL intlLogoURLVeh = new URL(rutaRemotaVeh);

                            if (intlLogoURLVeh != null) {
                                String imagenveh = directorio + nombreimagenVeh;
                                documentoDigitalizadoDao.writeTo(intlLogoURLVeh.openStream(), new FileOutputStream(new File(imagenveh)));

                                CenDocumentosDigitalizado cendocumentosdigitalizado = new CenDocumentosDigitalizado();
                                cendocumentosdigitalizado.setNombre(nombreimagenVeh);
                                cendocumentosdigitalizado.setRuta(imagenveh);
                                cendocumentosdigitalizado.setTipo(1);
                                cendocumentosdigitalizado.setReferencia_id(idcenso);
                                cendocumentosdigitalizado.setObservacion("Imagen cargada desde servidor remoto masivamente");
                                cendocumentosdigitalizado.setUsu_id(cenusuario.getId());
                                iddocumento = documentoDigitalizadoDao.adicionarDocumentoDigitalizado(conex, cendocumentosdigitalizado);
                                
                                System.out.println("Imagen cargada censo no. "+ numerocenso);
                            }
                        }
                    } catch (MalformedURLException emal) {
                        System.out.println("Error MalformedURLException --> "+emal.getMessage());
                    } catch (FileNotFoundException efile) {
                        System.out.println("Error FileNotFoundException --> " + efile.getMessage());
                    } catch (IOException exio) {
                        System.out.println("Error IOException --> " + exio.getMessage());
                    } catch (Exception ex) {
                        System.out.println("Error Exception --> " + ex.getMessage());
                    }

                }
                conex.commit();

                if (iddocumento > 0) {
                    conex.commit();
                    respuesta.put("status", "success");
                    respuesta.put("message", "Documentos guardados exitosamente");
                    //out.println("location='jsp/Documentos/ListarDocumentos.jsp?idcenso=" + idcenso + "';");
                } else {
                    conex.rollback();
                    respuesta.put("respuesta", "fail");
                    respuesta.put("message", "Documentos no guardados");
                }

            } else {
                respuesta.put("respuesta", "fail");
                respuesta.put("message", "No se encontraros registros de censos");
            }

        } catch (SQLException e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            respuesta.put("status", "Error al cargar los documentos del censo");
            String jsonError = new Gson().toJson(respuesta);
            response.getWriter().write(jsonError);
            e.printStackTrace();
            //out.println("location='jsp/Documentos/CargarDocumentos.jsp';");
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
