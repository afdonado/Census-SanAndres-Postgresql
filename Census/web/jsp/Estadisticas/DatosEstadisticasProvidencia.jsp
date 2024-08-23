<%@page import="com.censo.modelo.dao.EstadisticaDao"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%
    try {

        EstadisticaDao estadisticaDao = new EstadisticaDao();
        
        int opcion = Integer.parseInt(request.getParameter("opcion"));
        List<HashMap> datosEstadistica = null;
        JSONArray jsonArray = new JSONArray();
        switch (opcion) {
            case 1://Cantidad Censos por Clase de Vehiculo
                datosEstadistica = estadisticaDao.ListarCantidadCensosClaveVehiculoProvidencia();

                for (HashMap hash : datosEstadistica) {
                    JSONObject detalleJson = new JSONObject();
                    detalleJson.put("CLV_DESCRIPCION", hash.get("CLV_DESCRIPCION").toString());
                    detalleJson.put("CANTIDAD", hash.get("CANTIDAD").toString());
                    detalleJson.put("CANTIDAD_TOTAL", hash.get("CANTIDAD_TOTAL").toString());
                    jsonArray.put(detalleJson);
                }
                out.println(jsonArray);
                break;
            case 2://Cantidad Censos por Punto de Atencion

                datosEstadistica = estadisticaDao.ListarCantidadCensosPuntoAtencion();

                for (HashMap hash : datosEstadistica) {
                    JSONObject detalleJson = new JSONObject();
                    detalleJson.put("PUN_DESCRIPCION", hash.get("PUN_DESCRIPCION").toString());
                    detalleJson.put("CANTIDAD", hash.get("CANTIDAD").toString());
                    detalleJson.put("CANTIDAD_TOTAL", hash.get("CANTIDAD_TOTAL").toString());
                    jsonArray.put(detalleJson);
                }
                out.println(jsonArray);
                break;
                
            case 3://Cantidad Personas Censadas por Genero

                datosEstadistica = estadisticaDao.ListarCantidadPersonasCensadasGeneroProvidencia();

                for (HashMap hash : datosEstadistica) {
                    JSONObject detalleJson = new JSONObject();
                    detalleJson.put("GENERO", hash.get("GENERO").toString());
                    detalleJson.put("PORCENTAJE", hash.get("PORCENTAJE").toString());
                    detalleJson.put("CANTIDAD_TOTAL", hash.get("CANTIDAD_TOTAL").toString());
                    jsonArray.put(detalleJson);
                }
                out.println(jsonArray);
                break;
                
            case 4://Cantidad Personas Censadas por Licencia

                datosEstadistica = estadisticaDao.ListarCantidadPersonasCensadasLicenciaProvidencia();

                for (HashMap hash : datosEstadistica) {
                    JSONObject detalleJson = new JSONObject();
                    detalleJson.put("DESCRIPCION", hash.get("DESCRIPCION").toString());
                    detalleJson.put("PORCENTAJE", hash.get("PORCENTAJE").toString());
                    detalleJson.put("CANTIDAD", hash.get("CANTIDAD").toString());
                    detalleJson.put("CANTIDAD_TOTAL", hash.get("CANTIDAD_TOTAL").toString());
                    jsonArray.put(detalleJson);
                }
                out.println(jsonArray);
                break;
                
            case 5://Cantidad Vehiculos Censados por Placa

                datosEstadistica = estadisticaDao.ListarCantidadVehiculosPlacaProvidencia();

                for (HashMap hash : datosEstadistica) {
                    JSONObject detalleJson = new JSONObject();
                    detalleJson.put("DESCRIPCION", hash.get("DESCRIPCION").toString());
                    detalleJson.put("PORCENTAJE", hash.get("PORCENTAJE").toString());
                    detalleJson.put("CANTIDAD", hash.get("CANTIDAD").toString());
                    detalleJson.put("CANTIDAD_TOTAL", hash.get("CANTIDAD_TOTAL").toString());
                    jsonArray.put(detalleJson);
                }
                out.println(jsonArray);
                break;
                
            case 6://Cantidad Vehiculos con Soat
                datosEstadistica = estadisticaDao.ListarCantidadVehiculosSoatProvidencia();

                for (HashMap hash : datosEstadistica) {
                    JSONObject detalleJson = new JSONObject();
                    detalleJson.put("DESCRIPCION", hash.get("DESCRIPCION").toString());
                    detalleJson.put("CANTIDAD", hash.get("CANTIDAD").toString());
                    detalleJson.put("CANTIDAD_TOTAL", hash.get("CANTIDAD_TOTAL").toString());
                    jsonArray.put(detalleJson);
                }
                out.println(jsonArray);
                break;
                
            case 7://Cantidad Vehiculos con Tecno
                datosEstadistica = estadisticaDao.ListarCantidadVehiculosTecnoProvidencia();

                for (HashMap hash : datosEstadistica) {
                    JSONObject detalleJson = new JSONObject();
                    detalleJson.put("DESCRIPCION", hash.get("DESCRIPCION").toString());
                    detalleJson.put("CANTIDAD", hash.get("CANTIDAD").toString());
                    detalleJson.put("CANTIDAD_TOTAL", hash.get("CANTIDAD_TOTAL").toString());
                    jsonArray.put(detalleJson);
                }
                out.println(jsonArray);
                break;
        }

        

    } catch (Exception e) {
        java.io.StringWriter sw = new java.io.StringWriter();
        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
        e.printStackTrace(pw);
        out.println(sw.toString());
    }

%>
