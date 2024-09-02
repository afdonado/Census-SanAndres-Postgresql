<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Registrar Verificacion Censo</title>

        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="../../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../../scripts/Ajax.js" type="text/javascript"></script>

        <script src="../../scripts/validacionesCampos.js" type="text/javascript"></script>
        <script src="../../scripts/verificaciones.js" type="text/javascript"></script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("registrarVerificacion.jsp")) {
                    /*
                        if(cenverificacion.getVerificado_runt().equals("S")){
                            checkedRunt = "checked";
                        }
                        if(cenverificacion.getVerificado_doc().equals("S")){
                            checkedDoc = "checked";
                        }
                        if(cenverificacion.getVerificado_foto().equals("S")){
                            checkedFoto = "checked";
                        }
                     */
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
                            <h1 class="h3 mb-2 text-gray-800">Verificar Censo</h1>
                            <form role="form" id="frmmodificarverificacion">
                                <div class="card shadow mb-4">
                                    <div class="card-header py-3">
                                        <h6 class="m-0 font-weight-bold text-primary">Datos del censo</h6>
                                    </div>
                                    <div class="card-body">
                                        <div class="form-group row">
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label># Censo(*)</label>
                                                <input class="form-control" type="text" id="txtnumerocenso" name="txtnumerocenso" readonly="true">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Fecha Censo (*)</label>
                                                <input class="form-control" type="text" id="txtfechacenso" name="txtfechacenso" readonly="true">
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Runt Verificado</label>
                                                <input class="form-inline" type="checkbox" id="chkrunt" name="chkrunt" required="true">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Documentos Verificados</label>
                                                <input class="form-inline" type="checkbox" id="chkdocumentos" name="chkdocumentos" required="true">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Fotos Verificadas</label>
                                                <input class="form-inline" type="checkbox" id="chkfotos" name="chkfotos" required="true">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Estado</label>
                                                <select class="form-control" id="cmbestadosverificacion" name="cmbestadosverificacion" required="true"></select>
                                            </div>
                                        </div>
                                    </div>
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
                                        <button type="button" class="btn btn-lg btn-success btn-block" id="btnguardar" name="btnguardar" >Guardar</button><!--onclick="modificarVerificacion()"-->
                                    </div>
                                    <div class="form-group col-xs-6 col-sm-2 col-md-2">
                                        <button type="button" class="btn btn-lg btn-danger btn-block" id="btnvolver" name="btnvolver">Volver</button>
                                    </div>
                                </div>
                                <input type="hidden" id="idverificacion" name="idverificacion">
                                <input type="hidden" id="idcenso" name="idcenso">
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
            window.parent.location.href = "../dashboard";
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
    </body>
</html>
