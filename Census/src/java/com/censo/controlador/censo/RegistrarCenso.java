package com.censo.controlador.censo;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.dao.DocumentoDigitalizadoDao;
import com.censo.modelo.dao.VehiculoDao;
import com.censo.modelo.persistencia.CenCenso;
import com.censo.modelo.persistencia.CenDocumentosDigitalizado;
import com.censo.modelo.persistencia.CenUsuario;
import com.censo.modelo.persistencia.CenVehiculo;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "RegistrarCenso", urlPatterns = "/registrarCenso")
public class RegistrarCenso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {

            conex = dataSource.getConnection();

            CenUsuario cenusuario = (CenUsuario) request.getSession().getAttribute("usuario");

            if (request.getParameter("txtnumerocenso") == null || request.getParameter("txtnumerocenso").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'numero censo' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            String numero = request.getParameter("txtnumerocenso").toUpperCase().trim();

            if (numero.length() < 6) {
                String prefijo = "ACS";
                numero = (prefijo + ("00000".substring(0, 5 - (numero + "").length())) + numero).toUpperCase().trim();
            } else {
                respuesta.put("status", "error");
                respuesta.put("message", "Numero de censo no puede ser mayor a 5 digitos");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            CensoDao censoDao = new CensoDao();
            CenCenso cencenso = censoDao.ConsultarCensoByNumero(conex, numero);

            if (cencenso != null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Numero de censo no valido para registrarlo");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            if (request.getParameter("txtfechacenso") == null || request.getParameter("txtfechacenso").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'fecha censo' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            if (request.getParameter("cmbpuntosatencion") == null || request.getParameter("cmbpuntosatencion").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'punto de atencion' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            if (request.getParameter("idvehiculo") == null || request.getParameter("idvehiculo").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'id vehiculo' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            if (request.getParameter("txtobservaciones") == null) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'observaciones' no encontrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaCenso = LocalDate.parse(request.getParameter("txtfechacenso"), formatter);

            DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime horaActual = LocalTime.now();
            String hora = horaActual.format(formatterHora);

            int puntoAtencion = Integer.parseInt(request.getParameter("cmbpuntosatencion"));
            int idvehiculo = Integer.parseInt(request.getParameter("idvehiculo"));
            
            VehiculoDao vehiculoDao = new VehiculoDao();
            CenVehiculo cenvehiculo = vehiculoDao.ConsultarVehiculoById(conex, idvehiculo);
            if(cenvehiculo == null){
                respuesta.put("status", "error");
                respuesta.put("message", "Vehiculo no se encuentra registrado");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            String observaciones = request.getParameter("txtobservaciones").toUpperCase().trim();

            conex.setAutoCommit(false);

            cencenso = new CenCenso();
            cencenso.setFecha(fechaCenso == null ? null : Date.valueOf(fechaCenso));
            cencenso.setHora(hora);
            cencenso.setPun_id(puntoAtencion);
            cencenso.setVeh_id(idvehiculo);
            cencenso.setUsu_id(cenusuario.getId());
            cencenso.setEstado(1);
            cencenso.setNumero(numero);
            cencenso.setObservaciones(observaciones);

            int idcenso = censoDao.adicionarCenso(conex, cencenso);

            if (idcenso > 0) {

                String directorio = "/documentos/vehiculos/" + cenvehiculo.getPlaca_veh() + "/";
                //String directorio = "C:/DocumentosDigitalizados/vehiculos/" + cenvehiculo.getPlaca_veh() + "/";

                File carpeta = new File(directorio);
                if (carpeta.exists() && carpeta.isDirectory()) {
                    File[] archivos = carpeta.listFiles((dir, nombre) -> nombre.endsWith(".pdf"));

                    if (archivos != null) {
                        for (File archivo : archivos) {
                            String nombreArchivo = archivo.getName();
                            String ruta = archivo.getAbsolutePath();

                            DocumentoDigitalizadoDao documentoDigitalizadoDao = new DocumentoDigitalizadoDao();
                            CenDocumentosDigitalizado cendocumentosdigitalizado = new CenDocumentosDigitalizado();
                            cendocumentosdigitalizado.setNombre(nombreArchivo);
                            cendocumentosdigitalizado.setRuta(ruta);
                            cendocumentosdigitalizado.setTipo(3);
                            cendocumentosdigitalizado.setReferencia_id(idcenso);
                            cendocumentosdigitalizado.setObservacion("Imagen cargada desde documentos vehiculos");
                            cendocumentosdigitalizado.setUsu_id(cenusuario.getId());
                            documentoDigitalizadoDao.adicionarDocumentoDigitalizado(conex, cendocumentosdigitalizado);
                        }
                    }
                }
                
                
                /*
                String directorioTag = "/documentos/tag/";
                //String directorioTag = "C:/DocumentosDigitalizados/tag/";

                File carpetaTag = new File(directorioTag);
                if (carpetaTag.exists() && carpetaTag.isDirectory()) {
                    File[] archivos = carpetaTag.listFiles((dir, nombre) -> nombre.endsWith(".png"));

                    if (archivos != null) {
                        for (File archivo : archivos) {
                            String nombreArchivo = archivo.getName();
                            String ruta = archivo.getAbsolutePath();

                            DocumentoDigitalizadoDao documentoDigitalizadoDao = new DocumentoDigitalizadoDao();
                            CenDocumentosDigitalizado cendocumentosdigitalizado = new CenDocumentosDigitalizado();
                            cendocumentosdigitalizado.setNombre(nombreArchivo);
                            cendocumentosdigitalizado.setRuta(ruta);
                            cendocumentosdigitalizado.setTipo(1);
                            cendocumentosdigitalizado.setReferencia_id(idcenso);
                            cendocumentosdigitalizado.setObservacion("Imagen cargada desde documentos tag");
                            cendocumentosdigitalizado.setUsu_id(cenusuario.getId());
                            documentoDigitalizadoDao.adicionarDocumentoDigitalizado(conex, cendocumentosdigitalizado);
                        }
                    }
                }
                */

                conex.commit();
                respuesta.put("status", "success");
                respuesta.put("message", "Censo registrado exitosamente");
                respuesta.put("id", String.valueOf(idcenso));
            } else {
                conex.rollback();
                respuesta.put("status", "fail");
                respuesta.put("message", "Censo no registrado");
            }

        } catch (SQLException e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            respuesta.put("status", "error");
            respuesta.put("message", "Error al registrar el censo");
            e.printStackTrace();
        } finally {
            if (conex != null) {
                try {
                    conex.setAutoCommit(true);
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
