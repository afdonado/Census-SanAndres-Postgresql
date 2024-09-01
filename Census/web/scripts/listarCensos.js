
$(function () {

    $.ajax({
        url: '../../listarCensos',
        method: 'get',
        success: function (response) {

            if (response.status === "success") {
                if ($.fn.DataTable.isDataTable('#dataTable')) {
                    $('#dataTable').DataTable().destroy();
                }

                var lista = response.censos;

                $.each(lista, function (index, censo) {
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
                    <td><button type="button" class="btn btn-info btnconsultar" name="btnconsultar" data-id="${censo.CEN_ID}">Consultar</button></td>
                    <td><button type="button" class="btn btn-danger btneditar" name="btneditar" data-id="${censo.CEN_ID}">Editar</button></td>
                    </tr>
                `;
                    $("#lista-censos").append(nuevoElemento);
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
        console.log("id: " + id);
        window.location.href = "verCenso.jsp?opcion=1&id=" + id;

    });

    $('.table-responsive').on('click', '.btneditar', function () {
        var id = $(this).data('id');
        console.log("id: " + id);
        window.location.href = "verModificarCenso.jsp?opcion=2&id=" + id;
    });

});
