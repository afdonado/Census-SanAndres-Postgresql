
$(function () {
// Inicialización de DataTable con soporte para el paginado del lado del servidor
    $('#dataTable').DataTable({
        processing: true,
        serverSide: true,
        responsive: true,
        autoWidth: false,
        ajax: {
            url: '../../listarVerificaciones',
            type: 'GET',
            dataSrc: 'data'
        },
        columns: [
            {data: 'NUMERO', defaultContent: ''},
            {data: 'FECHA', defaultContent: ''},
            {data: 'PUNTO_ATENCION', defaultContent: ''},
            {data: 'VEH_PLACA', defaultContent: ''},
            {data: 'VEH_MOTOR', defaultContent: ''},
            {data: 'VEH_CHASIS', defaultContent: ''},
            {data: 'VEH_SERIE', defaultContent: ''},
            {data: 'VERIFICACION_DOC', defaultContent: ''},
            {data: 'VERIFICACION_FOTOS', defaultContent: ''},
            {data: 'FECHA_PROCESO_VERIFICACION_FORMAT', defaultContent: ''},
            {data: 'ESTADO_VERIFICACION', defaultContent: ''},
            {
                data: null,
                render: function (data) {
                    return `<button type="button" class="btn btn-info btnconsultar" data-id="${data.CEN_ID}">Consultar</button>`;
                }
            },
            {
                data: null,
                render: function (data) {
                    var idverificacion = data.VERIFICACION_ID || '';
                    if (idverificacion === '') {
                        return `<button type="button" class="btn btn-primary btnregistrar" name="btnregistrar" data-id="${data.CEN_ID}">Guardar</button>`;
                    } else {
                        return `<button type="button" class="btn btn-danger btneditar" name="btneditar" data-id="${data.CEN_ID}">Editar</button>`;
                    }
                }
            }
        ],
        language: {
            url: '../../template/vendor/datatables/i18n/Spanish.json'
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
