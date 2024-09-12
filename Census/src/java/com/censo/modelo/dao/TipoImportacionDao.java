package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenTipoImportacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TipoImportacionDao {

    public List ListarTiposImportacion(Connection conex) throws SQLException {

        List listaTipoImportacion = new LinkedList();

        String sql = "SELECT * FROM CEN_TIPOS_IMPORTACION WHERE EST_ID = 1 ORDER BY TIMP_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                listaTipoImportacion.add(CenTipoImportacion.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarTiposImportacion: " + e);
        }
        return listaTipoImportacion;
    }

    public CenTipoImportacion ConsultarTipoImportacionById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_TIPOS_IMPORTACION WHERE EST_ID = 1 AND TIMP_ID = ? ORDER BY TIMP_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenTipoImportacion.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarTipoImportacionById: " + e);
        }
        return null;
    }

}
