package com.censo.controlador.vehiculo;

import com.censo.modelo.dao.ColorDao;
import com.censo.modelo.dao.LineaDao;
import com.censo.modelo.dao.MarcaDao;
import com.censo.modelo.dao.PersonaVehiculoDao;
import com.censo.modelo.dao.VehiculoDao;
import com.censo.modelo.persistencia.CenColor;
import com.censo.modelo.persistencia.CenLinea;
import com.censo.modelo.persistencia.CenMarca;
import com.censo.modelo.persistencia.CenPersonaVehiculo;
import com.censo.modelo.persistencia.CenUsuario;
import com.censo.modelo.persistencia.CenVehiculo;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegistrarVehiculo", urlPatterns = "/registrarVehiculo")
public class RegistrarVehiculo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {

            VehiculoDao vehiculoDao = new VehiculoDao();
            conex = vehiculoDao.conectar();
            MarcaDao daoMarca = new MarcaDao();
            LineaDao daoLinea = new LineaDao();
            ColorDao daoColor = new ColorDao();
            PersonaVehiculoDao daoPersonaVehiculo = new PersonaVehiculoDao();

            CenUsuario cenusuario = (CenUsuario) request.getSession().getAttribute("usuario");

            conex.setAutoCommit(false);

            CenVehiculo cenvehiculo = new CenVehiculo();

            long idlinea = 0;
            long idcolor = 0;

            String placa = request.getParameter("txtplaca").toUpperCase().trim();
            String motor = request.getParameter("txtmotor").toUpperCase().trim();
            String chasis = request.getParameter("txtchasis").toUpperCase().trim();
            String serie = request.getParameter("txtserie").toUpperCase().trim();
            int claseVeh = Integer.parseInt(request.getParameter("cmbclasevehiculo"));
            int tipoServicio = Integer.parseInt(request.getParameter("cmbtiposservicio"));
            int tipoUso = Integer.parseInt(request.getParameter("cmbtiposuso"));
            String nomcolor = request.getParameter("txtcolores").toUpperCase().trim();
            String nommarca = request.getParameter("txtmarcas").toUpperCase().trim();
            String nomlinea = request.getParameter("txtlineas").toUpperCase().trim();

            boolean registrado = false;

            CenMarca cenmarca = daoMarca.ConsultarMarcaByNombre(conex, nommarca);
            if (cenmarca != null) {
                CenLinea cenlinea = daoLinea.ConsultarLineaByNombreIdMarca(conex, nomlinea, cenmarca.getId());
                if (cenlinea != null) {
                    idlinea = cenlinea.getId();
                }
            }

            CenColor cencolor = daoColor.ConsultarColorByNombre(conex, nomcolor);
            if (cencolor != null) {
                idcolor = cencolor.getId();
            }

            long modeloveh = Long.parseLong(request.getParameter("txtmodelo"));
            String transformado = request.getParameter("cmbtransformado").toUpperCase().trim();
            String runt = request.getParameter("cmbrunt").toUpperCase().trim();
            String licenciaTransito = request.getParameter("txtlicenciatransito").toUpperCase().trim();
            Date fechaMatricula = null;
            long paisMatricula = 0;
            long municipioMatricula = 0;
            String ciudaMatricula = "";
            if (!licenciaTransito.equals("")) {
                fechaMatricula = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechamatricula")).getTime());
                paisMatricula = Long.parseLong(request.getParameter("cmbpaismatricula"));
                municipioMatricula = Long.parseLong(request.getParameter("cmbmunicipiomatricula"));
                ciudaMatricula = request.getParameter("txtciudadmatricula").toUpperCase().trim();
            }

            int tipoDocImportacion = Integer.parseInt(request.getParameter("cmbtiposimportacion"));
            String documentoImportacion = "";
            Date fechaImportacion = null;
            long paisImportacion = 0;

