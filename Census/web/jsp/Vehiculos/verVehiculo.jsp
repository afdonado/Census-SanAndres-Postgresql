<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.util.LinkedList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Datos Vehiculo</title>

        <!-- Custom fonts for this template-->
        <link href="${pageContext.request.contextPath}/template/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/template/css/fonts-google.css" rel="stylesheet" type="text/css"/>

        <!-- Custom styles for this template-->
        <link href="${pageContext.request.contextPath}/template/css/sb-admin-2.min.css" rel="stylesheet">

        <!-- Custom styles for this page -->
        <link href="${pageContext.request.contextPath}/template/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    </head>
    <body id="page-top">
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("listarVehiculos.jsp")) {
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
                            <h1 class="h3 mb-2 text-gray-800">Datos del Vehiculo</h1>

                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Datos de Identificación</h6>
                                </div>
                                <div class="card-body">
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Placa</label>
                                            <input class="form-control" type="text" id="txtplaca" name="txtplaca" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Motor</label>
                                            <input class="form-control" type="text" id="txtmotor" name="txtmotor" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Chasis</label>
                                            <input class="form-control" type="text" id="txtchasis" name="txtchasis" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Serie</label>
                                            <input class="form-control" type="text" id="txtserie" name="txtserie" readonly="true">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Datos Generales</h6>
                                </div>
                                <div class="card-body">
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Clase</label>
                                            <input class="form-control" type="text" id="txtclase" name="txtclase" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Servicio</label>
                                            <input class="form-control" type="text" id="txtservicio" name="txtservicio" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Tipo de Uso</label>
                                            <input class="form-control" type="text" id="txttipouso" name="txttipouso" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Color</label>
                                            <input class="form-control" type="text" id="txtcolores" name="txtcolores" readonly="true">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Marca</label>
                                            <input class="form-control" id="txtmarcas" name="txtmarcas" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Linea</label>
                                            <input class="form-control" id="txtlineas" name="txtlineas" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Modelo</label>
                                            <input class="form-control" type="text" id="txtmodelo" name="txtmodelo" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Transformado</label>
                                            <input class="form-control" type="text" id="txttransformado" name="txttransformado" readonly="true">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Datos Registro Inicial</h6>
                                </div>
                                <div class="card-body">
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Registrado en Runt</label>
                                            <input class="form-control" type="text" id="txtrunt" name="txtrunt" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0 matricula">
                                            <label>Licencia Transito</label>
                                            <input class="form-control" type="text" id="txtlicenciatransito" name="txtlicenciatransito" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0 matricula">
                                            <label>Fecha Matricula</label>
                                            <input class="form-control" type="text" id="txtfechamatricula" name="txtfechamatricula" readonly="true">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0 matricula">
                                            <label>Pais</label>
                                            <input class="form-control" type="text" id="txtpaismatricula" name="txtpaismatricula" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0 matricula">
                                            <label>Departamento</label>
                                            <input class="form-control" type="text" id="txtdepartamentomatricula" name="txtdepartamentomatricula" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0 matricula">
                                            <label>Municipio</label>
                                            <input class="form-control" type="text" id="txtmunicipiomatricula" name="txtmunicipiomatricula" readonly="true">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Datos Importacion</h6>
                                </div>
                                <div class="card-body">
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Tipo Importacion</label>
                                            <input class="form-control" type="text" id="txttipodocimportacion" name="txttipodocimportacion" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0 importacion">
                                            <label>Documento Importacion</label>
                                            <input class="form-control" type="text" id="txtdocumentoimportacion" name="txtdocumentoimportacion" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0 importacion">
                                            <label>Fecha Importacion</label>
                                            <input class="form-control" type="text" id="txtfechaimportacion" name="txtfechaimportacion" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0 importacion">
                                            <label>Pais Importacion</label>
                                            <input class="form-control" type="text" id="txtpaisimportacion" name="txtpaisimportacion" readonly="true">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Polizas y Certificados</h6>
                                </div>
                                <div class="card-body">
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>SOAT</label>
                                            <input class="form-control" type="text" id="txtsoat" name="txtsoat" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0" id="soatcontenedor">
                                            <label>Fecha Venc. Soat</label>
                                            <input class="form-control" type="text" id="txtfechavsoat" name="txtfechavsoat" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Tecnomecanica</label>
                                            <input class="form-control" type="text" id="txttecnomecanica" name="txttecnomecanica" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0" id="tecnomecanicacontenedor">
                                            <label>Fecha Venc. Tecnomecanica</label>
                                            <input class="form-control" type="text" id="txtfechavtecnomecanica" name="txtfechavtecnomecanica" readonly="true">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card shadow mb-4">
                                <div class="card-header py-3 d-flex justify-content-between align-items-center">
                                    <h6 class="m-0 font-weight-bold text-primary">Personas Asociadas</h6>
                                </div>
                                <div class="card-body">
                                    <div class="form-group row">
                                        <div class="col-sm-2 mb-3 mb-sm-0">
                                            <label>Tipo</label>
                                        </div>
                                        <div class="col-sm-2 mb-3 mb-sm-0">
                                            <label>Tipo Documento</label>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Documento</label>
                                        </div>
                                        <div class="col-sm-5 mb-3 mb-sm-0">
                                            <label>Nombre</label>
                                        </div>
                                    </div>
                                    <div id="personas-vehiculo">

                                    </div>
                                </div>
                            </div>
                            <div class="row">
                            <%
                                if (((java.util.LinkedList) session.getAttribute("permisosUsuario")).contains("modificarVehiculo.jsp")) {
                            %>
                            <div class="form-group col-xs-6 col-sm-2 col-md-2">
                                <button type="button" class="btn btn-lg btn-success btn-block btneditar" id="btneditar" name="btneditar">Editar</button>
                            </div>
                            <%
                                }
                            %>
                            <div class="form-group col-xs-6 col-sm-2 col-md-2">
                                <button type="button" class="btn btn-lg btn-danger btn-block" id="btnvolver" name="btnvolver">Volver</button>
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
            window.parent.location.href = "dashboard";
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

        <script src="${pageContext.request.contextPath}/scripts/verVehiculo.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/scripts/vehiculos.js" type="text/javascript"></script>    

    </body>
</html>