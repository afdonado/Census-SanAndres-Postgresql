package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenLinea;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LineaDao {

    public ArrayList<String> ListarNombresLineas(Connection conex, String linea, int idmarca) throws SQLException {

        ArrayList<String> nombresLinea = new ArrayList<>();

        String sql = "SELECT LIN_NOMBRE FROM CEN_LINEAS WHERE LIN_NOMBRE LIKE ? AND MAR_ID = ? AND ROWNUM <= 20 ORDER BY LIN_NOMBRE";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, linea + "%");
            pst.setInt(2, idmarca);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    nombresLinea.add(rst.getString(1));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarNombresLineas: " + e);
        }
        return nombresLinea;
    }

    public CenLinea ConsultarLineaByNombreIdMarca(Connection conex, String nombre, int idmarca) throws SQLException {

        String sql = "SELECT * FROM CEN_LINEAS WHERE LIN_NOMBRE = ? AND MAR_ID = ? ORDER BY LIN_ID";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, nombre);
            pst.setInt(2, idmarca);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenLinea.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarLineaByNombreIdMarca: " + e);
        }
        return null;
    }

    public CenLinea ConsultarLineaById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_LINEAS WHERE LIN_ID = ? ORDER BY LIN_ID";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenLinea.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarLineaById: " + e);
        }
        return null;
    }

}
