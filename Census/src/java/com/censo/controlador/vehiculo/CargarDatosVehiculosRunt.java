package com.censo.controlador.vehiculo;

import com.censo.modelo.dao.VehiculoDao;
import com.censo.modelo.persistencia.CenVehiculo;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(name = "CargarDatosVehiculosRunt", urlPatterns = {"/cargarDatosVehiculoRunt"})
public class CargarDatosVehiculosRunt extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, Object> respuesta = new HashMap<>();

        try {

            if (request.getParameter("placa") == null || request.getParameter("valorreferencia").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'numero de referencia' no encontrado para verificar vehiculo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            int idvehiculo = Integer.parseInt(request.getParameter("id"));

            VehiculoDao vehiculoDao = new VehiculoDao();
            conex = vehiculoDao.conectar();

            CenVehiculo cenvehiculo = vehiculoDao.ConsultarVehiculoById(conex, idvehiculo);
/*
            if (cenvehiculo != null) {

                String urlString = "https://test.konivin.com:28183/konivin/servicio/persona/consultar?lcy=lagit&vpv=L4gIt&jor=24158996&icf=01&thy=CO&klm=ND1098XX";
                URL url = new URL(urlString);

                // Abrir conexión
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                StringBuilder content;
                try ( // Leer respuesta
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    // Cerrar conexiones
                }
                con.disconnect();

                // Procesar respuesta JSON con Gson
                Gson gson = new Gson();
                MiObjeto obj = gson.fromJson(content.toString(), MiObjeto.class);

                // Validar si el dato existe
                if (obj != null && obj.getDato() != null) {
                    // Procesar el objeto como necesites
                    System.out.println("Dato encontrado: " + obj.getDato());
                } else {
                    System.out.println("Dato no encontrado.");
                }

                respuesta.put("status", "success");
                respuesta.put("vehiculo", datosVehiculo);
            } else {
                respuesta.put("status", "fail");
                respuesta.put("message", "Vehiculo no se encuentra registrado");
            }
*/
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
