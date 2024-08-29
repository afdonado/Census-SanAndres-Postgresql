$(function () {
    $('#departamento-matricula').on('change', '#cmbdepartamentomatricula', function () {
        cargarMunicipiosByDepartamento($('#cmbdepartamentomatricula').val(), 'cmbmunicipiomatricula');
    });

    $('#departamento-matricula').on('focus', '#cmbdepartamentomatricula', function () {
        cargarMunicipiosByDepartamento($('#cmbdepartamentomatricula').val(), 'cmbmunicipiomatricula');
    });

    function cargarMunicipiosByDepartamento(iddepartamento, namecmbmunicipio) {
        var parametros = {
            "iddepartamento": iddepartamento,
            "nameCombo": namecmbmunicipio
        };
        $.ajax({
            data: parametros,
            url: "../../cargarMunicipios",
            type: "post"
        })
                .done(function (data) {
                    var respuesta = data.toString().trim();
                    $("#municipio-matricula").html(respuesta);
                });
    }


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

    $.ajax({
        url: "../../cargarClasesVehiculo",
        type: "post"
    }).done(function (data) {
        var respuesta = data.toString().trim();
        $("#clase-vehiculo").append(respuesta);
    });

    $.ajax({
        url: "../../cargarTiposServicio",
        type: "post"
    }).done(function (data) {
        var respuesta = data.toString().trim();
        $("#tipos-servicio").append(respuesta);
    });

    $.ajax({
        url: "../../cargarTiposUso",
        type: "post"
    }).done(function (data) {
        var respuesta = data.toString().trim();
        $("#tipos-uso").append(respuesta);
    });
    
    $.ajax({
        url: "../../cargarTiposReferencia",
        type: "post"
    }).done(function (data) {
        var respuesta = data.toString().trim();
        $("#tipos-referencia").append(respuesta);
    });
    
    $.ajax({
        url: "../../cargarTiposImportacion",
        type: "post"
    }).done(function (data) {
        var respuesta = data.toString().trim();
        $("#tipos-importacion").append(respuesta);
    });

    $.ajax({
        data: {
            "nameCombo": "cmbpaismatricula",
            "label": "Pais Matricula"
        },
        url: "../../cargarPaises",
        type: "post"
    }).done(function (data) {
        var respuesta = data.toString().trim();
        $("#pais-matricula").append(respuesta);
    });
    
    $.ajax({
        data: {
            "nameCombo": "cmbpaisimportacion",
            "label": "Pais Importación"
        },
        url: "../../cargarPaises",
        type: "post"
    }).done(function (data) {
        var respuesta = data.toString().trim();
        $("#pais-importancion").append(respuesta);
    });
    
    $.ajax({
        data: {"nameCombo": "cmbdepartamentomatricula"},
        url: "../../cargarDepartamentos",
        type: "post"
    }).done(function (data) {
        var respuesta = data.toString().trim();
        $("#departamento-matricula").append(respuesta);
    });
    
    $.ajax({
        data: {
            "iddepartamento": "4",
            "nameCombo": "cmbmunicipiomatricula"
        },
        url: "../../cargarMunicipios",
        type: "post"
    }).done(function (data) {
        var respuesta = data.toString().trim();
        $("#municipio-matricula").append(respuesta);
    });
    
    $.ajax({
        data: {
            "nameCombo": "cmbtipospersona1"
        },
        url: "../../cargarTiposPersona",
        type: "post"
    }).done(function (data) {
        var respuesta = data.toString().trim();
        $("#tipos-persona1").append(respuesta);
    });
    
    $.ajax({
        data: {
            nameCombo: "cmbtiposdocumento1",
            label: false
        },
        url: "../../cargarTiposDocumento",
        type: "post"
    }).done(function (data) {
        var respuesta = data.toString().trim();
        $("#tipos-documento1").append(respuesta);
    });
    
    $.ajax({
        data: {
            nameCombo: "cmbtiposdocumento",
            label: true
        },
        url: "../../cargarTiposDocumento",
        type: "post"
    }).done(function (data) {
        var respuesta = data.toString().trim();
        $("#tipos-documento").append(respuesta);
    });
    
});