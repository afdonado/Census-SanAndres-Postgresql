
function verificarNumeroCenso(numero) {
    $.ajax({
        data: {
            numero: numero
        },
        url: "../../verificarNumeroCenso",
        type: "POST",
        dataType: "json",
        success: function (response) {
            if (response.status === "success") {
                $('#txtnumerocenso').css("background-color", "lightgreen");
                console.log(response.message);
            } else if (response.status === "fail") {
                $('#txtnumerocenso').val('');
                $('#txtnumerocenso').focus();
                $('#txtnumerocenso').css("background-color", "");
                console.log(response.message);
            } else if (response.status === "error") {
                alert(response.message);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de verificar numero de censo: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de verificar numero de censo.");
        }
    });
}

// Verificar si la referencia de vehiculo existe o no
function consultarReferenciaVehiculo(tiporeferencia, valorreferencia) {
    var parametros = {
        tiporeferencia: tiporeferencia,
        valorreferencia: valorreferencia,
        opcion: 2
    };
    $.ajax({
        data: parametros,
        url: "../../verificarVehiculo2017",
        type: "POST",
        dataType: "json",
        success: function (response) {
            if (response.status === "success") {
                $('#idvehiculo').val(response.id);
                $('#txtreferencia').css("background-color", "lightgreen");
                console.log(response.message);
            } else if (response.status === "fail") {
                $('#txtreferencia').val('');
                $('#txtreferencia').focus();
                $('#txtreferencia').css("background-color", "");
                alert(response.message);
            } else if (response.status === "error") {
                alert(response.message);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de verificar vehiculo: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de verificar vehiculo.");
        }
    });
}

function generarReporteCenso(opcion) {

    switch (opcion) {
        case 0:
            var fechaini = document.getElementById("txtfechaini").value.toString();
            var fechafin = document.getElementById("txtfechafin").value.toString();
            var punto = document.getElementById('cmbpuntoatenfecha').value;
            window.frames[0].location.href = "generarReporteCensos.jsp?opcion=3&fechaini=" + fechaini + "&fechafin=" + fechafin + "&punto=" + punto;
            break;

        case 1:
            var fechaRegini = document.getElementById("txtfechaRegini").value.toString();
            var fechaRegfin = document.getElementById("txtfechaRegfin").value.toString();
            var punto = document.getElementById('cmbpuntoatenfechareg').value;
            window.frames[0].location.href = "generarReporteCensos.jsp?opcion=4&fechaRegini=" + fechaRegini + "&fechaRegfin=" + fechaRegfin + "&punto=" + punto;
            break;

        case 3 :
            window.frames[0].location.href = "generarReporteCensos.jsp?opcion=5";
            break;
    }

}

function ImprimirCensoById(idcenso) {
    if (confirm("¿Esta seguro que desea generar el reporte?")) {
        ancho = 800;
        alto = 640;
        barra = 0;
        izquierda = (screen.width) ? (screen.width - ancho) / 2 : 100;
        arriba = (screen.height) ? (screen.height - alto) / 2 : 100;
        opciones = 'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=' + barra + ',resizable=no,width=' + ancho + ',height=' + alto + ',left=' + izquierda + ',top=' + arriba;
        url = "../../imprimirReporte?idcenso=" + idcenso;
        window.open(url, 'popUp', opciones);
    }
}