package com.censo.controlador.censo;

import com.censo.modelo.dao.CensoDao;
import com.censo.modelo.dao.PersonaVehiculoDao;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CargarDatosCenso", urlPatterns = {"/cargarDatosCenso"})
public class CargarDatosCenso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conex = null;

        try {

            if (!request.getParameter("id").equals("")) {

                CensoDao censoDao = new CensoDao();
                conex = censoDao.conectar();

                long idcenso = Long.parseLong(request.getParameter("id"));

                HashMap<String, String> datosCenso = censoDao.ConsultarDatosCensoById(conex, idcenso);

                if (!datosCenso.isEmpty()) {

                    PersonaVehiculoDao personaVehiculoDao = new PersonaVehiculoDao();
                    List<HashMap> personasVehiculo = personaVehiculoDao.ListarHashPersonasVehiculoActivasByIdVehiculo(conex, Long.parseLong(datosCenso.get("VEH_ID")));

                    Map<String, Object> responseMap = new HashMap<>();
                    responseMap.put("censo", datosCenso);
                    responseMap.put("personasVehiculo", personasVehiculo);

                    Gson gson = new Gson();
                    String json = gson.toJson(responseMap);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);

                }
            }

        } catch (SQLException e) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Error al consultar los datos del censo');");
            out.println("location='dashboard';");
            out.println("</script>");
            e.printStackTrace();

        } finally {
            if (conex != null) {
                try {
                    conex.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
            out.close();
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
