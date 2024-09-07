
package com.censo.modelo.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
public class CenHistorialVerificacion {
    
    private int id;
    private int ver_id;
    private int estado;
    private int usu_id;
    private String observaciones;
    private Timestamp fechaproceso;
    
    public static CenHistorialVerificacion load(ResultSet rs)throws SQLException{
        CenHistorialVerificacion historialVerificacion = new CenHistorialVerificacion();
        historialVerificacion.setId(rs.getInt(1));
        historialVerificacion.setVer_id(rs.getInt(2));
        historialVerificacion.setEstado(rs.getInt(3));
        historialVerificacion.setUsu_id(rs.getInt(4));
        historialVerificacion.setObservaciones(rs.getString(5));
        historialVerificacion.setFechaproceso(rs.getTimestamp(6));
        
        return historialVerificacion;
    }
    
}
