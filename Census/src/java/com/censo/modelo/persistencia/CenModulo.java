
package com.censo.modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CenModulo {
    
    private int id;
    private String nombre;
    private int estado;
    private int orden;
    private String icono;
    
    public CenModulo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
    
    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }
    
    public static CenModulo load(ResultSet rs)throws SQLException{
        CenModulo cenModulo = new CenModulo();
        cenModulo.setId(rs.getInt(1));
        cenModulo.setNombre(rs.getString(2));
        cenModulo.setEstado(rs.getInt(3));
        cenModulo.setOrden(rs.getInt(4));
        cenModulo.setIcono(rs.getString(5));
        return cenModulo;
    }

    

   

}
