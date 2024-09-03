
$(function () {

    $.ajax({
        url: '../../listarVerificaciones',
        method: 'get',
        success: function (response) {

            if ($.fn.DataTable.isDataTable('#dataTable')) {
                $('#dataTable').DataTable().destroy();
            }

console.log('respuesta -> ',response);
            var lista = response.verificaciones;

            $.each(lista, function (index, verificacion) {
                var nuevoElemento = `
                <tr>
                    <td>${verificacion.NUMERO}</td>
                    <td>${verificacion.FECHA}</td>
                    <td>${verificacion.HORA}</td>
                    <td>${verificacion.PUNTO_ATENCION}</td>
                    <td>${verificacion.ESTADO}</td>
                    <td>${verificacion.USUARIO}</td>
                    <td>${verificacion.DOCUMENTO_PDF}</td>
                    <td>${verificacion.FOTO}</td>
                    <td>${verificacion.FECHA_PROCESO}</td>
                    <td>${verificacion.FECHA_PROCESO_VERIFICACION}</td>
                    <td>${verificacion.ESTADO_VERIFICACION}</td>
                    <td><button type="button" class="btn btn-info btnconsultar" name="btnconsultar" data-id="${verificacion.VER_ID}">Consultar</button></td>
                    <td><button type="button" class="btn btn-danger btneditar" name="btneditar" data-id="${verificacion.VER_ID}">Editar</button></td>
                    </tr>
                `;
                $("#lista-verificaciones").append(nuevoElemento);
            });
        }
    });

    $('.table-responsive').on('click', '.btnconsultar', function () {
        var id = $(this).data('id');
        window.location.href = "verVerificacion.jsp?opcion=1&id=" + id;

    });

    $('.table-responsive').on('click', '.btneditar', function () {
        var id = $(this).data('id');
        window.location.href = "modificarVerificacion.jsp?opcion=2&id=" + id;
    });

});
