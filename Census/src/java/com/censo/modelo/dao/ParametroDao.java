package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenParametro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParametroDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public java.util.List ListarParametros() throws SQLException {

        java.util.List listaParametro = new java.util.LinkedList();

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_PARAMETROS WHERE EST_ID = 1 ORDER BY PAR_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaParametro.add(CenParametro.load(rst));
            }
        } catch (Exception e) {
            throw new SQLException("Error en ListarParametros: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarParametros:" + e);
            }
        }
        return listaParametro;
    }

    public CenParametro ConsultarParametroById(int id) throws SQLException {

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_PARAMETROS WHERE EST_ID = 1 AND PAR_ID = ? ORDER BY PAR_ID ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenParametro.load(rst);
            }
        } catch (Exception e) {
            throw new SQLException("Error en ConsultarParametroById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarParametroById:" + e);
            }
        }
        return null;
    }
    
}