            if (tipoDocImportacion != 0) {
                documentoImportacion = request.getParameter("txtdocumentoimportacion").toUpperCase().trim();
                fechaImportacion = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechaimportacion")).getTime());
                paisImportacion = Long.parseLong(request.getParameter("cmbpaisimportacion"));
            }
            String soat = request.getParameter("cmbsoat").toUpperCase().trim();
            Date fechaVenSoat = null;
            if (soat.equals("S")) {
                fechaVenSoat = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechavsoat")).getTime());
            }
            String tecnoMecanica = request.getParameter("cmbtecnomecanica").toUpperCase().trim();
            Date fechaVenTecnomecanica = null;
            if (tecnoMecanica.equals("S")) {
                fechaVenTecnomecanica = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechavtecnomecanica")).getTime());
            }

            String observaciones = request.getParameter("txtobservaciones").toUpperCase().trim();
            int cantpersonas = (Integer.parseInt(request.getParameter("txtcantidadpersonas")));
            if (cantpersonas > 0) {

                cenvehiculo.setPlaca_veh(placa);
                cenvehiculo.setChasis(chasis);
                cenvehiculo.setSerie(serie);
                cenvehiculo.setMotor(motor);
                cenvehiculo.setClase_veh(claseVeh);
                cenvehiculo.setTipo_servicio(tipoServicio);
                cenvehiculo.setTipo_uso(tipoUso);
                cenvehiculo.setCol_id(idcolor);
                cenvehiculo.setLin_id(idlinea);
                cenvehiculo.setModelo(modeloveh);
                cenvehiculo.setTransformado(transformado);
                cenvehiculo.setRunt(runt);
                cenvehiculo.setLicencia_transito(licenciaTransito);
                cenvehiculo.setFecha_matricula(fechaMatricula);
                cenvehiculo.setPai_id_matricula(paisMatricula);
                cenvehiculo.setMun_id_matricula(municipioMatricula);
                cenvehiculo.setCiudad_matricula(ciudaMatricula);
                cenvehiculo.setTipodoc_importacion(tipoDocImportacion);
                cenvehiculo.setDoc_importacion(documentoImportacion);
                cenvehiculo.setFecha_importacion(fechaImportacion);
                cenvehiculo.setPai_id_origen(paisImportacion);
                cenvehiculo.setSoat(soat);
                cenvehiculo.setFechaven_soat(fechaVenSoat);
                cenvehiculo.setTecno_mecanica(tecnoMecanica);
                cenvehiculo.setFechaven_tecno(fechaVenTecnomecanica);
                cenvehiculo.setObservaciones(observaciones);
                cenvehiculo.setUsu_id(cenusuario.getId());
                cenvehiculo.setEstado(1);
                long idVehiculo = vehiculoDao.adicionarVehiculo(conex, cenvehiculo);

                if (idVehiculo > 0) {

                    CenPersonaVehiculo cenpersonavehiculo = new CenPersonaVehiculo();

                    for (int i = 1; i <= cantpersonas; i++) {

                        int tipoPersona = Integer.parseInt(request.getParameter("cmbtipospersona" + i));
                        long idPersona = Long.parseLong(request.getParameter("idpersona" + i));

                        cenpersonavehiculo.setTper_id(tipoPersona);
                        cenpersonavehiculo.setPer_id(idPersona);
                        cenpersonavehiculo.setVeh_id(idVehiculo);
                        cenpersonavehiculo.setUsu_id(cenusuario.getId());
                        cenpersonavehiculo.setEstado(1);
                        long idPersonaVehiculo = daoPersonaVehiculo.adicionarPersonaVehiculo(conex, cenpersonavehiculo);

                        if (idPersonaVehiculo > 0) {
                            registrado = true;
                        } else {
                            registrado = false;
                            break;
                        }
                    }
                }

                if (registrado) {
                    conex.commit();
                    respuesta.put("status", "success");
                    respuesta.put("message", "Vehiculo registrado exitosamente");
                    respuesta.put("id", String.valueOf(idVehiculo));
                } else {
                    conex.rollback();
                    respuesta.put("status", "fail");
                    respuesta.put("message", "Vehiculo no registrado");
                }
            }

        } catch (NumberFormatException | SQLException | ParseException e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            respuesta.put("status", "error");
            respuesta.put("message", "Error al registrar el vehiculo");
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
