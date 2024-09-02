
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
                $('#txtfechafinal').val(response.usuario.FECHA_FINAL);
                
                var estadoId = response.usuario.ESTADO_ID;
                
                $('#idusuario').val(response.usuario.USU_ID);

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

        if (nombre.length > 0) {
            $('#frmmodificarusuario').submit();
        } else {
            alert('Debe ingresar el nombre del usuario');
        }
    });

});
