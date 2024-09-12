package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenModulo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModuloDao {

    public CenModulo ConsultarModuloById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_MODULOS WHERE EST_ID = 1 AND MOD_ID = ? ORDER BY MOD_ORDEN";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenModulo.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarModuloById: " + e);
        }
        return null;
    }

}
