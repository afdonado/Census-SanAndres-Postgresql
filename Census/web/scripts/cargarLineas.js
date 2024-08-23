
$(function () {
    var lineas = new Array();
    $("#txtlineas").bind("keydown", function (event) {
        var data = {txtmarcas: $("#txtmarcas").val(), txtlineas: $("#txtlineas").val()};
        $.getJSON("../../cargarLineas", data, function (res, est, jqXHR) {
            lineas.length = 0;
            $.each(res, function (i, item) {
                lineas[i] = item;
            });
        });
    });
    $("#txtlineas").autocomplete({
        source: lineas,
        minLength: 1
    });
});