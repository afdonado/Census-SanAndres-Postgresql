
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

                    $('#txtnumerocenso').val(response.censo.numero);
                    $('#txtfechacenso').val(response.censo.fecha);
                    $('#txtpuntoatencion').val(response.censo.punto_atencion);
                    $('#txtusuarioregistro').val(response.censo.usuario);

                    $('#txtplaca').val(response.censo.veh_placa);
                    $('#txtmotor').val(response.censo.veh_motor);
                    $('#txtchasis').val(response.censo.veh_chasis);
                    $('#txtserie').val(response.censo.veh_serie);

                    $('#txtobservaciones').val(response.censo.observaciones);

                    $(".btneditar").attr("data-id", response.censo.cen_id);

                    var personas = response.personasVehiculo;
                    var contenedor = $('#personas-censo');
                    contenedor.empty();

                    $.each(personas, function (index, persona) {
                        var nuevoElemento = `
                <div id="contenedor" class="form-group row">
                    <div class="col-sm-2 mb-3 mb-sm-0">
                        <input class="form-control" id="txttipopersona" name="txttipopersona" value="${persona.tipo_persona}" readonly="true">
                    </div>
                    <div class="col-sm-2 mb-3 mb-sm-0">
                        <input class="form-control" id="txttipodocumento" name="txttipodocumento" value="${persona.tipo_doc}" readonly="true">
                    </div>
                    <div class="col-sm-3 mb-3 mb-sm-0">
                        <input class="form-control" type="text" id="txtdocumento" name="txtdocumento" value="${persona.documento}" readonly="true">
                    </div>
                    <div class="col-sm-5 mb-3 mb-sm-0">
                        <input class="form-control" type="text" id="txtnombre" name="txtnombre" value="${persona.nombre}" readonly="true">
                    </div>
                </div>
            `;
                        contenedor.append(nuevoElemento);

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

        $.ajax({
            url: '../../listarDocumentos',
            type: 'GET',
            data: {idcenso: id},
            dataType: 'json',
            success: function (response) {
                if (response.status === 'success') {

                    var titulo = `<h1 class=\"h3 mb-2 text-gray-800\">Documentos de Censo "${response.numerocenso}"</h1>`;
                    var containerTitulo = $('#titulo');
                    containerTitulo.empty();
                    containerTitulo.append(titulo);
                    var imagenes = response.imagenes;
                    var containerImagenes = $('#imagenes');
                    containerImagenes.empty();
                    imagenes.forEach(function (imagen) {
                        var src;
                        if (imagen.extension === 'pdf') {
                            src = "../../iconos/pdf.png";
                        } else {
                            src = "data:image/'" + imagen.extension + "';base64," + imagen.b64;
                        }

                        var img = '<div class="thumbnail text-center">' +
                                '<label>' + imagen.nombre + '</label>' + 
                                '<a href="../Documentos/visualizarDocumentos.jsp?iddocumento=' + imagen.iddocumento + '" target="_blank">' +
                                '<img src="' + src + '" title="' + imagen.nombre + '" alt="' + imagen.nombre + '">' +
                                '</a>' +
                                '<button class="btn btn-danger eliminar-doc" data-id="' + imagen.iddocumento + '">Eliminar</button>' +
                                '</div>';
                        containerImagenes.append(img);
                    });

                    $('.eliminar-doc').click(function () {
                        var iddocumento = $(this).data('id');

                        if (confirm("¿Estás seguro de que deseas eliminar este documento?")) {
                            $.ajax({
                                url: '../../eliminarDocumento',
                                type: 'POST',
                                data: {iddocumento: iddocumento},
                                dataType: 'json',
                                success: function (response) {
                                    if (response.status === 'success') {
                                        alert("Documento eliminado correctamente");
                                        location.reload();
                                    } else {
                                        alert("Error al eliminar el documento: " + response.message);
                                    }
                                },
                                error: function () {
                                    alert("Ocurrió un error al intentar eliminar el documento.");
                                }
                            });
                        }
                    });
                }
            },
            error: function () {
                console.error("Error en la solicitud de consultar documentos");
                alert("Ocurrió un error al procesar la solicitud de consultar documentos.");
            }
        });

    } else {
        console.log("Parámetros no encontrados en la URL");
    }

    $('#btnvolver').click(function () {
        window.location.href = "listarCensos.jsp";
    });

    $('#btneditar').click(function () {
        var id = $(this).data('id');
        window.location.href = "modificarCenso.jsp?opcion=2&id=" + id;
    });

});
