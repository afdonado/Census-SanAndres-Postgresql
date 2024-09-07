
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
public class CenTipoDocumento {
    
    private int id;
    private String descripcion;
    private String descripcion_corta;
    private int estado;

    public static CenTipoDocumento load(ResultSet rs)throws SQLException{
        CenTipoDocumento tipoDocumento = new CenTipoDocumento();
        tipoDocumento.setId(rs.getInt(1));
        tipoDocumento.setDescripcion(rs.getString(2));
        tipoDocumento.setDescripcion_corta(rs.getString(3));
        tipoDocumento.setEstado(rs.getInt(4));
        return tipoDocumento;
    }
    
}
