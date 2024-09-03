<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.LinkedList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <title>Datos Verificacion</title>

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
                if (((LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("listarVerificacion.jsp")) {
        %>
        <div class="modal fade" id="registrarverificacion" name="registrarverificacion" role="dialog" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">Verificacion Censo</h4>
                    </div>
                    <div class="modal-body">
                        <iframe frameborder="0" style="height: 450px;width: 100%;" ></iframe>
                    </div>
                </div>
            </div>
        </div>


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
                            <h1 class="h3 mb-2 text-gray-800">Verificacion</h1>

                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Datos censo</h6>
                                </div>
                                <div class="card-body">
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label># Censo</label>
                                            <input class="form-control" type="text" id="txtnumerocenso" name="txtnumerocenso" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Fecha Censo</label>
                                            <input class="form-control" type="text" id="txtfechacenso" name="txtfechacenso" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Punto de Atención</label>
                                            <input class="form-control" id="txtpuntoatencion" name="txtpuntoatencion" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Usuario Registró</label>
                                            <input class="form-control" id="txtusuarioregistro" name="txtusuarioregistro" readonly="true">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Identificación del Vehículo</h6>
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
                                    <div id="personas-censo">

                                    </div>
                                </div>
                            </div>
                            <div class="card shadow mb-4">
                                <div class="card-header py-3 d-flex justify-content-between align-items-center">
                                    <h6 class="m-0 font-weight-bold text-primary">Datos persona presentó vehículo</h6>
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
                                    <div class="form-group row">
                                        <div class="col-sm-2 mb-3 mb-sm-0">
                                            <input class="form-control" id="txttipopersonapresento" name="txttipopersonapresento" readonly="true">
                                        </div>
                                        <div class="col-sm-2 mb-3 mb-sm-0">
                                            <input class="form-control" id="txttipodocumentopresento" name="txttipodocumentopresento" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <input class="form-control" type="text" id="txtdocumentopresento" name="txtdocumentopresento" readonly="true">
                                        </div>
                                        <div class="col-sm-5 mb-3 mb-sm-0">
                                            <input class="form-control" type="text" id="txtnombrepresento" name="txtnombrepresento" readonly="true">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Identificación del Vehículo</h6>
                                </div>
                                <div class="card-body">
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Runt</label>
                                            <input class="form-control" type="text" id="txtrunt" name="txtrunt" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Documentos</label>
                                            <input class="form-control" type="text" id="txtdocumentos" name="txtdocumentos" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Fotos</label>
                                            <input class="form-control" type="text" id="txtfotos" name="txtfotos" readonly="true">
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
                                        <textarea id="txtobservaciones" name="txtobservaciones" maxlength="300" style="width: 100%" readonly="true"></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                            <%
                                if (((java.util.LinkedList) session.getAttribute("permisosUsuario")).contains("registrarVerificacion.jsp")) {
                            %>
                            <div class="form-group col-xs-6 col-sm-2 col-md-2">
                                <button type="button" class="btn btn-lg btn-success btn-block btneditar" id="btneditar" name="btneditar">Editar</button>
                            </div>
                            <%
                                }
                            %>
                            <div class="form-group col-xs-6 col-sm-2 col-md-2">
                                <button type="button" class="btn btn-lg btn-success btn-block" id="btnverificar" name="btnverificar">Verificar</button><!--onclick="ImprimirCensoById(idcenso)"-->
                            </div>
                            <div class="form-group col-xs-6 col-sm-2 col-md-2">
                                <button type="button" class="btn btn-lg btn-danger btn-block" id="btnvolver" name="btnvolver">Volver</button>
                            </div>
                        </div>
                        <!--
                                                    <div id="page-wrapper">
                                                        <div class="row">
                                                            <iframe src="../Documentos/ListarDocumentos.jsp?idcenso=idcenso" style="width: 100%; min-height: 1500px;" transparency="transparency" frameborder="0" ></iframe>
                                                    </div>
                                                </div>
                        -->
                    </div>
                </div>
                <footer class="sticky-footer bg-white">
                    <jsp:include page="/jsp/Footer.jsp"></jsp:include>
                    </footer>
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

        <script src="${pageContext.request.contextPath}/scripts/verVerificacion.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/scripts/verificaciones.js" type="text/javascript"></script>

    </body>
</html>