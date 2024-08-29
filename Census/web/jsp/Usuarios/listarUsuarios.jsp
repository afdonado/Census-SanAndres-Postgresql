<%@page import="com.censo.modelo.dao.UsuarioDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Listar Usuarios</title>

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
                    
                    UsuarioDao usuarioDao = new UsuarioDao();
                    
                    int opcion = Integer.parseInt(request.getParameter("opcion"));
                    int tipoconsulta = Integer.parseInt(request.getParameter("tipoconsulta"));

                    List<HashMap> datosUsuario = null;

                    long idusuario = 0;

                    switch (opcion) {
                        case 0:
                            String nombre = request.getParameter("nombre");
                            datosUsuario = usuarioDao.ListarUsuariosByNombre(nombre);
                            break;
                        case 1:
                            int tipodoc = Integer.parseInt(request.getParameter("tipodocumento"));
                            String documento = request.getParameter("documento");
                            datosUsuario = usuarioDao.ListarUsuariosByPersona(tipodoc, documento);
                            break;
                        case 2:
                            int punto = Integer.parseInt(request.getParameter("puntoatencion"));
                            datosUsuario = usuarioDao.ListarUsuariosByPunto(punto);
                            break;
                    }
        %>
        <div class="container-fluid">
            <div class="table-responsive">
                <table id="dynamic-table" name="dynamic-table" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr class="active">
                            <th>No.</th>
                            <th>Nombre</th>
                            <th>Fecha Creacion</th>
                            <th>Fecha Inactivo</th>
                            <th>Perfil</th>
                            <th>Punto Atención</th>
                            <th>Estado</th>
                            <th>Detalle</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <%
                                if (datosUsuario.size() > 0) {
                                    int contador = 0;
                                    for (HashMap hash : datosUsuario) {
                                        contador++;
                                        idusuario = Long.parseLong(hash.get("USU_ID").toString());
                            %>
                        <tr>
                            <td><%=contador %></td>
                            <td><%=hash.get("NOMBRE_USUARIO") == null ? "" : hash.get("NOMBRE_USUARIO").toString()%></td>
                            <td><%=hash.get("FECHA_INICIO") == null ? "" : hash.get("FECHA_INICIO").toString()%></td>
                            <td><%=hash.get("FECHA_FINAL") == null ? "" : hash.get("FECHA_FINAL").toString()%></td>
                            <td><%=hash.get("PERFIL") == null ? "" : hash.get("PERFIL").toString()%></td>
                            <td><%=hash.get("PUNTO_ATENCION") == null ? "" : hash.get("PUNTO_ATENCION").toString()%></td>
                            <td><%=hash.get("ESTADO") == null ? "" : hash.get("ESTADO").toString()%></td>
                            <%
                                if (tipoconsulta == 1) {
                            %>
                            <td>
                                <button type="button" class="btn btn-danger" title="Ver datos de la Usuario" onclick="consultarUsuarioById('<%=idusuario%>')"><span class="glyphicon glyphicon-search"></span></button>
                            </td>
                            <%
                            } else {
                            %>
                            <td>
                                <button type="button" class="btn btn-danger" title="Ver datos de la Usuario" onclick="modificarUsuarioById('<%=idusuario%>')"><span class="glyphicon glyphicon-search"></span></button>
                            </td>
                            <%
                                        }
                                    }
                                }
                            %>
                        </tr>
                    </tbody>
                </table>
            </div>            
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
