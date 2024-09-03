
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

                    $('#txttipodocumento').val(response.persona.TIPO_DOC);
                    $('#txtdocumento').val(response.persona.DOCUMENTO);

                    $('#txtprimernombre').val(response.persona.NOMBRE1);
                    $('#txtsegundonombre').val(response.persona.NOMBRE2);
                    $('#txtprimerapellido').val(response.persona.APELLIDO1);
                    $('#txtsegundoapellido').val(response.persona.APELLIDO2);

                    $('#txtfechanacimiento').val(response.persona.FECHA_NAC);
                    $('#txtgenero').val(response.persona.GENERO);
                    $('#txtgruposanguineo').val(response.persona.GRUPO_SANGUINEO);

                    $('#txtdepartamento').val(response.persona.DEPARTAMENTO);
                    $('#txtmunicipio').val(response.persona.MUNICIPIO);
                    $('#txtdireccion').val(response.persona.DIRECCION);
                    $('#txttelefono').val(response.persona.TELEFONO);
                    $('#txtemail').val(response.persona.MAIL);

                    $('#txtnumerolicencia').val(response.persona.LIC_CONDUCCION);
                    $('#txtcategoria').val(response.persona.CATEGORIA_LIC);
                    $('#txtfechaexpedicion').val(response.persona.FECHA_EXP);
                    $('#txtfechavencimiento').val(response.persona.FECHA_VEN);

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

});
