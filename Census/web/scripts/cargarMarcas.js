
$(function () {
    var marcas = new Array();
    $("#txtmarcas").bind("keydown", function (event) {
        var data = {txtmarcas: $("#txtmarcas").val()};
        $.getJSON("../../cargarMarcas", data, function (res, est, jqXHR) {
            marcas.length = 0;
            $.each(res, function (i, item) {
                marcas[i] = item;
            });
        });
    });
    $("#txtmarcas").autocomplete({
        source: marcas,
        minLength: 1
    });
});