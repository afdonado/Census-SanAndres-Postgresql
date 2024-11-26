
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
            {data: 'TIPO_DOC', defaultContent: ''},
            {data: 'DOCUMENTO', defaultContent: ''},
            {data: 'NOMBRE_COMPLETO', defaultContent: ''},
            {data: 'FECHA_NAC', defaultContent: ''},
            {data: 'DIRECCION', defaultContent: ''},
            {data: 'DEPARTAMENTO', defaultContent: ''},
            {data: 'MUNICIPIO', defaultContent: ''},
            {data: 'TELEFONO', defaultContent: ''},
            {data: 'MAIL', defaultContent: ''},
            {
                data: null,
                render: function (data) {
                    return `<button type="button" class="btn btn-info btnconsultar" data-id="${data.PER_ID}">Consultar</button>`;
                }
            },
            {
                data: null,
                render: function (data) {
                    return `<button type="button" class="btn btn-danger btneditar" data-id="${data.PER_ID}">Editar</button>`;
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
