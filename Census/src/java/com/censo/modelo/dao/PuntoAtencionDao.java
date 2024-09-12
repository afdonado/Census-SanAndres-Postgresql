package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenPuntoAtencion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PuntoAtencionDao {

    public List ListarPuntosAtencion(Connection conex) throws SQLException {

        List listaPuntoAtencion = new LinkedList();

        String sql = "SELECT * FROM CEN_PUNTOS_ATENCION WHERE EST_ID = 1 ORDER BY PUN_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    listaPuntoAtencion.add(CenPuntoAtencion.load(rst));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarPuntosAtencion: " + e);
        }
        return listaPuntoAtencion;
    }

    public CenPuntoAtencion ConsultarPuntoAtencionById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_PUNTOS_ATENCION WHERE PUN_ID = ? ORDER BY PUN_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenPuntoAtencion.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarPuntoAtencionById: " + e);
        }
        return null;
    }

}
