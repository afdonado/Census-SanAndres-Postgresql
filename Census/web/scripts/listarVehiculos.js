$(function () {
    // Inicialización de DataTable con soporte para el paginado del lado del servidor
    $('#dataTable').DataTable({
        processing: true,
        serverSide: true,
        responsive: true,
        autoWidth: false,
        ajax: {
            url: '../../listarVehiculos',
            type: 'GET',
            dataSrc: 'data'
        },
        columns: [
            {data: 'veh_placa', defaultContent: ''},
            {data: 'veh_motor', defaultContent: ''},
            {data: 'veh_chasis', defaultContent: ''},
            {data: 'veh_serie', defaultContent: ''},
            {data: 'marca', defaultContent: ''},
            {data: 'linea', defaultContent: ''},
            {
                data: null,
                render: function (data) {
                    return `<button type="button" class="btn btn-info btnconsultar" data-id="${data.veh_id}">Consultar</button>`;
                }
            },
            {
                data: null,
                render: function (data) {
                    return `<button type="button" class="btn btn-danger btneditar" data-id="${data.veh_id}">Editar</button>`;
                }
            }
        ],
        language: {
            url: '../../template/vendor/datatables/i18n/Spanish.json'
        }
    });


    // Manejo de eventos de los botones "Consultar" y "Editar"
    $('.table-responsive').on('click', '.btnconsultar', function () {
        var id = $(this).data('id');
        window.location.href = "verVehiculo.jsp?opcion=1&id=" + id;
    });

    $('.table-responsive').on('click', '.btneditar', function () {
        var id = $(this).data('id');
        window.location.href = "modificarVehiculo.jsp?opcion=2&id=" + id;
    });
});
