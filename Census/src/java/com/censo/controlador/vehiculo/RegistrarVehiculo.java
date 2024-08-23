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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrarVehiculo extends HttpServlet {

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
            
            dao.conectar().setAutoCommit(false);

            CenVehiculo cenvehiculo = new CenVehiculo();
            CenMarca cenmarca = new CenMarca();
            CenLinea cenlinea = new CenLinea();
            CenColor cencolor = new CenColor();

            long idlinea = 0;
            long idcolor = 0;

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

            boolean registrado = false;

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
            Date fechaMatricula = null;
            long paisMatricula = 0;
            long municipioMatricula = 0;
            String ciudaMatricula ="";
            if(!licenciaTransito.equals("")){
                fechaMatricula = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechamatri")).getTime());
                paisMatricula = Long.parseLong(request.getParameter("cmbpaismatricula"));
                municipioMatricula = Long.parseLong(request.getParameter("cmbmunicipiomatri"));
                ciudaMatricula = request.getParameter("txtciudadmatri").toUpperCase().trim();
            }
            
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
            Date fechaVenSoat = null;
            if (soat.equals("S")) {
                fechaVenSoat = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechavsoat")).getTime());
            }
            String tecnoMecanica = request.getParameter("cmbtecno").toUpperCase().trim();
            Date fechaVenTecnomecanica = null;
            if (tecnoMecanica.equals("S")) {
                fechaVenTecnomecanica = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechavsoat")).getTime());
            }

            String observaciones = request.getParameter("txtobservaciones").toUpperCase().trim();
            int cantpersonas = (Integer.parseInt(request.getParameter("txtcantpersonas")));
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
                long idVehiculo = dao.adicionarVehiculo(cenvehiculo);

                if (idVehiculo > 0) {

                    CenPersonaVehiculo cenpersonavehiculo = new CenPersonaVehiculo();

                    for (int i = 1; i <= cantpersonas; i++) {

                        int tipoPersona = Integer.parseInt(request.getParameter("cmbtipopersona" + i));
                        long idPersona = Long.parseLong(request.getParameter("idpersona" + i));

                        cenpersonavehiculo.setTper_id(tipoPersona);
                        cenpersonavehiculo.setPer_id(idPersona);
                        cenpersonavehiculo.setVeh_id(idVehiculo);
                        cenpersonavehiculo.setUsu_id(cenusuario.getId());
                        cenpersonavehiculo.setEstado(1);
                        long idPersonaVehiculo = daoPersonaVehiculo.adicionarPersonaVehiculo(cenpersonavehiculo);

                        if (idPersonaVehiculo > 0) {
                            registrado = true;
                        } else {
                            registrado = false;
                            break;
                        }
                    }
                }
            }

            if (registrado) {
                dao.conectar().commit();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Vehiculo Registrado');");
                out.println("location='jsp/Vehiculos/registrarVehiculo.jsp';");
                out.println("</script>");
            } else {
                dao.conectar().rollback();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Vehiculo no Registrado');");
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
