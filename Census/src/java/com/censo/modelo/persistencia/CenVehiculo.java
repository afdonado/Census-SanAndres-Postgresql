
package com.censo.modelo.persistencia;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CenVehiculo {
    
    
    private long id;
    private String placa_veh;
    private String chasis;
    private String serie;
    private String motor;
    private int clase_veh;
    private int tipo_servicio;
    private long col_id;
    private long modelo;
    private long lin_id;
    private String licencia_transito;
    private String runt;
    private String soat;
    private Date fechaven_soat;
    private String tecno_mecanica;
    private Date fechaven_tecno;
    private long pai_id_matricula;
    private long mun_id_matricula;
    private String ciudad_matricula;
    private int tipodoc_importacion;
    private String doc_importacion;
    private Date fecha_importacion;
    private long pai_id_origen;
    private String observaciones;
    private int tipo_uso;
    private String transformado;
    private long usu_id;
    private int estado;
    private Timestamp fechaproceso;
    private Date fecha_matricula;
    
    public CenVehiculo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlaca_veh() {
        return placa_veh;
    }

    public void setPlaca_veh(String placa_veh) {
        this.placa_veh = placa_veh;
    }

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public int getClase_veh() {
        return clase_veh;
    }

    public void setClase_veh(int clase_veh) {
        this.clase_veh = clase_veh;
    }

    public int getTipo_servicio() {
        return tipo_servicio;
    }

    public void setTipo_servicio(int tipo_servicio) {
        this.tipo_servicio = tipo_servicio;
    }

    public long getCol_id() {
        return col_id;
    }

    public void setCol_id(long col_id) {
        this.col_id = col_id;
    }

    public long getModelo() {
        return modelo;
    }

    public void setModelo(long modelo) {
        this.modelo = modelo;
    }

    public long getLin_id() {
        return lin_id;
    }

    public void setLin_id(long lin_id) {
        this.lin_id = lin_id;
    }

    public String getLicencia_transito() {
        return licencia_transito;
    }

    public void setLicencia_transito(String licencia_transito) {
        this.licencia_transito = licencia_transito;
    }

    public String getRunt() {
        return runt;
    }

    public void setRunt(String runt) {
        this.runt = runt;
    }

    public String getSoat() {
        return soat;
    }

    public void setSoat(String soat) {
        this.soat = soat;
    }

    public Date getFechaven_soat() {
        return fechaven_soat;
    }

    public void setFechaven_soat(Date fechaven_soat) {
        this.fechaven_soat = fechaven_soat;
    }

    public String getTecno_mecanica() {
        return tecno_mecanica;
    }

    public void setTecno_mecanica(String tecno_mecanica) {
        this.tecno_mecanica = tecno_mecanica;
    }

    public Date getFechaven_tecno() {
        return fechaven_tecno;
    }

    public void setFechaven_tecno(Date fechaven_tecno) {
        this.fechaven_tecno = fechaven_tecno;
    }

    public long getPai_id_matricula() {
        return pai_id_matricula;
    }

    public void setPai_id_matricula(long pai_id_matricula) {
        this.pai_id_matricula = pai_id_matricula;
    }

    public long getMun_id_matricula() {
        return mun_id_matricula;
    }

    public void setMun_id_matricula(long mun_id_matricula) {
        this.mun_id_matricula = mun_id_matricula;
    }

    public String getCiudad_matricula() {
        return ciudad_matricula;
    }

    public void setCiudad_matricula(String ciudad_matricula) {
        this.ciudad_matricula = ciudad_matricula;
    }

    public int getTipodoc_importacion() {
        return tipodoc_importacion;
    }

    public void setTipodoc_importacion(int tipodoc_importacion) {
        this.tipodoc_importacion = tipodoc_importacion;
    }

    public String getDoc_importacion() {
        return doc_importacion;
    }

    public void setDoc_importacion(String doc_importacion) {
        this.doc_importacion = doc_importacion;
    }

    public Date getFecha_importacion() {
        return fecha_importacion;
    }

    public void setFecha_importacion(Date fecha_importacion) {
        this.fecha_importacion = fecha_importacion;
    }

    public long getPai_id_origen() {
        return pai_id_origen;
    }

    public void setPai_id_origen(long pai_id_origen) {
        this.pai_id_origen = pai_id_origen;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getTipo_uso() {
        return tipo_uso;
    }

    public void setTipo_uso(int tipo_uso) {
        this.tipo_uso = tipo_uso;
    }

    public String getTransformado() {
        return transformado;
    }

    public void setTransformado(String transformado) {
        this.transformado = transformado;
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

    public Date getFecha_matricula() {
        return fecha_matricula;
    }

    public void setFecha_matricula(Date fecha_matricula) {
        this.fecha_matricula = fecha_matricula;
    }
    
    public static CenVehiculo load(ResultSet rs)throws SQLException{
        CenVehiculo vehiculo = new CenVehiculo();
        vehiculo.setId(rs.getLong(1));
        vehiculo.setPlaca_veh(rs.getString(2));
        vehiculo.setChasis(rs.getString(3));
        vehiculo.setSerie(rs.getString(4));
        vehiculo.setMotor(rs.getString(5));
        vehiculo.setClase_veh(rs.getInt(6));
        vehiculo.setTipo_servicio(rs.getInt(7));
        vehiculo.setCol_id(rs.getLong(8));
        vehiculo.setModelo(rs.getLong(9));
        vehiculo.setLin_id(rs.getLong(10));
        vehiculo.setLicencia_transito(rs.getString(11));
        vehiculo.setRunt(rs.getString(12));
        vehiculo.setSoat(rs.getString(13));
        vehiculo.setFechaven_soat(rs.getDate(14));
        vehiculo.setTecno_mecanica(rs.getString(15));
        vehiculo.setFechaven_tecno(rs.getDate(16));
        vehiculo.setPai_id_matricula(rs.getLong(17));
        vehiculo.setMun_id_matricula(rs.getLong(18));
        vehiculo.setCiudad_matricula(rs.getString(19));
        vehiculo.setTipodoc_importacion(rs.getInt(20));
        vehiculo.setDoc_importacion(rs.getString(21));
        vehiculo.setFecha_importacion(rs.getDate(22));
        vehiculo.setPai_id_origen(rs.getLong(23));
        vehiculo.setObservaciones(rs.getString(24));
        vehiculo.setTipo_uso(rs.getInt(25));
        vehiculo.setTransformado(rs.getString(26));
        vehiculo.setUsu_id(rs.getLong(27));
        vehiculo.setEstado(rs.getInt(28));
        vehiculo.setFechaproceso(rs.getTimestamp(29));
        vehiculo.setFecha_matricula(rs.getDate(30));
        return vehiculo;
    }
    
}
