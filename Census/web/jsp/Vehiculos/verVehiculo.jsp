<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Datos Vehiculo</title>

        <!-- Custom fonts for this template-->
        <link href="${pageContext.request.contextPath}/template/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/template/css/fonts-google.css" rel="stylesheet" type="text/css"/>

        <!-- Custom styles for this template-->
        <link href="${pageContext.request.contextPath}/template/css/sb-admin-2.min.css" rel="stylesheet">

        <!-- Custom styles for this page -->
        <link href="${pageContext.request.contextPath}/template/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("consultarVehiculo.jsp")) {
        %>
        <c:set var="vehiculo" value="${requestScope.datosVehiculo}" />
        <c:set var="personas" value="${requestScope.personasVehiculo}" />
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
                            <h1 class="h3 mb-2 text-gray-800">Datos del Vehiculo</h1>

                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Datos de Identificación</h6>
                                </div>
                                <div class="card-body">
                                    <div class="form-group row">
                                        <div class="col-sm-3 mb-3 mb-sm-0">
                                            <label>Placa</label>
                                            <input class="form-control" type="text" id="txtplaca" name="txtplaca" value="${vehiculo.VEH_PLACA}" readonly="true">
                                    </div>
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Motor</label>
                                        <input class="form-control" type="text" id="txtmotor" name="txtmotor" value="${vehiculo.VEH_MOTOR}" readonly="true">
                                    </div>
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Chasis</label>
                                        <input class="form-control" type="text" id="txtchasis" name="txtchasis" value="${vehiculo.VEH_CHASIS}" readonly="true">
                                    </div>
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Serie</label>
                                        <input class="form-control" type="text" id="txtserie" name="txtserie" value="${vehiculo.VEH_SERIE}" readonly="true">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Datos Generales</h6>
                            </div>
                            <div class="card-body">
                                <div class="form-group row">
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Clase</label>
                                        <input class="form-control" type="text" id="txtclase" name="txtclase" value="${vehiculo.CLASE}" readonly="true">
                                    </div>
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Servicio</label>
                                        <input class="form-control" type="text" id="txtservicio" name="txtservicio" value="${vehiculo.SERVICIO}" readonly="true">
                                    </div>
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Tipo de Uso</label>
                                        <input class="form-control" type="text" id="txttipouso" name="txttipouso" value="${vehiculo.TIPO_USO}" readonly="true">
                                    </div>
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Color</label>
                                        <input class="form-control" type="text" id="txtcolores" name="txtcolores" value="${vehiculo.COLOR}" readonly="true">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Marca</label>
                                        <input class="form-control" id="txtmarcas" name="txtmarcas" value="${vehiculo.MARCA}" readonly="true">
                                    </div>
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Linea</label>
                                        <input class="form-control" id="txtlineas" name="txtlineas" value="${vehiculo.LINEA}" readonly="true">
                                    </div>
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Modelo</label>
                                        <input class="form-control" type="text" id="txtmodelo" name="txtmodelo" value="${vehiculo.MODELO}" readonly="true">
                                    </div>
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Transformado</label>
                                        <input class="form-control" type="text" id="txttransformado" name="txttransformado" value="${vehiculo.TRANSFORMADO}" readonly="true">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Datos Registro Inicial</h6>
                            </div>
                            <div class="card-body">
                                <div class="form-group row">
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Registrado en Runt</label>
                                        <input class="form-control" type="text" id="txtrunt" name="txtrunt" value="${vehiculo.RUNT}" readonly="true">
                                    </div>
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Licencia Transito</label>
                                        <input class="form-control" type="text" id="txtlicenciatransito" name="txtlicenciatransito" value="${vehiculo.LICENCIA_TRANSITO}" readonly="true">
                                    </div>
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Fecha Matricula</label>
                                        <input class="form-control" type="text" id="txtfechamatricula" name="txtfechamatricula" readonly="true" value="${vehiculo.FECHA_MATRICULA}"/>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Pais</label>
                                        <input class="form-control" type="text" id="txtpaismatricula" name="txtpaismatricula" value="${vehiculo.PAIS_MATRICULA}" readonly="true">
                                    </div>
                                    <c:choose>
                                        <c:when test="${vehiculo.PAIS_MATRICULA != null && vehiculo.PAIS_MATRICULA == 'COLOMBIA'}">
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Departamento</label>
                                                <input class="form-control" type="text" id="txtdepartamentomatricula" name="txtdepartamentomatricula" value="${vehiculo.DPTO_MATRICULA}" readonly="true">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Municipio</label>
                                                <input class="form-control" type="text" id="txtmunicipiomatricula" name="txtmunicipiomatricula" value="${vehiculo.MUNI_MATRICULA}" readonly="true">
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Ciudad</label>
                                                <input class="form-control" type="text" id="txtciudadmatricula" name="txtciudadmatricula" value="${vehiculo.CIUDAD_MATRICULA}" readonly="true">
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Datos Importacion</h6>
                            </div>
                            <div class="card-body">
                                <div class="form-group row">
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Tipo Importacion</label>
                                        <input class="form-control" type="text" id="txttipodocimportacion" name="txttipodocimportacion" value="${vehiculo.TDOC_IMP}" readonly="true">
                                    </div>
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Documento Importacion</label>
                                        <input class="form-control" type="text" id="txtdocumentoimportacion" name="txtdocumentoimportacion" value="${vehiculo.DOCUMENTO_IMP}" readonly="true">
                                    </div>
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Fecha Importacion</label>
                                        <input class="form-control" type="text" id="txtfechaimportacion" name="txtfechaimportacion" value="${vehiculo.FECHA_IMP}" readonly="true">
                                    </div>
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Pais Importacion</label>
                                        <input class="form-control" type="text" id="txtpaisimportacion" name="txtpaisimportacion" value="${vehiculo.PAIS_IMP}" readonly="true">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Polizas y Certificados</h6>
                            </div>
                            <div class="card-body">
                                <div class="form-group row">
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>SOAT</label>
                                        <input class="form-control" type="text" id="txtsoat" name="txtsoat" value="<c:choose><c:when test='${vehiculo.SOAT} == "S"'>SI</c:when><c:otherwise>NO</c:otherwise></c:choose>"  readonly="true">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Fecha Venc. Soat</label>
                                                    <input class="form-control" type="text" id="txtfechavsoat" name="txtfechavsoat" value="${vehiculo.FECHAV_SOAT}" readonly="true">
                                    </div>
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Tecnomecanica</label>
                                        <input class="form-control" type="text" id="txttecnomecanica" name="txttecnomecanica" value="<c:choose><c:when test='${vehiculo.TECNO_MECANICA} == "S"'>SI</c:when><c:otherwise>NO</c:otherwise></c:choose>" readonly="true">
                                            </div>
                                            <div class="col-sm-3 mb-3 mb-sm-0">
                                                <label>Fecha Venc. Tecnomecanica</label>
                                                    <input class="form-control" type="text" id="txtfechavtecnomecanica" name="txtfechavtecnomecanica" value="${vehiculo.FECHAV_TECNO}" readonly="true">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Observaciones</h6>
                            </div>
                            <div class="card-body">
                                <div class="form-group row">
                                    <textarea id="txtobservaciones" name="txtobservaciones" maxlength="300" style="width: 100%" readonly="true">${vehiculo.OBSERVACIONES}</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="card shadow mb-4">
                            <div class="card-header py-3 d-flex justify-content-between align-items-center">
                                <h6 class="m-0 font-weight-bold text-primary">Personas Asociadas</h6>
                            </div>
                            <div class="card-body">
                                <div class="form-group row">
                                    <div class="col-sm-2 mb-3 mb-sm-0">
                                        <label>Tipo</label>
                                    </div>
                                    <div class="col-sm-2 mb-3 mb-sm-0">
                                        <label>Tipo Documento</label>
                                    </div>
                                    <div class="col-sm-3 mb-3 mb-sm-0">
                                        <label>Documento</label>
                                    </div>
                                    <div class="col-sm-5 mb-3 mb-sm-0">
                                        <label>Nombre</label>
                                    </div>
                                </div>
                                <c:choose>
                                    <c:when test="${not empty personas}">
                                        <c:forEach items="${personas}" var="persona">
                                            <div class="form-group row">
                                                <div class="col-sm-2 mb-3 mb-sm-0">
                                                    <input class="form-control" type="text" id="txttipopersona" name="txttipopersona" value="${persona.TIPO_PERSONA}" readonly="true">
                                                </div>
                                                <div class="col-sm-2 mb-3 mb-sm-0">
                                                    <input class="form-control" type="text" id="cmbtipodocumento" name="cmbtipodocumento" value="${persona.TIPO_DOC}" readonly="true">
                                                </div>
                                                <div class="col-sm-3 mb-3 mb-sm-0">
                                                    <input class="form-control" type="number" id="txtdocumento" name="txtdocumento" value="${persona.DOCUMENTO}" readonly="true">
                                                </div>
                                                <div class="col-sm-5 mb-3 mb-sm-0">
                                                    <input class="form-control" type="text" id="txtnombre" name="txtnombre" value="${persona.NOMBRE}" readonly="true">
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="form-group row">
                                            <label>No se encontraron personas asociadas al vehiculo</label>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%
        } else {
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
            document.location.href = "index.jsp";
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

        <!-- Page level plugins -->
        <script src="${pageContext.request.contextPath}/template/vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="${pageContext.request.contextPath}/template/vendor/datatables/dataTables.bootstrap4.min.js"></script>

        <!-- Page level custom scripts -->
        <script src="${pageContext.request.contextPath}/template/js/demo/datatables-demo.js"></script>

        <script src="${pageContext.request.contextPath}/scripts/vehiculos.js" type="text/javascript"></script>

    </body>
</html>