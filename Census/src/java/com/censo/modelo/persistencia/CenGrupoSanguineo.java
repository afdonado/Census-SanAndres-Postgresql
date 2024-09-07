
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
public class CenGrupoSanguineo {
    
    private int id;
    private String descripcion;
    private String descripcion_corta;
    private int estado;
    
    public static CenGrupoSanguineo load(ResultSet rs)throws SQLException{
        CenGrupoSanguineo grupoSanguineo = new CenGrupoSanguineo();
        grupoSanguineo.setId(rs.getInt(1));
        grupoSanguineo.setDescripcion(rs.getString(2));
        grupoSanguineo.setDescripcion_corta(rs.getString(3));
        grupoSanguineo.setEstado(rs.getInt(4));
        return grupoSanguineo;
    }
    
}
