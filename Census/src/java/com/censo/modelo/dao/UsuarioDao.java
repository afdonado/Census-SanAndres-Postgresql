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

public class UsuarioDao extends Conexion {

    private ResultSet rst = null;
    private PreparedStatement pst = null;

    public CenUsuario ConsultarUsuario(Connection conex, String usuario, String password, int estado) throws SQLException, IOException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_USUARIOS WHERE USU_NOMBRE = ? AND USU_PASS = ? AND EST_ID = ? AND USU_FECHAFINAL IS NULL ");
            pst.setString(1, usuario);
            pst.setString(2, password);
            pst.setInt(3, estado);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenUsuario.load(rst);
            }

        } catch (SQLException e) {
            System.err.println("Error en consultarUsuario: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de consultarUsuario:" + e);
            }
        }
        return null;
    }

    public List ListarPermisosById(Connection conex, long id) throws SQLException, IOException {
        List listaPermisos = new LinkedList();

        try {
            pst = conex.prepareStatement("SELECT PM.PRM_DESCRIPCION FROM CEN_USUARIOS U \n"
                    + "INNER JOIN CEN_PERFIL_USUARIO PU ON PU.EST_ID=1 AND PU.USU_ID=U.USU_ID  \n"
                    + "INNER JOIN CEN_PERFIL_PERMISO PP ON PP.EST_ID=1 AND PP.PEF_ID=PU.PEF_ID \n"
                    + "INNER JOIN CEN_PERMISOS PM ON PM.EST_ID=1 AND PM.PRM_ID=PP.PRM_ID \n"
                    + "WHERE U.EST_ID=1 AND U.USU_ID=? ORDER BY 1");
            pst.setLong(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                listaPermisos.add(rst.getString(1));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarPermisosById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarPermisosById:" + e);
            }

        }
        return listaPermisos;
    }

    public List ListarModulosByUsuario(Connection conex, long id) throws SQLException, IOException {
        List listaModulos = new LinkedList();

        try {
            pst = conex.prepareStatement("SELECT M.MOD_ID, M.MOD_ORDEN FROM CEN_MODULOS M \n"
                    + "INNER JOIN CEN_PERMISOS P ON P.EST_ID=1 AND P.MOD_ID=M.MOD_ID \n"
                    + "INNER JOIN CEN_PERFIL_PERMISO PP ON PP.EST_ID=1 AND PP.PRM_ID=P.PRM_ID \n"
                    + "INNER JOIN CEN_PERFIL_USUARIO PU ON PU.EST_ID=1 AND PU.PEF_ID=PP.PEF_ID \n"
                    + "INNER JOIN CEN_USUARIOS U ON U.EST_ID=1 AND U.USU_ID=PU.USU_ID AND U.USU_ID=? \n"
                    + "WHERE M.EST_ID=1 AND M.MOD_ID NOT IN(1) \n"
                    + "GROUP BY M.MOD_ID, M.MOD_ORDEN ORDER BY M.MOD_ORDEN");
            pst.setLong(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                listaModulos.add(rst.getInt(1));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarModulosByUsuario: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarModulosByUsuario:" + e);
            }
        }
        return listaModulos;
    }

    public List<CenModulo> ListarModulos(Connection conex) throws SQLException {

        List<CenModulo> listaModulos = new LinkedList();
        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_MODULOS WHERE EST_ID = 1 ORDER BY MOD_ORDEN ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaModulos.add(CenModulo.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarModulos: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarModulos:" + e);
            }
        }
        return listaModulos;
    }

    public long adicionarUsuario(Connection conex, CenUsuario cenusuario) throws SQLException, IOException {

        try {
            pst = conex.prepareStatement("INSERT INTO CEN_USUARIOS (USU_NOMBRE,USU_PASS,USU_FECHAINICIO,EST_ID,USU_FECHAPROCESO,TIPO_DOCUMENTO,NUMERO_DOCUMENTO) VALUES (?,?,SYSDATE,?,SYSDATE,?,?)", new String[]{"USU_ID"});
            pst.setString(1, cenusuario.getNombre());
            pst.setString(2, cenusuario.getPassword());
            pst.setInt(3, cenusuario.getEstado());
            pst.setInt(4, cenusuario.getTipodocumento());
            pst.setString(5, cenusuario.getNumerodocumento());
            pst.executeUpdate();
            rst = pst.getGeneratedKeys();
            if (rst != null) {
                if (rst.next()) {
                    return rst.getLong(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en adicionarUsuario: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de adicionarUsuario:" + e);
            }
        }
        return 0;
    }

    public CenUsuario ConsultarUsuarioByNombre(Connection conex, String nomusuario) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_USUARIOS WHERE USU_NOMBRE = ? ");
            pst.setString(1, nomusuario);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenUsuario.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en consultarUsuarioByNombre: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de consultarUsuarioByNombre:" + e);
            }
        }
        return null;
    }

    public List ListarPermisosByUsuarioModulo(Connection conex, long id, int idmodulo) throws SQLException {

        List listaPermisos = new LinkedList();

        try {
            pst = conex.prepareStatement("SELECT P.* FROM CEN_PERMISOS P\n"
                    + "INNER JOIN CEN_PERFIL_PERMISO PP ON PP.EST_ID=1 AND PP.PRM_ID=P.PRM_ID\n"
                    + "INNER JOIN CEN_PERFIL_USUARIO PU ON PU.EST_ID=1 AND PU.PEF_ID=PP.PEF_ID \n"
                    + "INNER JOIN CEN_USUARIOS U ON U.EST_ID=1 AND U.USU_ID=PU.USU_ID AND U.USU_ID=? \n"
                    + "WHERE P.EST_ID=1 AND P.PRM_TIPO=1 AND P.MOD_ID=? ORDER BY 1 ");
            pst.setLong(1, id);
            pst.setInt(2, idmodulo);
            rst = pst.executeQuery();

            while (rst.next()) {
                listaPermisos.add(CenPermiso.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarPermisosByUsuarioModulo: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarPermisosByUsuarioModulo:" + e);
            }
        }
        return listaPermisos;
    }

    public CenUsuario ConsultarUsuarioById(Connection conex, long id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_USUARIOS WHERE USU_ID = ? ");
            pst.setLong(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenUsuario.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarUsuarioById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarUsuarioById:" + e);
            }
        }
        return null;
    }

    public void modificarUsuario(Connection conex, CenUsuario cenusuario) throws SQLException, IOException {

        try {
            pst = conex.prepareStatement("UPDATE CEN_USUARIOS SET USU_PASS = ?,USU_FECHAFINAL = ?,EST_ID = ? WHERE USU_ID = ? ");
            pst.setString(1, cenusuario.getPassword());
            pst.setDate(2, cenusuario.getFechafin());
            pst.setInt(3, cenusuario.getEstado());
            pst.setLong(4, cenusuario.getId());
            pst.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error en modificarUsuario: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de modificarUsuario:" + e);
            }
        }
    }

    public void restaurarPasswordUsuario(Connection conex, long id, String newpassword) throws SQLException, IOException {

        try {
            pst = conex.prepareStatement("UPDATE CEN_USUARIOS SET USU_PASS = ? WHERE USU_ID = ? ");
            pst.setString(1, newpassword);
            pst.setLong(2, id);
            pst.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error en restaurarPasswordUsuario: " + e);
            throw e;
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de restaurarPasswordUsuario:" + e);
            }
        }
    }

    public List ListarUsuariosByNombre(Connection conex, String nombre) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT USU_ID, NOMBRE_USUARIO,TO_CHAR(FECHA_INICIO,'dd/MM/yyyy') FECHA_INICIO,NVL(TO_CHAR(FECHA_FINAL,'dd/MM/yyyy'),' ') FECHA_FINAL,TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,"
                    + "PEF_ID,PERFIL,PUN_ID,NVL(PUNTO_ATENCION,'SIN PUNTO') PUNTO_ATENCION,ESTADO,PER_ID,TDOC_ID,TIPO_DOC,DOCUMENTO,NOMBRE "
                    + "FROM VW_USUARIOS VU WHERE VU.NOMBRE_USUARIO LIKE ? ORDER BY NOMBRE_USUARIO");
            pst.setString(1, "%" + nombre + "%");
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
            throw new SQLException("Error en ListarUsuariosByNombre: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarUsuariosByNombre:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarUsuariosByPersona(Connection conex, int tipodoc, String documento) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT USU_ID, NOMBRE_USUARIO,TO_CHAR(FECHA_INICIO,'dd/MM/yyyy') FECHA_INICIO,NVL(TO_CHAR(FECHA_FINAL,'dd/MM/yyyy'),' ') FECHA_FINAL,TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,"
                    + "PEF_ID,PERFIL,PUN_ID,NVL(PUNTO_ATENCION,'SIN PUNTO') PUNTO_ATENCION,ESTADO,PER_ID,TDOC_ID,TIPO_DOC,DOCUMENTO,NOMBRE "
                    + "FROM VW_USUARIOS VU WHERE VU.TDOC_ID = ? AND VU.DOCUMENTO=? ORDER BY VU.FECHA_INICIO");
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
            throw new SQLException("Error en ListarUsuariosByPersona: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarUsuariosByPersona:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarUsuariosByPunto(Connection conex, int punto) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT USU_ID, NOMBRE_USUARIO,TO_CHAR(FECHA_INICIO,'dd/MM/yyyy') FECHA_INICIO,NVL(TO_CHAR(FECHA_FINAL,'dd/MM/yyyy'),' ') FECHA_FINAL,"
                    + "TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,PEF_ID,PERFIL,PUN_ID,NVL(PUNTO_ATENCION,'SIN PUNTO') PUNTO_ATENCION,ESTADO,PER_ID,TDOC_ID,TIPO_DOC,DOCUMENTO,NOMBRE "
                    + "FROM VW_USUARIOS VU WHERE (VU.PUN_ID = ? OR 0 = ?) ORDER BY VU.FECHA_INICIO");
            pst.setInt(1, punto);
            pst.setInt(2, punto);
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
            throw new SQLException("Error en ListarUsuariosByPunto: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarUsuariosByPunto:" + e);
            }
        }
        return listaDatos;
    }

    public CenPerfilUsuario ConsultarPerfilUsuarioByIdUsuario(Connection conex, long id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_PERFIL_USUARIO WHERE PFU_FECHAFINAL IS NULL AND USU_ID = ? ");
            pst.setLong(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenPerfilUsuario.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarPerfilUsuarioByIdUsuario: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarPerfilUsuarioByIdUsuario:" + e);
            }
        }
        return null;
    }

    public List ListarPerfiles(Connection conex) throws SQLException {

        List listaPerfil = new LinkedList();

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_PERFILES WHERE EST_ID = 1 ORDER BY PEF_ID ");
            rst = pst.executeQuery();

            while (rst.next()) {
                listaPerfil.add(CenPerfil.load(rst));
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarPerfiles: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarPerfiles:" + e);
            }
        }
        return listaPerfil;
    }

    public CenPerfil ConsultarPerfilById(Connection conex, int id) throws SQLException {

        try {
            pst = conex.prepareStatement("SELECT * FROM CEN_PERFILES WHERE EST_ID = 1 AND PEF_ID = ? ORDER BY PEF_ID ");
            pst.setInt(1, id);
            rst = pst.executeQuery();

            while (rst.next()) {
                return CenPerfil.load(rst);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarPerfilById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarPerfilById:" + e);
            }
        }
        return null;
    }

    public long adicionarPerfilUsuario(Connection conex, CenPerfilUsuario cenperfilusuario) {

        try {
            pst = conex.prepareStatement("INSERT INTO CEN_PERFIL_USUARIO (PEF_ID,USU_ID,PFU_FECHAINICIAL,PFU_FECHAPROCESO,EST_ID) VALUES (?,?,SYSDATE,SYSDATE,?)", new String[]{"PFU_ID"});
            pst.setLong(1, cenperfilusuario.getPef_id());
            pst.setLong(2, cenperfilusuario.getUsu_id());
            pst.setInt(3, cenperfilusuario.getEstado());
            pst.executeUpdate();
            rst = pst.getGeneratedKeys();
            if (rst != null) {
                if (rst.next()) {
                    return rst.getLong(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en adicionarPerfilUsuario: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de adicionarPerfilUsuario:" + e);
            }
        }
        return 0;
    }

    public void modificarPerfilUsuario(Connection conex, CenPerfilUsuario cenperfilusuario) throws SQLException, IOException {

        try {
            pst = conex.prepareStatement("UPDATE CEN_PERFIL_USUARIO SET PEF_ID = ?, PFU_FECHAFINAL = ?, EST_ID = ? WHERE PFU_ID = ? ");
            pst.setLong(1, cenperfilusuario.getPef_id());
            pst.setDate(2, cenperfilusuario.getFechafin());
            pst.setInt(3, cenperfilusuario.getEstado());
            pst.setLong(4, cenperfilusuario.getId());
            pst.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error en modificarPerfilUsuario: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de modificarPerfilUsuario:" + e);
            }
        }
    }
    
    
    public HashMap<String, String> ConsultarDatosUsuarioById(Connection conex, long id) throws SQLException {

        HashMap<String, String> datos = new HashMap<>();

        try {
            pst = conex.prepareStatement("SELECT USU_ID, NOMBRE_USUARIO,TO_CHAR(FECHA_INICIO,'dd/MM/yyyy') FECHA_INICIO,NVL(TO_CHAR(FECHA_FINAL,'dd/MM/yyyy'),' ') FECHA_FINAL,"
                    + "TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,PEF_ID,PERFIL, TIPO_DOCUMENTO_ID, TIPO_DOCUMENTO, TIPO_DOCUMENTO_CORTO,"
                    + "NUMERO_DOCUMENTO, ESTADO_ID, ESTADO FROM VW_USUARIOS "
                    + "WHERE USU_ID = ? ");
            pst.setLong(1, id);
            rst = pst.executeQuery();

            if (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    datos.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ConsultarDatosUsuarioById: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ConsultarDatosUsuarioById:" + e);
            }
        }
        return datos;
    }
    
    public List ListarUsuarios(Connection conex) throws SQLException {

        List<HashMap> lista = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT USU_ID, NOMBRE_USUARIO,TO_CHAR(FECHA_INICIO,'dd/MM/yyyy') FECHA_INICIO,NVL(TO_CHAR(FECHA_FINAL,'dd/MM/yyyy'),' ') FECHA_FINAL, "
                    + "TO_CHAR(FECHA_PROCESO,'dd/MM/yyyy') FECHA_PROCESO,PEF_ID,PERFIL, TIPO_DOCUMENTO_ID, TIPO_DOCUMENTO, TIPO_DOCUMENTO_CORTO, "
                    + "NUMERO_DOCUMENTO, ESTADO_ID, ESTADO FROM VW_USUARIOS");
            rst = pst.executeQuery();

            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, String> hash = new HashMap<>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                }
                lista.add(hash);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en ListarUsuarios: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarUsuarios:" + e);
            }
        }
        return lista;
    }

}
