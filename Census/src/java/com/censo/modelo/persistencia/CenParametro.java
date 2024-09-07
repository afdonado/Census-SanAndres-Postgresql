
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
public class CenParametro {
    
    private int id;
    private String descripcion;
    private int estado;
    
    public static CenParametro load(ResultSet rs)throws SQLException{
        CenParametro parametro= new CenParametro();
        parametro.setId(rs.getInt(1));
        parametro.setDescripcion(rs.getString(2));
        parametro.setEstado(rs.getInt(3));
        return parametro;
    }
    
}
