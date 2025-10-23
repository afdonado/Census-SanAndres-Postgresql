
$(function () {
    
    $('.datos-importancion').hide();
    $('#tipos-uso').hide();

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
            url: '../../cargarDatosVehiculo',
            method: 'get',
            data: {id: id},
            success: function (response) {

                if (response.status === "success") {

                    $('#txtplaca').val(response.vehiculo.veh_placa);
                    $('#txtmotor').val(response.vehiculo.veh_motor);
                    $('#txtchasis').val(response.vehiculo.veh_chasis);
                    $('#txtserie').val(response.vehiculo.veh_serie);

                    var claseVehiculoId = response.vehiculo.clase_id;
                    var servicioId = response.vehiculo.servicio_id;
                    var tipousoId = response.vehiculo.tipo_uso_id;

                    $('#txtcolores').val(response.vehiculo.color);
                    $('#txtmarcas').val(response.vehiculo.marca);
                    $('#txtlineas').val(response.vehiculo.linea);
                    $('#txtmodelo').val(response.vehiculo.modelo);
                    $('#cmbtransformado').val(response.vehiculo.transformado);
                    $('#cmbrunt').val(response.vehiculo.runt);                    
                    $('#txtlicenciatransito').val(response.vehiculo.licencia_transito);
                    $('#txtfechamatricula').val(response.vehiculo.fecha_matricula);
                    var paisMatriculaId = response.vehiculo.pais_matricula_id;
                    var departamentoMatriculaId = response.vehiculo.dpto_matricula_id;
                    var municipioMatriculaId = response.vehiculo.muni_matricula_id;
                    var runt = response.vehiculo.runt;
                    if (runt === "N") {
                        $('.matricula').hide();
                    } else {
                        $('.matricula').show();
                    }

                    //var tipoImportacionId = response.vehiculo.tdoc_imp_id;
                    //$('#txtdocumentoimportacion').val(response.vehiculo.documento_imp);
                    //$('#txtfechaimportacion').val(response.vehiculo.fecha_imp);
                    //var paisImportacionId = response.vehiculo.pais_imp_id;

                    //if (tipoImportacionId === 0) {
                    //    $('.importacion').hide();
                    //} else {
                    //    $('.importacion').show();
                    //}

                    $('#cmbsoat').val(response.vehiculo.soat);
                    $('#txtfechavsoat').val(response.vehiculo.fechav_soat);
                    var soat = response.vehiculo.soat;
                    if(soat === 'N'){
                        $('#soatcontenedor').hide();
                    } else {
                        $('#soatcontenedor').show();
                    }
                    
                    $('#cmbtecnomecanica').val(response.vehiculo.tecno_mecanica);
                    $('#txtfechavtecnomecanica').val(response.vehiculo.fechav_tecno);
                    var tecnomecanica = response.vehiculo.tecno_mecanica;
                    if(tecnomecanica === 'N'){
                        $('#tecnomecanicacontenedor').hide();
                    } else {
                        $('#tecnomecanicacontenedor').show();
                    }

                    $('#idvehiculo').val(response.vehiculo.veh_id);

                    $.ajax({
                        url: '../../cargarClasesVehiculo',
                        method: 'GET',
                        success: function (data) {
                            var select = $('#cmbclasevehiculo');
                            select.empty();
                            $.each(data, function (index, item) {
                                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
                            });
                            select.val(claseVehiculoId);
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
                            select.val(servicioId);
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
                            select.val(tipousoId);
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
                            selectMatricula.val(paisMatriculaId);


                            var selectImportacion = $('#cmbpaisimportacion');
                            selectImportacion.empty();
                            $.each(data, function (index, item) {
                                selectImportacion.append('<option value="' + item.id + '">' + item.nombre + '</option>');
                            });

                            selectImportacion.val(0);
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
                            select.val(departamentoMatriculaId);
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.error("Error en la solicitud de cargar departamentos: ", textStatus, errorThrown);
                            alert("Ocurrió un error al procesar la solicitud de cargar departamentos.");
                        }
                    });

                    $.ajax({
                        data: {
                            iddepartamento: departamentoMatriculaId
                        },
                        url: '../../cargarMunicipios',
                        method: 'GET',
                        success: function (data) {
                            var select = $('#cmbmunicipiomatricula');
                            select.empty();
                            $.each(data, function (index, item) {
                                select.append('<option value="' + item.id + '">' + item.nombre + '</option>');
                            });
                            select.val(municipioMatriculaId);
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

                    var personas = response.personasVehiculo;
                    var contenedor = $('#personas-vehiculo');
                    contenedor.empty();

                    $.each(personas, function (index, persona) {
                        var nuevoElemento = `
                <div id="contenedor${persona.pv_id}" class="form-group row">
                    <div class="col-sm-2 mb-3 mb-sm-0">
                        <select class="form-control" id="cmbtipospersona${persona.pv_id}" name="cmbtipospersona${persona.pv_id}"></select>
                    </div>
                    <div class="col-sm-2 mb-3 mb-sm-0">
                        <select class="form-control" id="cmbtiposdocumento${persona.pv_id}" name="cmbtiposdocumento${persona.pv_id}"></select>
                    </div>
                    <div class="col-sm-3 mb-3 mb-sm-0">
                        <input class="form-control solo-numeros" type="number" id="txtdocumento${persona.pv_id}" name="txtdocumento${persona.pv_id}" maxlength="20" value="${persona.documento}" style="text-transform: uppercase">
                    </div>
                    <div class="col-sm-4 mb-2 mb-sm-0">
                        <input class="form-control" type="text" id="txtnombre${persona.pv_id}" name="txtnombre${persona.pv_id}" value="${persona.nombre}">
                    </div>
                    <div class="col-sm-1 mb-1 mb-sm-0">
                        <button type="button" class="btn btn-danger btnanular" id="btnanular${persona.pv_id}" name="btnanular${persona.pv_id}" data-id="${persona.pv_id}">X</button>
                    </div>
                    <input type="hidden" id="idpersona${persona.pv_id}" name="idpersona${persona.pv_id}" value="${persona.per_id}">
                    <input type="hidden" id="estadoperveh${persona.pv_id}" name="estadoperveh${persona.pv_id}" value="1">
                </div>
            `;
                        contenedor.append(nuevoElemento);

                        cargarTiposPersona(`#cmbtipospersona${persona.pv_id}`, persona.tipo_persona_id);
                        cargarTiposDocumento(`#cmbtiposdocumento${persona.pv_id}`, persona.tipo_doc_id);
                    });

                } else if (response.status === "fail") {
                    alert(response.message);
                } else if (response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de cargar datos vehiculo: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de cargar datos vehiculo.");
            }
        });

    } else {
        console.log("Parámetros no encontrados en la URL");
    }

    function cargarTiposPersona(selector, tipoPersonaId) {
        $.ajax({
            url: '../../cargarTiposPersona',
            method: 'GET',
            success: function (data) {
                var select = $(selector);
                select.empty();
                $.each(data, function (index, item) {
                    select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
                });
                select.val(tipoPersonaId);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de cargar tipos de persona: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de cargar tipos de persona.");
            }
        });
    }

    function cargarTiposDocumento(selector, tipoDocumentoId) {
        $.ajax({
            url: '../../cargarTiposDocumento',
            method: 'GET',
            success: function (data) {
                var select = $(selector);
                select.empty();
                $.each(data, function (index, item) {
                    select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
                });
                select.val(tipoDocumentoId);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de cargar tipos de documentos: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de cargar tipos de documentos.");
            }
        });
    }

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

        $('#frmmodificarvehiculo').trigger("submit");
    });

    $("#frmmodificarvehiculo").submit(function (event) {
        event.preventDefault();

        var parametros = $(this).serialize();

        $.ajax({
            data: parametros,
            url: "../../modificarVehiculo",
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
                console.error("Error en la solicitud de modificar vehiculo: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de modificar vehiculo.");
            }
        });
    });
    
    $('#txtdocumento').blur(function () {
        if ($('#txtdocumento').val().length > 0) {
            consultarDocumentoPersonaVehiculo('cmbtiposdocumento', 'txtdocumento', 'txtnombre', 'idpersona');
        }
    });

    $('#personas-vehiculo').on('click', '.btnanular', function () {
        var id = $(this).data('id');
        anularPersonaVehiculo(id);
    });

    function anularPersonaVehiculo(idperveh) {
        $('#estadoperveh' + idperveh).val(2);
        $('#contenedor' + idperveh).hide();
    }
    
    $('#btnvolver').click(function () {
        window.location.href = "listarVehiculos.jsp";
    });
    
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

});
