package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenPersonaVehiculo;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

public class PersonaVehiculoDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public long adicionarPersonaVehiculo(CenPersonaVehiculo cenpersonasvehiculo) {

        try(Connection conex = conectar()) {
            pst = conex.prepareStatement("INSERT INTO CEN_PERSONA_VEHICULO (TPER_ID,PER_ID,VEH_ID,"
                    + "PV_FECHAINICIO,PV_FECHAFINAL,USU_ID,EST_ID,PV_FECHAPROCESO) VALUES (?,?,?,sysdate,null,?,?,sysdate)", new String[]{"PV_ID"});
            pst.setInt(1, cenpersonasvehiculo.getTper_id());
            pst.setLong(2, cenpersonasvehiculo.getPer_id());
            pst.setLong(3, cenpersonasvehiculo.getVeh_id());
            pst.setLong(4, cenpersonasvehiculo.getUsu_id());
            pst.setInt(5, cenpersonasvehiculo.getEstado());
            pst.executeUpdate();
            rst = pst.getGeneratedKeys();
            if (rst != null) {
                if (rst.next()) {
                    return rst.getLong(1);
                }
            }
        } catch (Exception e) {
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

    public java.util.List ListarPersonasVehiculoByIdVehiculo(long idvehiculo) throws SQLException {

        java.util.List listaPersonaVehiculo = new java.util.LinkedList();

        try(Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_PERSONA_VEHICULO WHERE VEH_ID = ? ORDER BY PV_FECHAINICIO,TPER_ID  ");
            pst.setLong(1, idvehiculo);
            rst = pst.executeQuery();

            while (rst.next()) {
                listaPersonaVehiculo.add(CenPersonaVehiculo.load(rst));
            }
        } catch (Exception e) {
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

    public java.util.List ListarPersonasVehiculoByIdPersona(long idpersona) throws SQLException {

        java.util.List listaPersonaVehiculo = new java.util.LinkedList();

        try(Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_PERSONA_VEHICULO WHERE PER_ID = ? ORDER BY PV_FECHAINICIO,TPER_ID ");
            pst.setLong(1, idpersona);
            rst = pst.executeQuery();

            while (rst.next()) {
                listaPersonaVehiculo.add(CenPersonaVehiculo.load(rst));
            }
        } catch (Exception e) {
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

    public java.util.List ListarHashPersonasVehiculoByIdVehiculo(long idvehiculo) throws SQLException {

        java.util.List<HashMap> listaPersonasVehiculo = new java.util.LinkedList<HashMap>();

        try(Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM VW_PERSONA_VEHICULO WHERE VEH_ID = ? ");
            pst.setLong(1, idvehiculo);
            rst = pst.executeQuery();

            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, String> hash = new HashMap<String, String>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                }
                listaPersonasVehiculo.add(hash);
            }
        } catch (Exception e) {
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
        return listaPersonasVehiculo;
    }

    public java.util.List ListarHashPersonasVehiculoActivasByIdVehiculo(long idvehiculo) throws SQLException {

        java.util.List<HashMap> listaPersonasVehiculo = new java.util.LinkedList<HashMap>();

        try(Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM VW_PERSONA_VEHICULO WHERE EST_ID = 1 AND VEH_ID = ? ");
            pst.setLong(1, idvehiculo);
            rst = pst.executeQuery();

            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, String> hash = new HashMap<String, String>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                }
                listaPersonasVehiculo.add(hash);
            }
        } catch (Exception e) {
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
        return listaPersonasVehiculo;
    }

    public void anularPersonaVehiculo(long id) throws SQLException, IOException {

        try(Connection conex = conectar()) {
            pst = conex.prepareStatement("UPDATE CEN_PERSONA_VEHICULO SET PV_FECHAFINAL = SYSDATE, EST_ID = 2 WHERE PV_ID = ? ");
            pst.setLong(1, id);
            pst.executeUpdate();

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
    }
    
}
