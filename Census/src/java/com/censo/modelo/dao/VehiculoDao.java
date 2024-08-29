package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenVehiculo;
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

public class VehiculoDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public long adicionarVehiculo(Connection conex, CenVehiculo cenvehiculo) {

        try {
            pst = conex.prepareStatement("INSERT INTO CEN_VEHICULOS (VEH_PLACA,VEH_CHASIS,VEH_SERIE,VEH_MOTOR,VEH_CLASE,VEH_SERVICIO,"
                    + "COL_ID,VEH_MODELO,LIN_ID,VEH_LICTRANSITO,VEH_RUNT,VEH_SOAT,VEH_FECVENSOAT,VEH_TECNOMEC,VEH_FECVENTECNO,PAI_ID,MUN_ID,"
                    + "VEH_CIUDADMATRICULA,VEH_TIPODOCUMENTOIMP,VEH_DOCUMENTOIMP,VEH_FECHAIMP,PAI_ID_ORIGEN,VEH_OBSERVACIONES,VEH_TIPOUSO,VEH_TRANSFORMADO,"
                    + "USU_ID,EST_ID,VEH_FECHAPROCESO,VEH_FECMATRI) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?)", new String[]{"VEH_ID"});
            pst.setString(1, cenvehiculo.getPlaca_veh());
            pst.setString(2, cenvehiculo.getChasis());
            pst.setString(3, cenvehiculo.getSerie());
            pst.setString(4, cenvehiculo.getMotor());
            pst.setInt(5, cenvehiculo.getClase_veh());
            pst.setInt(6, cenvehiculo.getTipo_servicio());
            pst.setLong(7, cenvehiculo.getCol_id());
            pst.setLong(8, cenvehiculo.getModelo());
            pst.setLong(9, cenvehiculo.getLin_id());
            pst.setString(10, cenvehiculo.getLicencia_transito());
            pst.setString(11, cenvehiculo.getRunt());
            pst.setString(12, cenvehiculo.getSoat());
            pst.setDate(13, cenvehiculo.getFechaven_soat());
            pst.setString(14, cenvehiculo.getTecno_mecanica());
            pst.setDate(15, cenvehiculo.getFechaven_tecno());
            pst.setLong(16, cenvehiculo.getPai_id_matricula());
            pst.setLong(17, cenvehiculo.getMun_id_matricula());
            pst.setString(18, cenvehiculo.getCiudad_matricula());
            pst.setInt(19, cenvehiculo.getTipodoc_importacion());
            pst.setString(20, cenvehiculo.getDoc_importacion());
            pst.setDate(21, cenvehiculo.getFecha_importacion());
            pst.setLong(22, cenvehiculo.getPai_id_origen());
            pst.setString(23, cenvehiculo.getObservaciones());
            pst.setInt(24, cenvehiculo.getTipo_uso());
            pst.setString(25, cenvehiculo.getTransformado());
            pst.setLong(26, cenvehiculo.getUsu_id());
            pst.setInt(27, cenvehiculo.getEstado());
            pst.setDate(28, cenvehiculo.getFecha_matricula());
            pst.executeUpdate();
            rst = pst.getGeneratedKeys();
            if (rst != null) {
                if (rst.next()) {
                    return rst.getLong(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en adicionarVehiculo: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.err.println("Error en cierres de adicionarVehiculo: " + e);
            }
        }
        return 0;
    }

    public boolean modificarVehiculo(Connection conex, CenVehiculo cenvehiculo) throws SQLException, IOException {

        try {
            pst = conex.prepareStatement("UPDATE CEN_VEHICULOS SET VEH_PLACA = ?,VEH_CHASIS = ?,VEH_SERIE = ?,"
                    + "VEH_MOTOR = ?,VEH_CLASE = ?,VEH_SERVICIO = ?,COL_ID = ?,VEH_MODELO = ?,LIN_ID = ?, "
                    + "VEH_LICTRANSITO = ?,VEH_RUNT = ?,VEH_SOAT = ?,VEH_FECVENSOAT = ?,VEH_TECNOMEC = ?,"
                    + "VEH_FECVENTECNO = ?,PAI_ID = ?,MUN_ID = ?, VEH_CIUDADMATRICULA = ?,VEH_TIPODOCUMENTOIMP = ?,"
                    + "VEH_DOCUMENTOIMP = ?,VEH_FECHAIMP = ?, PAI_ID_ORIGEN = ?, VEH_OBSERVACIONES = ?, "
                    + "VEH_TIPOUSO = ?, VEH_TRANSFORMADO = ?, EST_ID = ?, VEH_FECMATRI = ? WHERE VEH_ID = ? ");
            pst.setString(1, cenvehiculo.getPlaca_veh());
            pst.setString(2, cenvehiculo.getChasis());
            pst.setString(3, cenvehiculo.getSerie());
            pst.setString(4, cenvehiculo.getMotor());
            pst.setInt(5, cenvehiculo.getClase_veh());
            pst.setInt(6, cenvehiculo.getTipo_servicio());
            pst.setLong(7, cenvehiculo.getCol_id());
            pst.setLong(8, cenvehiculo.getModelo());
            pst.setLong(9, cenvehiculo.getLin_id());
            pst.setString(10, cenvehiculo.getLicencia_transito());
            pst.setString(11, cenvehiculo.getRunt());
            pst.setString(12, cenvehiculo.getSoat());
            pst.setDate(13, cenvehiculo.getFechaven_soat());
            pst.setString(14, cenvehiculo.getTecno_mecanica());
            pst.setDate(15, cenvehiculo.getFechaven_tecno());
            pst.setLong(16, cenvehiculo.getPai_id_matricula());
            pst.setLong(17, cenvehiculo.getMun_id_matricula());
            pst.setString(18, cenvehiculo.getCiudad_matricula());
            pst.setInt(19, cenvehiculo.getTipodoc_importacion());
            pst.setString(20, cenvehiculo.getDoc_importacion());
            pst.setDate(21, cenvehiculo.getFecha_importacion());
            pst.setLong(22, cenvehiculo.getPai_id_origen());
            pst.setString(23, cenvehiculo.getObservaciones());
            pst.setInt(24, cenvehiculo.getTipo_uso());
            pst.setString(25, cenvehiculo.getTransformado());
            pst.setInt(26, cenvehiculo.getEstado());
            pst.setDate(27, cenvehiculo.getFecha_matricula());
            pst.setLong(28, cenvehiculo.getId());
            pst.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.err.println("Error en modificarVehiculo: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de modificarVehiculo:" + e);
            }
        }

        return false;
    }

