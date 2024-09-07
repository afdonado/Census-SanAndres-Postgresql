
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
public class CenTipoServicio {
    
    private int id;
    private String descripcion;
    private String descripcion_corta;
    private int estado;
    
    public static CenTipoServicio load(ResultSet rs)throws SQLException{
        CenTipoServicio tipoServicio = new CenTipoServicio();
        tipoServicio.setId(rs.getInt(1));
        tipoServicio.setDescripcion(rs.getString(2));
        tipoServicio.setDescripcion_corta(rs.getString(3));
        tipoServicio.setEstado(rs.getInt(4));
        return tipoServicio;
    }
    
}
