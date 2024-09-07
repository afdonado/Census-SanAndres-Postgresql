
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
public class CenUsuario {
    
    private int id;
    private String nombre;
    private String password;
    private Date fechaini;
    private Date fechafin;
    private int estado;
    private Timestamp fechaproceso;
    private int tipodocumento;
    private String numerodocumento;
    
    public static CenUsuario load(ResultSet rs)throws SQLException{
        CenUsuario usuario= new CenUsuario();
        usuario.setId(rs.getInt(1));
        usuario.setNombre(rs.getString(2));
        usuario.setPassword(rs.getString(3));
        usuario.setFechaini(rs.getDate(4));
        usuario.setFechafin(rs.getDate(5));
        usuario.setEstado(rs.getInt(6));
        usuario.setFechaproceso(rs.getTimestamp(7));
        usuario.setTipodocumento(rs.getInt(8));
        usuario.setNumerodocumento(rs.getString(9));
        return usuario;
    }

}
