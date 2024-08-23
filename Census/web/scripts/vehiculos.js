
function registrarVehiculo() {
    var motor = document.getElementById('txtmotor').value.toString().toUpperCase();
    var chasis = document.getElementById('txtchasis').value.toString().toUpperCase();
    var modelo = document.getElementById('txtmodelo').value;

    var runt = document.getElementById('cmbrunt').value;
    var lictransito = document.getElementById('txtlictransito').value.toString().toUpperCase();
    var paismatricula = document.getElementById('cmbpaismatricula').value;
    var ciudadmatri = document.getElementById("txtciudadmatri").value.toString().toUpperCase();
    var tipodocimportacion = document.getElementById('cmdtipodocimportacion').value;
    var docimportacion = document.getElementById("txtdocimportacion").value.toString().toUpperCase();

    var colores = document.getElementById('txtcolores').value.toString().toUpperCase();
    var marcas = document.getElementById('txtmarcas').value.toString().toUpperCase();
    var lineas = document.getElementById('txtlineas').value.toString().toUpperCase();

    if (colores.length == 0 && marcas.length == 0 && lineas.length == 0) {
        alert('Debe ingresar los datos obligatorios (*)');
        return false;
    }

    if (motor.length == 0 && chasis.length == 0 && modelo.length == 0) {
        alert('Debe ingresar los datos obligatorios (*)');
        return false;
    }

    if (runt == "S" && lictransito.length == 0) {
        alert('Debe ingresar numero de Licencia de Transito datos obligatorios (*)');
        return false;
    }

    if (paismatricula != "18" && ciudadmatri.length == 0) {
        alert('Debe ingresar los datos obligatorios (*)');
        return false;
    }

    if (tipodocimportacion != "0" && docimportacion.length == 0) {
        alert('Debe ingresar los datos obligatorios (*)');
        return false;
    }

    document.getElementById('frmregistrarvehiculo').submit();

}

function verificarVehiculo(tiporef, nombrecampo) {
    var valorreferencia = document.getElementById(nombrecampo).value.toString().toUpperCase();

    if (valorreferencia.length > 0) {
        ajax = new nuevoAjax();
        ajax.open("POST", "../Gets/getVerificarVehiculo.jsp", true);
        ajax.onreadystatechange = function () {
            if (ajax.readyState == 4 && ajax.status == 200) {
                var docxml = ajax.responseXML;
                var result = docxml.getElementsByTagName('result')[0].childNodes[0].nodeValue;
                if (result == 'si') {
                    if (tiporef == '1') {
                        alert("Placa ya se encuentra registrada");
                    }
                    if (tiporef == '2') {
                        alert("Motor ya se encuentra registrado");
                    }
                    if (tiporef == '3') {
                        alert("Chasis ya se encuentra registrado");
                    }
                    if (tiporef == '4') {
                        alert("Serie ya se encuentra registrada");
                    }
                    document.getElementById(nombrecampo).value = '';
                    document.getElementById(nombrecampo).focus();
                }
            }
        }
        ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        ajax.send("tiporef=" + tiporef + "&valorreferencia=" + valorreferencia + "&opcion=1");
    }
}

function consultarVehiculoByReferencia() {

    var tiporef = document.getElementById("cmbtiporeferencia").value;
    var valorreferencia = document.getElementById("txtreferencia").value.toString().toUpperCase();
    namediv = document.getElementById('datosvehiculo');
    ajax = new nuevoAjax();
    ajax.open("POST", "../Vehiculos/verVehiculo.jsp", true);
    ajax.onreadystatechange = function () {
        if (ajax.readyState == 4) {
            namediv.innerHTML = ajax.responseText;
        }
    }
    ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    ajax.send("tiporef=" + tiporef + "&valorreferencia=" + valorreferencia + "&namediv=" + namediv);
}

