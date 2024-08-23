<%@page import="com.censo.modelo.dao.PersonaVehiculoDao"%>
<%@page import="com.censo.modelo.dao.TipoDocumentoDao"%>
<%@page import="com.censo.modelo.dao.PersonaDao"%>
<%@page import="com.censo.modelo.dao.TipoPersonaDao"%>
<%@page import="com.censo.modelo.dao.PuntoAtencionDao"%>
<%@page import="com.censo.modelo.dao.VehiculoDao"%>
<%@page import="com.censo.modelo.dao.UsuarioDao"%>
<%@page import="com.censo.modelo.dao.CensoDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.censo.modelo.persistencia.CenUsuario"%>
<%@page import="com.censo.modelo.persistencia.CenPuntoAtencion"%>
<%@page import="com.censo.modelo.persistencia.CenCenso"%>
<%@page import="com.censo.modelo.persistencia.CenTipoDocumento"%>
<%@page import="com.censo.modelo.persistencia.CenTipoPersona"%>
<%@page import="com.censo.modelo.persistencia.CenVehiculo"%>
<%@page import="com.censo.modelo.persistencia.CenPersona"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="../../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="../../scripts/Ajax.js" type="text/javascript"></script>

        <script src="../../scripts/censo.js" type="text/javascript"></script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("consultarCenso.jsp")) {
                    if (!request.getParameter("idcenso").equals("")) {
                        
                        CensoDao censoDao = new CensoDao();
                        PuntoAtencionDao puntoAtencionDao = new PuntoAtencionDao();
                        UsuarioDao usuarioDao = new UsuarioDao();
                        VehiculoDao vehiculoDao = new VehiculoDao();
                        TipoPersonaDao tipoPersonaDao = new TipoPersonaDao();
                        PersonaDao personaDao = new PersonaDao();
                        TipoDocumentoDao tipoDocumentoDao = new TipoDocumentoDao();
                        PersonaVehiculoDao personaVehiculoDao = new PersonaVehiculoDao();
                        
                        long idcenso = Long.parseLong(request.getParameter("idcenso"));

                        CenCenso cencenso = censoDao.ConsultarCensoById(idcenso);

                        if (cencenso != null) {
                            CenPuntoAtencion cenpuntoatencion = puntoAtencionDao.ConsultarPuntoAtencionById(cencenso.getPun_id());
                            CenUsuario cenusuario = usuarioDao.ConsultarUsuarioById(cencenso.getUsu_id());
                            CenVehiculo cenvehiculo = vehiculoDao.ConsultarVehiculoById(cencenso.getVeh_id());

                            CenTipoPersona centipopersona = tipoPersonaDao.ConsultarTipoPersonaById(cencenso.getTper_id());
                            CenPersona cenpersona = personaDao.ConsultarPersonaById(cencenso.getPer_id());
                            CenTipoDocumento centipodocumento = tipoDocumentoDao.ConsultarTipoDocumentoById(cenpersona.getTipodocumento());
                            String nombreCompleto = cenpersona.getNombre1() + " " + (cenpersona.getNombre2() != null ? cenpersona.getNombre2().trim() : "")
                                    + " " + (cenpersona.getApellido1() != null ? cenpersona.getApellido1().trim() : "")
                                    + " " + (cenpersona.getApellido2() != null ? cenpersona.getApellido2().trim() : "");

        %>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            Datos del Censo
                        </div>
                        <div class="panel-body">
                            <div class="row form-group">
                                <div class="col-md-2">
                                    <label># Censo</label>
                                    <input class="form-control" type="text" id="txtnumero" name="txtnumero" value="<%= cencenso.getNumero() == null ? "" : cencenso.getNumero().trim()%>" readonly="true">
                                </div>
                                <div class="col-md-2">
                                    <label>Fecha y Hora</label>
                                    <input class="form-control" type="text" id="txtfechahora" name="txtfechahora" value="<%= cencenso.getFecha() == null || cencenso.getHora() == null ? "" : new java.text.SimpleDateFormat("dd/MM/yyyy").format(cencenso.getFecha()) + " " + cencenso.getHora()%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Punto Atención</label>
                                    <input class="form-control" type="text" id="txtpuntoaten" name="txtpuntoaten" value="<%= cenpuntoatencion == null ? "" : cenpuntoatencion.getNombre().trim()%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Usuario Registró</label>
                                    <input class="form-control" type="text" id="txtusureg" name="txtusureg" value="<%= cenusuario == null ? "" : cenusuario.getNombre().trim()%>" readonly="true">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            Identificacion del Vehiculo
                        </div>
                        <div class="panel-body">
                            <div class="row form-group">
                                <div class="col-md-3">
                                    <label>Placa</label>
                                    <input class="form-control" type="text" id="txtplaca" name="txtplaca" value="<%= cenvehiculo.getPlaca_veh() == null ? "" : cenvehiculo.getPlaca_veh().trim()%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Motor</label>
                                    <input class="form-control" type="text" id="txtmotor" name="txtmotor" value="<%= cenvehiculo.getMotor() == null ? "" : cenvehiculo.getMotor().trim()%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Chasis</label>
                                    <input class="form-control" type="text" id="txtchasis" name="txtchasis" value="<%= cenvehiculo.getChasis() == null ? "" : cenvehiculo.getChasis().trim()%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Serie</label>
                                    <input class="form-control" type="text" id="txtserie" name="txtserie" value="<%= cenvehiculo.getSerie() == null ? "" : cenvehiculo.getSerie().trim()%>" readonly="true">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            Personas Asociadas al Vehiculo
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
                            <%
                                List<HashMap> personasVehiculo = personaVehiculoDao.ListarHashPersonasVehiculoActivasByIdVehiculo(cenvehiculo.getId());

                                if (personasVehiculo.size() > 0) {

                                    for (HashMap hash : personasVehiculo) {
                            %>
                            <div class="row form-group">
                                <div class="col-md-2">
                                    <input class="form-control" type="text" id="txttipopersona" name="txttipopersona" value="<%= hash.get("TIPO_PERSONA") == null ? "" : hash.get("TIPO_PERSONA")%>" readonly="true">
                                </div>
                                <div class="col-md-2">
                                    <input class="form-control" type="text" id="cmbtipodoc" name="cmbtipodoc" value="<%= hash.get("TIPO_DOC") == null ? "" : hash.get("TIPO_DOC")%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <input class="form-control" type="text" id="txtdocumento" name="txtdocumento" value="<%= hash.get("DOCUMENTO") == null ? "" : hash.get("DOCUMENTO")%>" readonly="true">
                                </div>
                                <div class="col-md-5">
                                    <input class="form-control" type="text" id="txtnombre" name="txtnombre" value="<%= hash.get("NOMBRE") == null ? "" : hash.get("NOMBRE")%>" readonly="true">
                                </div>
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
                    </div>


                    <div class="panel panel-info">
                        <div class="panel-heading">
                            Persona Asociada Presento Vehiculo
                        </div>
                        <div class="panel-body">
                            <div class="row form-group">
                                <div class="col-md-2">
                                    <label>Tipo</label>
                                    <input class="form-control" type="text" id="txttipopersona" name="txttipopersona" value="<%= centipopersona == null ? "" : centipopersona.getDescripcion().trim()%>" readonly="true">
                                </div>
                                <div class="col-md-2">
                                    <label>Tipo Documento</label>
                                    <input class="form-control" type="text" id="cmbtipodoc" name="cmbtipodoc" value="<%= centipodocumento == null ? "" : centipodocumento.getDescripcion()%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Documento</label>
                                    <input class="form-control" type="text" id="txtdocumento" name="txtdocumento" value="<%= cenpersona.getDocumento() == null ? "" : cenpersona.getDocumento().trim()%>" readonly="true">
                                </div>
                                <div class="col-md-5">
                                    <label>Nombre</label>
                                    <input class="form-control" type="text" id="txtnombre" name="txtnombre" value="<%= nombreCompleto%>" readonly="true">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            Observaciones
                        </div>
                        <div class="panel-body">
                            <div class="row form-group">
                                <div class="col-md-12">
                                    <textarea readonly id="txtobservaciones" name="txtobservaciones" maxlength="300" style="width: 100%"><%= cencenso.getObservaciones() == null ? "" : cencenso.getObservaciones().trim()%></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="col-md-3">
                                <label></label>
                                <button type="button" class="btn btn-md btn-danger btn-block" onclick="ImprimirCensoById(<%=idcenso%>)" id="btngenerarreporte">Imprimir Certificado PDF</button>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" id="idcenso" name="idcenso" value="<%=idcenso%>"/>
                    <div id="page-wrapper">
                        <div class="row">
                            <iframe src="../Documentos/ListarDocumentos.jsp?idcenso=<%=idcenso%>" style="width: 100%; min-height: 1500px;" transparency="transparency" frameborder="0" ></iframe>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%
        } else {
        %>
        <div class="panel panel-danger">    
            <div class="panel-heading">
                Numero de censo no registrado
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