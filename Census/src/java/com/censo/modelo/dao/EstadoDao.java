package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenEstado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class EstadoDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public List ListarEstados(Connection conex) throws SQLException {
        
        List listaEstado = new LinkedList();

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_ESTADOS ORDER BY EST_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaEstado.add(CenEstado.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarEstados: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarEstados:" + e);
            }
        }
        return listaEstado;
    }

    public CenEstado ConsultarEstadoById(Connection conex, int id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_ESTADOS WHERE EST_ID = ? ORDER BY EST_ID ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenEstado.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarEstadoById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarEstadoById:" + e);
            }
        }
        return null;
    }

}
