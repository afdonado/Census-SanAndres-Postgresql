
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
public class CenPerfilUsuario {
    
    private int id;
    private int pef_id;
    private int usu_id;
    private Date fechaini;
    private Date fechafin;
    private Timestamp fechaproceso;
    private int estado;
    
    public static CenPerfilUsuario load(ResultSet rs)throws SQLException{
        CenPerfilUsuario perfilUsuario= new CenPerfilUsuario();
        perfilUsuario.setId(rs.getInt(1));
        perfilUsuario.setPef_id(rs.getInt(2));
        perfilUsuario.setUsu_id(rs.getInt(3));
        perfilUsuario.setFechaini(rs.getDate(4));
        perfilUsuario.setFechafin(rs.getDate(5));
        perfilUsuario.setFechaproceso(rs.getTimestamp(6));
        perfilUsuario.setEstado(rs.getInt(7));
        return perfilUsuario;
    }
    
}
