
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
                    var estadoverificacionId = response.verificacion.ESTADO_VERIFICACION;

                    if (response.verificacion.VERIFICACION_DOC === 'S') {
                        $('#chkdocumentos').prop('checked', true);
                    } else {
                        $('#chkdocumentos').prop('checked', false);
                    }
                    
                    if (response.verificacion.VERIFICACION_FOTOS === 'S') {
                        $('#chkfotos').prop('checked', true);
                    } else {
                        $('#chkfotos').prop('checked', false);
                    }

                    $('#txtobservaciones').val(response.verificacion.OBSERVACIONES);

                    $('#idverificacion').val(response.verificacion.VERIFICACION_ID);
                    $('#idcenso').val(response.verificacion.CEN_ID);

                    $.ajax({
                        url: '../../cargarEstadosVerificacion',
                        method: 'GET',
                        success: function (data) {
                            var select = $('#cmbestadosverificacion');
                            select.empty();

                            $.each(data, function (index, item) {
                                select.append('<option value="' + item.id + '">' + item.nombre + '</option>');
                            });
                            select.val(estadoverificacionId);
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.error("Error en la solicitud de cargar los estados de verificacion: ", textStatus, errorThrown);
                            alert("Ocurrió un error al procesar la solicitud de cargar los estados de verificacion.");
                        }
                    });

                } else if (response.status === "fail" || response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de cargar datos de la verificacion: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de cargar datos de la verificacion.");
            }
        });

    } else {
        console.log("Parámetros no encontrados en la URL");
    }

    $('#btnguardar').click(function () {
        var numero = $('#txtnumerocenso').val();
        var fechacenso = $('#txtfechacenso').val();
        var documentos = $('#chkdocumentos').val();
        var fotos = $('#chkfotos').val();

        if (numero.length === 0 && fechacenso.length === 0 && documentos.length === 0 && fotos.length === 0) {
            alert('Debe ingresar los datos obligatorios (*)');
            return false;
        } else {
            $('#frmmodificarverificacion').trigger("submit");
        }
    });

    $("#frmmodificarverificacion").submit(function (event) {
        event.preventDefault();

        var parametros = $(this).serialize();

        $.ajax({
            data: parametros,
            url: "../../modificarVerificacion",
            type: "POST",
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    alert(response.message);
                    window.location.href = "verVerificacion.jsp?opcion=1&id=" + response.id;
                } else if (response.status === "fail" || response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de modificar verificacion: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de modificar verificacion.");
            }
        });
    });

    $('#btnvolver').click(function () {
        window.location.href = "listarVerificaciones.jsp";
    });

});
