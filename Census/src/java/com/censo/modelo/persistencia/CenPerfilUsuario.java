
package com.censo.modelo.persistencia;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CenPerfilUsuario {
    
    private long id;
    private int pef_id;
    private long usu_id;
    private Date fechaini;
    private Date fechafin;
    private Timestamp fechaproceso;
    private int estado;

    public CenPerfilUsuario() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPef_id() {
        return pef_id;
    }

    public void setPef_id(int pef_id) {
        this.pef_id = pef_id;
    }

    public long getUsu_id() {
        return usu_id;
    }

    public void setUsu_id(long usu_id) {
        this.usu_id = usu_id;
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
    
    public static CenPerfilUsuario load(ResultSet rs)throws SQLException{
        CenPerfilUsuario perfilUsuario= new CenPerfilUsuario();
        perfilUsuario.setId(rs.getLong(1));
        perfilUsuario.setPef_id(rs.getInt(2));
        perfilUsuario.setUsu_id(rs.getLong(3));
        perfilUsuario.setFechaini(rs.getDate(4));
        perfilUsuario.setFechafin(rs.getDate(5));
        perfilUsuario.setFechaproceso(rs.getTimestamp(6));
        perfilUsuario.setEstado(rs.getInt(7));
        return perfilUsuario;
    }
    
}
