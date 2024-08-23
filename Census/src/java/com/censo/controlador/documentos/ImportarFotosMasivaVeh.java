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
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImportarFotosMasivaVeh extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            DocumentoDigitalizadoDao dao = new DocumentoDigitalizadoDao();
            CensoDao censoDao = new CensoDao();

            long iddocumento = 0;
            long idcenso = 0;
            String numerocenso = "";

            List<HashMap> listaCensos = censoDao.ListarCensos();

            if (listaCensos.size() > 0) {
                dao.conectar().setAutoCommit(false);
                for (HashMap hash : listaCensos) {
                    try {
                        idcenso = Long.parseLong(hash.get("CEN_ID").toString());
                        numerocenso = hash.get("NUMERO").toString().trim();

                        CenUsuario cenusuario = (CenUsuario) request.getSession().getAttribute("usuario");

                        String nombreimagenVeh = numerocenso + "_VEH.jpg";
                        String directorio = "C:/DocumentosDigitalizados/Censos/" + numerocenso + "/";

                        File mkdir = new File(directorio);
                        if (!mkdir.exists()) {
                            mkdir.mkdirs();
                        }

                        CenDocumentosDigitalizado cendocumentosdigitalizadoVeh = dao.ConsultarDocumentoDigitalizadoByIdCensoNombre(idcenso, nombreimagenVeh);
                        if (cendocumentosdigitalizadoVeh == null) {
                            String rutaRemotaVeh = "http://www.lagit.com.co/censo_sap/censos/" + nombreimagenVeh;
                            URL intlLogoURLVeh = new URL(rutaRemotaVeh);

                            if (intlLogoURLVeh != null) {
                                String imagenveh = directorio + nombreimagenVeh;
                                dao.writeTo(intlLogoURLVeh.openStream(), new FileOutputStream(new File(imagenveh)));

                                CenDocumentosDigitalizado cendocumentosdigitalizado = new CenDocumentosDigitalizado();
                                cendocumentosdigitalizado.setNombre(nombreimagenVeh);
                                cendocumentosdigitalizado.setRuta(imagenveh);
                                cendocumentosdigitalizado.setTipo(1);
                                cendocumentosdigitalizado.setReferencia_id(idcenso);
                                cendocumentosdigitalizado.setObservacion("Imagen cargada desde servidor remoto masivamente");
                                cendocumentosdigitalizado.setUsu_id(cenusuario.getId());
                                iddocumento = dao.adicionarDocumentoDigitalizado(cendocumentosdigitalizado);
                                
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
                dao.conectar().commit();

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

        } catch (Exception ex) {
            System.out.println("Error --> " + ex.getMessage());
            out.println("<script type=\"text/javascript\">");
            out.println("alert('No se cargaron los documentos seleccionados');");
            out.println("location='jsp/Documentos/CargarDocumentos.jsp';");
            out.println("</script>");
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
