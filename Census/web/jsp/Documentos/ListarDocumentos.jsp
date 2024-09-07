<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.censo.modelo.dao.DocumentoDigitalizadoDao"%>
<%@page import="com.censo.modelo.persistencia.CenDocumentosDigitalizado"%>
<%@page import="com.censo.modelo.persistencia.CenCenso"%>
<%@page import="java.awt.RenderingHints"%>
<%@page import="java.awt.AlphaComposite"%>
<%@page import="java.awt.Graphics2D"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="java.io.ByteArrayInputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Listar Documentos Censo</title>

        <!-- Custom fonts for this template-->
        <link href="${pageContext.request.contextPath}/template/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/template/css/fonts-google.css" rel="stylesheet" type="text/css"/>

        <!-- Custom styles for this template-->
        <link href="${pageContext.request.contextPath}/template/css/sb-admin-2.min.css" rel="stylesheet">

        <!-- Custom styles for this page -->
        <link href="${pageContext.request.contextPath}/template/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

        <!--<meta name="viewport" content="width=device-width, initial-scale=1">-->

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
    <body id="page-top">
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("consultarDocumentos.jsp")) {
        %>
        <div id="wrapper">
            <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
                <jsp:include page="/jsp/Menu.jsp"></jsp:include>
                </ul>

                <div id="content-wrapper" class="d-flex flex-column">
                    <div id="content">

                        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
                        <jsp:include page="/jsp/Header.jsp"></jsp:include>
                        </nav>

                        <div class="container-fluid">
                            <div id="titulo"></div>
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <div id="imagenes"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <footer class="sticky-footer bg-white">
                    <jsp:include page="/jsp/Footer.jsp"></jsp:include>
                    </footer>
                </div>
            </div>
            <a class="scroll-to-top rounded" href="#page-top">
                <i class="fas fa-angle-up"></i>
            </a>
        <%
        } else {
        %>
        <script type="text/javascript">
            alert("Su usuario no tiene permiso para acceder a esta pagina");
            window.parent.location.href = "../dashboard";
        </script>
        <%
            }
        } else {
        %>
        <script type="text/javascript">
            alert("Su sesion a terminado");
            document.location.href = "../../index.jsp";
        </script>
        <%
            }
        %>

        <!-- Bootstrap core JavaScript-->
        <script src="${pageContext.request.contextPath}/template/vendor/jquery/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/template/vendor/bootstrap/js/bootstrap.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="${pageContext.request.contextPath}/template/vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="${pageContext.request.contextPath}/template/js/sb-admin-2.min.js"></script>

        <!--<script src="js/jspdf.min.js"></script>-->

    </body>
</html>