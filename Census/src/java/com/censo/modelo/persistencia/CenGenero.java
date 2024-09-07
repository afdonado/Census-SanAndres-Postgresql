
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
public class CenGenero {
    
    private int id;
    private String descripcion;
    private String descripcion_corta;
    private int estado;
    
    public static CenGenero load(ResultSet rs)throws SQLException{
        CenGenero genero = new CenGenero();
        genero.setId(rs.getInt(1));
        genero.setDescripcion(rs.getString(2));
        genero.setDescripcion_corta(rs.getString(3));
        genero.setEstado(rs.getInt(4));
        return genero;
    }
    
}
