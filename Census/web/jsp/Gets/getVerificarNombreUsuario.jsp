<%@page import="com.censo.modelo.dao.UsuarioDao"%>
<%@page import="com.censo.modelo.persistencia.CenUsuario"%>
        <%
    UsuarioDao usuarioDao = new UsuarioDao();

    if (!request.getParameter("nomusuario").equals("")) {
        String nomusuario = request.getParameter("nomusuario");

        CenUsuario cenusuario = usuarioDao.ConsultarUsuarioByNombre(nomusuario);
        if (cenusuario != null) {
            out.println("si");
        } else {
            out.println("no");
        }
    } else {
        out.println("no");
    }
%>
