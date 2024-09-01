package com.censo.controlador.censo;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.persistencia.CenCenso;
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

@WebServlet(name = "ModificarCenso", urlPatterns = "/modificarCenso")
public class ModificarCenso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {
            CensoDao censoDao = new CensoDao();
            conex = censoDao.conectar();
            conex.setAutoCommit(false);

            boolean sw;

            Date fechaActual = new java.sql.Date(new java.util.Date().getTime());

            long idcenso = Long.parseLong(request.getParameter("idcenso"));

            String numero = request.getParameter("txtnumerocenso").toUpperCase().trim();
            if (numero.length() < 6) {
                String prefijo = "ACS";
                numero = prefijo + ("00000".substring(0, 5 - (numero + "").length())) + numero;
            }

            CenCenso cencenso = censoDao.ConsultarCensoById(conex, idcenso);
            if (cencenso != null) {
                if (cencenso.getNumero().equals(numero)) {
                    sw = true;
                } else {
                    cencenso = censoDao.ConsultarCensoByNumero(conex, numero);
                    if (cencenso != null) {
                        sw = false;
                    } else {
                        cencenso = censoDao.ConsultarCensoById(conex, idcenso);
                        sw = true;
                    }
                }

                if (sw) {
                    String hora = new java.text.SimpleDateFormat("HHmm").format(fechaActual);
                    int puntoAtencion = Integer.parseInt(request.getParameter("cmbpuntosatencion"));
                    long idvehiculo = Long.parseLong(request.getParameter("idvehiculo"));
                    long idpersona = Long.parseLong(request.getParameter("idpersona"));
                    int tipoPersona = Integer.parseInt(request.getParameter("cmbtipospersona"));
                    String observaciones = request.getParameter("txtobservaciones").toUpperCase().trim();
                    Date fechaCenso = new java.sql.Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("txtfechacenso")).getTime());

                    cencenso.setFecha(fechaCenso);
                    cencenso.setHora(hora);
                    cencenso.setPun_id(puntoAtencion);
                    cencenso.setVeh_id(idvehiculo);
                    cencenso.setPer_id(idpersona);
                    cencenso.setTper_id(tipoPersona);
                    cencenso.setNumero(numero);
                    cencenso.setObservaciones(observaciones);

                    censoDao.modificarCenso(conex, cencenso);
                    conex.commit();

                    respuesta.put("status", "success");
                    respuesta.put("message", "Censo modificado exitosamente");
                    respuesta.put("id", String.valueOf(idcenso));
                } else {
                    conex.rollback();
                    respuesta.put("status", "fail");
                    respuesta.put("message", "Censo no modificado");
                }
            } else {
                conex.rollback();
                respuesta.put("status", "fail");
                respuesta.put("message", "Censo no se encuentra registrado");
            }

        } catch (SQLException | ParseException e) {
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            respuesta.put("status", "error");
            respuesta.put("message", "Error al modificar el censo");
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
