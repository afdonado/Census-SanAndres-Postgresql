package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenHistorialVerificacion;
import com.censo.modelo.persistencia.CenVerificacion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class VerificacionDao {

    public int adicionarVerificacion(Connection conex, CenVerificacion cenverificacion) {

        String sql = "INSERT INTO CEN_VERIFICACIONES (CEN_ID,VER_DOCUMENTOS,"
                + "VER_FOTOS,VER_OBSERVACIONES,USU_ID,VER_FECHAPROCESO,EVER_ID) "
                + "VALUES (?,?,?,?,?,sysdate,?)";
        try (PreparedStatement pst = conex.prepareStatement(sql, new String[]{"VER_ID"})) {
            pst.setInt(1, cenverificacion.getCen_id());
            pst.setString(2, cenverificacion.getVerificado_doc());
            pst.setString(3, cenverificacion.getVerificado_foto());
            pst.setString(4, cenverificacion.getObservaciones());
            pst.setInt(5, cenverificacion.getUsu_id());
            pst.setInt(6, cenverificacion.getEstado());
            pst.executeUpdate();
            try (ResultSet rst = pst.getGeneratedKeys()) {
                if (rst != null) {
                    if (rst.next()) {
                        return rst.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en adicionarVerificacion: " + e);
        }
        return 0;
    }

    public boolean modificarVerificacion(Connection conex, CenVerificacion cenverificacion) throws SQLException, IOException {

        String sql = "UPDATE CEN_VERIFICACIONES SET VER_DOCUMENTOS = ?,VER_FOTOS = ? ,VER_OBSERVACIONES = ?, EVER_ID = ?, USU_ID = ?, VER_FECHAPROCESO = SYSDATE WHERE VER_ID = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, cenverificacion.getVerificado_doc());
            pst.setString(2, cenverificacion.getVerificado_foto());
            pst.setString(3, cenverificacion.getObservaciones());
            pst.setInt(4, cenverificacion.getEstado());
            pst.setInt(5, cenverificacion.getUsu_id());
            pst.setInt(6, cenverificacion.getId());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error en modificarVerificacion: " + e);
        }
        return false;
    }

    public CenVerificacion ConsultarVerificacionByIdVerificacion(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_VERIFICACIONES WHERE VER_ID = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenVerificacion.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarVerificacionByIdVerificacion: " + e);
        }
        return null;
    }

    public int adicionarHistorialVerificacion(Connection conex, CenHistorialVerificacion cenhistotialverificacion) {

        String sql = "INSERT INTO CEN_HISTORIAL_VERIFICACIONES (VER_ID,EVER_ID,USU_ID,HVER_OBSERVACIONES,HVER_FECHAPROCESO) "
                + "VALUES (?,?,?,?,sysdate)";
        try (PreparedStatement pst = conex.prepareStatement(sql, new String[]{"VER_ID"})) {
            pst.setInt(1, cenhistotialverificacion.getVer_id());
            pst.setInt(2, cenhistotialverificacion.getEstado());
            pst.setInt(3, cenhistotialverificacion.getUsu_id());
            pst.setString(4, cenhistotialverificacion.getObservaciones());
            pst.executeUpdate();
            try (ResultSet rst = pst.getGeneratedKeys()) {
                if (rst != null) {
                    if (rst.next()) {
                        return rst.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en adicionarHistorialVerificacion: " + e);
        }
        return 0;
    }

    public List<HashMap<String, Object>> ListarVerificaciones(Connection conex) throws SQLException {

        List<HashMap<String, Object>> lista = new LinkedList<>();

        String sql = "SELECT CEN_ID, NUMERO, FECHA, PUNTO_ATENCION, \n"
                + "VEH_PLACA, VEH_MOTOR, VEH_CHASIS, VEH_SERIE, \n"
                + "VERIFICACION_DOC,VERIFICACION_FOTOS,\n"
                + "FECHA_PROCESO_VERIFICACION_FORMAT,ESTADO_VERIFICACION,\n"
                + "VERIFICACION_ID\n"
                + "FROM VW_CENSOS";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, Object> hash = new HashMap<>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getObject(i + 1));
                }
                lista.add(hash);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarVerificaciones: " + e);
        }
        return lista;
    }

    public List<HashMap<String, Object>> ListarVerificacionesReporte(Connection conex) throws SQLException {

        List<HashMap<String, Object>> lista = new LinkedList<>();

        String sql = "SELECT NUMERO, FECHA, HORA, PUNTO_ATENCION, OBSERVACIONES, \n"
                + "VEH_PLACA, VEH_MOTOR, VEH_CHASIS, VEH_SERIE, \n"
                + "VEH_COLOR, VEH_MARCA, VEH_LINEA, VEH_MODELO,\n"
                + "VEH_CLASE, VEH_SERVICIO, VEH_RUNT, VEH_SOAT, VEH_TECNOMEC,\n"
                + "ESTADO, USUARIO, FECHA_PROCESO, VERIFICACION_DOC, VERIFICACION_FOTOS,\n"
                + "OBSERVACIONES_VERIFICACION, FECHA_PROCESO_VERIFICACION_FORMAT,\n"
                + "USUARIO_VERIFICACION,ESTADO_VERIFICACION\n"
                + "FROM VW_CENSOS";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, Object> hash = new HashMap<>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getObject(i + 1));
                }
                lista.add(hash);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarVerificacionesReporte: " + e);
        }
        return lista;
    }

}
