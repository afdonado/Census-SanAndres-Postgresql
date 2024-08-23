package com.censo.modelo.dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Conexion {

     public Connection conectar() throws SQLException {
    
        InitialContext initContext;
        Connection conex = null;
        String jdbcName = "oracle";
        try {
            initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/"+ jdbcName);
            conex = ds.getConnection();
        } catch(NamingException ex){
            System.err.println("Error: ".concat(ex.getMessage()));
        } catch(SQLException ex){
            System.err.println("Error: ".concat(ex.getMessage()));
        } catch(Exception ex){
            System.err.println("Error: ".concat(ex.getMessage()));
        }
        
        if((conex == null) || conex.isClosed()){
            System.out.println("No hay conexion a la BD");
            throw new SQLException("No hay conexion a la bd ".concat(jdbcName));
        }
        return conex;
    }

}
