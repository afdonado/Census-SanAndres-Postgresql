
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
public class CenPerfil {
    
    private int id;
    private String nombre;
    private Date fechaini;
    private Date fechafin;
    private Timestamp fechaproceso;
    private int estado;
    
    public static CenPerfil load(ResultSet rs)throws SQLException{
        CenPerfil perfil= new CenPerfil();
        perfil.setId(rs.getInt(1));
        perfil.setNombre(rs.getString(2));
        perfil.setFechaini(rs.getDate(3));
        perfil.setFechafin(rs.getDate(4));
        perfil.setFechaproceso(rs.getTimestamp(5));
        perfil.setEstado(rs.getInt(6));
        return perfil;
    }
    
}
