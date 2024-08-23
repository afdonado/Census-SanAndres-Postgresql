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
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModificarVehiculo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            
            VehiculoDao dao = new VehiculoDao();
            MarcaDao daoMarca = new MarcaDao();
            LineaDao daoLinea = new LineaDao();
            ColorDao daoColor = new ColorDao();
            PersonaVehiculoDao daoPersonaVehiculo = new PersonaVehiculoDao();
            
            CenUsuario cenusuario = (CenUsuario) request.getSession().getAttribute("usuario");

            CenVehiculo cenvehiculo = new CenVehiculo();
            CenMarca cenmarca = new CenMarca();
            CenLinea cenlinea = new CenLinea();
            CenColor cencolor = new CenColor();
            
            dao.conectar().setAutoCommit(false);

            long idlinea = 0;
            long idcolor = 0;

            boolean registrado = false;
            
            long idvehiculo = Long.parseLong(request.getParameter("idvehiculo"));
            String placa = request.getParameter("txtplaca").toUpperCase().trim();
            String motor = request.getParameter("txtmotor").toUpperCase().trim();
            String chasis = request.getParameter("txtchasis").toUpperCase().trim();
            String serie = request.getParameter("txtserie").toUpperCase().trim();
            int claseVeh = Integer.parseInt(request.getParameter("cmbclaseveh"));
            int tipoServicio = Integer.parseInt(request.getParameter("cmbservicio"));
            int tipoUso = Integer.parseInt(request.getParameter("cmbtipouso"));
            String nomcolor = request.getParameter("txtcolores").toUpperCase().trim();
            String nommarca = request.getParameter("txtmarcas").toUpperCase().trim();
            String nomlinea = request.getParameter("txtlineas").toUpperCase().trim();

            cenmarca = daoMarca.ConsultarMarcaByNombre(nommarca);
            if (cenmarca != null) {
                cenlinea = daoLinea.ConsultarLineaByNombreIdMarca(nomlinea, cenmarca.getId());
                if (cenlinea != null) {
                    idlinea = cenlinea.getId();
                }
            }

            cencolor = daoColor.ConsultarColorByNombre(nomcolor);
            if (cencolor != null) {
                idcolor = cencolor.getId();
            }

            long modeloveh = Long.parseLong(request.getParameter("txtmodelo"));
            String transformado = request.getParameter("cmbtransformado").toUpperCase().trim();
            String runt = request.getParameter("cmbrunt").toUpperCase().trim();
            String licenciaTransito = request.getParameter("txtlictransito").toUpperCase().trim();
            Date fechaMatricula = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechamatri")).getTime());
            long paisMatricula = Long.parseLong(request.getParameter("cmbpaismatricula"));
            long municipioMatricula = Long.parseLong(request.getParameter("cmbmunicipiomatri"));
            String ciudaMatricula = request.getParameter("txtciudadmatri").toUpperCase().trim();
            int tipoDocImportacion = Integer.parseInt(request.getParameter("cmdtipodocimportacion"));
            String documentoImportacion = "";
            Date fechaImportacion = null;
            long paisImportacion = 0;

            if (tipoDocImportacion != 1) {
                documentoImportacion = request.getParameter("txtdocimportacion").toUpperCase().trim();
                fechaImportacion = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechaimportacion")).getTime());
                paisImportacion = Long.parseLong(request.getParameter("cmbpaisimportacion"));
            }
            String soat = request.getParameter("cmbsoat").toUpperCase().trim();
            Date fechaVenSoat = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechavsoat")).getTime());
            String tecnoMecanica = request.getParameter("cmbtecno").toUpperCase().trim();
            Date fechaVenTecnomecanica = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechavtecno")).getTime());

            String observaciones = request.getParameter("txtobservaciones").toUpperCase().trim();
            int cantpersonas = (Integer.parseInt(request.getParameter("txtcantpersonas")));

            cenvehiculo.setId(idvehiculo);
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
            cenvehiculo.setEstado(1);
            registrado = dao.modificarVehiculo(cenvehiculo);

            if (registrado) {

                List<HashMap> listapersonasVehiculo = daoPersonaVehiculo.ListarHashPersonasVehiculoActivasByIdVehiculo(cenvehiculo.getId());

                if (listapersonasVehiculo.size() > 0) {

                    for (HashMap hash : listapersonasVehiculo) {
                        long idperveh = Long.parseLong(hash.get("PV_ID").toString());
                        int estadoperveh = Integer.parseInt(request.getParameter("estadoperveh" + idperveh));
                        if (estadoperveh == 2) {
                            daoPersonaVehiculo.anularPersonaVehiculo(idperveh);
                        }
                    }
                }
            }
            
            if(registrado){

                if (cantpersonas > 0) {
                    CenPersonaVehiculo cenpersonavehiculo = new CenPersonaVehiculo();

                    for (int i = 1; i <= cantpersonas; i++) {

                        int tipoPersona = Integer.parseInt(request.getParameter("cmbtipopersona" + i));
                        long idPersona = Long.parseLong(request.getParameter("idpersona" + i));

                        cenpersonavehiculo.setTper_id(tipoPersona);
                        cenpersonavehiculo.setPer_id(idPersona);
                        cenpersonavehiculo.setVeh_id(idvehiculo);
                        cenpersonavehiculo.setUsu_id(cenusuario.getId());
                        cenpersonavehiculo.setEstado(1);
                        long idPersonaVehiculo = daoPersonaVehiculo.adicionarPersonaVehiculo(cenpersonavehiculo);

                        if (idPersonaVehiculo == 0) {
                            registrado = false;
                            break;
                        } else {
                            registrado = true;
                        }
                    }
                }
            }

            if (registrado) {
                dao.conectar().commit();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Vehiculo Modificado');");
                out.println("location='jsp/Vehiculos/verVehiculo.jsp?idvehiculo="+idvehiculo+"';");
                out.println("</script>");
            } else {
                dao.conectar().rollback();
                out.println("<script type=\"text/javascript\">");
                out.println("location='jsp/Vehiculos/verVehiculo.jsp?idvehiculo="+idvehiculo+"';");
                out.println("</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
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
