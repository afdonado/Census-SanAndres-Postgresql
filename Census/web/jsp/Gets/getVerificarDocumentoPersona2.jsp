<?xml version="1.0" encoding="iso-8859-1"?>
<%@page contentType="text/xml" %>
<%@page import="com.censo.modelo.dao.PersonaDao"%>
<%@page import="com.censo.modelo.persistencia.CenPersona"%>
<%@page import="com.censo.modelo.persistencia.CenUsuario"%>
<root>
<%
    PersonaDao personaDao = new PersonaDao();
    if (!request.getParameter("tipodocumento").equals("") && !request.getParameter("documento").equals("")) {
        int tipodoc = Integer.parseInt(request.getParameter("tipodocumento"));
        String documento = request.getParameter("documento");
        
        CenPersona cenpersona = personaDao.ConsultarPersona(tipodoc, documento);
        if(cenpersona != null){
            String nombreCompleto = cenpersona.getNombre1() + " " + (cenpersona.getNombre2() != null ? cenpersona.getNombre2().trim() : "") 
                    + " " + (cenpersona.getApellido1() != null ? cenpersona.getApellido1().trim() : "") 
                    + " " + (cenpersona.getApellido2() != null ? cenpersona.getApellido2().trim() : "");
            long idpersona = cenpersona.getId();

%>
<result>si</result>
<nombrecompleto><%= nombreCompleto %></nombrecompleto>
<idpersona><%= idpersona %></idpersona>
<%            
        } else {%>
<result>no</result>
    <%}
    } else {%>
<result>no</result>
    <%}%>
</root>