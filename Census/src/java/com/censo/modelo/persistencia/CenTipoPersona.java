
package com.censo.modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CenTipoPersona {
    
    private int id;
    private String descripcion;
    private String descripcion_corta;
    private int estado;

    public CenTipoPersona() {
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

    public String getDescripcion_corta() {
        return descripcion_corta;
    }

    public void setDescripcion_corta(String descripcion_corta) {
        this.descripcion_corta = descripcion_corta;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public static CenTipoPersona load(ResultSet rs)throws SQLException{
        CenTipoPersona tipoPersona = new CenTipoPersona();
        tipoPersona.setId(rs.getInt(1));
        tipoPersona.setDescripcion(rs.getString(2));
        tipoPersona.setDescripcion_corta(rs.getString(3));
        tipoPersona.setEstado(rs.getInt(4));
        return tipoPersona;
    }
    
}
