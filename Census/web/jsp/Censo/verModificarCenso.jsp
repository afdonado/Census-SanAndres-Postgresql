<%@page import="com.censo.modelo.dao.TipoDocumentoDao"%>
<%@page import="com.censo.modelo.dao.TipoReferenciaDao"%>
<%@page import="com.censo.modelo.dao.TipoPersonaDao"%>
<%@page import="com.censo.modelo.dao.PuntoAtencionDao"%>
<%@page import="com.censo.modelo.dao.PersonaDao"%>
<%@page import="com.censo.modelo.dao.VehiculoDao"%>
<%@page import="com.censo.modelo.dao.CensoDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.censo.modelo.persistencia.CenVehiculo"%>
<%@page import="com.censo.modelo.persistencia.CenTipoReferencia"%>
<%@page import="com.censo.modelo.persistencia.CenPersona"%>
<%@page import="com.censo.modelo.persistencia.CenCenso"%>
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
        <title>Modificar Censo</title>

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
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("modificarCenso.jsp")) {
                    if (!request.getParameter("idcenso").equals("")) {
                    
                    CensoDao censoDao = new CensoDao();
                    VehiculoDao vehiculoDao = new VehiculoDao();
                    PersonaDao personaDao = new PersonaDao();
                    PuntoAtencionDao puntoAtencionDao = new PuntoAtencionDao();
                    TipoPersonaDao tipoPersonaDao = new TipoPersonaDao();
                    TipoReferenciaDao tipoReferenciaDao = new TipoReferenciaDao();
                    TipoDocumentoDao tipoDocumentoDao = new TipoDocumentoDao();
                    
                    long idcenso = Long.parseLong(request.getParameter("idcenso"));
                    CenUsuario cenusuario = (CenUsuario) session.getAttribute("usuario");
                    
                    Date fechaActual = new Date(new java.util.Date().getTime());
                    String nombreCompleto = "";
                    int TipoRef = 0;
                    String referencia = "";
                    
                    CenCenso cencenso = censoDao.ConsultarCensoById(idcenso);
                    if(cencenso!=null){
                        
                        CenVehiculo cenvehiculo = vehiculoDao.ConsultarVehiculoById(cencenso.getVeh_id());
                        if(cenvehiculo!=null){
                            if(cenvehiculo.getPlaca_veh()!= null && !cenvehiculo.getPlaca_veh().equals("")){
                                TipoRef = 1;
                                referencia = cenvehiculo.getPlaca_veh();
                            }else{
                                if(cenvehiculo.getMotor()!= null && !cenvehiculo.getMotor().equals("")){
                                    TipoRef = 2;
                                    referencia = cenvehiculo.getMotor();
                                }else{
                                    if(cenvehiculo.getChasis()!= null && !cenvehiculo.getChasis().equals("")){
                                        TipoRef = 3;
                                        referencia = cenvehiculo.getChasis();
                                    }else{
                                        if(cenvehiculo.getSerie()!= null && !cenvehiculo.getSerie().equals("")){
                                            TipoRef = 4;
                                            referencia = cenvehiculo.getSerie();
                                        }
                                    }
                                }
                            }
                        }
                        
                        CenPersona cenpersonacenso = personaDao.ConsultarPersonaById(cencenso.getPer_id());
                        if(cenpersonacenso!=null){
                            nombreCompleto = cenpersonacenso.getNombre1() + " " + (cenpersonacenso.getNombre2() != null ? cenpersonacenso.getNombre2().trim() : "") 
                    + " " + (cenpersonacenso.getApellido1() != null ? cenpersonacenso.getApellido1().trim() : "") 
                    + " " + (cenpersonacenso.getApellido2() != null ? cenpersonacenso.getApellido2().trim() : "");
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
        <div class="container-fluid">
            <form role="form" id="frmmodificarcenso" action="../../modificarCenso">
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
                                        <input class="form-control" type="text" id="numero_m" name="numero" onKeyPress="return validarNumeros(event)" style="text-transform: uppercase" maxlength="7" value="<%= cencenso.getNumero()== null ? "" : cencenso.getNumero().trim()%>"/>
                                    </div>
                                    <div class="form-group col-md-2">
                                        <label>Fecha Censo (*)</label>
                                        <input class="form-control" type="text" id="txtfechacenso" name="txtfechacenso" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(cencenso.getFecha())%>"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-3">
                                        <label>Punto de Atención(*)</label>
                                        <select class="form-control" id="cmbpuntoaten" name="cmbpuntoaten">
                                            <%
                                                List listaPuntosAtencion = puntoAtencionDao.ListarPuntosAtencion();

                                                for (int i = 0; i < listaPuntosAtencion.size(); i++) {
                                                    CenPuntoAtencion cenpuntoatencion = (CenPuntoAtencion) listaPuntosAtencion.get(i);
                                                    if (cenpuntoatencion.getId() == cencenso.getPun_id()) {
                                            %>
                                            <option value="<%=cenpuntoatencion.getId()%>" selected><%=cenpuntoatencion.getNombre()%></option>
                                            <% } else {%>
                                            <option value="<%=cenpuntoatencion.getId()%>"><%=cenpuntoatencion.getNombre()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <label>Tipo Referencia (*)</label>
                                        <select class="form-control" id="cmbtiporeferencia" name="cmbtiporeferencia">
                                            <%
                                                List listaTipoReferencia = tipoReferenciaDao.ListarTiposReferencia();

                                                for (int i = 0; i < listaTipoReferencia.size(); i++) {
                                                    CenTipoReferencia centiporeferencia = (CenTipoReferencia) listaTipoReferencia.get(i);
                                                    if (centiporeferencia.getId() == TipoRef) {
                                            %>
                                            <option value="<%=centiporeferencia.getId()%>" selected><%=centiporeferencia.getDescripcion()%></option>
                                            <% } else {%>
                                            <option value="<%=centiporeferencia.getId()%>"><%=centiporeferencia.getDescripcion()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <label>Referencia Vehiculo(*)</label>
                                        <input class="form-control" type="text" id="txtreferencia" name="txtreferencia" placeholder="" onblur="consultarRefVehiculoModificar()" required style="text-transform: uppercase" value="<%=referencia%>">
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
                                                    if (centipopersona.getId() == cencenso.getTper_id()) {
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
                                                    if (centipodocumento.getId() == cenpersonacenso.getTipodocumento()) {
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
                                        <input class="form-control" type="text" id="txtdocumento" name="txtdocumento" onKeyPress=" return validarNumeros(event)" onblur="consultarPersona(2)" maxlength="20" required style="text-transform: uppercase" value="<%= cenpersonacenso.getDocumento()== null ? "" : cenpersonacenso.getDocumento().trim()%>">
                                    </div>
                                    <div class="col-md-3">
                                        <label>Nombre</label>
                                        <input class="form-control" type="text" id="txtnombre" name="txtnombre" readonly="true" value="<%=nombreCompleto%>" >
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
                                        <textarea id="txtobservaciones" name="txtobservaciones" maxlength="300" style="width: 100%" cols="3"><%=cenpersonacenso.getDocumento()== null ? "" : cenpersonacenso.getDocumento().trim()%></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row page-header">
                            <div class="form-group col-xs-12 col-sm-3 col-md-3">
                                <button type="button" class="btn btn-lg btn-success btn-block" onclick="modificarCenso()" id="btnregistrar" name="btnregistrar" >Guardar</button>
                            </div>
                        </div>
                        <input type="hidden" id="idcenso" name="idcenso" value="<%=cencenso.getId()%>">
                        <input type="hidden" id="idpersona" name="idpersona" value="<%=cenpersonacenso.getId()%>">
                        <input type="hidden" id="idvehiculo" name="idvehiculo" value="<%=cenvehiculo.getId()%>">
                        <input type="hidden" id="numerocenso" name="numerocenso" value="<%=cencenso.getNumero()%>">
                        <input type="hidden" id="tiporeferencia" name="tiporeferencia" value="<%=TipoRef%>">
                        <input type="hidden" id="referencia" name="referencia" value="<%=referencia %>">
                    </div>
                </div>
            </form>
        </div>
        <%
            }
            }
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
