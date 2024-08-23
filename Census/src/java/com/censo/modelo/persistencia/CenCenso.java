
package com.censo.modelo.persistencia;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CenCenso {
    
    private long id;
    private Date fecha;
    private String hora;
    private int pun_id;
    private long veh_id;
    private long per_id;
    private int tper_id;
    private long usu_id;
    private int estado;
    private Timestamp fechaproceso;
    private String numero;
    private String observaciones;

    public CenCenso() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getPun_id() {
        return pun_id;
    }

    public void setPun_id(int pun_id) {
        this.pun_id = pun_id;
    }

    public long getVeh_id() {
        return veh_id;
    }

    public void setVeh_id(long veh_id) {
        this.veh_id = veh_id;
    }

    public long getPer_id() {
        return per_id;
    }

    public void setPer_id(long per_id) {
        this.per_id = per_id;
    }

    public int getTper_id() {
        return tper_id;
    }

    public void setTper_id(int tper_id) {
        this.tper_id = tper_id;
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

    public Timestamp getFechaproceso() {
        return fechaproceso;
    }

    public void setFechaproceso(Timestamp fechaproceso) {
        this.fechaproceso = fechaproceso;
    }
    
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public static CenCenso load(ResultSet rs)throws SQLException{
        CenCenso censo = new CenCenso();
        censo.setId(rs.getLong(1));
        censo.setFecha(rs.getDate(2));
        censo.setHora(rs.getString(3));
        censo.setPun_id(rs.getInt(4));
        censo.setVeh_id(rs.getLong(5));
        censo.setPer_id(rs.getLong(6));
        censo.setTper_id(rs.getInt(7));
        censo.setUsu_id(rs.getLong(8));
        censo.setEstado(rs.getInt(9));
        censo.setFechaproceso(rs.getTimestamp(10));
        censo.setNumero(rs.getString(11));
        censo.setObservaciones(rs.getString(12));
        return censo;
    }

}
