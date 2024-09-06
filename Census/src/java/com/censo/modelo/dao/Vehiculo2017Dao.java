package com.censo.modelo.dao;

import com.censo.modelo.persistencia.Vehiculo2017;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

public class Vehiculo2017Dao extends Conexion {

    private ResultSet rst = null;
    private PreparedStatement pst = null;

    public Vehiculo2017 ConsultarVehiculo2017ByPlaca(Connection conex, String placa) throws SQLException {

        try {
                pst = conex.prepareStatement("SELECT * FROM VEHICULOS_2017 WHERE PLACA = ? ");
            pst.setString(1, placa);
            rst = pst.executeQuery();

            while (rst.next()) {
                return Vehiculo2017.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarVehiculo2017ByPlaca: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarVehiculo2017ByPlaca:" + e);
            }
        }
        return null;
    }
    
    public Vehiculo2017 ConsultarVehiculo2017ByMotor(Connection conex, String motor) throws SQLException {

        try {
                pst = conex.prepareStatement("SELECT * FROM VEHICULOS_2017 WHERE MOTOR = ? ");
            pst.setString(1, motor);
            rst = pst.executeQuery();

            while (rst.next()) {
                return Vehiculo2017.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarVehiculo2017ByMotor: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarVehiculo2017ByMotor:" + e);
            }
        }
        return null;
    }
    
    public Vehiculo2017 ConsultarVehiculo2017ByChasis(Connection conex, String chasis) throws SQLException {

        try {
                pst = conex.prepareStatement("SELECT * FROM VEHICULOS_2017 WHERE CHASIS = ? ");
            pst.setString(1, chasis);
            rst = pst.executeQuery();

            while (rst.next()) {
                return Vehiculo2017.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarVehiculo2017ByChasis: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarVehiculo2017ByChasis:" + e);
            }
        }
        return null;
    }
    
    public Vehiculo2017 ConsultarVehiculo2017BySerie(Connection conex, String serie) throws SQLException {

        try {
                pst = conex.prepareStatement("SELECT * FROM VEHICULOS_2017 WHERE SERIE = ? ");
            pst.setString(1, serie);
            rst = pst.executeQuery();

            while (rst.next()) {
                return Vehiculo2017.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarVehiculo2017BySerie: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarVehiculo2017BySerie:" + e);
            }
        }
        return null;
    }

    public HashMap<String, Object> ConsultarDatosVehiculo2017ByPlaca(Connection conex, long id) throws SQLException {

        HashMap<String, Object> datos = new HashMap<>();

        try {
            pst = conex.prepareStatement("SELECT * FROM VEHICULOS_2017 WHERE PLACA = ? ");
            pst.setLong(1, id);
            rst = pst.executeQuery();

            if (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    datos.put(rsmd.getColumnName(i + 1), rst.getObject(i + 1));
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarDatosVehiculo2017ByPlaca: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarDatosVehiculo2017ByPlaca:" + e);
            }
        }
        return datos;
    }

}
