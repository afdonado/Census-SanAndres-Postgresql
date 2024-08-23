<%@page import="javax.servlet.http.HttpSession"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Importar Documentos Censo</title>

        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="../../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="../../scripts/Ajax.js" type="text/javascript"></script>

        <script src="../../scripts/documentos.js" type="text/javascript"></script>

        <link href="../../fileinput/css/fileinput.css" rel="stylesheet" type="text/css"/>
        <script src="../../fileinput/js/fileinput.min.js" type="text/javascript"></script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("ConsultarDocumentosWeb.jsp")) {
        %>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="page-header">Documentos - Importar Documentos</h1>
                </div>
            </div>
            <form role="form" id="frmconsultardocumentosweb">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Importar Documentos
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <label>Numero Censo</label>
                                        <input class="form-control" type="text" id="txtnumerocenso" name="txtnumerocenso" maxlength="20" style="text-transform: uppercase" required>
                                    </div>
                                    <div class="col-md-2">
                                        <label></label>
                                        <button type="button" class="btn btn-md btn-primary btn-block" onclick="consultarImagenesWeb()" id="btnconsultar" name="btnconsultar" >Consultar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" id="idcenso" name="idcenso">
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