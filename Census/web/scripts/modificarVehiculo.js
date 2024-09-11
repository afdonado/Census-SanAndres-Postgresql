
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

                    $('#txtplaca').val(response.vehiculo.VEH_PLACA);
                    $('#txtmotor').val(response.vehiculo.VEH_MOTOR);
                    $('#txtchasis').val(response.vehiculo.VEH_CHASIS);
                    $('#txtserie').val(response.vehiculo.VEH_SERIE);

                    var claseVehiculoId = response.vehiculo.CLASE_ID;
                    var servicioId = response.vehiculo.SERVICIO_ID;
                    var tipousoId = response.vehiculo.TIPO_USO_ID;

                    $('#txtcolores').val(response.vehiculo.COLOR);
                    $('#txtmarcas').val(response.vehiculo.MARCA);
                    $('#txtlineas').val(response.vehiculo.LINEA);
                    $('#txtmodelo').val(response.vehiculo.MODELO);
                    $('#cmbtransformado').val(response.vehiculo.TRANSFORMADO);
                    $('#cmbrunt').val(response.vehiculo.RUNT);                    
                    $('#txtlicenciatransito').val(response.vehiculo.LICENCIA_TRANSITO);
                    $('#txtfechamatricula').val(response.vehiculo.FECHA_MATRICULA);
                    var paisMatriculaId = response.vehiculo.PAIS_MATRICULA_ID;
                    var departamentoMatriculaId = response.vehiculo.DPTO_MATRICULA_ID;
                    var municipioMatriculaId = response.vehiculo.MUNI_MATRICULA_ID;
                    var runt = response.vehiculo.RUNT;
                    if (runt === "N") {
                        $('.matricula').hide();
                    } else {
                        $('.matricula').show();
                    }

                    //var tipoImportacionId = response.vehiculo.TDOC_IMP_ID;
                    //$('#txtdocumentoimportacion').val(response.vehiculo.DOCUMENTO_IMP);
                    //$('#txtfechaimportacion').val(response.vehiculo.FECHA_IMP);
                    //var paisImportacionId = response.vehiculo.PAIS_IMP_ID;

                    //if (tipoImportacionId === 0) {
                    //    $('.importacion').hide();
                    //} else {
                    //    $('.importacion').show();
                    //}

                    $('#cmbsoat').val(response.vehiculo.SOAT);
                    $('#txtfechavsoat').val(response.vehiculo.FECHAV_SOAT);
                    var soat = response.vehiculo.SOAT;
                    if(soat === 'N'){
                        $('#soatcontenedor').hide();
                    } else {
                        $('#soatcontenedor').show();
                    }
                    
                    $('#cmbtecnomecanica').val(response.vehiculo.TECNO_MECANICA);
                    $('#txtfechavtecnomecanica').val(response.vehiculo.FECHAV_TECNO);
                    var tecnomecanica = response.vehiculo.TECNO_MECANICA;
                    if(tecnomecanica === 'N'){
                        $('#tecnomecanicacontenedor').hide();
                    } else {
                        $('#tecnomecanicacontenedor').show();
                    }

                    $('#idvehiculo').val(response.vehiculo.VEH_ID);

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
                <div id="contenedor${persona.PV_ID}" class="form-group row">
                    <div class="col-sm-2 mb-3 mb-sm-0">
                        <select class="form-control" id="cmbtipospersona${persona.PV_ID}" name="cmbtipospersona${persona.PV_ID}"></select>
                    </div>
                    <div class="col-sm-2 mb-3 mb-sm-0">
                        <select class="form-control" id="cmbtiposdocumento${persona.PV_ID}" name="cmbtiposdocumento${persona.PV_ID}"></select>
                    </div>
                    <div class="col-sm-3 mb-3 mb-sm-0">
                        <input class="form-control solo-numeros" type="number" id="txtdocumento${persona.PV_ID}" name="txtdocumento${persona.PV_ID}" maxlength="20" value="${persona.DOCUMENTO}" style="text-transform: uppercase">
                    </div>
                    <div class="col-sm-4 mb-2 mb-sm-0">
                        <input class="form-control" type="text" id="txtnombre${persona.PV_ID}" name="txtnombre${persona.PV_ID}" value="${persona.NOMBRE}">
                    </div>
                    <div class="col-sm-1 mb-1 mb-sm-0">
                        <button type="button" class="btn btn-danger btnanular" id="btnanular${persona.PV_ID}" name="btnanular${persona.PV_ID}" data-id="${persona.PV_ID}">X</button>
                    </div>
                    <input type="hidden" id="idpersona${persona.PV_ID}" name="idpersona${persona.PV_ID}" value="${persona.PER_ID}">
                    <input type="hidden" id="estadoperveh${persona.PV_ID}" name="estadoperveh${persona.PV_ID}" value="1">
                </div>
            `;
                        contenedor.append(nuevoElemento);

                        cargarTiposPersona(`#cmbtipospersona${persona.PV_ID}`, persona.TIPO_PERSONA_ID);
                        cargarTiposDocumento(`#cmbtiposdocumento${persona.PV_ID}`, persona.TIPO_DOC_ID);
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
