package com.censo.controlador.documentos;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.dao.DocumentoDigitalizadoDao;
import com.censo.modelo.persistencia.CenDocumentosDigitalizado;
import com.censo.modelo.persistencia.CenCenso;
import com.censo.modelo.persistencia.CenUsuario;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImportarDocumentos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            DocumentoDigitalizadoDao dao = new DocumentoDigitalizadoDao();
            CensoDao daoCenso = new CensoDao();

            long iddocumento = 0;
            long idcenso = 0;
            String numerocenso = "";

            if (request.getParameter("idcenso") != null && request.getParameter("txtnumerocenso") != null) {

                dao.conectar().setAutoCommit(false);

                idcenso = Long.parseLong(request.getParameter("idcenso"));
                numerocenso = request.getParameter("txtnumerocenso");

                CenUsuario cenusuario = (CenUsuario) request.getSession().getAttribute("usuario");

                String nombreimagenVeh = numerocenso + "_VEH.jpg";
                String nombreimagenImp = numerocenso + "_IMP.jpg";
                String nombreimagenLat = numerocenso + "_LAT.jpg";
                String nombreimagenTra = numerocenso + "_TRA.jpg";
                String directorio = "C:/DocumentosDigitalizados/Censos/" + numerocenso + "/";

                File mkdir = new File(directorio);
                if (!mkdir.exists()) {
                    mkdir.mkdirs();
                }

                CenDocumentosDigitalizado cendocumentosdigitalizadoVeh = dao.ConsultarDocumentoDigitalizadoByIdCensoNombre(idcenso, nombreimagenVeh);
                if (cendocumentosdigitalizadoVeh == null) {

                    /* definimos la URL de la cual vamos a leer */
                    String rutaRemotaVeh = "http://www.lagit.com.co/censo_sap/censos/" + nombreimagenVeh;

                    URL intlLogoURLVeh = new URL(rutaRemotaVeh);

                    if (intlLogoURLVeh != null) {
                        /* llamamos metodo para que lea de la URL y lo escriba en le fichero pasado */
                        //rutaServidor = rutaServidor + "/" + numeroCenso + "/" + nombreimagenVeh;
                        String imagenveh = directorio + nombreimagenVeh;
                        dao.writeTo(intlLogoURLVeh.openStream(), new FileOutputStream(new File(imagenveh)));

                        CenDocumentosDigitalizado cendocumentosdigitalizado = new CenDocumentosDigitalizado();
                        cendocumentosdigitalizado.setNombre(nombreimagenVeh);
                        cendocumentosdigitalizado.setRuta(imagenveh);
                        cendocumentosdigitalizado.setTipo(1);
                        cendocumentosdigitalizado.setReferencia_id(idcenso);
                        cendocumentosdigitalizado.setObservacion("Imagen cargada desde servidor remoto");
                        cendocumentosdigitalizado.setUsu_id(cenusuario.getId());
                        iddocumento = dao.adicionarDocumentoDigitalizado(cendocumentosdigitalizado);

                    }
                } else {

                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Documento " + nombreimagenVeh + " ya se encuentra Cargado');");
                    out.println("</script>");

                }

                CenDocumentosDigitalizado cendocumentosdigitalizadoImp = dao.ConsultarDocumentoDigitalizadoByIdCensoNombre(idcenso, nombreimagenImp);
                if (cendocumentosdigitalizadoImp == null) {

                    /* definimos la URL de la cual vamos a leer */
                    String rutaRemotaImp = "http://www.lagit.com.co/censo_sap/censos/" + nombreimagenImp;

                    URL intlLogoURLImp = new URL(rutaRemotaImp);

                    if (intlLogoURLImp != null) {

                        /* llamamos metodo para que lea de la URL y lo escriba en le fichero pasado */
                        //rutaServidor = rutaServidor + "/" + numeroCenso + "/" + nombreimagenVeh;
                        String imagenimp = directorio + nombreimagenImp;
                        dao.writeTo(intlLogoURLImp.openStream(), new FileOutputStream(new File(imagenimp)));

                        CenDocumentosDigitalizado cendocumentosdigitalizado = new CenDocumentosDigitalizado();
                        cendocumentosdigitalizado.setNombre(nombreimagenImp);
                        cendocumentosdigitalizado.setRuta(imagenimp);
                        cendocumentosdigitalizado.setTipo(1);
                        cendocumentosdigitalizado.setReferencia_id(idcenso);
                        cendocumentosdigitalizado.setObservacion("Imagen cargada desde servidor remoto");
                        cendocumentosdigitalizado.setUsu_id(cenusuario.getId());
                        iddocumento = dao.adicionarDocumentoDigitalizado(cendocumentosdigitalizado);

                    }
                } else {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Documento " + nombreimagenImp + " ya se encuentra Cargado');");
                    out.println("</script>");
                }

                CenCenso cencenso = daoCenso.ConsultarCensoById(idcenso);
                if (cencenso != null) {
                    if (cencenso.getFecha().getTime() > new java.text.SimpleDateFormat("dd/MM/yyyy").parse("11/03/2018").getTime()) {
                        CenDocumentosDigitalizado cendocumentosdigitalizadoLat = dao.ConsultarDocumentoDigitalizadoByIdCensoNombre(idcenso, nombreimagenLat);
                        if (cendocumentosdigitalizadoLat == null) {

                            /* definimos la URL de la cual vamos a leer */
                            String rutaRemotaLat = "http://www.lagit.com.co/censo_sap/censos/" + nombreimagenLat;

                            URL intlLogoURLLat = new URL(rutaRemotaLat);

                            if (intlLogoURLLat != null) {

                                /* llamamos metodo para que lea de la URL y lo escriba en le fichero pasado */
                                //rutaServidor = rutaServidor + "/" + numeroCenso + "/" + nombreimagenVeh;
                                String imagenlat = directorio + nombreimagenLat;
                                dao.writeTo(intlLogoURLLat.openStream(), new FileOutputStream(new File(imagenlat)));

                                CenDocumentosDigitalizado cendocumentosdigitalizado = new CenDocumentosDigitalizado();
                                cendocumentosdigitalizado.setNombre(nombreimagenLat);
                                cendocumentosdigitalizado.setRuta(imagenlat);
                                cendocumentosdigitalizado.setTipo(1);
                                cendocumentosdigitalizado.setReferencia_id(idcenso);
                                cendocumentosdigitalizado.setObservacion("Imagen cargada desde servidor remoto");
                                cendocumentosdigitalizado.setUsu_id(cenusuario.getId());
                                iddocumento = dao.adicionarDocumentoDigitalizado(cendocumentosdigitalizado);

                            }
                        } else {
                            out.println("<script type=\"text/javascript\">");
                            out.println("alert('Documento " + nombreimagenLat + " ya se encuentra Cargado');");
                            out.println("</script>");
                        }

                        CenDocumentosDigitalizado cendocumentosdigitalizadoTra = dao.ConsultarDocumentoDigitalizadoByIdCensoNombre(idcenso, nombreimagenTra);
                        if (cendocumentosdigitalizadoTra == null) {

                            /* definimos la URL de la cual vamos a leer */
                            String rutaRemotaTra = "http://www.lagit.com.co/censo_sap/censos/" + nombreimagenTra;

                            URL intlLogoURLTra = new URL(rutaRemotaTra);

                            if (intlLogoURLTra != null) {

                                /* llamamos metodo para que lea de la URL y lo escriba en le fichero pasado */
                                //rutaServidor = rutaServidor + "/" + numeroCenso + "/" + nombreimagenVeh;
                                String imagentra = directorio + nombreimagenTra;
                                dao.writeTo(intlLogoURLTra.openStream(), new FileOutputStream(new File(imagentra)));

                                CenDocumentosDigitalizado cendocumentosdigitalizado = new CenDocumentosDigitalizado();
                                cendocumentosdigitalizado.setNombre(nombreimagenTra);
                                cendocumentosdigitalizado.setRuta(imagentra);
                                cendocumentosdigitalizado.setTipo(1);
                                cendocumentosdigitalizado.setReferencia_id(idcenso);
                                cendocumentosdigitalizado.setObservacion("Imagen cargada desde servidor remoto");
                                cendocumentosdigitalizado.setUsu_id(cenusuario.getId());
                                iddocumento = dao.adicionarDocumentoDigitalizado(cendocumentosdigitalizado);

                            }
                        } else {
                            out.println("<script type=\"text/javascript\">");
                            out.println("alert('Documento " + nombreimagenTra + " ya se encuentra Cargado');");
                            out.println("</script>");
                        }
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Direccion mal formada');");
            out.println("location='jsp/Documentos/ConsultarDocumento.jsp';");
            out.println("</script>");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('No se encontraron documentos para este numero de censo');");
            out.println("location='jsp/Documentos/ConsultarDocumento.jsp';");
            out.println("</script>");

        } catch (IOException e) {
            e.printStackTrace();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('No se encontraron documentos para este numero de censo');");
            out.println("location='jsp/Documentos/ConsultarDocumentoWeb.jsp';");
            out.println("</script>");

        } catch (Exception ex) {
            System.out.println("Error --> " + ex.getMessage());
            out.println("<script type=\"text/javascript\">");
            out.println("alert('No se cargaron los documentos seleccionados');");
            out.println("location='jsp/Documentos/ConsultarDocumento.jsp';");
            out.println("</script>");
            //response.sendRedirect("./paginas/jsp/visor.jsp?opcion=La Imagen No Pudo Ser Guardada Debido A:" + ex.getMessage() + "&idcomparendo=" + referencia_id);
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
