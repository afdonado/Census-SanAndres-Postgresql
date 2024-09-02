
$(function () {

    $.ajax({
        url: '../../listarPersonas',
        method: 'get',
        success: function (response) {

            if (response.status === "success") {
                if ($.fn.DataTable.isDataTable('#dataTable')) {
                    $('#dataTable').DataTable().destroy();
                }

                var lista = response.personas;

                $.each(lista, function (index, persona) {
                    var nuevoElemento = `
                <tr>
                    <td>${persona.TIPO_DOC}</td>
                    <td>${persona.DOCUMENTO}</td>
                    <td>${persona.NOMBRE_COMPLETO}</td>
                    <td>${persona.FECHA_NAC}</td>
                    <td>${persona.DIRECCION}</td>
                    <td>${persona.DEPARTAMENTO}</td>
                    <td>${persona.MUNICIPIO}</td>
                    <td>${persona.TELEFONO}</td>
                    <td>${persona.MAIL}</td>
                    <td><button type="button" class="btn btn-info btnconsultar" name="btnconsultar" data-id="${persona.PER_ID}">Consultar</button></td>
                    <td><button type="button" class="btn btn-danger btneditar" name="btneditar" data-id="${persona.PER_ID}">Editar</button></td>
                    </tr>
                `;
                    $("#lista-personas").append(nuevoElemento);
                });
            } else if (response.status === "fail") {
                alert(response.message);
            } else if (response.status === "error") {
                alert(response.message);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de listas personas: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de listas personas");
        }
    });

    $('.table-responsive').on('click', '.btnconsultar', function () {
        var id = $(this).data('id');
        console.log("id: " + id);
        window.location.href = "verPersona.jsp?opcion=1&id=" + id;

    });

    $('.table-responsive').on('click', '.btneditar', function () {
        var id = $(this).data('id');
        console.log("id: " + id);
        window.location.href = "modificarPersona.jsp?opcion=2&id=" + id;
    });

});
