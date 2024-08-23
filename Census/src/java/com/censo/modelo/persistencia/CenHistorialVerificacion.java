
package com.censo.modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CenHistorialVerificacion {
    
    private long id;
    private long ver_id;
    private int estado;
    private long usu_id;
    private String observaciones;
    private Timestamp fechaproceso;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVer_id() {
        return ver_id;
    }

    public void setVer_id(long ver_id) {
        this.ver_id = ver_id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public long getUsu_id() {
        return usu_id;
    }

    public void setUsu_id(long usu_id) {
        this.usu_id = usu_id;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Timestamp getFechaproceso() {
        return fechaproceso;
    }

    public void setFechaproceso(Timestamp fechaproceso) {
        this.fechaproceso = fechaproceso;
    }
    
    public static CenHistorialVerificacion load(ResultSet rs)throws SQLException{
        CenHistorialVerificacion historialVerificacion = new CenHistorialVerificacion();
        historialVerificacion.setId(rs.getLong(1));
        historialVerificacion.setVer_id(rs.getLong(2));
        historialVerificacion.setEstado(rs.getInt(3));
        historialVerificacion.setUsu_id(rs.getLong(4));
        historialVerificacion.setObservaciones(rs.getString(5));
        historialVerificacion.setFechaproceso(rs.getTimestamp(6));
        
        return historialVerificacion;
    }
    
}
