<%@page import="com.censo.modelo.dao.VerificacionDao"%>
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

        <script src="../../scripts/verificaciones.js" type="text/javascript"></script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("consultarVerificacion.jsp")) {

                    VerificacionDao verificacionDao = new VerificacionDao();

                    int opcion = Integer.parseInt(request.getParameter("opcion"));

                    List<HashMap> datosVerificaciones = null;

                    long idcenso = 0;
                    int punto = 0;

                    Date fechaIni = null;
                    Date fechaFin = null;

                    String numeroCenso = "";
                    
                    switch (opcion) {
                        case 1:
                            fechaIni = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechaini")).getTime());
                            fechaFin = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechafin")).getTime());
                            punto = Integer.parseInt(request.getParameter("punto"));
                            datosVerificaciones = verificacionDao.ListarVerificacionesByFechaCenso(fechaIni, fechaFin, punto);
                            break;
                        case 2:
                            fechaIni = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechaini")).getTime());
                            fechaFin = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechafin")).getTime());
                            punto = Integer.parseInt(request.getParameter("punto"));
                            datosVerificaciones = verificacionDao.ListarVerificacionesByFechaRegistro(fechaIni, fechaFin, punto);
                            break;
                        case 3:
                            numeroCenso = request.getParameter("numero");
                            datosVerificaciones = verificacionDao.ListarVerificacionesByNumeroCenso(numeroCenso);
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
                            <th>PDF</th>
                            <th>Foto</th>
                            <th>Fecha Registro</th>
                            <th>Fecha Reg. Verifi.</th>
                            <th>Estado Verificacion</th>
                            <th>Detalle</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <%
                                if (datosVerificaciones.size() > 0) {
                                    int contador = 0;
                                    for (HashMap hash : datosVerificaciones) {
                                        contador++;
                                        idcenso = Long.parseLong(hash.get("CEN_ID").toString());
                            %>
                        <tr>
                            <td><%=contador%></td>
                            <td><%=hash.get("NUMERO") == null ? "" : hash.get("NUMERO").toString()%></td>
                            <td><%=hash.get("FECHA") == null ? "" : hash.get("FECHA").toString()%></td>
                            <td><%=hash.get("HORA") == null ? "" : hash.get("HORA").toString()%></td>
                            <td><%=hash.get("PUNTO_ATENCION") == null ? "" : hash.get("PUNTO_ATENCION").toString()%></td>
                            <td><%=hash.get("ESTADO") == null ? "" : hash.get("ESTADO").toString()%></td>
                            <td><%=hash.get("USUARIO") == null ? "" : hash.get("USUARIO").toString()%></td>
                            <td><%=hash.get("DOCUMENTO_PDF") == null ? "" : hash.get("DOCUMENTO_PDF")%></td>
                            <td><%=hash.get("FOTO") == null ? "" : hash.get("FOTO")%></td>
                            <td><%=hash.get("FECHA_PROCESO") == null ? "" : hash.get("FECHA_PROCESO").toString()%></td>
                            <td><%=hash.get("FECHA_PROCESO_VERIFICACION") == null ? "" : hash.get("FECHA_PROCESO_VERIFICACION").toString()%></td>
                            <td><%=hash.get("ESTADO_VERIFICACION") == null ? "" : hash.get("ESTADO_VERIFICACION").toString()%></td>
                            <td>
                                <button type="button" class="btn btn-danger" title="Ver datos" onclick="consultarRegistroById('<%=idcenso%>')"><span class="glyphicon glyphicon-search"></span></button>
                            </td>
                            <%
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
