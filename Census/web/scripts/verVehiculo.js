
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

                    $('#txtclase').val(response.vehiculo.clase);
                    $('#txtservicio').val(response.vehiculo.servicio);
                    $('#txttipouso').val(response.vehiculo.tipo_uso);

                    $('#txtcolores').val(response.vehiculo.color);
                    $('#txtmarcas').val(response.vehiculo.marca);
                    $('#txtlineas').val(response.vehiculo.linea);
                    $('#txtmodelo').val(response.vehiculo.modelo);
                    $('#txttransformado').val(response.vehiculo.transformado === 'S' ? 'Si' : 'No');
                    $('#txtrunt').val(response.vehiculo.runt === 'S' ? 'Si' : 'No');

                    var runt = response.vehiculo.runt;
                    $('#txtlicenciatransito').val(response.vehiculo.licencia_transito);
                    $('#txtfechamatricula').val(response.vehiculo.fecha_matricula);
                    $('#txtpaismatricula').val(response.vehiculo.pais_matricula);
                    $('#txtdepartamentomatricula').val(response.vehiculo.dpto_matricula);
                    $('#txtmunicipiomatricula').val(response.vehiculo.muni_matricula);
                    $('#txtciudadmatricula').val(response.vehiculo.ciudad_matricula);
                    if (runt === "N") {
                        $('.matricula').hide();
                    } else {
                        $('.matricula').show();
                    }

                    $('#txttipodocimportacion').val(response.vehiculo.tdoc_imp);
                    $('#txtdocumentoimportacion').val(response.vehiculo.documento_imp);
                    $('#txtfechaimportacion').val(response.vehiculo.fecha_imp);
                    $('#txtpaisimportacion').val(response.vehiculo.pais_imp);

                    var tipoImportacionId = response.vehiculo.tdoc_imp_id;
                    if (tipoImportacionId === 0) {
                        $('.importacion').hide();
                    } else {
                        $('.importacion').show();
                    }

                    $('#txtsoat').val(response.vehiculo.soat === 'S' ? 'Si' : 'No');
                    $('#txtfechavsoat').val(response.vehiculo.fechav_soat);
                    var soat = response.vehiculo.soat;
                    if (soat === 'N') {
                        $('#soatcontenedor').hide();
                    } else {
                        $('#soatcontenedor').show();
                    }

                    $('#txttecnomecanica').val(response.vehiculo.tecno_mecanica === 'S' ? 'Si' : 'No');
                    $('#txtfechavtecnomecanica').val(response.vehiculo.fechav_tecno);
                    var tecnomecanica = response.vehiculo.tecno_mecanica;
                    if (tecnomecanica === 'N') {
                        $('#tecnomecanicacontenedor').hide();
                    } else {
                        $('#tecnomecanicacontenedor').show();
                    }

                    $(".btneditar").attr("data-id", response.vehiculo.veh_id);

                    var personas = response.personasVehiculo;
                    var contenedor = $('#personas-vehiculo');
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
                console.error("Error en la solicitud de cargar datos del vehiculo: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de cargar datos del vehiculo.");
            }
        });

    } else {
        console.log("Parámetros no encontrados en la URL");
    }

    $('#btnvolver').click(function () {
        window.location.href = "listarVehiculos.jsp";
    });

    $('#btneditar').click(function () {
        var id = $(this).data('id');
        window.location.href = "modificarVehiculo.jsp?opcion=2&id=" + id;
    });

});
