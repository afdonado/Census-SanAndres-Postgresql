package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenPais;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PaisDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public List ListarPaises(Connection conex) throws SQLException {

        List listaPais = new LinkedList();

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_PAISES ORDER BY PAI_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaPais.add(CenPais.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarPaises: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarPaises:" + e);
            }
        }
        return listaPais;
    }

    public CenPais ConsultarPaisById(Connection conex, int id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_PAISES WHERE PAI_ID = ? ORDER BY PAI_ID ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenPais.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarPaisById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarPaisById:" + e);
            }
        }
        return null;
    }
    
}
