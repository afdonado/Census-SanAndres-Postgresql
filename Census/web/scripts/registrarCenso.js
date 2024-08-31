$(function (){
    
    $.ajax({
        url: '../../cargarPuntosAtencion',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbpuntosatencion');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.nombre + '</option>');
            });
        }
    });
    
    $.ajax({
        url: '../../cargarTiposReferencia',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbtiposreferencia');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
            });
        }
    });
    
    $.ajax({
        url: '../../cargarTiposPersona',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbtipospersona');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
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
        var numero = $('#txtnumerocenso').val();
        var fechacenso = $('#txtfechacenso').val();
        var referencia = $('#txtreferencia').val();
        var documento = $('#txtdocumento').val();

        if (numero.length > 0 && fechacenso.length > 0 && referencia.length > 0 && documento.length > 0) {
            $('#frmregistrarcenso').submit();
        } else {
            alert('Debe ingresar como minimo los datos obligatorios (*)');
        }
    });
    
});
