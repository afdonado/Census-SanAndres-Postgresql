
$(function () {

    $.ajax({
        url: '../../listarVehiculos',
        method: 'get',
        success: function (response) {
            if ($.fn.DataTable.isDataTable('#dataTable')) {
                $('#dataTable').DataTable().destroy();
            }

            $("#lista-vehiculos").empty();

            var lista = response.vehiculos;

            $.each(lista, function (index, vehiculo) {
                var nuevoElemento = `
                <tr>
                    <td>${vehiculo.VEH_PLACA}</td>
                    <td>${vehiculo.VEH_MOTOR}</td>
                    <td>${vehiculo.VEH_CHASIS}</td>
                    <td>${vehiculo.VEH_SERIE}</td>
                    <td>${vehiculo.MARCA}</td>
                    <td>${vehiculo.LINEA}</td>
                    <td><button type="button" class="btn btn-info btnconsultar" name="btnconsultar" data-id="${vehiculo.VEH_ID}">Consultar</button></td>
                    <td><button type="button" class="btn btn-danger btneditar" name="btneditar" data-id="${vehiculo.VEH_ID}">Editar</button></td>
                    </tr>
                `;
                $("#lista-vehiculos").append(nuevoElemento);
            });

            $('#dataTable').DataTable({
                responsive: true,
                autoWidth: false
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de listas vehiculos: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de listas vehiculos.");
        }
    });

    $('.table-responsive').on('click', '.btnconsultar', function () {
        var id = $(this).data('id');
        window.location.href = "verVehiculo.jsp?opcion=1&id=" + id;

    });

    $('.table-responsive').on('click', '.btneditar', function () {
        var id = $(this).data('id');
        window.location.href = "modificarVehiculo.jsp?opcion=2&id=" + id;
    });

});
