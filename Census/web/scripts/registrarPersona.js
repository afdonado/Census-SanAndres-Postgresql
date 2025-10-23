
$(function () {
    $('#txtdocumento').blur(function () {
        if ($('#txtdocumento').val().length > 0) {
            consultarDocumentoPersona2017($('#cmbtiposdocumento').val(), $('#txtdocumento').val());
        }
    });

    // Verificar si la placa del vehiculo existe en el runt
    function consultarDocumentoRunt(tipodocumento, documento) {
        $('#procesando').show();
        var parametros = {
            tipodocumento: tipodocumento,
            documento: documento
        };
        $.ajax({
            data: parametros,
            url: "../../verificarPersonaRunt",
            type: "POST",
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    var persona = response.personarunt;
                    if (persona.nombre1 && persona.nombre1.length > 0) {

                        $('#txtprimernombre').val(persona.nombre1);
                        $('#txtsegundonombre').val(persona.nombre2);
                        $('#txtprimerapellido').val(persona.apellido1);
                        $('#txtsegundoapellido').val(persona.apellido2);
                        
                        if(persona.numeroLicencia && persona.numeroLicencia.length > 0){
                            $('#cmblicenciaconduccion').val(persona.numeroLicencia.length > 0 ? 'S' : 'N');
                            $('#txtnumerolicencia').val(persona.numeroLicencia);
                            $('#cmbcategoriaslicencia').val(persona.categoriaId);
                            $('#txtfechaexplicencia').val(persona.fechaExpedicion);
                        }                        

                        if ($('#cmblicenciaconduccion').val() === 'S') {
                            $('.licencia-conduccion').show();
                        } else {
                            $('.licencia-conduccion').hide();
                        }
                    } else {
                        $('#txtprimernombre').val('');
                        $('#txtsegundonombre').val('');
                        $('#txtprimerapellido').val('');
                        $('#txtsegundoapellido').val('');
                        $('#cmblicenciaconduccion').val('N');
                        $('#txtnumerolicencia').val('');
                        $('#cmbcategoriaslicencia').val(2);
                        
                        consultarDocumentoPersona2017(tipodocumento, documento);
                    }

                } else if (response.status === "fail" || response.status === "error") {
                    consultarDocumentoPersona2017(tipodocumento, documento);
                    console.log(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de verificar persona runt: ", textStatus, errorThrown);
                //alert("Ocurrió un error al procesar la solicitud de verificar vehiculo.");
                consultarDocumentoPersona2017(tipodocumento, documento);
            },
            complete: function () {
                $('#procesando').hide();
            }
        });
    }

    function consultarDocumentoPersona2017(tipodocumento, documento) {
        if (documento.length > 0) {
            $.ajax({
                type: "POST",
                url: "../../verificarDocumentoPersona2017",
                data: {
                    tipodocumento: tipodocumento,
                    documento: documento
                },
                dataType: "json",
                success: function (response) {

                    if (response.status === "success2017") {

                        console.log(response);

                        $('#txtprimernombre').val(response.persona.nombre1);
                        $('#txtsegundonombre').val(response.persona.nombre2);
                        $('#txtprimerapellido').val(response.persona.apellido1);
                        $('#txtsegundoapellido').val(response.persona.apellido2);

                        $('#txtfechanacimiento').val(response.fechaNacimiento);
                        $('#cmbgeneros').val(response.persona.generoId);
                        $('#cmbgrupossanguineos').val(response.persona.grupoSanguineoId);

                        $('#cmbdepartamentos').val(response.persona.departamentoId);
                        $('#cmbmunicipios').val(response.persona.municipioId);
                        $('#txtdireccion').val(response.persona.direccion);
                        $('#txttelefono').val(response.persona.telefono);
                        $('#txtemail').val(response.persona.email);

                        $('#cmblicenciaconduccion').val(response.persona.numeroLicenciaConduccion.length > 0 ? 'S' : 'N');
                        $('#txtnumerolicencia').val(response.persona.numeroLicenciaConduccion);
                        $('#cmbcategoriaslicencia').val(response.persona.categoriaLicenciaId);
                        $('#txtfechaexplicencia').val(response.fechaExpedicion);

                        if ($('#cmblicenciaconduccion').val() === 'S') {
                            $('.licencia-conduccion').show();
                        } else {
                            $('.licencia-conduccion').hide();
                        }

                    } else if (response.status === "fail") {
                        $('#txtdocumento').val('');
                        $('#txtprimernombre').val('');
                        $('#txtsegundonombre').val('');
                        $('#txtprimerapellido').val('');
                        $('#txtsegundoapellido').val('');
                        $('#cmblicenciaconduccion').val('N');
                        $('#txtnumerolicencia').val('');
                        $('#cmbcategoriaslicencia').val(2);
                    } else if (response.status === "error") {
                        $('#txtdocumento').val('');
                        alert(response.message);
                    }
                },
                error: function (xhr, status, error) {
                    console.error("Error en la solicitud consultar Documento Persona: " + error);
                }
            });
        }
    }

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

    $.ajax({
        url: '../../cargarGeneros',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbgeneros');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar generos: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar generos.");
        }
    });

    $.ajax({
        url: '../../cargarGruposSanguineos',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbgrupossanguineos');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
            });
            select.val(5);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar grupos sanguineos: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar grupos sanguineos.");
        }
    });

    $.ajax({
        url: '../../cargarDepartamentos',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbdepartamentos');
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
            var select = $('#cmbmunicipios');
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
        url: '../../cargarCategoriasLicencia',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbcategoriaslicencia');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
            });
            select.val(2);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar categorias licencia: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar categorias licencia.");
        }
    });



    $('#btnguardar').click(function () {
        var documento = $('#txtdocumento').val();
        var primernombre = $('#txtprimernombre').val();
        var primerapellido = $('#txtprimerapellido').val();

        var fechanacimiento = $('#txtfechanacimiento').val();
        var direccion = $('#txtdireccion').val();
        var telefono = $('#txttelefono').val();

        if (documento.length === 0 && primernombre.length === 0 && primerapellido.length === 0) {
            alert('Debe ingresar los datos obligatorios (*)');
            return false;
        }

        if (fechanacimiento.length === 0 && direccion.length === 0 && telefono.length === 0) {
            alert('Debe ingresar los datos obligatorios (*)');
            return false;
        }

        $('#frmregistrarpersona').trigger("submit");
    });

    $("#frmregistrarpersona").submit(function (event) {
        event.preventDefault();

        var parametros = $(this).serialize();

        $.ajax({
            data: parametros,
            url: "../../registrarPersona",
            type: "POST",
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    alert(response.message);
                    $('#registrarpersona').hide();
                    window.location.href = "verPersona.jsp?opcion=1&id=" + response.id;
                } else if (response.status === "fail") {
                    alert(response.message);
                } else if (response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de registrar la persona: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de registrar la persona.");
            }
        });
    });

    $('#btnvolver').click(function () {
        window.location.href = "listarPersonas.jsp";
    });

});