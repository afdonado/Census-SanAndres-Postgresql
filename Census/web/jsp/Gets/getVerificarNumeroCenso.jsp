<?xml version="1.0" encoding="iso-8859-1"?>
<%@page contentType="text/xml" %>
<%@page import="com.censo.modelo.dao.CensoDao"%>
<%@page import="com.censo.modelo.persistencia.CenCenso"%>
<root>
<%

    CensoDao censoDao = new CensoDao();

    if (!request.getParameter("numerocenso").equals("")) {

        String numeroCenso = request.getParameter("numerocenso");

        if (numeroCenso.length() < 6) {
            String prefijo = "CS";
            numeroCenso = prefijo + ("00000".substring(0, 5 - (numeroCenso + "").length())) + numeroCenso;
        }

        CenCenso cencenso = censoDao.ConsultarCensoByNumero(numeroCenso);
        if (cencenso != null) {
        
            String fechaCenso = new java.text.SimpleDateFormat("dd/MM/yyyy").format(cencenso.getFecha());
%>
<result>si</result>
<idcenso><%=cencenso.getId()%></idcenso>
<fechacenso><%=fechaCenso%></fechacenso>
    <%
    } else {
    %>
<result>no</result>
<numerocenso><%=numeroCenso%></numerocenso>
    <%
        }
    } else {
    %>
<result>no</result>
    <%}%>
</root>