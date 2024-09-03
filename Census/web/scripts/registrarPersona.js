
$(function () {
    $('#txtdocumento').blur(function () {
        if ($('#txtdocumento').val().length > 0) {
            consultarDocumentoPersona(0, 'cmbtiposdocumento', 'txtdocumento', 'txtnombre', 'idpersona');
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
        var documento = $('#txtdocumento').val();
        var primernombre = $('#txtprimernombre').val();
        var primerapellido = $('#txtprimerapellido').val();

        var fechanacimiento = $('#txtfechanacimientos').val();
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