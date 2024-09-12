package com.censo.modelo.dao;

import com.censo.modelo.persistencia.Persona2017;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Persona2017Dao {

    public Persona2017 ConsultarPersona2017(Connection conex, int tipodocumento, String documento) throws SQLException {

        String sql = "SELECT * FROM PERSONAS_2017 WHERE TIPO_DOCUMENTO_ID = ? AND DOCUMENTO = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, tipodocumento);
            pst.setString(2, documento);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return Persona2017.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarPersona2017: " + e);
        }
        return null;
    }

}
