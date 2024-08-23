package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenColor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ColorDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;

    public java.util.List ListarColores() throws SQLException {
        
        java.util.List listaColor = new java.util.LinkedList();

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_COLORES ORDER BY COL_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaColor.add(CenColor.load(rst));
            }
        } catch (Exception e) {
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

    public ArrayList<String> ListarNombresColores(String dato) throws SQLException {

        ArrayList<String> nombresColor = new ArrayList<>();

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT COL_NOMBRE FROM CEN_COLORES WHERE COL_NOMBRE LIKE ? AND ROWNUM <= 10 ORDER BY COL_NOMBRE");
            pst.setString(1, dato + "%");
            rst = pst.executeQuery();

            while (rst.next()) {
                nombresColor.add(rst.getString(1));
            }
        } catch (Exception e) {
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

    public CenColor ConsultarColorByNombre(String nombre) throws SQLException {

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_COLORES WHERE COL_NOMBRE = ? ORDER BY COL_ID ");
            pst.setString(1, nombre);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenColor.load(rst);
            }
        } catch (Exception e) {
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

    public CenColor ConsultarColorById(long id) throws SQLException {

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_COLORES WHERE COL_ID = ? ORDER BY COL_ID ");
            pst.setLong(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenColor.load(rst);
            }
        } catch (Exception e) {
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
