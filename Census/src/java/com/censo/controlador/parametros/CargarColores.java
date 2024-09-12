package com.censo.controlador.parametros;

import com.censo.modelo.dao.ColorDao;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "CargarColores", urlPatterns = "/cargarColores")
public class CargarColores extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try (Connection conex = dataSource.getConnection()) {
            ColorDao colorDao = new ColorDao();
            
            String find = request.getParameter("colores").toUpperCase().trim();
            ArrayList<String> colores = colorDao.ListarNombresColores(conex, find);
            String json = new Gson().toJson(colores);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (IOException | SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al cargar los colores');");
            out.println("location='jsp/Censo/registrarCenso.jsp';");
            out.println("</script>");
            e.printStackTrace();
        } finally {
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
