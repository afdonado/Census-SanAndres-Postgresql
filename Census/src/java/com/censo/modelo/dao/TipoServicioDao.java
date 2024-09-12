package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenTipoServicio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TipoServicioDao {

    public List ListarTiposServicio(Connection conex) throws SQLException {

        List listaTipoServicio = new LinkedList();

        String sql = "SELECT * FROM CEN_TIPOS_SERVICIO WHERE EST_ID = 1 ORDER BY TSER_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                listaTipoServicio.add(CenTipoServicio.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarTiposServicio: " + e);
        }
        return listaTipoServicio;
    }

    public CenTipoServicio ConsultarTipoServicioById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_TIPOS_SERVICIO WHERE EST_ID = 1 AND TSER_ID = ? ORDER BY TSER_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenTipoServicio.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarTipoServicioById: " + e);
        }
        return null;
    }

    public CenTipoServicio ConsultarTipoServicioByDescripcion(Connection conex, String descripcion) throws SQLException {

        String sql = "SELECT * FROM CEN_TIPOS_SERVICIO WHERE EST_ID = 1 AND TSER_DESCRIPCION = ?";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, descripcion);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenTipoServicio.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarTipoServicioByDescripcion: " + e);
        }
        return null;
    }

}
