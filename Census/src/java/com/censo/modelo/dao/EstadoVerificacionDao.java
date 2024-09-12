package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenEstadoVerificacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class EstadoVerificacionDao {

    public List ListarEstadosVerificacion(Connection conex) throws SQLException {

        List listaEstado = new LinkedList();

        String sql = "SELECT * FROM CEN_ESTADOS_VERIFICACIONES ORDER BY EVER_ID";

        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                listaEstado.add(CenEstadoVerificacion.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarEstadosVerificacion: " + e);
        }
        return listaEstado;
    }

}
