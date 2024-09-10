package com.censo.modelo.dao;

import com.censo.modelo.persistencia.Persona2017;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Persona2017Dao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public Persona2017 ConsultarPersona2017(Connection conex, int tipodocumento, String documento) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM PERSONAS_2017 WHERE TIPO_DOCUMENTO_ID = ? AND DOCUMENTO = ? ");
            pst.setInt(1, tipodocumento);
            pst.setString(2, documento);
            rst = pst.executeQuery();

            while (rst.next()) {
                return Persona2017.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarPersona2017: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarPersona2017:" + e);
            }
        }
        return null;
    }
    
}
