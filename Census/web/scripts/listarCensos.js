
$(function () {

    $.ajax({
        url: '../../listarCensos',
        method: 'get',
        success: function (response) {

            if (response.status === "success") {
                if ($.fn.DataTable.isDataTable('#dataTable')) {
                    $('#dataTable').DataTable().destroy();
                }

                $("#lista-vehiculos").empty();

                var lista = response.censos;

                $.each(lista, function (index, censo) {

                    var placa = '';
                    if (censo.VEH_PLACA && censo.VEH_PLACA.length > 0) {
                        placa = censo.VEH_PLACA;
                    }

                    var motor = '';
                    if (censo.VEH_MOTOR && censo.VEH_MOTOR.length > 0) {
                        motor = censo.VEH_MOTOR;
                    }

                    var chasis = '';
                    if (censo.VEH_CHASIS && censo.VEH_CHASIS.length > 0) {
                        chasis = censo.VEH_CHASIS;
                    }

                    var serie = '';
                    if (censo.VEH_SERIE && censo.VEH_SERIE.length > 0) {
                        serie = censo.VEH_SERIE;
                    }

                    var nuevoElemento = `
                <tr>
                    <td>${censo.NUMERO}</td>
                    <td>${censo.FECHA}</td>
                    <td>${censo.HORA}</td>
                    <td>${censo.PUNTO_ATENCION}</td>
                    <td>${censo.ESTADO}</td>
                    <td>${censo.USUARIO}</td>
                    <td>${censo.FECHA_PROCESO_FORMAT} ${censo.FECHA_PROCESO_HORA}</td>
                    <td>${censo.DOCUMENTO_PDF}</td>
                    <td>${placa}</td>
                    <td>${motor}</td>
                    <td>${chasis}</td>
                    <td>${serie}</td>
                    <td><button type="button" class="btn btn-info btnconsultar" name="btnconsultar" data-id="${censo.CEN_ID}">Consultar</button></td>
                    <td><button type="button" class="btn btn-danger btneditar" name="btneditar" data-id="${censo.CEN_ID}">Editar</button></td>
                    </tr>
                `;
                    $("#lista-censos").append(nuevoElemento);
                });

                $('#dataTable').DataTable({
                    responsive: true,
                    autoWidth: false
                });

            } else if (response.status === "fail") {
                alert(response.message);
            } else if (response.status === "error") {
                alert(response.message);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de listas censos: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de listas censos.");
        }
    });

    $('.table-responsive').on('click', '.btnconsultar', function () {
        var id = $(this).data('id');
        window.location.href = "verCenso.jsp?opcion=1&id=" + id;

    });

    $('.table-responsive').on('click', '.btneditar', function () {
        var id = $(this).data('id');
        window.location.href = "modificarCenso.jsp?opcion=2&id=" + id;
    });

});
