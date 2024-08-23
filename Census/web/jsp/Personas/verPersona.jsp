<%@page import="com.censo.modelo.dao.PersonaDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Ver Persona</title>

        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="../../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="../../scripts/Ajax.js" type="text/javascript"></script>

        <script src="../../scripts/personas.js" type="text/javascript"></script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("consultarPersona.jsp")) {
                    if (!request.getParameter("idpersona").equals("")) {
                        
                        PersonaDao personaDao = new PersonaDao();
                    
                        long idpersona = Long.parseLong(request.getParameter("idpersona"));

                        List<HashMap> datosPersona = personaDao.ListarPersonasById(idpersona);

                        if (datosPersona.size() > 0) {

                            for (HashMap hash : datosPersona) {
        %>
        <div class="container-fluid">
            <form id="frmdatospersona" method="post">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Datos Persona
                            </div>
                            <div class="panel-body">
                                <div class="row form-group">
                                    <div class="col-md-3">
                                        <label>Tipo Documento</label>
                                        <input class="form-control" type="text" id="txttipodoc" name="txttipodoc" value="<%= hash.get("TIPO_DOC") == null ? "" : hash.get("TIPO_DOC")%>" readonly="true">
                                    </div>
                                    <div class="col-md-3">
                                        <label>Documento</label>
                                        <input class="form-control" type="text" id="txtdocumento" name="txtdocumento" value="<%=hash.get("DOCUMENTO") == null ? "" : hash.get("DOCUMENTO")%>" readonly="true">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-3">
                                        <label>Primer Nombre</label>
                                        <input class="form-control" type="text" id="txtprinom" name="txtprinom" value="<%= hash.get("NOMBRE1") == null ? "" : hash.get("NOMBRE1")%>" readonly="true">
                                    </div>
                                    <div class="col-md-3">
                                        <label>Segundo Nombre</label>
                                        <input class="form-control" type="text" id="txtsegnom" name="txtsegnom" value="<%=hash.get("NOMBRE2") == null ? "" : hash.get("NOMBRE2")%>" readonly="true">
                                    </div>
                                    <div class="col-md-3">
                                        <label>Primer Apellido</label>
                                        <input class="form-control" type="text" id="txtpriape" name="txtpriape" value="<%=hash.get("APELLIDO1") == null ? "" : hash.get("APELLIDO1")%>" readonly="true">
                                    </div>
                                    <div class="col-md-3">
                                        <label>Segundo Apellido</label>
                                        <input class="form-control" type="text" id="txtsegape" name="txtsegape" value="<%=hash.get("APELLIDO2") == null ? "" : hash.get("APELLIDO2")%>" readonly="true">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-3">
                                        <label>Fecha Nacimiento</label>
                                        <input class="form-control" type="text" id="txtfechanac" name="txtfechanac" value="<%= hash.get("FECHA_NAC") == null ? "" : hash.get("FECHA_NAC")%>" readonly="true">
                                    </div>
                                    <div class="col-md-3">
                                        <label>Genero</label>
                                        <input class="form-control" type="text" id="txtgenero" name="txtgenero" value="<%= hash.get("GENERO") == null ? "" : hash.get("GENERO")%>" readonly="true">
                                    </div>
                                    <div class="col-md-3">
                                        <label>Grupo Sanguineo</label>
                                        <input class="form-control" type="text" id="txtgruposan" name="txtgruposan" value="<%= hash.get("GRUPO_SANGUINEO") == null ? "" : hash.get("GRUPO_SANGUINEO")%>" readonly="true">
                                    </div>
                                    <div class="col-md-3">
                                        <label>Correo Electronico</label>
                                        <input class="form-control" type="text" id="txtmail" name="txtmail" value="<%= hash.get("MAIL") == null ? "" : hash.get("MAIL")%>" readonly="true">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Datos Contacto
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-md-3">
                                        <label>Departamento</label>
                                        <input class="form-control" type="text" id="txtnomdep" name="txtnomdep" value="<%= hash.get("DEPARTAMENTO") == null ? "" : hash.get("DEPARTAMENTO")%>" readonly="true">
                                    </div>
                                    <div class="col-md-3">
                                        <label>Municipio</label>
                                        <input class="form-control" type="text" id="txtnommun" name="txtnommun" value="<%= hash.get("MUNICIPIO") == null ? "" : hash.get("MUNICIPIO")%>" readonly="true">
                                    </div>
                                    <div class="col-md-3">
                                        <label>Dirección</label>
                                        <input class="form-control" type="text" id="txtdireccion" name="txtdireccion" value="<%= hash.get("DIRECCION") == null ? "" : hash.get("DIRECCION")%>" readonly="true">
                                    </div>
                                    <div class="col-md-3">
                                        <label>Telefono</label>
                                        <input class="form-control" type="text" id="txttelefono" name="txttelefono" value="<%= hash.get("TELEFONO") == null ? "" : hash.get("TELEFONO")%>" readonly="true">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Datos Licencia Conducción
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-md-3">
                                        <label>No. Licencia Conduccion</label>
                                        <input class="form-control" type="text" id="txtnumlicond" name="txtnumlicond" value="<%= hash.get("LIC_CONDUCCION") == null ? "" : hash.get("LIC_CONDUCCION") %>" readonly="true">
                                    </div>
                                    <div class="col-md-3">
                                        <label>Categoria</label>
                                        <input class="form-control" type="text" id="cmdcatlicencia" name="cmdcatlicencia" value="<%= hash.get("CATEGORIA_LIC") == null ? "" : hash.get("CATEGORIA_LIC") %>" readonly="true">
                                    </div>
                                    <div class="col-md-3">
                                        <label>Fecha Expedicion</label>
                                        <input class="form-control" type="text" id="txtfechaexplic" name="txtfechaexplic" value="<%= hash.get("FECHA_EXP") == null ? "" : hash.get("FECHA_EXP") %>" readonly="true">
                                    </div>
                                    <div class="col-md-3">
                                        <label>Fecha Vencimiento</label>
                                        <input class="form-control" type="text" id="txtfechavenlic" name="txtfechavenlic" value="<%= hash.get("FECHA_VEN") == null ? "" : hash.get("FECHA_VEN") %>" readonly="true">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" id="idpersona" name="idpersona" value="<%=idpersona%>">
                        <%
                            if (((java.util.LinkedList) session.getAttribute("permisosUsuario")).contains("modificarPersona.jsp")) {
                        %>
                        <div class="row page-header">
                            <div class="form-group col-xs-12 col-sm-3 col-md-3">
                                <button type="button" class="btn btn-lg btn-info btn-block" onclick="modificarPersonaById('<%=idpersona%>')" id="btnmodificar" name="btnmodificar" >Modificar</button>
                            </div>
                        </div>
                        <%
                                }
                            }
                        } else {
                        %>
                        <div class="panel panel-danger">    
                            <div class="panel-heading">
                                Persona no registrada
                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>
            </form>
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