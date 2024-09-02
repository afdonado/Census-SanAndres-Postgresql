
$(function () {

    $('#passwordactual').blur(function () {
        var passwordactual = $('#passwordactual').val();
        var idusuario = $('#idusuario').val();
        if (passwordactual.length > 0 && idusuario.length > 0) {
            validarPasswordActual(passwordactual, idusuario);
        }
    });

    $('#password').blur(function () {
        var password = $('#password').val();
        var repetirpassword = $('#repetirpassword').val();
        if (password.trim() !== "") {
            if (password.length > 5) {
                validarPassword(password, repetirpassword);
            } else {
                alert('El password debe tener minimo 6 caracteres');
                $('#password').val('');
                $('#password').focus();
            }
        }
    });

    $('#repetirpassword').blur(function () {
        var password = $('#password').val();
        var repetirpassword = $('#repetirpassword').val();
        if (repetirpassword.trim() !== "") {
            if (repetirpassword.length > 5) {
                validarPassword(password, repetirpassword);
            } else {
                alert('El password debe tener minimo 6 caracteres');
                $('#repetirpassword').val('');
                $('#repetirpassword').focus();
            }
        }
    });

    function validarPassword(password, repetirpassword) {
        if (password.length > 5 && repetirpassword.length > 5 && password !== repetirpassword) {
            alert('Los password no coinciden');
            $('#password').val('');
            $('#repetirpassword').val('');
            $('#password').focus();
        }
    }

    function validarPasswordActual(passwordactual, idusuario) {
        var parametros = {
            "idusuario": idusuario,
            "passwordactual": passwordactual
        };
        $.ajax({
            data: parametros,
            url: "../../verificarPasswordActual",
            type: "post"
        })
                .done(function (data) {
                    var respuesta = data.toString();
                    respuesta = respuesta.toString().trim();
                    if (respuesta === 'no') {
                        $('#passwordactual').val('');
                        $('#passwordactual').focus();
                        alert("Password actual no es correcto");
                    }
                });
    }

    $("#guardar").click(function (event) {
        event.preventDefault(); // Evita la navegación del enlace
        $("#frmactualizarusuario").submit(); // Envia el formulario
    });
    
    $('#btnvolver').click(function () {
        var id = $(this).data('id');
        window.location.href = "listarUsuarios.jsp";
    });

});
/*
function registrarUsuario() {
    var documento = document.getElementById('txtdocumento').value.toString().toUpperCase();
    var nombreusuario = document.getElementById('nombreusuario').value.toString().toUpperCase();
    var password = document.getElementById('password').value;
    var repetirpassword = document.getElementById('repetirpassword').value;

    if (documento.length === 0 && nombreusuario.length === 0 && password.length === 0 && repetirpassword.length === 0) {
        alert('Debe ingresar como minimo los datos obligatorios (*)');
    } else {
        document.getElementById('frmregistrarusuario').submit();
    }
}

function LimpiarCampos() {
    document.getElementById('nombreusuario').value = "";
    document.getElementById('password').value = "";
}

function consultarListadoUsuario(opcion) {
    var tipoconsulta = document.getElementById("tipoconsulta").value;

    switch (opcion) {
        case 0 :
            var nombre = document.getElementById("nombreusuario").value.toString().toUpperCase();
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
 * 
 */