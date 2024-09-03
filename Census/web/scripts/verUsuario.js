
$(function () {

    function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                results = regex.exec(location.search);
        return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    }

    var opcion = getParameterByName('opcion');
    var id = getParameterByName('id');

    if (opcion && id) {

        $.ajax({
            url: '../../cargarDatosUsuario',
            method: 'get',
            data: {id: id},
            success: function (response) {

                if (response.status === "success") {

                    $('#txttipodocumento').val(response.usuario.TIPO_DOCUMENTO);
                    $('#txtdocumento').val(response.usuario.NUMERO_DOCUMENTO);
                    $('#txtnombre').val(response.usuario.NOMBRE_USUARIO);
                    $('#txtperfil').val(response.usuario.PERFIL);

                    $('#txtfechainicial').val(response.usuario.FECHA_INICIO);
                    $('#txtfechafinal').val(response.usuario.FECHA_FINAL);
                    $('#txtestado').val(response.usuario.ESTADO);
                    
                } else if (response.status === "fail") {
                    alert(response.message);
                } else if (response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de cargar datos usuario: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de cargar datos usuario.");
            }
        });

    } else {
        console.log("Parámetros no encontrados en la URL");
    }
    
    $('#btnvolver').click(function () {
        window.location.href = "listarUsuarios.jsp";
    });

});
