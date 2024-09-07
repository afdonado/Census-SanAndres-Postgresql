
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
public class CenMarca {
    
    private int id;
    private String nombre;
    
    public static CenMarca load(ResultSet rs)throws SQLException{
        CenMarca marca = new CenMarca();
        marca.setId(rs.getInt(1));
        marca.setNombre(rs.getString(2));
        return marca;
    }
    
}
