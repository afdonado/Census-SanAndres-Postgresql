
package com.censo.modelo.persistencia;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CenPersona {
        
    private long id;
    private int tipodocumento;
    private String documento;
    private String nombre1;
    private String nombre2;
    private String apellido1;
    private String apellido2;
    private java.sql.Date fechanacimiento;
    private int genero;
    private String direccion;
    private long mun_id;
    private String telefono;
    private String mail;
    private int gruposanguineo;
    private String licenciaconduccion;
    private Date fechaexp;
    private Date fechaven;
    private int categorialicencia;
    private Timestamp fechaproceso;
    private long usu_id;    
    

    public CenPersona() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(int tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public java.sql.Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(java.sql.Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public long getMun_id() {
        return mun_id;
    }

    public void setMun_id(long mun_id) {
        this.mun_id = mun_id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getGruposanguineo() {
        return gruposanguineo;
    }

    public void setGruposanguineo(int gruposanguineo) {
        this.gruposanguineo = gruposanguineo;
    }

    public String getLicenciaconduccion() {
        return licenciaconduccion;
    }

    public void setLicenciaconduccion(String licenciaconduccion) {
        this.licenciaconduccion = licenciaconduccion;
    }

    public java.sql.Date getFechaexp() {
        return fechaexp;
    }

    public void setFechaexp(java.sql.Date fechaexp) {
        this.fechaexp = fechaexp;
    }

    public java.sql.Date getFechaven() {
        return fechaven;
    }

    public void setFechaven(java.sql.Date fechaven) {
        this.fechaven = fechaven;
    }

    public int getCategorialicencia() {
        return categorialicencia;
    }

    public void setCategorialicencia(int categorialicencia) {
        this.categorialicencia = categorialicencia;
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
    
    public static CenPersona load(ResultSet rs)throws SQLException{
        CenPersona persona= new CenPersona();
        persona.setId(rs.getLong(1));
        persona.setTipodocumento(rs.getInt(2));
        persona.setDocumento(rs.getString(3));
        persona.setNombre1(rs.getString(4));
        persona.setNombre2(rs.getString(5));
        persona.setApellido1(rs.getString(6));
        persona.setApellido2(rs.getString(7));
        persona.setFechanacimiento(rs.getDate(8));
        persona.setGenero(rs.getInt(9));
        persona.setDireccion(rs.getString(10));
        persona.setMun_id(rs.getLong(11));
        persona.setTelefono(rs.getString(12));
        persona.setMail(rs.getString(13));
        persona.setGruposanguineo(rs.getInt(14));
        persona.setLicenciaconduccion(rs.getString(15));
        persona.setFechaexp(rs.getDate(16));
        persona.setFechaven(rs.getDate(17));
        persona.setCategorialicencia(rs.getInt(18));
        persona.setFechaproceso(rs.getTimestamp(19));
        persona.setUsu_id(rs.getLong(20));
        return persona;
    }
    
}
