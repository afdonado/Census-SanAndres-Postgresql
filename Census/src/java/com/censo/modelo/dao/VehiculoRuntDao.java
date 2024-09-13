package com.censo.modelo.dao;

import com.censo.modelo.persistencia.VehiculoRunt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

public class VehiculoRuntDao {

    public boolean adicionarVehiculoRunt(Connection conex, VehiculoRunt vehiculoRunt) throws SQLException {

        String sql = "INSERT INTO VEHICULOS_RUNT (PLACA,LICENCIA_TRANSITO,ESTADO,TIPO_SERVICIO,CLASE_VEHICULO,"
                + "MARCA,LINEA,MODELO,COLOR,SERIE,MOTOR,CHASIS,VIN,CILINDRAJE,TIPO_CARROCERIA,TIPO_COMBUSTIBLE,"
                + "FECHA_MATRICULA,AUTORIDAD_TRANSITO,GRAVAMENES_PROPIEDAD,CLASICO_ANTIGUO,REPOTENCIADO,"
                + "REGRABACION_MOTOR,REGRABACION_CHASIS,REGRABACION_SERIE,REGRABACION_VIN,CAPACIDAD_CARGA,"
                + "PESO_BRUTO_VEHICULAR,CAPACIDAD_PASAJEROS,CAPACIDAD_PASAJEROS_SENTADOS,NRO_EJES,NUMERO_POLIZA,"
                + "FECHA_EXPEDICION_SOAT,FECHA_INICIO_VIGENCIA,FECHA_FIN_VIGENCIA,ENTIDAD_SOAT,ESTADO_SOAT,TIPO_REVISION,"
                + "FECHA_EXPEDICION_TECNO,FECHA_VIGENCIA,CDA_EXPIDE,VIGENTE,FECHA_CONSULTA,NUMERO_DOCUMENTO,"
                + "CLASE_VEHICULO_ID,TIPO_SERVICIO_ID) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement pst = conex.prepareStatement(sql)) {
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
            pst.setString(42, vehiculoRunt.getFechaConsulta());
            pst.setString(43, vehiculoRunt.getNumeroDocumento());
            pst.setInt(44, vehiculoRunt.getClaseVehiculoId());
            pst.setInt(45, vehiculoRunt.getTipoServicioId());
            pst.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("Error en adicionarVehiculoRunt: " + e);
        }
        return false;
    }

    public VehiculoRunt ConsultarVehiculoRuntByPlacaDocumento(Connection conex, String placa, String documento) throws SQLException {

        String sql = "SELECT * FROM VEHICULOS_RUNT WHERE PLACA = ? AND NUMERO_DOCUMENTO = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, placa);
            pst.setString(2, documento);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return VehiculoRunt.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarVehiculoRuntByPlacaDocumento: " + e);
        }
        return null;
    }

    public HashMap<String, Object> ConsultarDatosVehiculoRuntByPlaca(Connection conex, String placa) throws SQLException {

        HashMap<String, Object> datos = new HashMap<>();

        String sql = "SELECT * FROM VEHICULOS_RUNT WHERE PLACA = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, placa);
            try (ResultSet rst = pst.executeQuery()) {
                if (rst.next()) {
                    ResultSetMetaData rsmd = rst.getMetaData();
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        datos.put(rsmd.getColumnName(i + 1), rst.getObject(i + 1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarDatosVehiculoRuntByPlaca: " + e);
        }
        return datos;
    }

}
