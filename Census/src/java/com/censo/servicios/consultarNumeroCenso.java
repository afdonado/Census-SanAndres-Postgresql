
package com.censo.servicios;

import com.censo.modelo.dao.CensoDao;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "consultarNumeroCenso")
public class consultarNumeroCenso {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "consultarCenso")
    public Datosresponse consultarCenso(@WebParam(name = "consultarCensoResponse") String numero) {
        Datosresponse datosresponse = new Datosresponse();
        CensoDao dao = new CensoDao();
        try {
            datosresponse = dao.ConsultarCensoWSByNumero(numero);
        } catch (Exception e) {
        }
        return datosresponse;
    }
}
