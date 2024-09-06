package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenClaseVehiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ClaseVehiculoDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public List ListarClasesVehiculo(Connection conex) throws SQLException {

        List listaClaseVehiculo = new LinkedList();

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_CLASES_VEHICULO WHERE EST_ID = 1 ORDER BY CLV_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaClaseVehiculo.add(CenClaseVehiculo.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarClasesVehiculo: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarClasesVehiculo:" + e);
            }
        }
        return listaClaseVehiculo;
    }

    public CenClaseVehiculo ConsultarClaseVehiculoById(Connection conex, int id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_CLASES_VEHICULO WHERE EST_ID = 1 AND CLV_ID = ? ORDER BY CLV_ID ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenClaseVehiculo.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarClaseVehiculoById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarClaseVehiculoById:" + e);
            }
        }
        return null;
    }
    
    public CenClaseVehiculo ConsultarClaseVehiculoByDescripcion(Connection conex, String descripcion) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_CLASES_VEHICULO WHERE EST_ID = 1 AND CLV_DESCRIPCION = ?");
            pst.setString(1, descripcion);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenClaseVehiculo.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarClaseVehiculoByDescripcion: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarClaseVehiculoByDescripcion:" + e);
            }
        }
        return null;
    }
    
}
