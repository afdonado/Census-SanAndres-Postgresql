package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenGrupoSanguineo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GrupoSanguineoDao {

    public List ListarGruposSanguineo(Connection conex) throws SQLException {

        List listaGrupoSanguineo = new LinkedList();

        String sql = "SELECT * FROM CEN_GRUPOS_SANGUINEO WHERE EST_ID = 1 ORDER BY GRS_ID";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                listaGrupoSanguineo.add(CenGrupoSanguineo.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarGruposSanguineo: " + e);
        }
        return listaGrupoSanguineo;
    }

    public CenGrupoSanguineo ConsultarGrupoSanguineoById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_GRUPOS_SANGUINEO WHERE EST_ID = 1 AND GRS_ID = ? ORDER BY GRS_ID";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenGrupoSanguineo.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarGrupoSanguineoById: " + e);
        }
        return null;
    }

}
