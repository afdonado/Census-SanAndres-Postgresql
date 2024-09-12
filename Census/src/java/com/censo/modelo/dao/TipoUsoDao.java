package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenTipoUso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TipoUsoDao {

    public List ListarTiposUso(Connection conex) throws SQLException {

        List listaTipoUso = new LinkedList();

        String sql = "SELECT * FROM CEN_TIPOS_USO WHERE EST_ID = 1 ORDER BY TUSO_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                listaTipoUso.add(CenTipoUso.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarTiposUso: " + e);
        }
        return listaTipoUso;
    }

    public CenTipoUso ConsultarTipoUsoById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_TIPOS_USO WHERE EST_ID = 1 AND TUSO_ID = ? ORDER BY TUSO_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenTipoUso.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarTipoUsoById: " + e);
        }
        return null;
    }

}
