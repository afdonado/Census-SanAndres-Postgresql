package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenVehiculo;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class VehiculoDao {

    public int adicionarVehiculo(Connection conex, CenVehiculo cenvehiculo) {

        String sql = "INSERT INTO CEN_VEHICULOS (VEH_PLACA,VEH_CHASIS,VEH_SERIE,VEH_MOTOR,VEH_CLASE,VEH_SERVICIO,"
                + "COL_ID,VEH_MODELO,LIN_ID,VEH_LICTRANSITO,VEH_RUNT,VEH_SOAT,VEH_FECVENSOAT,VEH_TECNOMEC,VEH_FECVENTECNO,PAI_ID,MUN_ID,"
                + "VEH_TIPODOCUMENTOIMP,VEH_DOCUMENTOIMP,VEH_FECHAIMP,PAI_ID_ORIGEN,VEH_TIPOUSO,VEH_TRANSFORMADO,"
                + "USU_ID,EST_ID,VEH_FECHAPROCESO,VEH_FECMATRI) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?)";
        try (PreparedStatement pst = conex.prepareStatement(sql, new String[]{"VEH_ID"})) {
            pst.setString(1, cenvehiculo.getPlaca_veh());
            pst.setString(2, cenvehiculo.getChasis());
            pst.setString(3, cenvehiculo.getSerie());
            pst.setString(4, cenvehiculo.getMotor());
            pst.setInt(5, cenvehiculo.getClase_veh());
            pst.setInt(6, cenvehiculo.getTipo_servicio());
            pst.setInt(7, cenvehiculo.getCol_id());
            pst.setInt(8, cenvehiculo.getModelo());
            pst.setInt(9, cenvehiculo.getLin_id());
            pst.setString(10, cenvehiculo.getLicencia_transito());
            pst.setString(11, cenvehiculo.getRunt());
            pst.setString(12, cenvehiculo.getSoat());
            pst.setDate(13, cenvehiculo.getFechaven_soat());
            pst.setString(14, cenvehiculo.getTecno_mecanica());
            pst.setDate(15, cenvehiculo.getFechaven_tecno());
            pst.setInt(16, cenvehiculo.getPai_id_matricula());
            pst.setInt(17, cenvehiculo.getMun_id_matricula());
            pst.setInt(18, cenvehiculo.getTipodoc_importacion());
            pst.setString(19, cenvehiculo.getDoc_importacion());
            pst.setDate(20, cenvehiculo.getFecha_importacion());
            pst.setInt(21, cenvehiculo.getPai_id_origen());
            pst.setInt(22, cenvehiculo.getTipo_uso());
            pst.setString(23, cenvehiculo.getTransformado());
            pst.setInt(24, cenvehiculo.getUsu_id());
            pst.setInt(25, cenvehiculo.getEstado());
            pst.setDate(26, cenvehiculo.getFecha_matricula());
            pst.executeUpdate();
            try (ResultSet rst = pst.getGeneratedKeys()) {
                if (rst != null) {
                    if (rst.next()) {
                        return rst.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en adicionarVehiculo: " + e);
        }
        return 0;
    }

    public boolean modificarVehiculo(Connection conex, CenVehiculo cenvehiculo) throws SQLException, IOException {

        String sql = "UPDATE CEN_VEHICULOS SET VEH_PLACA = ?,VEH_CHASIS = ?,VEH_SERIE = ?,"
                + "VEH_MOTOR = ?,VEH_CLASE = ?,VEH_SERVICIO = ?,COL_ID = ?,VEH_MODELO = ?,LIN_ID = ?, "
                + "VEH_LICTRANSITO = ?,VEH_RUNT = ?,VEH_SOAT = ?,VEH_FECVENSOAT = ?,VEH_TECNOMEC = ?,"
                + "VEH_FECVENTECNO = ?,PAI_ID = ?,MUN_ID = ?, VEH_TIPODOCUMENTOIMP = ?,"
                + "VEH_DOCUMENTOIMP = ?,VEH_FECHAIMP = ?, PAI_ID_ORIGEN = ?, "
                + "VEH_TIPOUSO = ?, VEH_TRANSFORMADO = ?, EST_ID = ?, VEH_FECMATRI = ? WHERE VEH_ID = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, cenvehiculo.getPlaca_veh());
            pst.setString(2, cenvehiculo.getChasis());
            pst.setString(3, cenvehiculo.getSerie());
            pst.setString(4, cenvehiculo.getMotor());
            pst.setInt(5, cenvehiculo.getClase_veh());
            pst.setInt(6, cenvehiculo.getTipo_servicio());
            pst.setInt(7, cenvehiculo.getCol_id());
            pst.setInt(8, cenvehiculo.getModelo());
            pst.setInt(9, cenvehiculo.getLin_id());
            pst.setString(10, cenvehiculo.getLicencia_transito());
            pst.setString(11, cenvehiculo.getRunt());
            pst.setString(12, cenvehiculo.getSoat());
            pst.setDate(13, cenvehiculo.getFechaven_soat());
            pst.setString(14, cenvehiculo.getTecno_mecanica());
            pst.setDate(15, cenvehiculo.getFechaven_tecno());
            pst.setInt(16, cenvehiculo.getPai_id_matricula());
            pst.setInt(17, cenvehiculo.getMun_id_matricula());
            pst.setInt(18, cenvehiculo.getTipodoc_importacion());
            pst.setString(19, cenvehiculo.getDoc_importacion());
            pst.setDate(20, cenvehiculo.getFecha_importacion());
            pst.setInt(21, cenvehiculo.getPai_id_origen());
            pst.setInt(22, cenvehiculo.getTipo_uso());
            pst.setString(23, cenvehiculo.getTransformado());
            pst.setInt(24, cenvehiculo.getEstado());
            pst.setDate(25, cenvehiculo.getFecha_matricula());
            pst.setInt(26, cenvehiculo.getId());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error en modificarVehiculo: " + e);
        }
        return false;
    }

    public CenVehiculo ConsultarVehiculoByReferencia(Connection conex, int tipoRef, String valorReferencia) throws SQLException {

        String sql = "";
        if (tipoRef == 1) {
            sql = "SELECT * FROM CEN_VEHICULOS WHERE VEH_PLACA = ? ";
        }
        if (tipoRef == 2) {
            sql = "SELECT * FROM CEN_VEHICULOS WHERE VEH_MOTOR = ? ";
        }
        if (tipoRef == 3) {
            sql = "SELECT * FROM CEN_VEHICULOS WHERE VEH_CHASIS = ? ";
        }
        if (tipoRef == 4) {
            sql = "SELECT * FROM CEN_VEHICULOS WHERE VEH_SERIE = ? ";
        }
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, valorReferencia);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenVehiculo.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarVehiculoByReferencia: " + e);
        }
        return null;
    }

    public CenVehiculo ConsultarVehiculoById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_VEHICULOS WHERE VEH_ID = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenVehiculo.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarVehiculoById: " + e);
        }
        return null;
    }

    public List ListarVehiculosByPersona(Connection conex, int tipodoc, String documento) throws SQLException {

        List<HashMap> listaDatosVehiculo = new LinkedList<>();

        String sql = "SELECT VP.VEH_ID, VP.VEH_PLACA, VP.VEH_MOTOR, VP.VEH_CHASIS, VP.VEH_SERIE,VP.MARCA,VP.LINEA "
                + "FROM VW_VEHICULOS_PERSONAS VP WHERE VP.PER_TIPODOC = ? AND VP.PER_DOCUMENTO=? "
                + "GROUP BY VP.VEH_ID, VP.VEH_PLACA, VP.VEH_MOTOR, VP.VEH_CHASIS, VP.VEH_SERIE,VP.MARCA,VP.LINEA ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, tipodoc);
            pst.setString(2, documento);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    ResultSetMetaData rsmd = rst.getMetaData();
                    HashMap<String, String> hash = new HashMap<>();
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                    }
                    listaDatosVehiculo.add(hash);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarVehiculosByPersona: " + e);
        }
        return listaDatosVehiculo;
    }

    public HashMap<String, Object> ConsultarDatosVehiculoById(Connection conex, int id) throws SQLException {

        HashMap<String, Object> datos = new HashMap<>();

        String sql = "SELECT * FROM VW_VEHICULOS WHERE VEH_ID = ? ";
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
            throw new SQLException("Error en ConsultarDatosVehiculoById: " + e);
        }
        return datos;
    }

    public List<HashMap<String, Object>> ListarVehiculosOld(Connection conex) throws SQLException {

        List<HashMap<String, Object>> lista = new LinkedList<>();

        String sql = "SELECT VEH_ID, VEH_PLACA, VEH_MOTOR, VEH_CHASIS, VEH_SERIE, MARCA, LINEA FROM VW_VEHICULOS";
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
            throw new SQLException("Error en ListarVehiculos: " + e);
        }
        return lista;
    }

    public List<HashMap<String, Object>> listarVehiculosPaginados(Connection conex, int start, int length, String orderBy, String orderDirection) throws SQLException {
        List<HashMap<String, Object>> lista = new ArrayList<>();

        String sql = "SELECT VEH_ID, VEH_PLACA, VEH_MOTOR, VEH_CHASIS, VEH_SERIE, MARCA, LINEA "
                + "FROM VW_VEHICULOS ORDER BY " + orderBy + " " + orderDirection + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

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
        }

        return lista;
    }

    public List<HashMap<String, Object>> listarVehiculosPaginadosFiltrados(Connection conex, int start, int length, String searchValue, String orderBy, String orderDirection) throws SQLException {
        List<HashMap<String, Object>> lista = new ArrayList<>();

        String sql = "SELECT VEH_ID, VEH_PLACA, VEH_MOTOR, VEH_CHASIS, VEH_SERIE, MARCA, LINEA "
                + "FROM VW_VEHICULOS WHERE (VEH_PLACA LIKE ? OR VEH_MOTOR LIKE ? OR VEH_CHASIS LIKE ? OR VEH_SERIE LIKE ? OR MARCA LIKE ? OR LINEA LIKE ? ) "
                + "ORDER BY " + orderBy + " " + orderDirection + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            String searchPattern = "%" + searchValue + "%";
            pst.setString(1, searchPattern);
            pst.setString(2, searchPattern);
            pst.setString(3, searchPattern);
            pst.setString(4, searchPattern);
            pst.setString(5, searchPattern);
            pst.setString(6, searchPattern);
            pst.setInt(7, start);
            pst.setInt(8, length);

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
        }

        return lista;
    }

    public int contarVehiculos(Connection conex) throws SQLException {
        String sql = "SELECT COUNT(*) FROM VW_VEHICULOS";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            if (rst.next()) {
                return rst.getInt(1);
            }
        }
        return 0;
    }

    public int contarVehiculosFiltrados(Connection conex, String searchValue) throws SQLException {
        String sql = "SELECT COUNT(*) FROM VW_VEHICULOS WHERE (VEH_PLACA LIKE ? OR VEH_MOTOR LIKE ? OR VEH_CHASIS LIKE ? OR VEH_SERIE LIKE ? OR MARCA LIKE ? OR LINEA LIKE ? )";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            String searchPattern = "%" + searchValue + "%";
            pst.setString(1, searchPattern);
            pst.setString(2, searchPattern);
            pst.setString(3, searchPattern);
            pst.setString(4, searchPattern);
            pst.setString(5, searchPattern);
            pst.setString(6, searchPattern);
            try (ResultSet rst = pst.executeQuery()) {
                if (rst.next()) {
                    return rst.getInt(1);
                }
            }
        }
        return 0;
    }

}
