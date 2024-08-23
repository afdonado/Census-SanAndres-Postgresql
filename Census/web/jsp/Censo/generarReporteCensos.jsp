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
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("consultarCenso.jsp")) {

                    CensoDao censoDao = new CensoDao();
                    int opcion = Integer.parseInt(request.getParameter("opcion"));
                    String titulo = "";
                    List<HashMap> datosCenso = null;

                    int punto = 0;

                    switch (opcion) {
                        case 0:
                            String numero = request.getParameter("numero");
                            datosCenso = censoDao.ListarCensosByNumero(numero);
                            titulo = "por Numero";
                            break;
                        case 1:
                            int tiporef = Integer.parseInt(request.getParameter("tiporef"));
                            String referencia = request.getParameter("referencia");
                            datosCenso = censoDao.ListarCensosByReferenciaVehiculo(tiporef, referencia);
                            titulo = "por Referencia Vehiculo";
                            break;
                        case 2:
                            int tipodoc = Integer.parseInt(request.getParameter("tipodocumento"));
                            String documento = request.getParameter("documento");
                            datosCenso = censoDao.ListarCensosByPersona(tipodoc, documento);
                            titulo = "por Persona";
                            break;
                        case 3:
                            Date fechaIni = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechaini")).getTime());
                            Date fechaFin = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechafin")).getTime());
                            punto = Integer.parseInt(request.getParameter("punto"));
                            datosCenso = censoDao.ListarCensosByFecha(fechaIni, fechaFin, punto);
                            titulo = "por Rango de Fecha de " + request.getParameter("fechaini") + " a " + request.getParameter("fechafin");
                            break;
                        case 4:
                            Date fechaRegIni = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechaRegini")).getTime());
                            Date fechaRegFin = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechaRegfin")).getTime());
                            punto = Integer.parseInt(request.getParameter("punto"));
                            datosCenso = censoDao.ListarCensosByFechaRegistro(fechaRegIni, fechaRegFin, punto);
                            titulo = "por Fecha Registro de " + request.getParameter("fechaRegini") + " a " + request.getParameter("fechaRegfin");
                            break;
                        case 5:
                            datosCenso = censoDao.ListarCensos();
                            titulo = "General";
                            break;
                    }
        %>
        <div class="container-fluid">
            <div class="table-responsive">
                <%
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment;filename=\"ReporteCensos.xls\"");
                %>
                <table style="border:2">
                    <tr>
                        <th colspan="22">
                            Reporte Censos <%=titulo%>
                        </th>
                    </tr>
                    <tr>
                        <th>No.</th>
                        <th>Numero</th>
                        <th>Fecha</th>
                        <th>Hora</th>
                        <th>Punto Atención</th>
                        <th>Placa</th>
                        <th>Motor</th>
                        <th>Chasis</th>
                        <th>Serie</th>
                        <th>Tipo Documento</th>
                        <th>Documento</th>
                        <th>Nombre</th>
                        <th>Dirección</th>
                        <th>Teléfono</th>
                        <th>Estado</th>
                        <th>Usuario</th>
                        <th>PDF</th>
                        <th>Fotos</th>
                        <th>Fecha Registro</th>
                    </tr>
                    <%
                        if (datosCenso.size() > 0) {
                            int contador = 0;
                            for (HashMap hash : datosCenso) {
                                contador++;
                    %>
                    <tr>
                        <td><%=contador%></td>
                        <td><%=hash.get("NUMERO") == null ? "" : hash.get("NUMERO").toString()%></td>
                        <td><%=hash.get("FECHA") == null ? "" : hash.get("FECHA").toString()%></td>
                        <td><%=hash.get("HORA") == null ? "" : hash.get("HORA").toString()%></td>
                        <td><%=hash.get("PUNTO_ATENCION") == null ? "" : hash.get("PUNTO_ATENCION").toString()%></td>
                        <td><%=hash.get("VEH_PLACA") == null ? "" : hash.get("VEH_PLACA").toString()%></td>
                        <td><%=hash.get("VEH_MOTOR") == null ? "" : hash.get("VEH_MOTOR").toString()%></td>
                        <td><%=hash.get("VEH_CHASIS") == null ? "" : hash.get("VEH_CHASIS").toString()%></td>
                        <td><%=hash.get("VEH_SERIE") == null ? "" : hash.get("VEH_SERIE").toString()%></td>
                        <td><%=hash.get("TIPO_DOC") == null ? "" : hash.get("TIPO_DOC").toString()%></td>
                        <td><%=hash.get("DOCUMENTO") == null ? "" : hash.get("DOCUMENTO").toString()%></td>
                        <td><%=hash.get("NOMBRE") == null ? "" : hash.get("NOMBRE").toString()%></td>
                        <td><%=hash.get("DIRECCION") == null ? "" : hash.get("DIRECCION").toString()%></td>
                        <td><%=hash.get("TELEFONO") == null ? "" : hash.get("TELEFONO").toString()%></td>
                        <td><%=hash.get("ESTADO") == null ? "" : hash.get("ESTADO").toString()%></td>
                        <td><%=hash.get("USUARIO") == null ? "" : hash.get("USUARIO").toString()%></td>
                        <td><%=hash.get("DOCUMENTO_PDF") == null ? "" : hash.get("DOCUMENTO_PDF")%></td>
                        <td><%=hash.get("FOTO") == null ? "" : hash.get("FOTO")%></td>
                        <td><%=hash.get("FECHA_PROCESO") == null ? "" : hash.get("FECHA_PROCESO").toString()%></td>
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
