
package com.censo.modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CenLinea {
    
    private long id;
    private String nombre;
    private long mar_id;

    public CenLinea() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getMar_id() {
        return mar_id;
    }

    public void setMar_id(long mar_id) {
        this.mar_id = mar_id;
    }

    public static CenLinea load(ResultSet rs)throws SQLException{
        CenLinea linea= new CenLinea();
        linea.setId(rs.getLong(1));
        linea.setNombre(rs.getString(2));
        linea.setMar_id(rs.getLong(3));
        return linea;
    }
    
}
