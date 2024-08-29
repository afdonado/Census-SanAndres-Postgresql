package com.censo.controlador.documentos;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.dao.DocumentoDigitalizadoDao;
import com.censo.modelo.persistencia.CenDocumentosDigitalizado;
import com.censo.modelo.persistencia.CenUsuario;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ImportarFotosMasivaImp", urlPatterns = "/importarFotosMasivaImp")
public class ImportarFotosMasivaImp extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {
            DocumentoDigitalizadoDao documentoDigitalizadoDao = new DocumentoDigitalizadoDao();
            conex = documentoDigitalizadoDao.conectar();

            CensoDao censoDao = new CensoDao();

            long iddocumento = 0;
            long idcenso = 0;

            List<HashMap> listaCensos = censoDao.ListarCensos(conex);

            if (!listaCensos.isEmpty()) {
                conex.setAutoCommit(false);
                for (HashMap hash : listaCensos) {
                    try {
                        idcenso = Long.parseLong(hash.get("CEN_ID").toString());
                        String numerocenso = hash.get("NUMERO").toString().trim();

                        CenUsuario cenusuario = (CenUsuario) request.getSession().getAttribute("usuario");

                        String nombreimagenImp = numerocenso + "_IMP.jpg";
                        String directorio = "C:/DocumentosDigitalizados/Censos/" + numerocenso + "/";

                        File mkdir = new File(directorio);
                        if (!mkdir.exists()) {
                            mkdir.mkdirs();
                        }

                        CenDocumentosDigitalizado cendocumentosdigitalizadoImp
                                = documentoDigitalizadoDao.ConsultarDocumentoDigitalizadoByIdCensoNombre(conex, idcenso, nombreimagenImp);
                        if (cendocumentosdigitalizadoImp == null) {
                            String rutaRemotaImp = "http://www.lagit.com.co/censo_sap/censos/" + nombreimagenImp;
                            URL intlLogoURLImp = new URL(rutaRemotaImp);

                            if (intlLogoURLImp != null) {
                                String imagenimp = directorio + nombreimagenImp;
                                documentoDigitalizadoDao.writeTo(intlLogoURLImp.openStream(), new FileOutputStream(new File(imagenimp)));

                                CenDocumentosDigitalizado cendocumentosdigitalizado = new CenDocumentosDigitalizado();
                                cendocumentosdigitalizado.setNombre(nombreimagenImp);
                                cendocumentosdigitalizado.setRuta(imagenimp);
                                cendocumentosdigitalizado.setTipo(1);
                                cendocumentosdigitalizado.setReferencia_id(idcenso);
                                cendocumentosdigitalizado.setObservacion("Imagen cargada desde servidor remoto masivamente");
                                cendocumentosdigitalizado.setUsu_id(cenusuario.getId());
                                iddocumento = documentoDigitalizadoDao.adicionarDocumentoDigitalizado(conex, cendocumentosdigitalizado);

                                System.out.println("Imagen cargada censo no. " + numerocenso);
                            }
                        }

                    } catch (MalformedURLException emal) {
                        System.out.println("Error MalformedURLException --> " + emal.getMessage());
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
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Documentos Guardados');");
                    out.println("location='jsp/Documentos/ListarDocumentos.jsp?idcenso=" + idcenso + "';");
                    out.println("</script>");
                } else {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Documentos no Guardados');");
                    out.println("location='jsp/Documentos/ListarDocumentos.jsp?idcenso=" + idcenso + "';");
                    out.println("</script>");
                }

            }

        } catch (SQLException ex) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            System.out.println("Error --> " + ex.getMessage());
            out.println("<script type=\"text/javascript\">");
            out.println("alert('No se cargaron los documentos seleccionados');");
            out.println("location='jsp/Documentos/CargarDocumentos.jsp';");
            out.println("</script>");
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
