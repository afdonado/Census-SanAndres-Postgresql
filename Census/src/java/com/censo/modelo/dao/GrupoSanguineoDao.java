package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenGrupoSanguineo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GrupoSanguineoDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public java.util.List ListarGruposSanguineo() throws SQLException {

        java.util.List listaGrupoSanguineo = new java.util.LinkedList();

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_GRUPOS_SANGUINEO WHERE EST_ID = 1 ORDER BY GRS_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaGrupoSanguineo.add(CenGrupoSanguineo.load(rst));
            }
        } catch (Exception e) {
            throw new SQLException("Error en ListarGruposSanguineo: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarGruposSanguineo:" + e);
            }
        }
        return listaGrupoSanguineo;
    }

    public CenGrupoSanguineo ConsultarGrupoSanguineoById(int id) throws SQLException {

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_GRUPOS_SANGUINEO WHERE EST_ID = 1 AND GRS_ID = ? ORDER BY GRS_ID ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenGrupoSanguineo.load(rst);
            }
        } catch (Exception e) {
            throw new SQLException("Error en ConsultarGrupoSanguineoById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarGruposSanguineo:" + e);
            }
        }
        return null;
    }
    
}
