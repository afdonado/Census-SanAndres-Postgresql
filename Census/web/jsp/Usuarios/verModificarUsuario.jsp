<%@page import="com.censo.modelo.dao.EstadoDao"%>
<%@page import="com.censo.modelo.dao.TipoDocumentoDao"%>
<%@page import="com.censo.modelo.dao.PersonaDao"%>
<%@page import="com.censo.modelo.dao.UsuarioDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.sql.Date"%>
<%@page import="com.censo.modelo.persistencia.CenEstado"%>
<%@page import="com.censo.modelo.persistencia.CenPerfilUsuario"%>
<%@page import="com.censo.modelo.persistencia.CenPerfil"%>
<%@page import="com.censo.modelo.persistencia.CenUsuario"%>
<%@page import="com.censo.modelo.persistencia.CenTipoDocumento"%>
<%@page import="java.util.List"%>
<%@page import="com.censo.modelo.persistencia.CenCategoriaLicencia"%>
<%@page import="com.censo.modelo.persistencia.CenGenero"%>
<%@page import="com.censo.modelo.persistencia.CenGrupoSanguineo"%>
<%@page import="com.censo.modelo.persistencia.CenDepartamento"%>
<%@page import="com.censo.modelo.persistencia.CenPersona"%>
<%@page import="com.censo.modelo.persistencia.CenMunicipio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Registrar Usuario</title>
        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="../../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="../../scripts/Ajax.js" type="text/javascript"></script>

        <script src="../../scripts/validacionesCampos.js" type="text/javascript"></script>
        <script src="../../scripts/personas.js" type="text/javascript"></script>
        <script src="../../scripts/usuarios.js" type="text/javascript"></script>

    </head>
    <body onload="LimpiarCampos();">
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("modificarUsuario.jsp")) {
                    if (!request.getParameter("idusuario").equals("")) {
                        
                    UsuarioDao usuarioDao = new UsuarioDao();
                    PersonaDao personaDao = new PersonaDao();
                    TipoDocumentoDao tipoDocumentoDao = new TipoDocumentoDao();
                    EstadoDao estadoDao = new EstadoDao();
                        
                        long idusuario = Long.parseLong(request.getParameter("idusuario"));
                        String nombreCompleto = "";
                        CenUsuario cenusuario = usuarioDao.ConsultarUsuarioById(idusuario);
                        if (cenusuario != null) {

                            CenTipoDocumento centipodocumento = new CenTipoDocumento();
                            CenPerfilUsuario cenperfilusuario = new CenPerfilUsuario();
                            CenPerfil cenperfilUsu = new CenPerfil();
                            CenEstado cenestadoUsu = new CenEstado();

                            CenPersona cenpersona = personaDao.ConsultarPersonaById(cenusuario.getPer_id());
                            if (cenpersona != null) {

                                centipodocumento = tipoDocumentoDao.ConsultarTipoDocumentoById(cenpersona.getTipodocumento());
                                cenperfilusuario = usuarioDao.ConsultarPerfilUsuarioByIdUsuario(cenusuario.getId());
                                if (cenperfilusuario != null) {
                                    cenperfilUsu = usuarioDao.ConsultarPerfilById(cenperfilusuario.getPef_id());
                                }

                                cenestadoUsu = estadoDao.ConsultarEstadoById(cenusuario.getEstado());

                                centipodocumento = tipoDocumentoDao.ConsultarTipoDocumentoById(cenpersona.getTipodocumento());
                                nombreCompleto = cenpersona.getNombre1() + " " + (cenpersona.getNombre2() != null ? cenpersona.getNombre2().trim() : "")
                                        + " " + (cenpersona.getApellido1() != null ? cenpersona.getApellido1().trim() : "")
                                        + " " + (cenpersona.getApellido2() != null ? cenpersona.getApellido2().trim() : "");

                            }

        %>
        <div class="container-fluid">
            <form role="form" id="frmmodificarusuario" action="../../modificarUsuario">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Datos Basicos
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="form-group col-md-3">
                                        <label>Tipo Documento</label>
                                        <input class="form-control" type="text" id="txttipodoc" name="txttipodoc" value="<%= centipodocumento.getDescripcion() == null ? "" : centipodocumento.getDescripcion()%>" readonly="true">
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Documento</label>
                                        <input class="form-control" type="text" id="txtdocumento" name="txtdocumento" value="<%= cenpersona.getDocumento() == null ? "" : cenpersona.getDocumento().trim()%>" readonly="true">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label>Nombre</label>
                                        <input class="form-control" type="text" id="txtnombre" name="txtnombre" value="<%= nombreCompleto == null ? "" : nombreCompleto.trim()%>" readonly="true">
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Datos de Usuario
                            </div>
                            <div class="panel-body">
                                <div class="row form-group">
                                    <div class="form-group col-md-3">
                                        <label>Perfil</label>
                                        <select class="form-control" id="cmbperfiles" name="cmbperfiles">
                                            <%
                                                List listaPerfil = usuarioDao.ListarPerfiles();
                                                for (int i = 0; i < listaPerfil.size(); i++) {
                                                    CenPerfil cenperfil = (CenPerfil) listaPerfil.get(i);
                                                    if (cenperfil.getId() == cenperfilUsu.getId()) {
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
                                    <div class="form-group col-md-3">
                                        <label>Fecha Inicio</label>
                                        <input class="form-control" type="text" id="txtfechaini" name="txtfechaini" value="<%= cenusuario.getFechaini() == null ? "" : new java.text.SimpleDateFormat("dd/MM/yyyy").format(cenusuario.getFechaini())%>" readonly="true">
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Fecha Final</label>
                                        <input class="form-control" type="text" id="txtfechafin" name="txtfechafin" value="<%= cenusuario.getFechafin() == null ? "" : new java.text.SimpleDateFormat("dd/MM/yyyy").format(cenusuario.getFechafin())%>" readonly="true">
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Estado</label>
                                        <select class="form-control" id="cmbestado" name="cmbestado">
                                            <%
                                                List listaEstado = estadoDao.ListarEstados();
                                                for (int i = 0; i < listaEstado.size(); i++) {
                                                    CenEstado cenestado = (CenEstado) listaEstado.get(i);
                                                    if (cenestado.getId() == cenestadoUsu.getId()) {
                                            %>
                                            <option value="<%=cenestado.getId()%>" selected><%=cenestado.getDescripcion()%></option>
                                            <% } else {%>
                                            <option value="<%=cenestado.getId()%>"><%=cenestado.getDescripcion()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" id="idusuario" name="idusuario" value="<%= cenusuario.getId()%>">
                    <div class="row page-header">
                        <div class="form-group col-xs-12 col-sm-3 col-md-3">
                            <button type="button" class="btn btn-lg btn-success btn-block" onclick="modificarUsuario()" id="btnregistrar" name="btnregistrar" >Guardar</button>
                        </div>
                        <div class="form-group col-xs-12 col-sm-3 col-md-3">
                            <form id="frmrestaurarpassword" action="../../restaurarPassword">
                                <button type="button" class="btn btn-lg btn-info btn-block" onclick="restaurarPassword()" id="btnreset" name="btnreset" >Restaurar Password</button>                
                            </form>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <%
        } else {
        %>
        <div class="panel panel-danger">    
            <div class="panel-heading">
                Usuario no registrado
            </div>
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