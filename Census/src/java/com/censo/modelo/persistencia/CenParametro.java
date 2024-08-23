
package com.censo.modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CenParametro {
    
    private int id;
    private String descripcion;
    private int estado;

    public CenParametro() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public static CenParametro load(ResultSet rs)throws SQLException{
        CenParametro parametro= new CenParametro();
        parametro.setId(rs.getInt(1));
        parametro.setDescripcion(rs.getString(2));
        parametro.setEstado(rs.getInt(3));
        return parametro;
    }
    
}
