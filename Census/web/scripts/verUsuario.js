
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

                    $('#txttipodocumento').val(response.usuario.tipo_documento);
                    $('#txtdocumento').val(response.usuario.numero_documento);
                    $('#txtnombre').val(response.usuario.nombre_usuario);
                    $('#txtperfil').val(response.usuario.perfil);

                    $('#txtfechainicial').val(response.usuario.fecha_inicio);
                    $('#txtfechafinal').val(response.usuario.fecha_final);
                    $('#txtestado').val(response.usuario.estado);
                    
                    $(".btneditar").attr("data-id", response.usuario.usu_id);
                    
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
    
    $('#btneditar').click(function () {
        var id = $(this).data('id');
        window.location.href = "modificarUsuario.jsp?opcion=2&id="+id;
    });

});