function consultarListadoVehiculo(opcion) {
    var tipoconsulta = document.getElementById("tipoconsulta").value;

    switch (opcion) {
        case 1 :
            var tiporef = document.getElementById("cmbtiporeferencia").value;
            var referencia = document.getElementById("txtreferencia").value.toString().toUpperCase();
            window.frames[0].location.href = "listarVehiculos.jsp?opcion=1&tipoconsulta=" + tipoconsulta + "&tiporef=" + tiporef + "&referencia=" + referencia;
            break;

        case 2:
            var tipodocumento = document.getElementById('cmbtipodoc').value;
            var documento = document.getElementById('txtdocumento').value.toString().toUpperCase();
            window.frames[0].location.href = "listarVehiculos.jsp?opcion=2&tipoconsulta=" + tipoconsulta + "&tipodocumento=" + tipodocumento + "&documento=" + documento;
            break;

        case 3:
            var fechaRegini = document.getElementById("txtfechaRegini").value.toString();
            var fechaRegfin = document.getElementById("txtfechaRegfin").value.toString();
            window.frames[0].location.href = "listarVehiculos.jsp?opcion=3&tipoconsulta=" + tipoconsulta + "&fechaRegini=" + fechaRegini + "&fechaRegfin=" + fechaRegfin;
            break;

        case 4:
            ancho = 800;
            alto = 640;
            barra = 0;
            izquierda = (screen.width) ? (screen.width - ancho) / 2 : 100;
            arriba = (screen.height) ? (screen.height - alto) / 2 : 100;
            opciones = 'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=' + barra + ',resizable=no,width=' + ancho + ',height=' + alto + ',left=' + izquierda + ',top=' + arriba;
            url = "generarReporteVehiculos.jsp?opcion=1";
            window.open(url, 'popUp', opciones);
            break;
    }

}

function consultarVehiculoById(idvehiculo) {
    document.location.href = "verVehiculo.jsp?idvehiculo=" + idvehiculo;
}

function consultarRefVehiculo() {
    var tiporef = document.getElementById("cmbtiporeferencia").value;
    var valorreferencia = document.getElementById("txtreferencia").value.toString().toUpperCase();

    if (valorreferencia.length > 0) {
        ajax = new nuevoAjax();
        ajax.open("POST", "../Gets/getVerificarVehiculo.jsp", true);
        ajax.onreadystatechange = function () {
            if (ajax.readyState == 4 && ajax.status == 200) {
                var docxml = ajax.responseXML;
                var result = docxml.getElementsByTagName('result')[0].childNodes[0].nodeValue;
                if (result == 'si') {
                    var idvehiculo = docxml.getElementsByTagName('idvehiculo')[0].childNodes[0].nodeValue;
                    document.getElementById("idvehiculo").value = idvehiculo;
                    document.getElementById("txtreferencia").style.background = "#81F781";
                } else {
                    if (result == 'sicensado') {
                        alert('Vehiculo ya fue censado');
                    }
                    if (result == 'noexiste') {
                        alert('Vehiculo no registrado');
                    }
                    document.getElementById("idvehiculo").value = "";
                    document.getElementById("txtreferencia").value = "";
                    document.getElementById("txtreferencia").style.background = "#FE2E2E";
                }
            }
        }
        ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        ajax.send("tiporef=" + tiporef + "&valorreferencia=" + valorreferencia + "&opcion=2");
    }
}


function habilitarCombosDepMun() {
    var paismatricula = document.getElementById('cmbpaismatricula').value;
    if (paismatricula == "18") {
        document.getElementById("comboDepMatri").style.display = "";
        document.getElementById("comboMunMatri").style.display = "";
        document.getElementById("ciudadMatri").style.display = "none";
    } else {
        document.getElementById("comboDepMatri").style.display = "none";
        document.getElementById("comboMunMatri").style.display = "none";
        document.getElementById("ciudadMatri").style.display = "";
    }
}

function habilitarCamposImportacion() {
    var tipodocimportacion = document.getElementById('cmdtipodocimportacion').value;
    if (tipodocimportacion == "0") {
        document.getElementById("docImp").style.display = "none";
        document.getElementById("fechaImp").style.display = "none";
        document.getElementById("paisImp").style.display = "none";
    } else {
        document.getElementById("docImp").style.display = "";
        document.getElementById("fechaImp").style.display = "";
        document.getElementById("paisImp").style.display = "";
    }
}

function habilitarCampoLicenciaTransito() {
    var runt = document.getElementById('cmbrunt').value;
    if (runt == "N") {
        document.getElementById("lictransito").style.display = "none";
        document.getElementById("fechamatri").style.display = "none";
        document.getElementById("paismatri").style.display = "none";
        document.getElementById("comboDepMatri").style.display = "none";
        document.getElementById("comboMunMatri").style.display = "none";
        document.getElementById("ciudadMatri").style.display = "none";

    } else {
        document.getElementById("lictransito").style.display = "";
        document.getElementById("fechamatri").style.display = "";
        document.getElementById("paismatri").style.display = "";
        var paismatricula = document.getElementById('cmbpaismatricula').value;
        if (paismatricula == "18") {
            document.getElementById("comboDepMatri").style.display = "";
            document.getElementById("comboMunMatri").style.display = "";
            document.getElementById("ciudadMatri").style.display = "none";
        } else {
            document.getElementById("comboDepMatri").style.display = "none";
            document.getElementById("comboMunMatri").style.display = "none";
            document.getElementById("ciudadMatri").style.display = "";
        }
    }
}

