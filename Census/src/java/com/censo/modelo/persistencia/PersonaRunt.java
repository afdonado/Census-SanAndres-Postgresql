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
public class PersonaRunt {
    
    private int tipoDocumentoId;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombre1;
    private String nombre2;
    private String apellido1;
    private String apellido2;
    private String numeroLicencia;
    private String fechaExpedicion;
    private String fechaVencimiento;
    private int categoriaId;
    private String categoria;
    private String estadoLicencia;
    private String fechaConsulta;
    private String fuenteFallo;

    public static PersonaRunt load(ResultSet rs) throws SQLException {
        PersonaRunt personaRunt = PersonaRunt.builder()
                .tipoDocumentoId(rs.getInt(1))
                .tipoDocumento(rs.getString(2))
                .numeroDocumento(rs.getString(3))
                .nombre1(rs.getString(4))
                .nombre2(rs.getString(5))
                .apellido1(rs.getString(6))
                .apellido2(rs.getString(7))
                .numeroLicencia(rs.getString(8))
                .fechaExpedicion(rs.getString(9))
                .fechaVencimiento(rs.getString(10))
                .categoriaId(rs.getInt(11))
                .categoria(rs.getString(12))
                .estadoLicencia(rs.getString(13))
                .fechaConsulta(rs.getString(14))
                .fuenteFallo(rs.getString(15))
                .build();

        return personaRunt;
    }
}
