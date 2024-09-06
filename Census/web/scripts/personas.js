
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
        consultarDocumentoPersona(tipoDoc, documento, nombre, idPersona);
    });

});


function consultarDocumentoPersona(nametipodocumento, namedocumento, namenombre, nameidpersona) {
    var tipodocumento = $('#' + nametipodocumento).val();
    var documento = $('#' + namedocumento).val().toUpperCase();
    console.log("personas", tipodocumento + ' ' + documento);
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
                    $('#' + namenombre).val('');
                    $('#' + nameidpersona).val('');
                } else if (response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (xhr, status, error) {
                console.error("Error en la solicitud: " + error);
            }
        });
    }
}

function generarReportePersona(opcion) {

    ancho = 200;
    alto = 140;
    barra = 0;
    izquierda = (screen.width) ? (screen.width - ancho) / 2 : 100;
    arriba = (screen.height) ? (screen.height - alto) / 2 : 100;
    opciones = 'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=' + barra + ',resizable=no,width=' + ancho + ',height=' + alto + ',left=' + izquierda + ',top=' + arriba;
    switch (opcion) {
        case 1:
            var tipodocumento = document.getElementById('cmbtipodoc').value;
            var documento = document.getElementById('txtdocumento').value.toString().toUpperCase();
            url = "generarReportePersonas.jsp?opcion=1&tipodocumento=" + tipodocumento + "&documento=" + documento;
            break;

        case 2:
            var prinombre = document.getElementById("txtprinom").value.toString().toUpperCase();
            var segnombre = document.getElementById("txtsegnom").value.toString().toUpperCase();
            var priapellido = document.getElementById("txtpriape").value.toString().toUpperCase();
            var segapellido = document.getElementById("txtsegape").value.toString().toUpperCase();
            url = "generarReportePersonas.jsp?opcion=2&prinombre=" + prinombre + "&segnombre=" + segnombre + "&priapellido=" + priapellido + "&segapellido=" + segapellido;
            break;

        case 3:
            var fechanacini = document.getElementById("txtfechanacini").value.toString();
            var fechanacfin = document.getElementById("txtfechanacfin").value.toString();
            url = "generarReportePersonas.jsp?opcion=3&fechanacini=" + fechanacini + "&fechanacfin=" + fechanacfin;
            break;

        case 4:
            url = "generarReportePersonas.jsp?opcion=4";
            break;
    }

    window.open(url, 'popUp', opciones);

}