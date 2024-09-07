package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenCenso;
import com.censo.servicios.Datosresponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CensoDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public int adicionarCenso(Connection conex, CenCenso cencenso) {

        try {
            pst = conex.prepareStatement("INSERT INTO CEN_CENSOS (CEN_FECHA,CEN_HORA,PUN_ID,VEH_ID,USU_ID,"
                    + "EST_ID,CEN_FECHAPROCESO,CEN_NUMERO,CEN_OBSERVACIONES) "
                    + "VALUES (?,?,?,?,?,?,SYSDATE,?,?)", new String[]{"CEN_ID"});
            pst.setDate(1, cencenso.getFecha());
            pst.setString(2, cencenso.getHora());
            pst.setInt(3, cencenso.getPun_id());
            pst.setInt(4, cencenso.getVeh_id());
            pst.setInt(5, cencenso.getUsu_id());
            pst.setInt(6, cencenso.getEstado());
            pst.setString(7, cencenso.getNumero());
            pst.setString(8, cencenso.getObservaciones());
            pst.executeUpdate();
            rst = pst.getGeneratedKeys();
            if (rst != null) {
                if (rst.next()) {
                    return rst.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en adicionarCenso: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de adicionarCenso:" + e);
            }
        }
        return 0;
    }
    
    public boolean modificarCenso(Connection conex, CenCenso cencenso) throws SQLException, IOException {

        try {
            pst = conex.prepareStatement("UPDATE CEN_CENSOS SET CEN_FECHA = ?,PUN_ID = ?,VEH_ID = ?,"
                    + "CEN_NUMERO = ?,CEN_OBSERVACIONES = ? WHERE CEN_ID = ?");
            pst.setDate(1, cencenso.getFecha());
            pst.setInt(2, cencenso.getPun_id());
            pst.setInt(3, cencenso.getVeh_id());
            pst.setString(4, cencenso.getNumero());
            pst.setString(5, cencenso.getObservaciones());
            pst.setInt(6, cencenso.getId());
            pst.executeUpdate();
            
            return true;

        } catch (SQLException e) {
            System.err.println("Error en modificarCenso");
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de modificarCenso:" + e);
            }
        }
        
        return false;
    }
    
    public CenCenso ConsultarCensoById(Connection conex, int id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_CENSOS WHERE CEN_ID = ? ");
            pst.setInt(1, id);
            rst = pst.executeQuery();
            while (rst.next()) {
                return CenCenso.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarCensoById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarCensoById:" + e);
            }
        }
        return null;
    }

    public CenCenso ConsultarCensoByNumero(Connection conex, String numero) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_CENSOS WHERE EST_ID = 1 AND CEN_NUMERO = ? ");
            pst.setString(1, numero);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenCenso.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarCensoByNumero: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarCensoByNumero:" + e);
            }
        }
        return null;
    }

    public CenCenso ConsultarCensoByIdVehiculo(Connection conex, int idvehiculo) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_CENSOS WHERE EST_ID = 1 AND VEH_ID = ? ");
            pst.setInt(1, idvehiculo);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenCenso.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarCensoByIdVehiculo: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarCensoByIdVehiculo:" + e);
            }
        }
        return null;
    }
    
    public HashMap<String, Object> ConsultarDatosCensoById(Connection conex, int id) throws SQLException {

        HashMap<String, Object> datos = new HashMap<>();

        try {
            pst = conex.prepareStatement("SELECT * FROM VW_CENSOS WHERE CEN_ID = ? ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            if (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    datos.put(rsmd.getColumnName(i + 1), rst.getObject(i + 1));
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarDatosCensoById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarDatosCensoById:" + e);
            }
        }
        return datos;
    }
    
    public List<HashMap<String, Object>> ListarCensos(Connection conex) throws SQLException {

        List<HashMap<String, Object>> lista = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT * FROM VW_CENSOS ");
            rst = pst.executeQuery();

            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, Object> hash = new HashMap<>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getObject(i + 1));
                }
                lista.add(hash);
            }
        } catch (Exception e) {
            throw new SQLException("Error en ListarCensos: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCensos:" + e);
            }
        }
        return lista;
    }

    public List<HashMap<String, Object>> ListarCensosByFecha(Connection conex, Date fechaini, Date fechafin) throws SQLException {

        List<HashMap<String, Object>> lista = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT * FROM VW_CENSOS "
                    + "WHERE TO_DATE(TO_CHAR(FECHA,'dd/MM/yyyy')) BETWEEN ? AND ? ORDER BY NUMERO");
            pst.setDate(1, fechaini);
            pst.setDate(2, fechafin);
            rst = pst.executeQuery();

            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, Object> hash = new HashMap<>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getObject(i + 1));
                }
                lista.add(hash);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarCensosByFecha: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCensosByFecha:" + e);
            }
        }
        return lista;
    }

    public List<HashMap<String, Object>> ListarCensosByFechaRegistro(Connection conex, Date fechaini, Date fechafin) throws SQLException {
        
        List<HashMap<String, Object>> lista = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT * FROM VW_CENSOS "
                    + "WHERE TO_DATE(TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy')) BETWEEN ? AND ? ORDER BY NUMERO");
            pst.setDate(1, fechaini);
            pst.setDate(2, fechafin);
            rst = pst.executeQuery();

            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, Object> hash = new HashMap<>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                }
                lista.add(hash);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarCensosByFechaRegistro: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCensosByFechaRegistro:" + e);
            }
        }
        return lista;
    }

    public Datosresponse ConsultarCensoWSByNumero(Connection conex, String numero) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT to_char(CC.CEN_FECHA,'dd/MM/yyyy') FECHA_CENSO,CV.VEH_PLACA PLACA_VEHICULO,"
                    + "CV.VEH_MOTOR, CV.VEH_CHASIS, CV.VEH_SERIE"
                    + "FROM CEN_CENSOS CC "
                    + "INNER JOIN CEN_VEHICULOS CV ON CV.VEH_ID=CC.VEH_ID "
                    + "WHERE CC.CEN_NUMERO = ? AND CC.EST_ID = 1");
            pst.setString(1, numero);
            rst = pst.executeQuery();

            while (rst.next()) {
                return Datosresponse.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarCensoWSByNumero: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarCensoWSByNumero:" + e);
            }
        }
        return null;
    }
    
}
