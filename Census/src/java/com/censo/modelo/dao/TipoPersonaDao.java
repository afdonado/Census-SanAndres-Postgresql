package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenTipoPersona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TipoPersonaDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public java.util.List ListarTiposPersona() throws SQLException {

        java.util.List listaTipoPersona = new java.util.LinkedList();

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_TIPOS_PERSONA WHERE EST_ID = 1 ORDER BY TPER_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaTipoPersona.add(CenTipoPersona.load(rst));
            }
        } catch (Exception e) {
            throw new SQLException("Error en ListarTiposPersona: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarTiposPersona:" + e);
            }
        }
        return listaTipoPersona;
    }

    public CenTipoPersona ConsultarTipoPersonaById(int id) throws SQLException {

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_TIPOS_PERSONA WHERE EST_ID = 1 AND TPER_ID = ? ORDER BY TPER_ID ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenTipoPersona.load(rst);
            }
        } catch (Exception e) {
            throw new SQLException("Error en ConsultarTipoPersonaById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarTipoPersonaById:" + e);
            }
        }
        return null;
    }
    
}
