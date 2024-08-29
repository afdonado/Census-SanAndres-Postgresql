<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Consultar vehiculo</title>

        <!-- Custom fonts for this template-->
        <link href="../../template/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="../../template/css/fonts-google.css" rel="stylesheet" type="text/css"/>

        <!-- Custom styles for this template-->
        <link href="../../template/css/sb-admin-2.min.css" rel="stylesheet">

        <!-- Custom styles for this page -->
        <link href="../../template/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

        <style>
            #tabs {
                width: 100%; /* Ajusta al 100% del ancho del contenedor padre */
                height: auto; /* Ajusta automáticamente al contenido */
                min-height: 400px; /* Ajusta esta medida si es necesario */
            }

            #tabs .ui-tabs-panel {
                padding: 10px; /* Espaciado interno para mejor visualización */
            }

        </style>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("consultarVehiculo.jsp")) {

                    Date fechaActual = new Date(new java.util.Date().getTime());
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
                            <h1 class="h3 mb-2 text-gray-800">Consultar Vehiculo</h1>
                            <form class="user" id="frmconsultarvehiculo" action="">
                                <div class="card shadow mb-4">
                                    <div class="card shadow mb-4">
                                        <div class="card-body">
                                            <div class="form-group row">
                                                <div id="tabs">
                                                    <ul>
                                                        <li><a href="#tabs-1">Referencia</a></li>
                                                        <li><a href="#tabs-2">Persona</a></li>
                                                        <li><a href="#tabs-3">Fecha Registro</a></li>
                                                        <li><a href="#tabs-4">Reporte General</a></li>
                                                    </ul>
                                                    <div id="tabs-1">
                                                        <div class="form-group row">
                                                            <div class="col-sm-3 mb-3 mb-sm-0" id="tipos-referencia"></div>
                                                            <div class="col-sm-3">
                                                                <label>Referencia Vehiculo</label>
                                                                <input class="form-control" type="text" id="txtreferencia" name="txtreferencia" placeholder="" required="true" style="text-transform: uppercase">
                                                            </div>
                                                            <div class="col-sm-3">
                                                                <label></label>
                                                                <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarListadoVehiculo(1)" id="btnconsultar" name="btnconsultar" >Consultar</button>
                                                            </div>
                                                            <div class="col-sm-3">
                                                                <label></label>
                                                                <button type="button" class="btn btn-md btn-success btn-block" onclick="generarReporteVehiculo(1)" style="cursor: pointer;">Generar Reporte</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div id="tabs-2">
                                                        <div class="form-group row">
                                                            <div class="col-sm-6 mb-3 mb-sm-0" id="tipos-documento"></div>
                                                            <div class="col-sm-6 mb-3 mb-sm-0">
                                                                <label>Documento</label>
                                                                <input class="form-control" type="number" id="txtdocumento" name="txtdocumento" maxlength="20" required="true">
                                                            </div>
                                                            <div class="col-sm-6 mb-3 mb-sm-0">
                                                                <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarListadoVehiculo(2)" id="btnconsultar" name="btnconsultar" >Consultar</button>
                                                            </div>
                                                            <div class="col-sm-6 mb-3 mb-sm-0">
                                                                <button type="button" class="btn btn-md btn-success btn-block" onclick="generarReporteVehiculo(2)" style="cursor: pointer;">Generar Reporte</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div id="tabs-3">
                                                        <div class="form-group row">
                                                            <div class="col-sm-6 mb-3 mb-sm-0">
                                                                <label>Fecha Inicial</label>
                                                                <input class="form-control" type="text" id="txtfechaRegini" name="txtfechaRegini" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                                        </div>
                                                        <div class="col-sm-6 mb-3 mb-sm-0">
                                                            <label>Fecha Final</label>
                                                            <input class="form-control" type="text" id="txtfechaRegfin" name="txtfechaRegfin" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                                        </div>
                                                        <div class="col-sm-6 mb-3 mb-sm-0">
                                                            <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarListadoVehiculo(3)" id="btnconsultar" name="btnconsultar" >Consultar</button>
                                                        </div>
                                                        <div class="col-sm-6 mb-3 mb-sm-0">
                                                            <button type="button" class="btn btn-md btn-success btn-block" onclick="generarReporteVehiculo(3)" style="cursor: pointer;">Generar Reporte</button>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div id="tabs-4">
                                                    <div class="form-group row">
                                                        <div class="col-sm-6 mb-3 mb-sm-0">
                                                            <button type="button" class="btn btn-md btn-success btn-block" onclick="generarReporteVehiculo(4)" style="cursor: pointer;">Generar Reporte</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <input type="hidden" id="tipoconsulta" name="tipoconsulta" value="1">
                                            <div>
                                                &nbsp;
                                            </div>

                                            <div id="page-wrapper">
                                                <div class="row">
                                                    <iframe src="" style="width: 100%; min-height: 1500px;" transparency="transparency" frameborder="0" ></iframe>
                                                </div>
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
        <%        } else {
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
        <!-- Bootstrap core JavaScript-->
        <script src="../../template/vendor/jquery/jquery.min.js"></script>
        <script src="../../template/vendor/bootstrap/js/bootstrap.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="../../template/vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="../../template/js/sb-admin-2.min.js"></script>

        <link href="../../template/vendor/jquery-ui-1.12.1.Redmond/jquery-ui.css" rel="stylesheet" type="text/css"/>
        <link href="../../template/vendor/jquery/calendario_es.css" rel="stylesheet" type="text/css"/>
        <script src="../../template/vendor/jquery-ui-1.12.1.Redmond/jquery-ui.js" type="text/javascript"></script>
        <script src="../../template/vendor/jquery/calendario_es.js" type="text/javascript"></script>

        <script src="../../scripts/validacionesCampos.js" type="text/javascript"></script>
        <script src="../../scripts/parametros.js" type="text/javascript"></script>
        <script src="../../scripts/vehiculos.js" type="text/javascript"></script>        
        <script src="../../scripts/fechas.js" type="text/javascript"></script>
    </body>
</html>
