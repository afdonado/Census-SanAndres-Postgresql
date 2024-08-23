
package com.censo.modelo.persistencia;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CenVerificacion {
    
    private long id;
    private long cen_id;
    private String verificado_runt;
    private String verificado_doc;
    private String verificado_foto;
    private String observaciones;
    private long usu_id;
    private Timestamp fechaproceso;
    private int estado;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCen_id() {
        return cen_id;
    }

    public void setCen_id(long cen_id) {
        this.cen_id = cen_id;
    }

    public String getVerificado_runt() {
        return verificado_runt;
    }

    public void setVerificado_runt(String verificado_runt) {
        this.verificado_runt = verificado_runt;
    }

    public String getVerificado_doc() {
        return verificado_doc;
    }

    public void setVerificado_doc(String verificado_doc) {
        this.verificado_doc = verificado_doc;
    }

    public String getVerificado_foto() {
        return verificado_foto;
    }

    public void setVerificado_foto(String verificado_foto) {
        this.verificado_foto = verificado_foto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public long getUsu_id() {
        return usu_id;
    }

    public void setUsu_id(long usu_id) {
        this.usu_id = usu_id;
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
    
    public static CenVerificacion load(ResultSet rs)throws SQLException{
        CenVerificacion verificacion = new CenVerificacion();
        verificacion.setId(rs.getLong(1));
        verificacion.setCen_id(rs.getLong(2));
        verificacion.setVerificado_runt(rs.getString(3));
        verificacion.setVerificado_doc(rs.getString(4));
        verificacion.setVerificado_foto(rs.getString(5));
        verificacion.setObservaciones(rs.getString(6));
        verificacion.setUsu_id(rs.getLong(7));
        verificacion.setFechaproceso(rs.getTimestamp(8));
        verificacion.setEstado(rs.getInt(9));
        return verificacion;
    }
    
}
