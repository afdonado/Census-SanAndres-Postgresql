
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

                    $('#txtclase').val(response.vehiculo.CLASE);
                    $('#txtservicio').val(response.vehiculo.SERVICIO);
                    $('#txttipouso').val(response.vehiculo.TIPO_USO);

                    $('#txtcolores').val(response.vehiculo.COLOR);
                    $('#txtmarcas').val(response.vehiculo.MARCA);
                    $('#txtlineas').val(response.vehiculo.LINEA);
                    $('#txtmodelo').val(response.vehiculo.MODELO);
                    $('#txttransformado').val(response.vehiculo.TRANSFORMADO === 'S' ? 'Si' : 'No');
                    $('#txtrunt').val(response.vehiculo.RUNT === 'S' ? 'Si' : 'No');

                    var runt = response.vehiculo.RUNT;
                    $('#txtlicenciatransito').val(response.vehiculo.LICENCIA_TRANSITO);
                    $('#txtfechamatricula').val(response.vehiculo.FECHA_MATRICULA);
                    $('#txtpaismatricula').val(response.vehiculo.PAIS_MATRICULA);
                    $('#txtdepartamentomatricula').val(response.vehiculo.DPTO_MATRICULA);
                    $('#txtmunicipiomatricula').val(response.vehiculo.MUNI_MATRICULA);
                    $('#txtciudadmatricula').val(response.vehiculo.CIUDAD_MATRICULA);
                    if (runt === "N") {
                        $('.matricula').hide();
                    } else {
                        $('.matricula').show();
                    }

                    $('#txttipodocimportacion').val(response.vehiculo.TDOC_IMP);
                    $('#txtdocumentoimportacion').val(response.vehiculo.DOCUMENTO_IMP);
                    $('#txtfechaimportacion').val(response.vehiculo.FECHA_IMP);
                    $('#txtpaisimportacion').val(response.vehiculo.PAIS_IMP);

                    var tipoImportacionId = response.vehiculo.TDOC_IMP_ID;
                    if (tipoImportacionId === 0) {
                        $('.importacion').hide();
                    } else {
                        $('.importacion').show();
                    }

                    $('#txtsoat').val(response.vehiculo.SOAT === 'S' ? 'Si' : 'No');
                    $('#txtfechavsoat').val(response.vehiculo.FECHAV_SOAT);
                    var soat = response.vehiculo.SOAT;
                    if (soat === 'N') {
                        $('#soatcontenedor').hide();
                    } else {
                        $('#soatcontenedor').show();
                    }

                    $('#txttecnomecanica').val(response.vehiculo.TECNO_MECANICA === 'S' ? 'Si' : 'No');
                    $('#txtfechavtecnomecanica').val(response.vehiculo.FECHAV_TECNO);
                    var tecnomecanica = response.vehiculo.TECNO_MECANICA;
                    if (tecnomecanica === 'N') {
                        $('#tecnomecanicacontenedor').hide();
                    } else {
                        $('#tecnomecanicacontenedor').show();
                    }

                    $(".btneditar").attr("data-id", response.vehiculo.VEH_ID);

                    var personas = response.personasVehiculo;
                    var contenedor = $('#personas-vehiculo');
                    contenedor.empty();

                    $.each(personas, function (index, persona) {
                        var nuevoElemento = `
                <div id="contenedor" class="form-group row">
                    <div class="col-sm-2 mb-3 mb-sm-0">
                        <input class="form-control" id="txttipopersona" name="txttipopersona" value="${persona.TIPO_PERSONA}" readonly="true">
                    </div>
                    <div class="col-sm-2 mb-3 mb-sm-0">
                        <input class="form-control" id="txttipodocumento" name="txttipodocumento" value="${persona.TIPO_DOC}" readonly="true">
                    </div>
                    <div class="col-sm-3 mb-3 mb-sm-0">
                        <input class="form-control" type="text" id="txtdocumento" name="txtdocumento" value="${persona.DOCUMENTO}" readonly="true">
                    </div>
                    <div class="col-sm-5 mb-3 mb-sm-0">
                        <input class="form-control" type="text" id="txtnombre" name="txtnombre" value="${persona.NOMBRE}" readonly="true">
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
