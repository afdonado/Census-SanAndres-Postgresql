package com.censo.controlador.parametros;

import com.censo.modelo.dao.LineaDao;
import com.censo.modelo.dao.MarcaDao;
import com.censo.modelo.persistencia.CenMarca;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "CargarLineas", urlPatterns = "/cargarLineas")
public class CargarLineas extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (Connection conex = dataSource.getConnection()) {
            LineaDao lineaDao = new LineaDao();
            
            MarcaDao marcaDao = new MarcaDao();

            String nommarca = request.getParameter("marcas").toUpperCase().trim();
            String nomlinea = request.getParameter("lineas").toUpperCase().trim();

            if (!nommarca.equals("")) {
                CenMarca cenmarca = marcaDao.ConsultarMarcaByNombre(conex, nommarca);
                if (cenmarca != null) {
                    ArrayList<String> lineas = lineaDao.ListarNombresLineas(conex, nomlinea, cenmarca.getId());
                    String json = new Gson().toJson(lineas);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                }
            }
        } catch (IOException | SQLException e) {
            System.out.println(e);
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
