
package com.censo.modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CenMunicipio {
    
    private long id;
    private String nombre;
    private long dep_id;

    public CenMunicipio() {
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

    public long getDep_id() {
        return dep_id;
    }

    public void setDep_id(long dep_id) {
        this.dep_id = dep_id;
    }
    
    public static CenMunicipio load(ResultSet rs)throws SQLException{
        CenMunicipio municipio= new CenMunicipio();
        municipio.setId(rs.getLong(1));
        municipio.setNombre(rs.getString(2));
        municipio.setDep_id(rs.getLong(3));
        return municipio;
    }
    
}
