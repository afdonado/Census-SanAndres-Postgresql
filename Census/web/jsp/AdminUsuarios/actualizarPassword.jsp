<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.censo.modelo.persistencia.CenUsuario"%>
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
        <script src="../../scripts/usuarios.js" type="text/javascript"></script>

    </head>
    <body onload="LimpiarCamposActualizar();">
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("Inicio.jsp")) {
                
                    CenUsuario cenusuario = (CenUsuario) request.getSession().getAttribute("usuario");
        %>
        <div class="container-fluid">
            <form role="form" id="frmactualizarusuario" autocomplete="off">
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">Usuarios - Actualizar Contraseña</h1>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 page-content">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Datos del usuario
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="form-group col-md-3">
                                        <label>Usuario</label>
                                        <input class="form-control" type="text" id="txtnomusu" name="txtnomusu" maxlength="20" autocomplete="off" style="text-transform: uppercase" value="<%=cenusuario.getNombre()%>" readonly>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Password Actual</label>
                                        <input class="form-control" type="password" id="txtpassactual" name="txtpassactual" maxlength="20" onblur="validarPassActual()" autocomplete="off" value="" required>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Password Nuevo</label>
                                        <input class="form-control" type="password" id="txtpass" name="txtpass" maxlength="20" onblur="validarPass()" autocomplete="off" value="" required>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Repita Password Nuevo</label>
                                        <input class="form-control" type="password" id="txtreppass" name="txtreppass" maxlength="20" onblur="validarPass()" autocomplete="off" value="" required>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" id="idusuario" name="idusuario" value="<%=cenusuario.getId()%>">
                    </div>
                </div>
                <div class="row page-header">
                    <div class="form-group col-md-3">
                        <button type="button" class="btn btn-lg btn-success btn-block" onclick="actualizarPassword()" id="btnregistrar" name="btnregistrar" >Registrar</button>
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