package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenTipoServicio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TipoServicioDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public java.util.List ListarTiposServicio() throws SQLException {

        java.util.List listaTipoServicio = new java.util.LinkedList();

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_TIPOS_SERVICIO WHERE EST_ID = 1 ORDER BY TSER_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaTipoServicio.add(CenTipoServicio.load(rst));
            }
        } catch (Exception e) {
            throw new SQLException("Error en ListarTiposServicio: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarTiposServicio:" + e);
            }
        }
        return listaTipoServicio;
    }

    public CenTipoServicio ConsultarTipoServicioById(int id) throws SQLException {

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_TIPOS_SERVICIO WHERE EST_ID = 1 AND TSER_ID = ? ORDER BY TSER_ID ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenTipoServicio.load(rst);
            }
        } catch (Exception e) {
            throw new SQLException("Error en ConsultarTipoServicioById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarTipoServicioById:" + e);
            }
        }
        return null;
    }
    
}
