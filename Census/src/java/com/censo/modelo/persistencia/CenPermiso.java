
package com.censo.modelo.persistencia;

import java.sql.Date;
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
public class CenPermiso {
    
    private int id;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private Date fechaini;
    private Date fechafin;
    private Timestamp fechaproceso;
    private int estado;
    private int mod_id;
    private int tipo;
    
    public static CenPermiso load(ResultSet rs)throws SQLException{
        CenPermiso permisos= new CenPermiso();
        permisos.setId(rs.getInt(1));
        permisos.setNombre(rs.getString(2));
        permisos.setDescripcion(rs.getString(3));
        permisos.setUbicacion(rs.getString(4));
        permisos.setFechaini(rs.getDate(5));
        permisos.setFechafin(rs.getDate(6));
        permisos.setFechaproceso(rs.getTimestamp(7));
        permisos.setEstado(rs.getInt(8));
        permisos.setMod_id(rs.getInt(9));
        permisos.setTipo(rs.getInt(10));
        return permisos;
    }
    
}
