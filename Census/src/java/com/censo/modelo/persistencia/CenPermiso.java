
package com.censo.modelo.persistencia;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CenPermiso {
    
    private long id;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private Date fechaini;
    private Date fechafin;
    private Timestamp fechaproceso;
    private int estado;
    private int mod_id;
    private int tipo;

    public CenPermiso() {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
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

    public int getMod_id() {
        return mod_id;
    }

    public void setMod_id(int mod_id) {
        this.mod_id = mod_id;
    }
    
    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    public static CenPermiso load(ResultSet rs)throws SQLException{
        CenPermiso permisos= new CenPermiso();
        permisos.setId(rs.getInt(1));
        permisos.setNombre(rs.getString(2));
        permisos.setDescripcion(rs.getString(3));
        permisos.setUbicacion(rs.getString(4));
        permisos.setFechaini(rs.getDate(5));
        permisos.setFechafin(rs.getDate(6));
        permisos.setFechaproceso(rs.getTimestamp(7));
        permisos.setEstado(rs.getInt(8));
        permisos.setMod_id(rs.getInt(9));
        permisos.setTipo(rs.getInt(10));
        return permisos;
    }
    
}
