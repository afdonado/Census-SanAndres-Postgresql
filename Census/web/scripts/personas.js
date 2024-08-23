
function registrarPersona() {
    var documento = document.getElementById('txtdocumento').value.toString().toUpperCase();
    var primerNombre = document.getElementById('txtprinom').value.toString().toUpperCase();
    var primerApellido = document.getElementById('txtpriape').value.toString().toUpperCase();
    var direccion = document.getElementById('txtdireccion').value.toString().toUpperCase();
    var telefono = document.getElementById('txttelefono').value.toString().toUpperCase();

    if (documento.length > 0 && primerNombre.length > 0 && primerApellido.length > 0 && direccion.length > 0 && telefono.length > 0) {
        document.getElementById('frmregistrarpersona').submit();
    } else {
        alert('Debe ingresar como minimo los datos obligatorios (*)');
    }
}

function consultarListadoPersona(opcion) {
    var tipoconsulta = document.getElementById("tipoconsulta").value;

    switch (opcion) {
        case 1 :
            var tipodocumento = document.getElementById('cmbtipodoc').value;
            var documento = document.getElementById('txtdocumento').value.toString().toUpperCase();
            window.frames[0].location.href = "listarPersonas.jsp?opcion=1&tipoconsulta=" + tipoconsulta + "&tipodocumento=" + tipodocumento + "&documento=" + documento;
            break;

        case 2:
            var prinombre = document.getElementById("txtprinom").value.toString().toUpperCase();
            var segnombre = document.getElementById("txtsegnom").value.toString().toUpperCase();
            var priapellido = document.getElementById("txtpriape").value.toString().toUpperCase();
            var segapellido = document.getElementById("txtsegape").value.toString().toUpperCase();
            window.frames[0].location.href = "listarPersonas.jsp?opcion=2&tipoconsulta=" + tipoconsulta + "&prinombre=" + prinombre + "&segnombre=" + segnombre + "&priapellido=" + priapellido + "&segapellido=" + segapellido;
            break;

        case 3:
            var fechanacini = document.getElementById("txtfechanacini").value.toString();
            var fechanacfin = document.getElementById("txtfechanacfin").value.toString();
            window.frames[0].location.href = "listarPersonas.jsp?opcion=3&tipoconsulta=" + tipoconsulta + "&fechanacini=" + fechanacini + "&fechanacfin=" + fechanacfin;
            break;
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

$(function () {
    $('#txtdocumento').blur(function () {
        if ($('#txtdocumento').val().length > 0) {
            consultarDocumentoPersona($('#cmbtipodoc').val(), $('#txtdocumento').val().toString().toUpperCase(), 3);
        }
    });

    function consultarDocumentoPersona(tipodocumento, numdocumento, sw) {
        var parametros = {
            tipodocumento: tipodocumento,
            documento: numdocumento,
            sw: sw
        };
        $.ajax({
            data: parametros,
            url: "../Gets/getVerificarDocumentoPersona.jsp",
            type: "post",
            beforeSend: function () {
                console.log("Procesando");
            }
        })
                .done(function (data) {
                    var cadena = data.toString();
                    var matriz = cadena.toString().trim().split(",");
                    var respuesta = matriz[0];
                    if (respuesta === 'si') {
                        var idpersona = matriz[1];
                        var nombreCompleto = matriz[2];
                        var nomUsuario = matriz[3];
                        if (nomUsuario !== '0') {
                            alert('Esta persona tiene un usuario activo : ' + nomUsuario);
                            $('#txtdocumento').val('');
                            $('#txtnombre').val('');
                            $('#idpersona').val('');
                            $('#txtdocumento').focus();
                        } else {
                            $("#idpersona").val(idpersona);
                            $("#txtnombre").val(nombreCompleto);
                        }
                    } else {
                        if (sw !== '1') {
                            $('#txtnombre').val('');
                            $('#idpersona').val('');
                            viewModalRegPersona(tipodocumento, numdocumento);
                        }
                    }
                });
    }
});

function consultarPersona(sw) {
    var tipodocumento = document.getElementById('cmbtipodoc').value;
    var documento = document.getElementById('txtdocumento').value.toString().toUpperCase();

    if (documento.length > 0) {
        ajax = new nuevoAjax();
        ajax.open("POST", "../Gets/getVerificarDocumentoPersona2.jsp", true);
        ajax.onreadystatechange = function () {
            if (ajax.readyState == 4 && ajax.status == 200) {
                var docxml = ajax.responseXML;
                var result = docxml.getElementsByTagName('result')[0].childNodes[0].nodeValue;
                if (result == 'si') {
                    if (sw == '1') {
                        document.getElementById('txtdocumento').value = '';
                        document.getElementById('txtdocumento').focus();
                        alert("Documento ya se encuentra registrado");
                    }
                    if (sw == '2') {
                        var nombreCompleto = docxml.getElementsByTagName('nombrecompleto')[0].childNodes[0].nodeValue;
                        var idPersona = docxml.getElementsByTagName('idpersona')[0].childNodes[0].nodeValue;
                        document.getElementById('txtnombre').value = nombreCompleto;
                        document.getElementById('idpersona').value = idPersona;
                    }
                    if (sw == '3') {
                        var nombreCompleto = docxml.getElementsByTagName('nombrecompleto')[0].childNodes[0].nodeValue;
                        var idPersona = docxml.getElementsByTagName('idpersona')[0].childNodes[0].nodeValue;

                        var nomUsuario = docxml.getElementsByTagName('nomusuario')[0].childNodes[0].nodeValue;
                        if (nomUsuario != '0') {
                            alert('Esta persona tiene un usuario activo : ' + nomUsuario);
                            document.getElementById('txtdocumento').value = '';
                            document.getElementById('txtnombre').value = '';
                            document.getElementById('idpersona').value = '';
                            document.getElementById('txtdocumento').focus();
                        } else {
                            document.getElementById('txtnombre').value = nombreCompleto;
                            document.getElementById('idpersona').value = idPersona;
                        }
                    }
                } else {
                    if (sw != '1') {
                        document.getElementById('txtnombre').value = '';
                        viewModalRegPersona(tipodocumento, documento);
                    }
                }
            }
        }
        ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        ajax.send("tipodocumento=" + tipodocumento + "&documento=" + documento);
    }
}

function viewModalRegPersona(tipoDocumento, documento) {

    var src = "../Personas/registrarPersona.jsp?opcion=2&tipodoc=" + tipoDocumento + "&documento=" + documento;
    $('#registrarpersona').modal('show');
    $('#registrarpersona iframe').attr('src', src);

}

function consultarPersonaRegVeh(sw, tipodoc, doc, nom, idper) {
    var tipodocumento = document.getElementById(tipodoc).value;
    var documento = document.getElementById(doc).value.toString().toUpperCase();

    if (documento.length > 0) {
        ajax = new nuevoAjax();
        ajax.open("POST", "../Gets/getVerificarDocumentoPersona2.jsp", true);
        ajax.onreadystatechange = function () {
            if (ajax.readyState == 4 && ajax.status == 200) {
                var docxml = ajax.responseXML;
                var result = docxml.getElementsByTagName('result')[0].childNodes[0].nodeValue;
                if (result == 'si') {
                    if (sw == '1') {
                        var nombreCompleto = docxml.getElementsByTagName('nombrecompleto')[0].childNodes[0].nodeValue;
                        var idPersona = docxml.getElementsByTagName('idpersona')[0].childNodes[0].nodeValue;
                        document.getElementById(nom).value = nombreCompleto;
                        document.getElementById(idper).value = idPersona;
                    }
                } else {
                    document.getElementById(nom).value = '';
                    viewModalRegPersona(tipodocumento, documento);
                }
            }
        }
        ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        ajax.send("tipodocumento=" + tipodocumento + "&documento=" + documento);
    }
}

function consultarPersonaModificar(sw) {
    var tipodocumento = document.getElementById('cmbtipodoc').value;
    var documento = document.getElementById('txtdocumento').value.toString().toUpperCase();

    var tipodocumentomod = document.getElementById('tipodocmod').value;
    var documentomod = document.getElementById('documentomod').value.toString().toUpperCase();

    if (documento !== documentomod || tipodocumento !== tipodocumentomod) {
        if (documento.length > 0) {
            ajax = new nuevoAjax();
            ajax.open("POST", "../Gets/getVerificarDocumentoPersona2.jsp", true);
            ajax.onreadystatechange = function () {
                if (ajax.readyState === 4 && ajax.status === 200) {
                    var docxml = ajax.responseXML;
                    var result = docxml.getElementsByTagName('result')[0].childNodes[0].nodeValue;
                    if (result === 'si') {
                        if (sw === '1') {
                            document.getElementById('txtdocumento').value = '';
                            document.getElementById('txtdocumento').focus();
                            alert("Documento ya se encuentra registrado");
                        }
                        if (sw === '2') {
                            var nombreCompleto = docxml.getElementsByTagName('nombrecompleto')[0].childNodes[0].nodeValue;
                            var idPersona = docxml.getElementsByTagName('idpersona')[0].childNodes[0].nodeValue;
                            document.getElementById('txtnombre').value = nombreCompleto;
                            document.getElementById('idpersona').value = idPersona;
                        }
                        if (sw === '3') {
                            var nombreCompleto = docxml.getElementsByTagName('nombrecompleto')[0].childNodes[0].nodeValue;
                            var idPersona = docxml.getElementsByTagName('idpersona')[0].childNodes[0].nodeValue;

                            var nomUsuario = docxml.getElementsByTagName('nomusuario')[0].childNodes[0].nodeValue;
                            if (nomUsuario !== '0') {
                                alert('Esta persona tiene un usuario activo : ' + nomUsuario);
                                document.getElementById('txtdocumento').value = '';
                                document.getElementById('txtnombre').value = '';
                                document.getElementById('idpersona').value = '';
                                document.getElementById('txtdocumento').focus();
                            } else {
                                document.getElementById('txtnombre').value = nombreCompleto;
                                document.getElementById('idpersona').value = idPersona;
                            }
                        }
                    } else {
                        if (sw !== '1') {
                            document.getElementById('txtnombre').value = '';
                            viewModalRegPersona(tipodocumento, documento);
                        }
                    }
                }
            }
            ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            ajax.send("tipodocumento=" + tipodocumento + "&documento=" + documento);
        }
    }
}

function consultarPersonaById(idpersona) {
    document.location.href = "verPersona.jsp?idpersona=" + idpersona;
}

function modificarPersonaById(idpersona) {
    if (idpersona.length > 0) {
        document.location.href = "verModificarPersona.jsp?idpersona=" + idpersona;
    }
}

function modificarPersona() {
    if (confirm("Confirma Actualizar la persona?")) {
        var documento = document.getElementById('txtdocumento').value.toString().toUpperCase();
        var primerNombre = document.getElementById('txtprinom').value.toString().toUpperCase();
        var primerApellido = document.getElementById('txtpriape').value.toString().toUpperCase();
        var direccion = document.getElementById('txtdireccion').value.toString().toUpperCase();
        var telefono = document.getElementById('txttelefono').value.toString().toUpperCase();

        if (documento.length > 0 && primerNombre.length > 0 && primerApellido.length > 0 && direccion.length > 0 && telefono.length > 0) {
            document.getElementById('frmmodificarpersona').submit();
        } else {
            alert('Debe ingresar como minimo los datos obligatorios (*)');
        }
    }
}