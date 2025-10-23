
$(function () {

    $.ajax({
        url: '../../listarUsuarios',
        method: 'get',
        success: function (response) {

            if ($.fn.DataTable.isDataTable('#dataTable')) {
                $('#dataTable').DataTable().destroy();
            }
            
            $("#lista-vehiculos").empty();

            var lista = response.usuarios;

            $.each(lista, function (index, usuario) {
                var nuevoElemento = `
                <tr>
                    <td>${usuario.nombre_usuario}</td>
                    <td>${usuario.fecha_inicio}</td>
                    <td>${usuario.fecha_final}</td>
                    <td>${usuario.perfil}</td>
                    <td>${usuario.estado}</td>
                    <td><button type="button" class="btn btn-info btnconsultar" name="btnconsultar" data-id="${usuario.usu_id}">Consultar</button></td>
                    <td><button type="button" class="btn btn-danger btneditar" name="btneditar" data-id="${usuario.usu_id}">Editar</button></td>
                    </tr>
                `;
                $("#lista-usuarios").append(nuevoElemento);
            });

            $('#dataTable').DataTable({
                responsive: true,
                autoWidth: false
            });
        }
    });

    $('.table-responsive').on('click', '.btnconsultar', function () {
        var id = $(this).data('id');
        window.location.href = "verUsuario.jsp?opcion=1&id=" + id;

    });

    $('.table-responsive').on('click', '.btneditar', function () {
        var id = $(this).data('id');
        window.location.href = "modificarUsuario.jsp?opcion=2&id=" + id;
    });

});
