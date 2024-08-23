package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenCategoriaLicencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CategoriaLicenciaDao extends Conexion{
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;

    public List ListarCategoriasLicencia() throws SQLException {
        
        List listaCategoriasLicencia = new LinkedList();

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_CATEGORIAS_LICENCIA WHERE EST_ID = 1 ORDER BY CTL_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaCategoriasLicencia.add(CenCategoriaLicencia.load(rst));
            }
        } catch (Exception e) {
            throw new SQLException("Error en ListarCategoriasLicencia: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCategoriasLicencia:" + e);
            }
        }
        return listaCategoriasLicencia;
    }

    public CenCategoriaLicencia ConsultarCategoriaLicenciaById(int id) throws SQLException {

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_CATEGORIAS_LICENCIA WHERE EST_ID = 1 AND CTL_ID = ? ORDER BY CTL_ID ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenCategoriaLicencia.load(rst);
            }
        } catch (Exception e) {
            throw new SQLException("Error en ConsultarCategoriaLicenciaById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarCategoriaLicenciaById:" + e);
            }
        }
        return null;
    }
    
}
