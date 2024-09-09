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

public class PersonaDao extends Conexion {
    
    private ResultSet rst = null;
    private PreparedStatement pst = null;
    
    public int adicionarPersona(Connection conex, CenPersona cenpersona) {

        try {
            pst = conex.prepareStatement("INSERT INTO CEN_PERSONAS (PER_TIPODOC,PER_DOCUMENTO,"
                    + "PER_NOMBRE1,PER_NOMBRE2,PER_APELLIDO1,PER_APELLIDO2,PER_FECHANAC,PER_GENERO,PER_DIRECCION,"
                    + "MUN_ID,PER_TELEFONO,PER_MAIL,PER_GRUPOSANGUINEO,PER_LICONDUCCION,PER_FEXPLIC,PER_FVENLIC,"
                    + "PER_CATLIC,PER_FECHAPROCESO, LICENCIA_CONDUCCION) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?)", new String[]{"PER_ID"});
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
            rst = pst.getGeneratedKeys();
            if (rst != null) {
                if (rst.next()) {
                    return rst.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en adicionarPersona: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de adicionarPersona:" + e);
            }
        }
        return 0;
    }

    public boolean modificarPersona(Connection conex, CenPersona cenpersona) throws SQLException, IOException {

        try {
            pst = conex.prepareStatement("UPDATE CEN_PERSONAS SET PER_TIPODOC = ?,PER_DOCUMENTO = ?,"
                    + "PER_NOMBRE1 = ?,PER_NOMBRE2 = ?,PER_APELLIDO1 = ?,PER_APELLIDO2 = ?,PER_FECHANAC = ?,"
                    + "PER_GENERO = ?,PER_DIRECCION = ?,MUN_ID = ?,PER_TELEFONO = ?,PER_MAIL = ?,PER_GRUPOSANGUINEO = ?,"
                    + "PER_LICONDUCCION = ?,PER_FEXPLIC = ?,PER_FVENLIC = ?,"
                    + "PER_CATLIC = ?, LICENCIA_CONDUCCION = ? WHERE PER_ID = ? ");
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
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de modificarPersona:" + e);
            }
        }
        
        return false;
    }

