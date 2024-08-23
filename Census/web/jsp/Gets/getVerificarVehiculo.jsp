<?xml version="1.0" encoding="iso-8859-1"?>
<%@page contentType="text/xml" %>
<%@page import="com.censo.modelo.dao.CensoDao"%>
<%@page import="com.censo.modelo.dao.VehiculoDao"%>
<%@page import="com.censo.modelo.persistencia.CenCenso"%>
<%@page import="com.censo.modelo.persistencia.CenVehiculo"%>
<root>
<%
    VehiculoDao vehiculoDao = new VehiculoDao();
    CensoDao censoDao = new CensoDao();
    
    if (!request.getParameter("tiporef").equals("") && !request.getParameter("valorreferencia").equals("")) {
        int tipoRef = Integer.parseInt(request.getParameter("tiporef"));
        String valorReferencia = request.getParameter("valorreferencia");
        int opcion = Integer.parseInt(request.getParameter("opcion"));
        
        CenVehiculo cenvehiculo = vehiculoDao.ConsultarVehiculoByReferencia(tipoRef, valorReferencia);

        if (cenvehiculo != null) {
            
            if(opcion == 2){
            
                CenCenso cencenso = censoDao.ConsultarCensoByIdVehiculo(cenvehiculo.getId());

                    if (cencenso == null) {
        %>
        <result>si</result>
        <idvehiculo><%=cenvehiculo.getId()%></idvehiculo>
            <%
            } else {
            %>
        <result>sicensado</result>
            <%
                }

            }
%>
        <result>si</result>
            <%       
    } else {
            %>
<result>noexiste</result>
    <%}
    } else {%>
<result>no</result>
    <%}%>
</root>