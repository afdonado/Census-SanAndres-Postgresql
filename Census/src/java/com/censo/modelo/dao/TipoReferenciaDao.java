package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenTipoReferencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TipoReferenciaDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public java.util.List ListarTiposReferencia() throws SQLException {
        
        java.util.List listaTipoReferencia = new java.util.LinkedList();

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_TIPOS_REFERENCIA ORDER BY TREF_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaTipoReferencia.add(CenTipoReferencia.load(rst));
            }
        } catch (Exception e) {
            throw new SQLException("Error en ListarTiposReferencia: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarTiposReferencia:" + e);
            }
        }
        return listaTipoReferencia;
    }

    public CenTipoReferencia ConsultarTipoReferenciaById(int id) throws SQLException {

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_TIPOS_REFERENCIA WHERE TREF_ID = ? ORDER BY TREF_ID ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenTipoReferencia.load(rst);
            }
        } catch (Exception e) {
            throw new SQLException("Error en ConsultarTipoReferenciaById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarTipoReferenciaById:" + e);
            }
        }
        return null;
    }
    
}
