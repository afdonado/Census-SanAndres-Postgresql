package com.censo.controlador.estadistica;

import com.censo.modelo.dao.EstadisticaDao;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(name = "CargarEstadisticas", urlPatterns = "/cargarEstadisticas")
public class CargarEstadisticas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Connection conex = null;
        
        try {

            EstadisticaDao estadisticaDao = new EstadisticaDao();
            conex = estadisticaDao.conectar();
            
            List<HashMap> datosEstadistica = estadisticaDao.ListarCantidadCensosClaveVehiculo(conex);
            
            JSONArray jsonArray = new JSONArray();
            for (HashMap hash : datosEstadistica) {
                JSONObject detalleJson = new JSONObject();
                detalleJson.put("CLV_DESCRIPCION", hash.get("CLV_DESCRIPCION").toString());
                detalleJson.put("CANTIDAD", hash.get("CANTIDAD").toString());
                jsonArray.put(detalleJson);
            }
            out.println(jsonArray);
            
        } catch (SQLException | JSONException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al cargar los datos');");
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
