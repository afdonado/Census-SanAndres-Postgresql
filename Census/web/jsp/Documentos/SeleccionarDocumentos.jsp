<%@page import="com.censo.modelo.dao.CensoDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.censo.modelo.persistencia.CenCenso"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Cargar Documentos Censo</title>

        <link href="../../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="../../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../../vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="../../scripts/Ajax.js" type="text/javascript"></script>

        <script src="../../scripts/documentos.js" type="text/javascript"></script>

        <link href="../../fileinput/css/fileinput.css" rel="stylesheet" type="text/css"/>
        <script src="../../fileinput/js/fileinput.min.js" type="text/javascript"></script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("CargarDocumentos.jsp")) {
                    if (request.getParameter("numero") != null) {
                        
                        CensoDao censoDao = new CensoDao();
                        String numero = request.getParameter("numero");
                        long idcenso = 0;
                        CenCenso cencenso = censoDao.ConsultarCensoByNumero(numero);
                        if (cencenso != null) {
                            idcenso = cencenso.getId();
        %>
        <div class="container-fluid">
            <form id="frmcargardocumentos" enctype="multipart/form-data" onsubmit="checkSubmit('btncargar', 'Guardando...')" action="../../cargarDocumentos?idcenso=<%=idcenso%>&numero=<%=numero%>" method="post">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Seleccione Documentos
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="form-group col-md-10">
                                        <label>Seleccione los documentos</label>
                                        <input id="file" name="file" multiple type="file" class="file" onchange="return ValidarImagenes(this.id)" data-allowed-file-extensions='["png", "jpg", "pdf"]'>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <%
                } else {
%>
        <script type="text/javascript">
            alert("Numero de censo no registrado");
        </script>
        <%
}
            } else {
%>
        <script type="text/javascript">
            alert("Digite el numero del censo");
        </script>
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
