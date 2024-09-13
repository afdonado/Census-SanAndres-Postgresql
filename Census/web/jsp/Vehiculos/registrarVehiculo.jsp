<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Registrar Vehiculo</title>

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
                if (((LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("registrarVehiculo.jsp")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate fecha = LocalDate.now();
                    String fechaActual = fecha.format(formatter);
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
                            <h1 class="h3 mb-2 text-gray-800">Registrar Vehiculo</h1>
                            <form class="user" id="frmregistrarvehiculo">
                                <div class="card shadow mb-4">
                                    <div class="card-header py-3">
                                        <h6 class="m-0 font-weight-bold text-primary">Consulta Información Runt</h6>
                                    </div>
                                    <div class="card-body">
                                        <div class="form-group row">
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Consultar en Runt?</label>
                                                <select class="form-control" id="cmbconsultarunt" name="cmbconsultarunt">
                                                    <option value="S" selected>Si</option>
                                                    <option value="N">No</option>
                                                </select>
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0 consulta-runt">
                                                <label>Placa</label>
                                                <input class="form-control" type="text" id="txtplacarunt" name="txtplacarunt" maxlength="10" style="text-transform: uppercase">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0 consulta-runt">
                                                <label>Documento Propietario</label>
                                                <input class="form-control solo-numeros" type="text" id="txtdocumentorunt" name="txtdocumentorunt" maxlength="20">
                                            </div>
                                            <div id="procesando" style="display:none;">
                                                <p>Consultado los datos en el runt, espere por favor...</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="card shadow mb-4">
                                    <div class="card-header py-3">
                                        <h6 class="m-0 font-weight-bold text-primary">Datos de Identificación</h6>
                                    </div>
                                    <div class="card-body">
                                        <div class="form-group row">
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Placa</label>
                                                <input class="form-control" type="text" id="txtplaca" name="txtplaca" maxlength="10" style="text-transform: uppercase">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Motor<spam style="color: red">(*)</spam></label>
                                                <input class="form-control" type="text" id="txtmotor" name="txtmotor" maxlength="30" style="text-transform: uppercase" required="true">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Chasis<spam style="color: red">(*)</spam></label>
                                                <input class="form-control" type="text" id="txtchasis" name="txtchasis" maxlength="30"style="text-transform: uppercase" required="true">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Serie</label>
                                                <input class="form-control" type="text" id="txtserie" name="txtserie" maxlength="30" style="text-transform: uppercase">
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
                                                <label>Clase Vehículo<spam style="color: red">(*)</spam></label>
                                                <select class="form-control" id="cmbclasevehiculo" name="cmbclasevehiculo" required="true"></select>
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Servicio<spam style="color: red">(*)</spam></label>
                                                <select class="form-control" id="cmbtiposservicio" name="cmbtiposservicio" required="true"></select>
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0" id="tipos-uso">
                                                <label>Tipo de Uso</label>
                                                <select class="form-control" id="cmbtiposuso" name="cmbtiposuso" required="true"></select>
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Color<spam style="color: red">(*)</spam></label>
                                                <input class="form-control" id="txtcolores" name="txtcolores" style="text-transform: uppercase">
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Marca<spam style="color: red">(*)</spam></label>
                                                <input class="form-control" id="txtmarcas" name="txtmarcas" style="text-transform: uppercase">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Linea<spam style="color: red">(*)</spam></label>
                                                <input class="form-control" id="txtlineas" name="txtlineas" style="text-transform: uppercase">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Modelo<spam style="color: red">(*)</spam></label>
                                                <input class="form-control solo-numeros" type="text" id="txtmodelo" name="txtmodelo" maxlength="4" required>
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Transformado<spam style="color: red">(*)</spam></label>
                                                <select class="form-control" id="cmbtransformado" name="cmbtransformado">
                                                    <option value="S">Si</option>
                                                    <option value="N" selected>No</option>
                                                </select>
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
                                                <label>Registrado en Runt<spam style="color: red">(*)</spam></label>
                                                <select class="form-control" id="cmbrunt" name="cmbrunt">
                                                    <option value="S" selected>Si</option>
                                                    <option value="N">No</option>
                                                </select>
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0 matricula">
                                                <label>No. Licencia Transito</label>
                                                <input class="form-control" type="text" id="txtlicenciatransito" name="txtlicenciatransito" value="" style="text-transform: uppercase"/>
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0 matricula">
                                                <label>Fecha Matricula</label>
                                                <input class="form-control" type="text" id="txtfechamatricula" name="txtfechamatricula" readonly="true" value="<%=fechaActual%>">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <input type="hidden" class="form-control" id="cmbpaismatricula" name="cmbpaismatricula" required="true" value="18">
                                        <div class="col-sm-3 mb-3 mb-sm-0 matricula" id="departamento-matricula">
                                            <label>Departamento Matricula</label>
                                            <select class="form-control" id="cmbdepartamentomatricula" name="cmbdepartamentomatricula" required="true"></select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0 matricula">
                                            <label>Municipio Matricula</label>
                                            <select class="form-control" id="cmbmunicipiomatricula" name="cmbmunicipiomatricula" required="true"></select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card shadow mb-4 datos-importancion">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Datos Importación</h6>
                                </div>
                                <div class="card-body">
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0" id="tipos-importacion">
                                            <label>Tipo Importación</label>
                                            <select class="form-control" id="cmbtiposimportacion" name="cmbtiposimportacion" required="true"></select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0 importacion">
                                            <label>Documento Importación</label>
                                            <input class="form-control" type="text" id="txtdocumentoimportacion" name="txtdocumentoimportacion" maxlength="80" style="text-transform: uppercase">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0 importacion">
                                            <label>Fecha Importación</label>
                                            <input class="form-control" type="text" id="txtfechaimportacion" name="txtfechaimportacion" readonly="true" value="<%=fechaActual%>"/>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0 importacion">
                                            <label>Pais Importación</label>
                                            <select class="form-control" id="cmbpaisimportacion" name="cmbpaisimportacion"></select>
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
                                            <label>SOAT<spam style="color: red">(*)</spam></label>
                                            <select class="form-control" id="cmbsoat" name="cmbsoat">
                                                <option value="S" selected>Si</option>
                                                <option value="N">No</option>
                                            </select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0" id="soatcontenedor">
                                            <label>Fecha Venc. Soat</label>
                                            <input class="form-control" type="text" id="txtfechavsoat" name="txtfechavsoat" readonly="true" value="<%=fechaActual%>"/>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Tecnomecanica<spam style="color: red">(*)</spam></label>
                                            <select class="form-control" id="cmbtecnomecanica" name="cmbtecnomecanica">
                                                <option value="S" selected>Si</option>
                                                <option value="N">No</option>
                                            </select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0" id="tecnomecanicacontenedor">
                                            <label>Fecha Venc. Tecnomecanica</label>
                                            <input class="form-control" type="text" id="txtfechavtecnomecanica" name="txtfechavtecnomecanica" readonly="true" value="<%=fechaActual%>"/>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="card shadow mb-4">
                                <div class="card-header py-3 d-flex justify-content-between align-items-center">
                                    <h6 class="m-0 font-weight-bold text-primary">Personas Asociadas</h6>
                                    <div class="d-flex">
                                        <a class="btn btn-primary" id="agregar-campos">Agregar</a>
                                        <a class="btn btn-danger ml-1" id="quitar-campos">Quitar</a>
                                    </div>
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
                                        <div id="contenedor1" class="form-group row">
                                            <div class="col-sm-2 mb-3 mb-sm-0" id="tipos-persona1">
                                                <select class="form-control" id="cmbtipospersona1" name="cmbtipospersona1" required="true"></select>
                                            </div>
                                            <div class="col-sm-2 mb-3 mb-sm-0" id="tipos-documento1">
                                                <select class="form-control" id="cmbtiposdocumento1" name="cmbtiposdocumento1" required="true"></select>
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <input class="form-control solo-numeros" type="text" id="txtdocumento1" name="txtdocumento1" maxlength="20" required="true">
                                            </div>
                                            <div class="col-sm-4 mb-3 mb-sm-0">
                                                <input class="form-control" type="text" id="txtnombre1" name="txtnombre1" readonly="true">
                                            </div>
                                            <input type="hidden" id="idpersona1" name="idpersona1">
                                        </div>
                                    </div>

                                </div>
                                <input type="hidden" id="txtcantidadpersonas" name="txtcantidadpersonas" value="1">
                            </div>
                            <div class="row">
                                <div class="form-group col-xs-6 col-sm-2 col-md-2">
                                    <button type="button" class="btn btn-lg btn-success btn-block" id="btnguardar" name="btnguardar" >Guardar</button>
                                </div>
                                <div class="form-group col-xs-6 col-sm-2 col-md-2">
                                    <button type="button" class="btn btn-lg btn-danger btn-block" id="btnvolver" name="btnvolver">Volver</button>
                                </div>
                            </div>
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

        <script src="${pageContext.request.contextPath}/template/vendor/jquery-ui-1.12.1.Redmond/jquery-ui.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/template/vendor/jquery/calendario_es.js" type="text/javascript"></script>

        <script src="${pageContext.request.contextPath}/scripts/registrarVehiculo.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/scripts/vehiculos.js" type="text/javascript"></script>     

        <script src="${pageContext.request.contextPath}/scripts/persona_vehiculo.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/scripts/parametros.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/scripts/validacionesCampos.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/scripts/fechas.js" type="text/javascript"></script>

    </body>
</html>