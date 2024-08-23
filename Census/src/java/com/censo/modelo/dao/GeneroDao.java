package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenGenero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GeneroDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public java.util.List ListarGeneros() throws SQLException {
        
        java.util.List listaGenero = new java.util.LinkedList();

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_GENEROS WHERE EST_ID = 1 ORDER BY GEN_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaGenero.add(CenGenero.load(rst));
            }
        } catch (Exception e) {
            throw new SQLException("Error en ListarGeneros: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarGeneros:" + e);
            }
        }
        return listaGenero;
    }

    public CenGenero ConsultarGeneroById(int id) throws SQLException {

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_GENEROS WHERE EST_ID = 1 AND GEN_ID = ? ORDER BY GEN_ID ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenGenero.load(rst);
            }
        } catch (Exception e) {
            throw new SQLException("Error en ConsultarGeneroById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarGeneroById:" + e);
            }
        }
        return null;
    }
    
}
