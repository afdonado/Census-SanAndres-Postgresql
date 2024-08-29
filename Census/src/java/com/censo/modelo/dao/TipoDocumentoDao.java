package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenTipoDocumento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TipoDocumentoDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public List ListarTiposDocumento(Connection conex) throws SQLException {

        List listaTipoDocumento = new LinkedList();

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_TIPOS_DOCUMENTO WHERE EST_ID = 1 ORDER BY TDOC_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaTipoDocumento.add(CenTipoDocumento.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarTiposDocumento: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarTiposDocumento:" + e);
            }
        }
        return listaTipoDocumento;
    }

    public CenTipoDocumento ConsultarTipoDocumentoById(Connection conex, int id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_TIPOS_DOCUMENTO WHERE EST_ID = 1 AND TDOC_ID = ? ORDER BY TDOC_ID ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenTipoDocumento.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarTipoDocumentoById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarTipoDocumentoById:" + e);
            }
        }
        return null;
    }
    
}
