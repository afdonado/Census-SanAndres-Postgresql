package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenMarca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MarcaDao {

    public ArrayList<String> ListarNombresMarcas(Connection conex, String dato) throws SQLException {

        ArrayList<String> nombresMarca = new ArrayList<>();

        String sql = "SELECT MAR_NOMBRE FROM CEN_MARCAS WHERE MAR_NOMBRE LIKE ? AND ROWNUM <= 10 ORDER BY MAR_NOMBRE";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, dato + "%");
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    nombresMarca.add(rst.getString(1));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarNombresMarcas: " + e);
        }
        return nombresMarca;
    }

    public CenMarca ConsultarMarcaByNombre(Connection conex, String nombre) throws SQLException {

        String sql = "SELECT * FROM CEN_MARCAS WHERE MAR_NOMBRE = ? ORDER BY MAR_ID";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, nombre);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenMarca.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarMarcaByNombre: " + e);
        }
        return null;
    }

    public CenMarca ConsultarMarcaById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_MARCAS WHERE MAR_ID = ? ORDER BY MAR_ID";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenMarca.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarMarcaById: " + e);
        }
        return null;
    }

}
