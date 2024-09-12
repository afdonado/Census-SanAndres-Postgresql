package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenClaseVehiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ClaseVehiculoDao {

    public List ListarClasesVehiculo(Connection conex) throws SQLException {

        List listaClaseVehiculo = new LinkedList();

        String sql = "SELECT * FROM CEN_CLASES_VEHICULO WHERE EST_ID = 1 ORDER BY CLV_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                listaClaseVehiculo.add(CenClaseVehiculo.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarClasesVehiculo: " + e);
        }
        return listaClaseVehiculo;
    }

    public CenClaseVehiculo ConsultarClaseVehiculoById(Connection conex, int id) throws SQLException {
        String sql = "SELECT * FROM CEN_CLASES_VEHICULO WHERE EST_ID = 1 AND CLV_ID = ? ORDER BY CLV_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenClaseVehiculo.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarClaseVehiculoById: " + e);
        }
        return null;
    }

    public CenClaseVehiculo ConsultarClaseVehiculoByDescripcion(Connection conex, String descripcion) throws SQLException {

        String sql = "SELECT * FROM CEN_CLASES_VEHICULO WHERE EST_ID = 1 AND CLV_DESCRIPCION = ?";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, descripcion);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenClaseVehiculo.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarClaseVehiculoByDescripcion: " + e);
        }
        return null;
    }

}
