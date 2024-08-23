<%@page import="com.censo.modelo.dao.UsuarioDao"%>
<?xml version="1.0" encoding="iso-8859-1"?>
<%@page contentType="text/xml" %>
<%@page import="com.censo.modelo.persistencia.CenUsuario"%>
<%@page import="org.apache.commons.codec.digest.DigestUtils"%>
<root>
<%
    UsuarioDao usuarioDao = new UsuarioDao();

    if (!request.getParameter("idusuario").equals("") && !request.getParameter("passactual").equals("")) {
        long idusuario = Long.parseLong(request.getParameter("idusuario"));

        CenUsuario cenusuario = usuarioDao.ConsultarUsuarioById(idusuario);
        if (cenusuario != null) {
            String passactual = DigestUtils.md5Hex(request.getParameter("passactual"));
            if (passactual.equals(cenusuario.getPass())) {
%>
<result>si</result>
    <%
    } else {
    %>
<result>no</result>
    <%
        }
    } else {
    %>
<result>no</result>
    <%
        }
    } else {
    %>
<result>no</result>
    <%}%>
</root>