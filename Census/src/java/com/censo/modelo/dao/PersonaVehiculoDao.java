package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenPersonaVehiculo;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

public class PersonaVehiculoDao {

    public int adicionarPersonaVehiculo(Connection conex, CenPersonaVehiculo cenpersonasvehiculo) {

        String sql = "INSERT INTO CEN_PERSONA_VEHICULO (TPER_ID,PER_ID,VEH_ID,"
                + "PV_FECHAINICIO,PV_FECHAFINAL,USU_ID,EST_ID,PV_FECHAPROCESO) VALUES (?,?,?,now(),null,?,?,now())";
        try (PreparedStatement pst = conex.prepareStatement(sql, new String[]{"pv_id"})) {
            pst.setInt(1, cenpersonasvehiculo.getTper_id());
            pst.setInt(2, cenpersonasvehiculo.getPer_id());
            pst.setInt(3, cenpersonasvehiculo.getVeh_id());
            pst.setInt(4, cenpersonasvehiculo.getUsu_id());
            pst.setInt(5, cenpersonasvehiculo.getEstado());
            pst.executeUpdate();
            try (ResultSet rst = pst.getGeneratedKeys()) {
                if (rst != null) {
                    if (rst.next()) {
                        return rst.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en adicionar personaVehiculo: " + e);
            return 0;
        }
        return 0;
    }

    public boolean modificarPersonaVehiculo(Connection conex, CenPersonaVehiculo cenpersonasvehiculo) throws SQLException, IOException {

        String sql = "UPDATE CEN_PERSONA_VEHICULO SET TPER_ID = ?, PER_ID = ?, USU_ID = ?, PV_FECHAPROCESO = now() WHERE PV_ID = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, cenpersonasvehiculo.getTper_id());
            pst.setInt(2, cenpersonasvehiculo.getPer_id());
            pst.setInt(3, cenpersonasvehiculo.getUsu_id());
            pst.setInt(4, cenpersonasvehiculo.getId());
            pst.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.err.println("Error en modificarPersonaVehiculo: " + e);
        }
        return false;
    }

    public List ListarPersonasVehiculoByIdVehiculo(Connection conex, int idvehiculo) throws SQLException {

        List listaPersonaVehiculo = new LinkedList();

        String sql = "SELECT * FROM CEN_PERSONA_VEHICULO WHERE VEH_ID = ? ORDER BY PV_FECHAINICIO,TPER_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, idvehiculo);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    listaPersonaVehiculo.add(CenPersonaVehiculo.load(rst));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarPersonasVehiculoByIdVehiculo: " + e);
        }
        return listaPersonaVehiculo;
    }

    public List ListarPersonasVehiculoByIdPersona(Connection conex, int idpersona) throws SQLException {

        List listaPersonaVehiculo = new LinkedList();

        String sql = "SELECT * FROM CEN_PERSONA_VEHICULO WHERE PER_ID = ? ORDER BY PV_FECHAINICIO,TPER_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, idpersona);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    listaPersonaVehiculo.add(CenPersonaVehiculo.load(rst));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarPersonasVehiculoByIdPersona: " + e);
        }
        return listaPersonaVehiculo;
    }

    public List<HashMap<String, Object>> ListarHashPersonasVehiculoByIdVehiculo(Connection conex, int idvehiculo) throws SQLException {

        List<HashMap<String, Object>> lista = new LinkedList<>();

        String sql = "SELECT * FROM VW_PERSONA_VEHICULO WHERE VEH_ID = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, idvehiculo);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    ResultSetMetaData rsmd = rst.getMetaData();
                    HashMap<String, Object> hash = new HashMap<>();
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        hash.put(rsmd.getColumnName(i + 1), rst.getObject(i + 1));
                    }
                    lista.add(hash);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarHashPersonasVehiculoByIdVehiculo: " + e);
        }
        return lista;
    }

    public List<HashMap<String, Object>> ListarHashPersonasVehiculoActivasByIdVehiculo(Connection conex, int idvehiculo) throws SQLException {

        List<HashMap<String, Object>> lista = new LinkedList<>();

        String sql = "SELECT * FROM VW_PERSONA_VEHICULO WHERE EST_ID = 1 AND VEH_ID = ? ";

        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, idvehiculo);
            try (ResultSet rst = pst.executeQuery()) {

                while (rst.next()) {
                    ResultSetMetaData rsmd = rst.getMetaData();
                    HashMap<String, Object> hash = new HashMap<>();
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                    }
                    lista.add(hash);
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error en ListarHashPersonasVehiculoByIdVehiculo: " + e);
        }
        return lista;
    }

    public boolean anularPersonaVehiculo(Connection conex, int id) throws SQLException, IOException {

        String sql = "UPDATE CEN_PERSONA_VEHICULO SET PV_FECHAFINAL = now(), EST_ID = 2 WHERE PV_ID = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error en anularPersonaVehiculo: " + e);
        }
        return false;
    }

}
