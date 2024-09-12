package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenTipoReferencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TipoReferenciaDao {

    public List ListarTiposReferencia(Connection conex) throws SQLException {

        List listaTipoReferencia = new LinkedList();

        String sql = "SELECT * FROM CEN_TIPOS_REFERENCIA ORDER BY TREF_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                listaTipoReferencia.add(CenTipoReferencia.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarTiposReferencia: " + e);
        }
        return listaTipoReferencia;
    }

    public CenTipoReferencia ConsultarTipoReferenciaById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_TIPOS_REFERENCIA WHERE TREF_ID = ? ORDER BY TREF_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenTipoReferencia.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarTipoReferenciaById: " + e);
        }
        return null;
    }

}
