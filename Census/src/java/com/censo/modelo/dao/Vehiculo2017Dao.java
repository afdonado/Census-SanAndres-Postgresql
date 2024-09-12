package com.censo.modelo.dao;

import com.censo.modelo.persistencia.Vehiculo2017;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

public class Vehiculo2017Dao {

    public Vehiculo2017 ConsultarVehiculo2017ByPlaca(Connection conex, String placa) throws SQLException {

        String sql = "SELECT * FROM VEHICULOS_2017 WHERE PLACA = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, placa);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return Vehiculo2017.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarVehiculo2017ByPlaca: " + e);
        }
        return null;
    }

    public Vehiculo2017 ConsultarVehiculo2017ByMotor(Connection conex, String motor) throws SQLException {

        String sql = "SELECT * FROM VEHICULOS_2017 WHERE MOTOR = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, motor);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return Vehiculo2017.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarVehiculo2017ByMotor: " + e);
        }
        return null;
    }

    public Vehiculo2017 ConsultarVehiculo2017ByChasis(Connection conex, String chasis) throws SQLException {

        String sql = "SELECT * FROM VEHICULOS_2017 WHERE CHASIS = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, chasis);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return Vehiculo2017.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarVehiculo2017ByChasis: " + e);
        }
        return null;
    }

    public Vehiculo2017 ConsultarVehiculo2017BySerie(Connection conex, String serie) throws SQLException {

        String sql = "SELECT * FROM VEHICULOS_2017 WHERE SERIE = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, serie);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return Vehiculo2017.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarVehiculo2017BySerie: " + e);
        }
        return null;
    }

    public HashMap<String, Object> ConsultarDatosVehiculo2017ByPlaca(Connection conex, String placa) throws SQLException {

        HashMap<String, Object> datos = new HashMap<>();

        String sql = "SELECT * FROM VEHICULOS_2017 WHERE PLACA = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, placa);
            try (ResultSet rst = pst.executeQuery()) {
                if (rst.next()) {
                    ResultSetMetaData rsmd = rst.getMetaData();
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        datos.put(rsmd.getColumnName(i + 1), rst.getObject(i + 1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarDatosVehiculo2017ByPlaca: " + e);
        }
        return datos;
    }

}
