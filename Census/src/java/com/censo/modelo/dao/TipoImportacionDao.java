package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenTipoImportacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TipoImportacionDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public List ListarTiposImportacion(Connection conex) throws SQLException {

        List listaTipoImportacion = new LinkedList();

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_TIPOS_IMPORTACION WHERE EST_ID = 1 ORDER BY TIMP_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaTipoImportacion.add(CenTipoImportacion.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarTiposImportacion: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarTiposImportacion:" + e);
            }
        }
        return listaTipoImportacion;
    }

    public CenTipoImportacion ConsultarTipoImportacionById(Connection conex, int id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_TIPOS_IMPORTACION WHERE EST_ID = 1 AND TIMP_ID = ? ORDER BY TIMP_ID ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenTipoImportacion.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarTipoImportacionById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarTipoImportacionById:" + e);
            }
        }
        return null;
    }
    
}
