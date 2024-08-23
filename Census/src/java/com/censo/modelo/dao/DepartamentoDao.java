package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenDepartamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartamentoDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public java.util.List ListarDepartamentos() throws SQLException {

        java.util.List listaDepartamento = new java.util.LinkedList();

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_DEPARTAMENTOS ORDER BY DEPT_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaDepartamento.add(CenDepartamento.load(rst));
            }
        } catch (Exception e) {
            throw new SQLException("Error en ListarDepartamentos: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarDepartamentos:" + e);
            }
        }
        return listaDepartamento;
    }

    public CenDepartamento ConsultarDepartamentoById(long id) throws SQLException {

        try (Connection conex = conectar()) {
            pst = conex.prepareStatement("SELECT * FROM CEN_DEPARTAMENTOS WHERE DEPT_ID = ? ");
            pst.setLong(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenDepartamento.load(rst);
            }
        } catch (Exception e) {
            throw new SQLException("Error en consultarDepartamentoById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de consultarDepartamentoById:" + e);
            }
        }
        return null;
    }
    
}
