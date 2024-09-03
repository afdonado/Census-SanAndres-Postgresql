
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
                var tipoDocumentoId = response.usuario.TIPO_DOCUMENTO_ID;
                $('#txtdocumento').val(response.usuario.NUMERO_DOCUMENTO);
                $('#txtnombre').val(response.usuario.NOMBRE_USUARIO);

                var perfilId = response.usuario.PEF_ID;
                $('#txtfechainicial').val(response.usuario.FECHA_INICIO);

                var estadoId = response.usuario.ESTADO_ID;

                $('#idusuario').val(response.usuario.USU_ID);

                $('.restaurar').attr('data-id', response.usuario.USU_ID);

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
