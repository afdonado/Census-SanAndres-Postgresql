<%@page import="com.censo.modelo.dao.TipoDocumentoDao"%>
<%@page import="com.censo.modelo.dao.EstadoDao"%>
<%@page import="com.censo.modelo.dao.UsuarioDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.censo.modelo.persistencia.CenPerfilUsuario"%>
<%@page import="com.censo.modelo.persistencia.CenPerfil"%>
<%@page import="com.censo.modelo.persistencia.CenEstado"%>
<%@page import="com.censo.modelo.persistencia.CenTipoDocumento"%>
<%@page import="com.censo.modelo.persistencia.CenUsuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Ver Usuario</title>

        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="../../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="../../scripts/Ajax.js" type="text/javascript"></script>

        <script src="../../scripts/usuarios.js" type="text/javascript"></script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("consultarUsuario.jsp")) {
                    if (!request.getParameter("idusuario").equals("")) {
                    
                        UsuarioDao usuarioDao = new UsuarioDao();
                        EstadoDao estadoDao = new EstadoDao();
                        TipoDocumentoDao tipoDocumentoDao = new TipoDocumentoDao();
                        
                        long idusuario = Long.parseLong(request.getParameter("idusuario"));
                        
                        CenTipoDocumento centipodocumento = new CenTipoDocumento();
                        CenUsuario cenusuario = usuarioDao.ConsultarUsuarioById(idusuario);
                        
                        if (cenusuario != null) {
                            CenEstado cenestado = estadoDao.ConsultarEstadoById(cenusuario.getEstado());
                            CenPerfilUsuario cenperfilusuario = usuarioDao.ConsultarPerfilUsuarioByIdUsuario(cenusuario.getId());
                            CenPerfil cenperfil = new CenPerfil();
                            if(cenperfilusuario!=null){
                                cenperfil = usuarioDao.ConsultarPerfilById(cenperfilusuario.getPef_id());
                            }else{
                                cenperfil = null;
                            }
                            centipodocumento = tipoDocumentoDao.ConsultarTipoDocumentoById(cenusuario.getTipodocumento());
        %>
        <div class="container-fluid">
            <div class="panel panel-info">    
                <div class="panel-heading">
                    Datos Persona
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="form-group col-md-3">
                            <label>Tipo Documento</label>
                            <input class="form-control" type="text" id="txttipodoc" name="txttipodoc" value="<%= centipodocumento.getDescripcion() == null ? "" : centipodocumento.getDescripcion()%>" readonly="true">
                        </div>
                        <div class="form-group col-md-3">
                            <label>Documento</label>
                            <input class="form-control" type="text" id="txtdocumento" name="txtdocumento" value="<%= cenusuario.getNumerodocumento()== null ? "" : cenusuario.getNumerodocumento().trim()%>" readonly="true">
                        </div>
                        <div class="form-group col-md-6">
                            <label>Nombre</label>
                            <input class="form-control" type="text" id="txtnombre" name="txtnombre" value="<%= cenusuario.getNombre() == null ? "" : cenusuario.getNombre()%>" readonly="true">
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
                            <input class="form-control" type="text" id="txtperfil" name="txtperfil" value="<%= cenperfil == null ? "" : cenperfil.getNombre()%>" readonly="true">
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
                            <input class="form-control" type="text" id="txtestado" name="txtestado" value="<%= cenestado == null ? "" : cenestado.getDescripcion()%>" readonly="true">
                        </div>
                    </div>
                </div>
            </div>
            <input type="hidden" id="idusuario" name="idusuario" value="<%=idusuario%>">
            <%
                if (((java.util.LinkedList) session.getAttribute("permisosUsuario")).contains("modificarUsuario.jsp")) {
            %>
            <div class="row page-header">
                <div class="form-group col-xs-12 col-sm-3 col-md-3">
                    <button type="button" class="btn btn-lg btn-info btn-block" onclick="modificarUsuarioById('<%=idusuario%>')" id="btnmodificar" name="btnmodificar" >Modificar</button>
                </div>
            </div>
            <%
                }
            } else {
            %>
            <div class="panel panel-danger">    
                <div class="panel-heading">
                    Usuario no registrado
                </div>
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