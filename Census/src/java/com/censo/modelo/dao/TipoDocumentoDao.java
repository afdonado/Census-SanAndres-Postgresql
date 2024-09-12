package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenTipoDocumento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TipoDocumentoDao {

    public List ListarTiposDocumento(Connection conex) throws SQLException {

        List listaTipoDocumento = new LinkedList();

        String sql = "SELECT * FROM CEN_TIPOS_DOCUMENTO WHERE EST_ID = 1 ORDER BY TDOC_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                listaTipoDocumento.add(CenTipoDocumento.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarTiposDocumento: " + e);
        }
        return listaTipoDocumento;
    }

    public CenTipoDocumento ConsultarTipoDocumentoById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_TIPOS_DOCUMENTO WHERE EST_ID = 1 AND TDOC_ID = ? ORDER BY TDOC_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenTipoDocumento.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarTipoDocumentoById: " + e);
        }
        return null;
    }

}
