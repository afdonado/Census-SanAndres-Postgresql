<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Census - Inicio</title>

        <!-- Custom fonts for this template-->
        <link href="template/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="template/css/fonts-google.css" rel="stylesheet" type="text/css"/>

        <!-- Custom styles for this template-->
        <link href="template/css/sb-admin-2.min.css" rel="stylesheet">

    </head>
    <body id="page-top">
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("Inicio.jsp")) {
                    try {
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
                            <jsp:include page="/jsp/Info.jsp"></jsp:include>
                        </div>

                        <footer class="sticky-footer bg-white">
                        <jsp:include page="/jsp/Footer.jsp"></jsp:include>
                        </footer>
                    </div>
                </div>
            </div>
        <%
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
        %>
        <script type="text/javascript">
            alert("Su usuario no tiene permiso para acceder a esta pagina");
            document.location.href = "dashboard";
        </script>
        <%
            }
        } else {
        %>
        <script type="text/javascript">
            alert("Su sesion a terminado");
            document.location.href = "index.jsp";
        </script>
        <%
            }
        %>

        <!-- Bootstrap core JavaScript-->
        <script src="template/vendor/jquery/jquery.min.js"></script>
        <script src="template/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="template/vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="template/js/sb-admin-2.min.js"></script>
      
        <script src="template/vendor/Highcharts-6.0.4/code/highcharts.js" type="text/javascript"></script>
        <script src="template/vendor/Highcharts-6.0.4/code/highcharts-3d.js" type="text/javascript"></script>
        <script src="template/vendor/Highcharts-6.0.4/code/modules/exporting.js" type="text/javascript"></script>
        <script src="scripts/estadisticas.js" type="text/javascript"></script>
        
    </body>
</html>
