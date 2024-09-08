
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
    
    $('#btnguardar').click(function () {
        var passaactual = $('#txtpasswordactual').val();
        var pass = $('#txtpassword').val();
        var reppass = $('#txtrepetirpassword').val();

        if (passaactual.length === 0 && pass.length === 0 && reppass.length === 0) {
            alert('Debe ingresar los datos obligatorios (*)');
            return false;
        } else {
            $('#frmactualizarusuario').trigger("submit");
        }
    });
    
    $("#frmactualizarusuario").submit(function (event) {
        console.log("usuario act pass");
        event.preventDefault();

        var parametros = $(this).serialize();

        $.ajax({
            data: parametros,
            url: "../../actualizarPassword",
            type: "POST",
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    alert(response.message);
                    window.location.href = "../../dashboard";
                } else if (response.status === "fail") {
                    alert(response.message);
                } else if (response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de actualizar contraseña: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de actualizar contraseña.");
            }
        });
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