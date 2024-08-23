<%@page import="com.censo.modelo.dao.VehiculoDao"%>
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
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("consultarVehiculo.jsp")) {
                    
                    VehiculoDao vehiculoDao = new VehiculoDao();
                
                    int opcion = Integer.parseInt(request.getParameter("opcion"));
                    
                    String titulo = "";
                    List<HashMap> datosVehiculo = null;

                    switch (opcion) {
                        case 1:
                            int tiporef = Integer.parseInt(request.getParameter("tiporef"));
                            String referencia = request.getParameter("referencia");
                            datosVehiculo = vehiculoDao.ListarVehiculosByReferencia(tiporef, referencia);
                            titulo = "por Referencia";
                            break;
                        case 2:
                            int tipodoc = Integer.parseInt(request.getParameter("tipodocumento"));
                            String documento = request.getParameter("documento");
                            datosVehiculo = vehiculoDao.ListarVehiculosByPersona(tipodoc, documento);
                            titulo = "por Documento";
                            break;
                        case 3:
                            Date fechaRegIni = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechaRegini")).getTime());
                            Date fechaRegFin = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechaRegfin")).getTime());
                            datosVehiculo = vehiculoDao.ListarVehiculosByFechaRegistro(fechaRegIni, fechaRegFin);
                            titulo = "por Fecha Registro de " + request.getParameter("fechanacini") + " a " + request.getParameter("fechanacfin");
                            break;
                        case 4:
                            datosVehiculo = vehiculoDao.ListarVehiculos();
                            titulo = "General";
                            break;
                    }
        %>
        <div class="container-fluid">
            <div class="table-responsive">
                <%
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment;filename=\"ReporteGeneralVehiculos.xls\"");
                %>
                <table style="border:2">
                    <tr>
                        <th colspan="6">
                            Reporte de Vehiculos <%=titulo%>
                        </th>
                    </tr>
                    <tr>
                        <th>No.</th>
                        <th>Placa</th>
                        <th>Motor</th>
                        <th>Chasis</th>
                        <th>Serie</th>
                        <th>Marca</th>
                        <th>Linea</th>
                    </tr>
                    <%
                        if (datosVehiculo.size() > 0) {
                            int contador = 0;
                            for (HashMap hash : datosVehiculo) {
                                contador++;
                    %>
                    <tr>
                        <td><%=contador %></td>
                        <td><%=hash.get("VEH_PLACA") == null ? "" : hash.get("VEH_PLACA").toString()%></td>
                        <td><%=hash.get("VEH_MOTOR") == null ? "" : hash.get("VEH_MOTOR").toString()%></td>
                        <td><%=hash.get("VEH_CHASIS") == null ? "" : hash.get("VEH_CHASIS").toString()%></td>
                        <td><%=hash.get("VEH_SERIE") == null ? "" : hash.get("VEH_SERIE").toString()%></td>
                        <td><%=hash.get("MARCA") == null ? "" : hash.get("MARCA").toString()%></td>
                        <td><%=hash.get("LINEA") == null ? "" : hash.get("LINEA").toString()%></td>
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
