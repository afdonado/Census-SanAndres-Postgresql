package com.censo.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class EstadisticaDao extends Conexion {

    private ResultSet rst = null;
    private PreparedStatement pst = null;

    public List ListarCantidadCensosClaveVehiculo(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT CV.CLV_ID CLV_ID, CV.CLV_DESCRIPCION CLV_DESCRIPCION, COUNT(V.VEH_CLASE) CANTIDAD,"
                    + "(SELECT COUNT(C.CEN_ID) FROM CEN_CENSOS C WHERE C.EST_ID=1 ) CANTIDAD_TOTAL "
                    + "FROM CEN_CENSOS CC "
                    + "INNER JOIN CEN_VEHICULOS V ON V.VEH_ID=CC.VEH_ID "
                    + "INNER JOIN CEN_CLASES_VEHICULO CV ON CV.CLV_ID=V.VEH_CLASE "
                    + "WHERE CC.EST_ID=1 "
                    + "GROUP BY CV.CLV_ID, CV.CLV_DESCRIPCION "
                    + "ORDER BY CV.CLV_DESCRIPCION");
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
            throw new SQLException("Error en ListarCantidadCensosClaveVehiculo: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCantidadCensosClaveVehiculo:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCantidadCensosPuntoAtencion(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT P.PUN_ID PUN_ID, P.PUN_NOMBRE PUN_DESCRIPCION, COUNT(CC.PUN_ID) CANTIDAD,\n"
                    + "(SELECT COUNT(C.PUN_ID) FROM CEN_CENSOS C WHERE C.EST_ID=1 AND C.PUN_ID IN(SELECT CP.PUN_ID FROM CEN_PUNTOS_ATENCION CP )) CANTIDAD_TOTAL \n"
                    + "FROM CEN_CENSOS CC \n"
                    + "INNER JOIN CEN_PUNTOS_ATENCION P ON P.PUN_ID=CC.PUN_ID \n"
                    + "WHERE CC.EST_ID=1 \n"
                    + "GROUP BY P.PUN_ID, P.PUN_NOMBRE \n"
                    + "ORDER BY P.PUN_NOMBRE");
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
            throw new SQLException("Error en ListarCantidadCensosPuntoAtencion: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCantidadCensosPuntoAtencion:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCantidadPersonasCensadasGenero(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT P.PER_GENERO, G.GEN_DESCRIPCION GENERO, COUNT(DISTINCT P.PER_ID) CANTIDAD, \n"
                    + "ROUND(((COUNT(DISTINCT P.PER_ID)/(\n"
                    + "SELECT COUNT( DISTINCT CP.PER_ID) FROM CEN_PERSONAS CP \n"
                    + "INNER JOIN CEN_PERSONA_VEHICULO CPV ON CPV.PER_ID=CP.PER_ID AND CPV.EST_ID = 1\n"
                    + "INNER JOIN CEN_CENSOS CC ON CC.EST_ID=1 AND CC.VEH_ID=CPV.VEH_ID))*100)) PORCENTAJE,\n"
                    + "(SELECT COUNT( DISTINCT CP.PER_ID) FROM CEN_PERSONAS CP WHERE CP.PER_ID IN(\n"
                    + "SELECT CPV.PER_ID FROM CEN_PERSONA_VEHICULO CPV \n"
                    + "INNER JOIN CEN_CENSOS CC ON CC.EST_ID=1 AND CC.VEH_ID=CPV.VEH_ID \n"
                    + "WHERE CPV.EST_ID = 1)) CANTIDAD_TOTAL \n"
                    + "FROM CEN_PERSONAS P \n"
                    + "INNER JOIN CEN_PERSONA_VEHICULO PV ON PV.PER_ID=P.PER_ID AND PV.EST_ID = 1\n"
                    + "INNER JOIN CEN_CENSOS C ON C.EST_ID=1 AND C.VEH_ID=PV.VEH_ID \n"
                    + "INNER JOIN CEN_GENEROS G ON G.GEN_ID=P.PER_GENERO \n"
                    + "GROUP BY P.PER_GENERO,G.GEN_DESCRIPCION");
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
            throw new SQLException("Error en ListarCantidadPersonasCensadasGenero: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCantidadPersonasCensadasGenero:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCantidadPersonasCensadasLicencia(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT 'CON LICENCIA' DESCRIPCION, \n"
                    + "ROUND(((COUNT(DISTINCT CP.PER_ID)/(\n"
                    + "SELECT COUNT( DISTINCT CP2.PER_ID) FROM CEN_PERSONAS CP2 \n"
                    + "INNER JOIN CEN_PERSONA_VEHICULO CPV2 ON CPV2.PER_ID=CP2.PER_ID\n"
                    + "INNER JOIN CEN_CENSOS CC2 ON CC2.EST_ID=1 AND CC2.VEH_ID=CPV2.VEH_ID ))*100)) PORCENTAJE, \n"
                    + "COUNT(0) CANTIDAD, (\n"
                    + "SELECT COUNT(0) FROM CEN_PERSONAS P \n"
                    + "INNER JOIN CEN_PERSONA_VEHICULO PV ON PV.PER_ID=P.PER_ID AND PV.EST_ID = 1\n"
                    + "INNER JOIN CEN_CENSOS C ON C.VEH_ID=PV.VEH_ID AND C.EST_ID=1\n"
                    + ") CANTIDAD_TOTAL\n"
                    + "FROM CEN_PERSONAS CP \n"
                    + "INNER JOIN CEN_PERSONA_VEHICULO CPV ON CPV.PER_ID=CP.PER_ID AND CPV.EST_ID = 1\n"
                    + "INNER JOIN CEN_CENSOS CC ON CC.VEH_ID=CPV.VEH_ID AND CC.EST_ID=1 \n"
                    + "WHERE CP.PER_LICONDUCCION IS NULL group by 'CON LICENCIA', 0\n"
                    + "UNION \n"
                    + "SELECT 'SIN LICENCIA' DESCRIPCION, \n"
                    + "ROUND(((COUNT(DISTINCT CP.PER_ID)/(\n"
                    + "SELECT COUNT( DISTINCT CP2.PER_ID) \n"
                    + "FROM CEN_PERSONAS CP2 \n"
                    + "INNER JOIN CEN_PERSONA_VEHICULO CPV2 ON CPV2.PER_ID=CP2.PER_ID AND CPV2.EST_ID=1\n"
                    + "INNER JOIN CEN_CENSOS CC2 ON CC2.EST_ID=1 AND CC2.VEH_ID=CPV2.VEH_ID))*100)) PORCENTAJE,\n"
                    + "COUNT(0) CANTIDAD, (\n"
                    + "SELECT COUNT(0) FROM CEN_PERSONAS P \n"
                    + "INNER JOIN CEN_PERSONA_VEHICULO PV ON PV.PER_ID=P.PER_ID AND PV.EST_ID = 1\n"
                    + "INNER JOIN CEN_CENSOS C ON C.VEH_ID=PV.VEH_ID AND C.EST_ID=1) CANTIDAD_TOTAL \n"
                    + "FROM CEN_PERSONAS CP \n"
                    + "INNER JOIN CEN_PERSONA_VEHICULO PV ON PV.PER_ID=CP.PER_ID AND PV.EST_ID = 1\n"
                    + "INNER JOIN CEN_CENSOS CC ON CC.VEH_ID=PV.VEH_ID AND CC.EST_ID=1 \n"
                    + "WHERE CP.PER_LICONDUCCION IS NOT NULL group by 'SIN LICENCIA', 0");
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
            throw new SQLException("Error en ListarCantidadPersonasCensadasLicencia: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCantidadPersonasCensadasLicencia:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCantidadVehiculosPlaca(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT DESCRIPCION, PORCENTAJE, CANTIDAD, (SELECT COUNT(C.VEH_ID) FROM CEN_CENSOS C WHERE C.EST_ID=1) CANTIDAD_TOTAL FROM (\n"
                    + "SELECT 'CON PLACA DE 6 DIGITOS' DESCRIPCION, ROUND((COUNT(V.VEH_ID)/(SELECT COUNT(C.VEH_ID) FROM CEN_CENSOS C WHERE C.EST_ID=1))*100) PORCENTAJE,\n"
                    + "COUNT(V.VEH_ID) CANTIDAD, 0 CANTIDAD_TOTAL \n"
                    + "FROM CEN_VEHICULOS V \n"
                    + "WHERE V.VEH_PLACA IS NOT NULL AND LENGTH(V.VEH_PLACA) = 6 \n"
                    + "AND V.VEH_ID IN(SELECT C.VEH_ID FROM CEN_CENSOS C WHERE C.EST_ID=1 )\n"
                    + "group by 'CON PLACA DE 6 DIGITOS', 0\n"
                    + "UNION \n"
                    + "SELECT 'CON PLACA DIFERENTE A 6 DIGITOS' DESCRIPCION, ROUND((COUNT(V.VEH_ID)/(SELECT COUNT(C.VEH_ID) FROM CEN_CENSOS C WHERE C.EST_ID=1))*100) PORCENTAJE, \n"
                    + "COUNT(V.VEH_ID) CANTIDAD, 0 CANTIDAD_TOTAL \n"
                    + "FROM CEN_VEHICULOS V \n"
                    + "WHERE V.VEH_PLACA IS NOT NULL AND LENGTH(V.VEH_PLACA) != 6 \n"
                    + "AND V.VEH_ID IN(SELECT C.VEH_ID FROM CEN_CENSOS C WHERE C.EST_ID=1 ) \n"
                    + "group by 'CON PLACA DIFERENTE A 6 DIGITOS', 0 \n"
                    + "UNION \n"
                    + "SELECT 'SIN PLACA' DESCRIPCION, ROUND((COUNT(V.VEH_ID)/(SELECT COUNT(C.VEH_ID) FROM CEN_CENSOS C WHERE C.EST_ID=1))*100) PORCENTAJE, \n"
                    + "COUNT(V.VEH_ID) CANTIDAD, 0 CANTIDAD_TOTAL \n"
                    + "FROM CEN_VEHICULOS V \n"
                    + "WHERE V.VEH_PLACA IS NULL \n"
                    + "AND V.VEH_ID IN(SELECT C.VEH_ID FROM CEN_CENSOS C WHERE C.EST_ID=1 )\n"
                    + "group by 'SIN PLACA', 0)");
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
            throw new SQLException("Error en ListarCantidadVehiculosPlaca: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCantidadVehiculosPlaca:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCantidadVehiculosSoat(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT DECODE(V.VEH_SOAT,'S','Si','N','No') DESCRIPCION, COUNT(DISTINCT V.VEH_ID) CANTIDAD, \n"
                    + "(SELECT COUNT( DISTINCT CV.VEH_ID) FROM CEN_VEHICULOS CV WHERE CV.VEH_ID IN(SELECT CC.VEH_ID FROM CEN_CENSOS CC WHERE CC.EST_ID=1 )) CANTIDAD_TOTAL \n"
                    + "FROM CEN_VEHICULOS V \n"
                    + "INNER JOIN CEN_CENSOS C ON C.EST_ID=1 AND C.VEH_ID=V.VEH_ID \n"
                    + "GROUP BY V.VEH_SOAT");
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
            throw new SQLException("Error en ListarCantidadVehiculosSoat: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCantidadVehiculosSoat:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCantidadVehiculosTecno(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT DECODE(V.VEH_TECNOMEC,'S','Si','N','No') DESCRIPCION, COUNT(DISTINCT V.VEH_ID) CANTIDAD, \n"
                    + "(SELECT COUNT( DISTINCT CV.VEH_ID) FROM CEN_VEHICULOS CV WHERE CV.VEH_ID IN(SELECT CC.VEH_ID FROM CEN_CENSOS CC WHERE CC.EST_ID=1 )) CANTIDAD_TOTAL \n"
                    + "FROM CEN_VEHICULOS V \n"
                    + "INNER JOIN CEN_CENSOS C ON C.EST_ID=1 AND C.VEH_ID=V.VEH_ID \n"
                    + "GROUP BY V.VEH_TECNOMEC");
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
            throw new SQLException("Error en ListarCantidadVehiculosTecno: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCantidadVehiculosTecno:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCantidadCensosClaveVehiculoSanAndres(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT CV.CLV_ID CLV_ID, CV.CLV_DESCRIPCION CLV_DESCRIPCION, COUNT(V.VEH_CLASE) CANTIDAD,\n"
                    + "(SELECT COUNT(C.CEN_ID) FROM CEN_CENSOS C WHERE C.EST_ID=1 AND C.PUN_ID NOT IN (4)) CANTIDAD_TOTAL \n"
                    + "FROM CEN_CENSOS CC \n"
                    + "INNER JOIN CEN_VEHICULOS V ON V.VEH_ID=CC.VEH_ID \n"
                    + "INNER JOIN CEN_CLASES_VEHICULO CV ON CV.CLV_ID=V.VEH_CLASE \n"
                    + "WHERE CC.EST_ID=1 AND CC.PUN_ID NOT IN (4) \n"
                    + "GROUP BY CV.CLV_ID, CV.CLV_DESCRIPCION \n"
                    + "ORDER BY CV.CLV_DESCRIPCION");
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
            throw new SQLException("Error en ListarCantidadCensosClaveVehiculo: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCantidadCensosClaveVehiculo:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCantidadCensosClaveVehiculoProvidencia(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT CV.CLV_ID CLV_ID, CV.CLV_DESCRIPCION CLV_DESCRIPCION, COUNT(V.VEH_CLASE) CANTIDAD,\n"
                    + "(SELECT COUNT(C.CEN_ID) FROM CEN_CENSOS C WHERE C.EST_ID=1 AND C.PUN_ID IN (4)) CANTIDAD_TOTAL \n"
                    + "FROM CEN_CENSOS CC \n"
                    + "INNER JOIN CEN_VEHICULOS V ON V.VEH_ID=CC.VEH_ID \n"
                    + "INNER JOIN CEN_CLASES_VEHICULO CV ON CV.CLV_ID=V.VEH_CLASE \n"
                    + "WHERE CC.EST_ID=1 AND CC.PUN_ID IN (4) \n"
                    + "GROUP BY CV.CLV_ID, CV.CLV_DESCRIPCION \n"
                    + "ORDER BY CV.CLV_DESCRIPCION");
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
            throw new SQLException("Error en ListarCantidadCensosClaveVehiculo: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCantidadCensosClaveVehiculo:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCantidadPersonasCensadasGeneroSanAndres(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT P.PER_GENERO, G.GEN_DESCRIPCION GENERO, COUNT(DISTINCT P.PER_ID) CANTIDAD, \n"
                    + "ROUND(((COUNT(DISTINCT P.PER_ID)/(SELECT COUNT( DISTINCT CP.PER_ID) FROM CEN_PERSONAS CP INNER JOIN CEN_CENSOS CC ON CC.PUN_ID NOT IN(4) AND CC.EST_ID=1 AND CC.PER_ID=CP.PER_ID))*100)) PORCENTAJE,\n"
                    + "(SELECT COUNT( DISTINCT CP.PER_ID) FROM CEN_PERSONAS CP WHERE CP.PER_ID IN(SELECT CC.PER_ID FROM CEN_CENSOS CC WHERE CC.PUN_ID NOT IN(4) AND CC.EST_ID=1 )) CANTIDAD_TOTAL \n"
                    + "FROM CEN_PERSONAS P \n"
                    + "INNER JOIN CEN_CENSOS C ON C.PUN_ID NOT IN(4) AND C.EST_ID=1 AND C.PER_ID=P.PER_ID\n"
                    + "INNER JOIN CEN_GENEROS G ON G.GEN_ID=P.PER_GENERO \n"
                    + "GROUP BY P.PER_GENERO,G.GEN_DESCRIPCION");
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
            throw new SQLException("Error en ListarCantidadPersonasCensadasGeneroSanAndres: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCantidadPersonasCensadasGeneroSanAndres:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCantidadPersonasCensadasGeneroProvidencia(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT P.PER_GENERO, G.GEN_DESCRIPCION GENERO, COUNT(DISTINCT P.PER_ID) CANTIDAD, \n"
                    + "ROUND(((COUNT(DISTINCT P.PER_ID)/(SELECT COUNT( DISTINCT CP.PER_ID) FROM CEN_PERSONAS CP INNER JOIN CEN_CENSOS CC ON CC.PUN_ID IN(4) AND CC.EST_ID=1 AND CC.PER_ID=CP.PER_ID))*100)) PORCENTAJE,\n"
                    + "(SELECT COUNT( DISTINCT CP.PER_ID) FROM CEN_PERSONAS CP WHERE CP.PER_ID IN(SELECT CC.PER_ID FROM CEN_CENSOS CC WHERE CC.PUN_ID IN(4) AND CC.EST_ID=1 )) CANTIDAD_TOTAL \n"
                    + "FROM CEN_PERSONAS P \n"
                    + "INNER JOIN CEN_CENSOS C ON C.PUN_ID IN(4) AND C.EST_ID=1 AND C.PER_ID=P.PER_ID\n"
                    + "INNER JOIN CEN_GENEROS G ON G.GEN_ID=P.PER_GENERO \n"
                    + "GROUP BY P.PER_GENERO,G.GEN_DESCRIPCION");
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
            throw new SQLException("Error en ListarCantidadPersonasCensadasGeneroProvidencia: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCantidadPersonasCensadasGeneroProvidencia:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCantidadPersonasCensadasLicenciaSanAndres(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT 'CON LICENCIA' DESCRIPCION, \n"
                    + "ROUND(((COUNT(DISTINCT CP.PER_ID)/(SELECT COUNT( DISTINCT CP2.PER_ID) FROM CEN_PERSONAS CP2 INNER JOIN CEN_CENSOS CC2 ON CC2.PUN_ID NOT IN (4) AND CC2.EST_ID=1 AND CC2.PER_ID=CP2.PER_ID ))*100)) PORCENTAJE, \n"
                    + "COUNT(*) CANTIDAD, (SELECT COUNT(P.PER_ID) FROM CEN_PERSONAS P INNER JOIN CEN_CENSOS C ON C.PUN_ID NOT IN (4) AND C.EST_ID=1 AND C.PER_ID=P.PER_ID) CANTIDAD_TOTAL\n"
                    + "FROM CEN_PERSONAS CP \n"
                    + "INNER JOIN CEN_CENSOS CC ON CC.PUN_ID NOT IN (4) AND CC.EST_ID=1 AND CC.PER_ID=CP.PER_ID\n"
                    + "WHERE CP.PER_LICONDUCCION IS NULL group by 'CON LICENCIA', 0\n"
                    + "UNION \n"
                    + "SELECT 'SIN LICENCIA' DESCRIPCION, \n"
                    + "ROUND(((COUNT(DISTINCT CP.PER_ID)/(SELECT COUNT( DISTINCT CP2.PER_ID) FROM CEN_PERSONAS CP2 INNER JOIN CEN_CENSOS CC2 ON CC2.PUN_ID NOT IN (4) AND CC2.EST_ID=1 AND CC2.PER_ID=CP2.PER_ID))*100)) PORCENTAJE, \n"
                    + "COUNT(*) CANTIDAD, (SELECT COUNT(P.PER_ID) FROM CEN_PERSONAS P INNER JOIN CEN_CENSOS C ON C.PUN_ID NOT IN (4) AND C.EST_ID=1 AND C.PER_ID=P.PER_ID) CANTIDAD_TOTAL \n"
                    + "FROM CEN_PERSONAS CP \n"
                    + "INNER JOIN CEN_CENSOS CC ON CC.PUN_ID NOT IN (4) AND CC.EST_ID=1 AND CC.PER_ID=CP.PER_ID \n"
                    + "WHERE CP.PER_LICONDUCCION IS NOT NULL group by 'SIN LICENCIA', 0");
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
            throw new SQLException("Error en ListarCantidadPersonasCensadasLicenciaSanAndres: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCantidadPersonasCensadasLicenciaSanAndres:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCantidadPersonasCensadasLicenciaProvidencia(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT 'CON LICENCIA' DESCRIPCION, \n"
                    + "ROUND(((COUNT(DISTINCT CP.PER_ID)/(SELECT COUNT( DISTINCT CP2.PER_ID) FROM CEN_PERSONAS CP2 INNER JOIN CEN_CENSOS CC2 ON CC2.PUN_ID IN (4) AND CC2.EST_ID=1 AND CC2.PER_ID=CP2.PER_ID ))*100)) PORCENTAJE, \n"
                    + "COUNT(*) CANTIDAD, (SELECT COUNT(P.PER_ID) FROM CEN_PERSONAS P INNER JOIN CEN_CENSOS C ON C.PUN_ID IN (4) AND C.EST_ID=1 AND C.PER_ID=P.PER_ID) CANTIDAD_TOTAL\n"
                    + "FROM CEN_PERSONAS CP \n"
                    + "INNER JOIN CEN_CENSOS CC ON CC.PUN_ID IN (4) AND CC.EST_ID=1 AND CC.PER_ID=CP.PER_ID\n"
                    + "WHERE CP.PER_LICONDUCCION IS NULL group by 'CON LICENCIA', 0\n"
                    + "UNION \n"
                    + "SELECT 'SIN LICENCIA' DESCRIPCION, \n"
                    + "ROUND(((COUNT(DISTINCT CP.PER_ID)/(SELECT COUNT( DISTINCT CP2.PER_ID) FROM CEN_PERSONAS CP2 INNER JOIN CEN_CENSOS CC2 ON CC2.PUN_ID IN (4) AND CC2.EST_ID=1 AND CC2.PER_ID=CP2.PER_ID))*100)) PORCENTAJE, \n"
                    + "COUNT(*) CANTIDAD, (SELECT COUNT(P.PER_ID) FROM CEN_PERSONAS P INNER JOIN CEN_CENSOS C ON C.PUN_ID IN (4) AND C.EST_ID=1 AND C.PER_ID=P.PER_ID) CANTIDAD_TOTAL \n"
                    + "FROM CEN_PERSONAS CP \n"
                    + "INNER JOIN CEN_CENSOS CC ON CC.PUN_ID IN (4) AND CC.EST_ID=1 AND CC.PER_ID=CP.PER_ID \n"
                    + "WHERE CP.PER_LICONDUCCION IS NOT NULL group by 'SIN LICENCIA', 0");
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
            throw new SQLException("Error en ListarCantidadPersonasCensadasLicenciaProvidencia: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCantidadPersonasCensadasLicenciaProvidencia:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCantidadVehiculosPlacaSanAndres(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT DESCRIPCION, PORCENTAJE, CANTIDAD, (SELECT COUNT(C.VEH_ID) FROM CEN_CENSOS C WHERE C.PUN_ID NOT IN(4) AND C.EST_ID=1) CANTIDAD_TOTAL FROM (\n"
                    + "SELECT 'CON PLACA DE 6 DIGITOS' DESCRIPCION, ROUND((COUNT(V.VEH_ID)/(SELECT COUNT(C.VEH_ID) FROM CEN_CENSOS C WHERE C.PUN_ID NOT IN(4) AND C.EST_ID=1))*100) PORCENTAJE,\n"
                    + "COUNT(V.VEH_ID) CANTIDAD, 0 CANTIDAD_TOTAL \n"
                    + "FROM CEN_VEHICULOS V \n"
                    + "WHERE V.VEH_PLACA IS NOT NULL AND LENGTH(V.VEH_PLACA) = 6 \n"
                    + "AND V.VEH_ID IN(SELECT C.VEH_ID FROM CEN_CENSOS C WHERE C.PUN_ID NOT IN(4) AND C.EST_ID=1 )\n"
                    + "group by 'CON PLACA DE 6 DIGITOS', 0\n"
                    + "UNION \n"
                    + "SELECT 'CON PLACA DIFERENTE A 6 DIGITOS' DESCRIPCION, ROUND((COUNT(V.VEH_ID)/(SELECT COUNT(C.VEH_ID) FROM CEN_CENSOS C WHERE C.PUN_ID NOT IN(4) AND C.EST_ID=1))*100) PORCENTAJE, \n"
                    + "COUNT(V.VEH_ID) CANTIDAD, 0 CANTIDAD_TOTAL \n"
                    + "FROM CEN_VEHICULOS V \n"
                    + "WHERE V.VEH_PLACA IS NOT NULL AND LENGTH(V.VEH_PLACA) != 6 \n"
                    + "AND V.VEH_ID IN(SELECT C.VEH_ID FROM CEN_CENSOS C WHERE C.PUN_ID NOT IN(4) AND C.EST_ID=1 ) \n"
                    + "group by 'CON PLACA DIFERENTE A 6 DIGITOS', 0 \n"
                    + "UNION \n"
                    + "SELECT 'SIN PLACA' DESCRIPCION, ROUND((COUNT(V.VEH_ID)/(SELECT COUNT(C.VEH_ID) FROM CEN_CENSOS C WHERE C.PUN_ID NOT IN(4) AND C.EST_ID=1))*100) PORCENTAJE, \n"
                    + "COUNT(V.VEH_ID) CANTIDAD, 0 CANTIDAD_TOTAL \n"
                    + "FROM CEN_VEHICULOS V \n"
                    + "WHERE V.VEH_PLACA IS NULL \n"
                    + "AND V.VEH_ID IN(SELECT C.VEH_ID FROM CEN_CENSOS C WHERE C.PUN_ID NOT IN(4) AND C.EST_ID=1 )\n"
                    + "group by 'SIN PLACA', 0)");
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
            throw new SQLException("Error en ListarCantidadVehiculosPlacaSanAndres: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCantidadVehiculosPlacaSanAndres:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCantidadVehiculosPlacaProvidencia(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT DESCRIPCION, PORCENTAJE, CANTIDAD, (SELECT COUNT(C.VEH_ID) FROM CEN_CENSOS C WHERE C.PUN_ID IN(4) AND C.EST_ID=1) CANTIDAD_TOTAL FROM (\n"
                    + "SELECT 'CON PLACA DE 6 DIGITOS' DESCRIPCION, ROUND((COUNT(V.VEH_ID)/(SELECT COUNT(C.VEH_ID) FROM CEN_CENSOS C WHERE C.PUN_ID IN(4) AND C.EST_ID=1))*100) PORCENTAJE,\n"
                    + "COUNT(V.VEH_ID) CANTIDAD, 0 CANTIDAD_TOTAL \n"
                    + "FROM CEN_VEHICULOS V \n"
                    + "WHERE V.VEH_PLACA IS NOT NULL AND LENGTH(V.VEH_PLACA) = 6 \n"
                    + "AND V.VEH_ID IN(SELECT C.VEH_ID FROM CEN_CENSOS C WHERE C.PUN_ID IN(4) AND C.EST_ID=1 )\n"
                    + "group by 'CON PLACA DE 6 DIGITOS', 0\n"
                    + "UNION \n"
                    + "SELECT 'CON PLACA DIFERENTE A 6 DIGITOS' DESCRIPCION, ROUND((COUNT(V.VEH_ID)/(SELECT COUNT(C.VEH_ID) FROM CEN_CENSOS C WHERE C.PUN_ID IN(4) AND C.EST_ID=1))*100) PORCENTAJE, \n"
                    + "COUNT(V.VEH_ID) CANTIDAD, 0 CANTIDAD_TOTAL \n"
                    + "FROM CEN_VEHICULOS V \n"
                    + "WHERE V.VEH_PLACA IS NOT NULL AND LENGTH(V.VEH_PLACA) != 6 \n"
                    + "AND V.VEH_ID IN(SELECT C.VEH_ID FROM CEN_CENSOS C WHERE C.PUN_ID IN(4) AND C.EST_ID=1 ) \n"
                    + "group by 'CON PLACA DIFERENTE A 6 DIGITOS', 0 \n"
                    + "UNION \n"
                    + "SELECT 'SIN PLACA' DESCRIPCION, ROUND((COUNT(V.VEH_ID)/(SELECT COUNT(C.VEH_ID) FROM CEN_CENSOS C WHERE C.PUN_ID IN(4) AND C.EST_ID=1))*100) PORCENTAJE, \n"
                    + "COUNT(V.VEH_ID) CANTIDAD, 0 CANTIDAD_TOTAL \n"
                    + "FROM CEN_VEHICULOS V \n"
                    + "WHERE V.VEH_PLACA IS NULL \n"
                    + "AND V.VEH_ID IN(SELECT C.VEH_ID FROM CEN_CENSOS C WHERE C.PUN_ID IN(4) AND C.EST_ID=1 )\n"
                    + "group by 'SIN PLACA', 0)");
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
            throw new SQLException("Error en ListarCantidadVehiculosPlacaProvidencia: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCantidadVehiculosPlacaProvidencia:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCantidadVehiculosSoatSanAndres(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT DECODE(V.VEH_SOAT,'S','Si','N','No') DESCRIPCION, COUNT(DISTINCT V.VEH_ID) CANTIDAD, \n"
                    + "(SELECT COUNT( DISTINCT CV.VEH_ID) FROM CEN_VEHICULOS CV WHERE CV.VEH_ID IN(SELECT CC.VEH_ID FROM CEN_CENSOS CC WHERE CC.PUN_ID NOT IN(4) AND CC.EST_ID=1 )) CANTIDAD_TOTAL \n"
                    + "FROM CEN_VEHICULOS V \n"
                    + "INNER JOIN CEN_CENSOS C ON C.PUN_ID NOT IN(4) AND C.EST_ID=1  AND C.VEH_ID=V.VEH_ID \n"
                    + "GROUP BY V.VEH_SOAT");
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
            throw new SQLException("Error en ListarCantidadVehiculosSoat: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCantidadVehiculosSoat:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCantidadVehiculosSoatProvidencia(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT DECODE(V.VEH_SOAT,'S','Si','N','No') DESCRIPCION, COUNT(DISTINCT V.VEH_ID) CANTIDAD, \n"
                    + "(SELECT COUNT( DISTINCT CV.VEH_ID) FROM CEN_VEHICULOS CV WHERE CV.VEH_ID IN(SELECT CC.VEH_ID FROM CEN_CENSOS CC WHERE CC.PUN_ID IN(4) AND CC.EST_ID=1 )) CANTIDAD_TOTAL \n"
                    + "FROM CEN_VEHICULOS V \n"
                    + "INNER JOIN CEN_CENSOS C ON C.PUN_ID IN(4) AND C.EST_ID=1 AND C.VEH_ID=V.VEH_ID \n"
                    + "GROUP BY V.VEH_SOAT");
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
            throw new SQLException("Error en ListarCantidadVehiculosSoat: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCantidadVehiculosSoat:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCantidadVehiculosTecnoSanAndres(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT DECODE(V.VEH_TECNOMEC,'S','Si','N','No') DESCRIPCION, COUNT(DISTINCT V.VEH_ID) CANTIDAD, \n"
                    + "(SELECT COUNT( DISTINCT CV.VEH_ID) FROM CEN_VEHICULOS CV WHERE CV.VEH_ID IN(SELECT CC.VEH_ID FROM CEN_CENSOS CC WHERE CC.PUN_ID NOT IN(4) AND CC.EST_ID=1 )) CANTIDAD_TOTAL \n"
                    + "FROM CEN_VEHICULOS V \n"
                    + "INNER JOIN CEN_CENSOS C ON C.PUN_ID NOT IN(4) AND C.EST_ID=1  AND C.VEH_ID=V.VEH_ID \n"
                    + "GROUP BY V.VEH_TECNOMEC");
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
            throw new SQLException("Error en ListarCantidadVehiculosTecnoSanAndres: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCantidadVehiculosTecnoSanAndres:" + e);
            }
        }
        return listaDatos;
    }

    public List ListarCantidadVehiculosTecnoProvidencia(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        try {
            pst = conex.prepareStatement("SELECT DECODE(V.VEH_TECNOMEC,'S','Si','N','No') DESCRIPCION, COUNT(DISTINCT V.VEH_ID) CANTIDAD, \n"
                    + "(SELECT COUNT( DISTINCT CV.VEH_ID) FROM CEN_VEHICULOS CV WHERE CV.VEH_ID IN(SELECT CC.VEH_ID FROM CEN_CENSOS CC WHERE CC.PUN_ID IN(4) AND CC.EST_ID=1 )) CANTIDAD_TOTAL \n"
                    + "FROM CEN_VEHICULOS V \n"
                    + "INNER JOIN CEN_CENSOS C ON C.PUN_ID IN(4) AND C.EST_ID=1 AND C.VEH_ID=V.VEH_ID \n"
                    + "GROUP BY V.VEH_TECNOMEC");
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
            throw new SQLException("Error en ListarCantidadVehiculosTecnoProvidencia: " + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException e) {
                System.out.println("Error en cierres de ListarCantidadVehiculosTecnoProvidencia:" + e);
            }
        }
        return listaDatos;
    }

}
