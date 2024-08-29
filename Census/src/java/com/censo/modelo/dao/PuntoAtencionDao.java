package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenPuntoAtencion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PuntoAtencionDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public List ListarPuntosAtencion(Connection conex) throws SQLException {

        List listaPuntoAtencion = new LinkedList();

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_PUNTOS_ATENCION WHERE EST_ID = 1 ORDER BY PUN_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaPuntoAtencion.add(CenPuntoAtencion.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarPuntosAtencion: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarPuntosAtencion:" + e);
            }
        }
        return listaPuntoAtencion;
    }

    public CenPuntoAtencion ConsultarPuntoAtencionById(Connection conex, int id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_PUNTOS_ATENCION WHERE PUN_ID = ? ORDER BY PUN_ID ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenPuntoAtencion.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarPuntoAtencionById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarPuntoAtencionById:" + e);
            }
        }
        return null;
    }
    
}
