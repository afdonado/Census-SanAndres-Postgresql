
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
public class CenPersonaVehiculo {
    
    private int id;
    private int tper_id;
    private int per_id;
    private int veh_id;
    private Date fechaInicio;
    private Date fechaFinal;
    private int usu_id;
    private int estado;
    private Timestamp fechaProceso;
    
    public static CenPersonaVehiculo load(ResultSet rs)throws SQLException{
        CenPersonaVehiculo personaVehiculo= new CenPersonaVehiculo();
        personaVehiculo.setId(rs.getInt(1));
        personaVehiculo.setTper_id(rs.getInt(2));
        personaVehiculo.setPer_id(rs.getInt(3));
        personaVehiculo.setVeh_id(rs.getInt(4));
        personaVehiculo.setFechaInicio(rs.getDate(5));
        personaVehiculo.setFechaFinal(rs.getDate(6));
        personaVehiculo.setUsu_id(rs.getInt(7));
        personaVehiculo.setEstado(rs.getInt(8));
        personaVehiculo.setFechaProceso(rs.getTimestamp(9));        
        return personaVehiculo;
    }
    
}
