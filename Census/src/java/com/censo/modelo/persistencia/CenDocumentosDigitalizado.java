
package com.censo.modelo.persistencia;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CenDocumentosDigitalizado {
    
    private long id;
    private String nombre;
    private String ruta;
    private int tipo;
    private long referencia_id;
    private String observacion;
    private Timestamp fechaproceso;
    private long usu_id;

    public CenDocumentosDigitalizado() {
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

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public long getReferencia_id() {
        return referencia_id;
    }

    public void setReferencia_id(long referencia_id) {
        this.referencia_id = referencia_id;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Timestamp getFechaproceso() {
        return fechaproceso;
    }

    public void setFechaproceso(Timestamp fechaproceso) {
        this.fechaproceso = fechaproceso;
    }

    public long getUsu_id() {
        return usu_id;
    }

    public void setUsu_id(long usu_id) {
        this.usu_id = usu_id;
    }
    
    public static CenDocumentosDigitalizado load(ResultSet rs)throws SQLException{
        CenDocumentosDigitalizado documentosDigitalizado = new CenDocumentosDigitalizado();
        documentosDigitalizado.setId(rs.getLong(1));
        documentosDigitalizado.setNombre(rs.getString(2));
        documentosDigitalizado.setRuta(rs.getString(3));
        documentosDigitalizado.setTipo(rs.getInt(4));
        documentosDigitalizado.setReferencia_id(rs.getLong(5));
        documentosDigitalizado.setObservacion(rs.getString(6));
        documentosDigitalizado.setFechaproceso(rs.getTimestamp(7));
        documentosDigitalizado.setUsu_id(rs.getLong(8));
        return documentosDigitalizado;
    }
    
}
