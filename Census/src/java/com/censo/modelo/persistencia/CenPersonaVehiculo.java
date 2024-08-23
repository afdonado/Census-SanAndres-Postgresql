
package com.censo.modelo.persistencia;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CenPersonaVehiculo {
    
    private long id;
    private int tper_id;
    private long per_id;
    private long veh_id;
    private Date fechaInicio;
    private Date fechaFinal;
    private long usu_id;
    private int estado;
    private Timestamp fechaProceso;

    public CenPersonaVehiculo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTper_id() {
        return tper_id;
    }

    public void setTper_id(int tper_id) {
        this.tper_id = tper_id;
    }

    public long getPer_id() {
        return per_id;
    }

    public void setPer_id(long per_id) {
        this.per_id = per_id;
    }

    public long getVeh_id() {
        return veh_id;
    }

    public void setVeh_id(long veh_id) {
        this.veh_id = veh_id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public long getUsu_id() {
        return usu_id;
    }

    public void setUsu_id(long usu_id) {
        this.usu_id = usu_id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Timestamp getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Timestamp fechaProceso) {
        this.fechaProceso = fechaProceso;
    }
    
    public static CenPersonaVehiculo load(ResultSet rs)throws SQLException{
        CenPersonaVehiculo personaVehiculo= new CenPersonaVehiculo();
        personaVehiculo.setId(rs.getLong(1));
        personaVehiculo.setTper_id(rs.getInt(2));
        personaVehiculo.setPer_id(rs.getLong(3));
        personaVehiculo.setVeh_id(rs.getLong(4));
        personaVehiculo.setFechaInicio(rs.getDate(5));
        personaVehiculo.setFechaFinal(rs.getDate(6));
        personaVehiculo.setUsu_id(rs.getLong(7));
        personaVehiculo.setEstado(rs.getInt(8));
        personaVehiculo.setFechaProceso(rs.getTimestamp(9));        
        return personaVehiculo;
    }
    
}
