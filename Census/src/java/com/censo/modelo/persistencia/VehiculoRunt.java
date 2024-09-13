package com.censo.modelo.persistencia;

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
public class VehiculoRunt {

    private String placa;
    private String licenciaTransito;
    private String estado;
    private String tipoServicio;
    private String claseVehiculo;
    private String marca;
    private String linea;
    private String modelo;
    private String color;
    private String serie;
    private String motor;
    private String chasis;
    private String vin;
    private String cilindraje;
    private String tipoCarroceria;
    private String tipoCombustible;
    private String fechaMatriculaInicial;
    private String autoridadTransito;
    private String gravamenesPropiedad;
    private String clasicoAntiguo;
    private String repotenciado;
    private String regrabacionMotor;
    private String regrabacionChasis;
    private String regrabacionSerie;
    private String regrabacionVin;
    private String capacidadCarga;
    private String pesoBrutoVehicular;
    private String capacidadPasajeros;
    private String capacidadPasajerosSentados;
    private String nroEjes;
    private PolizaSoat polizaSoat;
    private TecnicoMecanico tecnicoMecanico;
    private String fechaConsulta;
    private String numeroDocumento;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class PolizaSoat {

        private String numeroPoliza;
        private String fechaExpedicion;
        private String fechaInicioVigencia;
        private String fechaFinVigencia;
        private String entidadSoat;
        private String estado;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class TecnicoMecanico {

        private String tipoRevision;
        private String fechaExpedicion;
        private String fechaVigencia;
        private String cdaExpide;
        private String vigente;
    }

    public static VehiculoRunt load(ResultSet rs) throws SQLException {
        VehiculoRunt vehiculoRunt = VehiculoRunt.builder()
                .placa(rs.getString(1))
                .licenciaTransito(rs.getString(2))
                .estado(rs.getString(3))
                .tipoServicio(rs.getString(4))
                .claseVehiculo(rs.getString(5))
                .marca(rs.getString(6))
                .linea(rs.getString(7))
                .modelo(rs.getString(8))
                .color(rs.getString(9))
                .serie(rs.getString(10))
                .motor(rs.getString(11))
                .chasis(rs.getString(12))
                .vin(rs.getString(13))
                .cilindraje(rs.getString(14))
                .tipoCarroceria(rs.getString(15))
                .tipoCombustible(rs.getString(16))
                .fechaMatriculaInicial(rs.getString(17))
                .autoridadTransito(rs.getString(18))
                .gravamenesPropiedad(rs.getString(19))
                .clasicoAntiguo(rs.getString(20))
                .repotenciado(rs.getString(21))
                .regrabacionMotor(rs.getString(22))
                .regrabacionChasis(rs.getString(23))
                .regrabacionSerie(rs.getString(24))
                .regrabacionVin(rs.getString(25))
                .capacidadCarga(rs.getString(26))
                .pesoBrutoVehicular(rs.getString(27))
                .capacidadPasajeros(rs.getString(28))
                .capacidadPasajerosSentados(rs.getString(29))
                .nroEjes(rs.getString(30))
                .polizaSoat(
                        VehiculoRunt.PolizaSoat.builder()
                                .numeroPoliza(rs.getString(31))
                                .fechaExpedicion(rs.getString(32))
                                .fechaInicioVigencia(rs.getString(33))
                                .fechaFinVigencia(rs.getString(34))
                                .entidadSoat(rs.getString(35))
                                .estado(rs.getString(36))
                                .build())
                .tecnicoMecanico(
                        VehiculoRunt.TecnicoMecanico.builder()
                                .tipoRevision(rs.getString(37))
                                .fechaExpedicion(rs.getString(38))
                                .fechaVigencia(rs.getString(39))
                                .cdaExpide(rs.getString(40))
                                .vigente(rs.getString(41))
                                .build())
                .fechaConsulta(rs.getString(42))
                .numeroDocumento(rs.getString(43))
                .build();

        return vehiculoRunt;
    }
}
