package com.censo.modelo.dao;

import com.censo.modelo.persistencia.VehiculoRunt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

public class VehiculoRuntDao extends Conexion {

    private ResultSet rst = null;
    private PreparedStatement pst = null;

    public boolean adicionarVehiculoRunt(Connection conex, VehiculoRunt vehiculoRunt) throws SQLException {

        String sql = "INSERT INTO VEHICULOS_RUNT (PLACA,VEH_CHASIS,VEH_SERIE,VEH_MOTOR,VEH_CLASE,VEH_SERVICIO,"
                    + "COL_ID,VEH_MODELO,LIN_ID,VEH_LICTRANSITO,VEH_RUNT,VEH_SOAT,VEH_FECVENSOAT,VEH_TECNOMEC,VEH_FECVENTECNO,PAI_ID,MUN_ID,"
                    + "VEH_CIUDADMATRICULA,VEH_TIPODOCUMENTOIMP,VEH_DOCUMENTOIMP,VEH_FECHAIMP,PAI_ID_ORIGEN,VEH_OBSERVACIONES,VEH_TIPOUSO,VEH_TRANSFORMADO,"
                    + "USU_ID,EST_ID,VEH_FECHAPROCESO,VEH_FECMATRI) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?)";
        
        try {
            pst = conex.prepareStatement(sql);
            pst.setString(1, vehiculoRunt.getPlaca());
            pst.setString(2, vehiculoRunt.getLicenciaTransito());
            pst.setString(3, vehiculoRunt.getEstado());
            pst.setString(4, vehiculoRunt.getTipoServicio());
            pst.setString(5, vehiculoRunt.getClaseVehiculo());
            pst.setString(6, vehiculoRunt.getMarca());
            pst.setString(7, vehiculoRunt.getLinea());
            pst.setString(8, vehiculoRunt.getModelo());
            pst.setString(9, vehiculoRunt.getColor());
            pst.setString(10, vehiculoRunt.getSerie());
            pst.setString(11, vehiculoRunt.getMotor());
            pst.setString(12, vehiculoRunt.getChasis());
            pst.setString(13, vehiculoRunt.getVin());
            pst.setString(14, vehiculoRunt.getCilindraje());
            pst.setString(15, vehiculoRunt.getTipoCarroceria());
            pst.setString(16, vehiculoRunt.getTipoCombustible());
            pst.setString(17, vehiculoRunt.getFechaMatriculaInicial());
            pst.setString(18, vehiculoRunt.getAutoridadTransito());
            pst.setString(19, vehiculoRunt.getGravamenesPropiedad());
            pst.setString(20, vehiculoRunt.getClasicoAntiguo());
            pst.setString(21, vehiculoRunt.getRepotenciado());
            pst.setString(22, vehiculoRunt.getRegrabacionMotor());
            pst.setString(23, vehiculoRunt.getRegrabacionChasis());
            pst.setString(24, vehiculoRunt.getRegrabacionSerie());
            pst.setString(25, vehiculoRunt.getRegrabacionVin());
            pst.setString(26, vehiculoRunt.getCapacidadCarga());
            pst.setString(27, vehiculoRunt.getPesoBrutoVehicular());
            pst.setString(28, vehiculoRunt.getCapacidadPasajeros());
            pst.setString(29, vehiculoRunt.getCapacidadPasajerosSentados());
            pst.setString(30, vehiculoRunt.getNroEjes());
            pst.setString(31, vehiculoRunt.getPolizaSoat().getNumeroPoliza());
            pst.setString(32, vehiculoRunt.getPolizaSoat().getFechaExpedicion());
            pst.setString(33, vehiculoRunt.getPolizaSoat().getFechaInicioVigencia());
            pst.setString(34, vehiculoRunt.getPolizaSoat().getFechaFinVigencia());
            pst.setString(35, vehiculoRunt.getPolizaSoat().getEntidadSoat());
            pst.setString(36, vehiculoRunt.getPolizaSoat().getEstado());
            pst.setString(37, vehiculoRunt.getTecnicoMecanico().getTipoRevision());
            pst.setString(38, vehiculoRunt.getTecnicoMecanico().getFechaExpedicion());
            pst.setString(39, vehiculoRunt.getTecnicoMecanico().getFechaVigencia());
            pst.setString(40, vehiculoRunt.getTecnicoMecanico().getCdaExpide());
            pst.setString(41, vehiculoRunt.getTecnicoMecanico().getVigente());
            pst.executeUpdate();
            rst = pst.getGeneratedKeys();
            
            return true;
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
        return false;
    }

    public VehiculoRunt ConsultarVehiculoByPlaca(Connection conex, String placa) throws SQLException {

        try {
                pst = conex.prepareStatement("SELECT * FROM VEHICULOS_RUNT WHERE PLACA = ? ");
            pst.setString(1, placa);
            rst = pst.executeQuery();

            while (rst.next()) {
                return VehiculoRunt.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarVehiculoByPlaca: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarVehiculoByPlaca:" + e);
            }
        }
        return null;
    }

    public HashMap<String, Object> ConsultarDatosVehiculoRuntByPlaca(Connection conex, int id) throws SQLException {

        HashMap<String, Object> datos = new HashMap<>();

        try {
            pst = conex.prepareStatement("SELECT * FROM VEHICULOS_RUNT WHERE PLACA = ? ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            if (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    datos.put(rsmd.getColumnName(i + 1), rst.getObject(i + 1));
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarDatosVehiculoRuntByPlaca: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarDatosVehiculoRuntByPlaca:" + e);
            }
        }
        return datos;
    }

}
