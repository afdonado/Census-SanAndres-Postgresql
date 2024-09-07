
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

});

function generarReporteUsuario(opcion) {

    switch (opcion) {
        case 0:
            var puntoatencion = document.getElementById('cmbpuntoaten').value;
            window.frames[0].location.href = "generarReporteUsuarios.jsp?opcion=0&puntoatencion=" + puntoatencion;
            break;
    }

}