
$(function () {

    var identificador = 0;
    $('#agregar-campos').click(function () {
        identificador = $("#txtcantidadpersonas").val();
        if (identificador < 5) {
            identificador++;
            var $contentID = $("#personas-vehiculo");

            var $nuevoDivPersona = $("<div>", {
                id: 'contenedor'.concat(identificador),
                class: 'row'
            });

            $.ajax({
                type: "post",
                url: "../../agregarCamposPersona",
                data: {
                    identificador: identificador,
                    nameComboTP: "cmbtipospersona".concat(identificador),
                    nameComboTD: "cmbtiposdocumento".concat(identificador),
                    nameTxtDocumento: "txtdocumento".concat(identificador),
                    nameTxtNombre: "txtnombre".concat(identificador),
                    nameTxtId: "idpersona".concat(identificador)
                },
                contentType: "application/x-www-form-urlencoded",
                success: function (response) {
                    $("#txtcantidadpersonas").val(identificador);
                    $nuevoDivPersona.html(response);
                    $nuevoDivPersona.css('class', 'form-group row');
                    $contentID.append($nuevoDivPersona);
                },
                error: function () {
                    alert("Hubo un error al cargar los campos.");
                }
            });
        } else {
            alert("No se pueden agregar más personas");
        }
    });

    $('#quitar-campos').click(function () {
        identificador = $("#txtcantidadpersonas").val();
        if (identificador !== 0) {
            $('#contenedor'.concat(identificador)).remove();
            identificador--;
            $("#txtcantidadpersonas").val(identificador);
        }
    });

    function actualizarCamposMatricula() {
        var runt = $('#cmbrunt').val();
        if (runt === "N") {
            $('.matricula').hide();
        } else {
            $('.matricula').show();
        }
    }

    $('#cmbrunt').on('change focus', actualizarCamposMatricula);

    function actualizarCamposImportacion() {
        var tipoImportacion = $('#cmbtiposimportacion').val();
        if (tipoImportacion === "0") {
            $('.importacion').hide();
        } else {
            $('.importacion').show();
        }
    }

    $('#cmbtiposimportacion').on('change focus', actualizarCamposImportacion);

    $('#cmbsoat').on('change focus', function () {
        if ($('#cmbsoat').val() === 'N') {
            $('#soatcontenedor').hide();
        } else {
            $('#soatcontenedor').show();
        }
    });

    $('#cmbtecnomecanica').on('change focus', function () {
        if ($('#cmbtecnomecanica').val() === 'N') {
            $('#tecnomecanicacontenedor').hide();
        } else {
            $('#tecnomecanicacontenedor').show();
        }
    });
    
});


function generarReporteVehiculo(opcion) {

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
            window.frames[0].location.href = "generarReporteVehiculos.jsp?opcion=2&tipodocumento=" + tipodocumento + "&documento=" + documento;
            break;

        case 2:
            var fechaRegini = document.getElementById("txtfechaRegini").value.toString();
            var fechaRegfin = document.getElementById("txtfechaRegfin").value.toString();
            window.frames[0].location.href = "generarReporteVehiculos.jsp?opcion=3&fechaRegini=" + fechaRegini + "&fechaRegfin=" + fechaRegfin;
            break;

        case 4:
            url = "generarReporteVehiculos.jsp?opcion=4";
            break;
    }

    window.open(url, 'popUp', opciones);

}