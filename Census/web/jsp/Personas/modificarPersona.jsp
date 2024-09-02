<%@page import="com.censo.modelo.dao.CategoriaLicenciaDao"%>
<%@page import="com.censo.modelo.dao.GrupoSanguineoDao"%>
<%@page import="com.censo.modelo.dao.GeneroDao"%>
<%@page import="com.censo.modelo.dao.DepartamentoDao"%>
<%@page import="com.censo.modelo.dao.MunicipioDao"%>
<%@page import="com.censo.modelo.dao.TipoDocumentoDao"%>
<%@page import="com.censo.modelo.dao.PersonaDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.censo.modelo.persistencia.CenTipoDocumento"%>
<%@page import="java.util.List"%>
<%@page import="com.censo.modelo.persistencia.CenCategoriaLicencia"%>
<%@page import="com.censo.modelo.persistencia.CenGenero"%>
<%@page import="com.censo.modelo.persistencia.CenGrupoSanguineo"%>
<%@page import="com.censo.modelo.persistencia.CenDepartamento"%>
<%@page import="com.censo.modelo.persistencia.CenPersona"%>
<%@page import="com.censo.modelo.persistencia.CenMunicipio"%>
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
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("modificarPersona.jsp")) {
                    if (!request.getParameter("idpersona").equals("")) {
                    
                        PersonaDao personaDao = new PersonaDao();
                        TipoDocumentoDao tipoDocumentoDao = new TipoDocumentoDao();
                        MunicipioDao municipioDao = new MunicipioDao();
                        DepartamentoDao departamentoDao = new DepartamentoDao();
                        GeneroDao generoDao = new GeneroDao();
                        GrupoSanguineoDao grupoSanguineoDao = new GrupoSanguineoDao();
                        CategoriaLicenciaDao categoriaLicenciaDao = new CategoriaLicenciaDao();
                        
                        long idpersona = Long.parseLong(request.getParameter("idpersona"));

                        java.sql.Date fechaActual = new java.sql.Date(new java.util.Date().getTime());


                        CenPersona cenpersona = personaDao.ConsultarPersonaById(idpersona);
                        if (cenpersona != null) {
                            CenTipoDocumento centipodocumento = tipoDocumentoDao.ConsultarTipoDocumentoById(cenpersona.getTipodocumento());
                            CenDepartamento cendepartamentoPer = new CenDepartamento();
                            CenMunicipio cenmunicipioPer = municipioDao.ConsultarMunicipioById(cenpersona.getMun_id());
                            if (cenmunicipioPer != null) {
                                cendepartamentoPer = departamentoDao.ConsultarDepartamentoById(cenmunicipioPer.getDep_id());
                            }

        %>
        <div class="container-fluid">
            <form role="form" id="frmmodificarpersona" action="../../modificarPersona">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Datos Basicos
                            </div>
                            <div class="panel-body">
                                <div class="row form-group">
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Tipo Documento</label>
                                        <select class="form-control" id="cmbtipodoc" name="cmbtipodoc">
                                            <%
                                                List listaTiposDocumento = tipoDocumentoDao.ListarTiposDocumento();

                                                for (int i = 0; i < listaTiposDocumento.size(); i++) {
                                                    centipodocumento = (CenTipoDocumento) listaTiposDocumento.get(i);
                                                    if (centipodocumento.getId() == cenpersona.getTipodocumento()) {
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
                                        <input class="form-control" type="text" id="txtdocumento" name="txtdocumento" onKeyPress=" return validarNumeros(event)" onblur="consultarPersonaModificar(1)" maxlength="20" style="text-transform: uppercase" value="<%= cenpersona.getDocumento() == null ? "" : cenpersona.getDocumento().trim()%>" required>
                                    </div>
                                </div>

                                <div class="row form-group">
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Primer Nombre (*)</label>
                                        <input class="form-control" type="text" id="txtprinom" name="txtprinom" onKeyPress=" return validarLetras(event)" maxlength="80" style="text-transform: uppercase" value="<%=cenpersona.getNombre1() == null ? "" : cenpersona.getNombre1()%>" required>
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Segundo Nombre</label>
                                        <input class="form-control" type="text" id="txtsegnom" name="txtsegnom" onKeyPress=" return validarLetras(event)" maxlength="80" style="text-transform: uppercase" value="<%=cenpersona.getNombre2() == null ? "" : cenpersona.getNombre2()%>">
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Primer Apellido (*)</label>
                                        <input class="form-control" type="text" id="txtpriape" name="txtpriape" onKeyPress=" return validarLetras(event)" maxlength="80" style="text-transform: uppercase" value="<%=cenpersona.getApellido1() == null ? "" : cenpersona.getApellido1()%>" required>
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Segundo Apellido</label>
                                        <input class="form-control" type="text" id="txtsegape" name="txtsegape" onKeyPress=" return validarLetras(event)" maxlength="80" style="text-transform: uppercase" value="<%=cenpersona.getApellido2() == null ? "" : cenpersona.getApellido2()%>">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Fecha Nacimiento (*)</label>
                                        <input class="form-control" type="text" id="txtfechanac" name="txtfechanac" readonly="true" value="<%= cenpersona.getFechanacimiento() == null ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual) : new java.text.SimpleDateFormat("dd/MM/yyyy").format(cenpersona.getFechanacimiento())%>" onclick="datepickFechaNac()"/>
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Genero (*)</label>
                                        <select class="form-control" id="cmbgenero" name="cmbgenero">
                                            <%
                                                List listaGeneros = generoDao.ListarGeneros();
                                                for (int i = 0; i < listaGeneros.size(); i++) {
                                                    CenGenero cengenero = (CenGenero) listaGeneros.get(i);
                                                    if (cengenero.getId() == cenpersona.getGenero()) {
                                            %>
                                            <option value="<%=cengenero.getId()%>" selected><%=cengenero.getDescripcion()%></option>
                                            <% } else {%>
                                            <option value="<%=cengenero.getId()%>"><%=cengenero.getDescripcion()%></option>
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
                                                    if (cengruposanguineo.getId() == cenpersona.getGruposanguineo()) {
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
                        </div>
                        <div class="panel panel-info">
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
                                                    if (cendepartamento.getId() == cendepartamentoPer.getId()) {
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
                                                java.util.List listaMunicipios = municipioDao.ListarMunicipiosByIdDepto(cendepartamentoPer.getId());
                                                for (int i = 0; i < listaMunicipios.size(); i++) {
                                                    CenMunicipio cenmunicipio = (CenMunicipio) listaMunicipios.get(i);
                                                    if (cenmunicipio.getId() == cenmunicipioPer.getId()) {
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
                                        <input class="form-control" type="text" id="txtdireccion" name="txtdireccion" maxlength="80" style="text-transform: uppercase" value="<%=cenpersona.getDireccion() == null ? "" : cenpersona.getDireccion()%>" required>
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Telefono (*)</label>
                                        <input class="form-control" type="text" id="txttelefono" name="txttelefono" maxlength="30" style="text-transform: uppercase" value="<%=cenpersona.getTelefono() == null ? "" : cenpersona.getTelefono()%>" required>
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Correo Electronico</label>
                                        <input class="form-control" type="text" id="txtmail" name="txtmail" maxlength="100" onblur="validateMail('txtmail')" style="text-transform: uppercase" value="<%=cenpersona.getMail() == null ? "" : cenpersona.getMail()%>">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Datos Licencia Conduccion
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>No. Licencia Conduccion</label>
                                        <input class="form-control" type="text" id="txtnumlicond" name="txtnumlicond" onKeyPress=" return validarNumeros(event)" maxlength="30" style="text-transform: uppercase" value="<%=cenpersona.getLicenciaconduccion() == null ? "" : cenpersona.getLicenciaconduccion()%>">
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Categoria</label>
                                        <select class="form-control" id="cmdcatlicencia" name="cmdcatlicencia">
                                            <%
                                                List listaCategoriasLicencia = categoriaLicenciaDao.ListarCategoriasLicencia();
                                                for (int i = 0; i < listaCategoriasLicencia.size(); i++) {
                                                    CenCategoriaLicencia cencategorialicencia = (CenCategoriaLicencia) listaCategoriasLicencia.get(i);
                                                    if (cencategorialicencia.getId() == cenpersona.getCategorialicencia()) {
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
                                        <input class="form-control" type="text" id="txtfechaexplic" name="txtfechaexplic" readonly="true" value="<%= cenpersona.getFechaexp() == null ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual) : new java.text.SimpleDateFormat("dd/MM/yyyy").format(cenpersona.getFechaexp())%>" onclick="datepickFechaExp()"/>
                                    </div>
                                    <div class="col-xs-12 col-sm-3 col-md-3">
                                        <label>Fecha Vencimiento</label>
                                        <input class="form-control" type="text" id="txtfechavenlic" name="txtfechavenlic" readonly="true" value="<%=cenpersona.getFechaven() == null ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaActual) : new java.text.SimpleDateFormat("dd/MM/yyyy").format(cenpersona.getFechaven())%>" onclick="datepickFechaVen()"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <input type="hidden" id="idpersona" name="idpersona" value="<%= cenpersona.getId()%>">
                <input type="hidden" id="tipodocmod" name="tipodocmod" value="<%= cenpersona.getTipodocumento()%>">
                <input type="hidden" id="documentomod" name="documentomod" value="<%= cenpersona.getDocumento()%>">
                <div class="row page-header">
                    <div class="form-group col-xs-12 col-sm-3 col-md-3">
                        <button type="button" class="btn btn-lg btn-success btn-block" onclick="modificarPersona()" id="btnregistrar" name="btnregistrar" >Guardar</button>
                    </div>
                </div>
            </form>
        </div>
        <%
        } else {
        %>
        <div class="panel panel-danger">    
            <div class="panel-heading">
                Documento no registrado
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

