package com.censo.modelo.dao;

import com.censo.modelo.persistencia.PersonaRunt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

public class PersonaRuntDao {

    public boolean adicionarPersonaRunt(Connection conex, PersonaRunt personaRunt) throws SQLException {

        String sql = "INSERT INTO PERSONAS_RUNT (TIPO_DOCUMENTO_ID, TIPO_DOCUMENTO,NUMERO_DOCUMENTO,"
                + "NOMBRE1,NOMBRE2,APELLIDO1,APELLIDO2,NUMERO_LICENCIA,FECHA_EXPEDICION,"
                + "FECHA_VENCIMIENTO,CATEGORIA_ID,CATEGORIA,ESTADO_LICENCIA,FECHA_CONSULTA) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, personaRunt.getTipoDocumentoId());
            pst.setString(2, personaRunt.getTipoDocumento());
            pst.setString(3, personaRunt.getNumeroDocumento());
            pst.setString(4, personaRunt.getNombre1());
            pst.setString(5, personaRunt.getNombre2());
            pst.setString(6, personaRunt.getApellido1());
            pst.setString(7, personaRunt.getApellido2());
            pst.setString(8, personaRunt.getNumeroLicencia());
            pst.setString(9, personaRunt.getFechaExpedicion());
            pst.setString(10, personaRunt.getFechaVencimiento());
            pst.setInt(11, personaRunt.getCategoriaId());
            pst.setString(12, personaRunt.getCategoria());
            pst.setString(13, personaRunt.getEstadoLicencia());
            pst.setString(14, personaRunt.getFechaConsulta());
            pst.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("Error en adicionarPersonaRunt: " + e);
        }
        return false;
    }

    public PersonaRunt ConsultarPersonaRuntByDocumento(Connection conex, int tipodocumento, String documento) throws SQLException {

        String sql = "SELECT * FROM PERSONAS_RUNT WHERE TIPO_DOCUMENTO_ID = ? AND NUMERO_DOCUMENTO = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, tipodocumento);
            pst.setString(2, documento);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return PersonaRunt.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarPersonaRuntByDocumento: " + e);
        }
        return null;
    }

    public HashMap<String, Object> ConsultarDatosPersonaRuntByDocumento(Connection conex, int tipodocumento, String documento) throws SQLException {

        HashMap<String, Object> datos = new HashMap<>();

        String sql = "SELECT * FROM PERSONAS_RUNT WHERE TIPO_DOCUMENTO_ID = ? AND NUMERO_DOCUMENTO = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, tipodocumento);
            pst.setString(2, documento);
            try (ResultSet rst = pst.executeQuery()) {
                if (rst.next()) {
                    ResultSetMetaData rsmd = rst.getMetaData();
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        datos.put(rsmd.getColumnName(i + 1), rst.getObject(i + 1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarDatosPersonaRuntByDocumento: " + e);
        }
        return datos;
    }

}