function viewModalRegVehiculo(tipoReferencia, referencia) {

    var src = "../Vehiculos/registrarVehiculo.jsp?opcion=2&tiporeferencia=" + tipoReferencia + "&referencia=" + referencia;
    $('#registrarvehiculo').modal('show');
    $('#registrarvehiculo iframe').attr('src', src);

}

var identificador = 1;

function AgregarCamposPersona() {

    if (identificador < 5) {
        identificador = identificador + 1;
        var contentID = document.getElementById("personasvehiculo");
        var nuevoDivPersona = document.createElement('div');
        nuevoDivPersona.setAttribute('id', 'contenedor' + identificador);
        nuevoDivPersona.setAttribute('class', 'row');
        ajax = new nuevoAjax();
        ajax.open("POST", "../Gets/getCrearCampos.jsp", true);
        ajax.onreadystatechange = function () {
            if (ajax.readyState == 4) {
                document.getElementById("txtcantpersonas").value = identificador;
                nuevoDivPersona.innerHTML = ajax.responseText;
                contentID.appendChild(nuevoDivPersona);
            }
        }
        ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        ajax.send("identificador=" + identificador);
    } else {
        alert("No se pueden agregar mas personas");
    }
}

function QuitarCampoPersona() {
    if (identificador != 0) {
        var contentID = document.getElementById('personasvehiculo');
        contentID.removeChild(document.getElementById('contenedor' + identificador));
        identificador = identificador - 1;
        document.getElementById("txtcantpersonas").value = identificador;
    }
}

var identificadorModificar = 0;

function AgregarCamposPersonaModificar() {

    if (identificadorModificar < 5) {
        identificadorModificar = identificadorModificar + 1;
        var contentID = document.getElementById("personasvehiculo");
        var nuevoDivPersona = document.createElement('div');
        nuevoDivPersona.setAttribute('id', 'contenedor' + identificadorModificar);
        nuevoDivPersona.setAttribute('class', 'row');
        ajax = new nuevoAjax();
        ajax.open("POST", "../Gets/getCrearCampos.jsp", true);
        ajax.onreadystatechange = function () {
            if (ajax.readyState == 4) {
                document.getElementById("txtcantpersonas").value = identificadorModificar;
                nuevoDivPersona.innerHTML = ajax.responseText;
                contentID.appendChild(nuevoDivPersona);
            }
        }
        ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        ajax.send("identificador=" + identificadorModificar);
    } else {
        alert("No se pueden agregar mas personas");
    }
}

function QuitarCampoPersonaModificar() {
    if (identificadorModificar != 0) {
        var contentID = document.getElementById('personasvehiculo');
        contentID.removeChild(document.getElementById('contenedor' + identificadorModificar));
        identificadorModificar = identificadorModificar - 1;
        document.getElementById("txtcantpersonas").value = identificadorModificar;
    }
}

function modificarVehiculoById(idvehiculo) {
    if (idvehiculo.length > 0) {
        document.location.href = "verModificarVehiculo.jsp?idvehiculo=" + idvehiculo;
    }
}

function verificarVehiculoModificar(tiporef, nombrecampo) {
    var valorreferencia = document.getElementById(nombrecampo).value.toString().toUpperCase();
    var valorreferenciamod = document.getElementById(nombrecampo + "mod").value.toString().toUpperCase();

    if (valorreferencia != valorreferenciamod) {
        if (valorreferencia.length > 0) {
            ajax = new nuevoAjax();
            ajax.open("POST", "../Gets/getVerificarVehiculo.jsp", true);
            ajax.onreadystatechange = function () {
                if (ajax.readyState == 4 && ajax.status == 200) {
                    var docxml = ajax.responseXML;
                    var result = docxml.getElementsByTagName('result')[0].childNodes[0].nodeValue;
                    if (result == 'si') {
                        if (tiporef == '1') {
                            alert("Placa ya se encuentra registrada");
                        }
                        if (tiporef == '2') {
                            alert("Motor ya se encuentra registrado");
                        }
                        if (tiporef == '3') {
                            alert("Chasis ya se encuentra registrado");
                        }
                        if (tiporef == '4') {
                            alert("Serie ya se encuentra registrada");
                        }
                        document.getElementById(nombrecampo).value = '';
                        document.getElementById(nombrecampo).focus();
                    }
                }
            }
            ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            ajax.send("tiporef=" + tiporef + "&valorreferencia=" + valorreferencia + "&opcion=1");
        }
    }
}

function anularPersonaVehiculo(idperveh) {
    document.getElementById("estadoperveh" + idperveh).value = 2;
    document.getElementById("registropersona" + idperveh).style.display = "none";

}

