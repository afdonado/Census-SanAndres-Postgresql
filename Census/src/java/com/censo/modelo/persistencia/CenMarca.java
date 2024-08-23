
package com.censo.modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CenMarca {
    
    private long id;
    private String nombre;

    public CenMarca() {
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
    
    public static CenMarca load(ResultSet rs)throws SQLException{
        CenMarca marca = new CenMarca();
        marca.setId(rs.getLong(1));
        marca.setNombre(rs.getString(2));
        return marca;
    }
    
}
