
package com.censo.servicios;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Datosresponse {
    
    private String fecha_censo;
    private String placa_vehiculo;
    private String tipo_persona;
    private String tipo_doc;
    private String documento;
    private String nombre;

    public String getFecha_censo() {
        return fecha_censo;
    }

    public void setFecha_censo(String fecha_censo) {
        this.fecha_censo = fecha_censo;
    }

    public String getPlaca_vehiculo() {
        return placa_vehiculo;
    }

    public void setPlaca_vehiculo(String placa_vehiculo) {
        this.placa_vehiculo = placa_vehiculo;
    }

    public String getTipo_persona() {
        return tipo_persona;
    }

    public void setTipo_persona(String tipo_persona) {
        this.tipo_persona = tipo_persona;
    }

    public String getTipo_doc() {
        return tipo_doc;
    }

    public void setTipo_doc(String tipo_doc) {
        this.tipo_doc = tipo_doc;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static Datosresponse load(ResultSet rs)throws SQLException{
        Datosresponse datosresponse = new Datosresponse();
        datosresponse.setFecha_censo(rs.getString(1));
        datosresponse.setPlaca_vehiculo(rs.getString(2));
        datosresponse.setTipo_persona(rs.getString(3));
        datosresponse.setTipo_doc(rs.getString(4));
        datosresponse.setDocumento(rs.getString(5));
        datosresponse.setNombre(rs.getString(6));
        return datosresponse;
    }
    

       
}
