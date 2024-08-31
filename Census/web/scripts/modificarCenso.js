
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
            url: '../../cargarDatosCenso',
            method: 'get',
            data: {id: id},
            success: function (data) {
                $('#txtnumerocenso').val(data.censo.NUMERO);
                $('#txtfechacenso').val(data.censo.FECHA);
                var puntoatencionId = data.censo.PUN_ID;


                var tipoReferenciaId = 0;
                var referencia = '';
                
                if (data.censo.VEH_PLACA.length > 0) {
                    tipoReferenciaId = 1;
                    referencia = data.censo.VEH_PLACA;
                } else {
                    if (data.censo.VEH_MOTOR.length > 0) {
                        tipoReferenciaId = 2;
                        referencia = data.censo.VEH_MOTOR;
                    } else {
                        if (data.censo.VEH_CHASIS.length > 0) {
                            tipoReferenciaId = 3;
                            referencia = data.censo.VEH_CHASIS;
                        } else {
                            if (data.censo.VEH_SERIE.length > 0) {
                                tipoReferenciaId = 4;
                                referencia = data.censo.VEH_SERIE;
                            }
                        }
                    }
                }
                $('#txtreferencia').val(referencia);
                
                var tipoPersonaId = data.censo.TIPO_PERSONA_ID;
                var tipoDocumentoId = data.censo.TDOC_ID;
                $('#txtdocumento').val(data.censo.DOCUMENTO);
                $('#txtnombre').val(data.censo.NOMBRE);

                $('#txtobservaciones').val(data.censo.OBSERVACIONES);
                
                $('#idcenso').val(data.censo.PER_ID);
                $('#idvehiculo').val(data.censo.VEH_ID);
                $('#idcenso').val(data.censo.CEN_ID);
                $('#numerocenso').val(data.censo.NUMERO);
                $('#tiporeferencia').val(tipoReferenciaId);
                $('#referencia').val(referencia);

                $.ajax({
                    url: '../../cargarPuntosAtencion',
                    method: 'GET',
                    success: function (data) {
                        var select = $('#cmbpuntosatencion');
                        select.empty();
                        $.each(data, function (index, item) {
                            select.append('<option value="' + item.id + '">' + item.nombre + '</option>');
                        });
                        select.val(puntoatencionId);
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
                        select.val(tipoReferenciaId);
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
                        select.val(tipoPersonaId);
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
            }
        });

    } else {
        console.log("Parámetros no encontrados en la URL");
    }
    
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
