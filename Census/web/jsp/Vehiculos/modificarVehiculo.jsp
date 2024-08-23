<%@page import="com.censo.modelo.dao.TipoDocumentoDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.censo.modelo.persistencia.CenTipoDocumento"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Modificar vehiculo</title>

        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="../../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="../../scripts/Ajax.js" type="text/javascript"></script>

        <link href="../../vendor/jquery-ui-1.12.1.Redmond/jquery-ui.css" rel="stylesheet" type="text/css"/>
        <link href="../../vendor/jquery/calendario_es.css" rel="stylesheet" type="text/css"/>
        <script src="../../vendor/jquery-ui-1.12.1.Redmond/jquery-ui.js" type="text/javascript"></script>
        <script src="../../vendor/jquery/calendario_es.js" type="text/javascript"></script>

        <script src="../../scripts/validacionesCampos.js" type="text/javascript"></script>
        <script src="../../scripts/vehiculos.js" type="text/javascript"></script>

        <script>
            $(function () {
                $("#tabs").tabs();
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
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("modificarVehiculo.jsp")) {
                    
                    TipoDocumentoDao tipoDocumentoDao = new TipoDocumentoDao();
                    
                    Date fechaActual = new Date(new java.util.Date().getTime());
        %>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="page-header">Vehiculo - Modificar Vehiculo</h1>
                </div>
            </div>
            <form role="form" id="frmconsultarvehiculo">
                <div class="row">
                    <div class="col-md-12">
                        <div id="tabs">
                            <ul>
                                <li><a href="#tabs-1">Referencia</a></li>
                                <li><a href="#tabs-2">Persona</a></li>
                                <li><a href="#tabs-3">Fecha Registro</a></li>
                                <li><a href="#tabs-4">Reporte General</a></li>
                            </ul>
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
                                                <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarListadoVehiculo(1)" id="btnconsultar" name="btnconsultar" >Consultar</button>
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
                                                <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarListadoVehiculo(2)" id="btnconsultar" name="btnconsultar" >Consultar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="tabs-3">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <label>Fecha Inicial</label>
                                                <input class="form-control" type="text" id="txtfechaRegini" name="txtfechaRegini" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                            </div>
                                            <div class="col-md-4">
                                                <label>Fecha Final</label>
                                                <input class="form-control" type="text" id="txtfechaRegfin" name="txtfechaRegfin" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                            </div>
                                            <div class="col-md-2">
                                                <label></label>
                                                <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarListadoVehiculo(3)" id="btnconsultar" name="btnconsultar" >Consultar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <input type="hidden" id="tipoconsulta" name="tipoconsulta" value="2">
                        </div>

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
