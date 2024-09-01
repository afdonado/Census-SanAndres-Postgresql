
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
            success: function (data) {
                $('#txttipodocumento').val(data.TIPO_DOCUMENTO);
                $('#txtdocumento').val(data.NUMERO_DOCUMENTO);
                $('#txtnombre').val(data.NOMBRE_USUARIO);
                $('#txtperfil').val(data.PERFIL);
                
                $('#txtfechainicial').val(data.FECHA_INICIO);
                $('#txtfechafinal').val(data.FECHA_FINAL);
                $('#txtestado').val(data.ESTADO);
            }
        });

    } else {
        console.log("Parámetros no encontrados en la URL");
    }

});
