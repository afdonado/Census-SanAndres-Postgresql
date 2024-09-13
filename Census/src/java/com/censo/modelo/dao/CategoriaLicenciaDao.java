package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenCategoriaLicencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CategoriaLicenciaDao {

    public List ListarCategoriasLicencia(Connection conex) throws SQLException {

        List listaCategoriasLicencia = new LinkedList();

        String sql = "SELECT * FROM CEN_CATEGORIAS_LICENCIA WHERE EST_ID = 1 ORDER BY CTL_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                listaCategoriasLicencia.add(CenCategoriaLicencia.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarCategoriasLicencia: " + e);
        }
        return listaCategoriasLicencia;
    }

    public CenCategoriaLicencia ConsultarCategoriaLicenciaById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_CATEGORIAS_LICENCIA WHERE EST_ID = 1 AND CTL_ID = ? ORDER BY CTL_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenCategoriaLicencia.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarCategoriaLicenciaById: " + e);
        }
        return null;
    }
    
    public CenCategoriaLicencia ConsultarCategoriaLicenciaByDescripcion(Connection conex, String nombre) throws SQLException {

        String sql = "SELECT * FROM CEN_CATEGORIAS_LICENCIA WHERE EST_ID = 1 AND CTL_DESCRIPCION_CORTA = ? ORDER BY CTL_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, nombre);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenCategoriaLicencia.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarCategoriaLicenciaByDescripcion: " + e);
        }
        return null;
    }

}