    public CenVehiculo ConsultarVehiculoByReferencia(Connection conex, int tipoRef, String valorReferencia) throws SQLException {

        try {
            if (tipoRef == 1) {
                pst = conex.prepareStatement("SELECT * FROM CEN_VEHICULOS WHERE VEH_PLACA = ? ");
            }
            if (tipoRef == 2) {
                pst = conex.prepareStatement("SELECT * FROM CEN_VEHICULOS WHERE VEH_MOTOR = ? ");
            }
            if (tipoRef == 3) {
                pst = conex.prepareStatement("SELECT * FROM CEN_VEHICULOS WHERE VEH_CHASIS = ? ");
            }
            if (tipoRef == 4) {
                pst = conex.prepareStatement("SELECT * FROM CEN_VEHICULOS WHERE VEH_SERIE = ? ");
            }
            pst.setString(1, valorReferencia);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenVehiculo.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarVehiculoByReferencia: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarVehiculoByReferencia:" + e);
            }
        }
        return null;
    }

    public CenVehiculo ConsultarVehiculoById(Connection conex, long id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_VEHICULOS WHERE VEH_ID = ? ");
            pst.setLong(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenVehiculo.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarVehiculoById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarVehiculoById:" + e);
            }
        }
        return null;
    }

    public List ListarVehiculosByReferencia(Connection conex, int tipoRef, String valorReferencia) throws SQLException {

        List<HashMap> listaDatosVehiculo = new LinkedList<>();

        try {
            if (tipoRef == 1) {
                pst = conex.prepareStatement("SELECT * FROM VW_VEHICULOS WHERE VEH_PLACA = ? ");
            }
            if (tipoRef == 2) {
                pst = conex.prepareStatement("SELECT * FROM VW_VEHICULOS WHERE VEH_MOTOR = ? ");
            }
            if (tipoRef == 3) {
                pst = conex.prepareStatement("SELECT * FROM VW_VEHICULOS WHERE VEH_CHASIS = ? ");
            }
            if (tipoRef == 4) {
                pst = conex.prepareStatement("SELECT * FROM VW_VEHICULOS WHERE VEH_SERIE = ? ");
            }
            pst.setString(1, valorReferencia);
            rst = pst.executeQuery();

            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, String> hash = new HashMap<>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                }
                listaDatosVehiculo.add(hash);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarVehiculosByReferencia: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarVehiculosByReferencia:" + e);
            }
        }
        return listaDatosVehiculo;
    }

    public List ListarVehiculosByPersona(Connection conex, int tipodoc, String documento) throws SQLException {

        List<HashMap> listaDatosVehiculo = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT VP.VEH_ID, VP.VEH_PLACA, VP.VEH_MOTOR, VP.VEH_CHASIS, VP.VEH_SERIE,VP.MARCA,VP.LINEA "
                    + "FROM VW_VEHICULOS_PERSONAS VP WHERE VP.PER_TIPODOC = ? AND VP.PER_DOCUMENTO=? "
                    + "GROUP BY VP.VEH_ID, VP.VEH_PLACA, VP.VEH_MOTOR, VP.VEH_CHASIS, VP.VEH_SERIE,VP.MARCA,VP.LINEA ");
            pst.setInt(1, tipodoc);
            pst.setString(2, documento);
            rst = pst.executeQuery();

            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, String> hash = new HashMap<>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                }
                listaDatosVehiculo.add(hash);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarVehiculosByPersona: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarVehiculosByPersona:" + e);
            }
        }
        return listaDatosVehiculo;
    }

    public List ListarVehiculosById(Connection conex, long id) throws SQLException {

        List<HashMap> listaDatosVehiculo = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT * FROM VW_VEHICULOS WHERE VEH_ID = ? ");
            pst.setLong(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, String> hash = new HashMap<>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                }
                listaDatosVehiculo.add(hash);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarVehiculosById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarVehiculosById:" + e);
            }
        }
        return listaDatosVehiculo;
    }

    public List ListarVehiculos(Connection conex) throws SQLException {

        List<HashMap> listaDatosVehiculo = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT * FROM VW_VEHICULOS ");
            rst = pst.executeQuery();

            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, String> hash = new HashMap<>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                }
                listaDatosVehiculo.add(hash);
            }
        } catch (Exception e) {
            throw new SQLException("Error en ListarVehiculos: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarVehiculos:" + e);
            }
        }
        return listaDatosVehiculo;
    }

    public List ListarVehiculosByFechaRegistro(Connection conex, Date fechaini, Date fechafin) throws SQLException {

        List<HashMap> listaDatosVehiculo = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT * FROM VW_VEHICULOS WHERE to_date(to_char(FECHA_PROCESO,'dd/MM/yyyy')) BETWEEN ? AND ? ORDER BY FECHA_PROCESO ");
            pst.setDate(1, fechaini);
            pst.setDate(2, fechafin);
            rst = pst.executeQuery();

            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, String> hash = new HashMap<>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                }
                listaDatosVehiculo.add(hash);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarVehiculosByFechaRegistro: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarVehiculosByFechaRegistro:" + e);
            }
        }
        return listaDatosVehiculo;
    }
    
}
