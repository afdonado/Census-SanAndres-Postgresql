package com.censo.modelo.persistencia;

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
    private String fuenteFallo;

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
}
