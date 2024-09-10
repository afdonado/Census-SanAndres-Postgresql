<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.util.LinkedList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Registrar Usuario</title>

        <!-- Custom fonts for this template-->
        <link href="${pageContext.request.contextPath}/template/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/template/css/fonts-google.css" rel="stylesheet" type="text/css"/>

        <!-- Custom styles for this template-->
        <link href="${pageContext.request.contextPath}/template/css/sb-admin-2.min.css" rel="stylesheet">

        <!-- Custom styles for this page -->
        <link href="${pageContext.request.contextPath}/template/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

        <link href="${pageContext.request.contextPath}/template/vendor/jquery-ui-1.12.1.Redmond/jquery-ui.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/template/vendor/jquery/calendario_es.css" rel="stylesheet" type="text/css"/>

    </head>
    <body id="page-top">
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("modificarUsuario.jsp")) {
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
                            <h1 class="h3 mb-2 text-gray-800">Modificar Usuario</h1>

                            <form role="form" id="frmmodificarusuario" action="../../modificarUsuario">
                                <div class="card shadow mb-4">
                                    <div class="card-header py-3">
                                        <h6 class="m-0 font-weight-bold text-primary">Datos del Usuario</h6>
                                    </div>
                                    <div class="card-body">
                                        <div class="form-group row">
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Nombre</label>
                                                <input class="form-control" id="txtnombre" name="txtnombre" readonly="true" style="text-transform: uppercase">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Tipo Documento</label>
                                                <select class="form-control" id="cmbtiposdocumento" name="cmbtiposdocumento"></select>
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Documento</label>
                                                <input class="form-control solo-numeros" type="text" id="txtdocumento" name="txtdocumento" style="text-transform: uppercase">
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Perfil<spam style="color: red">(*)</spam></label>
                                                <select class="form-control" id="cmbperfiles" name="cmbperfiles" required="true"></select>
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Estado<spam style="color: red">(*)</spam></label>
                                                <select class="form-control" id="cmbestados" name="cmbestados" required="true"></select>
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Fecha Inicio</label>
                                                <input class="form-control" type="text" id="txtfechainicial" name="txtfechainicial" readonly="true">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-xs-3 col-sm-3 col-md-2">
                                        <button type="button" class="btn btn-lg btn-success btn-block" id="btnguardar" name="btnguardar" >Guardar</button>
                                    </div>
                                    <div class="form-group col-xs-4 col-sm-4 col-md-4">
                                        <button type="button" class="btn btn-lg btn-primary btn-block restaurar" id="btnrestaurar" name="btnrestaurar">Restaurar Password</button>
                                    </div>
                                    <div class="form-group col-xs-3 col-sm-3 col-md-2">
                                        <button type="button" class="btn btn-lg btn-danger btn-block" id="btnvolver" name="btnvolver">Volver</button>
                                    </div>
                                </div>
                                <input type="hidden" id="idusuario" name="idusuario">
                            </form>
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

        <script src="${pageContext.request.contextPath}/template/vendor/jquery-ui-1.12.1.Redmond/jquery-ui.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/template/vendor/jquery/calendario_es.js" type="text/javascript"></script>

        <script src="${pageContext.request.contextPath}/scripts/modificarUsuario.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/scripts/usuarios.js" type="text/javascript"></script>

        <script src="${pageContext.request.contextPath}/scripts/validacionesCampos.js" type="text/javascript"></script>

    </body>
</html>