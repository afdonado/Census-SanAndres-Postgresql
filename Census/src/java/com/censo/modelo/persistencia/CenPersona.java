
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
public class CenPersona {
        
    private int id;
    private int tipodocumento;
    private String documento;
    private String nombre1;
    private String nombre2;
    private String apellido1;
    private String apellido2;
    private java.sql.Date fechanacimiento;
    private int genero;
    private String direccion;
    private int mun_id;
    private String telefono;
    private String mail;
    private int gruposanguineo;
    private String numerolicenciaconduccion;
    private Date fechaexp;
    private Date fechaven;
    private int categorialicencia;
    private Timestamp fechaproceso;
    private int usu_id;    
    private String licenciaconduccion;
    
    public static CenPersona load(ResultSet rs)throws SQLException{
        CenPersona persona= new CenPersona();
        persona.setId(rs.getInt(1));
        persona.setTipodocumento(rs.getInt(2));
        persona.setDocumento(rs.getString(3));
        persona.setNombre1(rs.getString(4));
        persona.setNombre2(rs.getString(5));
        persona.setApellido1(rs.getString(6));
        persona.setApellido2(rs.getString(7));
        persona.setFechanacimiento(rs.getDate(8));
        persona.setGenero(rs.getInt(9));
        persona.setDireccion(rs.getString(10));
        persona.setMun_id(rs.getInt(11));
        persona.setTelefono(rs.getString(12));
        persona.setMail(rs.getString(13));
        persona.setGruposanguineo(rs.getInt(14));
        persona.setNumerolicenciaconduccion(rs.getString(15));
        persona.setFechaexp(rs.getDate(16));
        persona.setFechaven(rs.getDate(17));
        persona.setCategorialicencia(rs.getInt(18));
        persona.setFechaproceso(rs.getTimestamp(19));
        persona.setUsu_id(rs.getInt(20));
        persona.setLicenciaconduccion(rs.getString(21));
        return persona;
    }
    
}
