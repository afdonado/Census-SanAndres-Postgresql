package com.censo.controlador.censo;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.persistencia.CenCenso;
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

@WebServlet(name = "VerificarNumeroCenso", urlPatterns = {"/verificarNumeroCenso"})
public class VerificarNumeroCenso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Connection conex = null;

        Map<String, String> respuesta = new HashMap<>();

        try {
            
            if (request.getParameter("numero") == null || request.getParameter("numero").isEmpty()) {
                respuesta.put("status", "error");
                respuesta.put("message", "Parametro 'numero censo' no encontrado para verificar");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }
            
            String numero = request.getParameter("numero");
            if (numero.length() < 6) {
                String prefijo = "ACS";
                numero = prefijo + ("00000".substring(0, 5 - (numero + "").length())) + numero;
            } else {
                respuesta.put("status", "error");
                respuesta.put("message", "Numero de censo no puede ser mayor a 5 digitos");

                String jsonError = new Gson().toJson(respuesta);
                response.getWriter().write(jsonError);
                return;
            }

            CensoDao censoDao = new CensoDao();
            conex = censoDao.conectar();

            CenCenso cencenso = censoDao.ConsultarCensoByNumero(conex, numero);

            if (cencenso == null) {
                respuesta.put("status", "success");
                respuesta.put("message", "Numero censo valido");
            } else {
                respuesta.put("status", "fail");
                respuesta.put("message", "Numero censo no valido, ya se encuentra registrado");
                respuesta.put("id", String.valueOf(cencenso.getId()));
            }

        } catch (SQLException e) {
            respuesta.put("status", "error");
            respuesta.put("message", "Error al verificar el número de censo");
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
