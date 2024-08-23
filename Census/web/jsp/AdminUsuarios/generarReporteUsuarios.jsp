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
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("consultarUsuario.jsp")) {

                    UsuarioDao usuarioDao = new UsuarioDao();
                    int opcion = Integer.parseInt(request.getParameter("opcion"));
                    String titulo = "";
                    List<HashMap> datosUsuario = null;

                    switch (opcion) {
                        case 0:
                            int punto = Integer.parseInt(request.getParameter("punto"));
                            datosUsuario = usuarioDao.ListarUsuariosByPunto(punto);
                            titulo = "por Punto de Atencion";
                            break;
                    }
        %>
        <div class="container-fluid">
            <div class="table-responsive">
                <%
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment;filename=\"ReporteUsuarios.xls\"");
                %>
                <table style="border:2">
                    <tr>
                        <th colspan="8">
                            Reporte General de Censos <%=titulo%>
                        </th>
                    </tr>
                    <tr>
                        <th>No.</th>
                        <th>Nombre</th>
                        <th>Fecha Creacion</th>
                        <th>Fecha Inactivo</th>
                        <th>Perfil</th>
                        <th>Punto Atención</th>
                        <th>Estado</th>
                    </tr>
                    <%
                        if (datosUsuario.size() > 0) {
                            int contador = 0;
                            for (HashMap hash : datosUsuario) {
                                contador++;
                    %>
                    <tr>
                        <td><%=contador%></td>
                        <td><%=hash.get("NOMBRE_USUARIO") == null ? "" : hash.get("NOMBRE_USUARIO").toString()%></td>
                        <td><%=hash.get("FECHA_INICIO") == null ? "" : hash.get("FECHA_INICIO").toString()%></td>
                        <td><%=hash.get("FECHA_FINAL") == null ? "" : hash.get("FECHA_FINAL").toString()%></td>
                        <td><%=hash.get("PERFIL") == null ? "" : hash.get("PERFIL").toString()%></td>
                        <td><%=hash.get("PUNTO_ATENCION") == null ? "" : hash.get("PUNTO_ATENCION").toString()%></td>
                        <td><%=hash.get("ESTADO") == null ? "" : hash.get("ESTADO").toString()%></td>
                    </tr>
                    <%
                            }
                        }
                    %>
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
