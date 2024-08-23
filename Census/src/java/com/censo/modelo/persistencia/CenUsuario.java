
package com.censo.modelo.persistencia;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CenUsuario {
    
    private long id;
    private String nombre;
    private String pass;
    private Date fechaini;
    private Date fechafin;
    private int estado;
    private Timestamp fechaproceso;
    private int tipodocumento;
    private String numerodocumento;

    public CenUsuario() {
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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
    
    public int getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(int tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public String getNumerodocumento() {
        return numerodocumento;
    }

    public void setNumerodocumento(String numerodocumento) {
        this.numerodocumento = numerodocumento;
    }
    
    public static CenUsuario load(ResultSet rs)throws SQLException{
        CenUsuario usuario= new CenUsuario();
        usuario.setId(rs.getLong(1));
        usuario.setNombre(rs.getString(2));
        usuario.setPass(rs.getString(3));
        usuario.setFechaini(rs.getDate(4));
        usuario.setFechafin(rs.getDate(5));
        usuario.setEstado(rs.getInt(6));
        usuario.setFechaproceso(rs.getTimestamp(7));
        usuario.setTipodocumento(rs.getInt(8));
        usuario.setNumerodocumento(rs.getString(9));
        return usuario;
    }

}
