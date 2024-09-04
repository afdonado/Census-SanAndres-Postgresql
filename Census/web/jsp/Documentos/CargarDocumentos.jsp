<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.util.LinkedList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Cargar Documentos Censo</title>

        <!-- Custom fonts for this template-->
        <link href="${pageContext.request.contextPath}/template/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/template/css/fonts-google.css" rel="stylesheet" type="text/css"/>

        <!-- Custom styles for this template-->
        <link href="${pageContext.request.contextPath}/template/css/sb-admin-2.min.css" rel="stylesheet">

        <!-- Custom styles for this page -->
        <link href="${pageContext.request.contextPath}/template/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

        <link href="${pageContext.request.contextPath}/fileinput/css/fileinput.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("cargarDocumentos.jsp")) {
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
                            <div class="container-fluid">
                                <h1 class="h3 mb-2 text-gray-800">Cargar Documentos Censo</h1>
                                <form role="form" id="frmcargardocumentos">
                                    <div class="card shadow mb-4">
                                        <div class="card-header py-3">
                                            <h6 class="m-0 font-weight-bold text-primary">Documentos Censo</h6>
                                        </div>
                                        <div class="card-body">
                                            <div class="form-group row">
                                                <div class="col-sm-3 mb-3 mb-sm-0">
                                                    <label>Numero Censo</label>
                                                    <input class="form-control solo-numeros-censo solo-numeros" type="text" id="txtnumerocensocargar" name="txtnumerocensocargar" maxlength="5">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <form role="form" id="frmseleccionardocumentos" enctype="multipart/form-data">
                                    <div class="card shadow mb-4">
                                        <div class="card-header py-3">
                                            <div class="card-body">
                                                <div class="form-group row">
                                                    <div id="cargardocumentos">
                                                        <label>Seleccione los documentos</label>
                                                        <input id="file" name="file" multiple type="file" class="file" onchange="return ValidarImagenes(this.id)" data-allowed-file-extensions='["png", "jpg", "pdf"]'>
                                                    </div>
                                                </div>
                                            </div>                                            
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <footer class="sticky-footer bg-white">
                            <jsp:include page="/jsp/Footer.jsp"></jsp:include>
                            </footer>
                        </div>
                    </div>
                </div>
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

            <script src="${pageContext.request.contextPath}/scripts/documentos.js" type="text/javascript"></script>

            <script src="${pageContext.request.contextPath}/fileinput/js/fileinput.min.js" type="text/javascript"></script>

    </body>
</html>
