<%@page import="com.censo.modelo.dao.PuntoAtencionDao"%>
<%@page import="com.censo.modelo.dao.TipoDocumentoDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.censo.modelo.persistencia.CenPuntoAtencion"%>
<%@page import="com.censo.modelo.persistencia.CenTipoDocumento"%>
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

        <link href="../../vendor/jquery-ui-1.12.1.Redmond/jquery-ui.css" rel="stylesheet" type="text/css"/>
        <link href="../../vendor/jquery/calendario_es.css" rel="stylesheet" type="text/css"/>
        <script src="../../vendor/jquery-ui-1.12.1.Redmond/jquery-ui.js" type="text/javascript"></script>
        <script src="../../vendor/jquery/calendario_es.js" type="text/javascript"></script>

        <script src="../../scripts/censo.js" type="text/javascript"></script>

        <script>
            $(function () {
                $("#tabs").tabs();
            });
            $(function () {
                $("#txtfechaini").datepicker();
            });
            $(function () {
                $("#txtfechafin").datepicker();
            });
            $(function () {
                $("#txtfechaRegini").datepicker();
            });
            $(function () {
                $("#txtfechaRegfin").datepicker();
            });
        </script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("modificarCenso.jsp")) {
                    
                    TipoDocumentoDao tipoDocumentoDao = new TipoDocumentoDao();
                    PuntoAtencionDao puntoAtencionDao = new PuntoAtencionDao();
                    
                    Date fechaActual = new java.sql.Date(new java.util.Date().getTime());
        %>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="page-header">Censo - Modificar Censo</h1>
                </div>
            </div>
            <form role="form" id="frmconsultarcenso" action="">
                <div class="row">
                    <div class="col-md-12">
                        <div id="tabs">
                            <ul>
                                <li><a href="#tabs-0">Numero Censo</a></li>
                                <li><a href="#tabs-1">Referencia</a></li>
                                <li><a href="#tabs-2">Persona</a></li>
                                <li><a href="#tabs-3">Fecha Censo</a></li>
                                <li><a href="#tabs-4">Fecha Registro</a></li>
                            </ul>
                            <div id="tabs-0">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class=" col-md-3">
                                                <label>Numero Censo</label>
                                                <input class="form-control" type="text" id="txtnumero" name="txtnumero" placeholder="" required style="text-transform: uppercase">
                                            </div>
                                            <div class="col-md-2">
                                                <label></label>
                                                <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarListadoCenso(0)" id="btnconsultar" name="btnconsultar" >Consultar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="tabs-1">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class=" col-md-3">
                                                <label>Tipo Referencia</label>
                                                <select class="form-control" id="cmbtiporeferencia" name="cmbtiporeferencia">
                                                    <option value="1" selected>Placa</option>
                                                    <option value="2">Motor</option>
                                                    <option value="3">Chasis</option>
                                                    <option value="4">Serie</option>
                                                </select>
                                            </div>
                                            <div class="col-md-3">
                                                <label>Referencia Vehiculo</label>
                                                <input class="form-control" type="text" id="txtreferencia" name="txtreferencia" placeholder="" required style="text-transform: uppercase">
                                            </div>
                                            <div class="col-md-2">
                                                <label></label>
                                                <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarListadoCenso(1)" id="btnconsultar" name="btnconsultar" >Consultar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="tabs-2">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class=" col-md-3">
                                                <label>Tipo Documento</label>
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
                                                <label>Documento</label>
                                                <input class="form-control" type="text" id="txtdocumento" name="txtdocumento" onKeyPress=" return validarNumeros(event)" maxlength="20" style="text-transform: uppercase" required>
                                            </div>
                                            <div class="col-md-2">
                                                <label></label>
                                                <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarListadoCenso(2)" id="btnconsultar" name="btnconsultar" >Consultar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="tabs-3">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-3">
                                                <label>Fecha Inicial</label>
                                                <input class="form-control" type="text" id="txtfechaini" name="txtfechaini" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                            </div>
                                            <div class="col-md-3">
                                                <label>Fecha Final</label>
                                                <input class="form-control" type="text" id="txtfechafin" name="txtfechafin" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                            </div>
                                            <div class="col-md-2">
                                                <label>Punto de Atención</label>
                                                <select class="form-control" id="cmbpuntoatenfecha" name="cmbpuntoatenfecha">
                                                    <option value="0" selected>Seleccione un Punto</option>
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
                                                <label></label>
                                                <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarListadoCenso(3)" id="btnconsultar" name="btnconsultar" >Consultar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="tabs-4">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-3">
                                                <label>Fecha Inicial</label>
                                                <input class="form-control" type="text" id="txtfechaRegini" name="txtfechaRegini" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                            </div>
                                            <div class="col-md-3">
                                                <label>Fecha Final</label>
                                                <input class="form-control" type="text" id="txtfechaRegfin" name="txtfechaRegfin" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                            </div>
                                            <div class="col-md-2">
                                                <label>Punto de Atención</label>
                                                <select class="form-control" id="cmbpuntoatenfechareg" name="cmbpuntoatenfechareg">
                                                    <option value="0" selected>Seleccione un Punto</option>
                                                    <%
                                                        List listaPuntosAtencion2 = puntoAtencionDao.ListarPuntosAtencion();

                                                        for (int i = 0; i < listaPuntosAtencion2.size(); i++) {
                                                            CenPuntoAtencion cenpuntoatencion2 = (CenPuntoAtencion) listaPuntosAtencion2.get(i);
                                                    %>
                                                    <option value="<%=cenpuntoatencion2.getId()%>"><%=cenpuntoatencion2.getNombre()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                            <div class="col-md-2">
                                                <label></label>
                                                <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarListadoCenso(4)" id="btnconsultar" name="btnconsultar" >Consultar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" id="tipoconsulta" name="tipoconsulta" value="2">
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
