<%@page import="com.censo.modelo.dao.CensoDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Listar Censos</title>

        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="../../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="../../scripts/Ajax.js" type="text/javascript"></script>

        <script src="../../scripts/censo.js" type="text/javascript"></script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("consultarCenso.jsp")) {
                    
                    CensoDao censoDao = new CensoDao();
                    int opcion = Integer.parseInt(request.getParameter("opcion"));
                    int tipoconsulta = Integer.parseInt(request.getParameter("tipoconsulta"));

                    List<HashMap> datosCenso = null;

                    long idcenso = 0;
                    int punto = 0;

                    switch (opcion) {
                        
                        case 0:
                            String numero = request.getParameter("numero");
                            datosCenso = censoDao.ListarCensosByNumero(numero);
                            break;
                        case 1:
                            int tiporef = Integer.parseInt(request.getParameter("tiporef"));
                            String referencia = request.getParameter("referencia");
                            datosCenso = censoDao.ListarCensosByReferenciaVehiculo(tiporef, referencia);
                            break;
                        case 2:
                            int tipodoc = Integer.parseInt(request.getParameter("tipodocumento"));
                            String documento = request.getParameter("documento");
                            datosCenso = censoDao.ListarCensosByPersona(tipodoc, documento);
                            break;
                        case 3:
                            Date fechaIni = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechaini")).getTime());
                            Date fechaFin = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechafin")).getTime());
                            punto = Integer.parseInt(request.getParameter("punto"));
                            datosCenso = censoDao.ListarCensosByFecha(fechaIni, fechaFin, punto);
                            break;
                        case 4:
                            Date fechaRegIni = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechaRegini")).getTime());
                            Date fechaRegFin = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechaRegfin")).getTime());
                            punto = Integer.parseInt(request.getParameter("punto"));
                            datosCenso = censoDao.ListarCensosByFechaRegistro(fechaRegIni, fechaRegFin, punto);
                            break;
                    }
        %>
        <div class="container-fluid">
            <div class="table-responsive">
                <table id="dynamic-table" name="dynamic-table" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr class="active">
                            <th>No.</th>
                            <th>Numero</th>
                            <th>Fecha</th>
                            <th>Hora</th>
                            <th>Punto Atención</th>
                            <th>Estado</th>
                            <th>Usuario</th>
                            <th>Fecha Registro</th>
                            <th>PDF</th>
                            <th>Fotos</th>
                            <th>Detalle</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <%
                                if (datosCenso.size() > 0) {
                                    int contador = 0;
                                    for (HashMap hash : datosCenso) {
                                        contador++;
                                        idcenso = Long.parseLong(hash.get("CEN_ID").toString());
                            %>
                        <tr>
                            <td><%=contador %></td>
                            <td><%=hash.get("NUMERO") == null ? "" : hash.get("NUMERO").toString()%></td>
                            <td><%=hash.get("FECHA") == null ? "" : hash.get("FECHA").toString()%></td>
                            <td><%=hash.get("HORA") == null ? "" : hash.get("HORA").toString()%></td>
                            <td><%=hash.get("PUNTO_ATENCION") == null ? "" : hash.get("PUNTO_ATENCION").toString()%></td>
                            <td><%=hash.get("ESTADO") == null ? "" : hash.get("ESTADO").toString()%></td>
                            <td><%=hash.get("USUARIO") == null ? "" : hash.get("USUARIO").toString()%></td>
                            <td><%=hash.get("FECHA_PROCESO") == null ? "" : hash.get("FECHA_PROCESO").toString()%></td>
                            <td><%=hash.get("DOCUMENTO_PDF") == null ? "" : hash.get("DOCUMENTO_PDF")%></td>
                            <td><%=hash.get("FOTO") == null ? "" : hash.get("FOTO")%></td>
                            <%
                                if (tipoconsulta == 1) {
                            %>
                            <td>
                                <button type="button" class="btn btn-danger" title="Ver datos de la Censo" onclick="consultarCensoById('<%=idcenso%>')"><span class="glyphicon glyphicon-search"></span></button>
                            </td>
                            <%
                            } else {
                            %>
                            <td>
                                <button type="button" class="btn btn-danger" title="Ver datos de la Censo" onclick="modificarCensoById('<%=idcenso%>')"><span class="glyphicon glyphicon-search"></span></button>
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
