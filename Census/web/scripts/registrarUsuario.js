$(function () {
    
    $('#txtnombre').blur(function () {
        var nombreusuario = $('#txtnombre').val().toString().toUpperCase();
        if (nombreusuario.length > 0) {
            verificarNombreUsuario(nombreusuario);
        }
    });
    
    function verificarNombreUsuario(nombre) {
        var parametros = {
            nombre: nombre
        };
        $.ajax({
            data: parametros,
            url: "../../verificarNombreUsuario",
            type: "post"
        })
                .done(function (data) {
                    var respuesta = data.toString();
                    respuesta = respuesta.toString().trim();
                    if (respuesta === 'si') {
                        $('#nombreusuario').val('');
                        $('#password').val('');
                        $('#repetirpassword').val('');
                        $('#nombreusuario').focus();
                        alert("Nombre de usuario ya se encuentra registrado");
                    }
                });
    }

    $.ajax({
        url: '../../cargarPerfiles',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbperfiles');
            select.empty();
            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.nombre + '</option>');
            });
        }
    });

    $.ajax({
        url: '../../cargarTiposDocumento',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbtiposdocumento');
            select.empty();
            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
            });
        }
    });



    $('#btnguardar').click(function () {
        var nombre = $('#txtnombre').val();

        if (nombre.length > 0) {
            $('#frmregistrarusuario').submit();
        } else {
            alert('Debe ingresar el nombre del usuario');
        }
    });

});
