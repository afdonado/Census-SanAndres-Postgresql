
$(function () {
    $('#personas-vehiculo').on('blur', 'input[type="number"]', function () {
        // Aquí puedes agregar la lógica para identificar qué input ha disparado el evento 'blur'
        var inputId = $(this).attr('id'); // Obtener el id del input que disparó el evento

        // Extraer el número del ID para construir los IDs de los otros elementos
        var idNumber = inputId.match(/\d+/)[0];

        // Construir los nombres dinámicos de los elementos
        var tipoDoc = 'cmbtiposdocumento' + idNumber;
        var documento = 'txtdocumento' + idNumber;
        var nombre = 'txtnombre' + idNumber;
        var idPersona = 'idpersona' + idNumber;

        // Llamar a la función con los parámetros correctos
        consultarDocumentoPersonaVehiculo(tipoDoc, documento, nombre, idPersona);
    });

});


function consultarDocumentoPersonaVehiculo(nametipodocumento, namedocumento, namenombre, nameidpersona) {
    var tipodocumento = $('#' + nametipodocumento).val();
    var documento = $('#' + namedocumento).val().toUpperCase();
    if (documento.length > 0) {
        $.ajax({
            type: "POST",
            url: "../../verificarPersonaVehiculo",
            data: {
                tipodocumento: tipodocumento,
                documento: documento,
                namenombre: namenombre,
                nameidpersona: nameidpersona
            },
            success: function (response) {

                if (response.status === "success") {
                    console.log("success", response.nombre);
                    $('#' + namenombre).val(response.nombre);
                    $('#' + nameidpersona).val(response.id);
                } else if (response.status === "fail") {
                    $('#' + namedocumento).val('');
                    $('#' + namenombre).val('');
                    $('#' + nameidpersona).val('');
                } else if (response.status === "error") {
                    $('#' + namedocumento).val('');
                    $('#' + namenombre).val('');
                    $('#' + nameidpersona).val('');
                    alert(response.message);
                }
            },
            error: function (xhr, status, error) {
                console.error("Error en la solicitud: " + error);
            }
        });
    }
}