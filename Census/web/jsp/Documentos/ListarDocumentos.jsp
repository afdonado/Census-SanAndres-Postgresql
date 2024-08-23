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
<%@page import="com.censo.modelo.persistencia.CenCenso"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Listar Documentos Censo</title>
        
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="../../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="js/jspdf.min.js"></script>
        
        <style type="text/css">
            .modal-footer{
                border-radius: 8px;
                width: 100%;
                color: black;
                position: absolute;
                z-index: 100;
                background-color: rgba(255, 255, 255, 0.81);
                bottom: 0;
                text-align: center;
                font-size: 21px;
            }

            .imagen{
                width: 100%;
                height: 150px;
            }
            .imagen:hover{
                cursor: pointer;
                width: 100%;
            }
            .cerrar{
                color: darkred;
                border-radius: 6px;
                right: 0;
                font-size: 40px;
                position: absolute;
                z-index: 100;
                padding: 12px;
            }

            .cerrar:hover{
                font-size: 50px;
                cursor: pointer;
            }

        </style>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("ConsultarDocumentos.jsp")) {
                    if (request.getParameter("idcenso") != null) {
                        
                        DocumentoDigitalizadoDao documentoDigitalizadoDao = new DocumentoDigitalizadoDao();
                        
                        long idcenso = Long.parseLong(request.getParameter("idcenso"));
        %>
        <div id="container-fluid">
            <div class="navigation-container">
                <div id="thumbs" class="navigation">
                    <ul class="thumbs noscript">
                        <%
                            List listaDocumentos = new LinkedList();
                            CenDocumentosDigitalizado cendocumentosdigitalizado = new CenDocumentosDigitalizado();
                            String nombre = "jpg";
                            String extension = "jpg";
                            String ruta = "";
                            String b64 = "";
                            listaDocumentos = documentoDigitalizadoDao.ListarDocumentosDigitalizados(idcenso);

                            for (int i = 0; i < listaDocumentos.size(); i++) {
                                cendocumentosdigitalizado = (CenDocumentosDigitalizado) listaDocumentos.get(i);
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

                                if (extension.equals("jpg") || extension.equals("png") || extension.equals("JPG") || extension.equals("PNG")) {

                                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
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
                                    byte[] imageInByteArray = baos.toByteArray();
                                    baos.close();
                                    b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
                        %>
                        <div class="col-lg-2 col-md-2 col-sm-2 form-group">
                            <a href="VisualizarDocumentos.jsp?iddocumento=<%=cendocumentosdigitalizado.getId()%>" target="_blank" >
                                <%=cendocumentosdigitalizado.getNombre()%>
                            <img src="data:image/<%=extension%>;base64,<%=b64%>" class="image-responsive imagen" title="<%=cendocumentosdigitalizado.getNombre()%>" alt="<%=cendocumentosdigitalizado.getNombre()%>">
                            </a>
                            <!--<a href="eliminarDocumentos.jsp?iddocumento=<%=cendocumentosdigitalizado.getId()%>">
                                <img src="../../iconos/eliminar.png" class="image-responsive imagen" title="Eliminar" alt="Eliminar" style="width: 30%; height: 20%;">
                            </a>-->
                        </div>
                        <%
                            } else {
                        %>
                        <div class="col-lg-2 col-md-2 col-sm-2 form-group">
                            <a href="VisualizarDocumentos.jsp?iddocumento=<%=cendocumentosdigitalizado.getId()%>" target="_blank" >
                                <%=cendocumentosdigitalizado.getNombre()%>
                            <img src="../../iconos/pdf.png" class="image-responsive imagen" title="<%=cendocumentosdigitalizado.getNombre()%>" alt="<%=cendocumentosdigitalizado.getNombre()%>">
                            </a>
                            <!--<a href="eliminarDocumentos.jsp?iddocumento=<%=cendocumentosdigitalizado.getId()%>">
                                <img src="../../iconos/eliminar.png" class="image-responsive imagen" title="Eliminar" alt="Eliminar" style="width: 30%; height: 20%;">
                            </a>-->
                        </div>
                        <%
                                }
                            }
                        %>
                    </ul>
                </div>
            </div>
        </div>
        <%
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