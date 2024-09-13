package com.censo.modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
public class ResponseVehiculoRunt {

    private String placaVehiculo;
    private String nroLicenciaTransito;
    private String estadoVehiculo;
    private String tipoServicio;
    private String claseVehiculo;
    private InformacionGeneralVehiculo informacionGeneralVehiculo;
    private DatosTecnicosVehiculo datosTecnicosVehiculo;
    private List<PolizaSoat> listPolizaSoat;
    private List<CertificadoTecnicoMecanicoGases> listCertificadoTecnicoMecanicoGases;
    private String fechaConsulta;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class InformacionGeneralVehiculo {

        private String marca;
        private String linea;
        private String modelo;
        private String color;
        private String nroSerie;
        private String nroMotor;
        private String nroChasis;
        private String nroVin;
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

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class DatosTecnicosVehiculo {

        private String capacidadCarga;
        private String pesoBrutoVehicular;
        private String capacidadPasajeros;
        private String capacidadPasajerosSentados;
        private String nroEjes;
    }

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
    public static class CertificadoTecnicoMecanicoGases {

        private String tipoRevision;
        private String fechaExpedicion;
        private String fechaVigencia;
        private String cdaExpide;
        private String vigente;
    }

    public static ResponseVehiculoRunt load(ResultSet rs) throws SQLException {
        ResponseVehiculoRunt vehiculoRunt = ResponseVehiculoRunt.builder()
                        .placaVehiculo(rs.getString(1))
                        .nroLicenciaTransito(rs.getString(2))
                        .estadoVehiculo(rs.getString(3))
                        .tipoServicio(rs.getString(4))
                        .claseVehiculo(rs.getString(5))
                        .informacionGeneralVehiculo(ResponseVehiculoRunt.InformacionGeneralVehiculo.builder()
                                .marca(rs.getString(6))
                                .linea(rs.getString(7))
                                .modelo(rs.getString(8))
                                .color(rs.getString(9))
                                .nroSerie(rs.getString(10))
                                .nroMotor(rs.getString(11))
                                .nroChasis(rs.getString(12))
                                .nroVin(rs.getString(13))
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
                                .build())
                        .datosTecnicosVehiculo(ResponseVehiculoRunt.DatosTecnicosVehiculo.builder()
                                .capacidadCarga(rs.getString(26))
                                .pesoBrutoVehicular(rs.getString(27))
                                .capacidadPasajeros(rs.getString(28))
                                .capacidadPasajerosSentados(rs.getString(29))
                                .nroEjes(rs.getString(30))
                                .build())
                        .listPolizaSoat(List.of(ResponseVehiculoRunt.PolizaSoat.builder()
                                .numeroPoliza(rs.getString(31))
                                .fechaExpedicion(rs.getString(32))
                                .fechaInicioVigencia(rs.getString(33))
                                .fechaFinVigencia(rs.getString(34))
                                .entidadSoat(rs.getString(35))
                                .estado(rs.getString(36))
                                .build()))
                        .listCertificadoTecnicoMecanicoGases(List.of(ResponseVehiculoRunt.CertificadoTecnicoMecanicoGases.builder()
                                .tipoRevision(rs.getString(37))
                                .fechaExpedicion(rs.getString(38))
                                .fechaVigencia(rs.getString(39))
                                .cdaExpide(rs.getString(40))
                                .vigente(rs.getString(41))
                                .build()))
                        .build();

        return vehiculoRunt;
    }
}
