package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenColor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ColorDao {

    public List ListarColores(Connection conex) throws SQLException {

        List listaColor = new LinkedList();
        String sql = "SELECT * FROM CEN_COLORES ORDER BY COL_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                listaColor.add(CenColor.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarColores: " + e);
        }
        return listaColor;
    }

    public ArrayList<String> ListarNombresColores(Connection conex, String dato) throws SQLException {

        ArrayList<String> nombresColor = new ArrayList<>();
        String sql = "SELECT COL_NOMBRE FROM CEN_COLORES WHERE COL_NOMBRE LIKE ? AND ROWNUM <= 10 ORDER BY COL_NOMBRE";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, dato + "%");
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    nombresColor.add(rst.getString(1));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarNombresColores: " + e);
        }
        return nombresColor;
    }

    public CenColor ConsultarColorByNombre(Connection conex, String nombre) throws SQLException {

        String sql = "SELECT * FROM CEN_COLORES WHERE COL_NOMBRE = ? ORDER BY COL_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, nombre);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenColor.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarColorByNombre: " + e);
        }
        return null;
    }

    public CenColor ConsultarColorById(Connection conex, int id) throws SQLException {
        String sql = "SELECT * FROM CEN_COLORES WHERE COL_ID = ? ORDER BY COL_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenColor.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarColorById: " + e);
        }
        return null;
    }

}
