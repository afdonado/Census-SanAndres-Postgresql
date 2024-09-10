
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
public class Persona2017 {
        
    private int id;
    private int tipoDocumentoId;
    private String tipoDocumento;
    private String tipoDocumentoCorto;
    private String documento;
    private String nombre1;
    private String nombre2;
    private String apellido1;
    private String apellido2;
    private Date fechaNacimiento;
    private int generoId;
    private String genero;
    private String generoCorto;
    private String direccion;
    private int municipioId;
    private String municipio;
    private int departamentoId;
    private String departamento;
    private String telefono;
    private String email;
    private int grupoSanguineoId;
    private String grupoSanguineo;
    private String numeroLicenciaConduccion;
    private Date fechaExpedicion;
    private Date fechaVencimiento;
    private int categoriaLicenciaId;
    private String categoriaLicencia;
    
    public static Persona2017 load(ResultSet rs)throws SQLException{
        Persona2017 persona= new Persona2017();
        persona.setId(rs.getInt(1));
        persona.setTipoDocumentoId(rs.getInt(2));
        persona.setTipoDocumento(rs.getString(3));
        persona.setTipoDocumentoCorto(rs.getString(4));
        persona.setDocumento(rs.getString(5));
        persona.setNombre1(rs.getString(6));
        persona.setNombre2(rs.getString(7));
        persona.setApellido1(rs.getString(8));
        persona.setApellido2(rs.getString(9));
        persona.setFechaNacimiento(rs.getDate(10));
        persona.setGeneroId(rs.getInt(11));
        persona.setGenero(rs.getString(12));
        persona.setGeneroCorto(rs.getString(13));
        persona.setDireccion(rs.getString(14));
        persona.setMunicipioId(rs.getInt(15));
        persona.setMunicipio(rs.getString(16));
        persona.setDepartamentoId(rs.getInt(17));
        persona.setDepartamento(rs.getString(18));
        persona.setTelefono(rs.getString(19));
        persona.setEmail(rs.getString(20));
        persona.setGrupoSanguineoId(rs.getInt(21));
        persona.setGrupoSanguineo(rs.getString(22));
        persona.setNumeroLicenciaConduccion(rs.getString(23));
        persona.setFechaExpedicion(rs.getDate(24));
        persona.setFechaVencimiento(rs.getDate(25));
        persona.setCategoriaLicenciaId(rs.getInt(26));
        persona.setCategoriaLicencia(rs.getString(27));
        return persona;
    }
    
}
