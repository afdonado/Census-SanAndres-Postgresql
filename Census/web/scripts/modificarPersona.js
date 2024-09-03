
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
            url: '../../cargarDatosPersona',
            method: 'get',
            data: {id: id},
            success: function (response) {

                if (response.status === "success") {

                    var tipodocumentoId = response.persona.PER_TIPODOC;

                    $('#txtdocumento').val(response.persona.DOCUMENTO);

                    $('#txtprimernombre').val(response.persona.NOMBRE1);
                    $('#txtsegundonombre').val(response.persona.NOMBRE2);
                    $('#txtprimerapellido').val(response.persona.APELLIDO1);
                    $('#txtsegundoapellido').val(response.persona.APELLIDO2);

                    $('#txtfechanacimiento').val(response.persona.FECHA_NAC);
                    var generoId = response.persona.PER_GENERO;
                    var gruposanguineoId = response.persona.ID_GRUPOSAN;

                    var departamentoId = response.persona.DEPT_ID;
                    var municipioId = response.persona.MUN_ID;
                    $('#txtdireccion').val(response.persona.DIRECCION);
                    $('#txttelefono').val(response.persona.TELEFONO);
                    $('#txtemail').val(response.persona.MAIL);

                    $('#txtnumerolicencia').val(response.persona.LIC_CONDUCCION);
                    var categoriaId = response.persona.PER_CATLIC;
                    $('#txtfechaexplicencia').val(response.persona.FECHA_EXP);
                    $('#txtfechavlicencia').val(response.persona.FECHA_VEN);

                    $('#idpersona').val(response.persona.PER_ID);
                    $('#tipodocumento').val(response.persona.PER_TIPODOC);
                    $('#documento').val(response.persona.DOCUMENTO);


                    $.ajax({
                        url: '../../cargarTiposDocumento',
                        method: 'GET',
                        success: function (data) {
                            var select = $('#cmbtiposdocumento');
                            select.empty();

                            $.each(data, function (index, item) {
                                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
                            });
                            select.val(tipodocumentoId);

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
                            select.val(generoId);
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
                            select.val(gruposanguineoId);
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
                            select.val(departamentoId);
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.error("Error en la solicitud de cargar departamentos: ", textStatus, errorThrown);
                            alert("Ocurrió un error al procesar la solicitud de cargar departamentos.");
                        }
                    });

                    $.ajax({
                        data: {
                            iddepartamento: departamentoId
                        },
                        url: '../../cargarMunicipios',
                        method: 'GET',
                        success: function (data) {
                            var select = $('#cmbmunicipios');
                            select.empty();

                            $.each(data, function (index, item) {
                                select.append('<option value="' + item.id + '">' + item.nombre + '</option>');
                            });
                            select.val(municipioId);
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
                            select.val(categoriaId);
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.error("Error en la solicitud de cargar categorias licencia: ", textStatus, errorThrown);
                            alert("Ocurrió un error al procesar la solicitud de cargar categorias licencia.");
                        }
                    });

                } else if (response.status === "fail") {
                    alert(response.message);
                } else if (response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de cargar datos de la persona: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de cargar datos de la persona.");
            }
        });

    } else {
        console.log("Parámetros no encontrados en la URL");
    }


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

        $('#frmmodificarpersona').trigger("submit");
    });

    $("#frmmodificarpersona").submit(function (event) {
        event.preventDefault();

        var parametros = $(this).serialize();

        $.ajax({
            data: parametros,
            url: "../../modificarPersona",
            type: "POST",
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    alert(response.message);
                    window.location.href = "verPersona.jsp?opcion=1&id=" + response.id;
                } else if (response.status === "fail") {
                    alert(response.message);
                } else if (response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de modificar persona: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de modificar persona.");
            }
        });
    });

    $('#txtdocumento').blur(function () {
        if ($('#txtdocumento').val().length > 0) {
            consultarDocumentoPersona(0, 'cmbtiposdocumento', 'txtdocumento', 'txtnombre', 'idpersona');
        }
    });

    $('#btnvolver').click(function () {
        window.location.href = "listarPersonas.jsp";
    });

});
