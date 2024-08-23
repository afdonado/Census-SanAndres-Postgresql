<%@page import="com.censo.modelo.dao.TipoDocumentoDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.censo.modelo.persistencia.CenPuntoAtencion"%>
<%@page import="com.censo.modelo.persistencia.CenTipoDocumento"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Date"%>
<%@page import="com.censo.modelo.dao.UsuarioDao"%>
<%@page import="java.util.LinkedList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Modificar Usuario</title>

        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="../../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../../scripts/Ajax.js" type="text/javascript"></script>

        <link href="../../vendor/jquery-ui-1.12.1.Redmond/jquery-ui.css" rel="stylesheet" type="text/css"/>
        <link href="../../vendor/jquery/calendario_es.css" rel="stylesheet" type="text/css"/>
        <script src="../../vendor/jquery-ui-1.12.1.Redmond/jquery-ui.js" type="text/javascript"></script>
        <script src="../../vendor/jquery/calendario_es.js" type="text/javascript"></script>

        <script src="../../scripts/usuarios.js" type="text/javascript"></script>
        <script>
            $(function () {
                $("#tabs").tabs();
            });
        </script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("modificarUsuario.jsp")) {
                TipoDocumentoDao tipoDocumentoDao = new TipoDocumentoDao();
        %>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="page-header">Usuarios - Modificar Usuario</h1>
                </div>
            </div>
            <form role="form" id="frmconsultarusuario" action="">
                <div class="row">
                    <div class="col-md-12">
                        <div id="tabs">
                            <ul>
                                <li><a href="#tabs-0">Nombre Usuario</a></li>
                                <li><a href="#tabs-1">Persona</a></li>
                                <li><a href="#tabs-2">Reporte General</a></li>
                            </ul>
                            <div id="tabs-0">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class=" col-md-3">
                                                <label>Nombre Usuario</label>
                                                <input class="form-control" type="text" id="txtnomusu" name="txtnomusu" placeholder="" required style="text-transform: uppercase">
                                            </div>
                                            <div class="col-md-2">
                                                <label></label>
                                                <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarListadoUsuario(0)" id="btnconsultar" name="btnconsultar" >Consultar</button>
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
                                                <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarListadoUsuario(1)" id="btnconsultar" name="btnconsultar" >Consultar</button>
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