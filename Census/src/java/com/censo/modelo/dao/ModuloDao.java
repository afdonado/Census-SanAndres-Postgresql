package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenModulo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModuloDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public CenModulo ConsultarModuloById(int id) throws SQLException {

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_MODULOS WHERE EST_ID = 1 AND MOD_ID = ? ORDER BY MOD_ORDEN ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenModulo.load(rst);
            }
        } catch (Exception e) {
            throw new SQLException("Error en ConsultarModuloById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.err.println("Error en cierres de ConsultarModuloById: " + e);
            }
        }
        return null;
    }

}
