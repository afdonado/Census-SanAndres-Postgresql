
package com.censo.modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CenTipoServicio {
    
    private int id;
    private String descripcion;
    private String descripcion_corta;
    private int estado;

    public CenTipoServicio() {
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
    
    public static CenTipoServicio load(ResultSet rs)throws SQLException{
        CenTipoServicio tipoServicio = new CenTipoServicio();
        tipoServicio.setId(rs.getInt(1));
        tipoServicio.setDescripcion(rs.getString(2));
        tipoServicio.setDescripcion_corta(rs.getString(3));
        tipoServicio.setEstado(rs.getInt(4));
        return tipoServicio;
    }
    
}
