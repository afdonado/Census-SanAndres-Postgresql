package com.censo.modelo.persistencia;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class Vehiculo2017 {
    
    
    private long id;
    private String placa;
    private String chasis;
    private String serie;
    private String motor;
    private int claseVehiculoId;
    private String claseVehiculo;
    private int tipoServicioId;
    private String tipoServicio;
    private int colorId;
    private String color;
    private int modelo;
    private int lineaId;
    private String linea;
    private int marcaId;
    private String marca;
    private String licenciaTransito;
    private String runt;
    private String soat;
    private Date fechaVenSoat;
    private String tecnomecanica;
    private Date fechaVenTecnomecanica;
    private int paisMatriculaId;
    private String paisMatricula;
    private int municipoMatriculaId;
    private String municipoMatricula;
    private int departamentoMatriculaId;
    private String departamentoMatricula;
    private String ciudadMatricula;
    private Date fechaMatricula;
    private int tipoImportacionId;
    private String tipoImportacion;
    private String documentoImportacion;
    private Date fechaImportacion;
    private int paisImportacionId;
    private String paisImportacion;
    private int tipoUsoId;
    private String tipoUso;
    private String transformado;
    private String observaciones;    

    public static Vehiculo2017 load(ResultSet rs)throws SQLException{
        Vehiculo2017 vehiculo = new Vehiculo2017();
        vehiculo.setId(rs.getInt(1));
        vehiculo.setPlaca(rs.getString(2));
        vehiculo.setChasis(rs.getString(3));
        vehiculo.setSerie(rs.getString(4));
        vehiculo.setMotor(rs.getString(5));
        vehiculo.setClaseVehiculoId(rs.getInt(6));
        vehiculo.setClaseVehiculo(rs.getString(7));
        vehiculo.setTipoServicioId(rs.getInt(8));
        vehiculo.setTipoServicio(rs.getString(9));
        vehiculo.setColorId(rs.getInt(10));
        vehiculo.setColor(rs.getString(11));
        vehiculo.setModelo(rs.getInt(12));
        vehiculo.setLineaId(rs.getInt(13));
        vehiculo.setLinea(rs.getString(14));
        vehiculo.setMarcaId(rs.getInt(15));
        vehiculo.setMarca(rs.getString(16));
        vehiculo.setLicenciaTransito(rs.getString(17));
        vehiculo.setRunt(rs.getString(18));
        vehiculo.setSoat(rs.getString(19));
        vehiculo.setFechaVenSoat(rs.getDate(20));
        vehiculo.setTecnomecanica(rs.getString(21));
        vehiculo.setFechaVenTecnomecanica(rs.getDate(22));
        vehiculo.setPaisMatriculaId(rs.getInt(23));
        vehiculo.setPaisMatricula(rs.getString(24));
        vehiculo.setMunicipoMatriculaId(rs.getInt(25));
        vehiculo.setMunicipoMatricula(rs.getString(26));
        vehiculo.setDepartamentoMatriculaId(rs.getInt(27));
        vehiculo.setDepartamentoMatricula(rs.getString(28));
        vehiculo.setCiudadMatricula(rs.getString(29));
        vehiculo.setFechaMatricula(rs.getDate(30));
        vehiculo.setTipoImportacionId(rs.getInt(31));
        vehiculo.setTipoImportacion(rs.getString(32));
        vehiculo.setDocumentoImportacion(rs.getString(33));
        vehiculo.setFechaImportacion(rs.getDate(34));
        vehiculo.setPaisImportacionId(rs.getInt(35));
        vehiculo.setPaisImportacion(rs.getString(36));
        vehiculo.setTipoUsoId(rs.getInt(37));
        vehiculo.setTipoUso(rs.getString(38));
        vehiculo.setTransformado(rs.getString(39));
        vehiculo.setObservaciones(rs.getString(40));
        
        return vehiculo;
    }
    
}
