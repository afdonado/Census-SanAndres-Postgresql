package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenGenero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GeneroDao {

    public List ListarGeneros(Connection conex) throws SQLException {

        List listaGenero = new LinkedList();

        String sql = "SELECT * FROM CEN_GENEROS WHERE EST_ID = 1 ORDER BY GEN_ID";

        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                listaGenero.add(CenGenero.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarGeneros: " + e);
        }
        return listaGenero;
    }

    public CenGenero ConsultarGeneroById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_GENEROS WHERE EST_ID = 1 AND GEN_ID = ? ORDER BY GEN_ID";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenGenero.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarGeneroById: " + e);
        }
        return null;
    }

}
