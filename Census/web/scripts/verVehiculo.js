
$(function () {

    function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                results = regex.exec(location.search);
        return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    }

    var opcion = getParameterByName('opcion');
    var id = getParameterByName('id');

    if (opcion && id) {

        $.ajax({
            url: '../../cargarDatosVehiculo',
            method: 'get',
            data: {id: id},
            success: function (data) {
                $('#txtplaca').val(data.vehiculo.VEH_PLACA);
                $('#txtmotor').val(data.vehiculo.VEH_MOTOR);
                $('#txtchasis').val(data.vehiculo.VEH_CHASIS);
                $('#txtserie').val(data.vehiculo.VEH_SERIE);

                $('#txtclase').val(data.vehiculo.CLASE);
                $('#txtservicio').val(data.vehiculo.SERVICIO);
                $('#txttipouso').val(data.vehiculo.TIPO_USO);

                $('#txtcolores').val(data.vehiculo.COLOR);
                $('#txtmarcas').val(data.vehiculo.MARCA);
                $('#txtlineas').val(data.vehiculo.LINEA);
                $('#txtmodelo').val(data.vehiculo.MODELO);
                $('#txttransformado').val(data.vehiculo.TRANSFORMADO==='S'?'Si':'No');
                $('#txtrunt').val(data.vehiculo.RUNT==='S'?'Si':'No');
                $('#txtlicenciatransito').val(data.vehiculo.LICENCIA_TRANSITO);
                $('#txtfechamatricula').val(data.vehiculo.FECHA_MATRICULA);


                $('#txtpaismatricula').val(data.vehiculo.PAIS_MATRICULA);
                $('#txtdepartamentomatricula').val(data.vehiculo.DPTO_MATRICULA);
                $('#txtmunicipiomatricula').val(data.vehiculo.MUNI_MATRICULA);
                $('#txtciudadmatricula').val(data.vehiculo.CIUDAD_MATRICULA);

                $('#txttipodocimportacion').val(data.vehiculo.TDOC_IMP);
                $('#txtdocumentoimportacion').val(data.vehiculo.DOCUMENTO_IMP);
                $('#txtfechaimportacion').val(data.vehiculo.FECHA_IMP);
                $('#txtpaisimportacion').val(data.vehiculo.PAIS_IMP);

                $('#txtsoat').val(data.vehiculo.SOAT==='S'?'Si':'No');
                $('#txtfechavsoat').val(data.vehiculo.FECHAV_SOAT);
                $('#txttecnomecanica').val(data.vehiculo.TECNO_MECANICA==='S'?'Si':'No');
                $('#txtfechavtecnomecanica').val(data.vehiculo.FECHAV_TECNO);

                $('#txtobservaciones').val(data.vehiculo.OBSERVACIONES);

                var personas = data.personasVehiculo;
                var contenedor = $('#personas-vehiculo'); // El contenedor principal donde se añadirán las estructuras
                contenedor.empty(); // Limpia el contenedor antes de añadir nuevos elementos

                $.each(personas, function (index, persona) {
                    var nuevoElemento = `
                <div id="contenedor" class="form-group row">
                    <div class="col-sm-2 mb-3 mb-sm-0">
                        <input class="form-control" id="txttipopersona" name="txttipopersona" value="${persona.TIPO_PERSONA}" readonly="true">
                    </div>
                    <div class="col-sm-2 mb-3 mb-sm-0">
                        <input class="form-control" id="txttipodocumento" name="txttipodocumento" value="${persona.TIPO_DOC}" readonly="true">
                    </div>
                    <div class="col-sm-3 mb-3 mb-sm-0">
                        <input class="form-control" type="number" id="txtdocumento" name="txtdocumento" value="${persona.DOCUMENTO}" required="true">
                    </div>
                    <div class="col-sm-5 mb-3 mb-sm-0">
                        <input class="form-control" type="text" id="txtnombre" name="txtnombre" value="${persona.NOMBRE}" readonly="true">
                    </div>
                </div>
            `;
                    contenedor.append(nuevoElemento);
                });
            }
        });

    } else {
        console.log("Parámetros no encontrados en la URL");
    }

});
