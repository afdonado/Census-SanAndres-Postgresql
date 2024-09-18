
$(function () {

    $.ajax({
        url: '../../listarVerificaciones',
        method: 'get',
        success: function (response) {

            if ($.fn.DataTable.isDataTable('#dataTable')) {
                $('#dataTable').DataTable().destroy();
            }

            $("#lista-vehiculos").empty();

            var lista = response.verificaciones;

            $.each(lista, function (index, verificacion) {
                
                var numero = '';
                if (verificacion.NUMERO && verificacion.NUMERO.length > 0) {
                    numero = verificacion.NUMERO;
                }
                
                var fecha = '';
                if (verificacion.FECHA && verificacion.FECHA.length > 0) {
                    fecha = verificacion.FECHA;
                }
                
                var punto_atencion = '';
                if (verificacion.PUNTO_ATENCION && verificacion.PUNTO_ATENCION.length > 0) {
                    punto_atencion = verificacion.PUNTO_ATENCION;
                }
                
                var placa = '';
                if (verificacion.VEH_PLACA && verificacion.VEH_PLACA.length > 0) {
                    placa = verificacion.VEH_PLACA;
                }
                
                var motor = '';
                if (verificacion.VEH_MOTOR && verificacion.VEH_MOTOR.length > 0) {
                    motor = verificacion.VEH_MOTOR;
                }
                
                var chasis = '';
                if (verificacion.VEH_CHASIS && verificacion.VEH_CHASIS.length > 0) {
                    chasis = verificacion.VEH_CHASIS;
                }
                
                var serie = '';
                if (verificacion.VEH_SERIE && verificacion.VEH_SERIE.length > 0) {
                    serie = verificacion.VEH_SERIE;
                }
                
                var verificacion_documentos = '';
                if (verificacion.VERIFICACION_DOC && verificacion.VERIFICACION_DOC.length > 0) {
                    verificacion_documentos = verificacion.VERIFICACION_DOC;
                }
                
                var verificacion_fotos = '';
                if (verificacion.VERIFICACION_FOTOS && verificacion.VERIFICACION_FOTOS.length > 0) {
                    verificacion_fotos = verificacion.VERIFICACION_FOTOS;
                }
                
                var fecha_verificacion = '';
                if (verificacion.FECHA_PROCESO_VERIFICACION_FORMAT 
                        && verificacion.FECHA_PROCESO_VERIFICACION_FORMAT.length > 0) {
                    fecha_verificacion = verificacion.FECHA_PROCESO_VERIFICACION_FORMAT;
                }
                
                var estado_verificacion = '';
                if (verificacion.ESTADO_VERIFICACION && verificacion.ESTADO_VERIFICACION.length > 0) {
                    estado_verificacion = verificacion.ESTADO_VERIFICACION;
                }

                var btnaccion = '';

                var idverificacion = verificacion.VERIFICACION_ID || '';
                if (idverificacion === '') {
                    btnaccion = `<button type="button" class="btn btn-primary btnregistrar" name="btnregistrar" data-id="${verificacion.CEN_ID}">Guardar</button>`;
                } else {
                    btnaccion = `<button type="button" class="btn btn-danger btneditar" name="btneditar" data-id="${verificacion.CEN_ID}">Editar</button>`;
                }
                
                

                var nuevoElemento = `
                <tr>
                    <td>${numero}</td>
                    <td>${fecha}</td>
                    <td>${punto_atencion}</td>
                    <td>${placa}</td>
                    <td>${motor}</td>
                    <td>${chasis}</td>
                    <td>${serie}</td>
                    <td>${verificacion_documentos === 'S' ? 'Si': 'No'}</td>
                    <td>${verificacion_fotos === 'S' ? 'Si' : 'No'}</td>
                    <td>${fecha_verificacion}</td>
                    <td>${estado_verificacion}</td>
                    <td><button type="button" class="btn btn-info btnconsultar" name="btnconsultar" data-id="${verificacion.CEN_ID}">Consultar</button></td>
                    <td>${btnaccion}</td>
                    </tr>
                `;
                $("#lista-verificaciones").append(nuevoElemento);
            });

            $('#dataTable').DataTable({
                responsive: true,
                autoWidth: false
            });

        }
    });

    $('.table-responsive').on('click', '.btnregistrar', function () {
        var id = $(this).data('id');
        window.location.href = "registrarVerificacion.jsp?opcion=1&id=" + id;
    });

    $('.table-responsive').on('click', '.btneditar', function () {
        var id = $(this).data('id');
        window.location.href = "modificarVerificacion.jsp?opcion=2&id=" + id;
    });

    $('.table-responsive').on('click', '.btnconsultar', function () {
        var id = $(this).data('id');
        window.location.href = "verVerificacion.jsp?opcion=1&id=" + id;
    });

});
