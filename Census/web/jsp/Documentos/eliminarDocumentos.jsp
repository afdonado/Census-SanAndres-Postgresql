<%@page import="com.censo.modelo.dao.DocumentoDigitalizadoDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.awt.RenderingHints"%>
<%@page import="java.awt.AlphaComposite"%>
<%@page import="java.awt.Graphics2D"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="java.io.ByteArrayInputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="com.censo.modelo.persistencia.CenDocumentosDigitalizado"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page import= "java.io.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Visualizar Documentos Censo</title>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("ConsultarDocumentos.jsp")) {
                    if (request.getParameter("iddocumento") != null) {
                        
                        DocumentoDigitalizadoDao documentoDigitalizadoDao = new DocumentoDigitalizadoDao();
                        
                        long iddocumento = Long.parseLong(request.getParameter("iddocumento"));
                        CenDocumentosDigitalizado cendocumentosdigitalizado = documentoDigitalizadoDao.ConsultarDocumentoDigitalizadoById(iddocumento);
                        long idcenso = cendocumentosdigitalizado.getReferencia_id();
                        String ruta = "";

                        if (cendocumentosdigitalizado.getRuta().startsWith("..")) {
                            ruta = cendocumentosdigitalizado.getRuta().substring(3);
                            ruta = application.getRealPath(ruta);
                        } else {
                            ruta = cendocumentosdigitalizado.getRuta();
                        }

                        File archivo = new File(""+ruta.replace('/', '\\'));
                        if (archivo.delete()) {
                            System.out.println("Documento borrado");
                            documentoDigitalizadoDao.conectar().setAutoCommit(false);
                            documentoDigitalizadoDao.eliminarDocumentoById(iddocumento);
                            documentoDigitalizadoDao.conectar().commit();
                            %>
                            <script type="text/javascript">
                            alert('Documento borrado correctamente');
                            location="ListarDocumentos.jsp?idcenso=<%=idcenso%>";
                            </script>
                            <%
                        } else {
%>
                            <script type="text/javascript">
                            alert('Documento no borrado');
                            location="ListarDocumentos.jsp?idcenso=<%=idcenso%>";
                            </script>
<%
                        }

                    } else {
        %>
        <script type="text/javascript">
            alert("Digite el numero del censo");
        </script>
        <%
            }
        } else {
        %>
        <script type="text/javascript">
            alert("Su usuario no tiene permiso para acceder a esta pagina");
            window.parent.location.href = "../Inicio.jsp";
        </script>
        <%
            }
        } else {
        %>
        <script type="text/javascript">
            alert("Su sesion a terminado");
            document.location.href = "../../cerrarSesion";
        </script>
        <%
            }
        %>
    </body>
</html>