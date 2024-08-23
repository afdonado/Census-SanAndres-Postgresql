<%@page import="com.censo.modelo.dao.PersonaVehiculoDao"%>
<%@page import="com.censo.modelo.dao.VehiculoDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
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

        <script src="../../scripts/vehiculos.js" type="text/javascript"></script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("consultarVehiculo.jsp")) {
                    if (!request.getParameter("idvehiculo").equals("")) {
                    
                        VehiculoDao vehiculoDao = new VehiculoDao();
                        PersonaVehiculoDao personaVehiculoDao = new PersonaVehiculoDao();
                    
                        long idvehiculo = Long.parseLong(request.getParameter("idvehiculo"));

                        List<HashMap> datosVehiculo = vehiculoDao.ListarVehiculosById(idvehiculo);

                        if (datosVehiculo.size() > 0) {

                            for (HashMap hash : datosVehiculo) {

        %>
        <div class="container-fluid">
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
                                    <input class="form-control" type="text" id="txtplaca" name="txtplaca" value="<%= hash.get("VEH_PLACA") == null ? "" : hash.get("VEH_PLACA")%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Motor</label>
                                    <input class="form-control" type="text" id="txtmotor" name="txtmotor" value="<%= hash.get("VEH_MOTOR") == null ? "" : hash.get("VEH_MOTOR")%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Chasis</label>
                                    <input class="form-control" type="text" id="txtchasis" name="txtchasis" value="<%= hash.get("VEH_CHASIS") == null ? "" : hash.get("VEH_CHASIS")%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Serie</label>
                                    <input class="form-control" type="text" id="txtserie" name="txtserie" value="<%= hash.get("VEH_SERIE") == null ? "" : hash.get("VEH_SERIE")%>" readonly="true">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            Datos Generales
                        </div>
                        <div class="panel-body">    
                            <div class="row form-group">
                                <div class="col-md-3">
                                    <label>Clase</label>
                                    <input class="form-control" type="text" id="txtclase" name="txtclase" value="<%= hash.get("CLASE") == null ? "" : hash.get("CLASE")%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Servicio</label>
                                    <input class="form-control" type="text" id="txtservicio" name="txtservicio" value="<%= hash.get("SERVICIO") == null ? "" : hash.get("SERVICIO")%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Tipo de Uso</label>
                                    <input class="form-control" type="text" id="txttipouso" name="txttipouso" value="<%= hash.get("TIPO_USO") == null ? "" : hash.get("TIPO_USO")%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Color</label>
                                    <input class="form-control" type="text" id="txtcolor" name="txtcolor" value="<%= hash.get("COLOR") == null ? "" : hash.get("COLOR")%>" readonly="true">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <label>Marca</label>
                                    <input class="form-control" type="text" id="txtmarca" name="txtmarca" value="<%= hash.get("MARCA") == null ? "" : hash.get("MARCA")%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Linea</label>
                                    <input class="form-control" type="text" id="txtlinea" name="txtlinea" value="<%= hash.get("LINEA") == null ? "" : hash.get("LINEA")%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Modelo</label>
                                    <input class="form-control" type="text" id="txtmodelo" name="txtmodelo" value="<%= hash.get("MODELO") == null ? "" : hash.get("MODELO")%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Transformado</label>
                                    <input class="form-control" type="text" id="txttransformado" name="txttransformado" value="<%= hash.get("TRANSFORMADO") == null ? "" : hash.get("TRANSFORMADO").equals("S") ? "SI" : "NO"%>" readonly="true">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            Datos Registro Inicial
                        </div>
                        <div class="panel-body">
                            <div class="row form-group">
                                <div class="col-md-3">
                                    <label>Registrado en Runt</label>
                                    <input class="form-control" type="text" id="txtrunt" name="txtrunt" value="<%= hash.get("RUNT") == null ? "" : hash.get("RUNT").equals("S") ? "SI" : "NO"%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Licencia Transito</label>
                                    <input class="form-control" type="text" id="txtlictransito" name="txtlictransito" value="<%= hash.get("LICENCIA_TRANSITO") == null ? "" : hash.get("LICENCIA_TRANSITO")%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Fecha Matricula</label>
                                    <input class="form-control" type="text" id="txtfechamatri" name="txtfechamatri" value="<%= hash.get("FECHA_MATRICULA") == null ? "" : hash.get("FECHA_MATRICULA")%>" readonly="true">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <label>Pais</label>
                                    <input class="form-control" type="text" id="txtpaismatricula" name="txtpaismatricula" value="<%= hash.get("PAIS_MATRICULA") == null ? "" : hash.get("PAIS_MATRICULA")%>" readonly="true">
                                </div>
                                <%
                                    if (hash.get("PAIS_MATRICULA") != null) {
                                        if (hash.get("PAIS_MATRICULA").equals("COLOMBIA")) {
                                %>
                                <div class="col-md-3">
                                    <label>Departamento</label>
                                    <input class="form-control" type="text" id="txtdepartamentomatri" name="txtdepartamentomatri" value="<%= hash.get("DPTO_MATRICULA") == null ? "" : hash.get("DPTO_MATRICULA")%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Municipio</label>
                                    <input class="form-control" type="text" id="txtmunicipiomatri" name="txtmunicipiomatri" value="<%= hash.get("MUNI_MATRICULA") == null ? "" : hash.get("MUNI_MATRICULA")%>" readonly="true">
                                </div>
                                <%
                                } else {
                                %>
                                <div class="col-md-3">
                                    <label>Ciudad</label>
                                    <input class="form-control" type="text" id="txtciudadmatri" name="txtciudadmatri" value="<%= hash.get("CIUDAD_MATRICULA") == null ? "" : hash.get("CIUDAD_MATRICULA")%>" readonly="true">
                                </div>
                                <%
                                        }
                                    }
                                %>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            Datos Importación
                        </div>
                        <div class="panel-body">
                            <div class="row form-group">
                                <div class="col-md-3">
                                    <label>Tipo Documento</label>
                                    <input class="form-control" type="text" id="txttipodocimportacion" name="txttipodocimportacion" value="<%= hash.get("TDOC_IMP") == null ? "" : hash.get("TDOC_IMP")%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Documento</label>
                                    <input class="form-control" type="text" id="txtdocimportacion" name="txtdocimportacion" value="<%= hash.get("DOCUMENTO_IMP") == null ? "" : hash.get("DOCUMENTO_IMP")%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Fecha</label>
                                    <input class="form-control" type="text" id="txtfechaimportacion" name="txtfechaimportacion" value="<%= hash.get("FECHA_IMP") == null ? "" : hash.get("FECHA_IMP")%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Pais</label>
                                    <input class="form-control" type="text" id="txtpaisimportacion" name="txtpaisimportacion" value="<%= hash.get("PAIS_IMP") == null ? "" : hash.get("PAIS_IMP")%>" readonly="true">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            Polizas y Certificados
                        </div>
                        <div class="panel-body">
                            <div class="row form-group">
                                <div class="col-md-3">
                                    <label>SOAT</label>
                                    <input class="form-control" type="text" id="txtsoat" name="txtrunt" value="<%= hash.get("SOAT") == null ? "" : hash.get("SOAT").equals("S") ? "SI" : "NO"%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Fecha Venc. Soat</label>
                                    <input class="form-control" type="text" id="txtfechavsoat" name="txtfechavsoat" value="<%= hash.get("FECHAV_SOAT") == null ? "" : hash.get("FECHAV_SOAT")%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Tecnomecanica</label>
                                    <input class="form-control" type="text" id="txttecno" name="txttecno" value="<%= hash.get("TECNO_MECANICA") == null ? "" : hash.get("TECNO_MECANICA").equals("S") ? "SI" : "NO"%>" readonly="true">
                                </div>
                                <div class="col-md-3">
                                    <label>Fecha Venc. Tecnomecanica</label>
                                    <input class="form-control" type="text" id="txtfechavtecno" name="txtfechavtecno" value="<%= hash.get("FECHAV_TECNO") == null ? "" : hash.get("FECHAV_TECNO")%>" readonly="true">
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
                                    <textarea readonly id="txtobservaciones" name="txtobservaciones" maxlength="300" style="width: 100%"><%= hash.get("OBSERVACIONES") == null ? "" : hash.get("OBSERVACIONES")%></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%
                        }
                    %>
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            Personas Asociadas
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
                                List<HashMap> personasVehiculo = personaVehiculoDao.ListarHashPersonasVehiculoActivasByIdVehiculo(idvehiculo);

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
                        <input type="hidden" id="idvehiculo" name="idvehiculo" value="<%=idvehiculo%>">
                        <%
                            if (((java.util.LinkedList) session.getAttribute("permisosUsuario")).contains("modificarVehiculo.jsp")) {
                        %>
                        <div class="row page-header">
                            <div class="form-group col-xs-12 col-sm-3 col-md-3">
                                <button type="button" class="btn btn-lg btn-info btn-block" onclick="modificarVehiculoById('<%=idvehiculo%>')" id="btnmodificar" name="btnmodificar">Modificar</button>
                            </div>
                        </div>
                        <%
                                }
                    } else {
                    %>
                    <div class="panel panel-danger">    
                        <div class="panel-heading">
                            Vehiculo no registrado
                        </div>
                    </div>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
        <%
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