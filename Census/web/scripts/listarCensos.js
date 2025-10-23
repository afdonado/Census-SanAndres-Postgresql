
$(function () {

    // Inicialización de DataTable con soporte para el paginado del lado del servidor
    $('#dataTable').DataTable({
        processing: true,
        serverSide: true,
        responsive: true,
        autoWidth: false,
        ajax: {
            url: '../../listarCensos',
            type: 'GET',
            dataSrc: 'data'
        },
        columns: [
            {data: 'numero', defaultContent: ''},
            {data: 'fecha', defaultContent: ''},
            {data: 'hora', defaultContent: ''},
            {data: 'punto_atencion', defaultContent: ''},
            {data: 'estado', defaultContent: ''},
            {data: 'usuario', defaultContent: ''},
            {data: 'fecha_proceso_format'.concat(' '.concat('fecha_proceso_hora')), defaultContent: ''},
            {data: 'documento_pdf', defaultContent: ''},
            {data: 'veh_placa', defaultContent: ''},
            {data: 'veh_motor', defaultContent: ''},
            {data: 'veh_chasis', defaultContent: ''},
            {data: 'veh_serie', defaultContent: ''},
            {
                data: null,
                render: function (data) {
                    return `<button type="button" class="btn btn-info btnconsultar" data-id="${data.cen_id}">Consultar</button>`;
                }
            },
            {
                data: null,
                render: function (data) {
                    return `<button type="button" class="btn btn-danger btneditar" data-id="${data.cen_id}">Editar</button>`;
                }
            }
        ],
        language: {
            url: '../../template/vendor/datatables/i18n/Spanish.json'
        }
    });

    $('.table-responsive').on('click', '.btnconsultar', function () {
        var id = $(this).data('id');
        window.location.href = "verCenso.jsp?opcion=1&id=" + id;

    });

    $('.table-responsive').on('click', '.btneditar', function () {
        var id = $(this).data('id');
        window.location.href = "modificarCenso.jsp?opcion=2&id=" + id;
    });

});
