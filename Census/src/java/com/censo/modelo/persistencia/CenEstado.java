
package com.censo.modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CenEstado {
    
    private int id;
    private String descripcion;

    public CenEstado() {
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
    
    public static CenEstado load(ResultSet rs)throws SQLException{
        CenEstado estado = new CenEstado();
        estado.setId(rs.getInt(1));
        estado.setDescripcion(rs.getString(2));
        return estado;
    }
    
}
