
$(function () {

    $("#ciudad-matricula").hide();

    var identificador = 0;
    $('#agregar-campos').click(function () {
        identificador = $("#txtcantidadpersonas").val();
        if (identificador < 5) {
            identificador++;
            var $contentID = $("#personas-vehiculo");

            var $nuevoDivPersona = $("<div>", {
                id: 'contenedor'.concat(identificador),
                class: 'row'
            });

            $.ajax({
                type: "post",
                url: "../../agregarCamposPersona",
                data: {
                    identificador: identificador,
                    nameComboTP: "cmbtipospersona".concat(identificador),
                    nameComboTD: "cmbtiposdocumento".concat(identificador),
                    nameTxtDocumento: "txtdocumento".concat(identificador),
                    nameTxtNombre: "txtnombre".concat(identificador),
                    nameTxtId: "idpersona".concat(identificador)
                },
                contentType: "application/x-www-form-urlencoded",
                success: function (response) {
                    $("#txtcantidadpersonas").val(identificador);
                    $nuevoDivPersona.html(response);
                    $nuevoDivPersona.css('class', 'form-group row');
                    $contentID.append($nuevoDivPersona);
                },
                error: function () {
                    alert("Hubo un error al cargar los campos.");
                }
            });
        } else {
            alert("No se pueden agregar más personas");
        }
    });

    $('#quitar-campos').click(function () {
        identificador = $("#txtcantidadpersonas").val();
        if (identificador !== 0) {
            $('#contenedor'.concat(identificador)).remove();
            identificador--;
            console.log('identificador despues: ', identificador);
            $("#txtcantidadpersonas").val(identificador);
        }
    });

    function actualizarCamposMatricula() {
        var runt = $('#cmbrunt').val();
        if (runt === "N") {
            $('.matricula').hide();
        } else {
            $('.matricula').show();
            var paismatricula = $("#cmbpaismatricula").val();
            if (paismatricula === "18") {
                $('.matricula-pais').show();
                $("#ciudad-matricula").hide();
            } else {
                $('.matricula-pais').hide();
                $("#ciudad-matricula").show();
            }
        }
    }

    $('#cmbrunt').on('change focus', actualizarCamposMatricula);

    $('#cmbpaismatricula').on('change', actualizarCamposMatricula);

    function actualizarCamposImportacion() {
        var tipoImportacion = $('#cmbtiposimportacion').val();
        if (tipoImportacion === "0") {
            $('.importacion').hide();
        } else {
            $('.importacion').show();
        }
    }

    $('#cmbtiposimportacion').on('change focus', actualizarCamposImportacion);

    $('#btnvolver').click(function () {
        var id = $(this).data('id');
        console.log("id: " + id);
        window.location.href = "listarVehiculos.jsp";
    });
});