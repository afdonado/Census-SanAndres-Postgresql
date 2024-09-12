package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenTipoPersona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TipoPersonaDao {

    public List ListarTiposPersona(Connection conex) throws SQLException {

        List listaTipoPersona = new LinkedList();

        String sql = "SELECT * FROM CEN_TIPOS_PERSONA WHERE EST_ID = 1 ORDER BY TPER_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                listaTipoPersona.add(CenTipoPersona.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarTiposPersona: " + e);
        }
        return listaTipoPersona;
    }

    public CenTipoPersona ConsultarTipoPersonaById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_TIPOS_PERSONA WHERE EST_ID = 1 AND TPER_ID = ? ORDER BY TPER_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenTipoPersona.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarTipoPersonaById: " + e);
        }
        return null;
    }

}
