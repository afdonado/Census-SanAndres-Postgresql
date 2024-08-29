package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenTipoUso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TipoUsoDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public List ListarTiposUso(Connection conex) throws SQLException {

        List listaTipoUso = new LinkedList();

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_TIPOS_USO WHERE EST_ID = 1 ORDER BY TUSO_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaTipoUso.add(CenTipoUso.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarTiposUso: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarTiposUso:" + e);
            }
        }
        return listaTipoUso;
    }

    public CenTipoUso ConsultarTipoUsoById(Connection conex, int id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_TIPOS_USO WHERE EST_ID = 1 AND TUSO_ID = ? ORDER BY TUSO_ID ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenTipoUso.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarTipoUsoById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarTipoUsoById:" + e);
            }
        }
        return null;
    }
    
}
