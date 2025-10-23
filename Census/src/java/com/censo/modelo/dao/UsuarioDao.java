package com.censo.modelo.dao;

import com.censo.modelo.persistencia.CenModulo;
import com.censo.modelo.persistencia.CenPerfil;
import com.censo.modelo.persistencia.CenPerfilUsuario;
import com.censo.modelo.persistencia.CenPermiso;
import com.censo.modelo.persistencia.CenUsuario;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class UsuarioDao {

    public CenUsuario ConsultarUsuario(Connection conex, String usuario, String password, int estado) throws SQLException, IOException {

        String sql = "select * from cen_usuarios where usu_nombre = ? and usu_pass = ? and est_id = ? and usu_fechafinal is null";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, usuario);
            pst.setString(2, password);
            pst.setInt(3, estado);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenUsuario.load(rst);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en consultarUsuario: " + e);
        }
        return null;
    }

    public List ListarPermisosById(Connection conex, int id) throws SQLException, IOException {

        List listaPermisos = new LinkedList();

        String sql = "select pm.prm_descripcion from cen_usuarios u "
                + "inner join cen_perfil_usuario pu on pu.est_id=1 and pu.usu_id=u.usu_id "
                + "inner join cen_perfil_permiso pp on pp.est_id=1 and pp.pef_id=pu.pef_id "
                + "inner join cen_permisos pm on pm.est_id=1 and pm.prm_id=pp.prm_id "
                + "where u.est_id=1 and u.usu_id=? order by 1";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    listaPermisos.add(rst.getString(1));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarPermisosById: " + e);
        }
        return listaPermisos;
    }

    public List ListarModulosByUsuario(Connection conex, int id) throws SQLException, IOException {

        List listaModulos = new LinkedList();

        String sql = "select m.mod_id, m.mod_orden from cen_modulos m "
                + "inner join cen_permisos p on p.est_id=1 and p.mod_id=m.mod_id "
                + "inner join cen_perfil_permiso pp on pp.est_id=1 and pp.prm_id=p.prm_id "
                + "inner join cen_perfil_usuario pu on pu.est_id=1 and pu.pef_id=pp.pef_id "
                + "inner join cen_usuarios u on u.est_id=1 and u.usu_id=pu.usu_id and u.usu_id=? "
                + "where m.est_id=1 and m.mod_id not in(1) "
                + "group by m.mod_id, m.mod_orden order by m.mod_orden";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    listaModulos.add(rst.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarModulosByUsuario: " + e);
        }
        return listaModulos;
    }

    public List<CenModulo> ListarModulos(Connection conex) throws SQLException {

        List<CenModulo> listaModulos = new LinkedList();
        String sql = "select * from cen_modulos where est_id = 1 order by mod_orden";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                listaModulos.add(CenModulo.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarModulos: " + e);
        }
        return listaModulos;
    }

    public int adicionarUsuario(Connection conex, CenUsuario cenusuario) throws SQLException, IOException {

        String sql = "INSERT INTO CEN_USUARIOS (USU_NOMBRE,USU_PASS,USU_FECHAINICIO,EST_ID,USU_FECHAPROCESO,TIPO_DOCUMENTO,NUMERO_DOCUMENTO) VALUES (?,?,now(),?,now(),?,?)";
        try (PreparedStatement pst = conex.prepareStatement(sql, new String[]{"usu_id"})) {
            pst.setString(1, cenusuario.getNombre());
            pst.setString(2, cenusuario.getPassword());
            pst.setInt(3, cenusuario.getEstado());
            pst.setInt(4, cenusuario.getTipodocumento());
            pst.setString(5, cenusuario.getNumerodocumento());
            pst.executeUpdate();
            try (ResultSet rst = pst.getGeneratedKeys()) {
                if (rst != null) {
                    if (rst.next()) {
                        return rst.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en adicionarUsuario: " + e);
        }
        return 0;
    }

    public CenUsuario ConsultarUsuarioByNombre(Connection conex, String nomusuario) throws SQLException {

        String sql = "SELECT * FROM CEN_USUARIOS WHERE USU_NOMBRE = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, nomusuario);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenUsuario.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en consultarUsuarioByNombre: " + e);
        }
        return null;
    }

    public List ListarPermisosByUsuarioModulo(Connection conex, int id, int idmodulo) throws SQLException {

        List listaPermisos = new LinkedList();

        String sql = "SELECT P.* FROM CEN_PERMISOS P\n"
                + "INNER JOIN CEN_PERFIL_PERMISO PP ON PP.EST_ID=1 AND PP.PRM_ID=P.PRM_ID\n"
                + "INNER JOIN CEN_PERFIL_USUARIO PU ON PU.EST_ID=1 AND PU.PEF_ID=PP.PEF_ID \n"
                + "INNER JOIN CEN_USUARIOS U ON U.EST_ID=1 AND U.USU_ID=PU.USU_ID AND U.USU_ID=? \n"
                + "WHERE P.EST_ID=1 AND P.PRM_TIPO=1 AND P.MOD_ID=? ORDER BY 1 ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.setInt(2, idmodulo);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    listaPermisos.add(CenPermiso.load(rst));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarPermisosByUsuarioModulo: " + e);
        }
        return listaPermisos;
    }

    public CenUsuario ConsultarUsuarioById(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_USUARIOS WHERE USU_ID = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenUsuario.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarUsuarioById: " + e);
        }
        return null;
    }

    public boolean modificarUsuario(Connection conex, CenUsuario cenusuario) throws SQLException, IOException {

        String sql = "UPDATE CEN_USUARIOS SET USU_PASS = ?,USU_FECHAFINAL = ?,EST_ID = ?, TIPO_DOCUMENTO = ?, NUMERO_DOCUMENTO = ? WHERE USU_ID = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, cenusuario.getPassword());
            pst.setDate(2, cenusuario.getFechafin());
            pst.setInt(3, cenusuario.getEstado());
            pst.setInt(4, cenusuario.getTipodocumento());
            pst.setString(5, cenusuario.getNumerodocumento());
            pst.setInt(6, cenusuario.getId());
            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error en modificarUsuario: " + e);
        }
        return false;
    }

    public boolean restaurarPasswordUsuario(Connection conex, int id, String newpassword) throws SQLException, IOException {

        String sql = "UPDATE CEN_USUARIOS SET USU_PASS = ? WHERE USU_ID = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, newpassword);
            pst.setInt(2, id);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error en restaurarPasswordUsuario: " + e);
        }
        return false;
    }

    public List ListarUsuariosByNombre(Connection conex, String nombre) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        String sql = "SELECT USU_ID, NOMBRE_USUARIO,TO_CHAR(FECHA_INICIO,'dd/MM/yyyy') FECHA_INICIO,COALESCE(TO_CHAR(FECHA_FINAL,'dd/MM/yyyy'),' ') FECHA_FINAL,TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,"
                + "PEF_ID,PERFIL,PUN_ID,COALESCE(PUNTO_ATENCION,'SIN PUNTO') PUNTO_ATENCION,ESTADO,PER_ID,TDOC_ID,TIPO_DOC,DOCUMENTO,NOMBRE "
                + "FROM VW_USUARIOS VU WHERE VU.NOMBRE_USUARIO LIKE ? ORDER BY NOMBRE_USUARIO";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, "%" + nombre + "%");
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    ResultSetMetaData rsmd = rst.getMetaData();
                    HashMap<String, String> hash = new HashMap<>();
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                    }
                    listaDatos.add(hash);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarUsuariosByNombre: " + e);
        }
        return listaDatos;
    }

    public List ListarUsuariosByPersona(Connection conex, int tipodoc, String documento) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        String sql = "SELECT USU_ID, NOMBRE_USUARIO,TO_CHAR(FECHA_INICIO,'dd/MM/yyyy') FECHA_INICIO,COALESCE(TO_CHAR(FECHA_FINAL,'dd/MM/yyyy'),' ') FECHA_FINAL,TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,"
                + "PEF_ID,PERFIL,PUN_ID,COALESCE(PUNTO_ATENCION,'SIN PUNTO') PUNTO_ATENCION,ESTADO,PER_ID,TDOC_ID,TIPO_DOC,DOCUMENTO,NOMBRE "
                + "FROM VW_USUARIOS VU WHERE VU.TDOC_ID = ? AND VU.DOCUMENTO=? ORDER BY VU.FECHA_INICIO";
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
                    listaDatos.add(hash);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarUsuariosByPersona: " + e);
        }
        return listaDatos;
    }

    public List ListarUsuariosByPunto(Connection conex, int punto) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        String sql = "SELECT USU_ID, NOMBRE_USUARIO,TO_CHAR(FECHA_INICIO,'dd/MM/yyyy') FECHA_INICIO,COALESCE(TO_CHAR(FECHA_FINAL,'dd/MM/yyyy'),' ') FECHA_FINAL,"
                + "TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,PEF_ID,PERFIL,PUN_ID,COALESCE(PUNTO_ATENCION,'SIN PUNTO') PUNTO_ATENCION,ESTADO,PER_ID,TDOC_ID,TIPO_DOC,DOCUMENTO,NOMBRE "
                + "FROM VW_USUARIOS VU WHERE (VU.PUN_ID = ? OR 0 = ?) ORDER BY VU.FECHA_INICIO";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, punto);
            pst.setInt(2, punto);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    ResultSetMetaData rsmd = rst.getMetaData();
                    HashMap<String, String> hash = new HashMap<>();
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                    }
                    listaDatos.add(hash);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarUsuariosByPunto: " + e);
        }
        return listaDatos;
    }

    public CenPerfilUsuario ConsultarPerfilUsuarioByIdUsuario(Connection conex, int id) throws SQLException {

        String sql = "SELECT * FROM CEN_PERFIL_USUARIO WHERE PFU_FECHAFINAL IS NULL AND USU_ID = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenPerfilUsuario.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarPerfilUsuarioByIdUsuario: " + e);
        }
        return null;
    }

    public List ListarPerfiles(Connection conex) throws SQLException {

        List listaPerfil = new LinkedList();

        String sql = "SELECT * FROM CEN_PERFILES WHERE EST_ID = 1 ORDER BY PEF_ID ";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
            while (rst.next()) {
                listaPerfil.add(CenPerfil.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarPerfiles: " + e);
        }
        return listaPerfil;
    }

    public CenPerfil ConsultarPerfilById(Connection conex, int id) throws SQLException {

        String sql = "select * from cen_perfiles where est_id = 1 and pef_id = ? order by pef_id";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                while (rst.next()) {
                    return CenPerfil.load(rst);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarPerfilById: " + e);
        }
        return null;
    }

    public int adicionarPerfilUsuario(Connection conex, CenPerfilUsuario cenperfilusuario) {

        String sql = "INSERT INTO CEN_PERFIL_USUARIO (PEF_ID,USU_ID,PFU_FECHAINICIAL,PFU_FECHAPROCESO,EST_ID) "
                + "VALUES (?,?,now(),now(),?)";
        try (PreparedStatement pst = conex.prepareStatement(sql, new String[]{"pfu_id"})) {
            pst.setInt(1, cenperfilusuario.getPef_id());
            pst.setInt(2, cenperfilusuario.getUsu_id());
            pst.setInt(3, cenperfilusuario.getEstado());
            pst.executeUpdate();
            try (ResultSet rst = pst.getGeneratedKeys()) {
                if (rst != null) {
                    if (rst.next()) {
                        return rst.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en adicionarPerfilUsuario: " + e);
        }
        return 0;
    }

    public boolean modificarPerfilUsuario(Connection conex, CenPerfilUsuario cenperfilusuario) throws SQLException, IOException {

        String sql = "UPDATE CEN_PERFIL_USUARIO SET PEF_ID = ?, PFU_FECHAFINAL = ?, EST_ID = ? WHERE PFU_ID = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, cenperfilusuario.getPef_id());
            pst.setDate(2, cenperfilusuario.getFechafin());
            pst.setInt(3, cenperfilusuario.getEstado());
            pst.setInt(4, cenperfilusuario.getId());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error en modificarPerfilUsuario: " + e);
        }
        return false;
    }

    public HashMap<String, Object> ConsultarDatosUsuarioById(Connection conex, int id) throws SQLException {

        HashMap<String, Object> datos = new HashMap<>();

        String sql = "SELECT USU_ID, NOMBRE_USUARIO,TO_CHAR(FECHA_INICIO,'dd/MM/yyyy') FECHA_INICIO,COALESCE(TO_CHAR(FECHA_FINAL,'dd/MM/yyyy'),' ') FECHA_FINAL,"
                + "TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,PEF_ID,PERFIL, TIPO_DOCUMENTO_ID, TIPO_DOCUMENTO, TIPO_DOCUMENTO_CORTO,"
                + "NUMERO_DOCUMENTO, ESTADO_ID, ESTADO FROM VW_USUARIOS "
                + "WHERE USU_ID = ? ";
        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rst = pst.executeQuery()) {
                if (rst.next()) {
                    ResultSetMetaData rsmd = rst.getMetaData();
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        datos.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarDatosUsuarioById: " + e);
        }
        return datos;
    }

    public List<HashMap<String, Object>> ListarUsuarios(Connection conex) throws SQLException {

        List<HashMap<String, Object>> lista = new LinkedList<>();

        String sql = "SELECT USU_ID, NOMBRE_USUARIO,TO_CHAR(FECHA_INICIO,'dd/MM/yyyy') FECHA_INICIO,COALESCE(TO_CHAR(FECHA_FINAL,'dd/MM/yyyy'),' ') FECHA_FINAL, "
                + "TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,PEF_ID,PERFIL, TIPO_DOCUMENTO_ID, TIPO_DOCUMENTO, TIPO_DOCUMENTO_CORTO, "
                + "NUMERO_DOCUMENTO, ESTADO_ID, ESTADO FROM VW_USUARIOS WHERE PEF_ID NOT IN(4) ";
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
            throw new SQLException("Error en ListarUsuarios: " + e);
        }
        return lista;
    }

}
