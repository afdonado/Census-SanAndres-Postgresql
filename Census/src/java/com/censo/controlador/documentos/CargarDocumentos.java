package com.censo.controlador.documentos;

import com.censo.modelo.dao.DocumentoDigitalizadoDao;
import com.censo.modelo.persistencia.CenDocumentosDigitalizado;
import com.censo.modelo.persistencia.CenUsuario;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "CargarDocumentos", urlPatterns = "/cargarDocumentos")
public class CargarDocumentos extends HttpServlet {

    protected boolean processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload sfu = new ServletFileUpload(factory);

            if (!ServletFileUpload.isMultipartContent(request)) {
                return false;
            }

            if (request.getParameter("idcenso") != null && request.getParameter("numero") != null) {

                DocumentoDigitalizadoDao documentoDigitalizadoDao = new DocumentoDigitalizadoDao();
                conex = documentoDigitalizadoDao.conectar();

                CenUsuario cenusuario = (CenUsuario) request.getSession().getAttribute("usuario");

                long idcenso = Long.parseLong(request.getParameter("idcenso"));
                String numerocenso = request.getParameter("numero");
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
                        String ruta = "C:/DocumentosDigitalizados/Censos/" + numerocenso + "/" + file.getName();

                        File mkdir = new File(directorio);
                        if (!mkdir.exists()) {
                            mkdir.mkdirs();
                        }

                        File saveTo = new File(ruta);
                        if (!saveTo.exists()) {
                            file.write(saveTo);

                            conex.setAutoCommit(false);

                            CenDocumentosDigitalizado cendocumentosdigitalizado = new CenDocumentosDigitalizado();
                            cendocumentosdigitalizado.setNombre(file.getName());
                            cendocumentosdigitalizado.setRuta(ruta);
                            cendocumentosdigitalizado.setTipo(tipo);
                            cendocumentosdigitalizado.setReferencia_id(idcenso);
                            cendocumentosdigitalizado.setObservacion("Imagen cargada desde cargar documentos");
                            cendocumentosdigitalizado.setUsu_id(cenusuario.getId());
                            documentoDigitalizadoDao.adicionarDocumentoDigitalizado(conex, cendocumentosdigitalizado);

                            conex.commit();

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
        } catch (Exception e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            out.println("<script type=\"text/javascript\">");
            out.println("alert('No se cargaron los documentos seleccionados');");
            out.println("location='jsp/Documentos/CargarDocumentos.jsp';");
            out.println("</script>");
            e.printStackTrace();
            System.out.println("Error Cargar Documentos --> " + e.getMessage());
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
        return false;
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
