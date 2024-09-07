
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
public class CenEstado {
    
    private int id;
    private String descripcion;

    public static CenEstado load(ResultSet rs)throws SQLException{
        CenEstado estado = new CenEstado();
        estado.setId(rs.getInt(1));
        estado.setDescripcion(rs.getString(2));
        return estado;
    }
    
}
