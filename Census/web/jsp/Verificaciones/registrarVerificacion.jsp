<%@page import="com.censo.modelo.dao.EstadoVerificacionDao"%>
<%@page import="com.censo.modelo.dao.VerificacionDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.censo.modelo.persistencia.CenVerificacion"%>
<%@page import="com.censo.modelo.persistencia.CenEstadoVerificacion"%>
<%@page import="com.censo.modelo.persistencia.CenEstado"%>
<%@page import="com.censo.modelo.persistencia.CenDepartamento"%>
<%@page import="com.censo.modelo.persistencia.CenMunicipio"%>
<%@page import="com.censo.modelo.persistencia.CenGenero"%>
<%@page import="com.censo.modelo.persistencia.CenGrupoSanguineo"%>
<%@page import="com.censo.modelo.persistencia.CenCategoriaLicencia"%>
<%@page import="com.censo.modelo.persistencia.CenTipoDocumento"%>
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
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("registrarVerificacion.jsp")) {

                    VerificacionDao verificacionDao = new VerificacionDao();
                    EstadoVerificacionDao estadoVerificacionDao = new EstadoVerificacionDao();
                
                    long idcenso = 0;
                    String numeroCenso = "";
                    String checkedRunt = "", checkedDoc = "", checkedFoto = "";

                    idcenso = Long.parseLong(request.getParameter("idcenso"));
                    numeroCenso = request.getParameter("numerocenso").toUpperCase().trim();
                    CenVerificacion cenverificacion = verificacionDao.ConsultarVerificacionByIdCenso(idcenso);
                    
                    if(cenverificacion!=null){
                        if(cenverificacion.getVerificado_runt().equals("S")){
                            checkedRunt = "checked";
                        }
                        if(cenverificacion.getVerificado_doc().equals("S")){
                            checkedDoc = "checked";
                        }
                        if(cenverificacion.getVerificado_foto().equals("S")){
                            checkedFoto = "checked";
                        }
        %>
        <div class="container-fluid">
            <form role="form" id="frmmodificarverificacion" action="../../modificarVerificacion">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-info" id="accordion">
                            <div class="panel-heading">
                                Verificar Censo
                            </div>
                            <div class="panel-body">
                                <div class="row form-group">
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Numero Censo</label>
                                        <input type="hidden" id="idcenso" name="idcenso" value="<%=idcenso%>"/>
                                        <input type="text" class="form-control" id="txtnumerocenso" name="txtnumerocenso" onpaste="return false" value="<%=numeroCenso%>" readonly="true" />
                                    </div>
                                </div>
                                <div class="row form-group">
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Runt Verificado</label>
                                        <input class="form-inline" type="checkbox" id="chkrunt" name="chkrunt" <%=checkedRunt%> required>
                                    </div>
                                    <div class="col-xs-12 col-sm-4 col-md-4">
                                        <label>Documentos Verificados</label>
                                        <input class="form-inline" type="checkbox" id="chkdocumentos" name="chkdocumentos" <%=checkedDoc%> required>
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Fotos Verificadas</label>
                                        <input class="form-inline" type="checkbox" id="chkfotos" name="chkfotos" <%=checkedFoto%> required>
                                    </div>
                                </div>
                                <div class="row form-group">
                                    <div class="col-xs-12 col-sm-4 col-md-4">
                                        <label>Estado</label>
                                        <select class="form-control" id="cmbestverificacion" name="cmbestverificacion">
                                            <%
                                                List listaEstadosVerificacion = estadoVerificacionDao.ListarEstadosVerificacion();
                                                for (int i = 0; i < listaEstadosVerificacion.size(); i++) {
                                                    CenEstadoVerificacion cenestadoverificacion = (CenEstadoVerificacion) listaEstadosVerificacion.get(i);
                                                    if (cenestadoverificacion.getId() == cenverificacion.getEstado()) {
                                            %>
                                            <option value="<%=cenestadoverificacion.getId()%>" selected><%=cenestadoverificacion.getDescripcion()%></option>
                                            <% } else {%>
                                            <option value="<%=cenestadoverificacion.getId()%>"><%=cenestadoverificacion.getDescripcion()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-12">
                                        <label>Observaciones</label>
                                        <textarea id="txtobservaciones" name="txtobservaciones" maxlength="500" style="width: 100%" cols="3"><%=cenverificacion.getObservaciones() == null ? "" : cenverificacion.getObservaciones().trim() %></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row page-header">
                    <div class="form-group col-xs-12 col-sm-3 col-md-3">
                        <button type="button" class="btn btn-lg btn-success btn-block" onclick="modificarVerificacion()" id="btnregistrar" name="btnregistrar" >Registrar</button>
                    </div>
                </div>
            </form>
        </div>
        <%                
                        
                    }else{
        %>
        <div class="container-fluid">
            <form role="form" id="frmregistrarverificacion" action="../../registrarVerificacion">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-info" id="accordion">
                            <div class="panel-heading">
                                Verificar Censo
                            </div>
                            <div class="panel-body">
                                <div class="row form-group">
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Numero Censo</label>
                                        <input type="hidden" id="idcenso" name="idcenso" value="<%=idcenso%>"/>
                                        <input type="text" class="form-control" id="txtnumerocenso" name="txtnumerocenso" onpaste="return false" value="<%=numeroCenso%>" readonly="true" />
                                    </div>
                                </div>
                                <div class="row form-group">
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Runt Verificado</label>
                                        <input class="form-inline" type="checkbox" id="chkrunt" name="chkrunt" value="S" required>
                                    </div>
                                    <div class="col-xs-12 col-sm-4 col-md-4">
                                        <label>Documentos Verificados</label>
                                        <input class="form-inline" type="checkbox" id="chkdocumentos" name="chkdocumentos" value="S" required>
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Fotos Verificadas</label>
                                        <input class="form-inline" type="checkbox" id="chkfotos" name="chkfotos" value="S" required>
                                    </div>
                                </div>
                                <div class="row form-group">
                                    <div class="col-xs-12 col-sm-4 col-md-4">
                                        <label>Estado</label>
                                        <select class="form-control" id="cmbestverificacion" name="cmbestverificacion">
                                            <%
                                                List listaEstadosVerificacion = estadoVerificacionDao.ListarEstadosVerificacion();
                                                for (int i = 0; i < listaEstadosVerificacion.size(); i++) {
                                                    CenEstadoVerificacion cenestadoverificacion = (CenEstadoVerificacion) listaEstadosVerificacion.get(i);
                                                    if (cenestadoverificacion.getId() == 1) {
                                            %>
                                            <option value="<%=cenestadoverificacion.getId()%>" selected><%=cenestadoverificacion.getDescripcion()%></option>
                                            <% } else {%>
                                            <option value="<%=cenestadoverificacion.getId()%>"><%=cenestadoverificacion.getDescripcion()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-12">
                                        <label>Observaciones</label>
                                        <textarea id="txtobservaciones" name="txtobservaciones" maxlength="500" style="width: 100%" cols="3"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row page-header">
                    <div class="form-group col-xs-12 col-sm-3 col-md-3">
                        <button type="button" class="btn btn-lg btn-success btn-block" onclick="registrarVerificacion()" id="btnregistrar" name="btnregistrar" >Registrar</button>
                    </div>
                </div>
            </form>
        </div>
        <%
            }//fin existe verificacion
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
