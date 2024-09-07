
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
public class CenEstadoVerificacion {
    
    private int id;
    private String descripcion;

    public static CenEstadoVerificacion load(ResultSet rs)throws SQLException{
        CenEstadoVerificacion estadoVerificacion = new CenEstadoVerificacion();
        estadoVerificacion.setId(rs.getInt(1));
        estadoVerificacion.setDescripcion(rs.getString(2));
        return estadoVerificacion;
    }
    
}
