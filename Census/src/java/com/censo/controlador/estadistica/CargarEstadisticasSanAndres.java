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

@WebServlet(name = "CargarEstadisticasSanAndres", urlPatterns = "/cargarEstadisticasSanAndres")
public class CargarEstadisticasSanAndres extends HttpServlet {
    
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
                        lista = estadisticaDao.ListarCantidadCensosClaveVehiculoSanAndres(conex);
                        if (!lista.isEmpty()) {
                            for (HashMap hash : lista) {
                                JSONObject detalleJson = new JSONObject();
                                detalleJson.put("CLV_DESCRIPCION", hash.get("CLV_DESCRIPCION").toString());
                                detalleJson.put("CANTIDAD", hash.get("CANTIDAD").toString());
                                detalleJson.put("CANTIDAD_TOTAL", hash.get("CANTIDAD_TOTAL").toString());
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
                                detalleJson.put("PUN_DESCRIPCION", hash.get("PUN_DESCRIPCION").toString());
                                detalleJson.put("CANTIDAD", hash.get("CANTIDAD").toString());
                                detalleJson.put("CANTIDAD_TOTAL", hash.get("CANTIDAD_TOTAL").toString());
                                jsonArray.put(detalleJson);
                            }
                        }
                        out.println(jsonArray);
                        break;

                    case 3://Cantidad Personas Censadas por Genero

                        lista = estadisticaDao.ListarCantidadPersonasCensadasGeneroSanAndres(conex);
                        if (!lista.isEmpty()) {
                            for (HashMap hash : lista) {
                                JSONObject detalleJson = new JSONObject();
                                detalleJson.put("GENERO", hash.get("GENERO").toString());
                                detalleJson.put("PORCENTAJE", hash.get("PORCENTAJE").toString());
                                detalleJson.put("CANTIDAD_TOTAL", hash.get("CANTIDAD_TOTAL").toString());
                                jsonArray.put(detalleJson);
                            }
                        }
                        out.println(jsonArray);
                        break;

                    case 4://Cantidad Personas Censadas por Licencia

                        lista = estadisticaDao.ListarCantidadPersonasCensadasLicenciaSanAndres(conex);
                        if (!lista.isEmpty()) {
                            for (HashMap hash : lista) {
                                JSONObject detalleJson = new JSONObject();
                                detalleJson.put("DESCRIPCION", hash.get("DESCRIPCION").toString());
                                detalleJson.put("PORCENTAJE", hash.get("PORCENTAJE").toString());
                                detalleJson.put("CANTIDAD", hash.get("CANTIDAD").toString());
                                detalleJson.put("CANTIDAD_TOTAL", hash.get("CANTIDAD_TOTAL").toString());
                                jsonArray.put(detalleJson);
                            }
                        }
                        out.println(jsonArray);
                        break;

                    case 5://Cantidad Vehiculos Censados por Placa

                        lista = estadisticaDao.ListarCantidadVehiculosPlacaSanAndres(conex);
                        if (!lista.isEmpty()) {
                            for (HashMap hash : lista) {
                                JSONObject detalleJson = new JSONObject();
                                detalleJson.put("DESCRIPCION", hash.get("DESCRIPCION").toString());
                                detalleJson.put("PORCENTAJE", hash.get("PORCENTAJE").toString());
                                detalleJson.put("CANTIDAD", hash.get("CANTIDAD").toString());
                                detalleJson.put("CANTIDAD_TOTAL", hash.get("CANTIDAD_TOTAL").toString());
                                jsonArray.put(detalleJson);
                            }
                        }
                        out.println(jsonArray);
                        break;

                    case 6://Cantidad Vehiculos con Soat
                        lista = estadisticaDao.ListarCantidadVehiculosSoatSanAndres(conex);
                        if (!lista.isEmpty()) {
                            for (HashMap hash : lista) {
                                JSONObject detalleJson = new JSONObject();
                                detalleJson.put("DESCRIPCION", hash.get("DESCRIPCION").toString());
                                detalleJson.put("CANTIDAD", hash.get("CANTIDAD").toString());
                                detalleJson.put("CANTIDAD_TOTAL", hash.get("CANTIDAD_TOTAL").toString());
                                jsonArray.put(detalleJson);
                            }
                        }
                        out.println(jsonArray);
                        break;

                    case 7://Cantidad Vehiculos con Tecno
                        lista = estadisticaDao.ListarCantidadVehiculosTecnoSanAndres(conex);
                        if (!lista.isEmpty()) {
                            for (HashMap hash : lista) {
                                JSONObject detalleJson = new JSONObject();
                                detalleJson.put("DESCRIPCION", hash.get("DESCRIPCION").toString());
                                detalleJson.put("CANTIDAD", hash.get("CANTIDAD").toString());
                                detalleJson.put("CANTIDAD_TOTAL", hash.get("CANTIDAD_TOTAL").toString());
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
