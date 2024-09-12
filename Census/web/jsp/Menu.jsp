<%@page import="javax.sql.DataSource"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.censo.modelo.persistencia.CenPermiso"%>
<%@page import="com.censo.modelo.persistencia.CenModulo"%>
<%@page import="com.censo.modelo.persistencia.CenUsuario"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page import="com.censo.modelo.dao.UsuarioDao"%>
<%
    HttpSession sessionCensus = request.getSession();
    CenUsuario cenusuario = (CenUsuario) sessionCensus.getAttribute("usuario");
    List listaModulosUsuario = (List) sessionCensus.getAttribute("modulosUsuario");

    DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");

    try (Connection conex = dataSource.getConnection()) {
        UsuarioDao usuarioDao = new UsuarioDao();
%>
<a class="sidebar-brand d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath}/dashboard">
    <div class="sidebar-brand-icon rotate-n-15">
        <i class="fas fa-laugh-wink"></i>
    </div>
    <div class="sidebar-brand-text mx-3">UT <sup>MOVISAI</sup></div>
</a>

<hr class="sidebar-divider my-0">

<li class="nav-item active">
    <a class="nav-link" href="${pageContext.request.contextPath}/dashboard">
        <i class="fas fa-fw fa-tachometer-alt"></i>
        <span>Dashboard</span></a>
</li>

<hr class="sidebar-divider">

<%
    List<CenModulo> listaModulos = (List<CenModulo>) sessionCensus.getAttribute("modulos");
    for (int i = 0; i < listaModulosUsuario.size(); i++) {
        for (CenModulo cenmodulo : listaModulos) {
            if (cenmodulo.getId() == listaModulosUsuario.get(i).hashCode()) {
%>
<li class="nav-item">
    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapse<%=cenmodulo.getNombre()%>" 
       aria-expanded="true" aria-controls="collapse<%=cenmodulo.getNombre()%>">
        <i class="<%=cenmodulo.getIcono()%>"></i>
        <span><%=cenmodulo.getNombre()%></span>
    </a>
    <div id="collapse<%=cenmodulo.getNombre()%>" class="collapse" aria-labelledby="heading<%=cenmodulo.getNombre()%>" 
         data-parent="#accordionSidebar">
        <div class="bg-white py-2 collapse-inner rounded">
            <%
                List<CenPermiso> listaPermisos = usuarioDao.ListarPermisosByUsuarioModulo(conex, cenusuario.getId(), cenmodulo.getId());
                for (CenPermiso cenpermiso : listaPermisos) {
                    if (cenpermiso.getUbicacion() != null) {
                        String ubicacion = "";
                        if (cenpermiso.getUbicacion() != null) {
                            ubicacion = cenpermiso.getUbicacion();
                        }
                        String ruta = "jsp/" + ubicacion + "/" + cenpermiso.getDescripcion() + "?opcion=1";
            %>
            <a class="collapse-item" href="${pageContext.request.contextPath}/<%=ruta%>"><%=cenpermiso.getNombre()%></a>
            <%
                    }
                }
            %>
        </div>
    </div>
</li>
<%
            }
        }
    }
%>
<hr class="sidebar-divider d-none d-md-block">

<div class="text-center d-none d-md-inline">
    <button class="rounded-circle border-0" id="sidebarToggle"></button>
</div>
<%
    }
%>