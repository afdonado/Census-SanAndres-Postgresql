package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenColor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ColorDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;

    public List ListarColores(Connection conex) throws SQLException {
        
        List listaColor = new LinkedList();

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_COLORES ORDER BY COL_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaColor.add(CenColor.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarColores: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarColores:" + e);
            }
        }
        return listaColor;
    }

    public ArrayList<String> ListarNombresColores(Connection conex, String dato) throws SQLException {

        ArrayList<String> nombresColor = new ArrayList<>();

        try {
            pst = conex.prepareStatement("SELECT COL_NOMBRE FROM CEN_COLORES WHERE COL_NOMBRE LIKE ? AND ROWNUM <= 10 ORDER BY COL_NOMBRE");
            pst.setString(1, dato + "%");
            rst = pst.executeQuery();

            while (rst.next()) {
                nombresColor.add(rst.getString(1));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarNombresColores: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarNombresColores:" + e);
            }
        }
        return nombresColor;
    }

    public CenColor ConsultarColorByNombre(Connection conex, String nombre) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_COLORES WHERE COL_NOMBRE = ? ORDER BY COL_ID ");
            pst.setString(1, nombre);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenColor.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarColorByNombre: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarColorByNombre:" + e);
            }
        }
        return null;
    }

    public CenColor ConsultarColorById(Connection conex, long id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_COLORES WHERE COL_ID = ? ORDER BY COL_ID ");
            pst.setLong(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenColor.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarColorById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarColorById:" + e);
            }
        }
        return null;
    }
    
}
