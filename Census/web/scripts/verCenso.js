
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
            url: '../../cargarDatosCenso',
            method: 'get',
            data: {id: id},
            success: function (data) {
                $('#txtnumerocenso').val(data.censo.NUMERO);
                $('#txtfechacenso').val(data.censo.FECHA);
                $('#txtpuntoatencion').val(data.censo.PUNTO_ATENCION);
                $('#txtusuarioregistro').val(data.censo.USUARIO);
                
                $('#txtplaca').val(data.censo.VEH_PLACA);
                $('#txtmotor').val(data.censo.VEH_MOTOR);
                $('#txtchasis').val(data.censo.VEH_CHASIS);
                $('#txtserie').val(data.censo.VEH_SERIE);

                $('#txttipopersonapresento').val(data.censo.TIPO_DOC);
                $('#txttipodocumentopresento').val(data.censo.TIPO_PERSONA);
                $('#txtdocumentopresento').val(data.censo.DOCUMENTO);
                $('#txtnombrepresento').val(data.censo.NOMBRE);
                
                $('#txtobservaciones').val(data.censo.OBSERVACIONES);

                var personas = data.personasVehiculo;
                var contenedor = $('#personas-censo');
                contenedor.empty();

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
