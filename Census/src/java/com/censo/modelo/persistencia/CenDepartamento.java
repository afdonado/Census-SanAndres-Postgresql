
package com.censo.modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CenDepartamento {
    
    private int id;
    private String nombre;

    public CenDepartamento() {
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

    public static CenDepartamento load(ResultSet rs)throws SQLException{
        CenDepartamento departamento= new CenDepartamento();
        departamento.setId(rs.getInt(1));
        departamento.setNombre(rs.getString(2));
        return departamento;
    }
}
