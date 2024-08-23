package com.censo.controlador.estadistica;

import com.censo.modelo.dao.EstadisticaDao;
import java.io.IOException;
import static java.lang.System.out;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class CargarEstadisticas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {

            EstadisticaDao dao = new EstadisticaDao();
            
            List<HashMap> datosEstadistica = dao.ListarCantidadCensosClaveVehiculo();
            
            JSONArray jsonArray = new JSONArray();
            for (HashMap hash : datosEstadistica) {
                JSONObject detalleJson = new JSONObject();
                detalleJson.put("CLV_DESCRIPCION", hash.get("CLV_DESCRIPCION").toString());
                detalleJson.put("CANTIDAD", hash.get("CANTIDAD").toString());
                jsonArray.put(detalleJson);
            }
            out.println(jsonArray);
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
