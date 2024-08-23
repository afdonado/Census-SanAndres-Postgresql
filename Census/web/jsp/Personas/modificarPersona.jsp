<%@page import="com.censo.modelo.dao.TipoDocumentoDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.List"%>
<%@page import="com.censo.modelo.persistencia.CenTipoDocumento"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Modificar Persona</title>

        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="../../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../../scripts/Ajax.js" type="text/javascript"></script>

        <link href="../../vendor/jquery-ui-1.12.1.Redmond/jquery-ui.css" rel="stylesheet" type="text/css"/>
        <link href="../../vendor/jquery/calendario_es.css" rel="stylesheet" type="text/css"/>
        <script src="../../vendor/jquery-ui-1.12.1.Redmond/jquery-ui.js" type="text/javascript"></script>
        <script src="../../vendor/jquery/calendario_es.js" type="text/javascript"></script>

        <script src="../../scripts/validacionesCampos.js" type="text/javascript"></script>
        <script src="../../scripts/personas.js" type="text/javascript"></script>
        <script src="../../scripts/municipios.js" type="text/javascript"></script>
        <script>
            $(function () {
                $("#tabs").tabs();
            });
            $(function () {
                $("#txtfechanac").datepicker();
            });
        </script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("modificarPersona.jsp")) {
                    
                    TipoDocumentoDao tipoDocumentoDao = new TipoDocumentoDao();
                    
                    Date fechaActual = new Date(new java.util.Date().getTime());
        %>
        <!--Modal Respuesta Peticion-->
        <div class="modal fade" id="respuesta" name="respuesta" role="dialog" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">Respuesta Registro Persona</h4>
                    </div>
                    <div class="modal-body" id="respuestaRegistro">

                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="page-header">Personas - Modificar Persona</h1>
                </div>
            </div>
            <form role="form" id="frmconsultarpersona" action="">
                <div class="row">
                    <div class="col-md-12">
                        <div id="tabs">
                            <ul>
                                <li><a href="#tabs-1">Documento</a></li>
                                <li><a href="#tabs-2">Nombre</a></li>
                                <li><a href="#tabs-3">Fecha Nacimiento</a></li>
                                <li><a href="#tabs-4">Reporte General</a></li>
                            </ul>
                            <div id="tabs-1">
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
                                                <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarListadoPersona(1)" id="btnconsultar" name="btnconsultar" >Consultar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="tabs-2">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-xs-12 col-sm-3 col-md-3">
                                                <label>Primer Nombre</label>
                                                <input class="form-control" type="text" id="txtprinom" name="txtprinom" onKeyPress=" return validarLetras(event)" maxlength="80" style="text-transform: uppercase">
                                            </div>
                                            <div class="col-xs-12 col-sm-3 col-md-3">
                                                <label>Segundo Nombre</label>
                                                <input class="form-control" type="text" id="txtsegnom" name="txtsegnom" onKeyPress=" return validarLetras(event)" maxlength="80" style="text-transform: uppercase">
                                            </div>
                                            <div class="col-xs-12 col-sm-3 col-md-3">
                                                <label>Primer Apellido</label>
                                                <input class="form-control" type="text" id="txtpriape" name="txtpriape" onKeyPress=" return validarLetras(event)" maxlength="80" style="text-transform: uppercase">
                                            </div>
                                            <div class="col-xs-12 col-sm-3 col-md-3">
                                                <label>Segundo Apellido</label>
                                                <input class="form-control" type="text" id="txtsegape" name="txtsegape" onKeyPress=" return validarLetras(event)" maxlength="80" style="text-transform: uppercase">
                                            </div>
                                            <div class="col-md-2">
                                                <label></label>
                                                <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarListadoPersona(2)" id="btnconsultar" name="btnconsultar" >Consultar</button>
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
                                                <label>Fecha Nacimiento</label>
                                                <input class="form-control" type="text" id="txtfechanac" name="txtfechanac" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                            </div>
                                            <div class="col-md-2">
                                                <label></label>
                                                <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarListadoPersona(3)" id="btnconsultar" name="btnconsultar" >Consultar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="tabs-4">
                                <div class="row">
                                    <div class="col-md-12">
                                        <button class="btn btn-success" onclick="consultarListadoPersona(4)" style="cursor: pointer;">Generar Reporte</button>
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
