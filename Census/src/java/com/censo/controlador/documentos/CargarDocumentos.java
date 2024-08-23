package com.censo.controlador.documentos;

import com.censo.modelo.dao.DocumentoDigitalizadoDao;
import com.censo.modelo.persistencia.CenDocumentosDigitalizado;
import com.censo.modelo.persistencia.CenUsuario;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class CargarDocumentos extends HttpServlet {

    protected boolean processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            DocumentoDigitalizadoDao dao = new DocumentoDigitalizadoDao();
            CenUsuario cenusuario = (CenUsuario) request.getSession().getAttribute("usuario");
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload sfu = new ServletFileUpload(factory);

            if (!ServletFileUpload.isMultipartContent(request)) {
                return false;
            }

            long idcenso = 0;
            String numerocenso = "";
            String ruta = "";

            if (request.getParameter("idcenso") != null && request.getParameter("numero") != null) {
                idcenso = Long.parseLong(request.getParameter("idcenso"));
                numerocenso = request.getParameter("numero");
                List items = sfu.parseRequest(request);

                int cant = 0;

                cant = items.size() - 1;

                for (int i = 0; i <= cant; i++) {
                    FileItem file = (FileItem) items.get(i);
                    String nombre = (String) file.getName();

                    int tipo = 0;
                    String format = nombre.substring(nombre.indexOf("."));
                    if ((format.equalsIgnoreCase(".jpg")) || (format.equalsIgnoreCase(".png")) || (format.equalsIgnoreCase(".png")) || format.equalsIgnoreCase(".pdf")) {
                        if (format.equalsIgnoreCase(".jpg")) {
                            tipo = 1;
                        } else if (format.equalsIgnoreCase(".png")) {
                            tipo = 2;
                        } else if (format.equalsIgnoreCase(".pdf")) {
                            tipo = 3;
                        }

                        String directorio = "C:/DocumentosDigitalizados/Censos/" + numerocenso;
                        ruta = "C:/DocumentosDigitalizados/Censos/" + numerocenso + "/" + file.getName();

                        File mkdir = new File(directorio);
                        if (!mkdir.exists()) {
                            mkdir.mkdirs();
                        }

                        File saveTo = new File(ruta);
                        if (!saveTo.exists()) {
                            file.write(saveTo);

                            dao.conectar().setAutoCommit(false);

                            CenDocumentosDigitalizado cendocumentosdigitalizado = new CenDocumentosDigitalizado();
                            cendocumentosdigitalizado.setNombre(file.getName());
                            cendocumentosdigitalizado.setRuta(ruta);
                            cendocumentosdigitalizado.setTipo(tipo);
                            cendocumentosdigitalizado.setReferencia_id(idcenso);
                            cendocumentosdigitalizado.setObservacion("Imagen cargada desde cargar documentos");
                            cendocumentosdigitalizado.setUsu_id(cenusuario.getId());
                            dao.adicionarDocumentoDigitalizado(cendocumentosdigitalizado);
                            
                            dao.conectar().commit();
                            
                            out.println("<script type=\"text/javascript\">");
                            out.println("alert('Documentos Cargados Correctamente');");
                            out.println("location='jsp/Documentos/ListarDocumentos.jsp?idcenso=" + idcenso + "';");
                            out.println("</script>");
                        } else {
                            out.println("<script type=\"text/javascript\">");
                            out.println("alert('Uno o varios documentos seleccionados ya se encuentran registrados');");
                            out.println("</script>");
                            return false;
                        }
                    } else {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('La extensión de los documentos seleccionados no es permitida');");
                        out.println("</script>");
                        return false;
                    }
                }
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Error --> " + ex.getMessage());
            out.println("<script type=\"text/javascript\">");
            out.println("alert('No se cargaron los documentos seleccionados');");
            out.println("location='jsp/Documentos/CargarDocumentos.jsp';");
            out.println("</script>");
        }
        return false;
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
