package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenDepartamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DepartamentoDao {

    public List ListarDepartamentos(Connection conex) throws SQLException {

        List listaDepartamento = new LinkedList();
        String sql = "SELECT * FROM CEN_DEPARTAMENTOS ORDER BY DEPT_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                listaDepartamento.add(CenDepartamento.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarDepartamentos: " + e);
        }
        return listaDepartamento;
    }

    public CenDepartamento ConsultarDepartamentoById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_DEPARTAMENTOS WHERE DEPT_ID = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenDepartamento.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en consultarDepartamentoById: " + e);
        }
        return null;
    }

}
