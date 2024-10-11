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
public class ResponsePersonaRunt {

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
    private String fuenteFallo;

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

            private Runt RUNT;

            @Getter
            @Setter
            @AllArgsConstructor
            @NoArgsConstructor
            @Builder
            @ToString
            public static class Runt {

                private String primerNombre;
                private String primerApellido;
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
