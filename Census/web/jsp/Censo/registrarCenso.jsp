<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Registrar Censo</title>

        <!-- Custom fonts for this template-->
        <link href="${pageContext.request.contextPath}/template/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/template/css/fonts-google.css" rel="stylesheet" type="text/css"/>

        <!-- Custom styles for this template-->
        <link href="${pageContext.request.contextPath}/template/css/sb-admin-2.min.css" rel="stylesheet">

        <!-- Custom styles for this page -->
        <link href="${pageContext.request.contextPath}/template/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("registrarCenso.jsp")) {
                    Date fechaActual = new Date(new Date().getTime());
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
                            <h1 class="h3 mb-2 text-gray-800">Registrar Censo</h1>
                            <form role="form" id="frmregistrarcenso">
                                <div class="card shadow mb-4">
                                    <div class="card-header py-3">
                                        <h6 class="m-0 font-weight-bold text-primary">Datos del censo</h6>
                                    </div>
                                    <div class="card-body">
                                        <div class="form-group row">
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label># Censo(*)</label>
                                                <input class="form-control solo-numeros" type="text" id="txtnumerocenso" name="txtnumerocenso" placeholder="Ingrese solo el numero" maxlength="10" style="text-transform: uppercase" required="true">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Fecha Censo (*)</label>
                                                <input class="form-control" type="text" id="txtfechacenso" name="txtfechacenso" readonly="true" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Punto de Atención(*)</label>
                                            <select class="form-control" id="cmbpuntosatencion" name="cmbpuntosatencion" required="true"></select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Tipo Referencia(*)</label>
                                            <select class="form-control" id="cmbtiposreferencia" name="cmbtiposreferencia" required="true"></select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Referencia Vehículo(*)</label>
                                            <input class="form-control" type="text" id="txtreferencia" name="txtreferencia" maxlength="10" style="text-transform: uppercase" required="true">
                                        </div>
                                    </div>                                       
                                </div>
                            </div>
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Observaciones</h6>
                                </div>
                                <div class="card-body">
                                    <div class="form-group row">
                                        <textarea id="txtobservaciones" name="txtobservaciones" maxlength="300" style="width: 100%" cols="3"></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-xs-6 col-sm-2 col-md-2">
                                    <button type="button" class="btn btn-lg btn-success btn-block" id="btnguardar" name="btnguardar" >Guardar</button>
                                </div>
                                <div class="form-group col-xs-6 col-sm-2 col-md-2">
                                    <button type="button" class="btn btn-lg btn-danger btn-block" id="btnvolver" name="btnvolver">Volver</button>
                                </div>
                            </div>
                            <input type="hidden" id="idpersona" name="idpersona">
                            <input type="hidden" id="idvehiculo" name="idvehiculo">
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

        <link href="${pageContext.request.contextPath}/template/vendor/jquery-ui-1.12.1.Redmond/jquery-ui.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/template/vendor/jquery/calendario_es.css" rel="stylesheet" type="text/css"/>
        <script src="${pageContext.request.contextPath}/template/vendor/jquery-ui-1.12.1.Redmond/jquery-ui.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/template/vendor/jquery/calendario_es.js" type="text/javascript"></script>

        <script src="${pageContext.request.contextPath}/scripts/registrarCenso.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/scripts/censo.js" type="text/javascript"></script>

        <script src="${pageContext.request.contextPath}/scripts/personas.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/scripts/validacionesCampos.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/scripts/fechas.js" type="text/javascript"></script>

        <link href="${pageContext.request.contextPath}/fileinput/css/fileinput.css" rel="stylesheet" type="text/css"/>
        <script src="${pageContext.request.contextPath}/fileinput/js/fileinput.min.js" type="text/javascript"></script>

    </body>
</html>
