
$(function () {
    $.ajax({
        url: "../../listarVehiculos",
        type: "post"
    }).done(function (data) {
        if ($.fn.DataTable.isDataTable('#dataTable')) {
            $('#dataTable').DataTable().destroy();
        }
        var respuesta = data.toString().trim();
        $("#lista-vehiculos").html(respuesta);
        $('#dataTable').DataTable();
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.error("Error al obtener los datos: ", textStatus, errorThrown);
    });

    $('.table-responsive').on('click', '.btnconsultar', function () {
        console.log("Consultar Vehiculo");
        var id = $(this).data('id');
        console.log("id: " + id);
        window.location.href = "../../cargarDatosVehiculo?id=" + id;
        
    });

    $('.table-responsive').on('click', '.btneditar', function () {
        console.log("Editar Vehiculo");
        var id = $(this).data('id');
        console.log("id: " + id);
        window.location.href = "verModificarVehiculo.jsp?id=" + id;
    });

});
