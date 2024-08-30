<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.LinkedList"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.util.Date"%>
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
                if (((LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("registrarVehiculo.jsp")) {

                    Date fechaActual = new Date(new Date().getTime());
                    int opcion = 0;
                    int tipoRefencia = 0;
                    String referencia = "";
                    String placa = "", motor = "", chasis = "", serie = "";
                    String readonlyplaca = "";
                    String readonlymotor = "";
                    String readonlychasis = "";
                    String readonlyserie = "";
                    if (request.getParameter("opcion") != null) {
                        opcion = Integer.parseInt(request.getParameter("opcion"));
                    }
        %>
        <div class="modal fade" id="registrarpersona" name="registrarpersona" role="dialog" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">Registrar Persona</h4>
                    </div>
                    <div class="modal-body">
                        <iframe frameborder="0" style="height: 600px;width: 100%;" ></iframe>
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
                        <%
                            if (opcion == 1) {
                        %>
                        <h1 class="h3 mb-2 text-gray-800">Registrar Vehiculo</h1>
                        <%
                            }

                            if (opcion == 2) {
                                tipoRefencia = Integer.parseInt(request.getParameter("tiporeferencia"));
                                referencia = request.getParameter("referencia").toUpperCase().trim();

                                if (tipoRefencia == 1) {
                                    placa = referencia;
                                    readonlyplaca = "readonly";
                                }
                                if (tipoRefencia == 2) {
                                    motor = referencia;
                                    readonlymotor = "readonly";
                                }
                                if (tipoRefencia == 3) {
                                    chasis = referencia;
                                    readonlychasis = "readonly";
                                }
                                if (tipoRefencia == 4) {
                                    serie = referencia;
                                    readonlyserie = "readonly";
                                }
                            }

                        %>
                        <form class="user" id="frmregistrarvehiculo" action="../../registrarVehiculo">
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Datos de Identificación</h6>
                                </div>
                                <div class="card-body">
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Placa</label>
                                            <input class="form-control" type="text" id="txtplaca" name="txtplaca" maxlength="10" style="text-transform: uppercase" value="<%=placa%>" <%=readonlyplaca%> required>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Motor (*)</label>
                                            <input class="form-control" type="text" id="txtmotor" name="txtmotor" maxlength="30" style="text-transform: uppercase" value="<%=motor%>" <%=readonlymotor%> required>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Chasis (*)</label>
                                            <input class="form-control" type="text" id="txtchasis" name="txtchasis" maxlength="30"style="text-transform: uppercase" value="<%=chasis%>" <%=readonlychasis%> required>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Serie</label>
                                            <input class="form-control" type="text" id="txtserie" name="txtserie" maxlength="30" style="text-transform: uppercase" value="<%=serie%>" <%=readonlyserie%> required>
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
                                            <label>Clase Vehículo(*)</label>
                                            <select class="form-control" id="cmbclasevehiculo" name="cmbclasevehiculo" required="true"></select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Servicio(*)</label>
                                            <select class="form-control" id="cmbtiposservicio" name="cmbtiposservicio" required="true"></select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0" id="tipos-uso">
                                            <label>Tipo de Uso</label>
                                            <select class="form-control" id="cmbtiposuso" name="cmbtiposuso" required="true"></select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Color(*)</label>
                                            <input class="form-control" id="txtcolores" name="txtcolores" style="text-transform: uppercase">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Marca(*)</label>
                                            <input class="form-control" id="txtmarcas" name="txtmarcas" style="text-transform: uppercase">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Linea(*)</label>
                                            <input class="form-control" id="txtlineas" name="txtlineas" style="text-transform: uppercase">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Modelo (*)</label>
                                            <input class="form-control" type="number" id="txtmodelo" name="txtmodelo" maxlength="4" required>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Transformado (*)</label>
                                            <select class="form-control" id="cmbtransformado" name="cmbtransformado">
                                                <option value="S" selected>Si</option>
                                                <option value="N">No</option>
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
                                            <label>Registrado en Runt (*)</label>
                                            <select class="form-control" id="cmbrunt" name="cmbrunt" onchange="habilitarCampoLicenciaTransito()">
                                                <option value="S" selected>Si</option>
                                                <option value="N">No</option>
                                            </select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0 matricula">
                                            <label>Licencia Transito</label>
                                            <input class="form-control" type="text" id="txtlicenciatransito" name="txtlicenciatransito" value="" style="text-transform: uppercase"/>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0 matricula">
                                            <label>Fecha Matricula</label>
                                            <input class="form-control" type="text" id="txtfechamatricula" name="txtfechamatricula" readonly="true" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0 matricula">
                                            <label>Pais Matricula</label>
                                            <select class="form-control" id="cmbpaismatricula" name="cmbpaismatricula" required="true"></select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0 matricula matricula-pais" id="departamento-matricula">
                                            <label>Departamento Matricula</label>
                                            <select class="form-control" id="cmbdepartamentomatricula" name="cmbdepartamentomatricula" required="true"></select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0 matricula matricula-pais">
                                            <label>Municipio Matricula</label>
                                            <select class="form-control" id="cmbmunicipiomatricula" name="cmbmunicipiomatricula" required="true"></select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0 matricula" id="ciudad-matricula">
                                            <label>Ciudad</label>
                                            <input class="form-control" type="text" id="txtciudadmatricula" name="txtciudadmatricula" maxlength="80" style="text-transform: uppercase">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Datos Importación</h6>
                                </div>
                                <div class="card-body">
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0" id="tipos-importacion">
                                            <label>Tipo Importación</label>
                                            <select class="form-control" id="cmdtiposimportacion" name="cmdtiposimportacion" required="true"></select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0 importacion">
                                            <label>Documento Importación(*)</label>
                                            <input class="form-control" type="text" id="txtdocumentoimportacion" name="txtdocumentoimportacion" maxlength="80" style="text-transform: uppercase">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0 importacion">
                                            <label>Fecha Importación (*)</label>
                                            <input class="form-control" type="text" id="txtfechaimportacion" name="txtfechaimportacion" readonly="true" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0 importacion">
                                            <label>Pais Importación</label>
                                            <select class="form-control" id="cmbpaisimportacion" name="cmbpaisimportacion" required="true"></select>
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
                                            <label>SOAT (*)</label>
                                            <select class="form-control" id="cmbsoat" name="cmbsoat">
                                                <option value="S" selected>Si</option>
                                                <option value="N">No</option>
                                            </select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Fecha Venc. Soat</label>
                                            <input class="form-control" type="text" id="txtfechavsoat" name="txtfechavsoat" readonly="true" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Tecnomecanica (*)</label>
                                            <select class="form-control" id="cmbtecnomecanica" name="cmbtecnomecanica">
                                                <option value="S" selected>Si</option>
                                                <option value="N">No</option>
                                            </select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Fecha Venc. Tecnomecanica</label>
                                            <input class="form-control" type="text" id="txtfechavtecnomecanica" name="txtfechavtecnomecanica" readonly="true" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
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
                                                <input class="form-control" type="number" id="txtdocumento1" name="txtdocumento1" maxlength="20" required="true">
                                            </div>
                                            <div class="col-sm-5 mb-3 mb-sm-0">
                                                <input class="form-control" type="text" id="txtnombre1" name="txtnombre1" readonly="true">
                                            </div>
                                            <input type="hidden" id="idpersona1" name="idpersona1">
                                        </div>
                                    </div>

                                </div>
                                <input type="hidden" id="txtcantidadpersonas" name="txtcantidadpersonas" value="1">
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

        <script src="../../scripts/registrarVehiculo.js" type="text/javascript"></script>
        <script src="../../scripts/validacionesCampos.js" type="text/javascript"></script>
        <script src="../../scripts/personas.js" type="text/javascript"></script>
        <script src="../../scripts/parametros.js" type="text/javascript"></script>
        <script src="../../scripts/vehiculos.js" type="text/javascript"></script>        
        <script src="../../scripts/fechas.js" type="text/javascript"></script>

    </body>
</html>