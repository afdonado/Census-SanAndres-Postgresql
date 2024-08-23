<%@page import="com.censo.modelo.dao.PersonaVehiculoDao"%>
<%@page import="com.censo.modelo.dao.TipoImportacionDao"%>
<%@page import="com.censo.modelo.dao.PaisDao"%>
<%@page import="com.censo.modelo.dao.TipoUsoDao"%>
<%@page import="com.censo.modelo.dao.TipoServicioDao"%>
<%@page import="com.censo.modelo.dao.ClaseVehiculoDao"%>
<%@page import="com.censo.modelo.dao.DepartamentoDao"%>
<%@page import="com.censo.modelo.dao.MunicipioDao"%>
<%@page import="com.censo.modelo.dao.MarcaDao"%>
<%@page import="com.censo.modelo.dao.LineaDao"%>
<%@page import="com.censo.modelo.dao.ColorDao"%>
<%@page import="com.censo.modelo.dao.VehiculoDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.censo.modelo.persistencia.CenColor"%>
<%@page import="com.censo.modelo.persistencia.CenVehiculo"%>
<%@page import="com.censo.modelo.persistencia.CenDepartamento"%>
<%@page import="com.censo.modelo.persistencia.CenMunicipio"%>
<%@page import="com.censo.modelo.persistencia.CenTipoUso"%>
<%@page import="com.censo.modelo.persistencia.CenLinea"%>
<%@page import="com.censo.modelo.persistencia.CenMarca"%>
<%@page import="com.censo.modelo.persistencia.CenPais"%>
<%@page import="com.censo.modelo.persistencia.CenClaseVehiculo"%>
<%@page import="com.censo.modelo.persistencia.CenTipoServicio"%>
<%@page import="com.censo.modelo.persistencia.CenTipoImportacion"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Modificar Vehiculo</title>

        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="../../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="../../scripts/Ajax.js" type="text/javascript"></script>

        <link href="../../vendor/jquery-ui-1.12.1.Redmond/jquery-ui.css" rel="stylesheet" type="text/css"/>
        <link href="../../vendor/jquery/calendario_es.css" rel="stylesheet" type="text/css"/>
        <script src="../../vendor/jquery-ui-1.12.1.Redmond/jquery-ui.js" type="text/javascript"></script>
        <script src="../../vendor/jquery/calendario_es.js" type="text/javascript"></script>

        <script src="../../scripts/validacionesCampos.js" type="text/javascript"></script>
        <script src="../../scripts/personas.js" type="text/javascript"></script>
        <script src="../../scripts/vehiculos.js" type="text/javascript"></script>
        <script src="../../scripts/municipios.js" type="text/javascript"></script>
        <script>
            $(function () {
                $("#txtfechamatri").datepicker();
            });
            $(function () {
                $("#txtfechaimportacion").datepicker();
            });
            $(function () {
                $("#txtfechavsoat").datepicker();
            });
            $(function () {
                $("#txtfechavtecno").datepicker();
            });
        </script>

        <script>
            $(function () {
                var marcas = new Array();
                $("#txtmarcas").bind("keydown", function (event) {
                    var data = {txtmarcas: $("#txtmarcas").val()};
                    $.getJSON("../../cargarMarcas", data, function (res, est, jqXHR) {
                        marcas.length = 0;
                        $.each(res, function (i, item) {
                            marcas[i] = item;
                        });
                    });
                });
                $("#txtmarcas").autocomplete({
                    source: marcas,
                    minLength: 1
                });
            });
            $(function () {
                var lineas = new Array();
                $("#txtlineas").bind("keydown", function (event) {
                    var data = {txtmarcas: $("#txtmarcas").val(), txtlineas: $("#txtlineas").val()};
                    $.getJSON("../../cargarLineas", data, function (res, est, jqXHR) {
                        lineas.length = 0;
                        $.each(res, function (i, item) {
                            lineas[i] = item;
                        });
                    });
                });
                $("#txtlineas").autocomplete({
                    source: lineas,
                    minLength: 1
                });
            });
            $(function () {
                var colores = new Array();
                $("#txtcolores").bind("keydown", function (event) {
                    var data = {txtcolores: $("#txtcolores").val()};
                    $.getJSON("../../cargarColores", data, function (res, est, jqXHR) {
                        colores.length = 0;
                        $.each(res, function (i, item) {
                            colores[i] = item;
                        });
                    });
                });
                $("#txtcolores").autocomplete({
                    source: colores,
                    minLength: 1
                });
            });
        </script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("modificarVehiculo.jsp")) {
                    if (!request.getParameter("idvehiculo").equals("")) {
                        
                        VehiculoDao vehiculoDao = new VehiculoDao();
                        ColorDao colorDao = new ColorDao();
                        LineaDao lineaDao = new LineaDao();
                        MarcaDao marcaDao = new MarcaDao();
                        MunicipioDao municipioDao = new MunicipioDao();
                        DepartamentoDao departamentoDao = new DepartamentoDao();
                        ClaseVehiculoDao claseVehiculoDao = new ClaseVehiculoDao();
                        TipoServicioDao tipoServicioDao = new TipoServicioDao();
                        TipoUsoDao tipoUsoDao = new TipoUsoDao();
                        PaisDao paisDao = new PaisDao();
                        TipoImportacionDao tipoImportacionDao = new TipoImportacionDao();
                        PersonaVehiculoDao personaVehiculoDao = new PersonaVehiculoDao();
                        
                        long idvehiculo = Integer.parseInt(request.getParameter("idvehiculo"));

                        Date fechaActual = new Date(new java.util.Date().getTime());

                        CenVehiculo cenvehiculo = vehiculoDao.ConsultarVehiculoById(idvehiculo);

                        if (cenvehiculo != null) {
                            CenColor cencolor = colorDao.ConsultarColorById(cenvehiculo.getCol_id());

                            CenLinea cenlinea = lineaDao.ConsultarLineaById(cenvehiculo.getLin_id());
                            CenMarca cenmarca = new CenMarca();
                            if (cenlinea != null) {
                                cenmarca = marcaDao.ConsultarMarcaById(cenlinea.getMar_id());
                            }

                            CenDepartamento cendepartamentoMatri = new CenDepartamento();
                            CenMunicipio cenmunicipioMatri = municipioDao.ConsultarMunicipioById(cenvehiculo.getMun_id_matricula());
                            if (cenmunicipioMatri != null) {
                                cendepartamentoMatri = departamentoDao.ConsultarDepartamentoById(cenmunicipioMatri.getDep_id());
                            }

        %>
        <div class="modal fade" id="registrarpersona" name="modificarpersona" role="dialog" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">Registrar Persona</h4>
                    </div>
                    <div class="modal-body">
                        <iframe frameborder="0" style="height: 600px;width: 100%;" ></iframe>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <form role="form" id="frmmodificarvehiculo" action="../../modificarVehiculo">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Datos de Identificacion
                            </div>
                            <div class="panel-body">
                                <div class="row form-group">
                                    <div class="col-md-3">
                                        <label>Placa</label>
                                        <input class="form-control" type="text" id="txtplaca" name="txtplaca" onblur="verificarVehiculoModificar(1, 'txtplaca')" maxlength="10" style="text-transform: uppercase" value="<%= cenvehiculo.getPlaca_veh() == null ? "" : cenvehiculo.getPlaca_veh().trim()%>">
                                    </div>
                                    <div class="col-md-3">
                                        <label>Motor (*)</label>
                                        <input class="form-control" type="text" id="txtmotor" name="txtmotor" onblur="verificarVehiculoModificar(2, 'txtmotor')" maxlength="30" style="text-transform: uppercase" value="<%= cenvehiculo.getMotor() == null ? "" : cenvehiculo.getMotor().trim()%>" required>
                                    </div>
                                    <div class="col-md-3">
                                        <label>Chasis (*)</label>
                                        <input class="form-control" type="text" id="txtchasis" name="txtchasis" onblur="verificarVehiculoModificar(3, 'txtchasis')" maxlength="30"style="text-transform: uppercase" value="<%= cenvehiculo.getChasis() == null ? "" : cenvehiculo.getChasis().trim()%>" required>
                                    </div>
                                    <div class="col-md-3">
                                        <label>Serie</label>
                                        <input class="form-control" type="text" id="txtserie" name="txtserie" onblur="verificarVehiculoModificar(4, 'txtserie')" maxlength="30" style="text-transform: uppercase" value="<%= cenvehiculo.getSerie() == null ? "" : cenvehiculo.getSerie().trim()%>">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Datos Generales
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="form-group col-md-3">
                                        <label>Clase</label>
                                        <select class="form-control" id="cmbclaseveh" name="cmbclaseveh">
                                            <%
                                                List listaClasesVehiculo = claseVehiculoDao.ListarClasesVehiculo();
                                                for (int i = 0; i < listaClasesVehiculo.size(); i++) {
                                                    CenClaseVehiculo cenclasevehiculo = (CenClaseVehiculo) listaClasesVehiculo.get(i);
                                                    if (cenclasevehiculo.getId() == cenvehiculo.getClase_veh()) {
                                            %>
                                            <option value="<%=cenclasevehiculo.getId()%>" selected><%=cenclasevehiculo.getDescripcion()%></option>
                                            <% } else {%>
                                            <option value="<%=cenclasevehiculo.getId()%>"><%=cenclasevehiculo.getDescripcion()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Servicio</label>
                                        <select class="form-control" id="cmbservicio" name="cmbservicio">
                                            <%
                                                List listaTiposServicio = tipoServicioDao.ListarTiposServicio();
                                                for (int i = 0; i < listaTiposServicio.size(); i++) {
                                                    CenTipoServicio Centiposervicio = (CenTipoServicio) listaTiposServicio.get(i);
                                                    if (Centiposervicio.getId() == cenvehiculo.getTipo_servicio()) {
                                            %>
                                            <option value="<%=Centiposervicio.getId()%>" selected><%=Centiposervicio.getDescripcion()%></option>
                                            <% } else {%>
                                            <option value="<%=Centiposervicio.getId()%>"><%=Centiposervicio.getDescripcion()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Tipo de Uso</label>
                                        <select class="form-control" id="cmbtipouso" name="cmbtipouso">
                                            <%
                                                List listaTiposUso = tipoUsoDao.ListarTiposUso();
                                                for (int i = 0; i < listaTiposUso.size(); i++) {
                                                    CenTipoUso centipouso = (CenTipoUso) listaTiposUso.get(i);
                                                    if (centipouso.getId() == cenvehiculo.getTipo_uso()) {
                                            %>
                                            <option value="<%=centipouso.getId()%>" selected><%=centipouso.getDescripcion()%></option>
                                            <% } else {%>
                                            <option value="<%=centipouso.getId()%>"><%=centipouso.getDescripcion()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Color</label>
                                        <input class="form-control" id="txtcolores" name="txtcolores" style="text-transform: uppercase" value="<%= cencolor == null ? "" : cencolor.getNombre()%>">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-3">
                                        <label>Marca</label>
                                        <input class="form-control" id="txtmarcas" name="txtmarcas" style="text-transform: uppercase" value="<%= cenmarca == null ? "" : cenmarca.getNombre()%>">
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Linea</label>
                                        <input class="form-control" id="txtlineas" name="txtlineas" style="text-transform: uppercase" value="<%= cenlinea == null ? "" : cenlinea.getNombre()%>">
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Modelo (*)</label>
                                        <input class="form-control" type="text" id="txtmodelo" name="txtmodelo" onKeyPress=" return validarNumeros(event)" maxlength="4" value="<%= cenvehiculo.getModelo()%>" required>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Transformado (*)</label>
                                        <select class="form-control" id="cmbtransformado" name="cmbtransformado">
                                            <%
                                                if (cenvehiculo.getTransformado().equals("S")) {
                                            %>
                                            <option value="S" selected>Si</option>
                                            <option value="N">No</option>
                                            <%
                                            } else {
                                            %>
                                            <option value="S">Si</option>
                                            <option value="N" selected>No</option>
                                            <%
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Datos Registro Inicial
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="form-group col-md-3">
                                        <label>Registrado en Runt (*)</label>
                                        <select class="form-control" id="cmbrunt" name="cmbrunt" onchange="habilitarCampoLicenciaTransito()">
                                            <%
                                                if (cenvehiculo.getRunt().equals("S")) {
                                            %>
                                            <option value="S" selected>Si</option>
                                            <option value="N">No</option>
                                            <%
                                            } else {
                                            %>
                                            <option value="S">Si</option>
                                            <option value="N" selected>No</option>
                                            <%
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-3" id="lictransito">
                                        <label>Licencia Transito</label>
                                        <input class="form-control" type="text" id="txtlictransito" name="txtlictransito" style="text-transform: uppercase" value="<%= cenvehiculo.getLicencia_transito() == null ? "" : cenvehiculo.getLicencia_transito().trim()%>" />
                                    </div>
                                    <div class="form-group col-md-3" id="fechamatri">
                                        <label>Fecha Matricula</label>
                                        <input class="form-control" type="text" id="txtfechamatri" name="txtfechamatri" readonly="true" value="<%= cenvehiculo.getFecha_matricula() == null ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual) : new java.text.SimpleDateFormat("dd/MM/yyyy").format(cenvehiculo.getFecha_matricula())%>"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-3" id="paismatri">
                                        <label>Pais</label>
                                        <select class="form-control" id="cmbpaismatricula" name="cmbpaismatricula" onChange="habilitarCombosDepMun()" onFocus="habilitarCombosDepMun()">
                                            <%
                                                List listaPaisesMatricula = paisDao.ListarPaises();

                                                for (int i = 0; i < listaPaisesMatricula.size(); i++) {
                                                    CenPais cenpais = (CenPais) listaPaisesMatricula.get(i);
                                                    if (cenpais.getId() == cenvehiculo.getPai_id_matricula()) {
                                            %>
                                            <option value="<%=cenpais.getId()%>" selected><%=cenpais.getNombre()%></option>
                                            <% } else {%>
                                            <option value="<%=cenpais.getId()%>"><%=cenpais.getNombre()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-3" id="comboDepMatri">
                                        <label>Departamento</label>

                                        <select class="form-control" id="cmbdepartamentomatri" name="cmbdepartamentomatri" onChange="consultarMunicipiosByDepto('cmbdepartamentomatri', 'cmbmunicipiomatri')
                                                return false" onFocus="consultarMunicipiosByDepto('cmbdepartamentomatri', 'cmbmunicipiomatri')">
                                            <%
                                                List listaDepartamentosMatri = departamentoDao.ListarDepartamentos();

                                                for (int i = 0; i < listaDepartamentosMatri.size(); i++) {
                                                    CenDepartamento cendepartamento = (CenDepartamento) listaDepartamentosMatri.get(i);
                                                    if (cendepartamento.getId() == cendepartamentoMatri.getId()) {
                                            %>
                                            <option value="<%=cendepartamento.getId()%>" selected><%=cendepartamento.getNombre()%></option>
                                            <% } else {%>
                                            <option value="<%=cendepartamento.getId()%>"><%=cendepartamento.getNombre()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-3" id="comboMunMatri">
                                        <label>Municipio</label>

                                        <select class="form-control" id="cmbmunicipiomatri" name="cmbmunicipiomatri">
                                            <%
                                                java.util.List listaMunicipiosMatricula = municipioDao.ListarMunicipiosByIdDepto(cendepartamentoMatri.getId());
                                                for (int i = 0; i < listaMunicipiosMatricula.size(); i++) {
                                                    CenMunicipio cenmunicipio = (CenMunicipio) listaMunicipiosMatricula.get(i);
                                                    if (cenmunicipio.getId() == cenmunicipioMatri.getId()) {
                                            %>
                                            <option value="<%=cenmunicipio.getId()%>" selected><%=cenmunicipio.getNombre()%></option>
                                            <%
                                            } else {
                                            %>
                                            <option value="<%=cenmunicipio.getId()%>"><%=cenmunicipio.getNombre()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-3" id="ciudadMatri" style="display: none">
                                        <label>Ciudad</label>
                                        <input class="form-control" type="text" id="txtciudadmatri" name="txtciudadmatri" maxlength="80" style="text-transform: uppercase" value="<%= cenvehiculo.getCiudad_matricula() == null ? "" : cenvehiculo.getCiudad_matricula().trim()%>" required>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Datos Importacion
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="form-group col-md-3">
                                        <label>Tipo Doc. Importacion</label>
                                        <select class="form-control" id="cmdtipodocimportacion" name="cmdtipodocimportacion" onchange="habilitarCamposImportacion()">
                                            <%
                                                List listaTiposImportacion = tipoImportacionDao.ListarTiposImportacion();
                                                for (int i = 0; i < listaTiposImportacion.size(); i++) {
                                                    CenTipoImportacion centipoimportacion = (CenTipoImportacion) listaTiposImportacion.get(i);
                                                    if (centipoimportacion.getId() == cenvehiculo.getTipodoc_importacion()) {
                                            %>
                                            <option value="<%=centipoimportacion.getId()%>" selected><%=centipoimportacion.getDescripcion()%></option>
                                            <% } else {%>
                                            <option value="<%=centipoimportacion.getId()%>"><%=centipoimportacion.getDescripcion()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-3" id="docImp">
                                        <label>Documento Importacion (*)</label>
                                        <input class="form-control" type="text" id="txtdocimportacion" name="txtdocimportacion" maxlength="80" style="text-transform: uppercase" value="<%= cenvehiculo.getDoc_importacion() == null ? "" : cenvehiculo.getDoc_importacion().trim()%>" >
                                    </div>
                                    <div class="form-group col-md-3" id="fechaImp">
                                        <label>Fecha Importacion (*)</label>
                                        <input class="form-control" type="text" id="txtfechaimportacion" name="txtfechaimportacion" readonly="true" value="<%=cenvehiculo.getFecha_importacion() == null ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual) : new java.text.SimpleDateFormat("dd/MM/yyyy").format(cenvehiculo.getFecha_importacion())%>"/>
                                    </div>
                                    <div class="form-group col-md-3" id="paisImp">
                                        <label>Pais</label>
                                        <select class="form-control" id="cmbpaisimportacion" name="cmbpaisimportacion" onChange="habilitarCombosDepMun() return false" onFocus="habilitarCombosDepMun()">
                                            <%
                                                List listaPaisesImportacion = paisDao.ListarPaises();

                                                for (int i = 0; i < listaPaisesImportacion.size(); i++) {
                                                    CenPais cenPais = (CenPais) listaPaisesImportacion.get(i);
                                                    if (cenPais.getId() == cenvehiculo.getPai_id_origen()) {
                                            %>
                                            <option value="<%=cenPais.getId()%>" selected><%=cenPais.getNombre()%></option>
                                            <% } else {%>
                                            <option value="<%=cenPais.getId()%>"><%=cenPais.getNombre()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Polizas y Certificados
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="form-group col-md-3">
                                        <label>SOAT (*)</label>
                                        <select class="form-control" id="cmbsoat" name="cmbsoat">
                                            <%
                                                if (cenvehiculo.getSoat().equals("S")) {
                                            %>
                                            <option value="S" selected>Si</option>
                                            <option value="N">No</option>
                                            <%
                                            } else {
                                            %>
                                            <option value="S">Si</option>
                                            <option value="N" selected>No</option>
                                            <%
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Fecha Venc. Soat</label>
                                        <input class="form-control" type="text" id="txtfechavsoat" name="txtfechavsoat" readonly="true" value="<%=cenvehiculo.getFechaven_soat() == null ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual) : new java.text.SimpleDateFormat("dd/MM/yyyy").format(cenvehiculo.getFechaven_soat())%>"/>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Tecnomecanica (*)</label>
                                        <select class="form-control" id="cmbtecno" name="cmbtecno">
                                            <%
                                                if (cenvehiculo.getTecno_mecanica().equals("S")) {
                                            %>
                                            <option value="S" selected>Si</option>
                                            <option value="N">No</option>
                                            <%
                                            } else {
                                            %>
                                            <option value="S">Si</option>
                                            <option value="N" selected>No</option>
                                            <%
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Fecha Venc. Tecnomecanica</label>
                                        <input class="form-control" type="text" id="txtfechavtecno" name="txtfechavtecno" readonly="true" value="<%=cenvehiculo.getFechaven_tecno() == null ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual) : new java.text.SimpleDateFormat("dd/MM/yyyy").format(cenvehiculo.getFechaven_tecno())%>"/>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-md-10">
                                        Personas Asociadas
                                    </div>
                                    <div class="col-md-1">
                                        <a class="btn btn-primary" href="javascript:AgregarCamposPersonaModificar();">Agregar</a>
                                        <!--<button type="button" class="btn btn-xs btn-success" id="btnagregar" name="btnagregar" onclick="AgregarCamposPersona()">Agregar</button>-->
                                    </div>
                                    <div class="col-md-1">
                                        <a class="btn btn-danger" href="javascript:QuitarCampoPersonaModificar();">Quitar</a>
                                        <!--<button type="button" class="btn btn-xs btn-danger" id="btnquitar" name="btnquitar" onclick="QuitarCampoPersona()">Quitar</button>-->
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-md-2">
                                        <label>Tipo</label>
                                    </div>
                                    <div class="col-md-2">
                                        <label>Tipo Documento</label>
                                    </div>
                                    <div class="col-md-2">
                                        <label>Documento</label>
                                    </div>
                                    <div class="col-md-5">
                                        <label>Nombre</label>
                                    </div>
                                    <div class="col-md-1">
                                        <label>Anular</label>
                                    </div>
                                </div>
                                <div>
                                    <%
                                        List<HashMap> personasVehiculo = personaVehiculoDao.ListarHashPersonasVehiculoActivasByIdVehiculo(cenvehiculo.getId());

                                        if (personasVehiculo.size() > 0) {

                                            for (HashMap hash : personasVehiculo) {
                                                long idperveh = Long.parseLong(hash.get("PV_ID").toString());
                                    %>
                                    <div class="row form-group" id="registropersona<%= idperveh%>">
                                        <div class="col-md-2">
                                            <input class="form-control" type="text" id="tipopersona<%=idperveh%>" name="tipopersona<%=idperveh%>" value="<%= hash.get("TIPO_PERSONA") == null ? "" : hash.get("TIPO_PERSONA")%>" readonly="true">
                                        </div>
                                        <div class="col-md-2">
                                            <input class="form-control" type="text" id="tipodoc<%=idperveh%>" name="tipodoc<%=idperveh%>" value="<%= hash.get("TIPO_DOC") == null ? "" : hash.get("TIPO_DOC")%>" readonly="true">
                                        </div>
                                        <div class="col-md-2">
                                            <input class="form-control" type="text" id="documento<%=idperveh%>" name="documento<%=idperveh%>" value="<%= hash.get("DOCUMENTO") == null ? "" : hash.get("DOCUMENTO")%>" readonly="true">
                                        </div>
                                        <div class="col-md-5">
                                            <input class="form-control" type="text" id="nombre<%=idperveh%>" name="nombre<%=idperveh%>" value="<%= hash.get("NOMBRE") == null ? "" : hash.get("NOMBRE")%>" readonly="true">
                                        </div>
                                        <div class="col-md-1">
                                            <button class="btn btn-danger" onclick="anularPersonaVehiculo(<%=idperveh%>);">X</button>
                                        </div>

                                        <input type="hidden" id="txtplacamod" name="txtplacamod" value="<%= cenvehiculo.getPlaca_veh() == null ? "" : cenvehiculo.getPlaca_veh().trim()%>">
                                        <input type="hidden" id="txtmotormod" name="txtmotormod" value="<%= cenvehiculo.getMotor() == null ? "" : cenvehiculo.getMotor().trim()%>">
                                        <input type="hidden" id="txtchasismod" name="txtchasismod" value="<%= cenvehiculo.getChasis() == null ? "" : cenvehiculo.getChasis().trim()%>">
                                        <input type="hidden" id="txtseriemod" name="txtseriemod" value="<%= cenvehiculo.getSerie() == null ? "" : cenvehiculo.getSerie().trim()%>">
                                        <input type="hidden" id="estadoperveh<%=idperveh%>" name="estadoperveh<%=idperveh%>" value="1">

                                    </div>
                                    <%
                                        }

                                    } else {
                                    %>
                                    <div class="row form-group">
                                        <div class="col-md-12">
                                            <label>No se encontraron personas registradas</label>
                                        </div>
                                    </div>
                                    <%
                                        }
                                    %>
                                </div>
                                <div id="personasvehiculo">
                                </div>
                            </div>
                            <input type="hidden" id="txtcantpersonas" name="txtcantpersonas" value="0">
                        </div>

                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Observaciones
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="form-group col-md-12">
                                        <textarea id="txtobservaciones" name="txtobservaciones" maxlength="300" style="width: 100%" cols="3"><%= cenvehiculo.getObservaciones() == null ? "" : cenvehiculo.getObservaciones()%></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" id="idvehiculo" name="idvehiculo" value="<%= cenvehiculo.getId()%>">
                </div>
                <div class="row page-header">
                    <div class="form-group col-xs-12 col-sm-3 col-md-3">
                        <button type="button" class="btn btn-lg btn-success btn-block" onclick="modificarVehiculo()" id="btnregistrar" name="btnregistrar" >Guardar</button>
                    </div>
                </div>
            </form>
        </div>
        <%
        } else {
        %>
        <div class="panel panel-danger">    
            <div class="panel-heading">
                Vehiculo no registrado
            </div>
        </div>
        <%
                }

            }
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
