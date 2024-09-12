package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenParametro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ParametroDao {

    public List ListarParametros(Connection conex) throws SQLException {

        List listaParametro = new LinkedList();

        String sql = "SELECT * FROM CEN_PARAMETROS WHERE EST_ID = 1 ORDER BY PAR_ID ";

        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                listaParametro.add(CenParametro.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarParametros: " + e);
        }
        return listaParametro;
    }

    public CenParametro ConsultarParametroById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_PARAMETROS WHERE EST_ID = 1 AND PAR_ID = ? ORDER BY PAR_ID";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenParametro.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarParametroById: " + e);
        }
        return null;
    }

}
