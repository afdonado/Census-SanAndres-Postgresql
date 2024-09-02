package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenHistorialVerificacion;
import com.censo.modelo.persistencia.CenVerificacion;
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

public class VerificacionDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public long adicionarVerificacion(Connection conex, CenVerificacion cenverificacion) {

        try {
            pst = conex.prepareStatement("INSERT INTO CEN_VERIFICACIONES (CEN_ID,VER_RUNT,VER_DOCUMENTOS,"
                    + "VER_FOTOS,VER_OBSERVACIONES,USU_ID,VER_FECHAPROCESO,EVER_ID) "
                    + "VALUES (?,?,?,?,?,?,sysdate,?)", new String[]{"VER_ID"});
            pst.setLong(1, cenverificacion.getCen_id());
            pst.setString(2, cenverificacion.getVerificado_runt());
            pst.setString(3, cenverificacion.getVerificado_doc());
            pst.setString(4, cenverificacion.getVerificado_foto());
            pst.setString(5, cenverificacion.getObservaciones());
            pst.setLong(6, cenverificacion.getUsu_id());
            pst.setInt(7, cenverificacion.getEstado());
            pst.executeUpdate();
            rst = pst.getGeneratedKeys();
            if (rst != null) {
                if (rst.next()) {
                    return rst.getLong(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en adicionarVerificacion: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.err.println("Error en cierres de adicionarVerificacion: " + e);
            }
        }
        return 0;
    }

    public boolean modificarVerificacion(Connection conex, CenVerificacion cenverificacion) throws SQLException, IOException {

        try {
            pst = conex.prepareStatement("UPDATE CEN_VERIFICACIONES SET VER_RUNT = ?,VER_DOCUMENTOS = ?,VER_FOTOS = ? ,VER_OBSERVACIONES = ?, EVER_ID = ?, USU_ID = ?, VER_FECHAPROCESO = SYSDATE WHERE VER_ID = ? ");
            pst.setString(1, cenverificacion.getVerificado_runt());
            pst.setString(2, cenverificacion.getVerificado_doc());
            pst.setString(3, cenverificacion.getVerificado_foto());
            pst.setString(4, cenverificacion.getObservaciones());
            pst.setInt(5, cenverificacion.getEstado());
            pst.setLong(6, cenverificacion.getUsu_id());
            pst.setLong(7, cenverificacion.getId());
            pst.executeUpdate();
            
            return true;

        } catch (SQLException e) {
            System.err.println("Error en modificarVerificacion: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de modificarVerificacion:" + e);
            }
        }
        
        return false;
    }
    
