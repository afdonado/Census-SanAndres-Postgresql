
package com.censo.modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CenGrupoSanguineo {
    
    private int id;
    private String descripcion;
    private String descripcion_corta;
    private int estado;

    public CenGrupoSanguineo() {
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
    
    public static CenGrupoSanguineo load(ResultSet rs)throws SQLException{
        CenGrupoSanguineo grupoSanguineo = new CenGrupoSanguineo();
        grupoSanguineo.setId(rs.getInt(1));
        grupoSanguineo.setDescripcion(rs.getString(2));
        grupoSanguineo.setDescripcion_corta(rs.getString(3));
        grupoSanguineo.setEstado(rs.getInt(4));
        return grupoSanguineo;
    }
    
}
