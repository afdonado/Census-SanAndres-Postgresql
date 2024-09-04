
$(function () {

    function verificarNumeroCenso(opcion, numero) {
        $.ajax({
            data: {
                numero: numero
            },
            url: "../../verificarNumeroCenso",
            type: "POST",
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    alert(response.message);
                } else if (response.status === "fail") {
                    if (opcion === 1) {
                        window.location.href = "listarDocumentos.jsp?idcenso=" + response.id + "&numero=" + numero;
                    } else if (opcion === 2) {
                        window.location.href = "seleccionarDocumentos.jsp?idcenso=" + response.id + "&numero=" + numero;
                    } else {
                        importarDocumentosWeb(response.id, numero);
                    }
                } else if (response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de verificar numero de censo: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de verificar numero de censo.");
            }
        });
    }

    function importarDocumentosWeb(id, numero) {
        $.ajax({
            data: {
                idcenso: id,
                numero: numero
            },
            url: "../../verificarNumeroCenso",
            type: "POST",
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    alert(response.message);
                } else if (response.status === "fail") {
                    window.location.href = "listarDocumentos.jsp?idcenso=" + response.id;
                } else if (response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de importar documentos web: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de importar documentos web.");
            }
        });
    }

    $('#txtnumerocenso').blur(function () {
        var numero = $('#txtnumerocenso').val();
        if (numero.length > 0 && numero.length < 6) {
            var prefijo = "ACS";
            numero = prefijo + ("00000".substring(0, 5 - numero.length)) + numero;
            console.log('numero:', numero);
            verificarNumeroCenso(1, $('#txtnumerocenso').val());
        }
    });

    $('#txtnumerocensocargar').blur(function () {
        var numero = $('#txtnumerocensocargar').val();
        if (numero.length > 0 && numero.length < 6) {
            var prefijo = "ACS";
            numero = prefijo + ("00000".substring(0, 5 - numero.length)) + numero;
            console.log('numero:', numero);
            verificarNumeroCenso(2, $('#txtnumerocensocargar').val());
        }
    });

    $('#txtnumerocensoimportar').blur(function () {
        var numero = $('#txtnumerocensoimportar').val();
        if (numero.length > 0 && numero.length < 6) {
            var prefijo = "ACS";
            numero = prefijo + ("00000".substring(0, 5 - numero.length)) + numero;
            console.log('numero:', numero);
            consultarNumeroCenso(3, $('#txtnumerocensoimportar').val());
        }
    });

});

function checkSubmit(id, mensaje) { // Con esta funcion se deshabilita el boton tipo submit y cambia el value para dar la impresion de que se esta procesando la informacion
    document.getElementById(id).value = mensaje;
    document.getElementById(id).disabled = true;
    return true;
}


function ValidarImagenes(id) {
    var imagen = document.getElementById(id).files;

    if (imagen.length === 0) {
        alert("La subida de imagenes es requerida");
        document.getElementById(id).value = "";
        return false;
    } else {
        for (x = 0; x < imagen.length; x++) {
            if (imagen[x].type !== "image/png" && imagen[x].type !== "image/jpg" && imagen[x].type !== "image/jpeg" && imagen[x].type !== "application/pdf") {
                alert("El archivo " + imagen[x].name + " Presenta Una Extension Invalida");
                document.getElementById(id).value = "";
                return false;
            }
            var mb = 1048576; //Cantidad de Bytes en 1 Megabyte
            var final = imagen[x].size / mb; //Tamaño del archivo en Megabytes
            if (final > 25) {
                alert("La imagen " + imagen[x].name + " supera el tamaño máximo permitido 25MB");
                document.getElementById(id).value = "";
                return false;
            }
        }
        return true;
    }
}



function agregarArchivos() {
    document.getElementById('agregar').style.display = 'block';
}

validateFileExtension = function (evt) {
    var file;
    evt = evento(evt);
    nCampo = rObj(evt);
    div = document.getElementById(nCampo.id);
    extensiones_permitidas = new Array(".jpg", ".png", ".doc", ".pdf", ".zip", ".rar", ".mp3", ".mp4", ".xls", ".xlsx", ".pdf", ".txt", ".docx", ".ppt", ".pps");
    file = div.files[0];
    if (file.size > 26214400) {

        alert("El Archivo " + file.name + " supera el tamaño máximo permitido 25MB");
        div.value = "";
    } else {


        if (!/(\.jpg|\.pdf|\.png|\.doc |\.pdf |\.zip|\.rar|\.mp3|\.mp4|\.xls|\.txt|\.docx|\.ppt|\.pps)$/i.test(div.value)) {

            alert("Comprueba la extensión de los archivos a subir. \nSólo se pueden subir archivos con extensiones: " + extensiones_permitidas.join());
            div.value = "";
            div.focus();
        }
    }
}

