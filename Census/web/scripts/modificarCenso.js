
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
            success: function (response) {

                if (response.status === "success") {

                    $('#txtnumerocenso').val(response.censo.NUMERO);
                    $('#txtfechacenso').val(response.censo.FECHA);
                    var puntoatencionId = response.censo.PUN_ID;


                    var tipoReferenciaId = 0;
                    var referencia = '';

                    if (response.censo.VEH_PLACA.length > 0) {
                        tipoReferenciaId = 1;
                        referencia = response.censo.VEH_PLACA;
                    } else {
                        if (response.censo.VEH_MOTOR.length > 0) {
                            tipoReferenciaId = 2;
                            referencia = response.censo.VEH_MOTOR;
                        } else {
                            if (response.censo.VEH_CHASIS.length > 0) {
                                tipoReferenciaId = 3;
                                referencia = response.censo.VEH_CHASIS;
                            } else {
                                if (response.censo.VEH_SERIE.length > 0) {
                                    tipoReferenciaId = 4;
                                    referencia = response.censo.VEH_SERIE;
                                }
                            }
                        }
                    }
                    $('#txtreferencia').val(referencia);

                    $('#txtobservaciones').val(response.censo.OBSERVACIONES);

                    $('#idcenso').val(response.censo.CEN_ID);
                    $('#idvehiculo').val(response.censo.VEH_ID);
                    $('#numerocenso').val(response.censo.NUMERO);
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
                            select.val(tipoReferenciaId);
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.error("Error en la solicitud de cargar tipos de referencia: ", textStatus, errorThrown);
                            alert("Ocurrió un error al procesar la solicitud de cargar tipos de referencia.");
                        }
                    });

                } else if (response.status === "fail") {
                    alert(response.message);
                } else if (response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de cargar datos censo: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de cargar datos censo.");
            }
        });

    } else {
        console.log("Parámetros no encontrados en la URL");
    }

    $('#txtnumerocenso').blur(function () {
        var numero = $('#txtnumerocenso').val();
        if (numero.length > 0 && numero.length < 6) {
            var prefijo = "ACS";
            numero = prefijo + ("00000".substring(0, 5 - numero.length)) + numero;
            console.log('numero:', numero);
            if (numero !== $('#numerocenso').val()) {
                verificarNumeroCenso($('#txtnumerocenso').val());
            } else {
                $('#txtnumerocenso').css("background-color", "lightgreen");
            }
        }
    });

    $('#txtreferencia').blur(function () {
        var tipoReferencia = $('#cmbtiposreferencia').val();
        var referencia = $('#txtreferencia').val().toString().toUpperCase();
        if (referencia.length > 0 &&
                (!referencia.eq($('#referencia').val().toString().toUpperCase()))
                || tipoReferencia !== $('#tiporeferencia').val()) {
            consultarReferenciaVehiculo(tipoReferencia, referencia);
        }
    });

    $('#btnguardar').click(function () {
        var numero = $('#txtnumerocenso').val();
        var fechacenso = $('#txtfechacenso').val();
        var referencia = $('#txtreferencia').val();
        var documento = $('#txtdocumento').val();

        if (numero.length === 0 && fechacenso.length === 0 && referencia.length === 0 && documento.length === 0) {
            alert('Debe ingresar los datos obligatorios (*)');
            return false;
        } else {
            $('#frmmodificarcenso').trigger("submit");
        }
    });

    $("#frmmodificarcenso").submit(function (event) {
        event.preventDefault();

        var parametros = $(this).serialize();

        $.ajax({
            data: parametros,
            url: "../../modificarCenso",
            type: "POST",
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    alert(response.message);
                    window.location.href = "verCenso.jsp?opcion=1&id=" + response.id;
                } else if (response.status === "fail") {
                    alert(response.message);
                } else if (response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de modificar censo: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de modificar censo.");
            }
        });
    });

    $('#btnvolver').click(function () {
        window.location.href = "listarCensos.jsp";
    });

});
