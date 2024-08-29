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

@WebServlet(name = "CargarLineas", urlPatterns = "/cargarLineas")
public class CargarLineas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        Connection conex = null;

        try {
            LineaDao lineaDao = new LineaDao();
            conex = lineaDao.conectar();
            
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
        } finally {
            if (conex != null) {
                try {
                    conex.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
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
