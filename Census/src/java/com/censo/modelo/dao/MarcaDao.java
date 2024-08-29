package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenMarca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MarcaDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public ArrayList<String> ListarNombresMarcas(Connection conex, String dato) throws SQLException {

        ArrayList<String> nombresMarca = new ArrayList<>();

        try {
            pst = conex.prepareStatement("SELECT MAR_NOMBRE FROM CEN_MARCAS WHERE MAR_NOMBRE LIKE ? AND ROWNUM <= 10 ORDER BY MAR_NOMBRE");
            pst.setString(1, dato + "%");
            rst = pst.executeQuery();

            while (rst.next()) {
                nombresMarca.add(rst.getString(1));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarNombresMarcas: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarNombresMarcas:" + e);
            }
        }
        return nombresMarca;
    }

    public CenMarca ConsultarMarcaByNombre(Connection conex, String nombre) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_MARCAS WHERE MAR_NOMBRE = ? ORDER BY MAR_ID ");
            pst.setString(1, nombre);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenMarca.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarMarcaByNombre: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarMarcaByNombre:" + e);
            }
        }
        return null;
    }

    public CenMarca ConsultarMarcaById(Connection conex, long id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_MARCAS WHERE MAR_ID = ? ORDER BY MAR_ID ");
            pst.setLong(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenMarca.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarMarcaById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarMarcaById:" + e);
            }
        }
        return null;
    }
    
}
