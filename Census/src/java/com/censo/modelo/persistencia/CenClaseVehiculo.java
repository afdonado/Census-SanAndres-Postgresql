
package com.censo.modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CenClaseVehiculo {
    
    private int id;
    private String descripcion;
    private String descripcion_corta;
    private int estado;

    public CenClaseVehiculo() {
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
    
    public static CenClaseVehiculo load(ResultSet rs)throws SQLException{
        CenClaseVehiculo claseVehiculo = new CenClaseVehiculo();
        claseVehiculo.setId(rs.getInt(1));
        claseVehiculo.setDescripcion(rs.getString(2));
        claseVehiculo.setDescripcion_corta(rs.getString(3));
        claseVehiculo.setEstado(rs.getInt(4));
        return claseVehiculo;
    }
}
