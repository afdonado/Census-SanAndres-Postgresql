package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenTipoReferencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TipoReferenciaDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public List ListarTiposReferencia(Connection conex) throws SQLException {
        
        List listaTipoReferencia = new LinkedList();

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_TIPOS_REFERENCIA ORDER BY TREF_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaTipoReferencia.add(CenTipoReferencia.load(rst));
            }
        } catch (SQLException e) {
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

    public CenTipoReferencia ConsultarTipoReferenciaById(Connection conex, int id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_TIPOS_REFERENCIA WHERE TREF_ID = ? ORDER BY TREF_ID ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenTipoReferencia.load(rst);
            }
        } catch (SQLException e) {
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
