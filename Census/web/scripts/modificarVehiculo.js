
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
            url: '../../cargarDatosVehiculo',
            method: 'get',
            data: {id: id},
            success: function (data) {
                $('#txtplaca').val(data.vehiculo.VEH_PLACA);
                $('#txtmotor').val(data.vehiculo.VEH_MOTOR);
                $('#txtchasis').val(data.vehiculo.VEH_CHASIS);
                $('#txtserie').val(data.vehiculo.VEH_SERIE);

                var claseVehiculoId = data.vehiculo.CLASE_ID;
                var servicioId = data.vehiculo.SERVICIO_ID;
                var tipousoId = data.vehiculo.TIPO_USO_ID;

                $('#txtcolores').val(data.vehiculo.COLOR);
                $('#txtmarcas').val(data.vehiculo.MARCA);
                $('#txtlineas').val(data.vehiculo.LINEA);
                $('#txtmodelo').val(data.vehiculo.MODELO);
                $('#cmbtransformado').val(data.vehiculo.TRANSFORMADO);
                $('#cmbrunt').val(data.vehiculo.RUNT);
                $('#txtlicenciatransito').val(data.vehiculo.LICENCIA_TRANSITO);
                $('#txtfechamatricula').val(data.vehiculo.FECHA_MATRICULA);


                var paisMatriculaId = data.vehiculo.PAIS_MATRICULA_ID;
                var departamentoMatriculaId = data.vehiculo.DPTO_MATRICULA_ID;
                var municipioMatriculaId = data.vehiculo.MUNI_MATRICULA_ID;
                $('#txtciudadmatricula').val(data.vehiculo.CIUDAD_MATRICULA);

                var tipoImportacionId = data.vehiculo.TDOC_IMP_ID;
                $('#txtdocumentoimportacion').val(data.vehiculo.DOCUMENTO_IMP);
                $('#txtfechaimportacion').val(data.vehiculo.FECHA_IMP);
                var paisImportacionId = data.vehiculo.PAIS_IMP_ID;

                $('#cmbsoat').val(data.vehiculo.SOAT);
                $('#txtfechavsoat').val(data.vehiculo.FECHAV_SOAT);
                $('#cmbtecnomecanica').val(data.vehiculo.TECNO_MECANICA);
                $('#txtfechavtecnomecanica').val(data.vehiculo.FECHAV_TECNO);

                $('#txtobservaciones').val(data.vehiculo.OBSERVACIONES);
                $('#idvehiculo').val(data.vehiculo.VEH_ID);

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

                        selectImportacion.val(paisImportacionId);

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
                        select.val(tipoImportacionId);
                    }
                });

                var personas = data.personasVehiculo;
                var contenedor = $('#personas-vehiculo');
                var contador = 0;
                contenedor.empty();

                $.each(personas, function (index, persona) {
                    contador = index + 1;
                    var nuevoElemento = `
                <div id="contenedor${persona.PV_ID}" class="form-group row">
                    <div class="col-sm-2 mb-3 mb-sm-0">
                        <select class="form-control" id="cmbtipospersona${contador}" name="cmbtipospersona${contador}" required="true"></select>
                    </div>
                    <div class="col-sm-2 mb-3 mb-sm-0">
                        <select class="form-control" id="cmbtiposdocumento${contador}" name="cmbtiposdocumento${contador}" required="true"></select>
                    </div>
                    <div class="col-sm-3 mb-3 mb-sm-0">
                        <input class="form-control" type="number" id="txtdocumento${contador}" name="txtdocumento${contador}" maxlength="20" value="${persona.DOCUMENTO}" required="true">
                    </div>
                    <div class="col-sm-5 mb-2 mb-sm-0">
                        <input class="form-control" type="text" id="txtnombre${contador}" name="txtnombre${contador}" value="${persona.NOMBRE}" readonly="true">
                    </div>
                    <div class="col-sm-1 mb-1 mb-sm-0">
                        <button class="btn btn-danger" onclick="anularPersonaVehiculo(${persona.PV_ID});">X</button>
                    </div>
                    <input type="hidden" id="idpersona${contador}" name="idpersona${contador}" value="${persona.ID}">
                    <input type="hidden" id="estadoperveh${persona.PV_ID}" name="estadoperveh${persona.PV_ID}" value="1">
                </div>
            `;
                    contenedor.append(nuevoElemento);

                    cargarTiposPersona(`#cmbtipospersona${contador}`, persona.TIPO_PERSONA_ID);
                    cargarTiposDocumento(`#cmbtiposdocumento${contador}`, persona.TIPO_DOC_ID);
                });
                console.log('contador: ', contador);
                $('#txtcantidadpersonas').val(contador);

            }
        });

    } else {
        console.log("Parámetros no encontrados en la URL");
    }

    function anularPersonaVehiculo(idperveh) {
        $('#estadoperveh' + idperveh).val(2);
        $('#registropersona' + idperveh).hide();

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
                console.log("tipo persona: ", tipoPersonaId);
                select.val(tipoPersonaId);
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
                console.log("tipo documento: ", tipoDocumentoId);
                select.val(tipoDocumentoId);
            }
        });
    }

});
