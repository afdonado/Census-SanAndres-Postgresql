package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenMunicipio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MunicipioDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public java.util.List ListarMunicipiosByIdDepto(long iddepto) throws SQLException {

        java.util.List listaMunicipio = new java.util.LinkedList();

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_MUNICIPIOS WHERE DEPT_ID = ? ORDER BY MUN_ID ");
            pst.setLong(1, iddepto);
            rst = pst.executeQuery();

            while (rst.next()) {
                listaMunicipio.add(CenMunicipio.load(rst));
            }
        } catch (Exception e) {
            throw new SQLException("Error en ListarMunicipiosByIdDepto: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarMunicipiosByIdDepto:" + e);
            }
        }
        return listaMunicipio;
    }

    public CenMunicipio ConsultarMunicipioById(long id) throws SQLException {

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_MUNICIPIOS WHERE MUN_ID = ? ORDER BY MUN_ID ");
            pst.setLong(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenMunicipio.load(rst);
            }
        } catch (Exception e) {
            throw new SQLException("Error en consultarMunicipioById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de consultarMunicipioById:" + e);
            }
        }
        return null;
    }
    
}
