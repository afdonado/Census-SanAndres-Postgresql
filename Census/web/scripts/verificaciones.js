
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
            url: '../../cargarDatosVerificacion',
            method: 'get',
            data: {id: id},
            success: function (response) {
                if (response.status === "success") {

                    $('#idverificacion').val(response.verificacion.VERIFICACION_ID || '');
                    $('#idcenso').val(response.verificacion.CEN_ID);
                    $('#txtnumerocenso').val(response.verificacion.NUMERO);
                    $('#txtfechacenso').val(response.verificacion.FECHA);
                    if (response.verificacion.VERIFICACION_DOC === 'S') {
                        $('#chkdocumentos').prop('checked', true);
                    }
                    if (response.verificacion.VERIFICACION_FOTOS === 'S') {
                        $('#chkfotos').prop('checked', true);
                    }
                    var estadoverificacionId = response.verificacion.ESTADO_VERIFICACION_ID || 1;
                    $('#txtobservaciones').val(response.verificacion.OBSERVACIONES);

                    $.ajax({
                        url: '../../cargarEstadosVerificacion',
                        method: 'GET',
                        success: function (data) {
                            var select = $('#cmbestadosverificacion');
                            select.empty();
                            $.each(data, function (index, item) {
                                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
                            });
                            select.val(estadoverificacionId);
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.error("Error en la solicitud de cargar los estados de verificacion: ", textStatus, errorThrown);
                            alert("Ocurrió un error al procesar la solicitud de cargar los estados de verificacion.");
                        }
                    });

                } else if (response.status === "fail" || response.status === "error") {
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

    $('#btnguardar').click(function () {
        var numero = $('#txtnumerocenso').val();
        var fechacenso = $('#txtfechacenso').val();
        var idcenso = $('#idcenso').val();
        var idverificacion = $('#idverificacion').val();

        if (numero.length > 0 && fechacenso.length > 0 && idcenso > 0) {
            if (idverificacion === '') {
                $('#frmregistrarverificacion').trigger("submit");
            } else {
                $('#frmmodificarverificacion').trigger("submit");
            }
        } else {
            alert('Debe ingresar los datos obligatorios (*)');
        }
    });

    $("#frmregistrarverificacion").submit(function (event) {
        event.preventDefault();

        var idcenso = $('#idcenso').val();

        var documento = 'N';
        if ($('#chkdocumentos').prop('checked') === true) {
            documento = 'S';
        }

        var foto = 'N';
        if ($('#chkfotos').prop('checked') === true) {
            foto = 'S';
        }

        var estadoverificacion = $('#cmbestadosverificacion').val();
        var observacion = $('#txtobservaciones').val();

        var parametros = {
            idcenso: idcenso,
            documento: documento,
            foto: foto,
            estadoverificacion: estadoverificacion,
            observacion: observacion
        };

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

    $("#frmmodificarverificacion").submit(function (event) {
        event.preventDefault();

        var idverificacion = $('#idverificacion').val();
        var idcenso = $('#idcenso').val();

        var documento = 'N';
        if ($('#chkdocumentos').prop('checked') === true) {
            documento = 'S';
        }

        var foto = 'N';
        if ($('#chkfotos').prop('checked') === true) {
            foto = 'S';
        }

        var estadoverificacion = $('#cmbestadosverificacion').val();
        var observacion = $('#txtobservaciones').val();

        var parametros = {
            idcenso: idcenso,
            idverificacion: idverificacion,
            documento: documento,
            foto: foto,
            estadoverificacion: estadoverificacion,
            observacion: observacion
        };

        $.ajax({
            data: parametros,
            url: "../../modificarVerificacion",
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
                console.error("Error en la solicitud de modificar verificacion: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de modificar verificacion.");
            }
        });
    });

    $('#btnvolver').click(function () {
        window.location.href = "listarVerificaciones.jsp";
    });

});


function generarReporteVerificacion(opcion) {

    var fechaini;
    var fechafin;
    var punto;

    switch (opcion) {
        case 1:
            fechaini = document.getElementById("txtfechainicenso").value.toString();
            fechafin = document.getElementById("txtfechafincenso").value.toString();
            punto = document.getElementById('cmbpuntoatencenso').value;
            window.frames[0].location.href = "generarReporteVerificacion.jsp?opcion=" + opcion + "&fechaini=" + fechaini + "&fechafin=" + fechafin + "&punto=" + punto;
            break;

        case 2:
            fechaini = document.getElementById("txtfechainiregistro").value.toString();
            fechafin = document.getElementById("txtfechafinregistro").value.toString();
            punto = document.getElementById('cmbpuntoatenregistro').value;
            window.frames[0].location.href = "generarReporteVerificacion.jsp?opcion=" + opcion + "&fechaini=" + fechaini + "&fechafin=" + fechafin + "&punto=" + punto;
            break;
        case 3:
            var numero = document.getElementById("txtnumero").value.toString().toUpperCase();
            window.frames[0].location.href = "generarReporteVerificacion.jsp?opcion=" + opcion + "&numero=" + numero;
            break;
    }
}

function consultarRegistroById(idcenso) {
    document.location.href = "../Verificaciones/verRegistro.jsp?idcenso=" + idcenso;
}