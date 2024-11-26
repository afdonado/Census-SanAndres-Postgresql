package com.censo.controlador.censo;

import com.censo.modelo.dao.CensoDao;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "ListarCensos", urlPatterns = {"/listarCensos"})
public class ListarCensos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> respuesta = new HashMap<>();
        
        // Parámetros de paginación de DataTables
        int draw = Integer.parseInt(request.getParameter("draw"));
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        String searchValue = request.getParameter("search[value]").toUpperCase();
        String orderColumnIndex = request.getParameter("order[0][column]");
        String orderDirection = request.getParameter("order[0][dir]");

        String[] columnNames = {"NUMERO","FECHA","HORA","PUNTO_ATENCION","ESTADO","USUARIO"
                ,"FECHA_PROCESO_FORMAT","FECHA_PROCESO_HORA","DOCUMENTO_PDF"
                ,"VEH_PLACA", "VEH_MOTOR", "VEH_CHASIS", "VEH_SERIE"};

        String orderBy = columnNames[Integer.parseInt(orderColumnIndex)];
        
        try (Connection conex = dataSource.getConnection()) {
            CensoDao censoDao = new CensoDao();
            
            List<HashMap<String, Object>> lista;
            if (searchValue.equals("")) {
                // Obtén los datos paginados
                lista = censoDao.listarCensosPaginados(conex, start, length, orderBy, orderDirection);
            } else {
                // Obtén los datos paginados y filtrados
                lista = censoDao.listarCensosPaginadosFiltrados(conex, start, length, searchValue, orderBy, orderDirection);
            }

            // Obtén el total de registros (sin filtro)
            int totalRecords = censoDao.contarCensos(conex);

            // Obtén el total de registros filtrados
            int filteredRecords = censoDao.contarCensosFiltrados(conex, searchValue);

            respuesta.put("draw", draw);
            respuesta.put("recordsTotal", totalRecords);
            respuesta.put("recordsFiltered", filteredRecords);
            respuesta.put("data", lista);
            
        } catch (SQLException e) {
            respuesta.put("status", "error");
            respuesta.put("message", "Error al listas los censos");
            e.printStackTrace();

        }

        String json = new Gson().toJson(respuesta);
        response.getWriter().write(json);
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
