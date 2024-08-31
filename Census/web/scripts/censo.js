$(function () {
    $('#txtnumerocenso').blur(function () {
        if ($('#txtnumerocenso').val().length > 0) {
            verificarNumeroCenso($('#txtnumerocenso').val().toString().toUpperCase());
        }
    });

    function verificarNumeroCenso(numero) {
        var parametros = {
            numero: numero
        };
        $.ajax({
            data: parametros,
            url: "../../verificarNumeroCenso",
            type: "post"
        })
                .done(function (data) {
                    var respuesta = data.toString();
                    respuesta = respuesta.toString().trim();
                    if (respuesta === 'si') {
                        $('#txtnumerocenso').val('');
                        $('#txtnumerocenso').focus();
                        alert("Numero Censo ya se encuentra registrado");
                    }
                });
    }


    $('#numero_m').blur(function () {
        if ($('#numero_m').val().length > 0 && $('#numero_m').val().eq($('#numerocenso').val())) {
            verificarNumeroCenso($('#numero_m').val().toString().toUpperCase());
        }
    });
    
    $('#txtreferencia').blur(function () {
        console.log('referencia:', $('#txtreferencia').val());
        var tipoReferencia = $('#cmbtiposreferencia').val();
        var referencia = $('#txtreferencia').val().toString().toUpperCase();
        if (referencia.length > 0) {
            consultarReferenciaVehiculo(tipoReferencia, referencia);
        }
    });

    // Verificar si la referencia de vehiculo existe o no
    function consultarReferenciaVehiculo(tiporeferencia, valorreferencia) {
        console.log('consulta ref');
        var parametros = {
            tiporeferencia: tiporeferencia,
            valorreferencia: valorreferencia,
            opcion: 2
        };
        $.ajax({
            data: parametros,
            url: "../../verificarVehiculo",
            type: "post"
        })
                .done(function (data) {
                    var respuesta = data.toString().trim();
                    console.log('respuesta: ', respuesta);
                    if (respuesta === 'si') {
                        alert("Vehiculo ya se encuentra censado");
                        $('#txtreferencia').val('');
                        $('#txtreferencia').focus();
                    } else {
                        if(respuesta === 'no'){
                            alert("Vehiculo no se encuentra registrado");
                            $('#txtreferencia').val('');
                            $('#txtreferencia').focus();
                        }
                        $('#idvehiculo').val(respuesta);
                    }
                });
    }

    $('#btnvolver').click(function () {
        var id = $(this).data('id');
        console.log("id: " + id);
        window.location.href = "listarCensos.jsp";
    });

    /*function consultarNumeroCenso(numero) {
     var parametros = {
     "numero": numero
     };
     $.ajax({
     data: parametros,
     url: "../../consultarNumeroCenso",
     type: "post"
     })
     .done(function (data) {
     var respuesta = data.toString();
     respuesta = respuesta.toString().trim();
     var datos = respuesta.split(',');
     if (datos[0] === 'si') {
     $('#numero_m').val('');
     $('#numero_m').focus();
     alert("Numero Censo ya se encuentra registrado");
     } else {
     $('#numero_m').val(datos[1]);
     }
     });
     }*/

});

/*function consultarNumeroCensoModificar() {
 var numerocensonew = document.getElementById('txtnumero').value.toString().toUpperCase();
 var numerocenso = document.getElementById('numerocenso').value.toString().toUpperCase();
 
 if (numerocensonew.length > 0 && numerocensonew !== numerocenso) {
 ajax = new nuevoAjax();
 ajax.open("POST", "../Gets/getVerificarNumeroCenso.jsp", true);
 ajax.onreadystatechange = function () {
 if (ajax.readyState == 4 && ajax.status == 200) {
 var docxml = ajax.responseXML;
 var result = docxml.getElementsByTagName('result')[0].childNodes[0].nodeValue;
 if (result == 'si') {
 document.getElementById('txtnumero').value = '';
 alert("Numero Censo ya se encuentra registrado");
 }else{
 var numeroCen = docxml.getElementsByTagName('numerocenso')[0].childNodes[0].nodeValue;
 document.getElementById('txtnumero').value = numeroCen;
 }
 }
 }
 ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
 ajax.send("numerocenso=" + numerocensonew);
 }
 }
 */

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

/*function consultarNumeroCenso() {
 var numerocenso = document.getElementById('txtnumero').value.toString().toUpperCase();
 
 if (numerocenso.length > 0) {
 ajax = new nuevoAjax();
 ajax.open("POST", "../Gets/getVerificarNumeroCenso.jsp", true);
 ajax.onreadystatechange = function () {
 if (ajax.readyState == 4 && ajax.status == 200) {
 var docxml = ajax.responseXML;
 var result = docxml.getElementsByTagName('result')[0].childNodes[0].nodeValue;
 if (result == 'si') {
 document.getElementById('txtnumero').value = '';
 alert("Numero Censo ya se encuentra registrado");
 }else{
 var numeroCen = docxml.getElementsByTagName('numerocenso')[0].childNodes[0].nodeValue;
 document.getElementById('txtnumero').value = numeroCen;
 }
 }
 }
 ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
 ajax.send("numerocenso=" + numerocenso);
 }
 }
 * 
 */

function consultarCensoById(idcenso) {
    document.location.href = "verCenso.jsp?idcenso=" + idcenso;
}

function modificarCensoById(idcenso) {
    if (idcenso.length > 0) {
        document.location.href = "verModificarCenso.jsp?idcenso=" + idcenso;
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

function consultarListadoCenso(opcion) {
    var tipoconsulta = document.getElementById("tipoconsulta").value;

    switch (opcion) {
        case 0 :
            var numero = document.getElementById("txtnumero").value.toString().toUpperCase();
            window.frames[0].location.href = "listarCensos.jsp?opcion=0&tipoconsulta=" + tipoconsulta + "&numero=" + numero;
            break;

        case 1 :
            var tiporef = document.getElementById("cmbtiporeferencia").value;
            var referencia = document.getElementById("txtreferencia").value.toString().toUpperCase();
            window.frames[0].location.href = "listarCensos.jsp?opcion=1&tipoconsulta=" + tipoconsulta + "&tiporef=" + tiporef + "&referencia=" + referencia;
            break;

        case 2:
            var tipodocumento = document.getElementById('cmbtipodoc').value;
            var documento = document.getElementById('txtdocumento').value.toString().toUpperCase();
            window.frames[0].location.href = "listarCensos.jsp?opcion=2&tipoconsulta=" + tipoconsulta + "&tipodocumento=" + tipodocumento + "&documento=" + documento;
            break;

        case 3:
            var fechaini = document.getElementById("txtfechaini").value.toString();
            var fechafin = document.getElementById("txtfechafin").value.toString();
            var punto = document.getElementById('cmbpuntoatenfecha').value;
            window.frames[0].location.href = "listarCensos.jsp?opcion=3&tipoconsulta=" + tipoconsulta + "&fechaini=" + fechaini + "&fechafin=" + fechafin + "&punto=" + punto;
            break;

        case 4:
            var fechaRegini = document.getElementById("txtfechaRegini").value.toString();
            var fechaRegfin = document.getElementById("txtfechaRegfin").value.toString();
            var punto = document.getElementById('cmbpuntoatenfechareg').value;
            window.frames[0].location.href = "listarCensos.jsp?opcion=4&tipoconsulta=" + tipoconsulta + "&fechaRegini=" + fechaRegini + "&fechaRegfin=" + fechaRegfin + "&punto=" + punto;
            break;
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