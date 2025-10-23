package com.censo.controlador.estadistica;

import com.censo.modelo.dao.EstadisticaDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(name = "CargarEstadisticasProvidencia", urlPatterns = "/cargarEstadisticasProvidencia")
public class CargarEstadisticasProvidencia extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");

        response.setContentType("text/html;charset=UTF-8");


        try (PrintWriter out = response.getWriter(); Connection conex = dataSource.getConnection()) {

            EstadisticaDao estadisticaDao = new EstadisticaDao();

            if (request.getParameter("opcion") != null || !request.getParameter("opcion").isEmpty()) {

                int opcion = Integer.parseInt(request.getParameter("opcion"));

                List<HashMap> lista;
                JSONArray jsonArray = new JSONArray();

                switch (opcion) {
                    case 1://Cantidad Censos por Clase de Vehiculo
                        lista = estadisticaDao.ListarCantidadCensosClaveVehiculoProvidencia(conex);
                        if (!lista.isEmpty()) {
                            for (HashMap hash : lista) {
                                JSONObject detalleJson = new JSONObject();
                                detalleJson.put("clv_descripcion", hash.get("clv_descripcion").toString());
                                detalleJson.put("cantidad", hash.get("cantidad").toString());
                                detalleJson.put("cantidad_total", hash.get("cantidad_total").toString());
                                jsonArray.put(detalleJson);
                            }
                        }
                        out.println(jsonArray);
                        break;

                    case 2://Cantidad Censos por Punto de Atencion

                        lista = estadisticaDao.ListarCantidadCensosPuntoAtencion(conex);
                        if (!lista.isEmpty()) {
                            for (HashMap hash : lista) {
                                JSONObject detalleJson = new JSONObject();
                                detalleJson.put("pun_descripcion", hash.get("pun_descripcion").toString());
                                detalleJson.put("cantidad", hash.get("cantidad").toString());
                                detalleJson.put("cantidad_total", hash.get("cantidad_total").toString());
                                jsonArray.put(detalleJson);
                            }
                        }
                        out.println(jsonArray);
                        break;

                    case 3://Cantidad Personas Censadas por Genero

                        lista = estadisticaDao.ListarCantidadPersonasCensadasGeneroProvidencia(conex);
                        if (!lista.isEmpty()) {
                            for (HashMap hash : lista) {
                                JSONObject detalleJson = new JSONObject();
                                detalleJson.put("genero", hash.get("genero").toString());
                                detalleJson.put("porcentaje", hash.get("porcentaje").toString());
                                detalleJson.put("cantidad_total", hash.get("cantidad_total").toString());
                                jsonArray.put(detalleJson);
                            }
                        }
                        out.println(jsonArray);
                        break;

                    case 4://Cantidad Personas Censadas por Licencia

                        lista = estadisticaDao.ListarCantidadPersonasCensadasLicenciaProvidencia(conex);
                        if (!lista.isEmpty()) {
                            for (HashMap hash : lista) {
                                JSONObject detalleJson = new JSONObject();
                                detalleJson.put("descripcion", hash.get("descripcion").toString());
                                detalleJson.put("porcentaje", hash.get("porcentaje").toString());
                                detalleJson.put("cantidad", hash.get("cantidad").toString());
                                detalleJson.put("cantidad_total", hash.get("cantidad_total").toString());
                                jsonArray.put(detalleJson);
                            }
                        }
                        out.println(jsonArray);
                        break;

                    case 5://Cantidad Vehiculos Censados por Placa

                        lista = estadisticaDao.ListarCantidadVehiculosPlacaProvidencia(conex);
                        if (!lista.isEmpty()) {
                            for (HashMap hash : lista) {
                                JSONObject detalleJson = new JSONObject();
                                detalleJson.put("descripcion", hash.get("descripcion").toString());
                                detalleJson.put("porcentaje", hash.get("porcentaje").toString());
                                detalleJson.put("cantidad", hash.get("cantidad").toString());
                                detalleJson.put("cantidad_total", hash.get("cantidad_total").toString());
                                jsonArray.put(detalleJson);
                            }
                        }
                        out.println(jsonArray);
                        break;

                    case 6://Cantidad Vehiculos con Soat
                        lista = estadisticaDao.ListarCantidadVehiculosSoatProvidencia(conex);
                        if (!lista.isEmpty()) {
                            for (HashMap hash : lista) {
                                JSONObject detalleJson = new JSONObject();
                                detalleJson.put("descripcion", hash.get("descripcion").toString());
                                detalleJson.put("cantidad", hash.get("cantidad").toString());
                                detalleJson.put("cantidad_total", hash.get("cantidad_total").toString());
                                jsonArray.put(detalleJson);
                            }
                        }
                        out.println(jsonArray);
                        break;

                    case 7://Cantidad Vehiculos con Tecno
                        lista = estadisticaDao.ListarCantidadVehiculosTecnoProvidencia(conex);
                        if (!lista.isEmpty()) {
                            for (HashMap hash : lista) {
                                JSONObject detalleJson = new JSONObject();
                                detalleJson.put("descripcion", hash.get("descripcion").toString());
                                detalleJson.put("cantidad", hash.get("cantidad").toString());
                                detalleJson.put("cantidad_total", hash.get("cantidad_total").toString());
                                jsonArray.put(detalleJson);
                            }
                        }
                        out.println(jsonArray);
                        break;

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException ex) {
            ex.printStackTrace();
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
