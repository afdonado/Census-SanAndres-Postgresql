
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
public class CenPais {
    
    private int id;
    private String nombre;

    public static CenPais load(ResultSet rs)throws SQLException{
        CenPais pais = new CenPais();
        pais.setId(rs.getInt(1));
        pais.setNombre(rs.getString(2));
        return pais;
    }
    
}
