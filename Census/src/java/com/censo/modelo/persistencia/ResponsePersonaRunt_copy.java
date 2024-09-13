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
public class ResponsePersonaRunt_copy {

    private RuntVO runtVO;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class RuntVO {

        private PersonaVO personaVO;
        private String nroInscripcion;
        private String fechaInscripcion;
        private String estadoConductor;
        private String estadoPersona;
        private String tieneMultas;
        private String nroPazYSalvo;
        private List<LicenciaConduccion> licenciasConduccion;
        private List<CertificadosAptitudConduccion> certificadosAptitudConduccion;
        private List<SolicitudesConductor> solicitudesConductor;
        private String fechaConsulta;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @ToString
        public static class PersonaVO {

            private String numeroDocumento;
            private String tipoDocumento;
            private String pais;
            private Nombres nombres;

            @Getter
            @Setter
            @AllArgsConstructor
            @NoArgsConstructor
            @Builder
            @ToString
            public static class Nombres {

                private Runt runt;

                @Getter
                @Setter
                @AllArgsConstructor
                @NoArgsConstructor
                @Builder
                @ToString
                public static class Runt {

                    private String primerNombre;
                    private String segundoApellido;
                    private String tipoNombre;
                }
            }
        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @ToString
        public static class LicenciaConduccion {

            private String numeroLicencia;
            private String otExpide;
            private String fechaExpedicion;
            private String estadoLicencia;
            private String restricciones;
            private List<DetalleLicencias> detalleLicencias;

            @Getter
            @Setter
            @AllArgsConstructor
            @NoArgsConstructor
            @Builder
            @ToString
            public static class DetalleLicencias {

                private String categoria;
                private String fechaExpedicion;
                private String fechaVencimiento;
                private String fechaVencimientoExamen;
                private String categoriaAntigua;
            }

        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @ToString
        public static class CertificadosAptitudConduccion {

            private String numeroCertificado;
            private String nombreCea;
            private String fechaExpedicion;
            private String categoria;
            private String tipoCertificado;
            private String estado;
            private String nroSolicitud;
            private String fechaSolicitud;
            private String nroFupa;
            private String tramiteSolicitado;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @ToString
        public static class SolicitudesConductor {

            private String numeroSolicitud;
            private String fechaSolicitud;
            private String identificador;
            private String estado;
            private String tramitesRealizados;
            private String entidad;
        }

    }

    public static ResponsePersonaRunt_copy load(ResultSet rs) throws SQLException {

        ResponsePersonaRunt_copy personaRunt = ResponsePersonaRunt_copy.builder()
                .runtVO(ResponsePersonaRunt_copy.RuntVO.builder()
                                .personaVO(ResponsePersonaRunt_copy.RuntVO.PersonaVO.builder()
                                                .numeroDocumento(rs.getString(1))
                                                .tipoDocumento(rs.getString(2))
                                                .pais(rs.getString(3))
                                                .nombres(ResponsePersonaRunt_copy.RuntVO.PersonaVO.Nombres.builder()
                                                                .runt(ResponsePersonaRunt_copy.RuntVO.PersonaVO.Nombres.Runt.builder()
                                                                                .primerNombre(rs.getString(4))
                                                                                .segundoApellido(rs.getString(5))
                                                                                .tipoNombre(rs.getString(6))
                                                                                .build())
                                                                .build())
                                                .build())
                                .nroInscripcion(rs.getString(7))
                                .fechaInscripcion(rs.getString(8))
                                .estadoConductor(rs.getString(9))
                                .estadoPersona(rs.getString(10))
                                .tieneMultas(rs.getString(11))
                                .nroPazYSalvo(rs.getString(12))
                                .licenciasConduccion(List.of(ResponsePersonaRunt_copy.RuntVO.LicenciaConduccion.builder()
                                                .numeroLicencia(rs.getString(13))
                                                .otExpide(rs.getString(14))
                                                .fechaExpedicion(rs.getString(15))
                                                .estadoLicencia(rs.getString(16))
                                                .restricciones(rs.getString(17))
                                                .detalleLicencias(List.of(ResponsePersonaRunt_copy.RuntVO.LicenciaConduccion.DetalleLicencias.builder()
                                                                .categoria(rs.getString(18))
                                                                .fechaExpedicion(rs.getString(19))
                                                                .fechaVencimiento(rs.getString(20))
                                                                .fechaVencimientoExamen(rs.getString(21))
                                                                .categoriaAntigua(rs.getString(22))
                                                                .build()))
                                                .build()))
                                .build())
                .build();

        return personaRunt;
    }
}
