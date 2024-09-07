
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
public class CenDepartamento {
    
    private int id;
    private String nombre;

    public static CenDepartamento load(ResultSet rs)throws SQLException{
        CenDepartamento departamento= new CenDepartamento();
        departamento.setId(rs.getInt(1));
        departamento.setNombre(rs.getString(2));
        return departamento;
    }
}
