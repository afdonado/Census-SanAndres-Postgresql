<%@page import="com.censo.modelo.dao.MunicipioDao"%>
<%@page import="com.censo.modelo.persistencia.CenMunicipio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    MunicipioDao municipioDao = new MunicipioDao();
    
    if (!request.getParameter("iddep").equals("") && !request.getParameter("nameCombo").equals("")) {
%>
<select name="<%=request.getParameter("nameCombo")%>" id="<%=request.getParameter("nameCombo")%>" style="width:150px; font-size:11px">
    <%
        java.util.List listaMunicipios = municipioDao.ListarMunicipiosByIdDepto(Integer.parseInt(request.getParameter("iddep").toString()));
        for (int i = 0; i < listaMunicipios.size(); i++) {
                        CenMunicipio cenmunicipio = (CenMunicipio) listaMunicipios.get(i);%>
    <option value="<%=cenmunicipio.getId() + ""%>"><%=cenmunicipio.getNombre()%></option>
    <%
        }
    %>
</select>
<%
    }
%>

