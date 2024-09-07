package com.censo.controlador.vehiculo;

import com.censo.modelo.dao.PersonaVehiculoDao;
import com.censo.modelo.dao.VehiculoDao;
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

@WebServlet(name = "CargarDatosVehiculo", urlPatterns = {"/cargarDatosVehiculo"})
public class CargarDatosVehiculo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, Object> respuesta = new HashMap<>();

        try {

            if (request.getParameter("id").equals("")) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'id' no encontrado para cargar datos del vehiculo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            int idvehiculo = Integer.parseInt(request.getParameter("id"));

            VehiculoDao vehiculoDao = new VehiculoDao();
            conex = vehiculoDao.conectar();

            HashMap<String, Object> datosVehiculo = vehiculoDao.ConsultarDatosVehiculoById(conex, idvehiculo);

            if (!datosVehiculo.isEmpty()) {
                PersonaVehiculoDao personaVehiculoDao = new PersonaVehiculoDao();
                List<HashMap<String, Object>> personasVehiculo = personaVehiculoDao.ListarHashPersonasVehiculoActivasByIdVehiculo(conex, idvehiculo);
                respuesta.put("status", "success");
                respuesta.put("vehiculo", datosVehiculo);
                respuesta.put("personasVehiculo", personasVehiculo);
            } else {
                respuesta.put("status", "fail");
                respuesta.put("message", "Vehiculo no se encuentra registrado");
            }

        } catch (SQLException e) {
            respuesta.put("status", "error");
            respuesta.put("message", "Error al cargar los datos del vehiculo");
            e.printStackTrace();

        } finally {
            if (conex != null) {
                try {
                    conex.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
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
