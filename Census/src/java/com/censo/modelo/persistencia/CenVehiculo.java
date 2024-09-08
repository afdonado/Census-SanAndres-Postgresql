
package com.censo.modelo.persistencia;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CenVehiculo {
    
    
    private int id;
    private String placa_veh;
    private String chasis;
    private String serie;
    private String motor;
    private int clase_veh;
    private int tipo_servicio;
    private int col_id;
    private int modelo;
    private int lin_id;
    private String licencia_transito;
    private String runt;
    private String soat;
    private Date fechaven_soat;
    private String tecno_mecanica;
    private Date fechaven_tecno;
    private int pai_id_matricula;
    private int mun_id_matricula;
    private String ciudad_matricula;
    private int tipodoc_importacion;
    private String doc_importacion;
    private Date fecha_importacion;
    private int pai_id_origen;
    private String observaciones;
    private int tipo_uso;
    private String transformado;
    private int usu_id;
    private int estado;
    private Timestamp fechaproceso;
    private Date fecha_matricula;
    
    public static CenVehiculo load(ResultSet rs)throws SQLException{
        CenVehiculo vehiculo = new CenVehiculo();
        vehiculo.setId(rs.getInt(1));
        vehiculo.setPlaca_veh(rs.getString(2));
        vehiculo.setChasis(rs.getString(3));
        vehiculo.setSerie(rs.getString(4));
        vehiculo.setMotor(rs.getString(5));
        vehiculo.setClase_veh(rs.getInt(6));
        vehiculo.setTipo_servicio(rs.getInt(7));
        vehiculo.setCol_id(rs.getInt(8));
        vehiculo.setModelo(rs.getInt(9));
        vehiculo.setLin_id(rs.getInt(10));
        vehiculo.setLicencia_transito(rs.getString(11));
        vehiculo.setRunt(rs.getString(12));
        vehiculo.setSoat(rs.getString(13));
        vehiculo.setFechaven_soat(rs.getDate(14));
        vehiculo.setTecno_mecanica(rs.getString(15));
        vehiculo.setFechaven_tecno(rs.getDate(16));
        vehiculo.setPai_id_matricula(rs.getInt(17));
        vehiculo.setMun_id_matricula(rs.getInt(18));
        vehiculo.setCiudad_matricula(rs.getString(19));
        vehiculo.setTipodoc_importacion(rs.getInt(20));
        vehiculo.setDoc_importacion(rs.getString(21));
        vehiculo.setFecha_importacion(rs.getDate(22));
        vehiculo.setPai_id_origen(rs.getInt(23));
        vehiculo.setObservaciones(rs.getString(24));
        vehiculo.setTipo_uso(rs.getInt(25));
        vehiculo.setTransformado(rs.getString(26));
        vehiculo.setUsu_id(rs.getInt(27));
        vehiculo.setEstado(rs.getInt(28));
        vehiculo.setFechaproceso(rs.getTimestamp(29));
        vehiculo.setFecha_matricula(rs.getDate(30));
        return vehiculo;
    }
    
}
