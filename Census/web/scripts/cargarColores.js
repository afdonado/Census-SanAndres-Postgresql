
$(function () {
    var colores = new Array();
    $("#txtcolores").bind("keydown", function (event) {
        var data = {txtcolores: $("#txtcolores").val()};
        $.getJSON("../../cargarColores", data, function (res, est, jqXHR) {
            colores.length = 0;
            $.each(res, function (i, item) {
                colores[i] = item;
            });
        });
    });
    $("#txtcolores").autocomplete({
        source: colores,
        minLength: 1
    });
});