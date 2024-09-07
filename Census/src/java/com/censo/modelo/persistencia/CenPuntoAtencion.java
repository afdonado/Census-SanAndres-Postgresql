
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
public class CenPuntoAtencion {
    
    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String consecutivo;
    private int estado;

    public static CenPuntoAtencion load(ResultSet rs)throws SQLException{
        CenPuntoAtencion puntoAtencion = new CenPuntoAtencion();
        puntoAtencion.setId(rs.getInt(1));
        puntoAtencion.setNombre(rs.getString(2));
        puntoAtencion.setDireccion(rs.getString(3));
        puntoAtencion.setTelefono(rs.getString(4));
        puntoAtencion.setConsecutivo(rs.getString(5));
        puntoAtencion.setEstado(rs.getInt(6));
        return puntoAtencion;
    }
    
}
