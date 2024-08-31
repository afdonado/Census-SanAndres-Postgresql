
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
                var tipoDocumentoId = data.TIPO_DOCUMENTO_ID;
                $('#txtdocumento').val(data.NUMERO_DOCUMENTO);
                $('#txtnombre').val(data.NOMBRE_USUARIO);
                
                var perfilId = data.PEF_ID;
                $('#txtfechainicial').val(data.FECHA_INICIO);
                $('#txtfechafinal').val(data.FECHA_FINAL);
                
                var estadoId = data.ESTADO_ID;

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
                        select.val(perfilId);
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
        var numero = $('#txtnumerocenso').val();
        var fechainicio = $('#txtfechainicial').val();
        var fechafinal = $('#txtfechafinal').val();

        if (numero.length > 0 && fechainicio.length > 0 && fechafinal.length > 0) {
            $('#frmmodificarusuario').submit();
        } else {
            alert('Debe ingresar como minimo los datos obligatorios (*)');
        }
    });

});
