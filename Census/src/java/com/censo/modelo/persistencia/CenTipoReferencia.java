
package com.censo.modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CenTipoReferencia {
    
    private int id;
    private String descripcion;

    public CenTipoReferencia() {
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
    
    public static CenTipoReferencia load(ResultSet rs)throws SQLException{
        CenTipoReferencia tipoReferencia = new CenTipoReferencia();
        tipoReferencia.setId(rs.getInt(1));
        tipoReferencia.setDescripcion(rs.getString(2));
        return tipoReferencia;
    }
    
}
