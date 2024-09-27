package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenPersona;
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

public class PersonaDao {

    public int adicionarPersona(Connection conex, CenPersona cenpersona) {

        String sql = "INSERT INTO CEN_PERSONAS (PER_TIPODOC,PER_DOCUMENTO,"
                + "PER_NOMBRE1,PER_NOMBRE2,PER_APELLIDO1,PER_APELLIDO2,PER_FECHANAC,PER_GENERO,PER_DIRECCION,"
                + "MUN_ID,PER_TELEFONO,PER_MAIL,PER_GRUPOSANGUINEO,PER_LICONDUCCION,PER_FEXPLIC,PER_FVENLIC,"
                + "PER_CATLIC,PER_FECHAPROCESO, LICENCIA_CONDUCCION) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?)";
        try (PreparedStatement pst = conex.prepareStatement(sql, new String[]{"PER_ID"})) {
            pst.setInt(1, cenpersona.getTipodocumento());
            pst.setString(2, cenpersona.getDocumento());
            pst.setString(3, cenpersona.getNombre1());
            pst.setString(4, cenpersona.getNombre2());
            pst.setString(5, cenpersona.getApellido1());
            pst.setString(6, cenpersona.getApellido2());
            pst.setDate(7, cenpersona.getFechanacimiento());
            pst.setInt(8, cenpersona.getGenero());
            pst.setString(9, cenpersona.getDireccion());
            pst.setInt(10, cenpersona.getMun_id());
            pst.setString(11, cenpersona.getTelefono());
            pst.setString(12, cenpersona.getMail());
            pst.setInt(13, cenpersona.getGruposanguineo());
            pst.setString(14, cenpersona.getNumerolicenciaconduccion());
            pst.setDate(15, cenpersona.getFechaexp());
            pst.setDate(16, cenpersona.getFechaven());
            pst.setInt(17, cenpersona.getCategorialicencia());
            pst.setString(18, cenpersona.getLicenciaconduccion());
            pst.executeUpdate();
            try (ResultSet rst = pst.getGeneratedKeys()) {
                if (rst != null) {
                    if (rst.next()) {
                        return rst.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en adicionarPersona: " + e);
        }
        return 0;
    }

    public boolean modificarPersona(Connection conex, CenPersona cenpersona) throws SQLException, IOException {

        String sql = "UPDATE CEN_PERSONAS SET PER_TIPODOC = ?,PER_DOCUMENTO = ?,"
                + "PER_NOMBRE1 = ?,PER_NOMBRE2 = ?,PER_APELLIDO1 = ?,PER_APELLIDO2 = ?,PER_FECHANAC = ?,"
                + "PER_GENERO = ?,PER_DIRECCION = ?,MUN_ID = ?,PER_TELEFONO = ?,PER_MAIL = ?,PER_GRUPOSANGUINEO = ?,"
                + "PER_LICONDUCCION = ?,PER_FEXPLIC = ?,PER_FVENLIC = ?,"
                + "PER_CATLIC = ?, LICENCIA_CONDUCCION = ? WHERE PER_ID = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, cenpersona.getTipodocumento());
            pst.setString(2, cenpersona.getDocumento());
            pst.setString(3, cenpersona.getNombre1());
            pst.setString(4, cenpersona.getNombre2());
            pst.setString(5, cenpersona.getApellido1());
            pst.setString(6, cenpersona.getApellido2());
            pst.setDate(7, cenpersona.getFechanacimiento());
            pst.setInt(8, cenpersona.getGenero());
            pst.setString(9, cenpersona.getDireccion());
            pst.setInt(10, cenpersona.getMun_id());
            pst.setString(11, cenpersona.getTelefono());
            pst.setString(12, cenpersona.getMail());
            pst.setInt(13, cenpersona.getGruposanguineo());
            pst.setString(14, cenpersona.getNumerolicenciaconduccion());
            pst.setDate(15, cenpersona.getFechaexp());
            pst.setDate(16, cenpersona.getFechaven());
            pst.setInt(17, cenpersona.getCategorialicencia());
            pst.setString(18, cenpersona.getLicenciaconduccion());
            pst.setInt(19, cenpersona.getId());
            pst.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.err.println("Error en modificarPersona: " + e);
        }

        return false;
    }

    public CenPersona ConsultarPersona(Connection conex, int tipodoc, String documento) throws SQLException {

        String sql = "SELECT * FROM CEN_PERSONAS WHERE PER_TIPODOC = ? AND PER_DOCUMENTO = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, tipodoc);
            pst.setString(2, documento);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenPersona.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en consultarPersona: " + e);
        }
        return null;
    }

    public CenPersona ConsultarPersonaById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_PERSONAS WHERE PER_ID = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenPersona.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en consultarPersonaById: " + e);
        }
        return null;
    }

    public List<HashMap<String, Object>> ListarPersonas(Connection conex) throws SQLException {

        List<HashMap<String, Object>> lista = new LinkedList<>();

        String sql = "SELECT PER_ID, TIPO_DOC, DOCUMENTO, NOMBRE_COMPLETO, \n"
                + "FECHA_NAC, DIRECCION, DEPARTAMENTO, MUNICIPIO, \n"
                + "TELEFONO, MAIL\n"
                + "FROM VW_PERSONAS";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, Object> hash = new HashMap<>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                }
                lista.add(hash);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarPersonas: " + e);
        }
        return lista;
    }

    public HashMap<String, Object> ConsultarDatosPersonaById(Connection conex, int id) throws SQLException {

        HashMap<String, Object> datos = new HashMap<>();

        String sql = "SELECT * FROM VW_PERSONAS WHERE PER_ID = ? ";
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
            throw new SQLException("Error en ConsultarDatosPersonaById: " + e);
        }
        return datos;
    }

}
