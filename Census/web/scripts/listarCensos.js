
$(function () {

    $.ajax({
        url: '../../listarCensos',
        method: 'get',
        success: function (data) {

            if ($.fn.DataTable.isDataTable('#dataTable')) {
                $('#dataTable').DataTable().destroy();
            }

            $.each(data, function (index, censo) {
                var nuevoElemento = `
                <tr>
                    <td>${censo.NUMERO}</td>
                    <td>${censo.FECHA}</td>
                    <td>${censo.HORA}</td>
                    <td>${censo.PUNTO_ATENCION}</td>
                    <td>${censo.ESTADO}</td>
                    <td>${censo.USUARIO}</td>
                    <td>${censo.FECHA_PROCESO}</td>
                    <td>${censo.DOCUMENTO_PDF}</td>
                    <td>${censo.FOTO}</td>
                    <td><button type="button" class="btn btn-info btnconsultar" name="btnconsultar" data-id="${censo.CEN_ID}" title="Ver datos del vehiculo">Consultar</button></td>
                    <td><button type="button" class="btn btn-danger btneditar" name="btneditar" data-id="${censo.CEN_ID}" title="Editar datos del vehiculo">Editar</button></td>
                    </tr>
                `;
                $("#lista-censos").append(nuevoElemento);
            });
        }
    });

    $('.table-responsive').on('click', '.btnconsultar', function () {
        console.log("Consultar Vehiculo");
        var id = $(this).data('id');
        console.log("id: " + id);
        window.location.href = "verCenso.jsp?opcion=1&id=" + id;

    });

    $('.table-responsive').on('click', '.btneditar', function () {
        console.log("Editar Vehiculo");
        var id = $(this).data('id');
        console.log("id: " + id);
        window.location.href = "verModificarCenso.jsp?opcion=2&id=" + id;
    });

});
