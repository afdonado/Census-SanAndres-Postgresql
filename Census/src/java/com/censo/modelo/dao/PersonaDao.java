package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenPersona;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PersonaDao {

    public int adicionarPersona(Connection conex, CenPersona cenpersona) {

        String sql = "insert into cen_personas ("
                + "per_tipodoc,per_documento,"
                + "per_nombre1,per_nombre2,per_apellido1,per_apellido2,"
                + "per_fechanac,per_genero,"
                + "per_direccion,mun_id,per_telefono,per_mail,"
                + "per_gruposanguineo,per_liconduccion,per_fexplic,per_fvenlic,"
                + "per_catlic,per_fechaproceso, licencia_conduccion) "
                + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?)";
        try (PreparedStatement pst = conex.prepareStatement(sql, new String[]{"per_id"})) {
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

        String sql = "update cen_personas set "
                + "per_tipodoc = ?,per_documento = ?, "
                + "per_nombre1 = ?,per_nombre2 = ?,per_apellido1 = ?,per_apellido2 = ?,"
                + "per_fechanac = ?,per_genero = ?,"
                + "per_direccion = ?,mun_id = ?,per_telefono = ?,per_mail = ?,"
                + "per_gruposanguineo = ?,"
                + "per_liconduccion = ?,per_fexplic = ?,per_fvenlic = ?,"
                + "per_catlic = ?, licencia_conduccion = ? where per_id = ? ";
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

    public List<HashMap<String, Object>> listarPersonasOld(Connection conex) throws SQLException {

        List<HashMap<String, Object>> lista = new LinkedList<>();

        String sql = "SELECT PER_ID, TIPO_DOC, DOCUMENTO, NOMBRE_COMPLETO, "
                + "FECHA_NAC, DIRECCION, DEPARTAMENTO, MUNICIPIO, "
                + "TELEFONO, MAIL "
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

    public List<HashMap<String, Object>> listarPersonasPaginados(Connection conex, int start, int length, String orderBy, String orderDirection) throws SQLException {
        List<HashMap<String, Object>> lista = new ArrayList<>();

        String sql = "SELECT PER_ID, TIPO_DOC, DOCUMENTO, NOMBRE_COMPLETO, FECHA_NAC, DIRECCION, DEPARTAMENTO, MUNICIPIO, TELEFONO, MAIL "
                + "FROM VW_PERSONAS ORDER BY " + orderBy + " " + orderDirection + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

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

    public List<HashMap<String, Object>> listarPersonasPaginadosFiltrados(Connection conex, int start, int length, String searchValue, String orderBy, String orderDirection) throws SQLException {
        List<HashMap<String, Object>> lista = new ArrayList<>();

        String sql = "SELECT PER_ID, TIPO_DOC, DOCUMENTO, NOMBRE_COMPLETO, FECHA_NAC, DIRECCION, DEPARTAMENTO, MUNICIPIO, TELEFONO, MAIL "
                + "FROM VW_PERSONAS WHERE (TIPO_DOC LIKE ? OR DOCUMENTO LIKE ? OR NOMBRE_COMPLETO LIKE ? OR FECHA_NAC LIKE ? OR DIRECCION LIKE ? "
                + "OR DEPARTAMENTO LIKE ? OR MUNICIPIO LIKE ? OR TELEFONO LIKE ? OR MAIL LIKE ?) "
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
            pst.setInt(10, start);
            pst.setInt(11, length);

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

    public int contarPersonas(Connection conex) throws SQLException {
        String sql = "SELECT COUNT(*) FROM VW_PERSONAS";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            if (rst.next()) {
                return rst.getInt(1);
            }
        }
        return 0;
    }

    public int contarPersonasFiltrados(Connection conex, String searchValue) throws SQLException {
        String sql = "SELECT COUNT(*) FROM VW_PERSONAS WHERE (TIPO_DOC LIKE ? OR DOCUMENTO LIKE ? OR NOMBRE_COMPLETO LIKE ? OR FECHA_NAC LIKE ? OR DIRECCION LIKE ? "
                + "OR DEPARTAMENTO LIKE ? OR MUNICIPIO LIKE ? OR TELEFONO LIKE ? OR MAIL LIKE ?)";
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
            try (ResultSet rst = pst.executeQuery()) {
                if (rst.next()) {
                    return rst.getInt(1);
                }
            }
        }
        return 0;
    }

}
