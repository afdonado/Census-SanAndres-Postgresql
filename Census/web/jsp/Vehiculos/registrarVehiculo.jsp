<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.censo.modelo.dao.TipoDocumentoDao"%>
<%@page import="com.censo.modelo.dao.TipoPersonaDao"%>
<%@page import="com.censo.modelo.dao.TipoImportacionDao"%>
<%@page import="com.censo.modelo.dao.MunicipioDao"%>
<%@page import="com.censo.modelo.dao.DepartamentoDao"%>
<%@page import="com.censo.modelo.dao.PaisDao"%>
<%@page import="com.censo.modelo.dao.TipoUsoDao"%>
<%@page import="com.censo.modelo.dao.TipoServicioDao"%>
<%@page import="com.censo.modelo.dao.ClaseVehiculoDao"%>
<%@page import="com.censo.modelo.dao.VehiculoDao"%>
<%@page import="com.censo.modelo.persistencia.CenTipoDocumento"%>
<%@page import="com.censo.modelo.persistencia.CenTipoPersona"%>
<%@page import="com.censo.modelo.persistencia.CenDepartamento"%>
<%@page import="com.censo.modelo.persistencia.CenMunicipio"%>
<%@page import="com.censo.modelo.persistencia.CenTipoUso"%>
<%@page import="com.censo.modelo.persistencia.CenLinea"%>
<%@page import="com.censo.modelo.persistencia.CenMarca"%>
<%@page import="com.censo.modelo.persistencia.CenPais"%>
<%@page import="com.censo.modelo.persistencia.CenClaseVehiculo"%>
<%@page import="com.censo.modelo.persistencia.CenTipoServicio"%>
<%@page import="com.censo.modelo.persistencia.CenTipoImportacion"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Registrar Vehiculo</title>

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
        <script src="../../scripts/cargarMarcas.js" type="text/javascript"></script>
        <script src="../../scripts/cargarLineas.js" type="text/javascript"></script>
        <script src="../../scripts/cargarColores.js" type="text/javascript"></script>
        <script src="../../scripts/fechas.js" type="text/javascript"></script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("registrarVehiculo.jsp")) {
                    
                    ClaseVehiculoDao claseVehiculoDao = new ClaseVehiculoDao();
                    TipoServicioDao tipoServicioDao = new TipoServicioDao();
                    TipoUsoDao tipoUsoDao = new TipoUsoDao();
                    PaisDao paisDao = new PaisDao();
                    DepartamentoDao departamentoDao = new DepartamentoDao();
                    MunicipioDao municipioDao = new MunicipioDao();
                    TipoImportacionDao tipoImportacionDao = new TipoImportacionDao();
                    TipoPersonaDao tipoPersonaDao = new TipoPersonaDao();
                    TipoDocumentoDao tipoDocumentoDao = new TipoDocumentoDao();
                    
                    Date fechaActual = new Date(new java.util.Date().getTime());
                    int opcion = 0;
                    int tipoRefencia = 0;
                    String referencia = "";
                    String placa = "", motor = "", chasis = "", serie = "";
                    String readonlyplaca = "";
                    String readonlymotor = "";
                    String readonlychasis = "";
                    String readonlyserie = "";
                    if (request.getParameter("opcion") != null) {
                        opcion = Integer.parseInt(request.getParameter("opcion"));
                    }
        %>
        <div class="modal fade" id="registrarpersona" name="registrarpersona" role="dialog" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
            <%
                if (opcion == 1) {
            %>
            <div class="row">
                <div class="col-md-12">
                    <h1 class="page-header">Vehiculos - Registrar Vehiculo</h1>
                </div>
            </div>
            <%
                }
            %>
            <form role="form" id="frmregistrarvehiculo" action="../../registrarVehiculo">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Datos de Identificacion
                            </div>
                            <%
                                if (opcion == 2) {
                                    tipoRefencia = Integer.parseInt(request.getParameter("tiporeferencia"));
                                    referencia = request.getParameter("referencia").toUpperCase().trim();

                                    if (tipoRefencia == 1) {
                                        placa = referencia;
                                        readonlyplaca = "readonly";
                                    }
                                    if (tipoRefencia == 2) {
                                        motor = referencia;
                                        readonlymotor = "readonly";
                                    }
                                    if (tipoRefencia == 3) {
                                        chasis = referencia;
                                        readonlychasis = "readonly";
                                    }
                                    if (tipoRefencia == 4) {
                                        serie = referencia;
                                        readonlyserie = "readonly";
                                    }
                                }

                            %>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="form-group col-md-3">
                                        <label>Placa</label>
                                        <input class="form-control" type="text" id="txtplaca" name="txtplaca" onblur="verificarVehiculo(1, 'txtplaca')" maxlength="10" style="text-transform: uppercase" value="<%=placa%>" <%=readonlyplaca%> required>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Motor (*)</label>
                                        <input class="form-control" type="text" id="txtmotor" name="txtmotor" onblur="verificarVehiculo(2, 'txtmotor')" maxlength="30" style="text-transform: uppercase" value="<%=motor%>" <%=readonlymotor%> required>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Chasis (*)</label>
                                        <input class="form-control" type="text" id="txtchasis" name="txtchasis" onblur="verificarVehiculo(3, 'txtchasis')" maxlength="30"style="text-transform: uppercase" value="<%=chasis%>" <%=readonlychasis%> required>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Serie</label>
                                        <input class="form-control" type="text" id="txtserie" name="txtserie" onblur="verificarVehiculo(4, 'txtserie')" maxlength="30" style="text-transform: uppercase" value="<%=serie%>" <%=readonlyserie%> required>
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
                                        <label>Clase(*)</label>
                                        <select class="form-control" id="cmbclaseveh" name="cmbclaseveh">
                                            <%
                                                List listaClasesVehiculo = claseVehiculoDao.ListarClasesVehiculo();
                                                for (int i = 0; i < listaClasesVehiculo.size(); i++) {
                                                    CenClaseVehiculo cenclasevehiculo = (CenClaseVehiculo) listaClasesVehiculo.get(i);
                                                    if (cenclasevehiculo.getId() == 1) {
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
                                        <label>Servicio(*)</label>
                                        <select class="form-control" id="cmbservicio" name="cmbservicio">
                                            <%
                                                List listaTiposServicio = tipoServicioDao.ListarTiposServicio();
                                                for (int i = 0; i < listaTiposServicio.size(); i++) {
                                                    CenTipoServicio Centiposervicio = (CenTipoServicio) listaTiposServicio.get(i);
                                                    if (Centiposervicio.getId() == 1) {
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
                                                    if (centipouso.getId() == 1) {
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
                                        <label>Color(*)</label>
                                        <input class="form-control" id="txtcolores" name="txtcolores" style="text-transform: uppercase">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-3">
                                        <label>Marca(*)</label>
                                        <input class="form-control" id="txtmarcas" name="txtmarcas" style="text-transform: uppercase">
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Linea(*)</label>
                                        <input class="form-control" id="txtlineas" name="txtlineas" style="text-transform: uppercase">
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Modelo (*)</label>
                                        <input class="form-control" type="text" id="txtmodelo" name="txtmodelo" onKeyPress=" return validarNumeros(event)" maxlength="4" required>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Transformado (*)</label>
                                        <select class="form-control" id="cmbtransformado" name="cmbtransformado">
                                            <option value="S" selected>Si</option>
                                            <option value="N">No</option>
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
                                            <option value="S" selected>Si</option>
                                            <option value="N">No</option>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-3" id="lictransito" >
                                        <label>Licencia Transito</label>
                                        <input class="form-control" type="text" id="txtlictransito" name="txtlictransito" value="" style="text-transform: uppercase"/>
                                    </div>
                                    <div class="form-group col-md-3" id="fechamatri">
                                        <label>Fecha Matricula</label>
                                        <input class="form-control" type="text" id="txtfechamatri" name="txtfechamatri" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
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
                                                    if (cenpais.getId() == 18) {
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
                                    <div class="form-group col-md-3" id="comboDepMatri" id="deptmatri">
                                        <label>Departamento</label>

                                        <select class="form-control" id="cmbdepartamentomatri" name="cmbdepartamentomatri" onChange="consultarMunicipiosByDepto('cmbdepartamentomatri', 'cmbmunicipiomatri')
                                                return false" onFocus="consultarMunicipiosByDepto('cmbdepartamentomatri', 'cmbmunicipiomatri')">
                                            <%
                                                List listaDepartamentosMatri = departamentoDao.ListarDepartamentos();

                                                for (int i = 0; i < listaDepartamentosMatri.size(); i++) {
                                                    CenDepartamento cendepartamento = (CenDepartamento) listaDepartamentosMatri.get(i);
                                                    if (cendepartamento.getId() == 4) {
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
                                                java.util.List listaMunicipiosMatricula = municipioDao.ListarMunicipiosByIdDepto(4);
                                                for (int i = 0; i < listaMunicipiosMatricula.size(); i++) {
                                                    CenMunicipio cenmunicipio = (CenMunicipio) listaMunicipiosMatricula.get(i);
                                                    if (cenmunicipio.getId() == 144) {
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
                                        <input class="form-control" type="text" id="txtciudadmatri" name="txtciudadmatri" maxlength="80" style="text-transform: uppercase" required>
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
                                                    if (centipoimportacion.getId() == 1) {
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
                                        <input class="form-control" type="text" id="txtdocimportacion" name="txtdocimportacion" maxlength="80" style="text-transform: uppercase">
                                    </div>
                                    <div class="form-group col-md-3" id="fechaImp">
                                        <label>Fecha Importacion (*)</label>
                                        <input class="form-control" type="text" id="txtfechaimportacion" name="txtfechaimportacion" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                    </div>
                                    <div class="form-group col-md-3" id="paisImp">
                                        <label>Pais</label>
                                        <select class="form-control" id="cmbpaisimportacion" name="cmbpaisimportacion" onChange="habilitarCombosDepMun() return false" onFocus="habilitarCombosDepMun()">
                                            <%
                                                List listaPaisesImportacion = paisDao.ListarPaises();

                                                for (int i = 0; i < listaPaisesImportacion.size(); i++) {
                                                    CenPais cenPais = (CenPais) listaPaisesImportacion.get(i);
                                                    if (cenPais.getId() == 18) {
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
                                            <option value="S" selected>Si</option>
                                            <option value="N">No</option>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Fecha Venc. Soat</label>
                                        <input class="form-control" type="text" id="txtfechavsoat" name="txtfechavsoat" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Tecnomecanica (*)</label>
                                        <select class="form-control" id="cmbtecno" name="cmbtecno">
                                            <option value="S" selected>Si</option>
                                            <option value="N">No</option>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label>Fecha Venc. Tecnomecanica</label>
                                        <input class="form-control" type="text" id="txtfechavtecno" name="txtfechavtecno" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
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
                                        <a class="btn btn-primary" href="javascript:AgregarCamposPersona();">Agregar</a>
                                    </div>
                                    <div class="col-md-1">
                                        <a class="btn btn-danger" href="javascript:QuitarCampoPersona();">Quitar</a>
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
                                    <div class="col-md-3">
                                        <label>Documento</label>
                                    </div>
                                    <div class="col-md-5">
                                        <label>Nombre</label>
                                    </div>
                                </div>
                                <div id="personasvehiculo">
                                    <div id="contenedor1" class="row">
                                        <div class="form-group col-md-2">
                                            <select class="form-control" id="cmbtipopersona1" name="cmbtipopersona1">
                                                <%
                                                    List listaTiposPersona = tipoPersonaDao.ListarTiposPersona();

                                                    for (int i = 0; i < listaTiposPersona.size(); i++) {
                                                        CenTipoPersona centipopersona = (CenTipoPersona) listaTiposPersona.get(i);
                                                        if (centipopersona.getId() == 1) {
                                                %>
                                                <option value="<%=centipopersona.getId()%>" selected><%=centipopersona.getDescripcion()%></option>
                                                <% } else {%>
                                                <option value="<%=centipopersona.getId()%>"><%=centipopersona.getDescripcion()%></option>
                                                <%
                                                        }
                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <div class="form-group col-md-2">
                                            <select class="form-control" id="cmbtipodoc1" name="cmbtipodoc1">
                                                <%
                                                    List listaTiposDocumento = tipoDocumentoDao.ListarTiposDocumento();

                                                    for (int i = 0; i < listaTiposDocumento.size(); i++) {
                                                        CenTipoDocumento centipodocumento = (CenTipoDocumento) listaTiposDocumento.get(i);
                                                        if (centipodocumento.getId() == 1) {
                                                %>
                                                <option value="<%=centipodocumento.getId()%>" selected><%=centipodocumento.getDescripcion()%></option>
                                                <% } else {%>
                                                <option value="<%=centipodocumento.getId()%>"><%=centipodocumento.getDescripcion()%></option>
                                                <%
                                                        }
                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <div class="form-group col-md-2">
                                            <input class="form-control" type="text" id="txtdocumento1" name="txtdocumento1" onKeyPress=" return validarNumeros(event)" onblur="consultarPersonaRegVeh(1, 'cmbtipodoc1', 'txtdocumento1', 'txtnombre1', 'idpersona1')" maxlength="20" style="text-transform: uppercase" required>
                                        </div>
                                        <div class="form-group col-md-5">
                                            <input class="form-control" type="text" id="txtnombre1" name="txtnombre1" readonly="true">
                                        </div>
                                        <input type="hidden" id="idpersona1" name="idpersona1">
                                    </div>
                                </div>

                            </div>
                            <input type="hidden" id="txtcantpersonas" name="txtcantpersonas" value="1">
                        </div>

                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Observaciones
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="form-group col-md-12">
                                        <textarea id="txtobservaciones" name="txtobservaciones" maxlength="300" style="width: 100%" cols="3"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row page-header">
                    <div class="form-group col-xs-12 col-sm-3 col-md-3">
                        <button type="button" class="btn btn-lg btn-success btn-block" onclick="registrarVehiculo()" id="btnregistrar" name="btnregistrar" >Registrar</button>
                    </div>
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
