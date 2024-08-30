$(function () {
    
    var marcas = new Array();
    $("#txtmarcas").bind("keydown", function (event) {
        var data = {marcas: $("#txtmarcas").val()};
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


    var lineas = new Array();
    $("#txtlineas").bind("keydown", function (event) {
        var data = {marcas: $("#txtmarcas").val(), lineas: $("#txtlineas").val()};
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

    var colores = new Array();
    $("#txtcolores").bind("keydown", function (event) {
        var data = {colores: $("#txtcolores").val()};
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