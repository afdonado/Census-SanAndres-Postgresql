package com.censo.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class EstadisticaDao {

    public List ListarCantidadCensosClaveVehiculo(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        String sql = "select cv.clv_id clv_id, cv.clv_descripcion clv_descripcion, count(v.veh_clase) cantidad, "
                + "(select count(c.cen_id) from cen_censos c where c.est_id=1 ) cantidad_total "
                + "from cen_censos cc "
                + "inner join cen_vehiculos v on v.veh_id=cc.veh_id "
                + "inner join cen_clases_vehiculo cv on cv.clv_id=v.veh_clase "
                + "where cc.est_id=1 "
                + "group by cv.clv_id, cv.clv_descripcion "
                + "order by cv.clv_descripcion";

        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {

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
        }
        return listaDatos;
    }

    public List ListarCantidadCensosPuntoAtencion(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        String sql = "select p.pun_id pun_id, p.pun_nombre pun_descripcion, count(cc.pun_id) cantidad, "
                + "(select count(c.pun_id) from cen_censos c where c.est_id=1 and c.pun_id in(select cp.pun_id from cen_puntos_atencion cp )) cantidad_total "
                + "from cen_censos cc "
                + "inner join cen_puntos_atencion p on p.pun_id=cc.pun_id "
                + "where cc.est_id=1 "
                + "group by p.pun_id, p.pun_nombre "
                + "order by p.pun_nombre";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {

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
        }
        return listaDatos;
    }

    public List ListarCantidadPersonasCensadasGenero(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();
        String sql = "select p.per_genero, g.gen_descripcion genero, count(distinct p.per_id) cantidad, "
                + "round(((count(distinct p.per_id)/("
                + "select count( distinct cp.per_id) from cen_personas cp "
                + "inner join cen_persona_vehiculo cpv on cpv.per_id=cp.per_id and cpv.est_id = 1 "
                + "inner join cen_censos cc on cc.est_id=1 and cc.veh_id=cpv.veh_id))*100)) porcentaje, "
                + "(select count( distinct cp.per_id) from cen_personas cp where cp.per_id in("
                + "select cpv.per_id from cen_persona_vehiculo cpv "
                + "inner join cen_censos cc on cc.est_id=1 and cc.veh_id=cpv.veh_id "
                + "where cpv.est_id = 1)) cantidad_total "
                + "from cen_personas p "
                + "inner join cen_persona_vehiculo pv on pv.per_id=p.per_id and pv.est_id = 1"
                + "inner join cen_censos c on c.est_id=1 and c.veh_id=pv.veh_id "
                + "inner join cen_generos g on g.gen_id=p.per_genero "
                + "group by p.per_genero,g.gen_descripcion";

        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {

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
        }
        return listaDatos;
    }

    public List ListarCantidadPersonasCensadasLicencia(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();
        String sql = "SELECT "
                + "    'CON LICENCIA' AS descripcion, "
                + "    ROUND( "
                + "        ( "
                + "            COUNT(DISTINCT cp.per_id)::numeric / "
                + "            ( "
                + "                SELECT COUNT(DISTINCT cp2.per_id) "
                + "                FROM cen_personas cp2 "
                + "                INNER JOIN cen_persona_vehiculo cpv2 ON cpv2.per_id = cp2.per_id "
                + "                INNER JOIN cen_censos cc2 ON cc2.est_id = 1 AND cc2.veh_id = cpv2.veh_id "
                + "            ) * 100 "
                + "        ), 2 "
                + "    ) AS porcentaje, "
                + "    COUNT(*) AS cantidad, "
                + "    ( "
                + "        SELECT COUNT(*) "
                + "        FROM cen_personas p "
                + "        INNER JOIN cen_persona_vehiculo pv ON pv.per_id = p.per_id AND pv.est_id = 1 "
                + "        INNER JOIN cen_censos c ON c.veh_id = pv.veh_id AND c.est_id = 1 "
                + "    ) AS cantidad_total "
                + "FROM cen_personas cp "
                + "INNER JOIN cen_persona_vehiculo cpv ON cpv.per_id = cp.per_id AND cpv.est_id = 1 "
                + "INNER JOIN cen_censos cc ON cc.veh_id = cpv.veh_id AND cc.est_id = 1 "
                + "WHERE cp.per_liconduccion IS NULL "
                + "GROUP BY descripcion "
                + "UNION ALL "
                + "SELECT "
                + "    'SIN LICENCIA' AS descripcion, "
                + "    ROUND("
                + "        ("
                + "            COUNT(DISTINCT cp.per_id)::numeric / "
                + "            ( "
                + "                SELECT COUNT(DISTINCT cp2.per_id) "
                + "                FROM cen_personas cp2 "
                + "                INNER JOIN cen_persona_vehiculo cpv2 ON cpv2.per_id = cp2.per_id AND cpv2.est_id = 1 "
                + "                INNER JOIN cen_censos cc2 ON cc2.est_id = 1 AND cc2.veh_id = cpv2.veh_id "
                + "            ) * 100 "
                + "        ), 2 "
                + "    ) AS porcentaje, "
                + "    COUNT(*) AS cantidad, "
                + "    ("
                + "        SELECT COUNT(*) "
                + "        FROM cen_personas p "
                + "        INNER JOIN cen_persona_vehiculo pv ON pv.per_id = p.per_id AND pv.est_id = 1 "
                + "        INNER JOIN cen_censos c ON c.veh_id = pv.veh_id AND c.est_id = 1 "
                + "    ) AS cantidad_total "
                + "FROM cen_personas cp "
                + "INNER JOIN cen_persona_vehiculo pv ON pv.per_id = cp.per_id AND pv.est_id = 1 "
                + "INNER JOIN cen_censos cc ON cc.veh_id = pv.veh_id AND cc.est_id = 1 "
                + "WHERE cp.per_liconduccion IS NOT NULL "
                + "GROUP BY descripcion";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {

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
        }
        return listaDatos;
    }

    public List ListarCantidadVehiculosPlaca(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        String sql = "SELECT "
                + "    descripcion, "
                + "    porcentaje, "
                + "    cantidad, "
                + "    (SELECT COUNT(c.veh_id) FROM cen_censos c WHERE c.est_id = 1) AS cantidad_total "
                + "FROM ( "
                + "    SELECT "
                + "        'CON PLACA DE 6 DIGITOS' AS descripcion, "
                + "        ROUND((COUNT(v.veh_id)::numeric / (SELECT COUNT(c.veh_id) FROM cen_censos c WHERE c.est_id = 1)) * 100, 2) AS porcentaje, "
                + "        COUNT(v.veh_id) AS cantidad "
                + "    FROM cen_vehiculos v "
                + "    WHERE v.veh_placa IS NOT NULL "
                + "      AND LENGTH(v.veh_placa) = 6 "
                + "      AND v.veh_id IN (SELECT c.veh_id FROM cen_censos c WHERE c.est_id = 1) "
                + "    GROUP BY descripcion "
                + "    UNION ALL "
                + "    SELECT "
                + "        'CON PLACA DIFERENTE A 6 DIGITOS' AS descripcion, "
                + "        ROUND((COUNT(v.veh_id)::numeric / (SELECT COUNT(c.veh_id) FROM cen_censos c WHERE c.est_id = 1)) * 100, 2) AS porcentaje, "
                + "        COUNT(v.veh_id) AS cantidad "
                + "    FROM cen_vehiculos v "
                + "    WHERE v.veh_placa IS NOT NULL "
                + "      AND LENGTH(v.veh_placa) <> 6 "
                + "      AND v.veh_id IN (SELECT c.veh_id FROM cen_censos c WHERE c.est_id = 1) "
                + "    GROUP BY descripcion "
                + "    UNION ALL "
                + "    SELECT "
                + "        'SIN PLACA' AS descripcion, "
                + "        ROUND((COUNT(v.veh_id)::numeric / (SELECT COUNT(c.veh_id) FROM cen_censos c WHERE c.est_id = 1)) * 100, 2) AS porcentaje, "
                + "        COUNT(v.veh_id) AS cantidad "
                + "    FROM cen_vehiculos v "
                + "    WHERE v.veh_placa IS NULL "
                + "      AND v.veh_id IN (SELECT c.veh_id FROM cen_censos c WHERE c.est_id = 1) "
                + "    GROUP BY descripcion "
                + ") AS resultados";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {

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
        }
        return listaDatos;
    }

    public List ListarCantidadVehiculosSoat(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        String sql = "SELECT "
                + "    CASE "
                + "        WHEN v.veh_soat = 'S' THEN 'SI' "
                + "        WHEN v.veh_soat = 'N' THEN 'NO' "
                + "        ELSE 'DESCONOCIDO' "
                + "    END AS descripcion, "
                + "    COUNT(DISTINCT v.veh_id) AS cantidad, "
                + "    ( "
                + "        SELECT COUNT(DISTINCT cv.veh_id) "
                + "        FROM cen_vehiculos cv "
                + "        WHERE cv.veh_id IN ( "
                + "            SELECT cc.veh_id "
                + "            FROM cen_censos cc "
                + "            WHERE cc.est_id = 1 "
                + "        ) "
                + "    ) AS cantidad_total "
                + "FROM cen_vehiculos v "
                + "INNER JOIN cen_censos c "
                + "    ON c.veh_id = v.veh_id "
                + "   AND c.est_id = 1 "
                + "GROUP BY v.veh_soat";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {

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
        }
        return listaDatos;
    }

    public List ListarCantidadVehiculosTecno(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        String sql = "SELECT "
                + "    CASE "
                + "        WHEN v.veh_tecnomec = 'S' THEN 'SI' "
                + "        WHEN v.veh_tecnomec = 'N' THEN 'NO' "
                + "        ELSE 'DESCONOCIDO' "
                + "    END AS descripcion, "
                + "    COUNT(DISTINCT v.veh_id) AS cantidad, "
                + "    ( "
                + "        SELECT COUNT(DISTINCT cv.veh_id) "
                + "        FROM cen_vehiculos cv "
                + "        WHERE cv.veh_id IN ( "
                + "            SELECT cc.veh_id "
                + "            FROM cen_censos cc "
                + "            WHERE cc.est_id = 1 "
                + "        ) "
                + "    ) AS cantidad_total "
                + "FROM cen_vehiculos v "
                + "INNER JOIN cen_censos c "
                + "    ON c.veh_id = v.veh_id "
                + "   AND c.est_id = 1 "
                + "GROUP BY v.veh_tecnomec;";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {

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
        }
        return listaDatos;
    }

    public List ListarCantidadCensosClaveVehiculoSanAndres(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        String sql = "SELECT CV.CLV_ID CLV_ID, CV.CLV_DESCRIPCION CLV_DESCRIPCION, COUNT(V.VEH_CLASE) CANTIDAD,\n"
                + "(SELECT COUNT(C.CEN_ID) FROM CEN_CENSOS C WHERE C.EST_ID=1 AND C.PUN_ID NOT IN (4)) CANTIDAD_TOTAL \n"
                + "FROM CEN_CENSOS CC \n"
                + "INNER JOIN CEN_VEHICULOS V ON V.VEH_ID=CC.VEH_ID \n"
                + "INNER JOIN CEN_CLASES_VEHICULO CV ON CV.CLV_ID=V.VEH_CLASE \n"
                + "WHERE CC.EST_ID=1 AND CC.PUN_ID NOT IN (4) \n"
                + "GROUP BY CV.CLV_ID, CV.CLV_DESCRIPCION \n"
                + "ORDER BY CV.CLV_DESCRIPCION";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery();) {

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
        }
        return listaDatos;
    }

    public List ListarCantidadCensosClaveVehiculoProvidencia(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        String sql = "SELECT CV.CLV_ID CLV_ID, CV.CLV_DESCRIPCION CLV_DESCRIPCION, COUNT(V.VEH_CLASE) CANTIDAD,\n"
                + "(SELECT COUNT(C.CEN_ID) FROM CEN_CENSOS C WHERE C.EST_ID=1 AND C.PUN_ID IN (4)) CANTIDAD_TOTAL \n"
                + "FROM CEN_CENSOS CC \n"
                + "INNER JOIN CEN_VEHICULOS V ON V.VEH_ID=CC.VEH_ID \n"
                + "INNER JOIN CEN_CLASES_VEHICULO CV ON CV.CLV_ID=V.VEH_CLASE \n"
                + "WHERE CC.EST_ID=1 AND CC.PUN_ID IN (4) \n"
                + "GROUP BY CV.CLV_ID, CV.CLV_DESCRIPCION \n"
                + "ORDER BY CV.CLV_DESCRIPCION";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {

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
        }
        return listaDatos;
    }

    public List ListarCantidadPersonasCensadasGeneroSanAndres(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        String sql = "SELECT P.PER_GENERO, G.GEN_DESCRIPCION GENERO, COUNT(DISTINCT P.PER_ID) CANTIDAD, \n"
                + "ROUND(((COUNT(DISTINCT P.PER_ID)/(SELECT COUNT( DISTINCT CP.PER_ID) FROM CEN_PERSONAS CP INNER JOIN CEN_CENSOS CC ON CC.PUN_ID NOT IN(4) AND CC.EST_ID=1 AND CC.PER_ID=CP.PER_ID))*100)) PORCENTAJE,\n"
                + "(SELECT COUNT( DISTINCT CP.PER_ID) FROM CEN_PERSONAS CP WHERE CP.PER_ID IN(SELECT CC.PER_ID FROM CEN_CENSOS CC WHERE CC.PUN_ID NOT IN(4) AND CC.EST_ID=1 )) CANTIDAD_TOTAL \n"
                + "FROM CEN_PERSONAS P \n"
                + "INNER JOIN CEN_CENSOS C ON C.PUN_ID NOT IN(4) AND C.EST_ID=1 AND C.PER_ID=P.PER_ID\n"
                + "INNER JOIN CEN_GENEROS G ON G.GEN_ID=P.PER_GENERO \n"
                + "GROUP BY P.PER_GENERO,G.GEN_DESCRIPCION";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
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
        }
        return listaDatos;
    }

    public List ListarCantidadPersonasCensadasGeneroProvidencia(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        String sql = "SELECT P.PER_GENERO, G.GEN_DESCRIPCION GENERO, COUNT(DISTINCT P.PER_ID) CANTIDAD, \n"
                + "ROUND(((COUNT(DISTINCT P.PER_ID)/(SELECT COUNT( DISTINCT CP.PER_ID) FROM CEN_PERSONAS CP INNER JOIN CEN_CENSOS CC ON CC.PUN_ID IN(4) AND CC.EST_ID=1 AND CC.PER_ID=CP.PER_ID))*100)) PORCENTAJE,\n"
                + "(SELECT COUNT( DISTINCT CP.PER_ID) FROM CEN_PERSONAS CP WHERE CP.PER_ID IN(SELECT CC.PER_ID FROM CEN_CENSOS CC WHERE CC.PUN_ID IN(4) AND CC.EST_ID=1 )) CANTIDAD_TOTAL \n"
                + "FROM CEN_PERSONAS P \n"
                + "INNER JOIN CEN_CENSOS C ON C.PUN_ID IN(4) AND C.EST_ID=1 AND C.PER_ID=P.PER_ID\n"
                + "INNER JOIN CEN_GENEROS G ON G.GEN_ID=P.PER_GENERO \n"
                + "GROUP BY P.PER_GENERO,G.GEN_DESCRIPCION";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {

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
        }
        return listaDatos;
    }

    public List ListarCantidadPersonasCensadasLicenciaSanAndres(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();
        String sql = "SELECT 'CON LICENCIA' DESCRIPCION, \n"
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
                + "WHERE CP.PER_LICONDUCCION IS NOT NULL group by 'SIN LICENCIA', 0";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {

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
        }
        return listaDatos;
    }

    public List ListarCantidadPersonasCensadasLicenciaProvidencia(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();
        String sql = "SELECT 'CON LICENCIA' DESCRIPCION, \n"
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
                + "WHERE CP.PER_LICONDUCCION IS NOT NULL group by 'SIN LICENCIA', 0";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {

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
        }
        return listaDatos;
    }

    public List ListarCantidadVehiculosPlacaSanAndres(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        String sql = "SELECT DESCRIPCION, PORCENTAJE, CANTIDAD, (SELECT COUNT(C.VEH_ID) FROM CEN_CENSOS C WHERE C.PUN_ID NOT IN(4) AND C.EST_ID=1) CANTIDAD_TOTAL FROM (\n"
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
                + "group by 'SIN PLACA', 0)";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {

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
        }
        return listaDatos;
    }

    public List ListarCantidadVehiculosPlacaProvidencia(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();
        String sql = "SELECT DESCRIPCION, PORCENTAJE, CANTIDAD, (SELECT COUNT(C.VEH_ID) FROM CEN_CENSOS C WHERE C.PUN_ID IN(4) AND C.EST_ID=1) CANTIDAD_TOTAL FROM (\n"
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
                + "group by 'SIN PLACA', 0)";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {

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
        }
        return listaDatos;
    }

    public List ListarCantidadVehiculosSoatSanAndres(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        String sql = "SELECT CASE WHEN v.veh_soat = 'S' THEN 'Si' "
                + "WHEN v.veh_soat = 'N' THEN 'No' "
                + "ELSE 'Desconocido' END AS DESCRIPCION, "
                + "COUNT(DISTINCT V.VEH_ID) AS CANTIDAD, "
                + "(SELECT COUNT( DISTINCT CV.VEH_ID) "
                + "FROM CEN_VEHICULOS CV WHERE CV.VEH_ID IN("
                + "SELECT CC.VEH_ID FROM CEN_CENSOS CC "
                + "WHERE CC.PUN_ID NOT IN(4) AND CC.EST_ID=1 )) AS CANTIDAD_TOTAL "
                + "FROM CEN_VEHICULOS V "
                + "INNER JOIN CEN_CENSOS C ON C.PUN_ID NOT IN(4) "
                + "AND C.EST_ID=1 AND C.VEH_ID=V.VEH_ID "
                + "GROUP BY V.VEH_SOAT";
        try (PreparedStatement pst = conex.prepareStatement(sql); 
                ResultSet rst = pst.executeQuery()) {

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
        }
        return listaDatos;
    }

    public List ListarCantidadVehiculosSoatProvidencia(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        String sql = "SELECT CASE WHEN v.veh_soat = 'S' THEN 'Si' "
                + "WHEN v.veh_soat = 'N' THEN 'No' "
                + "ELSE 'Desconocido' END AS DESCRIPCION, "
                + "COUNT(DISTINCT V.VEH_ID) AS CANTIDAD, "
                + "(SELECT COUNT( DISTINCT CV.VEH_ID) "
                + "FROM CEN_VEHICULOS CV WHERE CV.VEH_ID IN("
                + "SELECT CC.VEH_ID FROM CEN_CENSOS CC "
                + "WHERE CC.PUN_ID IN(4) AND CC.EST_ID=1 )) AS CANTIDAD_TOTAL "
                + "FROM CEN_VEHICULOS V "
                + "INNER JOIN CEN_CENSOS C ON C.PUN_ID IN(4) "
                + "AND C.EST_ID=1 AND C.VEH_ID=V.VEH_ID "
                + "GROUP BY V.VEH_SOAT";

        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {

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
        }
        return listaDatos;
    }

    public List ListarCantidadVehiculosTecnoSanAndres(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        String sql = "SELECT CASE WHEN v.veh_tecnomec = 'S' THEN 'Si' "
                + "WHEN v.veh_tecnomec = 'N' THEN 'No' "
                + "ELSE 'Desconocido' END AS DESCRIPCION, "
                + "COUNT(DISTINCT V.VEH_ID) AS CANTIDAD, "
                + "(SELECT COUNT( DISTINCT CV.VEH_ID) "
                + "FROM CEN_VEHICULOS CV WHERE CV.VEH_ID IN("
                + "SELECT CC.VEH_ID FROM CEN_CENSOS CC "
                + "WHERE CC.PUN_ID NOT IN(4) AND CC.EST_ID=1 )) AS CANTIDAD_TOTAL "
                + "FROM CEN_VEHICULOS V "
                + "INNER JOIN CEN_CENSOS C ON C.PUN_ID NOT IN(4) "
                + "AND C.EST_ID=1  AND C.VEH_ID=V.VEH_ID "
                + "GROUP BY V.VEH_TECNOMEC";

        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {
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
        }
        return listaDatos;
    }

    public List ListarCantidadVehiculosTecnoProvidencia(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        String sql = "SELECT CASE WHEN v.veh_tecnomec = 'S' THEN 'Si' "
                + "WHEN v.veh_tecnomec = 'N' THEN 'No' "
                + "ELSE 'Desconocido' END AS DESCRIPCION, "
                + "COUNT(DISTINCT V.VEH_ID) CANTIDAD, "
                + "(SELECT COUNT( DISTINCT CV.VEH_ID) "
                + "FROM CEN_VEHICULOS CV WHERE CV.VEH_ID IN("
                + "SELECT CC.VEH_ID FROM CEN_CENSOS CC "
                + "WHERE CC.PUN_ID IN(4) AND CC.EST_ID=1 )) CANTIDAD_TOTAL "
                + "FROM CEN_VEHICULOS V "
                + "INNER JOIN CEN_CENSOS C ON C.PUN_ID IN(4) "
                + "AND C.EST_ID=1 AND C.VEH_ID=V.VEH_ID "
                + "GROUP BY V.VEH_TECNOMEC";
        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {

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
        }
        return listaDatos;
    }

    public List reporteCensosGeneralMensual(Connection conex) throws SQLException {

        List<HashMap> listaDatos = new LinkedList<>();

        String sql = "select * from sql_censos_general_mensual";

        try (PreparedStatement pst = conex.prepareStatement(sql); ResultSet rst = pst.executeQuery()) {

            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                HashMap<String, String> hash = new HashMap<>();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                }
                listaDatos.add(hash);
            }
        } catch (SQLException e) {
            throw new SQLException("Error en reporteCensosGeneralMensual: " + e);
        }
        return listaDatos;
    }

    public HashMap<String, Object> reporteCensosGeneral(Connection conex, String mes) throws SQLException {

        HashMap<String, Object> hash = new HashMap<>();

        String sql = "select * from sql_censos_general where mes = ?";

        try (PreparedStatement pst = conex.prepareStatement(sql)) {
            pst.setString(1, mes);
            try (ResultSet rst = pst.executeQuery()) {
                if (rst.next()) {
                    ResultSetMetaData rsmd = rst.getMetaData();
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        hash.put(rsmd.getColumnName(i + 1), rst.getString(i + 1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en reporteCensosGeneral: " + e);
        }
        return hash;
    }

}
