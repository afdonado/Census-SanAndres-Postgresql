<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.List"%>
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
    <body id="page-top">
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("registrarPersona.jsp")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate fecha = LocalDate.now();
                    String fechaActual = fecha.format(formatter);
        %>
        <div id="wrapper">
            <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
                <jsp:include page="/jsp/Menu.jsp"></jsp:include>
                </ul>

                <div id="content-wrapper" class="d-flex flex-column">
                    <div id="content">

                        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
                        <jsp:include page="/jsp/Header.jsp"></jsp:include>
                        </nav>

                        <div class="container-fluid">
                            <h1 class="h3 mb-2 text-gray-800">Registrar Persona</h1>
                            <form class="user" id="frmregistrarpersona">
                                <div class="card shadow mb-4">
                                    <div class="card-header py-3">
                                        <h6 class="m-0 font-weight-bold text-primary">Datos de Identificación</h6>
                                    </div>
                                    <div class="card-body">
                                        <div class="form-group row">
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Tipo Documento<spam style="color: red">(*)</spam></label>
                                                <select class="form-control" id="cmbtiposdocumento" name="cmbtiposdocumento" required="true"></select>
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Documento<spam style="color: red">(*)</spam></label>
                                                <input class="form-control solo-numeros" type="text" id="txtdocumento" name="txtdocumento" maxlength="20" style="text-transform: uppercase" required="true">
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <div id="procesando" style="display:none;">
                                                <spam style="color: red"><p>Consultado los datos en el runt, espere por favor...</p></spam>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Primer Nombre<spam style="color: red">(*)</spam></label>
                                                <input class="form-control solo-letras" type="text" id="txtprimernombre" name="txtprimernombre" maxlength="80" style="text-transform: uppercase" required="true">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Segundo Nombre</label>
                                                <input class="form-control solo-letras" type="text" id="txtsegundonombre" name="txtsegundonombre" maxlength="80" style="text-transform: uppercase">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Primer Apellido<spam style="color: red">(*)</spam></label>
                                                <input class="form-control solo-letras" type="text" id="txtprimerapellido" name="txtprimerapellido" maxlength="80" style="text-transform: uppercase" required="true">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Segundo Apellido</label>
                                                <input class="form-control solo-letras" type="text" id="txtsegundoapellido" name="txtsegundoapellido" maxlength="80" style="text-transform: uppercase">
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Fecha Nacimiento<spam style="color: red">(*)</spam></label>
                                                <input class="form-control" type="text" id="txtfechanacimiento" name="txtfechanacimiento" readonly="true" value="<%=fechaActual%>" required="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Genero<spam style="color: red">(*)</spam></label>
                                            <select class="form-control" id="cmbgeneros" name="cmbgeneros" required="true"></select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Grupo Sanguineo<spam style="color: red">(*)</spam></label>
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
                                            <label>Departamento<spam style="color: red">(*)</spam></label>
                                            <select class="form-control" id="cmbdepartamentos" name="cmbdepartamentos" required="true"></select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Municipio<spam style="color: red">(*)</spam></label>
                                            <select class="form-control" id="cmbmunicipios" name="cmbmunicipios" required="true"></select>
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Dirección<spam style="color: red">(*)</spam></label>
                                            <input class="form-control" type="text" id="txtdireccion" name="txtdireccion" maxlength="80" style="text-transform: uppercase" required="true">
                                        </div>
                                    </div>
                                    <div class="form-group row">                                    
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Telefono<spam style="color: red">(*)</spam></label>
                                            <input class="form-control solo-numeros" type="text" id="txttelefono" name="txttelefono" maxlength="30" required="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Correo Electronico<spam style="color: red">(*)</spam></label>
                                            <input class="form-control validar-email" type="text" id="txtemail" name="txtemail" maxlength="100" style="text-transform: uppercase" required="true">
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
                                                <label>Tiene licencia de conducción?<spam style="color: red">(*)</spam></label>
                                                <select class="form-control" id="cmblicenciaconduccion" name="cmblicenciaconduccion">
                                                    <option value="S" selected>Si</option>
                                                    <option value="N">No</option>
                                                </select>
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0 licencia-conduccion">
                                                <label>No. Licencia Conduccion<spam style="color: red">(*)</spam></label>
                                                <input class="form-control solo-numeros" type="text" id="txtnumerolicencia" name="txtnumerolicencia" maxlength="30" style="text-transform: uppercase">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0 licencia-conduccion">
                                                <label>Categoria<spam style="color: red">(*)</spam></label>
                                                <select class="form-control" id="cmbcategoriaslicencia" name="cmbcategoriaslicencia"></select>
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0 licencia-conduccion">
                                                <label>Fecha Expedición<spam style="color: red">(*)</spam></label>
                                                <input class="form-control" type="text" id="txtfechaexplicencia" name="txtfechaexplicencia" readonly="true" value="<%=fechaActual%>">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-xs-6 col-sm-2 col-md-2">
                                    <button type="button" class="btn btn-lg btn-success btn-block" id="btnguardar" name="btnguardar" >Guardar</button>
                                </div>
                                <div class="form-group col-xs-6 col-sm-2 col-md-2">
                                    <button type="button" class="btn btn-lg btn-danger btn-block" id="btnvolver" name="btnvolver">Volver</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <footer class="sticky-footer bg-white">
                    <jsp:include page="/jsp/Footer.jsp"></jsp:include>
                    </footer>
                </div>
            </div>
            <a class="scroll-to-top rounded" href="#page-top">
                <i class="fas fa-angle-up"></i>
            </a>
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