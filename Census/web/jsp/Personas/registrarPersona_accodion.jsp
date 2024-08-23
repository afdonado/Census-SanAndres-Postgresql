<%@page import="com.censo.modelo.dao.GrupoSanguineoDao"%>
<%@page import="com.censo.modelo.dao.CategoriaLicenciaDao"%>
<%@page import="com.censo.modelo.dao.MunicipioDao"%>
<%@page import="com.censo.modelo.dao.DepartamentoDao"%>
<%@page import="com.censo.modelo.dao.GeneroDao"%>
<%@page import="com.censo.modelo.dao.TipoDocumentoDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.List"%>
<%@page import="com.censo.modelo.persistencia.CenDepartamento"%>
<%@page import="com.censo.modelo.persistencia.CenMunicipio"%>
<%@page import="com.censo.modelo.persistencia.CenGenero"%>
<%@page import="com.censo.modelo.persistencia.CenGrupoSanguineo"%>
<%@page import="com.censo.modelo.persistencia.CenCategoriaLicencia"%>
<%@page import="com.censo.modelo.persistencia.CenTipoDocumento"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Registrar Persona</title>

        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="../../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../../scripts/Ajax.js" type="text/javascript"></script>

        <link href="../../vendor/jquery-ui-1.12.1.Redmond/jquery-ui.css" rel="stylesheet" type="text/css"/>
        <link href="../../vendor/jquery/calendario_es.css" rel="stylesheet" type="text/css"/>
        <script src="../../vendor/jquery-ui-1.12.1.Redmond/jquery-ui.js" type="text/javascript"></script>
        <script src="../../vendor/jquery/calendario_es.js" type="text/javascript"></script>

        <script src="../../scripts/validacionesCampos.js" type="text/javascript"></script>
        <script src="../../scripts/personas.js" type="text/javascript"></script>
        <script src="../../scripts/municipios.js" type="text/javascript"></script>

        <script>
            $(function () {
                $("#txtfechanac").datepicker();
            });
            $(function () {
                $("#txtfechaexplic").datepicker();
            });
            $(function () {
                $("#txtfechavenlic").datepicker();
            });
            $(function () {
                $("#accordion").accordion();
            });
        </script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("registrarPersona.jsp")) {
                
                    TipoDocumentoDao tipoDocumentoDao = new TipoDocumentoDao();
                    GeneroDao generoDao = new GeneroDao();
                    GrupoSanguineoDao grupoSanguineoDao = new GrupoSanguineoDao();
                    DepartamentoDao departamentoDao = new DepartamentoDao();
                    MunicipioDao municipioDao = new MunicipioDao();
                    CategoriaLicenciaDao categoriaLicenciaDao = new CategoriaLicenciaDao();

                    Date fechaActual = new Date(new java.util.Date().getTime());

                    CenTipoDocumento centipodocumento = new CenTipoDocumento();
                    int opcion = 0;
                    int tipoDoc = 0;
                    String documento = "";
                    if (request.getParameter("opcion") != null) {
                        opcion = Integer.parseInt(request.getParameter("opcion"));
                    }
        %>
        <!--Modal Respuesta Peticion-->
        <div class="modal fade" id="respuesta" name="respuesta" role="dialog" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">Respuesta Registro Persona</h4>
                    </div>
                    <div class="modal-body" id="respuestaRegistro">

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
                    <h1 class="page-header">Personas - Registrar Persona</h1>
                </div>
            </div>
            <%
                }
            %>
            <form role="form" id="frmregistrarpersona" action="../../registrarPersona">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-info" id="accordion">
                            <div class="panel-heading">
                                Datos Basicos
                            </div>
                            <div class="panel-body">
                                <%
                                    if (opcion == 2) {
                                        tipoDoc = Integer.parseInt(request.getParameter("tipodoc"));
                                        documento = request.getParameter("documento").toUpperCase().trim();
                                        centipodocumento = tipoDocumentoDao.ConsultarTipoDocumentoById(tipoDoc);
                                %>
                                <div class="row form-group">
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Tipo Documento</label>
                                        <input type="hidden" id="cmbtipodoc" name="cmbtipodoc" value="<%=tipoDoc%>"/>
                                        <input type="text" class="form-control" id="txtdesctipodoc" name="txtdesctipodoc" onpaste="return false" value="<%=centipodocumento.getDescripcion()%>" readonly="true" />
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Documento</label>
                                        <input type="text" class="form-control" id="txtdocumento" name="txtdocumento" onpaste="return false" value="<%=documento%>" size="20" readonly="true" />
                                    </div>
                                </div>
                                <%
                                } else {
                                %>
                                <div class="row form-group">
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Tipo Documento</label>
                                        <select class="form-control" id="cmbtipodoc" name="cmbtipodoc">
                                            <%
                                                List listaTiposDocumento = tipoDocumentoDao.ListarTiposDocumento();

                                                for (int i = 0; i < listaTiposDocumento.size(); i++) {
                                                    centipodocumento = (CenTipoDocumento) listaTiposDocumento.get(i);
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
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Documento (*)</label>
                                        <input class="form-control" type="text" id="txtdocumento" name="txtdocumento" onKeyPress=" return validarNumeros(event)" onblur="consultarPersona(1)" maxlength="20" style="text-transform: uppercase" required>
                                    </div>
                                </div>
                                <%
                                    }
                                %>
                                <div class="row form-group">
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Primer Nombre (*)</label>
                                        <input class="form-control" type="text" id="txtprinom" name="txtprinom" onKeyPress=" return validarLetras(event)" maxlength="80" style="text-transform: uppercase" required>
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Segundo Nombre</label>
                                        <input class="form-control" type="text" id="txtsegnom" name="txtsegnom" onKeyPress=" return validarLetras(event)" maxlength="80" style="text-transform: uppercase">
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Primer Apellido (*)</label>
                                        <input class="form-control" type="text" id="txtpriape" name="txtpriape" onKeyPress=" return validarLetras(event)" maxlength="80" style="text-transform: uppercase" required>
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Segundo Apellido</label>
                                        <input class="form-control" type="text" id="txtsegape" name="txtsegape" onKeyPress=" return validarLetras(event)" maxlength="80" style="text-transform: uppercase">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Fecha Nacimiento (*)</label>
                                        <input class="form-control" type="text" id="txtfechanac" name="txtfechanac" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Genero (*)</label>
                                        <select class="form-control" id="cmbgenero" name="cmbgenero">
                                            <%
                                                List listaGeneros = generoDao.ListarGeneros();
                                                for (int i = 0; i < listaGeneros.size(); i++) {
                                                    CenGenero cengenero = (CenGenero) listaGeneros.get(i);
                                                    if (cengenero.getId() == 1) {
                                            %>
                                            <option value="<%=cengenero.getDescripcion_corta()%>" selected><%=cengenero.getDescripcion()%></option>
                                            <% } else {%>
                                            <option value="<%=cengenero.getDescripcion_corta()%>"><%=cengenero.getDescripcion()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Grupo Sanguineo (*)</label>
                                        <select class="form-control" id="cmbgrusanguineo" name="cmbgrusanguineo">
                                            <%
                                                List listaGruposSanguineo = grupoSanguineoDao.ListarGruposSanguineo();
                                                for (int i = 0; i < listaGruposSanguineo.size(); i++) {
                                                    CenGrupoSanguineo cengruposanguineo = (CenGrupoSanguineo) listaGruposSanguineo.get(i);
                                                    if (cengruposanguineo.getId() == 1) {
                                            %>
                                            <option value="<%=cengruposanguineo.getId()%>" selected><%=cengruposanguineo.getDescripcion()%></option>
                                            <% } else {%>
                                            <option value="<%=cengruposanguineo.getId()%>"><%=cengruposanguineo.getDescripcion()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-heading">
                                Datos Contacto
                            </div>
                            <div class="panel-body">
                                <div class="row form-group">
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Departamento (*)</label>
                                        <select class="form-control" id="cmbdepartamentodir" name="cmbdepartamentodir" onChange="consultarMunicipiosByDepto('cmbdepartamentodir', 'cmbmunicipiodir')
                                                return false" onFocus="consultarMunicipiosByDepto('cmbdepartamentodir', 'cmbmunicipiodir')">
                                            <%
                                                List listaDepartamentos = departamentoDao.ListarDepartamentos();

                                                for (int i = 0; i < listaDepartamentos.size(); i++) {
                                                    CenDepartamento cendepartamento = (CenDepartamento) listaDepartamentos.get(i);
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
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Municipio (*)</label>

                                        <select class="form-control" id="cmbmunicipiodir" name="cmbmunicipiodir">
                                            <%
                                                java.util.List listaMunicipios = municipioDao.ListarMunicipiosByIdDepto(4);
                                                for (int i = 0; i < listaMunicipios.size(); i++) {
                                                    CenMunicipio cenmunicipio = (CenMunicipio) listaMunicipios.get(i);
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
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Direccion (*)</label>
                                        <input class="form-control" type="text" id="txtdireccion" name="txtdireccion" maxlength="80" style="text-transform: uppercase" required>
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Telefono (*)</label>
                                        <input class="form-control" type="text" id="txttelefono" name="txttelefono" maxlength="30" style="text-transform: uppercase" required>
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Correo Electronico</label>
                                        <input class="form-control" type="text" id="txtmail" name="txtmail" maxlength="100" onblur="validateMail('txtmail')" style="text-transform: uppercase">
                                    </div>
                                </div>
                            </div>
                            <div class="panel-heading">
                                Datos Licencia Conduccion
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>No. Licencia Conduccion</label>
                                        <input class="form-control" type="text" id="txtnumlicond" name="txtnumlicond" onKeyPress=" return validarNumeros(event)" maxlength="30" style="text-transform: uppercase">
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Categoria</label>
                                        <select class="form-control" id="cmdcatlicencia" name="cmdcatlicencia">
                                            <%
                                                List listaCategoriasLicencia = categoriaLicenciaDao.ListarCategoriasLicencia();
                                                for (int i = 0; i < listaCategoriasLicencia.size(); i++) {
                                                    CenCategoriaLicencia cencategorialicencia = (CenCategoriaLicencia) listaCategoriasLicencia.get(i);
                                                    if (cencategorialicencia.getId() == 1) {
                                            %>
                                            <option value="<%=cencategorialicencia.getId()%>" selected><%=cencategorialicencia.getDescripcion()%></option>
                                            <% } else {%>
                                            <option value="<%=cencategorialicencia.getId()%>"><%=cencategorialicencia.getDescripcion()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Fecha Expedicion</label>
                                        <input class="form-control" type="text" id="txtfechaexplic" name="txtfechaexplic" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Fecha Vencimiento</label>
                                        <input class="form-control" type="text" id="txtfechavenlic" name="txtfechavenlic" readonly="true" value="<%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual)%>"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" id="opcion" name="opcion" value="<%=opcion%>"/>
                    </div>
                </div>
                <div class="row page-header">
                    <div class="form-group col-xs-12 col-sm-3 col-md-3">
                        <button type="button" class="btn btn-lg btn-success btn-block" onclick="registrarPersona()" id="btnregistrar" name="btnregistrar" >Registrar</button>
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
