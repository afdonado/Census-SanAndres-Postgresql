<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
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
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("listarVerificaciones.jsp")) {

                    DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");

                    try (Connection conex = dataSource.getConnection()) {

                        VerificacionDao verificacionDao = new VerificacionDao();

                        List<HashMap<String, Object>> datosVerificaciones = verificacionDao.ListarVerificacionesReporte(conex);


        %>
        <div class="container-fluid">
            <div class="table-responsive">
                <%
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment;filename=\"ReporteVerificaciones.xls\"");
                %>
                <table style="border:2">
                    <tr>
                        <th colspan="34">
                            Reporte Verificaciones de Censos
                        </th>
                    </tr>
                    <tr>
                        <th>No.</th>
                        <th>Numero</th>
                        <th>Fecha</th>
                        <th>Hora</th>
                        <th>Punto Atención</th>
                        <th>Observaciones Censo</th>
                        <th>Placa</th>
                        <th>Motor</th>
                        <th>Chasis</th>
                        <th>Serie</th>
                        <th>Color</th>
                        <th>Marca</th>
                        <th>Linea</th>
                        <th>Modelo</th>
                        <th>Clase</th>
                        <th>Servicio</th>
                        <th>Runt</th>
                        <th>Soat</th>
                        <th>Tec. Mec.</th>
                        <th>Estado</th>
                        <th>Usuario</th>
                        <th>Fecha Registro</th>
                        <th>Verificacion Documentos</th>
                        <th>Verificacion Fotos</th>
                        <th>Observaciones Verificacion</th>
                        <th>Fecha Verificacion</th>
                        <th>Usuario Verificacion</th>
                        <th>Estado Verificacion</th>
                        <th>PDF Cargado</th>
                    </tr>
                    <%
                        if (datosVerificaciones.size() > 0) {
                            int contador = 0;
                            for (HashMap hash : datosVerificaciones) {
                                contador++;
                    %>
                    <tr>
                        <td><%=contador%></td>
                        <td><%=hash.get("NUMERO") == null ? "" : hash.get("NUMERO").toString()%></td>
                        <td><%=hash.get("FECHA") == null ? "" : hash.get("FECHA").toString()%></td>
                        <td><%=hash.get("HORA") == null ? "" : hash.get("HORA").toString()%></td>
                        <td><%=hash.get("PUNTO_ATENCION") == null ? "" : hash.get("PUNTO_ATENCION").toString()%></td>
                        <td><%=hash.get("OBSERVACIONES") == null ? "" : hash.get("OBSERVACIONES").toString()%></td>
                        <td><%=hash.get("VEH_PLACA") == null ? "" : hash.get("VEH_PLACA").toString()%></td>
                        <td><%=hash.get("VEH_MOTOR") == null ? "" : hash.get("VEH_MOTOR").toString()%></td>
                        <td><%=hash.get("VEH_CHASIS") == null ? "" : hash.get("VEH_CHASIS").toString()%></td>
                        <td><%=hash.get("VEH_SERIE") == null ? "" : hash.get("VEH_SERIE").toString()%></td>
                        <td><%=hash.get("VEH_COLOR") == null ? "" : hash.get("VEH_COLOR").toString()%></td>
                        <td><%=hash.get("VEH_MARCA") == null ? "" : hash.get("VEH_MARCA").toString()%></td>
                        <td><%=hash.get("VEH_LINEA") == null ? "" : hash.get("VEH_LINEA").toString()%></td>
                        <td><%=hash.get("VEH_MODELO") == null ? "" : hash.get("VEH_MODELO").toString()%></td>
                        <td><%=hash.get("VEH_CLASE") == null ? "" : hash.get("VEH_CLASE").toString()%></td>
                        <td><%=hash.get("VEH_SERVICIO") == null ? "" : hash.get("VEH_SERVICIO").toString()%></td>
                        <td><%=hash.get("VEH_RUNT") == null ? "" : hash.get("VEH_RUNT").toString()%></td>
                        <td><%=hash.get("VEH_SOAT") == null ? "" : hash.get("VEH_SOAT").toString()%></td>
                        <td><%=hash.get("VEH_TECNOMEC") == null ? "" : hash.get("VEH_TECNOMEC").toString()%></td>
                        <td><%=hash.get("ESTADO") == null ? "" : hash.get("ESTADO").toString()%></td>
                        <td><%=hash.get("USUARIO") == null ? "" : hash.get("USUARIO").toString()%></td>
                        <td><%=hash.get("FECHA_PROCESO") == null ? "" : hash.get("FECHA_PROCESO").toString()%></td>
                        <td><%=hash.get("VERIFICACION_DOC") == null ? "" : hash.get("VERIFICACION_DOC").toString()%></td>
                        <td><%=hash.get("VERIFICACION_FOTOS") == null ? "" : hash.get("VERIFICACION_FOTOS").toString()%></td>
                        <td><%=hash.get("OBSERVACIONES_VERIFICACION") == null ? "" : hash.get("OBSERVACIONES_VERIFICACION").toString()%></td>
                        <td><%=hash.get("FECHA_PROCESO_VERIFICACION_FORMAT") == null ? "" : hash.get("FECHA_PROCESO_VERIFICACION_FORMAT").toString()%></td>
                        <td><%=hash.get("USUARIO_VERIFICACION") == null ? "" : hash.get("USUARIO_VERIFICACION").toString()%></td>
                        <td><%=hash.get("ESTADO_VERIFICACION") == null ? "" : hash.get("ESTADO_VERIFICACION").toString()%></td>
                        <td><%=hash.get("DOCUMENTO_PDF") == null ? "" : hash.get("DOCUMENTO_PDF").toString()%></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </table>

            </div>            
        </div>
        <%
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {
        %>
        <script type="text/javascript">
            alert("Su usuario no tiene permiso para acceder a esta pagina");
            window.parent.location.href = "../dashboard";
        </script>
        <%
            }
        } else {
        %>
        <script type="text/javascript">
            alert("Su sesion a terminado");
            document.location.href = "../../index.jsp";
        </script>
        <%
            }
        %>
    </body>
</html>
