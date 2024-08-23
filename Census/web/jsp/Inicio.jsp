<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="com.censo.modelo.dao.UsuarioDao"%>
<%@page import="com.censo.modelo.persistencia.CenModulo"%>
<%@page import="com.censo.modelo.persistencia.CenPermiso"%>
<%@page import="com.censo.modelo.persistencia.CenUsuario"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Census - Inicio</title>

        <link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="../dist/css/sb-admin-2.css" rel="stylesheet">
        <link href="../vendor/metisMenu/metisMenu.min.css" rel="stylesheet">
        <link href="../vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

        <script src="../vendor/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="../vendor/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../vendor/metisMenu/metisMenu.min.js" type="text/javascript"></script>
        <script src="../dist/js/sb-admin-2.js" type="text/javascript" ></script>

        <script>
            function action(file) {
                window.frames[0].location.href = file;
            }
        </script>
    </head>
    <body>
        <%
            HttpSession sessionCensus = request.getSession();
            if (sessionCensus.getAttribute("usuario") != null) {
                if (((java.util.LinkedList) sessionCensus.getAttribute("permisosUsuario")).contains("Inicio.jsp")) {
                    CenUsuario cenusuario = (CenUsuario) sessionCensus.getAttribute("usuario");
                    try {
        %>
        <div id="wrapper">
            <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <a class="navbar-brand" href="Inicio.jsp"><img src="../vendor/imagenes/lagit.jpeg" alt="Lagit" style="width: 130px; height: 30px;"></a>
                </div>

                <ul class="nav navbar-top-links navbar-right">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            <%=cenusuario.getNombre()%>&nbsp;<i class="fa fa-user fa-fw"></i><i class="fa fa-caret-down"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-user">
                            <li><a onclick="action('AdminUsuarios/actualizarPassword.jsp')"><i class="fa fa-user fa-fw"></i> Cambiar Password</a>
                            </li>
                            <li class="divider"></li>
                            <li><a href="../cerrarSesion"><i class="fa fa-sign-out fa-fw"></i> Cerrar Sesion</a>
                            </li>
                        </ul>
                    </li>
                </ul>

                <div class="navbar-default sidebar" role="navigation">
                    <div class="sidebar-nav navbar-collapse">
                        <ul class="nav" id="side-menu">
                            <li>
                                <a href="Inicio.jsp"><i class="fa fa-dashboard fa-fw"></i> Inicio</a>
                            </li>
                            <%
                                List listaModulosUsuario = (List) sessionCensus.getAttribute("modulosUsuario");
                                List<CenModulo> listaModulos = (List<CenModulo>) sessionCensus.getAttribute("modulos");
                                for (int i = 0; i < listaModulosUsuario.size(); i++) {
                                    for (CenModulo cenmodulo : listaModulos) {
                                        if (cenmodulo.getId() == listaModulosUsuario.get(i).hashCode()) {
                            %>
                            <li>
                                <a href="#"><i class="<%=cenmodulo.getIcono()%>"></i><%=cenmodulo.getNombre()%><span class="fa arrow"></span></a>
                                        <%
                                            UsuarioDao usuarioDao = new UsuarioDao();
                                            List listaPermisos = usuarioDao.ListarPermisosByUsuarioModulo(cenusuario.getId(), cenmodulo.getId());
                                            for (int j = 0; j < listaPermisos.size(); j++) {
                                                CenPermiso permiso = (CenPermiso) listaPermisos.get(j);
                                                String ubicacion = "";
                                                if (permiso.getUbicacion() != null) {
                                                    ubicacion = permiso.getUbicacion();
                                                }
                                                String ruta = ubicacion + "/" + permiso.getDescripcion() + "?opcion=1";
                                        %>
                                <ul class="nav nav-second-level">
                                    <li>
                                        <a onclick="action('<%=ruta%>')" ><%=permiso.getNombre()%></a>
                                    </li>
                                </ul>
                                <%
                                    }
                                %>
                            </li>
                            <%
                                        }
                                    }
                                }
                            %>
                        </ul>
                    </div>
                </div>
            </nav>

            <div id="page-wrapper">
                <div class="row">
                    <iframe id="frmPrincipal" src="Info.jsp" style="width: 100%; min-height: 1800px;" transparency="transparency" frameborder="0" ></iframe>
                </div>
            </div>
        </div>
        <%
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
        %>
        <script type="text/javascript">
            alert("Su usuario no tiene permiso para acceder a esta pagina");
            document.location.href = "../cerrarSesion";
        </script>
        <%
            }
        } else {
        %>
        <script type="text/javascript">
            alert("Su sesion a terminado");
            document.location.href = "../cerrarSesion";
        </script>
        <%
            }
        %>
    </body>
</html>
