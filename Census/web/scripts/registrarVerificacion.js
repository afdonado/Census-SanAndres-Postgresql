$(function (){
    
    $.ajax({
        url: '../../cargarEstadosVerificacion',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbestadosverificacion');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.nombre + '</option>');
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar los estados de verificacion: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar los estados de verificacion.");
        }
    });
    
    $('#txtnumerocenso').blur(function () {
        if ($('#txtnumerocenso').val().length > 0) {
            verificarNumeroCenso($('#txtnumerocenso').val().toString().toUpperCase());
        }
    });
    
    $('#btnguardar').click(function () {
        var numero = $('#txtnumerocenso').val();
        var fechacenso = $('#txtfechacenso').val();
        var runt = $('#chkrunt').val();
        var documentos = $('#chkdocumentos').val();
        var fotos = $('#chkfotos').val();

        if (numero.length > 0 && fechacenso.length > 0 && runt.length > 0 && documentos.length > 0 && fotos.length > 0) {
            $('#frmregistrarverificacion').trigger("submit");
        } else {
            alert('Debe ingresar los datos obligatorios (*)');
        }
    });
    
    $("#frmregistrarverificacion").submit(function (event) {
        event.preventDefault();

        var parametros = $(this).serialize();

        $.ajax({
            data: parametros,
            url: "../../registrarVerificacion",
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
                console.error("Error en la solicitud de registrar verificacion: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de registrar verificacion.");
            }
        });
    });
    
});
