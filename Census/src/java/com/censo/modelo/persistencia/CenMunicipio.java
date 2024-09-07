
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
public class CenMunicipio {
    
    private int id;
    private String nombre;
    private int dep_id;
    
    public static CenMunicipio load(ResultSet rs)throws SQLException{
        CenMunicipio municipio= new CenMunicipio();
        municipio.setId(rs.getInt(1));
        municipio.setNombre(rs.getString(2));
        municipio.setDep_id(rs.getInt(3));
        return municipio;
    }
    
}
