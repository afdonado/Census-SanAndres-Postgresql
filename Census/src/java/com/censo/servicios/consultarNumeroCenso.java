package com.censo.servicios;

import com.censo.modelo.dao.CensoDao;
import java.sql.Connection;
import java.sql.SQLException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "consultarNumeroCenso")
public class consultarNumeroCenso {

    @WebMethod(operationName = "consultarCenso")
    public Datosresponse consultarCenso(@WebParam(name = "consultarCensoResponse") String numero) {

        Datosresponse datosresponse = new Datosresponse();
        
        Connection conex = null;
        
        try {
            
            CensoDao censoDao = new CensoDao();
            conex = censoDao.conectar();
            
            datosresponse = censoDao.ConsultarCensoWSByNumero(conex, numero);
        } catch (SQLException e) {
            System.out.println("Error Servicio ConsultarNumeroCenso");
            e.printStackTrace();
        } finally {
            if (conex != null) {
                try {
                    conex.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
        return datosresponse;
    }
}
