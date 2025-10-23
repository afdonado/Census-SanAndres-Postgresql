
$(function () {
    
    $('#dataTable').DataTable({
        processing: true,
        serverSide: true,
        responsive: true,
        autoWidth: false,
        ajax: {
            url: '../../listarPersonas',
            type: 'GET',
            dataSrc: 'data'
        },
        columns: [
            {data: 'tipo_doc', defaultContent: ''},
            {data: 'documento', defaultContent: ''},
            {data: 'nombre_completo', defaultContent: ''},
            {data: 'fecha_nac', defaultContent: ''},
            {data: 'direccion', defaultContent: ''},
            {data: 'departamento', defaultContent: ''},
            {data: 'municipio', defaultContent: ''},
            {data: 'telefono', defaultContent: ''},
            {data: 'mail', defaultContent: ''},
            {
                data: null,
                render: function (data) {
                    return `<button type="button" class="btn btn-info btnconsultar" data-id="${data.per_id}">Consultar</button>`;
                }
            },
            {
                data: null,
                render: function (data) {
                    return `<button type="button" class="btn btn-danger btneditar" data-id="${data.per_id}">Editar</button>`;
                }
            }
        ],
        language: {
            url: '../../template/vendor/datatables/i18n/Spanish.json'
        }
    });

    $('.table-responsive').on('click', '.btnconsultar', function () {
        var id = $(this).data('id');
        window.location.href = "verPersona.jsp?opcion=1&id=" + id;

    });

    $('.table-responsive').on('click', '.btneditar', function () {
        var id = $(this).data('id');
        window.location.href = "modificarPersona.jsp?opcion=2&id=" + id;
    });

});
