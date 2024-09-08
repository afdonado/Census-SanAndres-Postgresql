
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
public class CenVerificacion {
    
    private int id;
    private int cen_id;
    private String verificado_runt;
    private String verificado_doc;
    private String verificado_foto;
    private String observaciones;
    private int usu_id;
    private Timestamp fechaproceso;
    private int estado;
    
    public static CenVerificacion load(ResultSet rs)throws SQLException{
        CenVerificacion verificacion = new CenVerificacion();
        verificacion.setId(rs.getInt(1));
        verificacion.setCen_id(rs.getInt(2));
        verificacion.setVerificado_runt(rs.getString(3));
        verificacion.setVerificado_doc(rs.getString(4));
        verificacion.setVerificado_foto(rs.getString(5));
        verificacion.setObservaciones(rs.getString(6));
        verificacion.setUsu_id(rs.getInt(7));
        verificacion.setFechaproceso(rs.getTimestamp(8));
        verificacion.setEstado(rs.getInt(9));
        return verificacion;
    }
    
}
