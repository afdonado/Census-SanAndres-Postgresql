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
        <title>Listar Personas</title>

        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="../../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="../../scripts/Ajax.js" type="text/javascript"></script>

        <script src="../../scripts/personas.js" type="text/javascript"></script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("consultarPersona.jsp")) {
                    
                    PersonaDao personaDao = new PersonaDao();
                    
                    int opcion = Integer.parseInt(request.getParameter("opcion"));
                    int tipoconsulta = Integer.parseInt(request.getParameter("tipoconsulta"));

                    List<HashMap> datosPersona = null;

                    long idpersona = 0;

                    switch (opcion) {
                        case 1:
                            int tipodoc = Integer.parseInt(request.getParameter("tipodocumento"));
                            String documento = request.getParameter("documento");
                            datosPersona = personaDao.ListarPersonasByDocumento(tipodoc, documento);
                            break;
                        case 2:
                            String prinombre = request.getParameter("prinombre");
                            String segnombre = request.getParameter("segnombre");
                            String priapellido = request.getParameter("priapellido");
                            String segapellido = request.getParameter("segapellido");
                            datosPersona = personaDao.ListarPersonasByNombres(prinombre,segnombre,priapellido,segapellido);
                            break;
                        case 3:
                            Date fechaNacIni = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechanacini")).getTime());
                            Date fechaNacFin = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("fechanacfin")).getTime());
                            datosPersona = personaDao.ListarPersonasByFechaNacimiento(fechaNacIni,fechaNacFin);
                            break;
                    }
        %>
        <div class="container-fluid">
            <form id="frmlistarpersonas" method="post">
            <div class="table-responsive">
                <table id="dynamic-table" name="dynamic-table" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr class="active">
                            <th>No.</th>
                            <th>Tipo Doc.</th>
                            <th>Documento</th>
                            <th>Nombre</th>
                            <th>Fecha Nacimiento</th>
                            <th>Direccion</th>
                            <th>Telefono</th>
                            <th>Detalle</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <%
                                if (datosPersona.size() > 0) {
                                    int contador = 0;
                                    for (HashMap hash : datosPersona) {
                                        contador++;
                                        idpersona = Long.parseLong(hash.get("PER_ID").toString());
                            %>
                        <tr>
                            <td><%=contador %></td>
                            <td><%=hash.get("TIPO_DOC") == null ? "" : hash.get("TIPO_DOC")%></td>
                            <td><%=hash.get("DOCUMENTO") == null ? "" : hash.get("DOCUMENTO").toString()%></td>
                            <td><%=hash.get("NOMBRE_COMPLETO") == null ? "" : hash.get("NOMBRE_COMPLETO").toString()%></td>
                            <td><%=hash.get("FECHA_NAC") == null ? "" : hash.get("FECHA_NAC").toString()%></td>
                            <td><%=hash.get("DIRECCION") == null ? "" : hash.get("DIRECCION").toString()%></td>
                            <td><%=hash.get("TELEFONO") == null ? "" : hash.get("TELEFONO").toString()%></td>
                            <%
                                if (tipoconsulta == 1) {
                            %>
                            <td>
                                <button type="button" class="btn btn-danger" title="Ver datos de la persona" onclick="consultarPersonaById('<%=idpersona%>')"><span class="glyphicon glyphicon-search"></span></button>
                            </td>
                            <%
                            } else {
                            %>
                            <td>
                                <button type="button" class="btn btn-danger" title="Ver datos de la persona" onclick="modificarPersonaById('<%=idpersona%>')"><span class="glyphicon glyphicon-search"></span></button>
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
