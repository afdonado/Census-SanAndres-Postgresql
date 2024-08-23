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
        <title>Listar Vehiculos</title>

        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="../../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="../../scripts/Ajax.js" type="text/javascript"></script>

        <script src="../../scripts/vehiculos.js" type="text/javascript"></script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("consultarVehiculo.jsp")) {
                    
                    VehiculoDao vehiculoDao = new VehiculoDao();
                
                    int opcion = Integer.parseInt(request.getParameter("opcion"));
                    int tipoconsulta = Integer.parseInt(request.getParameter("tipoconsulta"));

                    List<HashMap> datosVehiculo = null;

                    long idvehiculo = 0;

                    switch (opcion) {

                        case 1:
                            int tiporef = Integer.parseInt(request.getParameter("tiporef"));
                            String referencia = request.getParameter("referencia");
                            datosVehiculo = vehiculoDao.ListarVehiculosByReferencia(tiporef, referencia);
                            break;
                        case 2:
                            int tipodoc = Integer.parseInt(request.getParameter("tipodocumento"));
                            String documento = request.getParameter("documento");
                            datosVehiculo = vehiculoDao.ListarVehiculosByPersona(tipodoc, documento);
                            break;
                        case 3:
                            Date fechaRegIni = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechaRegini")).getTime());
                            Date fechaRegFin = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechaRegfin")).getTime());
                            datosVehiculo = vehiculoDao.ListarVehiculosByFechaRegistro(fechaRegIni, fechaRegFin);
                            break;
                    }
        %>
        <div class="container-fluid">
            <div class="table-responsive">
                <table id="dynamic-table" name="dynamic-table" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr class="active">
                            <th>No.</th>
                            <th>Placa</th>
                            <th>Motor</th>
                            <th>Chasis</th>
                            <th>Serie</th>
                            <th>Marca</th>
                            <th>Linea</th>
                            <th>Detalle</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <%
                                if (datosVehiculo.size() > 0) {
                                    int contador = 0;
                                    for (HashMap hash : datosVehiculo) {
                                        contador++;
                                        idvehiculo = Long.parseLong(hash.get("VEH_ID").toString());
                            %>
                        <tr>
                            <td><%=contador%></td>
                            <td><%=hash.get("VEH_PLACA") == null ? "" : hash.get("VEH_PLACA").toString()%></td>
                            <td><%=hash.get("VEH_MOTOR") == null ? "" : hash.get("VEH_MOTOR").toString()%></td>
                            <td><%=hash.get("VEH_CHASIS") == null ? "" : hash.get("VEH_CHASIS").toString()%></td>
                            <td><%=hash.get("VEH_SERIE") == null ? "" : hash.get("VEH_SERIE").toString()%></td>
                            <td><%=hash.get("MARCA") == null ? "" : hash.get("MARCA").toString()%></td>
                            <td><%=hash.get("LINEA") == null ? "" : hash.get("LINEA").toString()%></td>
                            <%
                                if (tipoconsulta == 1) {
                            %>
                            <td>
                                <button type="button" class="btn btn-danger" title="Ver datos del vehiculo" onclick="consultarVehiculoById('<%=idvehiculo%>')"><span class="glyphicon glyphicon-search"></span></button>
                            </td>
                            <%
                            } else {
                            %>
                            <td>
                                <button type="button" class="btn btn-danger" title="Ver datos del vehiculo" onclick="modificarVehiculoById('<%=idvehiculo%>')"><span class="glyphicon glyphicon-search"></span></button>
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
