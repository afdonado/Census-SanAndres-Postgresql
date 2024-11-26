package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenCenso;
import com.censo.servicios.Datosresponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CensoDao {

    public int adicionarCenso(Connection conex, CenCenso cencenso) {

        String sql = "INSERT INTO CEN_CENSOS (CEN_FECHA,CEN_HORA,PUN_ID,VEH_ID,USU_ID,"
                + "EST_ID,CEN_FECHAPROCESO,CEN_NUMERO,CEN_OBSERVACIONES) "
                + "VALUES (?,?,?,?,?,?,SYSDATE,?,?)";
        try (PreparedStatement pst = conex.prepareStatement(sql, new String[]{"CEN_ID"})) {
            pst.setDate(1, cencenso.getFecha());
            pst.setString(2, cencenso.getHora());
            pst.setInt(3, cencenso.getPun_id());
            pst.setInt(4, cencenso.getVeh_id());
            pst.setInt(5, cencenso.getUsu_id());
            pst.setInt(6, cencenso.getEstado());
            pst.setString(7, cencenso.getNumero());
            pst.setString(8, cencenso.getObservaciones());
            pst.executeUpdate();
            try (ResultSet rst = pst.getGeneratedKeys()) {
                if (rst != null) {
                    if (rst.next()) {
                        return rst.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en adicionarCenso: " + e);
        }
        return 0;
    }

    public boolean modificarCenso(Connection conex, CenCenso cencenso) throws SQLException, IOException {

        String sql = "UPDATE CEN_CENSOS SET CEN_FECHA = ?,PUN_ID = ?,VEH_ID = ?,"
                + "CEN_NUMERO = ?,CEN_OBSERVACIONES = ? WHERE CEN_ID = ?";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {

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
        }
        return false;
    }

    public CenCenso ConsultarCensoById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_CENSOS WHERE CEN_ID = ?";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenCenso.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarCensoById: " + e);
        }
        return null;
    }

    public CenCenso ConsultarCensoByNumero(Connection conex, String numero) throws SQLException {

        String sql = "SELECT * FROM CEN_CENSOS WHERE EST_ID = 1 AND CEN_NUMERO = ?";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {

            pst.setString(1, numero);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenCenso.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarCensoByNumero: " + e);
        }
        return null;
    }

    public CenCenso ConsultarCensoByIdVehiculo(Connection conex, int idvehiculo) throws SQLException {
        String sql = "SELECT * FROM CEN_CENSOS WHERE EST_ID = 1 AND VEH_ID = ?";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, idvehiculo);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenCenso.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarCensoByIdVehiculo: " + e);
        }
        return null;
    }

    public HashMap<String, Object> ConsultarDatosCensoById(Connection conex, int id) throws SQLException {

        HashMap<String, Object> datos = new HashMap<>();

        String sql = "SELECT * FROM VW_CENSOS WHERE CEN_ID = ?";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                if (rst.next()) {
                    ResultSetMetaData rsmd = rst.getMetaData();
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        datos.put(rsmd.getColumnName(i + 1), rst.getObject(i + 1));
                    }
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarDatosCensoById: " + e);
        }
        return datos;
    }

    public List<HashMap<String, Object>> ListarCensos(Connection conex) throws SQLException {

        List<HashMap<String, Object>> lista = new LinkedList<>();

        String sql = "SELECT CEN_ID, NUMERO, FECHA, HORA, PUNTO_ATENCION, \n"
                + "ESTADO, USUARIO, FECHA_PROCESO_FORMAT, FECHA_PROCESO_HORA, DOCUMENTO_PDF,\n"
                + "VEH_PLACA, VEH_MOTOR, VEH_CHASIS, VEH_SERIE \n"
                + "FROM VW_CENSOS";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {

            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    ResultSetMetaData rsmd = rst.getMetaData();
                    HashMap<String, Object> hash = new HashMap<>();
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        hash.put(rsmd.getColumnName(i + 1), rst.getObject(i + 1));
                    }
                    lista.add(hash);
                }
            }
        } catch (Exception e) {
            throw new SQLException("Error en ListarCensos: " + e);
        }
        return lista;
    }

    public List<HashMap<String, Object>> listarCensosPaginados(Connection conex, int start, int length, String orderBy, String orderDirection) throws SQLException {

        List<HashMap<String, Object>> lista = new LinkedList<>();

        String sql = "SELECT CEN_ID, NUMERO, FECHA, HORA, PUNTO_ATENCION, "
                + "ESTADO, USUARIO, FECHA_PROCESO_FORMAT, FECHA_PROCESO_HORA, DOCUMENTO_PDF, "
                + "VEH_PLACA, VEH_MOTOR, VEH_CHASIS, VEH_SERIE "
                + "FROM VW_CENSOS ORDER BY " + orderBy + " " + orderDirection + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, start);
            pst.setInt(2, length);

            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    ResultSetMetaData rsmd = rst.getMetaData();
                    HashMap<String, Object> hash = new HashMap<>();
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        hash.put(rsmd.getColumnName(i + 1), rst.getObject(i + 1));
                    }
                    lista.add(hash);
                }
            }
        } catch (Exception e) {
            throw new SQLException("Error en listarCensosPaginados: " + e);
        }
        return lista;
    }

    public List<HashMap<String, Object>> listarCensosPaginadosFiltrados(Connection conex, int start, int length, String searchValue, String orderBy, String orderDirection) throws SQLException {

        List<HashMap<String, Object>> lista = new LinkedList<>();

        String sql = "SELECT CEN_ID, NUMERO, FECHA, HORA, PUNTO_ATENCION, "
                + "ESTADO, USUARIO, FECHA_PROCESO_FORMAT, FECHA_PROCESO_HORA, DOCUMENTO_PDF, "
                + "VEH_PLACA, VEH_MOTOR, VEH_CHASIS, VEH_SERIE "
                + "FROM VW_CENSOS WHERE (NUMERO LIKE ? OR FECHA LIKE ? OR HORA LIKE ? OR PUNTO_ATENCION LIKE ? OR "
                + "ESTADO LIKE ? OR USUARIO LIKE ? OR FECHA_PROCESO_FORMAT LIKE ? OR FECHA_PROCESO_HORA LIKE ? OR DOCUMENTO_PDF LIKE ? OR "
                + "VEH_PLACA LIKE ? OR VEH_MOTOR LIKE ? OR VEH_CHASIS LIKE ? OR VEH_SERIE LIKE ?) "
                + "ORDER BY " + orderBy + " " + orderDirection + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            String searchPattern = "%" + searchValue + "%";
            pst.setString(1, searchPattern);
            pst.setString(2, searchPattern);
            pst.setString(3, searchPattern);
            pst.setString(4, searchPattern);
            pst.setString(5, searchPattern);
            pst.setString(6, searchPattern);
            pst.setString(7, searchPattern);
            pst.setString(8, searchPattern);
            pst.setString(9, searchPattern);
            pst.setString(10, searchPattern);
            pst.setString(11, searchPattern);
            pst.setString(12, searchPattern);
            pst.setString(13, searchPattern);
            pst.setInt(14, start);
            pst.setInt(15, length);

            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    ResultSetMetaData rsmd = rst.getMetaData();
                    HashMap<String, Object> hash = new HashMap<>();
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        hash.put(rsmd.getColumnName(i + 1), rst.getObject(i + 1));
                    }
                    lista.add(hash);
                }
            }
        } catch (Exception e) {
            throw new SQLException("Error en listarCensosPaginados: " + e);
        }
        return lista;
    }
    
    public int contarCensos(Connection conex) throws SQLException {
        String sql = "SELECT COUNT(*) FROM VW_CENSOS";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            if (rst.next()) {
                return rst.getInt(1);
            }
        }
        return 0;
    }

    public int contarCensosFiltrados(Connection conex, String searchValue) throws SQLException {
        String sql = "SELECT COUNT(*) FROM VW_CENSOS "
                + "WHERE (NUMERO LIKE ? OR FECHA LIKE ? OR HORA LIKE ? OR PUNTO_ATENCION LIKE ? OR "
                + "ESTADO LIKE ? OR USUARIO LIKE ? OR FECHA_PROCESO_FORMAT LIKE ? OR FECHA_PROCESO_HORA LIKE ? OR DOCUMENTO_PDF LIKE ? OR "
                + "VEH_PLACA LIKE ? OR VEH_MOTOR LIKE ? OR VEH_CHASIS LIKE ? OR VEH_SERIE LIKE ?)";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            String searchPattern = "%" + searchValue + "%";
            pst.setString(1, searchPattern);
            pst.setString(2, searchPattern);
            pst.setString(3, searchPattern);
            pst.setString(4, searchPattern);
            pst.setString(5, searchPattern);
            pst.setString(6, searchPattern);
            pst.setString(7, searchPattern);
            pst.setString(8, searchPattern);
            pst.setString(9, searchPattern);
            pst.setString(10, searchPattern);
            pst.setString(11, searchPattern);
            pst.setString(12, searchPattern);
            pst.setString(13, searchPattern);
            try (ResultSet rst = pst.executeQuery()) {
                if (rst.next()) {
                    return rst.getInt(1);
                }
            }
        }
        return 0;
    }

    public Datosresponse ConsultarCensoWSByNumero(Connection conex, String numero) throws SQLException {

        String sql = "SELECT to_char(CC.CEN_FECHA,'dd/MM/yyyy') FECHA_CENSO,CV.VEH_PLACA PLACA_VEHICULO,"
                + "CV.VEH_MOTOR, CV.VEH_CHASIS, CV.VEH_SERIE"
                + "FROM CEN_CENSOS CC "
                + "INNER JOIN CEN_VEHICULOS CV ON CV.VEH_ID=CC.VEH_ID "
                + "WHERE CC.CEN_NUMERO = ? AND CC.EST_ID = 1";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, numero);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return Datosresponse.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarCensoWSByNumero: " + e);
        }
        return null;
    }

}
