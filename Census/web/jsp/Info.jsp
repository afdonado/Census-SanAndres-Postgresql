<%@page import="javax.sql.DataSource"%>
<%@page import="java.sql.SQLException"%>
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
%>
<div class="d-sm-flex align-items-center justify-content-between mb-4">
    <h1 class="h3 mb-0 text-gray-800">Dashboard - Census</h1>
    <!--<a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i
            class="fas fa-download fa-sm text-white-50"></i> Generar Reportes</a>-->
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
    if (((LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("estadisticas")) {

        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");

        try (Connection conex = dataSource.getConnection()) {

            EstadisticaDao estadisticaDao = new EstadisticaDao();
%>

<%
    List<HashMap<String, Object>> datosCensosGeneralMensual = estadisticaDao.reporteCensosGeneralMensual(conex);
    if (datosCensosGeneralMensual.size() > 0) {
        for (HashMap hash : datosCensosGeneralMensual) {
%>
<div class="row">
    <div class="col-lg-12">
        <div class="card shadow mb-4">
            <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary">Año: <%=hash.get("ano").toString()%> Mes: <%=hash.get("mes").toString()%> Cantidad: <%=hash.get("cantidad_mes").toString()%></h6>
            </div>
            <div class="card-body">
                <div id="tablaCantidadGeneral" class="table-responsive">
                    <table class="table table-condensed table-bordered" style="width: 100%; font-size: 10px;">
                        <thead>
                        <th>1</th>
                        <th>2</th>
                        <th>3</th>
                        <th>4</th>
                        <th>5</th>
                        <th>6</th>
                        <th>7</th>
                        <th>8</th>
                        <th>9</th>
                        <th>10</th>
                        <th>11</th>
                        <th>12</th>
                        <th>13</th>
                        <th>14</th>
                        <th>15</th>
                        <th>16</th>
                        <th>17</th>
                        <th>18</th>
                        <th>19</th>
                        <th>20</th>
                        <th>21</th>
                        <th>22</th>
                        <th>23</th>
                        <th>24</th>
                        <th>25</th>
                        <th>26</th>
                        <th>27</th>
                        <th>28</th>
                        <th>29</th>
                        <th>30</th>
                        <th>31</th>
                        </thead>
                        <tbody>
                            <%
                                HashMap<String, Object> dias = estadisticaDao.reporteCensosGeneral(conex, hash.get("mes").toString());

                                if (!dias.isEmpty()) {

                            %>
                            <tr>
                                <td><%=dias.get("d01") == null ? "" : dias.get("d01").toString()%></td>
                                <td><%=dias.get("d02") == null ? "" : dias.get("d02").toString()%></td>
                                <td><%=dias.get("d03") == null ? "" : dias.get("d03").toString()%></td>
                                <td><%=dias.get("d04") == null ? "" : dias.get("d04").toString()%></td>
                                <td><%=dias.get("d05") == null ? "" : dias.get("d05").toString()%></td>
                                <td><%=dias.get("d06") == null ? "" : dias.get("d06").toString()%></td>
                                <td><%=dias.get("d07") == null ? "" : dias.get("d07").toString()%></td>
                                <td><%=dias.get("d08") == null ? "" : dias.get("d08").toString()%></td>
                                <td><%=dias.get("d09") == null ? "" : dias.get("d09").toString()%></td>
                                <td><%=dias.get("d10") == null ? "" : dias.get("d10").toString()%></td>
                                <td><%=dias.get("d11") == null ? "" : dias.get("d11").toString()%></td>
                                <td><%=dias.get("d12") == null ? "" : dias.get("d12").toString()%></td>
                                <td><%=dias.get("d13") == null ? "" : dias.get("d13").toString()%></td>
                                <td><%=dias.get("d14") == null ? "" : dias.get("d14").toString()%></td>
                                <td><%=dias.get("d15") == null ? "" : dias.get("d15").toString()%></td>
                                <td><%=dias.get("d16") == null ? "" : dias.get("d16").toString()%></td>
                                <td><%=dias.get("d17") == null ? "" : dias.get("d17").toString()%></td>
                                <td><%=dias.get("d18") == null ? "" : dias.get("d18").toString()%></td>
                                <td><%=dias.get("d19") == null ? "" : dias.get("d19").toString()%></td>
                                <td><%=dias.get("d20") == null ? "" : dias.get("d20").toString()%></td>
                                <td><%=dias.get("d21") == null ? "" : dias.get("d21").toString()%></td>
                                <td><%=dias.get("d22") == null ? "" : dias.get("d22").toString()%></td>
                                <td><%=dias.get("d23") == null ? "" : dias.get("d23").toString()%></td>
                                <td><%=dias.get("d24") == null ? "" : dias.get("d24").toString()%></td>
                                <td><%=dias.get("d25") == null ? "" : dias.get("d25").toString()%></td>
                                <td><%=dias.get("d26") == null ? "" : dias.get("d26").toString()%></td>
                                <td><%=dias.get("d27") == null ? "" : dias.get("d27").toString()%></td>
                                <td><%=dias.get("d28") == null ? "" : dias.get("d28").toString()%></td>
                                <td><%=dias.get("d29") == null ? "" : dias.get("d29").toString()%></td>
                                <td><%=dias.get("d30") == null ? "" : dias.get("d30").toString()%></td>
                                <td><%=dias.get("d31") == null ? "" : dias.get("d31").toString()%></td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<%
        }
    }
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
                    List<HashMap<String, Object>> datosClaseVeh = estadisticaDao.ListarCantidadCensosClaveVehiculo(conex);

                    if (datosClaseVeh.size() > 0) {
                        int contador = 0;
                        for (HashMap hash : datosClaseVeh) {
                            contador++;

                %>
                <tr>
                    <td><%=contador%></td>
                    <td><%=hash.get("clv_descripcion") == null ? "" : hash.get("clv_descripcion")%></td>
                    <td><%=hash.get("cantidad") == null ? "" : hash.get("cantidad").toString()%></td>
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
                    <td><%=hash.get("descripcion") == null ? "" : hash.get("descripcion")%></td>
                    <td><%=hash.get("cantidad") == null ? "" : hash.get("cantidad").toString()%></td>
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
                    <td><%=hash.get("pun_descripcion") == null ? "" : hash.get("pun_descripcion")%></td>
                    <td><%=hash.get("cantidad") == null ? "" : hash.get("cantidad").toString()%></td>
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
                    <td><%=hash.get("genero") == null ? "" : hash.get("genero")%></td>
                    <td><%=hash.get("cantidad") == null ? "" : hash.get("cantidad").toString()%></td>
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
                    <td><%=hash.get("descripcion") == null ? "" : hash.get("descripcion")%></td>
                    <td><%=hash.get("cantidad") == null ? "" : hash.get("cantidad").toString()%></td>
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
                    <td><%=hash.get("descripcion") == null ? "" : hash.get("descripcion")%></td>
                    <td><%=hash.get("cantidad") == null ? "" : hash.get("cantidad").toString()%></td>
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
                    <td><%=hash.get("descripcion") == null ? "" : hash.get("descripcion")%></td>
                    <td><%=hash.get("cantidad") == null ? "" : hash.get("cantidad").toString()%></td>
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
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
%>