var numero = 0;
agregar_Archivo = function () {// Funcion para adicionar mas input type file
    if (numero < 4) { // Condicion para validar la cantidad de input type file que se pueden agregar
        numero = numero + 1;
        nDiv = document.createElement('div'); // Con esta linea se crea el div que contiene los input type file que se van adicionando

        nDiv.className = 'archivo'; // Con esta linea se establece el atributo name al input type file que se va adicionando

        nDiv.id = 'file' + (numero); // Con esta linea se establece el atributo id al input type file que se va adicionando

        nCampo = document.createElement('input'); // Con esta linea se crea el input

        nCampo.name = 'archivo' + (numero); // Con esta linea se establece un name que varia cuando se van adicionando los input type file
        nCampo.id = 'archivo' + (numero); // Con esta linea se establece un id que varia cuando se van adicionando los input type file
        nCampo.type = 'file'; // Con esta linea establecemos el tipo de input que vamos adicionando
        nCampo.multiple = 'multiple'; // Con esta linea establecemos la propiedad multiple para que se pueden seleccionar multiples archivos
        nCampo.onchange = validateFileExtension; // Con esta funcion establecemos el evento onchange al input type file que vamos adicionando y le asignamos una funcion

        a = document.createElement('a'); // Con esta linea creamos el link para eliminar mar archivos

        a.name = nDiv.id; // Con esta linea le asignamos un id al link que nos permite eliminar mas input type file

        a.href = '#'; // Esta linea solo mustra la propiedad href del link

        a.onclick = elimCamp; // Con esta linea le asignamos el evento onclick acompañada de la funcion que elimina los input type file

        a.innerHTML = 'Quitar Archivo'; // Con esta linea establecemos el texto del link

        nDiv.appendChild(nCampo);
        nDiv.appendChild(a);
        container = document.getElementById('contenido_archivos');
        container.appendChild(nDiv);
    } else {
        alert("No se pueden agregar mas Archivos");
    }
}

function consultarCensoDocumento() {
    var numero = document.getElementById("txtnumerocenso").value.toString().toUpperCase();
    window.frames[0].location.href = "SeleccionarDocumentos.jsp?numero=" + numero;
}

/*
 function consultarDocumentosDigitalizadosByNumeroCenso() {
 var numerocenso = document.getElementById('txtnumerocenso').value.toString().toUpperCase();
 
 if (numerocenso.length > 0) {
 ajax = new nuevoAjax();
 ajax.open("POST", "../Gets/getVerificarNumeroCenso.jsp", true);
 ajax.onreadystatechange = function () {
 if (ajax.readyState == 4 && ajax.status == 200) {
 var docxml = ajax.responseXML;
 var result = docxml.getElementsByTagName('result')[0].childNodes[0].nodeValue;
 if (result == 'si') {
 var idcenso = docxml.getElementsByTagName('idcenso')[0].childNodes[0].nodeValue;
 window.frames[0].location.href = "ListarDocumentos.jsp?idcenso=" + idcenso;
 } else {
 document.getElementById('txtnumerocenso').focus();
 alert("Numero de censo no se encuentra registrado");
 }
 }
 }
 ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
 ajax.send("numerocenso=" + numerocenso);
 }
 
 }
 */
function DescargarDocumento() {
    document.getElementById('frmdescargardocumento').action = '../../descargarDocumento';
    document.getElementById('frmdescargardocumento').submit();
}


function consultarImagenesWeb() {
    var numerocenso = document.getElementById('txtnumerocenso').value.toString().toUpperCase();

    if (numerocenso.length > 0) {
        ajax = new nuevoAjax();
        ajax.open("POST", "../Gets/getVerificarNumeroCenso.jsp", true);
        ajax.onreadystatechange = function () {
            if (ajax.readyState == 4 && ajax.status == 200) {
                var docxml = ajax.responseXML;
                var result = docxml.getElementsByTagName('result')[0].childNodes[0].nodeValue;
                if (result == 'si') {
                    var idcenso = docxml.getElementsByTagName('idcenso')[0].childNodes[0].nodeValue;
                    document.getElementById('idcenso').value = idcenso;
                    document.getElementById('frmconsultardocumentosweb').action = '../../importarDocumentos';
                    //document.getElementById('frmconsultardocumentosweb').action = '../../importarFotosMasivaVeh';
                    document.getElementById('frmconsultardocumentosweb').submit();
                } else {
                    document.getElementById('txtnumerocenso').focus();
                    alert("Numero de censo no se encuentra registrado");
                }
            }
        }
        ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        ajax.send("numerocenso=" + numerocenso);
    }

}

function consultarImagenesWeb2() {
    document.getElementById('frmconsultardocumentosweb').action = '../../consultarDocumento';
    document.getElementById('frmconsultardocumentosweb').submit();
}