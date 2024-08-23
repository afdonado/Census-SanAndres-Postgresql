
package com.censo.modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CenEstadoVerificacion {
    
    private int id;
    private String descripcion;

    public CenEstadoVerificacion() {
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
    
    public static CenEstadoVerificacion load(ResultSet rs)throws SQLException{
        CenEstadoVerificacion estadoVerificacion = new CenEstadoVerificacion();
        estadoVerificacion.setId(rs.getInt(1));
        estadoVerificacion.setDescripcion(rs.getString(2));
        return estadoVerificacion;
    }
    
}
