package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenLinea;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LineaDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public ArrayList<String> ListarNombresLineas(String linea, long idmarca) throws SQLException {

        ArrayList<String> nombresLinea = new ArrayList<>();

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT LIN_NOMBRE FROM CEN_LINEAS WHERE LIN_NOMBRE LIKE ? AND MAR_ID = ? AND ROWNUM <= 20 ORDER BY LIN_NOMBRE");
            pst.setString(1, linea + "%");
            pst.setLong(2, idmarca);
            rst = pst.executeQuery();

            while (rst.next()) {
                nombresLinea.add(rst.getString(1));
            }
        } catch (Exception e) {
            throw new SQLException("Error en ListarNombresLineas: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarNombresLineas:" + e);
            }
        }
        return nombresLinea;
    }

    public CenLinea ConsultarLineaByNombreIdMarca(String nombre, long idmarca) throws SQLException {

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_LINEAS WHERE LIN_NOMBRE = ? AND MAR_ID = ? ORDER BY LIN_ID ");
            pst.setString(1, nombre);
            pst.setLong(2, idmarca);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenLinea.load(rst);
            }
        } catch (Exception e) {
            throw new SQLException("Error en ConsultarLineaByNombreIdMarca: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarLineaByNombreIdMarca:" + e);
            }
        }
        return null;
    }

    public CenLinea ConsultarLineaById(long id) throws SQLException {

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_LINEAS WHERE LIN_ID = ? ORDER BY LIN_ID ");
            pst.setLong(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenLinea.load(rst);
            }
        } catch (Exception e) {
            throw new SQLException("Error en ConsultarLineaById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarLineaById:" + e);
            }
        }
        return null;
    }
    
}
