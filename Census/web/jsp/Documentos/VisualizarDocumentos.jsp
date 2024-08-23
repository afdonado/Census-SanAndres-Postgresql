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
                        CenDocumentosDigitalizado cendocumentosdigitalizado = new CenDocumentosDigitalizado();
                        String nombre = "jpg";
                        String extension = "";
                        String ruta = "";
                        cendocumentosdigitalizado = documentoDigitalizadoDao.ConsultarDocumentoDigitalizadoById(iddocumento);

                            if (cendocumentosdigitalizado.getRuta().startsWith("..")) {
                                ruta = cendocumentosdigitalizado.getRuta().substring(3);
                                ruta = application.getRealPath(ruta);
                            } else {
                                ruta = cendocumentosdigitalizado.getRuta().replace("/", "\\");
                            }
                            nombre = cendocumentosdigitalizado.getNombre();
                            extension = nombre.substring(nombre.indexOf(".") + 1, nombre.length());
                            FileInputStream archivo = new FileInputStream(ruta);
                            int longitud = archivo.available();
                            byte[] data = new byte[longitud];
                            archivo.read(data, 0, longitud);
                            
                            response.setHeader("Content-disposition", "inline; filename=" + nombre + "");
                            
                            if (extension.toUpperCase().equals("JPG") || extension.toUpperCase().equals("PNG")) {
                                response.setContentType("image/gif");
                            }else{
                                if (extension.toUpperCase().equals("PDF")) {
                                    response.setContentType("application/pdf");
                                }
                            }
                            
                            response.setContentLength(longitud);
                            response.getOutputStream().write(data);

                            archivo.close();

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