package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenMunicipio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MunicipioDao {

    public List ListarMunicipiosByIdDepto(Connection conex, int iddepto) throws SQLException {

        List listaMunicipio = new LinkedList();

        String sql = "SELECT * FROM CEN_MUNICIPIOS WHERE DEPT_ID = ? ORDER BY MUN_ID";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, iddepto);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    listaMunicipio.add(CenMunicipio.load(rst));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarMunicipiosByIdDepto: " + e);
        }
        return listaMunicipio;
    }

    public CenMunicipio ConsultarMunicipioById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_MUNICIPIOS WHERE MUN_ID = ? ORDER BY MUN_ID";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenMunicipio.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en consultarMunicipioById: " + e);
        }
        return null;
    }

}
