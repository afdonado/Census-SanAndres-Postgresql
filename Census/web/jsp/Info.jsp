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
                                <td><%=dias.get("D01") == null ? "" : dias.get("D01").toString()%></td>
                                <td><%=dias.get("D02") == null ? "" : dias.get("D02").toString()%></td>
                                <td><%=dias.get("D03") == null ? "" : dias.get("D03").toString()%></td>
                                <td><%=dias.get("D04") == null ? "" : dias.get("D04").toString()%></td>
                                <td><%=dias.get("D05") == null ? "" : dias.get("D05").toString()%></td>
                                <td><%=dias.get("D06") == null ? "" : dias.get("D06").toString()%></td>
                                <td><%=dias.get("D07") == null ? "" : dias.get("D07").toString()%></td>
                                <td><%=dias.get("D08") == null ? "" : dias.get("D08").toString()%></td>
                                <td><%=dias.get("D09") == null ? "" : dias.get("D09").toString()%></td>
                                <td><%=dias.get("D10") == null ? "" : dias.get("D10").toString()%></td>
                                <td><%=dias.get("D11") == null ? "" : dias.get("D11").toString()%></td>
                                <td><%=dias.get("D12") == null ? "" : dias.get("D12").toString()%></td>
                                <td><%=dias.get("D13") == null ? "" : dias.get("D13").toString()%></td>
                                <td><%=dias.get("D14") == null ? "" : dias.get("D14").toString()%></td>
                                <td><%=dias.get("D15") == null ? "" : dias.get("D15").toString()%></td>
                                <td><%=dias.get("D16") == null ? "" : dias.get("D16").toString()%></td>
                                <td><%=dias.get("D17") == null ? "" : dias.get("D17").toString()%></td>
                                <td><%=dias.get("D18") == null ? "" : dias.get("D18").toString()%></td>
                                <td><%=dias.get("D19") == null ? "" : dias.get("D19").toString()%></td>
                                <td><%=dias.get("D20") == null ? "" : dias.get("D20").toString()%></td>
                                <td><%=dias.get("D21") == null ? "" : dias.get("D21").toString()%></td>
                                <td><%=dias.get("D22") == null ? "" : dias.get("D22").toString()%></td>
                                <td><%=dias.get("D23") == null ? "" : dias.get("D23").toString()%></td>
                                <td><%=dias.get("D24") == null ? "" : dias.get("D24").toString()%></td>
                                <td><%=dias.get("D25") == null ? "" : dias.get("D25").toString()%></td>
                                <td><%=dias.get("D26") == null ? "" : dias.get("D26").toString()%></td>
                                <td><%=dias.get("D27") == null ? "" : dias.get("D27").toString()%></td>
                                <td><%=dias.get("D28") == null ? "" : dias.get("D28").toString()%></td>
                                <td><%=dias.get("D29") == null ? "" : dias.get("D29").toString()%></td>
                                <td><%=dias.get("D30") == null ? "" : dias.get("D30").toString()%></td>
                                <td><%=dias.get("D31") == null ? "" : dias.get("D31").toString()%></td>
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
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
%>
