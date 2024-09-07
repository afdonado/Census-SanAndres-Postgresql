
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
public class CenCategoriaLicencia {
    
    private int id;
    private String descripcion;
    private String descripcion_corta;
    private int estado;

    public static CenCategoriaLicencia load(ResultSet rs)throws SQLException{
        CenCategoriaLicencia cenCategoriaLicencia = new CenCategoriaLicencia();
        cenCategoriaLicencia.setId(rs.getInt(1));
        cenCategoriaLicencia.setDescripcion(rs.getString(2));
        cenCategoriaLicencia.setDescripcion_corta(rs.getString(3));
        cenCategoriaLicencia.setEstado(rs.getInt(4));
        return cenCategoriaLicencia;
    }
}
