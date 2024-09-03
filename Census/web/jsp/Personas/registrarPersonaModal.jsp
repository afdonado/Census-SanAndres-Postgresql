<%@page import="com.censo.modelo.dao.GrupoSanguineoDao"%>
<%@page import="com.censo.modelo.dao.CategoriaLicenciaDao"%>
<%@page import="com.censo.modelo.dao.MunicipioDao"%>
<%@page import="com.censo.modelo.dao.DepartamentoDao"%>
<%@page import="com.censo.modelo.dao.GeneroDao"%>
<%@page import="com.censo.modelo.dao.TipoDocumentoDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.List"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.util.LinkedList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Registrar Persona</title>

        <!-- Custom fonts for this template-->
        <link href="${pageContext.request.contextPath}/template/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/template/css/fonts-google.css" rel="stylesheet" type="text/css"/>

        <!-- Custom styles for this template-->
        <link href="${pageContext.request.contextPath}/template/css/sb-admin-2.min.css" rel="stylesheet">

        <!-- Custom styles for this page -->
        <link href="${pageContext.request.contextPath}/template/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

        <link href="${pageContext.request.contextPath}/template/vendor/jquery-ui-1.12.1.Redmond/jquery-ui.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/template/vendor/jquery/calendario_es.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("registrarPersona.jsp")) {

                    int opcion = Integer.parseInt(request.getParameter("opcion"));
                    int tipoDocumento = Integer.parseInt(request.getParameter("tipodoc"));
                    String documento = request.getParameter("documento").toUpperCase().trim();
                        
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

        <div id="wrapper">

                <div id="content-wrapper" class="d-flex flex-column">
                    <div id="content">
                        <div class="container-fluid">
                        <form class="user" id="frmregistrarpersona">
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Datos de Identificación</h6>
                                </div>
                                <div class="card-body">
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Tipo Documento</label>
                                            <select class="form-control" id="cmbtiposdocumento" name="cmbtiposdocumento" value="<%=tipoDocumento%>" readonly="true" required="true"></select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Documento</label>
                                            <input class="form-control solo-numeros" type="text" id="txtdocumento" name="txtdocumento" maxlength="20" style="text-transform: uppercase" value="<%=documento%>" readonly="true" required="true">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Primer Nombre(*)</label>
                                            <input class="form-control solo-letras" type="text" id="txtprimernombre" name="txtprimernombre" maxlength="80" style="text-transform: uppercase" required="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Segundo Nombre</label>
                                            <input class="form-control solo-letras" type="text" id="txtsegundonombre" name="txtsegundonombre" maxlength="80" style="text-transform: uppercase">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Primer Apellido(*)</label>
                                            <input class="form-control solo-letras" type="text" id="txtprimerapellido" name="txtprimerapellido" maxlength="80" style="text-transform: uppercase" required="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Segundo Apellido</label>
                                            <input class="form-control solo-letras" type="text" id="txtsegundoapellido" name="txtsegundoapellido" maxlength="80" style="text-transform: uppercase">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Fecha Nacimiento(*)</label>
                                            <input class="form-control" type="text" id="txtfechanacimiento" name="txtfechanacimiento" readonly="true" required="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Genero(*)</label>
                                            <select class="form-control" id="cmbgeneros" name="cmbgeneros" required="true"></select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Grupo Sanguineo(*)</label>
                                            <select class="form-control" id="cmbgrupossanguineos" name="cmbgrupossanguineos" required="true"></select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Datos de Contacto</h6>
                                </div>
                                <div class="card-body">
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Departamento</label>
                                            <select class="form-control" id="cmbdepartamentos" name="cmbdepartamentos" required="true"></select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Municipio</label>
                                            <select class="form-control" id="cmbmunicipios" name="cmbmunicipios" required="true"></select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Dirección</label>
                                            <input class="form-control" type="text" id="txtdireccion" name="txtdireccion" maxlength="80" style="text-transform: uppercase" readonly="true">
                                        </div>
                                    </div>
                                    <div class="form-group row">                                    
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Telefono</label>
                                            <input class="form-control solo-numeros" type="text" id="txttelefono" name="txttelefono" maxlength="30" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Correo Electronico</label>
                                            <input class="form-control" type="email" id="txtemail" name="txtemail" maxlength="100" style="text-transform: uppercase" readonly="true">
                                        </div>
                                    </div>
                                </div>
                                <div class="card shadow mb-4">
                                    <div class="card-header py-3">
                                        <h6 class="m-0 font-weight-bold text-primary">Datos Licencia Conducción</h6>
                                    </div>
                                    <div class="card-body">
                                        <div class="form-group row">
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>No. Licencia Conduccion</label>
                                                <input class="form-control solo-numeros" type="text" id="txtnumerolicencia" name="txtnumerolicencia" maxlength="30" readonly="true">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Categoria</label>
                                                <select class="form-control" id="cmbcategoriaslicencia" name="cmbcategoriaslicencia" required="true"></select>
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Fecha Expedición</label>
                                                <input class="form-control" type="text" id="txtfechaexplicencia" name="txtfechaexplicencia" readonly="true">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Fecha Vencimiento</label>
                                                <input class="form-control" type="text" id="txtfechavlicencia" name="txtfechavlicencia" readonly="true">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-xs-6 col-sm-2 col-md-2">
                                    <button type="button" class="btn btn-lg btn-success btn-block" id="btnguardar" name="btnguardar" >Guardar</button>
                                </div>
                            </div>
                            <input type="hidden" id="opcion" name="opcion" value="<%=opcion%>">
                        </form>
                    </div>
                    </div>
                </div>
            </div>
        <%
        } else {
        %>
        <script type="text/javascript">
            alert("Su usuario no tiene permiso para acceder a esta pagina");
            window.parent.location.href = "../dashboard";
        </script>
        <%
            }
        } else {
        %>
        <script type="text/javascript">
            alert("Su sesion a terminado");
            document.location.href = "../../index.jsp";
        </script>
        <%
            }
        %>

        <!-- Bootstrap core JavaScript-->
        <script src="${pageContext.request.contextPath}/template/vendor/jquery/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/template/vendor/bootstrap/js/bootstrap.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="${pageContext.request.contextPath}/template/vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="${pageContext.request.contextPath}/template/js/sb-admin-2.min.js"></script>

        <script src="${pageContext.request.contextPath}/template/vendor/jquery-ui-1.12.1.Redmond/jquery-ui.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/template/vendor/jquery/calendario_es.js" type="text/javascript"></script>

        <script src="${pageContext.request.contextPath}/scripts/registrarPersona.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/scripts/personas.js" type="text/javascript"></script>

        <script src="${pageContext.request.contextPath}/scripts/validacionesCampos.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/scripts/fechas.js" type="text/javascript"></script>
    </body>
</html>
