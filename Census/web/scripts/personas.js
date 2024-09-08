
function consultarDocumentoPersona(tipodocumento, documento) {
    if (documento.length > 0) {
        $.ajax({
            type: "POST",
            url: "../../verificarDocumentoPersona",
            data: {
                tipodocumento: tipodocumento,
                documento: documento
            },
            success: function (response) {

                if (response.status === "success") {
                    console.log("success", response.message);
                } else if (response.status === "fail") {
                    $('#txtdocumento').val('');
                } else if (response.status === "error") {
                    alert(response.message);
                    $('#txtdocumento').val('');
                }
            },
            error: function (xhr, status, error) {
                console.error("Error en la solicitud consultar Documento Persona: " + error);
            }
        });
    }
}

function generarReportePersona(opcion) {

    ancho = 200;
    alto = 140;
    barra = 0;
    izquierda = (screen.width) ? (screen.width - ancho) / 2 : 100;
    arriba = (screen.height) ? (screen.height - alto) / 2 : 100;
    opciones = 'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=' + barra + ',resizable=no,width=' + ancho + ',height=' + alto + ',left=' + izquierda + ',top=' + arriba;
    switch (opcion) {
        case 0:
            var fechanacini = document.getElementById("txtfechanacini").value.toString();
            var fechanacfin = document.getElementById("txtfechanacfin").value.toString();
            url = "generarReportePersonas.jsp?opcion=3&fechanacini=" + fechanacini + "&fechanacfin=" + fechanacfin;
            break;

        case 1:
            url = "generarReportePersonas.jsp?opcion=4";
            break;
    }

    window.open(url, 'popUp', opciones);

}