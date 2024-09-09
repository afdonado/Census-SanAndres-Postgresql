
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

                var btnaccion = '';

                var idverificacion = verificacion.VERIFICACION_ID || '';
                if (idverificacion === '') {
                    btnaccion = `<button type="button" class="btn btn-primary btnregistrar" name="btnregistrar" data-id="${verificacion.CEN_ID}">Guardar</button>`;
                } else {
                    btnaccion = `<button type="button" class="btn btn-danger btneditar" name="btneditar" data-id="${verificacion.CEN_ID}">Editar</button>`;
                }

                var nuevoElemento = `
                <tr>
                    <td>${verificacion.NUMERO}</td>
                    <td>${verificacion.FECHA}</td>
                    <td>${verificacion.HORA}</td>
                    <td>${verificacion.PUNTO_ATENCION}</td>
                    <td>${verificacion.ESTADO}</td>
                    <td>${verificacion.USUARIO}</td>
                    <td>${verificacion.VERIFICACION_DOC === 'S' ? 'Si': 'No'}</td>
                    <td>${verificacion.VERIFICACION_FOTOS === 'S' ? 'Si' : 'No'}</td>
                    <td>${verificacion.FECHA_PROCESO_FORMAT}</td>
                    <td>${verificacion.FECHA_PROCESO_VERIFICACION_FORMAT}</td>
                    <td>${verificacion.ESTADO_VERIFICACION}</td>
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
