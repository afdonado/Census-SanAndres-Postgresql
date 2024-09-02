
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
            url: '../../cargarDatosVerificacion',
            method: 'get',
            data: {id: id},
            success: function (response) {

                if (response.status === "success") {

                    $('#txtnumerocenso').val(response.censo.NUMERO);
                    $('#txtfechacenso').val(response.censo.FECHA);
                    $('#txtpuntoatencion').val(response.censo.PUNTO_ATENCION);
                    $('#txtusuarioregistro').val(response.censo.USUARIO);

                    $('#txtplaca').val(response.censo.VEH_PLACA);
                    $('#txtmotor').val(response.censo.VEH_MOTOR);
                    $('#txtchasis').val(response.censo.VEH_CHASIS);
                    $('#txtserie').val(response.censo.VEH_SERIE);

                    $('#txttipopersonapresento').val(response.censo.TIPO_DOC);
                    $('#txttipodocumentopresento').val(response.censo.TIPO_PERSONA);
                    $('#txtdocumentopresento').val(response.censo.DOCUMENTO);
                    $('#txtnombrepresento').val(response.censo.NOMBRE);

                    $('#txtobservaciones').val(response.censo.OBSERVACIONES);

                    var personas = response.personasVehiculo;
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
                } else if (response.status === "fail" || response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de cargar datos censo: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de cargar datos censo.");
            }
        });

    } else {
        console.log("Parámetros no encontrados en la URL");
    }

});