    public CenVerificacion ConsultarVerificacionByIdCenso(Connection conex, long id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_VERIFICACIONES WHERE CEN_ID = ? ");
            pst.setLong(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenVerificacion.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarVerificacionByIdCenso: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarVerificacionByIdCenso:" + e);
            }
        }
        return null;
    }


    public CenVerificacion ConsultarVerificacionByIdVerificacion(Connection conex, long id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_VERIFICACIONES WHERE VER_ID = ? ");
            pst.setLong(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenVerificacion.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarVerificacionByIdVerificacion: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarVerificacionByIdVerificacion:" + e);
            }
        }
        return null;
    }

    public List ListarVerificacionesByFechaCenso(Connection conex, Date fechaini, Date fechafin, int punto) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT CEN_ID,NUMERO,TO_CHAR(FECHA,'dd/MM/yyyy') FECHA,HORA,EST_ID, ESTADO,"
                    + "TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,PUN_ID,PUNTO_ATENCION,"
                    + "VEH_PLACA,VEH_MOTOR,VEH_CHASIS,VEH_SERIE,PER_ID,TDOC_ID,TIPO_DOC_CORTO,TIPO_DOC,"
                    + "DOCUMENTO,NOMBRE,USU_ID,USUARIO,DOCUMENTO_PDF,FOTO,VERIFICACION_RUNT,VERIFICACION_DOC,VERIFICACION_FOTOS,"
                    + "OBSERVACIONES_VERIFICACION,TO_CHAR(FECHA_PROCESO_VERIFICACION,'dd/MM/yyyy HH:mm') FECHA_PROCESO_VERIFICACION,"
                    + "USU_ID_VERIFICACION,USUARIO_VERIFICACION,ESTADO_VERIFICACION,VEH_COLOR,VEH_MARCA,VEH_LINEA, "
                    + "VEH_RUNT,VEH_SOAT,VEH_TECNOMEC,VEH_MODELO,VEH_SERVICIO,VEH_CLASE "
                    + "FROM VW_CENSOS WHERE (PUN_ID = ? OR 0 = ?) AND to_date(to_char(FECHA,'dd/MM/yyyy')) BETWEEN ? AND ? ORDER BY NUMERO");
            pst.setInt(1, punto);
            pst.setInt(2, punto);
            pst.setDate(3, fechaini);
            pst.setDate(4, fechafin);
            rst = pst.executeQuery();

            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, String> hash = new HashMap<>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                }
                listaDatos.add(hash);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarVerificacionesByFechaCenso: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarVerificacionesByFechaCenso:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarVerificacionesByFechaRegistro(Connection conex, Date fechaini, Date fechafin, int punto) throws SQLException {
        
        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT CEN_ID,NUMERO,TO_CHAR(FECHA,'dd/MM/yyyy') FECHA,HORA,EST_ID, ESTADO,"
                    + "TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,PUN_ID,PUNTO_ATENCION,"
                    + "VEH_PLACA,VEH_MOTOR,VEH_CHASIS,VEH_SERIE,PER_ID,TDOC_ID,TIPO_DOC_CORTO,TIPO_DOC,"
                    + "DOCUMENTO,NOMBRE,USU_ID,USUARIO,DOCUMENTO_PDF,FOTO,VERIFICACION_RUNT,VERIFICACION_DOC,VERIFICACION_FOTOS,"
                    + "OBSERVACIONES_VERIFICACION,TO_CHAR(FECHA_PROCESO_VERIFICACION,'dd/MM/yyyy HH:mm') FECHA_PROCESO_VERIFICACION,"
                    + "USU_ID_VERIFICACION,USUARIO_VERIFICACION,ESTADO_VERIFICACION,VEH_COLOR,VEH_MARCA,VEH_LINEA, "
                    + "VEH_RUNT,VEH_SOAT,VEH_TECNOMEC,VEH_MODELO,VEH_SERVICIO,VEH_CLASE "
                    + "FROM VW_CENSOS WHERE (PUN_ID = ? OR 0 = ?) AND to_date(to_char(FECHA_PROCESO_VERIFICACION,'dd/MM/yyyy')) BETWEEN ? AND ? ORDER BY NUMERO ");
            pst.setInt(1, punto);
            pst.setInt(2, punto);
            pst.setDate(3, fechaini);
            pst.setDate(4, fechafin);
            rst = pst.executeQuery();

            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, String> hash = new HashMap<>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                }
                listaDatos.add(hash);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarVerificacionesByFechaRegistro: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarVerificacionesByFechaRegistro:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarVerificacionesByNumeroCenso(Connection conex, String numeroCenso) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT CEN_ID,NUMERO,TO_CHAR(FECHA,'dd/MM/yyyy') FECHA,HORA,EST_ID, ESTADO,"
                    + "TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,PUN_ID,PUNTO_ATENCION,"
                    + "VEH_PLACA,VEH_MOTOR,VEH_CHASIS,VEH_SERIE,PER_ID,TDOC_ID,TIPO_DOC_CORTO,TIPO_DOC,"
                    + "DOCUMENTO,NOMBRE,USU_ID,USUARIO,DOCUMENTO_PDF,FOTO,VERIFICACION_RUNT,VERIFICACION_DOC,VERIFICACION_FOTOS,"
                    + "OBSERVACIONES_VERIFICACION,TO_CHAR(FECHA_PROCESO_VERIFICACION,'dd/MM/yyyy HH:mm') FECHA_PROCESO_VERIFICACION,"
                    + "USU_ID_VERIFICACION,USUARIO_VERIFICACION,ESTADO_VERIFICACION,VEH_COLOR,VEH_MARCA,VEH_LINEA,"
                    + "VEH_RUNT,VEH_SOAT,VEH_TECNOMEC,VEH_MODELO,VEH_SERVICIO,VEH_CLASE "
                    + "FROM VW_CENSOS WHERE NUMERO = ? ORDER BY NUMERO ");
            pst.setString(1, numeroCenso);
            rst = pst.executeQuery();

            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, String> hash = new HashMap<>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                }
                listaDatos.add(hash);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarVerificacionesByFechaRegistro: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarVerificacionesByFechaRegistro:" + e);
            }
        }
        return listaDatos;
    }

    public long adicionarHistorialVerificacion(Connection conex, CenHistorialVerificacion cenhistotialverificacion) {

        try {
            pst = conex.prepareStatement("INSERT INTO CEN_HISTORIAL_VERIFICACIONES (VER_ID,EVER_ID,USU_ID,HVER_OBSERVACIONES,HVER_FECHAPROCESO) "
                    + "VALUES (?,?,?,?,sysdate)", new String[]{"VER_ID"});
            pst.setLong(1, cenhistotialverificacion.getVer_id());
            pst.setInt(2, cenhistotialverificacion.getEstado());
            pst.setLong(3, cenhistotialverificacion.getUsu_id());
            pst.setString(4, cenhistotialverificacion.getObservaciones());
            pst.executeUpdate();
            rst = pst.getGeneratedKeys();
            if (rst != null) {
                if (rst.next()) {
                    return rst.getLong(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en adicionarHistorialVerificacion: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.err.println("Error en cierres de adicionarHistorialVerificacion: " + e);
            }
        }
        return 0;
    }
    
}
