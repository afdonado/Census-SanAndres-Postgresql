
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
public class CenTipoImportacion {
    
    private int id;
    private String descripcion;
    private String descripcion_corta;
    private int estado;
    
    public static CenTipoImportacion load(ResultSet rs)throws SQLException{
        CenTipoImportacion tipoImportacion = new CenTipoImportacion();
        tipoImportacion.setId(rs.getInt(1));
        tipoImportacion.setDescripcion(rs.getString(2));
        tipoImportacion.setDescripcion_corta(rs.getString(3));
        tipoImportacion.setEstado(rs.getInt(4));
        return tipoImportacion;
    }
}
