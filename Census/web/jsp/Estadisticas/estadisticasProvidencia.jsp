<%@page import="javax.sql.DataSource"%>
<%@page import="java.sql.SQLException"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.censo.modelo.dao.EstadisticaDao"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.LinkedList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Estadisticas Providencia</title>

        <!-- Custom fonts for this template-->
        <link href="${pageContext.request.contextPath}/template/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/template/css/fonts-google.css" rel="stylesheet" type="text/css"/>

        <!-- Custom styles for this template-->
        <link href="${pageContext.request.contextPath}/template/css/sb-admin-2.min.css" rel="stylesheet">

        <!-- Custom styles for this page -->
        <link href="${pageContext.request.contextPath}/template/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

    </head>
    <body id="page-top">
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("estadisticasProvidencia.jsp")) {
                
                DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");
                try (Connection conex = dataSource.getConnection()) {
                        EstadisticaDao estadisticaDao = new EstadisticaDao();
        %>
        <div id="wrapper">
            <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
                <jsp:include page="/jsp/Menu.jsp"></jsp:include>
                </ul>

                <div id="content-wrapper" class="d-flex flex-column">
                    <div id="content">

                        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
                        <jsp:include page="/jsp/Header.jsp"></jsp:include>
                        </nav>

                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-md-12">
                                    <h1 class="page-header">Census - Estadisticas Providencia</h1>
                                </div>
                            </div>
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
                                            List<HashMap> datosClaseVeh = estadisticaDao.ListarCantidadCensosClaveVehiculoProvidencia(conex);

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
                            <div id="graficaPersonasLicencia" class="col-md-6"></div>
                            <div id="graficaPersonasGenero" class="col-md-6"></div>
                        </div>
                        <div class="row">
                            <div id="tablaPersonasLicencia" class="col-md-6">
                                <table class="table table-condensed" >
                                    <thead>
                                    <th>#</th>
                                    <th>Descripcion</th>
                                    <th>Cantidad</th>
                                    </thead>
                                    <tbody>
                                        <%
                                            List<HashMap> datosPersonasLicencia = estadisticaDao.ListarCantidadPersonasCensadasLicenciaProvidencia(conex);

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

                            <div id="tablaPersonasGenero" class="col-md-6">
                                <table class="table table-condensed" >
                                    <thead>
                                    <th>#</th>
                                    <th>Genero</th>
                                    <th>Cantidad</th>
                                    </thead>
                                    <tbody>
                                        <%
                                            List<HashMap> datosPersonasGenero = estadisticaDao.ListarCantidadPersonasCensadasGeneroProvidencia(conex);

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
                        </div>
                        <div class="row">
                            <div id="graficaVehiculosPlacas" class="col-md-6"></div>
                        </div>
                        <div class="row">
                            <div id="tablaVehiculosPlacas" class="col-md-6">
                                <table class="table table-condensed" >
                                    <thead>
                                    <th>#</th>
                                    <th>Punto Atención</th>
                                    <th>Cantidad</th>
                                    </thead>
                                    <tbody>
                                        <%
                                            List<HashMap> datosVehiculosPlacas = estadisticaDao.ListarCantidadVehiculosPlacaProvidencia(conex);

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
                                            List<HashMap> datosVehSoat = estadisticaDao.ListarCantidadVehiculosSoatProvidencia(conex);

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
                                            List<HashMap> datosVehTecno = estadisticaDao.ListarCantidadVehiculosTecnoProvidencia(conex);

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

                    </div>
                </div>
                <footer class="sticky-footer bg-white">
                    <jsp:include page="/jsp/Footer.jsp"></jsp:include>
                    </footer>
                </div>
            </div>
            <a class="scroll-to-top rounded" href="#page-top">
                <i class="fas fa-angle-up"></i>
            </a>
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

        <!-- Bootstrap core JavaScript-->
        <script src="${pageContext.request.contextPath}/template/vendor/jquery/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/template/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="${pageContext.request.contextPath}/template/vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="${pageContext.request.contextPath}/template/js/sb-admin-2.min.js"></script>

        <script src="${pageContext.request.contextPath}/template/vendor/Highcharts-6.0.4/code/highcharts.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/template/vendor/Highcharts-6.0.4/code/highcharts-3d.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/template/vendor/Highcharts-6.0.4/code/modules/exporting.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/scripts/estadisticasProvidencia.js" type="text/javascript"></script>
    </body>
</html>