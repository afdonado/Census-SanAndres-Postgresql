
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
            url: '../../cargarDatosVerificacion',
            method: 'get',
            data: {id: id},
            success: function (response) {

                if (response.status === "success") {

                    $('#txtnumerocenso').val(response.verificacion.NUMERO);
                    $('#txtfechacenso').val(response.verificacion.FECHA);
                    if (response.verificacion.VERIFICACION_RUNT === 'S') {
                        $('#chkrunt').prop('checked', true);
                    }
                    if (response.verificacion.VERIFICACION_DOC === 'S') {
                        $('#chkdocumentos').prop('checked', true);
                    }
                    if (response.verificacion.VERIFICACION_FOTO === 'S') {
                        $('#chkfotos').prop('checked', true);
                    }

                    $('#txtestadoverificacion').val(response.verificacion.ESTADO_VERIFICACION);
                    $("#btneditar").attr("data-id", response.verificacion.CEN_ID);
                    
                } else if (response.status === "fail" || response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de cargar datos censo: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de cargar datos censo.");
            }
        });
    } else {
        console.log("Parámetros no encontrados en la URL");
    }

    $('#btneditar').click(function () {
        var id = $(this).data('id');
        window.location.href = "modificarVerificacion.jsp?opcion=2&id=" + id;
    });
    $('#btnvolver').click(function () {
        window.location.href = "listarVerificaciones.jsp";
    });
});
