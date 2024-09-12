package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenPais;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PaisDao {

    public List ListarPaises(Connection conex) throws SQLException {

        List listaPais = new LinkedList();

        String sql = "SELECT * FROM CEN_PAISES ORDER BY PAI_ID";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                listaPais.add(CenPais.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarPaises: " + e);
        }
        return listaPais;
    }

    public CenPais ConsultarPaisById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_PAISES WHERE PAI_ID = ? ORDER BY PAI_ID";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenPais.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarPaisById: " + e);
        }
        return null;
    }

}
