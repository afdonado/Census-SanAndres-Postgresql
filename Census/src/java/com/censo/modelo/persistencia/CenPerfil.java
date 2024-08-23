
package com.censo.modelo.persistencia;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CenPerfil {
    
    private int id;
    private String nombre;
    private Date fechaini;
    private Date fechafin;
    private Timestamp fechaproceso;
    private int estado;

    public CenPerfil() {
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

    public Date getFechaini() {
        return fechaini;
    }

    public void setFechaini(Date fechaini) {
        this.fechaini = fechaini;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public Timestamp getFechaproceso() {
        return fechaproceso;
    }

    public void setFechaproceso(Timestamp fechaproceso) {
        this.fechaproceso = fechaproceso;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public static CenPerfil load(ResultSet rs)throws SQLException{
        CenPerfil perfil= new CenPerfil();
        perfil.setId(rs.getInt(1));
        perfil.setNombre(rs.getString(2));
        perfil.setFechaini(rs.getDate(3));
        perfil.setFechafin(rs.getDate(4));
        perfil.setFechaproceso(rs.getTimestamp(5));
        perfil.setEstado(rs.getInt(6));
        return perfil;
    }
    
}