    public CenPersona ConsultarPersona(Connection conex, int tipodoc, String documento) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_PERSONAS WHERE PER_TIPODOC = ? AND PER_DOCUMENTO = ? ");
            pst.setInt(1, tipodoc);
            pst.setString(2, documento);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenPersona.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en consultarPersona: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de consultarPersona:" + e);
            }
        }
        return null;
    }

    public CenPersona ConsultarPersonaById(Connection conex, int id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_PERSONAS WHERE PER_ID = ? ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenPersona.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en consultarPersonaById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de consultarPersonaById:" + e);
            }
        }
        return null;
    }

    public List<HashMap<String, Object>> ListarPersonas(Connection conex) throws SQLException {

        List<HashMap<String, Object>> lista = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT * FROM VW_PERSONAS ");
            rst = pst.executeQuery();

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
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarPersonas:" + e);
            }
        }
        return lista;
    }

    public List ListarPersonasById(Connection conex, int id) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT PER_ID,PER_TIPODOC,TIPO_DOC,DOCUMENTO,NOMBRE1,NOMBRE2,APELLIDO1,APELLIDO2,NOMBRE_COMPLETO,"
                    + "TO_CHAR(FECHA_NAC,'dd/MM/yyyy') FECHA_NAC,PER_GENERO,GENERO,DIRECCION,MUN_ID,MUNICIPIO,DEPT_ID,DEPARTAMENTO,TELEFONO,"
                    + "MAIL,ID_GRUPOSAN,GRUPO_SANGUINEO,LIC_CONDUCCION,FECHA_EXP,FECHA_VEN,PER_CATLIC,CATEGORIA_LIC,TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO "
                    + "FROM VW_PERSONAS WHERE PER_ID = ? ");
            pst.setInt(1, id);
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
            throw new SQLException("Error en ListarPersonasById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarPersonasById:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarPersonasByDocumento(Connection conex, int tipodoc, String documento) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT PER_ID,PER_TIPODOC,TIPO_DOC,DOCUMENTO,NOMBRE1,NOMBRE2,APELLIDO1,APELLIDO2,NOMBRE_COMPLETO,"
                    + "TO_CHAR(FECHA_NAC,'dd/MM/yyyy') FECHA_NAC,PER_GENERO,GENERO,DIRECCION,MUN_ID,MUNICIPIO,DEPT_ID,DEPARTAMENTO,TELEFONO,MAIL,ID_GRUPOSAN,GRUPO_SANGUINEO,"
                    + "LIC_CONDUCCION,FECHA_EXP,FECHA_VEN,PER_CATLIC,CATEGORIA_LIC,TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO "
                    + "FROM VW_PERSONAS WHERE PER_TIPODOC = ? AND DOCUMENTO=? ");
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
            throw new SQLException("Error en ListarPersonasByDocumento: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarPersonasByDocumento:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarPersonasByNombres(Connection conex, String nombre1, String nombre2, String apellido1, String apellido2) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT PER_ID,PER_TIPODOC,TIPO_DOC,DOCUMENTO,NOMBRE1,NOMBRE2,APELLIDO1,APELLIDO2,NOMBRE_COMPLETO,"
                    + "TO_CHAR(FECHA_NAC,'dd/MM/yyyy') FECHA_NAC,PER_GENERO,GENERO,DIRECCION,MUN_ID,MUNICIPIO,DEPT_ID,DEPARTAMENTO,TELEFONO,MAIL,ID_GRUPOSAN,GRUPO_SANGUINEO,"
                    + "LIC_CONDUCCION,FECHA_EXP,FECHA_VEN,PER_CATLIC,CATEGORIA_LIC,TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO "
                    + "FROM VW_PERSONAS WHERE NOMBRE1 LIKE ? AND NOMBRE2 LIKE ? AND APELLIDO1 LIKE ? AND APELLIDO2 LIKE ? ");
            pst.setString(1, nombre1 + "%");
            pst.setString(2, nombre2 + "%");
            pst.setString(3, apellido1 + "%");
            pst.setString(4, apellido2 + "%");
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
            throw new SQLException("Error en ListarPersonasByNombres: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarPersonasByNombres:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarPersonasByFechaNacimiento(Connection conex, Date fechanacini, Date fechanacfin) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT PER_ID,PER_TIPODOC,TIPO_DOC,DOCUMENTO,NOMBRE1,NOMBRE2,APELLIDO1,APELLIDO2,NOMBRE_COMPLETO,"
                    + "TO_CHAR(FECHA_NAC,'dd/MM/yyyy') FECHA_NAC,PER_GENERO,GENERO,DIRECCION,MUN_ID,MUNICIPIO,DEPT_ID,DEPARTAMENTO,TELEFONO,MAIL,ID_GRUPOSAN,GRUPO_SANGUINEO,"
                    + "LIC_CONDUCCION,FECHA_EXP,FECHA_VEN,PER_CATLIC,CATEGORIA_LIC,TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO "
                    + "FROM VW_PERSONAS WHERE to_date(to_char(FECHA_NAC,'dd/MM/yyyy')) BETWEEN ? AND ? ORDER BY FECHA_NAC ");
            pst.setDate(1, fechanacini);
            pst.setDate(2, fechanacfin);
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
            throw new SQLException("Error en ListarPersonasByFechaNacimiento: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarPersonasByFechaNacimiento:" + e);
            }
        }
        return listaDatos;
    }
    
    public HashMap<String, Object> ConsultarDatosPersonaById(Connection conex, int id) throws SQLException {

        HashMap<String, Object> datos = new HashMap<>();

        try {
            pst = conex.prepareStatement("SELECT * FROM VW_PERSONAS WHERE PER_ID = ? ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            if (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    datos.put(rsmd.getColumnName(i + 1), rst.getObject(i + 1));
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarDatosPersonaById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarDatosPersonaById:" + e);
            }
        }
        return datos;
    }
    
}
