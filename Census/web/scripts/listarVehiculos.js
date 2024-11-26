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
            {data: 'VEH_PLACA', defaultContent: ''},
            {data: 'VEH_MOTOR', defaultContent: ''},
            {data: 'VEH_CHASIS', defaultContent: ''},
            {data: 'VEH_SERIE', defaultContent: ''},
            {data: 'MARCA', defaultContent: ''},
            {data: 'LINEA', defaultContent: ''},
            {
                data: null,
                render: function (data) {
                    return `<button type="button" class="btn btn-info btnconsultar" data-id="${data.VEH_ID}">Consultar</button>`;
                }
            },
            {
                data: null,
                render: function (data) {
                    return `<button type="button" class="btn btn-danger btneditar" data-id="${data.VEH_ID}">Editar</button>`;
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