function modificarVehiculo() {
    if (confirm("Confirma Actualizar el vehiculo?")) {

        var motor = document.getElementById('txtmotor').value.toString().toUpperCase();
        var chasis = document.getElementById('txtchasis').value.toString().toUpperCase();
        var modelo = document.getElementById('txtmodelo').value;

        var runt = document.getElementById('cmbrunt').value;
        var lictransito = document.getElementById('txtlictransito').value.toString().toUpperCase();
        var paismatricula = document.getElementById('cmbpaismatricula').value;
        var ciudadmatri = document.getElementById("txtciudadmatri").value.toString().toUpperCase();
        var tipodocimportacion = document.getElementById('cmdtipodocimportacion').value;
        var docimportacion = document.getElementById("txtdocimportacion").value.toString().toUpperCase();

        var colores = document.getElementById('txtcolores').value.toString().toUpperCase();
        var marcas = document.getElementById('txtmarcas').value.toString().toUpperCase();
        var lineas = document.getElementById('txtlineas').value.toString().toUpperCase();

        if (colores.length == 0 && marcas.length == 0 && lineas.length == 0) {
            alert('Debe ingresar los datos de color, marca y linea son obligatorios (*)');
            return false;
        }

        if (motor.length == 0 && chasis.length == 0 && modelo.length == 0) {
            alert('Debe ingresar los datos de motor, chasis y modelo obligatorios (*)');
            return false;
        }

        if (runt == "S" && lictransito.length == 0) {
            alert('Debe ingresar numero de Licencia de Transito datos obligatorios (*)');
            return false;
        }

        if (paismatricula != "18" && ciudadmatri.length == 0) {
            alert('Debe ingresar los datos de pais y ciudad de matricula obligatorios (*)');
            return false;
        }

        if (tipodocimportacion != "0" && docimportacion.length == 0) {
            alert('Debe ingresar los datos de importacion obligatorios (*)');
            return false;
        }

        document.getElementById('frmmodificarvehiculo').submit();
    }

}

function generarReporteVehiculo(opcion) {

    ancho = 200;
    alto = 140;
    barra = 0;
    izquierda = (screen.width) ? (screen.width - ancho) / 2 : 100;
    arriba = (screen.height) ? (screen.height - alto) / 2 : 100;
    opciones = 'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=' + barra + ',resizable=no,width=' + ancho + ',height=' + alto + ',left=' + izquierda + ',top=' + arriba;
    switch (opcion) {
        case 1:
        case 1 :
            var tiporef = document.getElementById("cmbtiporeferencia").value;
            var referencia = document.getElementById("txtreferencia").value.toString().toUpperCase();
            window.frames[0].location.href = "generarReporteVehiculos.jsp?opcion=1&tiporef=" + tiporef + "&referencia=" + referencia;
            break;

        case 2:
            var tipodocumento = document.getElementById('cmbtipodoc').value;
            var documento = document.getElementById('txtdocumento').value.toString().toUpperCase();
            window.frames[0].location.href = "generarReporteVehiculos.jsp?opcion=2&tipodocumento=" + tipodocumento + "&documento=" + documento;
            break;

        case 3:
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

function consultarRefVehiculoModificar() {
    var tiporefnew = document.getElementById("cmbtiporeferencia").value;
    var valorreferencianew = document.getElementById("txtreferencia").value.toString().toUpperCase();

    var tiporef = document.getElementById("tiporeferencia").value;
    var valorreferencia = document.getElementById("referencia").value.toString().toUpperCase();

    if (valorreferencia.length > 0) {
        if (tiporefnew != tiporef || valorreferencianew != valorreferencia) {
            ajax = new nuevoAjax();
            ajax.open("POST", "../Gets/getVerificarVehiculo.jsp", true);
            ajax.onreadystatechange = function () {
                if (ajax.readyState == 4 && ajax.status == 200) {
                    var docxml = ajax.responseXML;
                    var result = docxml.getElementsByTagName('result')[0].childNodes[0].nodeValue;
                    if (result == 'si') {
                        var idvehiculo = docxml.getElementsByTagName('idvehiculo')[0].childNodes[0].nodeValue;
                        document.getElementById("idvehiculo").value = idvehiculo;
                        document.getElementById("txtreferencia").style.background = "#81F781";
                    } else {
                        if (result == 'sicensado') {
                            alert('Vehiculo ya fue censado');
                        }
                        if (result == 'noexiste') {
                            alert('Vehiculo no registrado');
                        }
                        document.getElementById("idvehiculo").value = "";
                        document.getElementById("txtreferencia").value = "";
                        document.getElementById("txtreferencia").style.background = "#FE2E2E";
                    }
                }
            }
            ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            ajax.send("tiporef=" + tiporefnew + "&valorreferencia=" + valorreferencianew + "&opcion=2");
        }
    }
}