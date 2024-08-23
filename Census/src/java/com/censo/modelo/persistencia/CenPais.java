
package com.censo.modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CenPais {
    
    private long id;
    private String nombre;

    public CenPais() {
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
    
    public static CenPais load(ResultSet rs)throws SQLException{
        CenPais pais = new CenPais();
        pais.setId(rs.getLong(1));
        pais.setNombre(rs.getString(2));
        return pais;
    }
    
}
