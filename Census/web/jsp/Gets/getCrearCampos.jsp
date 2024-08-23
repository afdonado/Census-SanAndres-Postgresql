<%@page import="com.censo.modelo.dao.TipoDocumentoDao"%>
<%@page import="com.censo.modelo.dao.TipoPersonaDao"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.censo.modelo.persistencia.CenTipoDocumento"%>
<%@page import="com.censo.modelo.persistencia.CenTipoPersona"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    HttpSession sessionCensus = request.getSession();
    if (sessionCensus.getAttribute("usuario") != null) {
        if (((LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("registrarVehiculo.jsp")) {
            if (!request.getParameter("identificador").equals("")) {
                
                TipoPersonaDao tipoPersonaDao = new TipoPersonaDao();
                TipoDocumentoDao tipoDocumentoDao = new TipoDocumentoDao();
                
                int identificador = Integer.parseInt(request.getParameter("identificador"));
%>
<div class="form-group col-md-2">
    <select class="form-control" id="cmbtipopersona<%=identificador%>" name="cmbtipopersona<%=identificador%>">
        <%
            List listaTiposPersona = tipoPersonaDao.ListarTiposPersona();

            for (int i = 0; i < listaTiposPersona.size(); i++) {
                CenTipoPersona centipopersona = (CenTipoPersona) listaTiposPersona.get(i);
                if (centipopersona.getId() == 1) {
        %>
        <option value="<%=centipopersona.getId()%>" selected><%=centipopersona.getDescripcion()%></option>
        <% } else {%>
        <option value="<%=centipopersona.getId()%>"><%=centipopersona.getDescripcion()%></option>
        <%
                }
            }
        %>
    </select>
</div>
<div class="form-group col-md-2">
    <select class="form-control" id="cmbtipodoc<%=identificador%>" name="cmbtipodoc<%=identificador%>">
        <%
            List listaTiposDocumento = tipoDocumentoDao.ListarTiposDocumento();

            for (int i = 0; i < listaTiposDocumento.size(); i++) {
                CenTipoDocumento centipodocumento = (CenTipoDocumento) listaTiposDocumento.get(i);
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
<div class="form-group col-md-2">
    <input class="form-control" type="text" id="txtdocumento<%=identificador%>" name="txtdocumento<%=identificador%>" onKeyPress=" return validarNumeros(event)" onblur="consultarPersonaRegVeh(1, 'cmbtipodoc<%=identificador%>', 'txtdocumento<%=identificador%>', 'txtnombre<%=identificador%>', 'idpersona<%=identificador%>')" maxlength="20" style="text-transform: uppercase" required>
</div>
<div class="form-group col-md-5">
    <input class="form-control" type="text" id="txtnombre<%=identificador%>" name="txtnombre<%=identificador%>" readonly="true">
</div>
<input type="hidden" id="idpersona<%=identificador%>" name="idpersona<%=identificador%>">
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