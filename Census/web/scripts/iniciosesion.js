
$(function () {

    $("#frminiciosesion").submit(function (event) {
        event.preventDefault();

        var parametros = $(this).serialize();

        $.ajax({
            data: parametros,
            url: "inicioSesion",
            type: "POST",
            dataType: "json",
            beforeSend: function () {
                $('#btniniciar').prop('disabled', true);
            },
            success: function (response) {
                if (response.status === "fail" || response.status === "error") {
                    alert(response.message);
                } else {
                    window.location.href = response.redirect;
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error al iniciar sesion: ", textStatus, errorThrown);
                alert("Error al iniciar sesion");
            }
        });
    });
    
    $('#btniniciar').click(function () {
        var nombre = $('#txtloginusuario').val();
        var password = $('#txtloginpassword').val();

        if (nombre.length === 0 && password.length === 0) {
            alert('Debe ingresar un nombre de usuario y contraseña validos');
            return false;
        } else {
            $('#frminiciosesion').trigger("submit");
        }
    });

});