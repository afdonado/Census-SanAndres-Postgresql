
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
public class CenTipoReferencia {
    
    private int id;
    private String descripcion;
    
    public static CenTipoReferencia load(ResultSet rs)throws SQLException{
        CenTipoReferencia tipoReferencia = new CenTipoReferencia();
        tipoReferencia.setId(rs.getInt(1));
        tipoReferencia.setDescripcion(rs.getString(2));
        return tipoReferencia;
    }
    
}
