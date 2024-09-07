
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
public class CenColor {
    
    private int id;
    private String nombre;

    public static CenColor load(ResultSet rs)throws SQLException{
        CenColor color = new CenColor();
        color.setId(rs.getInt(1));
        color.setNombre(rs.getString(2));
        return color;
    }
    
}
