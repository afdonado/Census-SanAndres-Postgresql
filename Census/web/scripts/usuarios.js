
$(function () {
    $('#txtnomusuario').blur(function () {
        if ($('#txtnomusuario').val().length > 0) {
            verificarNombreUsuario($('#txtnomusuario').val().toString().toUpperCase());
        }
    });

    function verificarNombreUsuario(nomusuario) {
        var parametros = {
            "nomusuario": nomusuario
        };
        $.ajax({
            data: parametros,
            url: "../Gets/getVerificarNombreUsuario.jsp",
            type: "post"
        })
                .done(function (data) {
                    var respuesta = data.toString();
                    respuesta = respuesta.toString().trim();
                    if (respuesta == 'si') {
                        $('#txtnomusuario').val('');
                        $('#txtpass').val('');
                        $('#txtreppass').val('');
                        $('#txtnomusuario').focus();
                        alert("Nombre de usuario ya se encuentra registrado");
                    }
                });
    }
});

function registrarUsuario() {
    var documento = document.getElementById('txtdocumento').value.toString().toUpperCase();
    var nomusu = document.getElementById('txtnomusuario').value.toString().toUpperCase();
    var pass = document.getElementById('txtpass').value;
    var repass = document.getElementById('txtreppass').value;

    if (documento.length === 0 && nomusu.length === 0 && pass.length === 0 && repass.length === 0) {
        alert('Debe ingresar como minimo los datos obligatorios (*)');
    } else {
        document.getElementById('frmregistrarusuario').submit();
    }
}

function LimpiarCampos() {
    document.getElementById('txtnomusuario').value = "";
    document.getElementById('txtpass').value = "";
}


function validarPass() {
    var pass = document.getElementById('txtpass').value;
    var repass = document.getElementById('txtreppass').value;

    if (pass.length > 5 && repass.length > 5 && pass !== repass) {
        alert('Los password no coinciden');
        document.getElementById('txtpass').value = '';
        document.getElementById('txtreppass').value = '';
    }

}

function verificarNombreUsuarioAjax() {
    var nomusuario = document.getElementById('txtnomusu').value.toString().toUpperCase();

    if (nomusuario.length > 0) {
        ajax = new nuevoAjax();
        ajax.open("POST", "../Gets/getVerificarNombreUsuario.jsp", true);
        ajax.onreadystatechange = function () {
            if (ajax.readyState == 4 && ajax.status == 200) {
                var docxml = ajax.responseXML;
                var result = docxml.getElementsByTagName('result')[0].childNodes[0].nodeValue;
                if (result == 'si') {
                    document.getElementById('txtnomusu').value = '';
                    document.getElementById('txtpass').value = '';
                    document.getElementById('txtreppass').value = '';
                    document.getElementById('txtnomusu').focus();
                    alert("Nombre de usuario ya se encuentra registrado");
                }
            }
        }
        ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        ajax.send("nomusuario=" + nomusuario);
    }
}

function consultarListadoUsuario(opcion) {
    var tipoconsulta = document.getElementById("tipoconsulta").value;

    switch (opcion) {
        case 0 :
            var nombre = document.getElementById("txtnomusu").value.toString().toUpperCase();
            window.frames[0].location.href = "listarUsuarios.jsp?opcion=0&tipoconsulta=" + tipoconsulta + "&nombre=" + nombre;
            break;

        case 1:
            var tipodocumento = document.getElementById('cmbtipodoc').value;
            var documento = document.getElementById('txtdocumento').value.toString().toUpperCase();
            window.frames[0].location.href = "listarUsuarios.jsp?opcion=1&tipoconsulta=" + tipoconsulta + "&tipodocumento=" + tipodocumento + "&documento=" + documento;
            break;

        case 2 ://General
            var puntoatencion = document.getElementById("cmbpuntoaten").value;
            window.frames[0].location.href = "listarUsuarios.jsp?opcion=2&tipoconsulta=" + tipoconsulta + "&puntoatencion=" + puntoatencion;
            break;
    }
}

function generarReporteUsuario(opcion) {

    switch (opcion) {
        case 0:
            var puntoatencion = document.getElementById('cmbpuntoaten').value;
            window.frames[0].location.href = "generarReporteUsuarios.jsp?opcion=0&puntoatencion=" + puntoatencion;
            break;
    }

}

function modificarUsuarioById(idusuario) {
    if (idusuario.length > 0) {
        document.location.href = "verModificarUsuario.jsp?idusuario=" + idusuario;
    }
}

function modificarUsuario() {
    if (confirm("Confirma Actualizar el usuario?")) {
        document.getElementById('frmmodificarusuario').submit();
    }
}

function restaurarPassword() {
    var idusuario = document.getElementById('idusuario').value.toString().toUpperCase();
    if (idusuario > 0) {
        document.getElementById('frmconsultarusuario').action = '../../restaurarPassword';
        document.getElementById('frmconsultarusuario').submit();
    }
}

function validarPassActual() {
    var idusuario = document.getElementById('idusuario').value.toString().toUpperCase();
    var passactual = document.getElementById('txtpassactual').value.toString().toUpperCase();

    if (idusuario.length > 0 && passactual.length > 0) {
        ajax = new nuevoAjax();
        ajax.open("POST", "../Gets/getVerificarPasswordActual.jsp", true);
        ajax.onreadystatechange = function () {
            if (ajax.readyState == 4 && ajax.status == 200) {
                var docxml = ajax.responseXML;
                var result = docxml.getElementsByTagName('result')[0].childNodes[0].nodeValue;
                if (result == 'no') {
                    document.getElementById('txtpassactual').value = '';
                    document.getElementById('txtpassactual').focus();
                    alert("Password actual no es correcto");
                }
            }
        }
        ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        ajax.send("idusuario=" + idusuario + "&passactual=" + passactual);
    }
}

function actualizarPassword() {
    var idusuario = document.getElementById('idusuario').value.toString().toUpperCase();
    var passactual = document.getElementById('txtpassactual').value.toString().toUpperCase();
    var pass = document.getElementById('txtpass').value.toString().toUpperCase();
    var reppass = document.getElementById('txtreppass').value.toString().toUpperCase();
    if (idusuario > 0 && passactual.length > 0 && pass.length > 7 && reppass.length > 7) {
        document.getElementById('frmactualizarusuario').action = '../../actualizarPassword';
        document.getElementById('frmactualizarusuario').submit();
    }
}

function LimpiarCamposActualizar() {
    document.getElementById('txtpassactual').value = "";
}

function consultarUsuarioById(idusuario) {
    document.location.href = "verUsuario.jsp?idusuario=" + idusuario;
}