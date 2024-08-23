
package com.censo.modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CenColor {
    
    private long id;
    private String nombre;

    public CenColor() {
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
    
    public static CenColor load(ResultSet rs)throws SQLException{
        CenColor color = new CenColor();
        color.setId(rs.getLong(1));
        color.setNombre(rs.getString(2));
        return color;
    }
    
}
