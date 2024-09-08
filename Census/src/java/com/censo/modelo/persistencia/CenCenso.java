
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
public class CenCenso {
    
    private int id;
    private Date fecha;
    private String hora;
    private int pun_id;
    private int veh_id;
    private int per_id;
    private int tper_id;
    private int usu_id;
    private int estado;
    private Timestamp fechaproceso;
    private String numero;
    private String observaciones;
    
    public static CenCenso load(ResultSet rs)throws SQLException{
        CenCenso censo = new CenCenso();
        censo.setId(rs.getInt(1));
        censo.setFecha(rs.getDate(2));
        censo.setHora(rs.getString(3));
        censo.setPun_id(rs.getInt(4));
        censo.setVeh_id(rs.getInt(5));
        censo.setPer_id(rs.getInt(6));
        censo.setTper_id(rs.getInt(7));
        censo.setUsu_id(rs.getInt(8));
        censo.setEstado(rs.getInt(9));
        censo.setFechaproceso(rs.getTimestamp(10));
        censo.setNumero(rs.getString(11));
        censo.setObservaciones(rs.getString(12));
        return censo;
    }

}
