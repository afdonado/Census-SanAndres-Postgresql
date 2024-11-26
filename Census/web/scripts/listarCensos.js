
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
            {data: 'NUMERO', defaultContent: ''},
            {data: 'FECHA', defaultContent: ''},
            {data: 'HORA', defaultContent: ''},
            {data: 'PUNTO_ATENCION', defaultContent: ''},
            {data: 'ESTADO', defaultContent: ''},
            {data: 'USUARIO', defaultContent: ''},
            {data: 'FECHA_PROCESO_FORMAT'.concat(' '.concat('FECHA_PROCESO_HORA')), defaultContent: ''},
            {data: 'DOCUMENTO_PDF', defaultContent: ''},
            {data: 'VEH_PLACA', defaultContent: ''},
            {data: 'VEH_MOTOR', defaultContent: ''},
            {data: 'VEH_CHASIS', defaultContent: ''},
            {data: 'VEH_SERIE', defaultContent: ''},
            {
                data: null,
                render: function (data) {
                    return `<button type="button" class="btn btn-info btnconsultar" data-id="${data.CEN_ID}">Consultar</button>`;
                }
            },
            {
                data: null,
                render: function (data) {
                    return `<button type="button" class="btn btn-danger btneditar" data-id="${data.CEN_ID}">Editar</button>`;
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
