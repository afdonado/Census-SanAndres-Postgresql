
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
public class CenTipoPersona {
    
    private int id;
    private String descripcion;
    private String descripcion_corta;
    private int estado;
    
    public static CenTipoPersona load(ResultSet rs)throws SQLException{
        CenTipoPersona tipoPersona = new CenTipoPersona();
        tipoPersona.setId(rs.getInt(1));
        tipoPersona.setDescripcion(rs.getString(2));
        tipoPersona.setDescripcion_corta(rs.getString(3));
        tipoPersona.setEstado(rs.getInt(4));
        return tipoPersona;
    }
    
}
