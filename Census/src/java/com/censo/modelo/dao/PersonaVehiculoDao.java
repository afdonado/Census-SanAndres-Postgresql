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

public class PersonaVehiculoDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public int adicionarPersonaVehiculo(Connection conex, CenPersonaVehiculo cenpersonasvehiculo) {

        try {
            pst = conex.prepareStatement("INSERT INTO CEN_PERSONA_VEHICULO (TPER_ID,PER_ID,VEH_ID,"
                    + "PV_FECHAINICIO,PV_FECHAFINAL,USU_ID,EST_ID,PV_FECHAPROCESO) VALUES (?,?,?,sysdate,null,?,?,sysdate)", new String[]{"PV_ID"});
            pst.setInt(1, cenpersonasvehiculo.getTper_id());
            pst.setInt(2, cenpersonasvehiculo.getPer_id());
            pst.setInt(3, cenpersonasvehiculo.getVeh_id());
            pst.setInt(4, cenpersonasvehiculo.getUsu_id());
            pst.setInt(5, cenpersonasvehiculo.getEstado());
            pst.executeUpdate();
            rst = pst.getGeneratedKeys();
            if (rst != null) {
                if (rst.next()) {
                    return rst.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en adicionar personaVehiculo: " + e);
            return 0;
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de personaVehiculo:" + e);
                return 0;
            }
        }
        return 0;
    }
    
    public boolean modificarPersonaVehiculo(Connection conex, CenPersonaVehiculo cenpersonasvehiculo) throws SQLException, IOException {

        try {
            pst = conex.prepareStatement("UPDATE CEN_PERSONA_VEHICULO SET TPER_ID = ?, PER_ID = ?, USU_ID = ?, PV_FECHAPROCESO = sysdate WHERE PV_ID = ? ");
            pst.setInt(1, cenpersonasvehiculo.getTper_id());
            pst.setInt(2, cenpersonasvehiculo.getPer_id());
            pst.setInt(3, cenpersonasvehiculo.getUsu_id());
            pst.setInt(4, cenpersonasvehiculo.getId());
            pst.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.err.println("Error en modificarPersonaVehiculo: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de modificarPersonaVehiculo:" + e);
            }
        }

        return false;
    }

    public List ListarPersonasVehiculoByIdVehiculo(Connection conex, int idvehiculo) throws SQLException {

        List listaPersonaVehiculo = new LinkedList();

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_PERSONA_VEHICULO WHERE VEH_ID = ? ORDER BY PV_FECHAINICIO,TPER_ID  ");
            pst.setInt(1, idvehiculo);
            rst = pst.executeQuery();

            while (rst.next()) {
                listaPersonaVehiculo.add(CenPersonaVehiculo.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarPersonasVehiculoByIdVehiculo: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarPersonasVehiculoByIdVehiculo:" + e);
            }
        }
        return listaPersonaVehiculo;
    }

    public List ListarPersonasVehiculoByIdPersona(Connection conex, int idpersona) throws SQLException {

        List listaPersonaVehiculo = new LinkedList();

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_PERSONA_VEHICULO WHERE PER_ID = ? ORDER BY PV_FECHAINICIO,TPER_ID ");
            pst.setInt(1, idpersona);
            rst = pst.executeQuery();

            while (rst.next()) {
                listaPersonaVehiculo.add(CenPersonaVehiculo.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarPersonasVehiculoByIdPersona: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarPersonasVehiculoByIdPersona:" + e);
            }
        }
        return listaPersonaVehiculo;
    }

    public List<HashMap<String, Object>> ListarHashPersonasVehiculoByIdVehiculo(Connection conex, int idvehiculo) throws SQLException {

        List<HashMap<String, Object>> lista = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT * FROM VW_PERSONA_VEHICULO WHERE VEH_ID = ? ");
            pst.setInt(1, idvehiculo);
            rst = pst.executeQuery();

            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, Object> hash = new HashMap<>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getObject(i + 1));
                }
                lista.add(hash);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarHashPersonasVehiculoByIdVehiculo: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarHashPersonasVehiculoByIdVehiculo:" + e);
            }
        }
        return lista;
    }

    public List<HashMap<String, Object>> ListarHashPersonasVehiculoActivasByIdVehiculo(Connection conex, int idvehiculo) throws SQLException {

        List<HashMap<String, Object>> lista = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT * FROM VW_PERSONA_VEHICULO WHERE EST_ID = 1 AND VEH_ID = ? ");
            pst.setInt(1, idvehiculo);
            rst = pst.executeQuery();

            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, Object> hash = new HashMap<>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                }
                lista.add(hash);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarHashPersonasVehiculoByIdVehiculo: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarHashPersonasVehiculoByIdVehiculo:" + e);
            }
        }
        return lista;
    }

    public boolean anularPersonaVehiculo(Connection conex, int id) throws SQLException, IOException {

        try {
            pst = conex.prepareStatement("UPDATE CEN_PERSONA_VEHICULO SET PV_FECHAFINAL = SYSDATE, EST_ID = 2 WHERE PV_ID = ? ");
            pst.setInt(1, id);
            pst.executeUpdate();
            
            return true;

        } catch (SQLException e) {
            System.err.println("Error en anularPersonaVehiculo: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de anularPersonaVehiculo:" + e);
            }
        }
        
        return false;
    }
    
}
