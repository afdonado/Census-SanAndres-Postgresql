
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
                var tipoDocumentoId = response.usuario.tipo_documento_id;
                $('#txtdocumento').val(response.usuario.numero_documento);
                $('#txtnombre').val(response.usuario.nombre_usuario);

                var perfilId = response.usuario.pef_id;
                $('#txtfechainicial').val(response.usuario.fecha_inicio);

                var estadoId = response.usuario.estado_id;

                $('#idusuario').val(response.usuario.usu_id);

                $('.restaurar').attr('data-id', response.usuario.usu_id);

                $.ajax({
                    url: '../../cargarPerfiles',
                    method: 'GET',
                    success: function (data) {
                        var select = $('#cmbperfiles');
                        select.empty();
                        $.each(data, function (index, item) {
                            select.append('<option value="' + item.id + '">' + item.nombre + '</option>');
                        });
                        select.val(perfilId);
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
                        select.val(tipoDocumentoId);
                    }
                });

                $.ajax({
                    url: '../../cargarEstados',
                    method: 'GET',
                    success: function (data) {
                        var select = $('#cmbestados');
                        select.empty();
                        $.each(data, function (index, item) {
                            select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
                        });
                        select.val(estadoId);
                    }
                });
            }
        });

    } else {
        console.log("Parámetros no encontrados en la URL");
    }

    $('#btnguardar').click(function () {
        var nombre = $('#txtnombre').val();

        if (nombre.length === 0) {
            alert('Debe ingresar el nombre del usuario');
            return false;
        }

        $('#frmmodificarusuario').trigger("submit");
    });

    $("#frmmodificarusuario").submit(function (event) {
        event.preventDefault();

        var parametros = $(this).serialize();

        $.ajax({
            data: parametros,
            url: "../../modificarUsuario",
            type: "POST",
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    alert(response.message);
                    window.location.href = "verUsuario.jsp?opcion=1&id=" + response.id;
                } else if (response.status === "fail") {
                    alert(response.message);
                } else if (response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de modificar usuario: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de modificar usuario.");
            }
        });
    });

    $('#btnvolver').click(function () {
        window.location.href = "listarUsuarios.jsp";
    });


    $('#btnrestaurar').click(function () {
        var id = $(this).data('id');
        restaurarPassword(id);
    });

    function restaurarPassword(id) {
        $.ajax({
            url: "../../restaurarPassword",
            method: "get",
            data: {idusuario: id},
            success: function (response) {
                if (response.status === "success") {
                    alert(response.message);
                } else if (response.status === "fail") {
                    alert(response.message);
                } else if (response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de restaurar password: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de restaurar password.");
            }
        });
    }

});
