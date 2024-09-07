<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.censo.modelo.dao.EstadisticaDao"%>
<%@page import="com.censo.modelo.dao.UsuarioDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.censo.modelo.persistencia.CenUsuario"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession sessionCensus = request.getSession();
    CenUsuario cenusuario = (CenUsuario) sessionCensus.getAttribute("usuario");
    if (cenusuario != null) {
%>
<div class="d-sm-flex align-items-center justify-content-between mb-4">
    <h1 class="h3 mb-0 text-gray-800">Dashboard - Census</h1>
    <a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i
            class="fas fa-download fa-sm text-white-50"></i> Generar Reportes</a>
</div>
<div class="row">
    <div class="col-lg-12">
        <div class="card shadow mb-4">
            <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary">Bienvenido a Census</h6>
            </div>
            <div class="card-body">
                Sistema de información para registro y control de datos poblacionales.
            </div>
        </div>
    </div>
</div>

<%
    if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("estadisticas")) {

        Connection conex = null;

        EstadisticaDao estadisticaDao = new EstadisticaDao();
        conex = estadisticaDao.conectar();
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
                    List<HashMap> datosClaseVeh = estadisticaDao.ListarCantidadCensosClaveVehiculo(conex);

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
                    List<HashMap> datosPersonasLicencia = estadisticaDao.ListarCantidadPersonasCensadasLicencia(conex);

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
                    List<HashMap> datosPuntosAtencion = estadisticaDao.ListarCantidadCensosPuntoAtencion(conex);

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
                    List<HashMap> datosPersonasGenero = estadisticaDao.ListarCantidadPersonasCensadasGenero(conex);

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
                    List<HashMap> datosVehiculosPlacas = estadisticaDao.ListarCantidadVehiculosPlaca(conex);

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
                    List<HashMap> datosVehSoat = estadisticaDao.ListarCantidadVehiculosSoat(conex);

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
                    List<HashMap> datosVehTecno = estadisticaDao.ListarCantidadVehiculosTecno(conex);

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
    }
%>
