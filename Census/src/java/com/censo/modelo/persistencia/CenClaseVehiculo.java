
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
public class CenClaseVehiculo {
    
    private int id;
    private String descripcion;
    private String descripcion_corta;
    private int estado;

    public static CenClaseVehiculo load(ResultSet rs)throws SQLException{
        CenClaseVehiculo claseVehiculo = new CenClaseVehiculo();
        claseVehiculo.setId(rs.getInt(1));
        claseVehiculo.setDescripcion(rs.getString(2));
        claseVehiculo.setDescripcion_corta(rs.getString(3));
        claseVehiculo.setEstado(rs.getInt(4));
        return claseVehiculo;
    }
}
