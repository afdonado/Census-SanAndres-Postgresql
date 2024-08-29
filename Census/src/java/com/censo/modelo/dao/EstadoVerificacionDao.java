package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenEstadoVerificacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class EstadoVerificacionDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public List ListarEstadosVerificacion(Connection conex) throws SQLException {

        List listaEstado = new LinkedList();

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_ESTADOS_VERIFICACIONES ORDER BY EVER_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaEstado.add(CenEstadoVerificacion.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarEstadosVerificacion: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarEstadosVerificacion:" + e);
            }
        }
        return listaEstado;
    }
    
}
