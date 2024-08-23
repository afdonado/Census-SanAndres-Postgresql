
package com.censo.modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CenPuntoAtencion {
    
    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String consecutivo;
    private int estado;

    public CenPuntoAtencion() {
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public static CenPuntoAtencion load(ResultSet rs)throws SQLException{
        CenPuntoAtencion puntoAtencion = new CenPuntoAtencion();
        puntoAtencion.setId(rs.getInt(1));
        puntoAtencion.setNombre(rs.getString(2));
        puntoAtencion.setDireccion(rs.getString(3));
        puntoAtencion.setTelefono(rs.getString(4));
        puntoAtencion.setConsecutivo(rs.getString(5));
        puntoAtencion.setEstado(rs.getInt(6));
        return puntoAtencion;
    }
    
}
