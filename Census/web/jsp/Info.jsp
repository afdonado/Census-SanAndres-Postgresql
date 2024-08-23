<%@page import="com.censo.modelo.dao.EstadisticaDao"%>
<%@page import="com.censo.modelo.dao.UsuarioDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.censo.modelo.persistencia.CenUsuario"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Informacion</title>

        <link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../vendor/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../vendor/Highcharts-6.0.4/code/highcharts.js" type="text/javascript"></script>
        <script src="../vendor/Highcharts-6.0.4/code/highcharts-3d.js" type="text/javascript"></script>
        <script src="../vendor/Highcharts-6.0.4/code/modules/exporting.js" type="text/javascript"></script>
        <script src="../scripts/estadisticas.js" type="text/javascript"></script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("Inicio.jsp")) {
                    CenUsuario cenusuario = (CenUsuario) sessionCensus.getAttribute("usuario");
                    if (cenusuario != null) {
        %>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="page-header">Census - Pagina de Inicio</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            Bienvenido a Census usuario <%=cenusuario.getNombre()%>                          
                        </div>
                        <div class="panel-body">
                            Sistema de información para registro y control de datos poblacionales.
                        </div>
                    </div>
                </div>
            </div>
            <%
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("estadisticas")) {
                    EstadisticaDao estadisticaDao = new EstadisticaDao();
            %>
            <div class="row">
                <div id="graficaClaseVeh" class="col-md-8"></div>
                <div id="tablaClaseVeh" class="col-md-4">
                    <table class="table table-condensed" >
                        <thead>
                        <th>#</th>
                        <th>Clase Vehiculo</th>
                        <th>Cantidad</th>
                        </thead>
                        <tbody>
                            <%
                                List<HashMap> datosClaseVeh = estadisticaDao.ListarCantidadCensosClaveVehiculo();

                                if (datosClaseVeh.size() > 0) {
                                    int contador = 0;
                                    for (HashMap hash : datosClaseVeh) {
                                        contador++;

                            %>
                            <tr>
                                <td><%=contador%></td>
                                <td><%=hash.get("CLV_DESCRIPCION") == null ? "" : hash.get("CLV_DESCRIPCION")%></td>
                                <td><%=hash.get("CANTIDAD") == null ? "" : hash.get("CANTIDAD").toString()%></td>
                            </tr>
                            <%
                                    }
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="row">
                <div id="graficaPersonasLicencia" class="col-md-4"></div>
                <div id="graficaPuntosAten" class="col-md-8"></div>
            </div>
            <div class="row">
                <div id="tablaPersonasLicencia" class="col-md-4">
                    <table class="table table-condensed" >
                        <thead>
                        <th>#</th>
                        <th>Descripcion</th>
                        <th>Cantidad</th>
                        </thead>
                        <tbody>
                            <%
                                List<HashMap> datosPersonasLicencia = estadisticaDao.ListarCantidadPersonasCensadasLicencia();

                                if (datosPersonasLicencia.size() > 0) {
                                    int contador = 0;
                                    for (HashMap hash : datosPersonasLicencia) {
                                        contador++;

                            %>
                            <tr>
                                <td><%=contador%></td>
                                <td><%=hash.get("DESCRIPCION") == null ? "" : hash.get("DESCRIPCION")%></td>
                                <td><%=hash.get("CANTIDAD") == null ? "" : hash.get("CANTIDAD").toString()%></td>
                            </tr>
                            <%
                                    }
                                }
                            %>
                        </tbody>
                    </table>
                </div>

                <div id="tablaPuntosAtencion" class="col-md-8">
                    <table class="table table-condensed" >
                        <thead>
                        <th>#</th>
                        <th>Punto Atención</th>
                        <th>Cantidad</th>
                        </thead>
                        <tbody>
                            <%
                                List<HashMap> datosPuntosAtencion = estadisticaDao.ListarCantidadCensosPuntoAtencion();

                                if (datosPuntosAtencion.size() > 0) {
                                    int contador = 0;
                                    for (HashMap hash : datosPuntosAtencion) {
                                        contador++;

                            %>
                            <tr>
                                <td><%=contador%></td>
                                <td><%=hash.get("PUN_DESCRIPCION") == null ? "" : hash.get("PUN_DESCRIPCION")%></td>
                                <td><%=hash.get("CANTIDAD") == null ? "" : hash.get("CANTIDAD").toString()%></td>
                            </tr>
                            <%
                                    }
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="row">
                <div id="graficaPersonasGenero" class="col-md-6"></div>
                <div id="graficaVehiculosPlacas" class="col-md-6"></div>
            </div>
            <div class="row">
                <div id="tablaPersonasGenero" class="col-md-6">
                    <table class="table table-condensed" >
                        <thead>
                        <th>#</th>
                        <th>Genero</th>
                        <th>Cantidad</th>
                        </thead>
                        <tbody>
                            <%
                                List<HashMap> datosPersonasGenero = estadisticaDao.ListarCantidadPersonasCensadasGenero();

                                if (datosPersonasGenero.size() > 0) {
                                    int contador = 0;
                                    for (HashMap hash : datosPersonasGenero) {
                                        contador++;

                            %>
                            <tr>
                                <td><%=contador%></td>
                                <td><%=hash.get("GENERO") == null ? "" : hash.get("GENERO")%></td>
                                <td><%=hash.get("CANTIDAD") == null ? "" : hash.get("CANTIDAD").toString()%></td>
                            </tr>
                            <%
                                    }
                                }
                            %>
                        </tbody>
                    </table>
                </div>

                <div id="tablaVehiculosPlacas" class="col-md-6">
                    <table class="table table-condensed" >
                        <thead>
                        <th>#</th>
                        <th>Punto Atención</th>
                        <th>Cantidad</th>
                        </thead>
                        <tbody>
                            <%
                                List<HashMap> datosVehiculosPlacas = estadisticaDao.ListarCantidadVehiculosPlaca();

                                if (datosVehiculosPlacas.size() > 0) {
                                    int contador = 0;
                                    for (HashMap hash : datosVehiculosPlacas) {
                                        contador++;

                            %>
                            <tr>
                                <td><%=contador%></td>
                                <td><%=hash.get("DESCRIPCION") == null ? "" : hash.get("DESCRIPCION")%></td>
                                <td><%=hash.get("CANTIDAD") == null ? "" : hash.get("CANTIDAD").toString()%></td>
                            </tr>
                            <%
                                    }
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="row">
                <div id="graficaVehiculoSoat" class="col-md-6"></div>
                <div id="graficaVehiculoTecno" class="col-md-6"></div>
            </div>
            <div class="row">
                <div id="tablaVehiculoSoat" class="col-md-6">
                    <table class="table table-condensed" >
                        <thead>
                        <th>#</th>
                        <th>Soat</th>
                        <th>Cantidad</th>
                        </thead>
                        <tbody>
                            <%
                                List<HashMap> datosVehSoat = estadisticaDao.ListarCantidadVehiculosSoat();

                                if (datosVehSoat.size() > 0) {
                                    int contador = 0;
                                    for (HashMap hash : datosVehSoat) {
                                        contador++;

                            %>
                            <tr>
                                <td><%=contador%></td>
                                <td><%=hash.get("DESCRIPCION") == null ? "" : hash.get("DESCRIPCION")%></td>
                                <td><%=hash.get("CANTIDAD") == null ? "" : hash.get("CANTIDAD").toString()%></td>
                            </tr>
                            <%
                                    }
                                }
                            %>
                        </tbody>
                    </table>
                </div>
                <div id="tablaVehiculoTecno" class="col-md-6">
                    <table class="table table-condensed" >
                        <thead>
                        <th>#</th>
                        <th>Tecnomecanica</th>
                        <th>Cantidad</th>
                        </thead>
                        <tbody>
                            <%
                                List<HashMap> datosVehTecno = estadisticaDao.ListarCantidadVehiculosTecno();

                                if (datosVehTecno.size() > 0) {
                                    int contador = 0;
                                    for (HashMap hash : datosVehTecno) {
                                        contador++;

                            %>
                            <tr>
                                <td><%=contador%></td>
                                <td><%=hash.get("DESCRIPCION") == null ? "" : hash.get("DESCRIPCION")%></td>
                                <td><%=hash.get("CANTIDAD") == null ? "" : hash.get("CANTIDAD").toString()%></td>
                            </tr>
                            <%
                                    }
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
            <%
                }
            %>
        </div>
        <%
            }
        } else {
        %>
        <script language="javascript" type="text/javascript">
            alert("Su usuario no tiene permiso para acceder a esta pagina");
            document.location.href = "../cerrarSesion";
        </script>
        <%
            }
        } else {
        %>
        <script language="javascript" type="text/javascript">
            alert("Su sesion a terminado");
            document.location.href = "../cerrarSesion";
        </script>
        <%
            }
        %>
    </body>
</html>
