<%@page import="com.censo.modelo.dao.TipoPersonaDao"%>
<%@page import="com.censo.modelo.dao.PuntoAtencionDao"%>
<%@page import="com.censo.modelo.dao.TipoDocumentoDao"%>
<%@page import="com.censo.modelo.dao.CensoDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.censo.modelo.persistencia.CenUsuario"%>
<%@page import="com.censo.modelo.persistencia.CenTipoPersona"%>
<%@page import="com.censo.modelo.persistencia.CenTipoDocumento"%>
<%@page import="com.censo.modelo.persistencia.CenPuntoAtencion"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Registrar Censo</title>

        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="../../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="../../scripts/Ajax.js" type="text/javascript"></script>

        <script src="../../scripts/validacionesCampos.js" type="text/javascript"></script>
        <script src="../../scripts/censo.js" type="text/javascript"></script>
        <script src="../../scripts/vehiculos.js" type="text/javascript"></script>
        <script src="../../scripts/personas.js" type="text/javascript"></script>

        <link href="../../vendor/jquery-ui-1.12.1.Redmond/jquery-ui.css" rel="stylesheet" type="text/css"/>
        <link href="../../vendor/jquery/calendario_es.css" rel="stylesheet" type="text/css"/>
        <script src="../../vendor/jquery-ui-1.12.1.Redmond/jquery-ui.js" type="text/javascript"></script>
        <script src="../../vendor/jquery/calendario_es_menor.js" type="text/javascript"></script>

        <link href="../../fileinput/css/fileinput.css" rel="stylesheet" type="text/css"/>
        <script src="../../fileinput/js/fileinput.min.js" type="text/javascript"></script>
        <script>
            $(function () {
                $("#txtfechacenso").datepicker();
            });
        </script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("registrarCenso.jsp")) {
                    
                    TipoDocumentoDao tipoDocumentoDao = new TipoDocumentoDao();
                    PuntoAtencionDao puntoAtencionDao = new PuntoAtencionDao();
                    TipoPersonaDao tipoPersonaDao = new TipoPersonaDao();
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

        <div class="modal fade" id="registrarvehiculo" name="registrarvehiculo" role="dialog" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">Registrar Vehiculo</h4>
                    </div>
                    <div class="modal-body">
                        <iframe frameborder="0" style="height: 600px;width: 100%;" ></iframe>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="page-header">Censo - Registrar Censo</h1>
                </div>
            </div>
            <form role="form" id="frmregistrarcenso" action="../../registrarCenso">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Datos del censo
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="form-group col-md-2">
                                        <label># Censo(*)</label>
                                        <input class="form-control" type="text" id="txtnumero" name="txtnumero" value="" onblur="consultarNumeroCenso()" onKeyPress="return validarNumeros(event)" style="text-transform: uppercase" maxlength="5"/>
                                    </div>
                                    <div class="form-group col-md-2">
                                        <label>Fecha Censo (*)</label>
                                        <input class="form-control" type="text" id="txtfechacenso" name="txtfechacenso" readonly="true" value=""/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-2">
                                        <label>Punto de Atención(*)</label>
                                        <select class="form-control" id="cmbpuntoaten" name="cmbpuntoaten">
                                            <%
                                                List listaPuntosAtencion = puntoAtencionDao.ListarPuntosAtencion();

                                                for (int i = 0; i < listaPuntosAtencion.size(); i++) {
                                                    CenPuntoAtencion cenpuntoatencion = (CenPuntoAtencion) listaPuntosAtencion.get(i);
                                            %>
                                            <option value="<%=cenpuntoatencion.getId()%>"><%=cenpuntoatencion.getNombre()%></option>
                                            <%
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <label>Tipo Referencia(*)</label>
                                        <select class="form-control" id="cmbtiporeferencia" name="cmbtiporeferencia">
                                            <option value="1" selected>Placa</option>
                                            <option value="2">Motor</option>
                                            <option value="3">Chasis</option>
                                            <option value="4">Serie</option>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <label>Referencia Vehiculo(*)</label>
                                        <input class="form-control" type="text" id="txtreferencia" name="txtreferencia" placeholder="" onblur="consultarRefVehiculo()" required style="text-transform: uppercase">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Datos Persona Presentó Vehiculo
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="form-group col-md-3">
                                        <label>Tipo Persona(*)</label>
                                        <select class="form-control" id="cmbtipopersona" name="cmbtipopersona">
                                            <%
                                                List listaTiposPersona = tipoPersonaDao.ListarTiposPersona();

                                                for (int i = 0; i < listaTiposPersona.size(); i++) {
                                                    CenTipoPersona centipopersona = (CenTipoPersona) listaTiposPersona.get(i);
                                                    if (centipopersona.getId() == 1) {
                                            %>
                                            <option value="<%=centipopersona.getId()%>" selected><%=centipopersona.getDescripcion()%></option>
                                            <% } else {%>
                                            <option value="<%=centipopersona.getId()%>"><%=centipopersona.getDescripcion()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <label>Tipo Documento(*)</label>
                                        <select class="form-control" id="cmbtipodoc" name="cmbtipodoc">
                                            <%
                                                List listaTiposDocumento = tipoDocumentoDao.ListarTiposDocumento();

                                                for (int i = 0; i < listaTiposDocumento.size(); i++) {
                                                    CenTipoDocumento centipodocumento = (CenTipoDocumento) listaTiposDocumento.get(i);
                                                    if (centipodocumento.getId() == 1) {
                                            %>
                                            <option value="<%=centipodocumento.getId()%>" selected><%=centipodocumento.getDescripcion()%></option>
                                            <% } else {%>
                                            <option value="<%=centipodocumento.getId()%>"><%=centipodocumento.getDescripcion()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <label>Documento(*)</label>
                                        <input class="form-control" type="text" id="txtdocumento" name="txtdocumento" onKeyPress=" return validarNumeros(event)" onblur="consultarPersona(2)" maxlength="20" required style="text-transform: uppercase">
                                    </div>
                                    <div class="col-md-3">
                                        <label>Nombre</label>
                                        <input class="form-control" type="text" id="txtnombre" name="txtnombre" readonly="true">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Observaciones
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="form-group col-md-12">
                                        <textarea id="txtobservaciones" name="txtobservaciones" maxlength="300" style="width: 100%" cols="3"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row page-header">
                            <div class="form-group col-xs-12 col-sm-3 col-md-3">
                                <button type="button" class="btn btn-lg btn-success btn-block" onclick="registrarCenso()" id="btnregistrar" name="btnregistrar" >Registrar</button>
                            </div>
                        </div>
                        <input type="hidden" id="idpersona" name="idpersona">
                        <input type="hidden" id="idvehiculo" name="idvehiculo">
                    </div>
                </div>
            </form>
        </div>
        <%
        } else {
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
    </body>
</html>
