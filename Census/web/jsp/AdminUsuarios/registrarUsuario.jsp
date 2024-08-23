<%@page import="com.censo.modelo.dao.TipoDocumentoDao"%>
<%@page import="com.censo.modelo.dao.UsuarioDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.censo.modelo.persistencia.CenPerfil"%>
<%@page import="java.util.List"%>
<%@page import="com.censo.modelo.persistencia.CenTipoDocumento"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Registrar Usuario</title>
        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="../../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>

        <script src="../../scripts/validacionesCampos.js" type="text/javascript"></script>
        <script src="../../scripts/personas.js" type="text/javascript"></script>
        <script src="../../scripts/usuarios.js" type="text/javascript"></script>
    </head>
    <body onload="LimpiarCampos();">
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("registrarUsuario.jsp")) {
                    UsuarioDao usuarioDao = new UsuarioDao();
                    TipoDocumentoDao tipoDocumentoDao = new TipoDocumentoDao();
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
            <form role="form" id="frmregistrarusuario" action="../../registrarUsuario" autocomplete="off">
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">Usuarios - Registrar Usuario</h1>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 page-content">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Datos de la persona
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="form-group col-md-3">
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
                                    <div class="form-group col-md-3">
                                        <label>Documento(*)</label>
                                        <input class="form-control" type="text" id="txtdocumento" name="txtdocumento" onKeyPress=" return validarNumeros(event)" maxlength="20" style="text-transform: uppercase" required>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label>Nombre</label>
                                        <input class="form-control" type="text" id="txtnombre" name="txtnombre" readonly="true">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Datos del usuario
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="form-group col-md-3">
                                        <label>Usuario(*)</label>
                                        <input class="form-control" type="text" id="txtnomusuario" name="txtnomusuario" maxlength="20" autocomplete="off" style="text-transform: uppercase" required>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Password(*)</label>
                                        <input class="form-control" type="password" id="txtpass" name="txtpass" maxlength="20" onblur="validarPass()" autocomplete="off" required>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Repita Password(*)</label>
                                        <input class="form-control" type="password" id="txtreppass" name="txtreppass" maxlength="20" onblur="validarPass()" autocomplete="off" required>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Perfil(*)</label>
                                        <select class="form-control" id="cmbperfiles" name="cmbperfiles">
                                            <%
                                                List listaPerfil = usuarioDao.ListarPerfiles();
                                                for (int i = 0; i < listaPerfil.size(); i++) {
                                                    CenPerfil cenperfil = (CenPerfil) listaPerfil.get(i);
                                                    if (cenperfil.getId() == 1) {
                                            %>
                                            <option value="<%=cenperfil.getId()%>" selected><%=cenperfil.getNombre()%></option>
                                            <% } else {%>
                                            <option value="<%=cenperfil.getId()%>"><%=cenperfil.getNombre()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" id="idpersona" name="idpersona">
                    </div>
                </div>
                <div class="row page-header">
                    <div class="form-group col-md-3">
                        <button type="button" class="btn btn-lg btn-success btn-block" onclick="registrarUsuario()" id="btnregistrar" name="btnregistrar" >Registrar</button>
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

