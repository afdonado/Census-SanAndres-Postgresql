
$(function () {

    $.ajax({
        url: '../../listarUsuarios',
        method: 'get',
        success: function (data) {

            if ($.fn.DataTable.isDataTable('#dataTable')) {
                $('#dataTable').DataTable().destroy();
            }

            $.each(data, function (index, usuario) {
                var nuevoElemento = `
                <tr>
                    <td>${usuario.NOMBRE_USUARIO}</td>
                    <td>${usuario.FECHA_INICIO}</td>
                    <td>${usuario.FECHA_FINAL}</td>
                    <td>${usuario.PERFIL}</td>
                    <td>${usuario.ESTADO}</td>
                    <td><button type="button" class="btn btn-info btnconsultar" name="btnconsultar" data-id="${usuario.USU_ID}">Consultar</button></td>
                    <td><button type="button" class="btn btn-danger btneditar" name="btneditar" data-id="${usuario.USU_ID}">Editar</button></td>
                    </tr>
                `;
                $("#lista-usuarios").append(nuevoElemento);
            });
        }
    });

    $('.table-responsive').on('click', '.btnconsultar', function () {
        var id = $(this).data('id');
        window.location.href = "verUsuario.jsp?opcion=1&id=" + id;

    });

    $('.table-responsive').on('click', '.btneditar', function () {
        var id = $(this).data('id');
        window.location.href = "verModificarUsuario.jsp?opcion=2&id=" + id;
    });

});
