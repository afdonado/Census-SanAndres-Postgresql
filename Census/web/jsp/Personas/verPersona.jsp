<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.util.LinkedList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Datos Persona</title>

        <!-- Custom fonts for this template-->
        <link href="${pageContext.request.contextPath}/template/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/template/css/fonts-google.css" rel="stylesheet" type="text/css"/>

        <!-- Custom styles for this template-->
        <link href="${pageContext.request.contextPath}/template/css/sb-admin-2.min.css" rel="stylesheet">

        <!-- Custom styles for this page -->
        <link href="${pageContext.request.contextPath}/template/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    </head>
    <body id="page-top">
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("listarPersonas.jsp")) {
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
                            <h1 class="h3 mb-2 text-gray-800">Datos de la Persona</h1>

                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Datos de Identificación</h6>
                                </div>
                                <div class="card-body">
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Tipo Documento</label>
                                            <input class="form-control" type="text" id="txttipodocumento" name="txttipodocumento" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Documento</label>
                                            <input class="form-control" type="text" id="txtdocumento" name="txtdocumento" readonly="true">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Primer Nombre</label>
                                            <input class="form-control" type="text" id="txtprimernombre" name="txtprimernombre" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Segundo Nombre</label>
                                            <input class="form-control" type="text" id="txtsegundonombre" name="txtsegundonombre" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Primer Apellido</label>
                                            <input class="form-control" type="text" id="txtprimerapellido" name="txtprimerapellido" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Segundo Apellido</label>
                                            <input class="form-control" type="text" id="txtsegundoapellido" name="txtsegundoapellido" readonly="true">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Fecha Nacimiento</label>
                                            <input class="form-control" type="text" id="txtfechanacimiento" name="txtfechanacimiento" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Genero</label>
                                            <input class="form-control" type="text" id="txtgenero" name="txtgenero" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Grupo Sanguineo</label>
                                            <input class="form-control" type="text" id="txtgruposanguineo" name="txtgruposanguineo" readonly="true">
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
                                            <input class="form-control" type="text" id="txtdepartamento" name="txtdepartamento" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Municipio</label>
                                            <input class="form-control" type="text" id="txtmunicipio" name="txtmunicipio" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Dirección</label>
                                            <input class="form-control" type="text" id="txtdireccion" name="txtdireccion" readonly="true">
                                        </div>
                                    </div>
                                    <div class="form-group row">                                    
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Telefono</label>
                                            <input class="form-control" type="text" id="txttelefono" name="txttelefono" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Correo Electronico</label>
                                            <input class="form-control" type="text" id="txtemail" name="txtemail" readonly="true">
                                        </div>
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
                                            <input class="form-control" type="text" id="txtnumerolicencia" name="txtnumerolicencia" readonly="true">
                                        </div>
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Categoria</label>
                                            <input class="form-control" type="text" id="txtcategorialicencia" name="txtcategorialicencia" readonly="true">
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
                            <div class="row">
                            <%
                                if (((java.util.LinkedList) session.getAttribute("permisosUsuario")).contains("modificarPersona.jsp")) {
                            %>
                            <div class="form-group col-xs-6 col-sm-2 col-md-2">
                                <button type="button" class="btn btn-lg btn-success btn-block btneditar" id="btneditar" name="btneditar">Editar</button>
                            </div>
                            <%
                                }
                            %>
                            <div class="form-group col-xs-6 col-sm-2 col-md-2">
                                <button type="button" class="btn btn-lg btn-danger btn-block" id="btnvolver" name="btnvolver">Volver</button>
                            </div>
                        </div>
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
        <%        } else {
        %>
        <script type="text/javascript">
            alert("Su usuario no tiene permiso para acceder a esta pagina");
            window.parent.location.href = "dashboard";
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

        <script src="${pageContext.request.contextPath}/scripts/verPersona.js" type="text/javascript"></script>
    </body>
</html>