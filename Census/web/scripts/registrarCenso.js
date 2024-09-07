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
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar puntos de atencion: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar puntos de atencion.");
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
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar tipos de referencia: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar tipos de referencia.");
        }
    });
    
    $('#txtnumerocenso').blur(function () {
        if ($('#txtnumerocenso').val().length > 0) {
            verificarNumeroCenso($('#txtnumerocenso').val());
        }
    });
    
    $('#txtreferencia').blur(function () {
        var tipoReferencia = $('#cmbtiposreferencia').val();
        var referencia = $('#txtreferencia').val().toString().toUpperCase();
        if (referencia.length > 0) {
            consultarReferenciaVehiculo(tipoReferencia, referencia);
        }
    });
    
    $('#btnguardar').click(function () {
        var numero = $('#txtnumerocenso').val();
        var fechacenso = $('#txtfechacenso').val();
        var referencia = $('#txtreferencia').val();

        if (numero.length > 0 && fechacenso.length > 0 && referencia.length > 0) {
            $('#frmregistrarcenso').trigger("submit");
        } else {
            alert('Debe ingresar los datos obligatorios (*)');
        }
    });
    
    $("#frmregistrarcenso").submit(function (event) {
        event.preventDefault();

        var parametros = $(this).serialize();

        $.ajax({
            data: parametros,
            url: "../../registrarCenso",
            type: "POST",
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    alert(response.message);
                    window.location.href = "verCenso.jsp?opcion=1&id=" + response.id;
                } else if (response.status === "fail" || response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de registrar censo: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de registrar censo.");
            }
        });
    });
    
    $('#btnvolver').click(function () {
        window.location.href = "listarCensos.jsp";
    });
    
});