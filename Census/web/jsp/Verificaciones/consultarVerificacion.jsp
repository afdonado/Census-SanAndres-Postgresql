<%@page import="com.censo.modelo.dao.PuntoAtencionDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.censo.modelo.persistencia.CenUsuario"%>
<%@page import="com.censo.modelo.persistencia.CenPuntoAtencion"%>
<%@page import="com.censo.modelo.persistencia.CenTipoDocumento"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Consultar Censo</title>

        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="../../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="../../scripts/Ajax.js" type="text/javascript"></script>

        <link href="../../vendor/jquery-ui-1.12.1.Redmond/jquery-ui.css" rel="stylesheet" type="text/css"/>
        <link href="../../vendor/jquery/calendario_es.css" rel="stylesheet" type="text/css"/>
        <script src="../../vendor/jquery-ui-1.12.1.Redmond/jquery-ui.js" type="text/javascript"></script>
        <script src="../../vendor/jquery/calendario_es.js" type="text/javascript"></script>

        <script src="../../scripts/verificaciones.js" type="text/javascript"></script>

        <script>
            $(function () {
                $("#tabs").tabs();
            });
            $(function () {
                $("#txtfechainicenso").datepicker();
            });
            $(function () {
                $("#txtfechafincenso").datepicker();
            });
            $(function () {
                $("#txtfechainiregistro").datepicker();
            });
            $(function () {
                $("#txtfechafinregistro").datepicker();
            });
        </script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("consultarVerificacion.jsp")) {

                    PuntoAtencionDao puntoAtencionDao = new PuntoAtencionDao();

                    CenUsuario cenusuario = (CenUsuario) session.getAttribute("usuario");
                    Date fechaActual = new java.sql.Date(new java.util.Date().getTime());
        %>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="page-header">Verificacion - Consultar</h1>
                </div>
            </div>
            <form role="form" id="frmconsultarcenso" action="">
                <div class="row">
                    <div class="col-md-12">
                        <div id="tabs">
                            <ul>
                                <li><a href="#tabs-1">Fecha Censo</a></li>
                                <li><a href="#tabs-2">Fecha Registro</a></li>
                                <li><a href="#tabs-3">Numero Censo</a></li>
                            </ul>
                            <div id="tabs-1">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-3">
                                                <label>Fecha Inicial</label>
                                                <input class="form-control" type="text" id="txtfechainicenso" name="txtfechainicenso" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                            </div>
                                            <div class="col-md-3">
                                                <label>Fecha Final</label>
                                                <input class="form-control" type="text" id="txtfechafincenso" name="txtfechafincenso" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                            </div>
                                            <div class="col-md-2">
                                                <label>Punto de Atención</label>
                                                <select class="form-control" id="cmbpuntoatencenso" name="cmbpuntoatencenso">
                                                    <option value="0" selected>Seleccione un Punto</option>
                                                    <%
                                                        List listaPuntosAtencionCenso = puntoAtencionDao.ListarPuntosAtencion();

                                                        for (int i = 0; i < listaPuntosAtencionCenso.size(); i++) {
                                                            CenPuntoAtencion cenpuntoatencion = (CenPuntoAtencion) listaPuntosAtencionCenso.get(i);
                                                    %>
                                                    <option value="<%=cenpuntoatencion.getId()%>"><%=cenpuntoatencion.getNombre()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                            <div class="col-md-2">
                                                <label></label>
                                                <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarListadoVerificacion(1)" id="btnconsultar" name="btnconsultar" >Consultar</button>
                                            </div>
                                            <div class="col-md-2">
                                                <label></label>
                                                <button type="button" class="btn btn-md btn-success btn-block" onclick="generarReporteVerificacion(1)" id="btngenerarreporte">Generar Reporte</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="tabs-2">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-3">
                                                <label>Fecha Inicial</label>
                                                <input class="form-control" type="text" id="txtfechainiregistro" name="txtfechainiregistro" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                            </div>
                                            <div class="col-md-3">
                                                <label>Fecha Final</label>
                                                <input class="form-control" type="text" id="txtfechafinregistro" name="txtfechafinregistro" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                            </div>
                                            <div class="col-md-2">
                                                <label>Punto de Atención</label>
                                                <select class="form-control" id="cmbpuntoatenregistro" name="cmbpuntoatenregistro">
                                                    <option value="0" selected>Seleccione un Punto</option>
                                                    <%
                                                        List listaPuntosAtencionRegistro = puntoAtencionDao.ListarPuntosAtencion();

                                                        for (int i = 0; i < listaPuntosAtencionRegistro.size(); i++) {
                                                            CenPuntoAtencion cenpuntoatencion = (CenPuntoAtencion) listaPuntosAtencionRegistro.get(i);
                                                    %>
                                                    <option value="<%=cenpuntoatencion.getId()%>"><%=cenpuntoatencion.getNombre()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                            <div class="col-md-2">
                                                <label></label>
                                                <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarListadoVerificacion(2)" id="btnconsultar" name="btnconsultar" >Consultar</button>
                                            </div>
                                            <div class="col-md-2">
                                                <label></label>
                                                <button type="button" class="btn btn-md btn-success btn-block" onclick="generarReporteVerificacion(2)" id="btngenerarreporte">Generar Reporte</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="tabs-3">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class=" col-md-3">
                                                <label>Numero Censo</label>
                                                <input class="form-control" type="text" id="txtnumero" name="txtnumero" placeholder="" required style="text-transform: uppercase">
                                            </div>
                                            <div class="col-md-2">
                                                <label></label>
                                                <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarListadoVerificacion(3)" id="btnconsultar" name="btnconsultar" >Consultar</button>
                                            </div>
                                            <div class="col-md-2">
                                                <label></label>
                                                <button type="button" class="btn btn-md btn-success btn-block" onclick="generarReporteVerificacion(3)" id="btngenerarreporte">Generar Reporte</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
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
