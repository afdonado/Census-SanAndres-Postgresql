$(function () {

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
