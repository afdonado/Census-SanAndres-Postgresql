<%@page import="com.censo.modelo.dao.PersonaDao"%>
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
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("consultarPersona.jsp")) {
                
                    PersonaDao personaDao = new PersonaDao();
                    
                    int opcion = Integer.parseInt(request.getParameter("opcion"));

                    String titulo = "";


                    List<HashMap> datosPersona = null;

                    switch (opcion) {
                        case 1:
                            int tipodoc = Integer.parseInt(request.getParameter("tipodocumento"));
                            String documento = request.getParameter("documento");
                            datosPersona = personaDao.ListarPersonasByDocumento(tipodoc, documento);
                            titulo = "por Documento";
                            break;
                        case 2:
                            String prinombre = request.getParameter("prinombre");
                            String segnombre = request.getParameter("segnombre");
                            String priapellido = request.getParameter("priapellido");
                            String segapellido = request.getParameter("segapellido");
                            datosPersona = personaDao.ListarPersonasByNombres(prinombre, segnombre, priapellido, segapellido);
                            titulo = "por Nombre";
                            break;
                        case 3:
                            Date fechaNacIni = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechanacini")).getTime());
                            Date fechaNacFin = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechanacfin")).getTime());
                            datosPersona = personaDao.ListarPersonasByFechaNacimiento(fechaNacIni, fechaNacFin);
                            titulo = "por Rango de Fecha Nacimiento de " + request.getParameter("fechaNacini") + " a " + request.getParameter("fechaNacfin");
                            break;
                        case 4:
                            datosPersona = personaDao.ListarPersonas();
                            titulo = "General";
                            break;
                    }
        %>
        <div class="container-fluid">
            <div class="table-responsive">
                <%
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment;filename=\"ReportePersonas.xls\"");
                %>
                <table style="border:2">
                    <tr>
                        <th colspan="7">
                            Reporte de Personas <%=titulo%>
                        </th>
                    </tr>
                    <tr>
                        <th>No.</th>
                        <th>Tipo Doc.</th>
                        <th>Documento</th>
                        <th>Nombre</th>
                        <th>Fecha Nacimiento</th>
                        <th>Direccion</th>
                        <th>Telefono</th>
                    </tr>
                    <%
                        if (datosPersona.size() > 0) {
                            int contador = 0;
                            for (HashMap hash : datosPersona) {
                                contador++;
                    %>
                    <tr>
                        <td><%=contador%></td>
                        <td><%=hash.get("TIPO_DOC") == null ? "" : hash.get("TIPO_DOC")%></td>
                        <td><%=hash.get("DOCUMENTO") == null ? "" : hash.get("DOCUMENTO").toString()%></td>
                        <td><%=hash.get("NOMBRE_COMPLETO") == null ? "" : hash.get("NOMBRE_COMPLETO").toString()%></td>
                        <td><%=hash.get("FECHA_NAC") == null ? "" : hash.get("FECHA_NAC").toString()%></td>
                        <td><%=hash.get("DIRECCION") == null ? "" : hash.get("DIRECCION").toString()%></td>
                        <td><%=hash.get("TELEFONO") == null ? "" : hash.get("TELEFONO").toString()%></td>
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
