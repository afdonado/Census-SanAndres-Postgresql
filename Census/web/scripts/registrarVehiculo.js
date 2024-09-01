
$(function () {
    $('#txtplaca').blur(function () {
        console.log('placa: ', $('#txtplaca').val());
        var placa = $('#txtplaca').val().toString().toUpperCase();
        if (placa.length > 0) {
            verificarVehiculo(1, placa);
        }
    });

    $('#txtmotor').blur(function () {
        var motor = $('#txtmotor').val().toString().toUpperCase();
        if (motor.length > 0) {
            verificarVehiculo(2, motor);
        }
    });

    $('#txtchasis').blur(function () {
        var chasis = $('#txtchasis').val().toString().toUpperCase();
        if (chasis.length > 0) {
            verificarVehiculo(3, chasis);
        }
    });

    $('#txtserie').blur(function () {
        var serie = $('#txtserie').val().toString().toUpperCase();
        if (serie.length > 0) {
            verificarVehiculo(4, serie);
        }
    });

    // Verificar si la referencia de vehiculo existe o no
    function verificarVehiculo(tiporeferencia, valorreferencia) {
        var parametros = {
            tiporeferencia: tiporeferencia,
            valorreferencia: valorreferencia,
            opcion: 1
        };
        $.ajax({
            data: parametros,
            url: "../../verificarVehiculo",
            type: "POST",
            dataType: "json",
            success: function (response) {
                // Maneja la respuesta JSON
                if (response.status === "success") {
                    alert(response.message);
                } else if (response.status === "fail") {
                    $(response.input).val('');
                    $(response.input).focus();
                    alert(response.message);
                } else if (response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de verificar vehiculo: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de verificar vehiculo.");
            }
        });
    }

    $('#cmbdepartamentomatricula').change(function () {
        cargarMunicipiosByDepartamento($('#cmbdepartamentomatricula').val());
    });

    $('#cmbdepartamentomatricula').focus(function () {
        cargarMunicipiosByDepartamento($('#cmbdepartamentomatricula').val());
    });

    function cargarMunicipiosByDepartamento(iddepartamento) {
        $.ajax({
            data: {
                iddepartamento: iddepartamento
            },
            url: '../../cargarMunicipios',
            method: 'GET',
            success: function (data) {
                var select = $('#cmbmunicipiomatricula');
                select.empty();

                $.each(data, function (index, item) {
                    select.append('<option value="' + item.id + '">' + item.nombre + '</option>');
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de cargar municipios por departamento: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de cargar municipios por departamento.");
            }
        });
    }

    $.ajax({
        url: '../../cargarClasesVehiculo',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbclasevehiculo');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar clases vehiculo: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar clases de vehiculo.");
        }
    });

    $.ajax({
        url: '../../cargarTiposServicio',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbtiposservicio');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar tipos servicio: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar tipos de servicio.");
        }
    });


    $.ajax({
        url: '../../cargarTiposUso',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbtiposuso');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar tipos servicio: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar tipos de uso.");
        }
    });

    $.ajax({
        url: '../../cargarPaises',
        method: 'GET',
        success: function (data) {
            var selectMatricula = $('#cmbpaismatricula');
            selectMatricula.empty();

            $.each(data, function (index, item) {
                selectMatricula.append('<option value="' + item.id + '">' + item.nombre + '</option>');
            });
            selectMatricula.val(18);

            var selectImportacion = $('#cmbpaisimportacion');
            selectImportacion.empty();

            $.each(data, function (index, item) {
                selectImportacion.append('<option value="' + item.id + '">' + item.nombre + '</option>');
            });
            selectImportacion.val(18);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar paises: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar paises.");
        }
    });

    $.ajax({
        url: '../../cargarDepartamentos',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbdepartamentomatricula');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.nombre + '</option>');
            });

            select.val(4);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar departamentos: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar departamentos.");
        }
    });

    $.ajax({
        data: {
            iddepartamento: "4"
        },
        url: '../../cargarMunicipios',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbmunicipiomatricula');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.nombre + '</option>');
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar municipios: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar municipios.");
        }
    });

    $.ajax({
        url: '../../cargarTiposImportacion',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbtiposimportacion');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
            });

            select.val(1);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar tipos de importacion: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar tipos de importacion.");
        }
    });

    $.ajax({
        url: '../../cargarTiposReferencia',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbtiposreferencia');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar tipos de referencia: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar tipos de referencia.");
        }
    });

    $.ajax({
        url: '../../cargarTiposPersona',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbtipospersona1');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar tipos de persona: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar tipos de persona.");
        }
    });

    $.ajax({
        url: '../../cargarTiposDocumento',
        method: 'GET',
        success: function (data) {
            var select = $('#cmbtiposdocumento');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
            });
            
            var select = $('#cmbtiposdocumento1');
            select.empty();

            $.each(data, function (index, item) {
                select.append('<option value="' + item.id + '">' + item.descripcion + '</option>');
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("Error en la solicitud de cargar tipos de documentos: ", textStatus, errorThrown);
            alert("Ocurrió un error al procesar la solicitud de cargar tipos de documentos.");
        }
    });

    $('#btnguardar').click(function () {
        var motor = $('#txtmotor').val();
        var chasis = $('#txtchasis').val();
        var modelo = $('#txtmodelo').val();

        var runt = $('#cmbrunt').val();
        var licenciaTransito = $('#txtlicenciatransito').val();
        var paisMatricula = $('#cmbpaismatricula').val();
        var ciudadMatricula = $('#txtciudadmatricula').val();

        var tipoImportacion = $('#cmbtiposimportacion').val();
        var documentoImportacion = $('#txtdocumentoimportacion').val();

        var colores = $('#txtcolores').val();
        var marcas = $('#txtmarcas').val();
        var lineas = $('#txtlineas').val();

        if (motor.length === 0 && chasis.length === 0 && modelo.length === 0) {
            alert('Debe ingresar los datos obligatorios (*)');
            return false;
        }

        if (colores.length === 0 && marcas.length === 0 && lineas.length === 0) {
            alert('Debe ingresar los datos obligatorios (*)');
            return false;
        }

        if (runt === "S" && licenciaTransito.length === 0) {
            alert('Debe ingresar numero de Licencia de Transito');
            return false;
        }

        if (paisMatricula !== "18" && ciudadMatricula.length === 0) {
            alert('Debe ingresar los datos obligatorios (*)');
            return false;
        }

        if (tipoImportacion !== "0" && documentoImportacion.length === 0) {
            alert('Debe ingresar los datos obligatorios (*)');
            return false;
        }

        $('#frmregistrarvehiculo').trigger("submit");
    });

    $("#frmregistrarvehiculo").submit(function (event) {
        event.preventDefault();

        var parametros = $(this).serialize();

        $.ajax({
            data: parametros,
            url: "../../registrarVehiculo",
            type: "POST",
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    alert(response.message);
                    window.location.href = "verVehiculo.jsp?opcion=1&id=" + response.id;
                } else if (response.status === "fail") {
                    alert(response.message);
                } else if (response.status === "error") {
                    alert(response.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error en la solicitud de registrar vehiculo: ", textStatus, errorThrown);
                alert("Ocurrió un error al procesar la solicitud de registrar vehiculo.");
            }
        });
    });

});
/*
 function registrarVehiculo() {
 var motor = document.getElementById('txtmotor').value.toString().toUpperCase();
 var chasis = document.getElementById('txtchasis').value.toString().toUpperCase();
 var modelo = document.getElementById('txtmodelo').value;
 
 var runt = document.getElementById('cmbrunt').value;
 var lictransito = document.getElementById('txtlictransito').value.toString().toUpperCase();
 var paismatricula = document.getElementById('cmbpaismatricula').value;
 var ciudadmatri = document.getElementById("txtciudadmatri").value.toString().toUpperCase();
 var tipodocimportacion = document.getElementById('cmbtipodocimportacion').value;
 var docimportacion = document.getElementById("txtdocimportacion").value.toString().toUpperCase();
 
 var colores = document.getElementById('txtcolores').value.toString().toUpperCase();
 var marcas = document.getElementById('txtmarcas').value.toString().toUpperCase();
 var lineas = document.getElementById('txtlineas').value.toString().toUpperCase();
 
 if (colores.length == 0 && marcas.length == 0 && lineas.length == 0) {
 alert('Debe ingresar los datos obligatorios (*)');
 return false;
 }
 
 if (motor.length == 0 && chasis.length == 0 && modelo.length == 0) {
 alert('Debe ingresar los datos obligatorios (*)');
 return false;
 }
 
 if (runt == "S" && lictransito.length == 0) {
 alert('Debe ingresar numero de Licencia de Transito datos obligatorios (*)');
 return false;
 }
 
 if (paismatricula != "18" && ciudadmatri.length == 0) {
 alert('Debe ingresar los datos obligatorios (*)');
 return false;
 }
 
 if (tipodocimportacion != "0" && docimportacion.length == 0) {
 alert('Debe ingresar los datos obligatorios (*)');
 return false;
 }
 
 document.getElementById('frmregistrarvehiculo').submit();
 
 }
 
 function consultarRefVehiculo() {
 var tiporef = document.getElementById("cmbtiporeferencia").value;
 var valorreferencia = document.getElementById("txtreferencia").value.toString().toUpperCase();
 
 if (valorreferencia.length > 0) {
 ajax = new nuevoAjax();
 ajax.open("POST", "../Gets/getVerificarVehiculo.jsp", true);
 ajax.onreadystatechange = function () {
 if (ajax.readyState == 4 && ajax.status == 200) {
 var docxml = ajax.responseXML;
 var result = docxml.getElementsByTagName('result')[0].childNodes[0].nodeValue;
 if (result == 'si') {
 var idvehiculo = docxml.getElementsByTagName('idvehiculo')[0].childNodes[0].nodeValue;
 document.getElementById("idvehiculo").value = idvehiculo;
 document.getElementById("txtreferencia").style.background = "#81F781";
 } else {
 if (result == 'sicensado') {
 alert('Vehiculo ya fue censado');
 }
 if (result == 'noexiste') {
 alert('Vehiculo no registrado');
 }
 document.getElementById("idvehiculo").value = "";
 document.getElementById("txtreferencia").value = "";
 document.getElementById("txtreferencia").style.background = "#FE2E2E";
 }
 }
 }
 ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
 ajax.send("tiporef=" + tiporef + "&valorreferencia=" + valorreferencia + "&opcion=2");
 }
 }
 
 function viewModalRegVehiculo(tipoReferencia, referencia) {
 
 var src = "../Vehiculos/registrarVehiculo.jsp?opcion=2&tiporeferencia=" + tipoReferencia + "&referencia=" + referencia;
 $('#registrarvehiculo').modal('show');
 $('#registrarvehiculo iframe').attr('src', src);
 
 }
 
 function verificarVehiculoModificar(tiporef, nombrecampo) {
 var valorreferencia = document.getElementById(nombrecampo).value.toString().toUpperCase();
 var valorreferenciamod = document.getElementById(nombrecampo + "mod").value.toString().toUpperCase();
 
 if (valorreferencia != valorreferenciamod) {
 if (valorreferencia.length > 0) {
 ajax = new nuevoAjax();
 ajax.open("POST", "../Gets/getVerificarVehiculo.jsp", true);
 ajax.onreadystatechange = function () {
 if (ajax.readyState == 4 && ajax.status == 200) {
 var docxml = ajax.responseXML;
 var result = docxml.getElementsByTagName('result')[0].childNodes[0].nodeValue;
 if (result == 'si') {
 if (tiporef == '1') {
 alert("Placa ya se encuentra registrada");
 }
 if (tiporef == '2') {
 alert("Motor ya se encuentra registrado");
 }
 if (tiporef == '3') {
 alert("Chasis ya se encuentra registrado");
 }
 if (tiporef == '4') {
 alert("Serie ya se encuentra registrada");
 }
 document.getElementById(nombrecampo).value = '';
 document.getElementById(nombrecampo).focus();
 }
 }
 }
 ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
 ajax.send("tiporef=" + tiporef + "&valorreferencia=" + valorreferencia + "&opcion=1");
 }
 }
 }
 
 function modificarVehiculo() {
 if (confirm("Confirma Actualizar el vehiculo?")) {
 
 var motor = document.getElementById('txtmotor').value.toString().toUpperCase();
 var chasis = document.getElementById('txtchasis').value.toString().toUpperCase();
 var modelo = document.getElementById('txtmodelo').value;
 
 var runt = document.getElementById('cmbrunt').value;
 var lictransito = document.getElementById('txtlictransito').value.toString().toUpperCase();
 var paismatricula = document.getElementById('cmbpaismatricula').value;
 var ciudadmatri = document.getElementById("txtciudadmatri").value.toString().toUpperCase();
 var tipodocimportacion = document.getElementById('cmbtipodocimportacion').value;
 var docimportacion = document.getElementById("txtdocimportacion").value.toString().toUpperCase();
 
 var colores = document.getElementById('txtcolores').value.toString().toUpperCase();
 var marcas = document.getElementById('txtmarcas').value.toString().toUpperCase();
 var lineas = document.getElementById('txtlineas').value.toString().toUpperCase();
 
 if (colores.length == 0 && marcas.length == 0 && lineas.length == 0) {
 alert('Debe ingresar los datos de color, marca y linea son obligatorios (*)');
 return false;
 }
 
 if (motor.length == 0 && chasis.length == 0 && modelo.length == 0) {
 alert('Debe ingresar los datos de motor, chasis y modelo obligatorios (*)');
 return false;
 }
 
 if (runt == "S" && lictransito.length == 0) {
 alert('Debe ingresar numero de Licencia de Transito datos obligatorios (*)');
 return false;
 }
 
 if (paismatricula != "18" && ciudadmatri.length == 0) {
 alert('Debe ingresar los datos de pais y ciudad de matricula obligatorios (*)');
 return false;
 }
 
 if (tipodocimportacion != "0" && docimportacion.length == 0) {
 alert('Debe ingresar los datos de importacion obligatorios (*)');
 return false;
 }
 
 document.getElementById('frmmodificarvehiculo').submit();
 }
 
 }
 
 function generarReporteVehiculo(opcion) {
 
 ancho = 200;
 alto = 140;
 barra = 0;
 izquierda = (screen.width) ? (screen.width - ancho) / 2 : 100;
 arriba = (screen.height) ? (screen.height - alto) / 2 : 100;
 opciones = 'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=' + barra + ',resizable=no,width=' + ancho + ',height=' + alto + ',left=' + izquierda + ',top=' + arriba;
 switch (opcion) {
 case 1:
 case 1 :
 var tiporef = document.getElementById("cmbtiporeferencia").value;
 var referencia = document.getElementById("txtreferencia").value.toString().toUpperCase();
 window.frames[0].location.href = "generarReporteVehiculos.jsp?opcion=1&tiporef=" + tiporef + "&referencia=" + referencia;
 break;
 
 case 2:
 var tipodocumento = document.getElementById('cmbtipodoc').value;
 var documento = document.getElementById('txtdocumento').value.toString().toUpperCase();
 window.frames[0].location.href = "generarReporteVehiculos.jsp?opcion=2&tipodocumento=" + tipodocumento + "&documento=" + documento;
 break;
 
 case 3:
 var fechaRegini = document.getElementById("txtfechaRegini").value.toString();
 var fechaRegfin = document.getElementById("txtfechaRegfin").value.toString();
 window.frames[0].location.href = "generarReporteVehiculos.jsp?opcion=3&fechaRegini=" + fechaRegini + "&fechaRegfin=" + fechaRegfin;
 break;
 
 case 4:
 url = "generarReporteVehiculos.jsp?opcion=4";
 break;
 }
 
 window.open(url, 'popUp', opciones);
 
 }
 
 function consultarRefVehiculoModificar() {
 var tiporefnew = document.getElementById("cmbtiporeferencia").value;
 var valorreferencianew = document.getElementById("txtreferencia").value.toString().toUpperCase();
 
 var tiporef = document.getElementById("tiporeferencia").value;
 var valorreferencia = document.getElementById("referencia").value.toString().toUpperCase();
 
 if (valorreferencia.length > 0) {
 if (tiporefnew != tiporef || valorreferencianew != valorreferencia) {
 ajax = new nuevoAjax();
 ajax.open("POST", "../Gets/getVerificarVehiculo.jsp", true);
 ajax.onreadystatechange = function () {
 if (ajax.readyState == 4 && ajax.status == 200) {
 var docxml = ajax.responseXML;
 var result = docxml.getElementsByTagName('result')[0].childNodes[0].nodeValue;
 if (result == 'si') {
 var idvehiculo = docxml.getElementsByTagName('idvehiculo')[0].childNodes[0].nodeValue;
 document.getElementById("idvehiculo").value = idvehiculo;
 document.getElementById("txtreferencia").style.background = "#81F781";
 } else {
 if (result == 'sicensado') {
 alert('Vehiculo ya fue censado');
 }
 if (result == 'noexiste') {
 alert('Vehiculo no registrado');
 }
 document.getElementById("idvehiculo").value = "";
 document.getElementById("txtreferencia").value = "";
 document.getElementById("txtreferencia").style.background = "#FE2E2E";
 }
 }
 }
 ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
 ajax.send("tiporef=" + tiporefnew + "&valorreferencia=" + valorreferencianew + "&opcion=2");
 }
 }
 }
 * 
 */