
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
public class CenModulo {
    
    private int id;
    private String nombre;
    private int estado;
    private int orden;
    private String icono;
    
    public static CenModulo load(ResultSet rs)throws SQLException{
        CenModulo cenModulo = new CenModulo();
        cenModulo.setId(rs.getInt(1));
        cenModulo.setNombre(rs.getString(2));
        cenModulo.setEstado(rs.getInt(3));
        cenModulo.setOrden(rs.getInt(4));
        cenModulo.setIcono(rs.getString(5));
        return cenModulo;
    }

}
