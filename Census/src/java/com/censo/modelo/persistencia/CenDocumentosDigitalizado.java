
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
public class CenDocumentosDigitalizado {
    
    private int id;
    private String nombre;
    private String ruta;
    private int tipo;
    private int referencia_id;
    private String observacion;
    private Timestamp fechaproceso;
    private int usu_id;
    
    public static CenDocumentosDigitalizado load(ResultSet rs)throws SQLException{
        CenDocumentosDigitalizado documentosDigitalizado = new CenDocumentosDigitalizado();
        documentosDigitalizado.setId(rs.getInt(1));
        documentosDigitalizado.setNombre(rs.getString(2));
        documentosDigitalizado.setRuta(rs.getString(3));
        documentosDigitalizado.setTipo(rs.getInt(4));
        documentosDigitalizado.setReferencia_id(rs.getInt(5));
        documentosDigitalizado.setObservacion(rs.getString(6));
        documentosDigitalizado.setFechaproceso(rs.getTimestamp(7));
        documentosDigitalizado.setUsu_id(rs.getInt(8));
        return documentosDigitalizado;
    }
    
}
