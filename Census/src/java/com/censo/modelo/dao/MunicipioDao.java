package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenMunicipio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MunicipioDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public List ListarMunicipiosByIdDepto(Connection conex, int iddepto) throws SQLException {

        List listaMunicipio = new LinkedList();

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_MUNICIPIOS WHERE DEPT_ID = ? ORDER BY MUN_ID ");
            pst.setInt(1, iddepto);
            rst = pst.executeQuery();

            while (rst.next()) {
                listaMunicipio.add(CenMunicipio.load(rst));
            }
        } catch (SQLException e) {
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

    public CenMunicipio ConsultarMunicipioById(Connection conex, int id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_MUNICIPIOS WHERE MUN_ID = ? ORDER BY MUN_ID ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenMunicipio.load(rst);
            }
        } catch (SQLException e) {
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
