
function verificarNumeroCenso(numero) {
    console.log('verificarNumeroCenso');
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
        url: "../../verificarVehiculo",
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

function registrarCenso() {
    var numero = document.getElementById('txtnumero').value;
    var fechacenso = document.getElementById('txtfechacenso').value;
    var referencia = document.getElementById('txtreferencia').value;
    var documento = document.getElementById('txtdocumento').value;

    if (numero.length > 0 && fechacenso.length > 0 && referencia.length > 0 && documento.length > 0) {
        document.getElementById('frmregistrarcenso').submit();
    } else {
        alert('Debe ingresar como minimo los datos obligatorios (*)');
    }
}

function modificarCenso() {
    if (confirm("Confirma Actualizar el censo?")) {
        var numerocenso = document.getElementById('txtnumero').value.toString().toUpperCase();
        var fechacenso = document.getElementById('txtfechacenso').value.toString().toUpperCase();
        var referencia = document.getElementById('txtreferencia').value.toString().toUpperCase();
        var documento = document.getElementById('txtdocumento').value.toString().toUpperCase();

        if (numerocenso.length > 4 && fechacenso.length > 0 && referencia.length > 0 && documento.length > 0) {
            document.getElementById('frmmodificarcenso').submit();
        } else {
            alert('Debe ingresar como minimo los datos obligatorios (*)');
        }
    }
}

function generarReporteCenso(opcion) {

    switch (opcion) {
        case 0 :
            var numero = document.getElementById("txtnumero").value.toString().toUpperCase();
            window.frames[0].location.href = "generarReporteCensos.jsp?opcion=0&numero=" + numero;
            break;

        case 1 :
            var tiporef = document.getElementById("cmbtiporeferencia").value;
            var referencia = document.getElementById("txtreferencia").value.toString().toUpperCase();
            window.frames[0].location.href = "generarReporteCensos.jsp?opcion=1&tiporef=" + tiporef + "&referencia=" + referencia;
            break;

        case 2:
            var tipodocumento = document.getElementById('cmbtipodoc').value;
            var documento = document.getElementById('txtdocumento').value.toString().toUpperCase();
            window.frames[0].location.href = "generarReporteCensos.jsp?opcion=2&tipodocumento=" + tipodocumento + "&documento=" + documento;
            break;

        case 3:
            var fechaini = document.getElementById("txtfechaini").value.toString();
            var fechafin = document.getElementById("txtfechafin").value.toString();
            var punto = document.getElementById('cmbpuntoatenfecha').value;
            window.frames[0].location.href = "generarReporteCensos.jsp?opcion=3&fechaini=" + fechaini + "&fechafin=" + fechafin + "&punto=" + punto;
            break;

        case 4:
            var fechaRegini = document.getElementById("txtfechaRegini").value.toString();
            var fechaRegfin = document.getElementById("txtfechaRegfin").value.toString();
            var punto = document.getElementById('cmbpuntoatenfechareg').value;
            window.frames[0].location.href = "generarReporteCensos.jsp?opcion=4&fechaRegini=" + fechaRegini + "&fechaRegfin=" + fechaRegfin + "&punto=" + punto;
            break;

        case 5 :
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