package com.censo.controlador.persona;

import com.censo.modelo.dao.CategoriaLicenciaDao;
import com.censo.modelo.dao.PersonaDao;
import com.censo.modelo.dao.PersonaRuntDao;
import com.censo.modelo.persistencia.CenCategoriaLicencia;
import com.censo.modelo.persistencia.CenPersona;
import com.censo.modelo.persistencia.PersonaRunt;
import com.censo.modelo.persistencia.ResponsePersonaRunt;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "VerificarPersonaRunt", urlPatterns = {"/verificarPersonaRunt"})
public class VerificarPersonaRunt extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DataSource dataSource = (DataSource) getServletContext().getAttribute("DataSource");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, Object> respuesta = new HashMap<>();

        try {

            conex = dataSource.getConnection();

            //Validar parametro tipo documento
            if (request.getParameter("tipodocumento") == null || request.getParameter("tipodocumento").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'tipo documento' no encontrado para registrar la persona");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            //Validar parametro numero documento
            if (request.getParameter("documento") == null || request.getParameter("documento").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'numero documento' no encontrado para registrar la persona");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            int tipoDocumento = Integer.parseInt(request.getParameter("tipodocumento"));
            String documento = request.getParameter("documento").toUpperCase().trim();

            String tipo_documento;
            switch (tipoDocumento) {
                case 1:
                    tipo_documento = "01";
                    break;
                case 2:
                    tipo_documento = "03";
                    break;
                case 3:
                    tipo_documento = "02";
                    break;
                case 4:
                    tipo_documento = "PS";
                    break;
                case 5:
                    tipo_documento = "NI";
                    break;
                default:
                    tipo_documento = "01";
            }

            PersonaRuntDao personaRuntDao = new PersonaRuntDao();
            PersonaRunt personaRunt = personaRuntDao.ConsultarPersonaRuntByDocumento(conex, tipoDocumento, documento, "NO");

            if (personaRunt != null && personaRunt.getFuenteFallo().equals("NO")) {

                PersonaDao personaDao = new PersonaDao();
                CenPersona cenpersona = personaDao.ConsultarPersona(conex, tipoDocumento, documento);

                if (cenpersona != null) {
                    respuesta.put("status", "fail");
                    respuesta.put("message", "Tipo y Documento no valido, ya se encuentra registrada");
                } else {
                    respuesta.put("status", "success");
                    respuesta.put("personarunt", personaRunt);
                }

            } else {

                PersonaDao personaDao = new PersonaDao();
                CenPersona cenpersona = personaDao.ConsultarPersona(conex, tipoDocumento, documento);

                if (cenpersona == null) {
                    //String urlString = System.getenv("URL_RUNT_CEDULA");
                    String urlString = "http://produccion.konivin.com:32564/konivin/servicio/persona/consultar?lcy=Lagit&vpv=L4gIt&jor=23566548";
                    urlString = urlString.concat("&icf=").concat(tipo_documento).concat("&thy=CO&klm=").concat(documento);
                    URL url = new URL(urlString);

                    // Abrir conexión
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");

                    StringBuilder content;
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                        String inputLine;
                        content = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }
                    }
                    con.disconnect();

                    Gson gson = new Gson();
                    ResponsePersonaRunt responsePR = gson.fromJson(content.toString(), ResponsePersonaRunt.class);

                    // Validar si el dato existe
                    if (responsePR != null && responsePR.getFuenteFallo().equals("NO")) {

                        PersonaRunt personaRuntLicencia = new PersonaRunt();
                        if (!responsePR.getLicenciasConduccion().isEmpty()) {
                            for (ResponsePersonaRunt.LicenciaConduccion lc : responsePR.getLicenciasConduccion()) {
                                if (lc.getEstadoLicencia().equals("ACTIVA")) {
                                    CategoriaLicenciaDao categoriaLicenciaDao = new CategoriaLicenciaDao();
                                    CenCategoriaLicencia cencategorialicencia
                                            = categoriaLicenciaDao.ConsultarCategoriaLicenciaByDescripcion(conex, lc.getDetalleLicencias().get(0).getCategoria().toUpperCase().trim());
                                    int categorialicenciaId = 0;
                                    if (cencategorialicencia != null) {
                                        categorialicenciaId = cencategorialicencia.getId();
                                    }

                                    personaRuntLicencia = PersonaRunt.builder()
                                            .numeroLicencia(lc.getNumeroLicencia())
                                            .fechaExpedicion(lc.getDetalleLicencias().get(0).getFechaExpedicion())
                                            .fechaVencimiento(lc.getDetalleLicencias().get(0).getFechaVencimiento())
                                            .categoriaId(categorialicenciaId)
                                            .categoria(lc.getDetalleLicencias().get(0).getCategoria())
                                            .estadoLicencia(lc.getEstadoLicencia())
                                            .build();
                                    break;
                                } else {
                                    PersonaRunt.builder().build();
                                }
                            }
                        } else {
                            personaRuntLicencia = PersonaRunt.builder()
                                    .numeroLicencia(null)
                                    .fechaExpedicion(null)
                                    .fechaVencimiento(null)
                                    .categoriaId(0)
                                    .categoria(null)
                                    .estadoLicencia(null)
                                    .build();
                        }

                        DateTimeFormatter formatterEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                        String[] partesNombre = responsePR.getPersonaVO().getNombres().getRUNT().getPrimerNombre().split(" ");

                        String primerNombre = "";
                        String segundoNombre = "";

                        if (partesNombre.length > 0) {
                            primerNombre = partesNombre[0];
                        }

                        if (partesNombre.length > 1) {
                            segundoNombre = partesNombre[1];
                        }

                        String[] partesApellido = responsePR.getPersonaVO().getNombres().getRUNT().getPrimerApellido().split(" ");

                        String primerApellido = "";
                        String segundoApellido = "";

                        if (partesApellido.length > 0) {
                            primerApellido = partesApellido[0];
                        }

                        if (partesApellido.length > 1) {
                            segundoApellido = partesApellido[1];
                        }

                        personaRunt = PersonaRunt.builder()
                                .tipoDocumentoId(tipoDocumento)
                                .tipoDocumento(responsePR.getPersonaVO().getTipoDocumento())
                                .numeroDocumento(responsePR.getPersonaVO().getNumeroDocumento())
                                .nombre1(primerNombre)
                                .nombre2(segundoNombre)
                                .apellido1(primerApellido)
                                .apellido2(segundoApellido)
                                .numeroLicencia(personaRuntLicencia.getNumeroLicencia())
                                .fechaExpedicion(personaRuntLicencia.getFechaExpedicion() == null ? "" : formatter.format(formatterEntrada.parse(personaRuntLicencia.getFechaExpedicion())))
                                .fechaVencimiento(personaRuntLicencia.getFechaVencimiento() == null ? "" : formatter.format(formatterEntrada.parse(personaRuntLicencia.getFechaVencimiento())))
                                .categoriaId(personaRuntLicencia.getCategoriaId())
                                .categoria(personaRuntLicencia.getCategoria())
                                .estadoLicencia(personaRuntLicencia.getEstadoLicencia())
                                .fechaConsulta(responsePR.getFechaConsulta())
                                .fuenteFallo(responsePR.getFuenteFallo())
                                .build();

                        conex.setAutoCommit(false);

                        boolean runt_registrado = personaRuntDao.adicionarPersonaRunt(conex, personaRunt);

                        if (runt_registrado) {
                            conex.commit();
                            respuesta.put("status", "success");
                            respuesta.put("personarunt", personaRunt);
                        } else {
                            conex.rollback();
                        }

                    } else {
                        
                        personaRunt = personaRuntDao.ConsultarPersonaRuntByDocumento(conex, tipoDocumento, documento, "SI");
                        
                        LocalDateTime fechaActual = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        
                        if(personaRunt == null){
                            
                            personaRuntDao.adicionarPersonaRunt(conex, PersonaRunt.builder()
                                .tipoDocumentoId(tipoDocumento)
                                .tipoDocumento(tipo_documento)
                                .numeroDocumento(documento)
                                .categoriaId(0)
                                .fechaConsulta(fechaActual.format(formatter))
                                .fuenteFallo("SI")
                                .build());
                            
                        } else {
                            personaRuntDao.modificarPersonaRunt(conex, PersonaRunt.builder()
                                .tipoDocumento(tipo_documento)
                                .numeroDocumento(documento)
                                .fechaConsulta(fechaActual.format(formatter))
                                .fuenteFallo("SI")
                                .build());
                        }
                        respuesta.put("status", "fail");
                        respuesta.put("message", "Persona no encontrada en runt");
                    }
                } else {
                    respuesta.put("status", "fail");
                    respuesta.put("message", "Tipo y documento no validos, ya se encuentra registrada");
                }

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
            respuesta.put("message", "Error al verificar la persona");
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

        response.getWriter()
                .write(json);

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
