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
    
    public long adicionarCenso(Connection conex, CenCenso cencenso) {

        try {
            pst = conex.prepareStatement("INSERT INTO CEN_CENSOS (CEN_FECHA,CEN_HORA,PUN_ID,VEH_ID,PER_ID,TPER_ID,USU_ID,EST_ID,CEN_FECHAPROCESO,CEN_NUMERO,CEN_OBSERVACIONES) VALUES (?,?,?,?,?,?,?,?,SYSDATE,?,?)", new String[]{"CEN_ID"});
            pst.setDate(1, cencenso.getFecha());
            pst.setString(2, cencenso.getHora());
            pst.setLong(3, cencenso.getPun_id());
            pst.setLong(4, cencenso.getVeh_id());
            pst.setLong(5, cencenso.getPer_id());
            pst.setLong(6, cencenso.getTper_id());
            pst.setLong(7, cencenso.getUsu_id());
            pst.setInt(8, cencenso.getEstado());
            pst.setString(9, cencenso.getNumero());
            pst.setString(10, cencenso.getObservaciones());
            pst.executeUpdate();
            rst = pst.getGeneratedKeys();
            if (rst != null) {
                if (rst.next()) {
                    return rst.getLong(1);
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

    public CenCenso ConsultarCensoById(Connection conex, long id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_CENSOS WHERE CEN_ID = ? ");
            pst.setLong(1, id);
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

    public CenCenso ConsultarCensoByIdVehiculo(Connection conex, long idvehiculo) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_CENSOS WHERE EST_ID = 1 AND VEH_ID = ? ");
            pst.setLong(1, idvehiculo);
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
/*
    public List ListarCensos(Connection conex) throws SQLException {
        
        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT CEN_ID,NUMERO,TO_CHAR(FECHA,'dd/MM/yyyy') FECHA,HORA,EST_ID, ESTADO,"
                    + "TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,PUN_ID,PUNTO_ATENCION,"
                    + "VEH_PLACA,VEH_MOTOR,VEH_CHASIS,VEH_SERIE,PER_ID,TDOC_ID,TIPO_DOC_CORTO,TIPO_DOC,"
                    + "DOCUMENTO,NOMBRE,DIRECCION,TELEFONO,USU_ID,USUARIO,VERIFICACION_RUNT,VERIFICACION_DOC,VERIFICACION_FOTOS,"
                    + "OBSERVACIONES_VERIFICACION,TO_CHAR(FECHA_PROCESO_VERIFICACION,'dd/MM/yyyy') FECHA_PROCESO_VERIFICACION,USU_ID_VERIFICACION,USUARIO_VERIFICACION "
                    + "FROM VW_CENSOS ORDER BY FECHA");
            rst = pst.executeQuery();

            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, String> hash = new HashMap<String, String>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                }
                listaDatos.add(hash);
            }
        } catch (SQLException e) {
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
        return listaDatos;
    }
*/

    public List ListarCensosByNumero(Connection conex, String numero) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT CEN_ID,NUMERO,TO_CHAR(FECHA,'dd/MM/yyyy') FECHA,HORA,EST_ID, ESTADO,"
                    + "TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,PUN_ID,PUNTO_ATENCION,"
                    + "VEH_PLACA,VEH_MOTOR,VEH_CHASIS,VEH_SERIE,PER_ID,TDOC_ID,TIPO_DOC_CORTO,TIPO_DOC,"
                    + "DOCUMENTO,NOMBRE,DIRECCION,TELEFONO,USU_ID,USUARIO,DOCUMENTO_PDF,FOTO,VERIFICACION_RUNT,VERIFICACION_DOC,VERIFICACION_FOTOS,"
                    + "OBSERVACIONES_VERIFICACION,TO_CHAR(FECHA_PROCESO_VERIFICACION,'dd/MM/yyyy') FECHA_PROCESO_VERIFICACION,USU_ID_VERIFICACION,USUARIO_VERIFICACION "
                    + "FROM VW_CENSOS WHERE NUMERO=? ORDER BY FECHA");
            pst.setString(1, numero);
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
            throw new SQLException("Error en ListarCensosByNumero: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCensosByNumero:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCensosByReferenciaVehiculo(Connection conex, int tipoRef, String valorReferencia) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            if (tipoRef == 1) {
                pst = conex.prepareStatement("SELECT CEN_ID,NUMERO,TO_CHAR(FECHA,'dd/MM/yyyy') FECHA,HORA,EST_ID, ESTADO,"
                        + "TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,PUN_ID,PUNTO_ATENCION,"
                        + "VEH_PLACA,VEH_MOTOR,VEH_CHASIS,VEH_SERIE,PER_ID,TDOC_ID,TIPO_DOC_CORTO,TIPO_DOC,"
                        + "DOCUMENTO,NOMBRE,DIRECCION,TELEFONO,USU_ID,USUARIO,DOCUMENTO_PDF,FOTO,VERIFICACION_RUNT,VERIFICACION_DOC,VERIFICACION_FOTOS,"
                        + "OBSERVACIONES_VERIFICACION,TO_CHAR(FECHA_PROCESO_VERIFICACION,'dd/MM/yyyy') FECHA_PROCESO_VERIFICACION,USU_ID_VERIFICACION,USUARIO_VERIFICACION "
                        + "FROM VW_CENSOS WHERE VEH_PLACA = ? ");
            }
            if (tipoRef == 2) {
                pst = conex.prepareStatement("SELECT CEN_ID,NUMERO,TO_CHAR(FECHA,'dd/MM/yyyy') FECHA,HORA,EST_ID, ESTADO,"
                        + "TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,PUN_ID,PUNTO_ATENCION,"
                        + "VEH_PLACA,VEH_MOTOR,VEH_CHASIS,VEH_SERIE,PER_ID,TDOC_ID,TIPO_DOC_CORTO,TIPO_DOC,"
                        + "DOCUMENTO,NOMBRE,DIRECCION,TELEFONO,USU_ID,USUARIO,DOCUMENTO_PDF,FOTO,VERIFICACION_RUNT,VERIFICACION_DOC,VERIFICACION_FOTOS,"
                        + "OBSERVACIONES_VERIFICACION,TO_CHAR(FECHA_PROCESO_VERIFICACION,'dd/MM/yyyy') FECHA_PROCESO_VERIFICACION,USU_ID_VERIFICACION,USUARIO_VERIFICACION "
                        + "FROM VW_CENSOS WHERE VEH_MOTOR = ? ");
            }
            if (tipoRef == 3) {
                pst = conex.prepareStatement("SELECT CEN_ID,NUMERO,TO_CHAR(FECHA,'dd/MM/yyyy') FECHA,HORA,EST_ID, ESTADO,"
                        + "TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,PUN_ID,PUNTO_ATENCION,"
                        + "VEH_PLACA,VEH_MOTOR,VEH_CHASIS,VEH_SERIE,PER_ID,TDOC_ID,TIPO_DOC_CORTO,TIPO_DOC,"
                        + "DOCUMENTO,NOMBRE,DIRECCION,TELEFONO,USU_ID,USUARIO,DOCUMENTO_PDF,FOTO,VERIFICACION_RUNT,VERIFICACION_DOC,VERIFICACION_FOTOS,"
                        + "OBSERVACIONES_VERIFICACION,TO_CHAR(FECHA_PROCESO_VERIFICACION,'dd/MM/yyyy') FECHA_PROCESO_VERIFICACION,USU_ID_VERIFICACION,USUARIO_VERIFICACION "
                        + "FROM VW_CENSOS WHERE VEH_CHASIS = ? ");
            }
            if (tipoRef == 4) {
                pst = conex.prepareStatement("SELECT CEN_ID,NUMERO,TO_CHAR(FECHA,'dd/MM/yyyy') FECHA,HORA,EST_ID, ESTADO,"
                        + "TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,PUN_ID,PUNTO_ATENCION,"
                        + "VEH_PLACA,VEH_MOTOR,VEH_CHASIS,VEH_SERIE,PER_ID,TDOC_ID,TIPO_DOC_CORTO,TIPO_DOC,"
                        + "DOCUMENTO,NOMBRE,DIRECCION,TELEFONO,USU_ID,USUARIO,DOCUMENTO_PDF,FOTO,VERIFICACION_RUNT,VERIFICACION_DOC,VERIFICACION_FOTOS,"
                        + "OBSERVACIONES_VERIFICACION,TO_CHAR(FECHA_PROCESO_VERIFICACION,'dd/MM/yyyy') FECHA_PROCESO_VERIFICACION,USU_ID_VERIFICACION,USUARIO_VERIFICACION "
                        + "FROM VW_CENSOS WHERE VEH_SERIE = ? ");
            }
            pst.setString(1, valorReferencia);
            rst = pst.executeQuery();

            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, String> hash = new HashMap<String, String>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                }
                listaDatos.add(hash);
            }
        } catch (Exception e) {
            throw new SQLException("Error en ListarCensosByReferenciaVehiculo: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCensosByReferenciaVehiculo:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCensosByPersona(Connection conex, int tipodoc, String documento) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT CEN_ID,NUMERO,TO_CHAR(FECHA,'dd/MM/yyyy') FECHA,HORA,EST_ID, ESTADO,"
                    + "TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,PUN_ID,PUNTO_ATENCION,"
                    + "VEH_PLACA,VEH_MOTOR,VEH_CHASIS,VEH_SERIE,PER_ID,TDOC_ID,TIPO_DOC_CORTO,TIPO_DOC,"
                    + "DOCUMENTO,NOMBRE,DIRECCION,TELEFONO,USU_ID,USUARIO,DOCUMENTO_PDF,FOTO,VERIFICACION_RUNT,VERIFICACION_DOC,VERIFICACION_FOTOS,"
                    + "OBSERVACIONES_VERIFICACION,TO_CHAR(FECHA_PROCESO_VERIFICACION,'dd/MM/yyyy') FECHA_PROCESO_VERIFICACION,USU_ID_VERIFICACION,USUARIO_VERIFICACION "
                    + "FROM VW_CENSOS VC WHERE VC.TDOC_ID = ? AND VC.DOCUMENTO=? ORDER BY NUMERO");
            pst.setInt(1, tipodoc);
            pst.setString(2, documento);
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
            throw new SQLException("Error en ListarCensosByPersona: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCensosByPersona:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCensosByFecha(Connection conex, Date fechaini, Date fechafin, int punto) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT CEN_ID,NUMERO,TO_CHAR(FECHA,'dd/MM/yyyy') FECHA,HORA,EST_ID, ESTADO,"
                    + "TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,PUN_ID,PUNTO_ATENCION,"
                    + "VEH_PLACA,VEH_MOTOR,VEH_CHASIS,VEH_SERIE,PER_ID,TDOC_ID,TIPO_DOC_CORTO,TIPO_DOC,"
                    + "DOCUMENTO,NOMBRE,DIRECCION,TELEFONO,USU_ID,USUARIO,DOCUMENTO_PDF,FOTO,VERIFICACION_RUNT,VERIFICACION_DOC,VERIFICACION_FOTOS,"
                    + "OBSERVACIONES_VERIFICACION,TO_CHAR(FECHA_PROCESO_VERIFICACION,'dd/MM/yyyy') FECHA_PROCESO_VERIFICACION,USU_ID_VERIFICACION,USUARIO_VERIFICACION "
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
        return listaDatos;
    }

    public List ListarCensosByFechaRegistro(Connection conex, Date fechaini, Date fechafin, int punto) throws SQLException {
        
        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT CEN_ID,NUMERO,TO_CHAR(FECHA,'dd/MM/yyyy') FECHA,HORA,EST_ID, ESTADO,"
                    + "TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,PUN_ID,PUNTO_ATENCION,"
                    + "VEH_PLACA,VEH_MOTOR,VEH_CHASIS,VEH_SERIE,PER_ID,TDOC_ID,TIPO_DOC_CORTO,TIPO_DOC,"
                    + "DOCUMENTO,NOMBRE,DIRECCION,TELEFONO,USU_ID,USUARIO,DOCUMENTO_PDF,FOTO,VERIFICACION_RUNT,VERIFICACION_DOC,VERIFICACION_FOTOS,"
                    + "OBSERVACIONES_VERIFICACION,TO_CHAR(FECHA_PROCESO_VERIFICACION,'dd/MM/yyyy') FECHA_PROCESO_VERIFICACION,USU_ID_VERIFICACION,USUARIO_VERIFICACION "
                    + "FROM VW_CENSOS WHERE (PUN_ID = ? OR 0 = ?) AND to_date(to_char(FECHA_PROCESO,'dd/MM/yyyy')) BETWEEN ? AND ? ORDER BY NUMERO ");
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
        return listaDatos;
    }

    public Datosresponse ConsultarCensoWSByNumero(Connection conex, String numero) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT to_char(CC.CEN_FECHA,'dd/MM/yyyy') FECHA_CENSO,CV.VEH_PLACA PLACA_VEHICULO,CTP.TPER_DESCRIPCION TIPO_PERSONA,"
                    + "CTD.TDOC_DESCRIPCION_CORTA TIPO_DOC, CP.PER_DOCUMENTO DOCUMENTO, CP.PER_NOMBRE1||' '||CP.PER_NOMBRE2||' '||CP.PER_APELLIDO1||' '||CP.PER_APELLIDO2 NOMBRE "
                    + "FROM CEN_CENSOS CC "
                    + "INNER JOIN CEN_VEHICULOS CV ON CV.VEH_ID=CC.VEH_ID "
                    + "INNER JOIN CEN_PERSONAS CP ON CP.PER_ID=CC.PER_ID "
                    + "INNER JOIN CEN_TIPOS_DOCUMENTO CTD ON CTD.TDOC_ID=CP.PER_TIPODOC "
                    + "INNER JOIN CEN_TIPOS_PERSONA CTP ON CTP.TPER_ID=CC.TPER_ID "
                    + "WHERE CC.CEN_NUMERO = ? ");
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

    public void modificarCenso(Connection conex, CenCenso cencenso) throws SQLException, IOException {

        try {
            pst = conex.prepareStatement("UPDATE CEN_CENSOS SET CEN_FECHA = ?,PUN_ID = ?,VEH_ID = ?,PER_ID = ?, TPER_ID = ?,CEN_NUMERO = ?,CEN_OBSERVACIONES = ? WHERE CEN_ID = ?");
            pst.setDate(1, cencenso.getFecha());
            pst.setLong(2, cencenso.getPun_id());
            pst.setLong(3, cencenso.getVeh_id());
            pst.setLong(4, cencenso.getPer_id());
            pst.setLong(5, cencenso.getTper_id());
            pst.setString(6, cencenso.getNumero());
            pst.setString(7, cencenso.getObservaciones());
            pst.setLong(8, cencenso.getId());
            pst.executeUpdate();

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
    }
    
    public HashMap<String, String> ConsultarDatosCensoById(Connection conex, long id) throws SQLException {

        HashMap<String, String> datos = new HashMap<>();

        try {
            pst = conex.prepareStatement("SELECT * FROM VW_CENSOS WHERE CEN_ID = ? ");
            pst.setLong(1, id);
            rst = pst.executeQuery();

            if (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    datos.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
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
    
}
