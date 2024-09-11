
$(function () {
    
    $('.datos-importancion').hide();
    $('#tipos-uso').hide();
    
    $('#txtplaca').blur(function () {
        var placa = $('#txtplaca').val().toString().toUpperCase();
        if (placa.length > 0) {
            verificarVehiculoRunt(1, placa);
        }
    });

    $('#txtmotor').blur(function () {
        var motor = $('#txtmotor').val().toString().toUpperCase();
        if (motor.length > 0) {
            verificarVehiculo2017(2, motor);
        }
    });

    $('#txtchasis').blur(function () {
        var chasis = $('#txtchasis').val().toString().toUpperCase();
        if (chasis.length > 0) {
            verificarVehiculo2017(3, chasis);
        }
    });

    $('#txtserie').blur(function () {
        var serie = $('#txtserie').val().toString().toUpperCase();
        if (serie.length > 0) {
            verificarVehiculo2017(4, serie);
        }
    });

    // Verificar si la placa del vehiculo existe en el runt
    function verificarVehiculoRunt(tiporeferencia, valorreferencia) {
        console.log('verificarVehiculoRunt');
        var parametros = {
            tiporeferencia: tiporeferencia,
            valorreferencia: valorreferencia
        };
        $.ajax({
            data: parametros,
            url: "../../verificarVehiculoRunt",
            type: "POST",
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    var vehiculo = response.vehiculorunt;
                    $('#txtmotor').val(vehiculo.motor);
                    $('#txtchasis').val(vehiculo.chasis);
                    $('#txtserie').val(vehiculo.serie);
                    $('#cmbclasevehiculo').val(response.clasevehiculoId);
                    $('#cmbtiposservicio').val(response.tiposervicioId);
                    $('#txtcolores').val(vehiculo.color);
                    $('#txtmarcas').val(vehiculo.marca);
                    $('#txtlineas').val(vehiculo.linea);
                    $('#txtmodelo').val(vehiculo.modelo);
                    $('#txtlicenciatransito').val(vehiculo.licenciaTransito);
                    $('#txtfechamatricula').val(vehiculo.fechaMatriculaInicial);
                    if (vehiculo.polizaSoat.estado === 'VIGENTE') {
                        $('#cmbsoat').val('S');
                        $('#txtfechavsoat').val(vehiculo.polizaSoat.fechaFinVigencia);
                        $('#soatcontenedor').show();
                    } else {
                        $('#cmbsoat').val('N');
                        $('#soatcontenedor').hide();
                    }
                    if (vehiculo.tecnicoMecanico.vigente === 'SI') {
                        $('#cmbtecnomecanica').val('S');
                        $('#txtfechavtecnomecanica').val(vehiculo.tecnicoMecanico.fechaVigencia);
                        $('#tecnomecanicacontenedor').show();
                    } else {
                        $('#cmbtecnomecanica').val('N');
                        $('#tecnomecanicacontenedor').hide();
                    }
                } else if (response.status === "fail" || response.status === "error") {
                    verificarVehiculo2017(tiporeferencia, valorreferencia);
                    console.log(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de verificar vehiculo: ", textStatus, errorThrown);
                //alert("Ocurrió un error al procesar la solicitud de verificar vehiculo.");
                verificarVehiculo2017(tiporeferencia, valorreferencia);
            }
        });
    }


    // Verificar si la referencia de vehiculo existe o no
    function verificarVehiculo2017(tiporeferencia, valorreferencia) {
        console.log('verificarVehiculo2017');
        var parametros = {
            tiporeferencia: tiporeferencia,
            valorreferencia: valorreferencia,
            opcion: 1
                    //opcion 1 verifica solo si el vehiculo está registrado
                    //opcion 2 verificar si está registrado y si está censado
        };
        $.ajax({
            data: parametros,
            url: "../../verificarVehiculo2017",
            type: "POST",
            dataType: "json",
            success: function (response) {
                if (response.status === "success2017") {
                    var vehiculo = response.vehiculo;
                    $('#txtplaca').val(response.vehiculo.placa);
                    $('#txtmotor').val(vehiculo.motor);
                    $('#txtchasis').val(vehiculo.chasis);
                    $('#txtserie').val(vehiculo.serie);
                    $('#cmbclasevehiculo').val(vehiculo.claseVehiculoId);
                    $('#cmbtiposservicio').val(vehiculo.tipoServicioId);
                    $('#cmbtiposuso').val(vehiculo.tipoUsoId);
                    $('#txtcolores').val(vehiculo.color);
                    $('#txtmarcas').val(vehiculo.marca);
                    $('#txtlineas').val(vehiculo.linea);
                    $('#txtmodelo').val(vehiculo.modelo);
                    $('#cmbtransformado').val(vehiculo.transformado);
                    $('#cmbrunt').val(vehiculo.runt);
                    $('#txtlicenciatransito').val(vehiculo.licenciaTransito);
                    $('#txtfechamatricula').val(response.fechaMatricula);
                    $('#cmbpaismatricula').val(vehiculo.paisMatriculaId);
                    $('#cmbdepartamentomatricula').val(vehiculo.departamentoMatriculaId);
                    $('#cmbmunicipiomatricula').val(vehiculo.municipoMatriculaId);
                    if (vehiculo.runt === 'N') {
                        $('.matricula').hide();
                    } else {
                        $('.matricula').show();
                    }

                    //$('#cmbtiposimportacion').val(vehiculo.tipoImportacionId);
                    //$('#txtdocumentoimportacion').val(vehiculo.documentoImportacion);
                    //$('#txtfechaimportacion').val(response.fechaImportacion);
                    //$('#cmbpaisimportacion').val(vehiculo.paisImportacionId);
                    //if (vehiculo.tipoImportacionId === 0) {
                    //    $('.importacion').hide();
                    //} else {
                    //    $('.importacion').show();
                    //}
                    
                    $('#cmbsoat').val(vehiculo.soat);
                    $('#txtfechavsoat').val(response.fechaVenSoat);
                    if (vehiculo.soat === 'S') {
                        $('#soatcontenedor').show();
                    } else {
                        $('#soatcontenedor').hide();
                    }
                    
                    $('#cmbtecnomecanica').val(vehiculo.tecnomecanica);
                    $('#txtfechavtecnomecanica').val(response.fechaVenTecnomecanica);
                    if (vehiculo.tecnomecanica === 'S') {                        
                        $('#tecnomecanicacontenedor').show();
                    } else {
                        $('#tecnomecanicacontenedor').hide();
                    }
                } else if (response.status === "fail") {
                    $(response.input).val('');
                    $(response.input).focus();
                    alert(response.message);
                } else if (response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de verificar vehiculo 2017: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de verificar vehiculo.");
            }
        });
    }

    $('#cmbdepartamentomatricula').change(function () {
        cargarMunicipiosByDepartamento($('#cmbdepartamentomatricula').val());
    });

    $('#cmbdepartamentomatricula').focus(function () {
        cargarMunicipiosByDepartamento($('#cmbdepartamentomatricula').val());
    });

    function cargarMunicipiosByDepartamento(iddepartamento) {
        $.ajax({
            data: {
                iddepartamento: iddepartamento
            },
            url: '../../cargarMunicipios',
            method: 'GET',
            success: function (data) {
                var select = $('#cmbmunicipiomatricula');
                select.empty();

                $.each(data, function (index, item) {
                    select.append('<option value="' + item.id + '">' + item.nombre + '</option>');
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de cargar municipios por departamento: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de cargar municipios por departamento.");
            }
        });
    }

    $.ajax({
        url: '../../cargarClasesVehiculo',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbclasevehiculo');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
            });
            select.val(10);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar clases vehiculo: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar clases de vehiculo.");
        }
    });

    $.ajax({
        url: '../../cargarTiposServicio',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbtiposservicio');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar tipos servicio: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar tipos de servicio.");
        }
    });


    $.ajax({
        url: '../../cargarTiposUso',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbtiposuso');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar tipos servicio: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar tipos de uso.");
        }
    });

    $.ajax({
        url: '../../cargarPaises',
        method: 'GET',
        success: function (data) {
            var selectMatricula = $('#cmbpaismatricula');
            selectMatricula.empty();

            $.each(data, function (index, item) {
                selectMatricula.append('<option value="' + item.id + '">' + item.nombre + '</option>');
            });
            selectMatricula.val(18);

            var selectImportacion = $('#cmbpaisimportacion');
            selectImportacion.empty();

            $.each(data, function (index, item) {
                selectImportacion.append('<option value="' + item.id + '">' + item.nombre + '</option>');
            });
            selectImportacion.val(18);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar paises: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar paises.");
        }
    });

    $.ajax({
        url: '../../cargarDepartamentos',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbdepartamentomatricula');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.nombre + '</option>');
            });

            select.val(4);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar departamentos: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar departamentos.");
        }
    });

    $.ajax({
        data: {
            iddepartamento: "4"
        },
        url: '../../cargarMunicipios',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbmunicipiomatricula');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.nombre + '</option>');
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar municipios: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar municipios.");
        }
    });

    $.ajax({
        url: '../../cargarTiposImportacion',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbtiposimportacion');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
            });

            select.val(0);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar tipos de importacion: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar tipos de importacion.");
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

    $.ajax({
        url: '../../cargarTiposPersona',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbtipospersona1');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar tipos de persona: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar tipos de persona.");
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

            var select = $('#cmbtiposdocumento1');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar tipos de documentos: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar tipos de documentos.");
        }
    });

    $('#btnguardar').click(function () {
        var motor = $('#txtmotor').val();
        var chasis = $('#txtchasis').val();
        var modelo = $('#txtmodelo').val();

        var colores = $('#txtcolores').val();
        var marcas = $('#txtmarcas').val();
        var lineas = $('#txtlineas').val();

        if (motor.length === 0 && chasis.length === 0 && modelo.length === 0) {
            alert('Debe ingresar los datos obligatorios (*)');
            return false;
        }

        if (colores.length === 0 && marcas.length === 0 && lineas.length === 0) {
            alert('Debe ingresar los datos obligatorios (*)');
            return false;
        }

        $('#frmregistrarvehiculo').trigger("submit");
    });

    $("#frmregistrarvehiculo").submit(function (event) {
        event.preventDefault();

        var parametros = $(this).serialize();

        $.ajax({
            data: parametros,
            url: "../../registrarVehiculo",
            type: "POST",
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    alert(response.message);
                    window.location.href = "verVehiculo.jsp?opcion=1&id=" + response.id;
                } else if (response.status === "fail") {
                    alert(response.message);
                } else if (response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de registrar vehiculo: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de registrar vehiculo.");
            }
        });
    });

    $('#txtdocumento1').blur(function () {
        if ($('#txtdocumento1').val().length > 0) {
            consultarDocumentoPersonaVehiculo('cmbtiposdocumento1', 'txtdocumento1', 'txtnombre1', 'idpersona1');
        }
    });

    $('#btnvolver').click(function () {
        window.location.href = "listarVehiculos.jsp";
    });

});