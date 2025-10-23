
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
            {data: 'numero', defaultContent: ''},
            {data: 'fecha', defaultContent: ''},
            {data: 'punto_atencion', defaultContent: ''},
            {data: 'veh_placa', defaultContent: ''},
            {data: 'veh_motor', defaultContent: ''},
            {data: 'veh_chasis', defaultContent: ''},
            {data: 'veh_serie', defaultContent: ''},
            {data: 'verificacion_doc', defaultContent: ''},
            {data: 'verificacion_fotos', defaultContent: ''},
            {data: 'fecha_proceso_verificacion_format', defaultContent: ''},
            {data: 'estado_verificacion', defaultContent: ''},
            {
                data: null,
                render: function (data) {
                    return `<button type="button" class="btn btn-info btnconsultar" data-id="${data.cen_id}">Consultar</button>`;
                }
            },
            {
                data: null,
                render: function (data) {
                    var idverificacion = data.VERIFICACION_ID || '';
                    if (idverificacion === '') {
                        return `<button type="button" class="btn btn-primary btnregistrar" name="btnregistrar" data-id="${data.cen_id}">Guardar</button>`;
                    } else {
                        return `<button type="button" class="btn btn-danger btneditar" name="btneditar" data-id="${data.cen_id}">Editar</button>`;
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
