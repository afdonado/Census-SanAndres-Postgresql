
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

console.log("response. ",response   );
                if (response.status === "success") {

                    $('#txttipodocumento').val(response.persona.tipo_doc);
                    $('#txtdocumento').val(response.persona.documento);

                    $('#txtprimernombre').val(response.persona.nombre1);
                    $('#txtsegundonombre').val(response.persona.nombre2);
                    $('#txtprimerapellido').val(response.persona.apellido1);
                    $('#txtsegundoapellido').val(response.persona.apellido2);

                    $('#txtfechanacimiento').val(response.persona.fecha_nac);
                    $('#txtgenero').val(response.persona.genero);
                    $('#txtgruposanguineo').val(response.persona.grupo_sanguineo);

                    $('#txtdepartamento').val(response.persona.departamento);
                    $('#txtmunicipio').val(response.persona.municipio);
                    $('#txtdireccion').val(response.persona.direccion);
                    $('#txttelefono').val(response.persona.telefono);
                    $('#txtemail').val(response.persona.mail);

console.log("licencia de conduccion. ",response.persona.licencia_conduccion);
                    if (response.persona.licencia_conduccion === 'S') {
                        $('#txtnumerolicencia').val(response.persona.lic_conduccion);
                        $('#txtcategorialicencia').val(response.persona.categoria_lic);
                        $('#txtfechaexplicencia').val(response.persona.fecha_exp);
                        $('#txtfechavlicencia').val(response.persona.fecha_ven);
                        $('.licencia-conduccion').show();
                    } else {
                        $('#txtnumerolicencia').val('');
                        $('#txtcategorialicencia').val('');
                        $('#txtfechaexplicencia').val('');
                        $('#txtfechavlicencia').val('');
                        $('.licencia-conduccion').hide();
                    }
                    $(".btneditar").attr("data-id", response.persona.per_id);

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

    $('#btnvolver').click(function () {
        window.location.href = "listarPersonas.jsp";
    });

    $('#btneditar').click(function () {
        var id = $(this).data('id');
        window.location.href = "modificarPersona.jsp?opcion=2&id=" + id;
    });

});
