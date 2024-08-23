package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenClaseVehiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClaseVehiculoDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public java.util.List ListarClasesVehiculo() throws SQLException {

        java.util.List listaClaseVehiculo = new java.util.LinkedList();

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_CLASES_VEHICULO WHERE EST_ID = 1 ORDER BY CLV_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaClaseVehiculo.add(CenClaseVehiculo.load(rst));
            }
        } catch (Exception e) {
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

    public CenClaseVehiculo ConsultarClaseVehiculoById(int id) throws SQLException {

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_CLASES_VEHICULO WHERE EST_ID = 1 AND CLV_ID = ? ORDER BY CLV_ID ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenClaseVehiculo.load(rst);
            }
        } catch (Exception e) {
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
    
}
