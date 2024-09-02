<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Listar Verificacione</title>

        <!-- Custom fonts for this template-->
        <link href="../../template/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="../../template/css/fonts-google.css" rel="stylesheet" type="text/css"/>

        <!-- Custom styles for this template-->
        <link href="../../template/css/sb-admin-2.min.css" rel="stylesheet">

        <!-- Custom styles for this page -->
        <link href="../../template/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("listarVerificaciones.jsp")) {
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
                            <h1 class="h3 mb-2 text-gray-800">Verificaciones</h1>

                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Lista de Verificaciones</h6>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                            <thead>
                                                <tr>
                                                    <th>Numero</th>
                                                    <th>Fecha</th>
                                                    <th>Hora</th>
                                                    <th>Punto Atención</th>
                                                    <th>Estado</th>
                                                    <th>Usuario</th>
                                                    <th>PDF</th>
                                                    <th>Foto</th>
                                                    <th>Fecha Registro</th>
                                                    <th>Fecha Reg. Verifi.</th>
                                                    <th>Estado Verificacion</th>
                                                    <th>Ver</th>
                                                    <th>Editar</th>
                                                </tr>
                                            </thead>
                                            <tfoot>
                                                <tr>
                                                    <th>Numero</th>
                                                    <th>Fecha</th>
                                                    <th>Hora</th>
                                                    <th>Punto Atención</th>
                                                    <th>Estado</th>
                                                    <th>Usuario</th>
                                                    <th>PDF</th>
                                                    <th>Foto</th>
                                                    <th>Fecha Registro</th>
                                                    <th>Fecha Reg. Verifi.</th>
                                                    <th>Estado Verificacion</th>
                                                    <th>Ver</th>
                                                    <th>Editar</th>
                                                </tr>
                                            </tfoot>
                                            <tbody id="lista-verificaciones"></tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
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
            window.parent.location.href = "../../dashboard";
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
        <script src="../../template/vendor/jquery/jquery.min.js"></script>
        <script src="../../template/vendor/bootstrap/js/bootstrap.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="../../template/vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="../../template/js/sb-admin-2.min.js"></script>

        <!-- Page level plugins -->
        <script src="../../template/vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="../../template/vendor/datatables/dataTables.bootstrap4.min.js"></script>

        <!-- Page level custom scripts -->
        <script src="../../template/js/demo/datatables-demo.js"></script>

        <script src="../../scripts/listarVerificaciones.js" type="text/javascript"></script>

    </body>
</html>
