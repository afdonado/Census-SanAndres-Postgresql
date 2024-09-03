package com.censo.controlador.parametros;

import com.censo.modelo.dao.CategoriaLicenciaDao;
import com.censo.modelo.persistencia.CenCategoriaLicencia;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CargarCategoriasLicencia", urlPatterns = {"/cargarCategoriasLicencia"})
public class CargarCategoriasLicencia extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            CategoriaLicenciaDao categoriaLicenciaDao = new CategoriaLicenciaDao();
            conex = categoriaLicenciaDao.conectar();
            
            List<CenCategoriaLicencia> lista = categoriaLicenciaDao.ListarCategoriasLicencia(conex);
            
            if (!lista.isEmpty()) {
                    Gson gson = new Gson();
                    String json = gson.toJson(lista);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
            }

        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al cargar las categorias de licencia de conduccion');");
            out.println("location='jsp/Inicio.jsp';");
            out.println("</script>");
            e.printStackTrace();

        } finally {
            if (conex != null) {
                try {
                    conex.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
