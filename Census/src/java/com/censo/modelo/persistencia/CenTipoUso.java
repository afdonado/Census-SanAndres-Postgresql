
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
public class CenTipoUso {
    
    private int id;
    private String descripcion;
    private String descripcion_corta;
    private int estado;
    
    public static CenTipoUso load(ResultSet rs)throws SQLException{
        CenTipoUso tipoUso = new CenTipoUso();
        tipoUso.setId(rs.getInt(1));
        tipoUso.setDescripcion(rs.getString(2));
        tipoUso.setDescripcion_corta(rs.getString(3));
        tipoUso.setEstado(rs.getInt(4));
        return tipoUso;
    }
    
}
