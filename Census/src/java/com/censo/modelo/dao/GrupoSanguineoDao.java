package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenGrupoSanguineo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GrupoSanguineoDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public List ListarGruposSanguineo(Connection conex) throws SQLException {

        List listaGrupoSanguineo = new LinkedList();

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_GRUPOS_SANGUINEO WHERE EST_ID = 1 ORDER BY GRS_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaGrupoSanguineo.add(CenGrupoSanguineo.load(rst));
            }
        } catch (SQLException e) {
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

    public CenGrupoSanguineo ConsultarGrupoSanguineoById(Connection conex, int id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_GRUPOS_SANGUINEO WHERE EST_ID = 1 AND GRS_ID = ? ORDER BY GRS_ID ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenGrupoSanguineo.load(rst);
            }
        } catch (SQLException e) {
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
