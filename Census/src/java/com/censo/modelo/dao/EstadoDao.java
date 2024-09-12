package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenEstado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class EstadoDao {

    public List ListarEstados(Connection conex) throws SQLException {

        List listaEstado = new LinkedList();

        String sql = "SELECT * FROM CEN_ESTADOS ORDER BY EST_ID";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {

            while (rst.next()) {
                listaEstado.add(CenEstado.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarEstados: " + e);
        }
        return listaEstado;
    }

    public CenEstado ConsultarEstadoById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_ESTADOS WHERE EST_ID = ? ORDER BY EST_ID";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenEstado.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarEstadoById: " + e);
        }
        return null;
    }

}
