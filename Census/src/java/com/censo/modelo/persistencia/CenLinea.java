
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
public class CenLinea {
    
    private int id;
    private String nombre;
    private int mar_id;

    public static CenLinea load(ResultSet rs)throws SQLException{
        CenLinea linea= new CenLinea();
        linea.setId(rs.getInt(1));
        linea.setNombre(rs.getString(2));
        linea.setMar_id(rs.getInt(3));
        return linea;
    }
    
}
