function registrarVerificacion() {
    var idcenso = document.getElementById('idcenso').value;
    if (idcenso.length) {
        document.getElementById('frmregistrarverificacion').submit();
    } else {
        alert('Debe ingresar el numero del censo');
    }
}

function modificarVerificacion() {
    var idcenso = document.getElementById('idcenso').value;
    if (idcenso.length) {
        document.getElementById('frmmodificarverificacion').submit();
    } else {
        alert('Debe ingresar el numero del censo');
    }
}

function verificar(){
    var idcenso = document.getElementById('idcenso').value;
    var numerocenso = document.getElementById('txtnumero').value.toString().toUpperCase();
    viewModalRegVerificacion(idcenso, numerocenso);
}

function viewModalRegVerificacion(idcenso, numerocenso) {

    var src = "../Verificaciones/registrarVerificacion.jsp?idcenso=" + idcenso + "&numerocenso=" + numerocenso;
    $('#registrarverificacion').modal('show');
    $('#registrarverificacion iframe').attr('src', src);

}

function consultarListadoVerificacion(opcion) {
    
    var fechaini;
    var fechafin;
    var punto;
            
    switch (opcion) {
        case 1:
            fechaini = document.getElementById("txtfechainicenso").value.toString();
            fechafin = document.getElementById("txtfechafincenso").value.toString();
            punto = document.getElementById('cmbpuntoatencenso').value;
            window.frames[0].location.href = "listarVerificaciones.jsp?opcion="+opcion+"&fechaini=" + fechaini + "&fechafin=" + fechafin + "&punto="+punto;
            break;

        case 2:
            fechaini = document.getElementById("txtfechainiregistro").value.toString();
            fechafin = document.getElementById("txtfechafinregistro").value.toString();
            punto = document.getElementById('cmbpuntoatenregistro').value;
            window.frames[0].location.href = "listarVerificaciones.jsp?opcion="+opcion+"&fechaini=" + fechaini + "&fechafin=" + fechafin + "&punto="+punto;
            break;
            
        case 3:
            var numero = document.getElementById("txtnumero").value.toString().toUpperCase();
            window.frames[0].location.href = "listarVerificaciones.jsp?opcion="+opcion+"&numero=" + numero;
            break;
    }
}

function generarReporteVerificacion(opcion) {
    
    var fechaini;
    var fechafin;
    var punto;
            
    switch (opcion) {
        case 1:
            fechaini = document.getElementById("txtfechainicenso").value.toString();
            fechafin = document.getElementById("txtfechafincenso").value.toString();
            punto = document.getElementById('cmbpuntoatencenso').value;
            window.frames[0].location.href = "generarReporteVerificacion.jsp?opcion="+opcion+"&fechaini=" + fechaini + "&fechafin=" + fechafin + "&punto="+punto;
            break;

        case 2:
            fechaini = document.getElementById("txtfechainiregistro").value.toString();
            fechafin = document.getElementById("txtfechafinregistro").value.toString();
            punto = document.getElementById('cmbpuntoatenregistro').value;
            window.frames[0].location.href = "generarReporteVerificacion.jsp?opcion="+opcion+"&fechaini=" + fechaini + "&fechafin=" + fechafin + "&punto="+punto;
            break;
        case 3:
            var numero = document.getElementById("txtnumero").value.toString().toUpperCase();
            window.frames[0].location.href = "generarReporteVerificacion.jsp?opcion="+opcion+"&numero=" + numero;
            break;
    }
}

function consultarRegistroById(idcenso){
    document.location.href = "../Verificaciones/verRegistro.jsp?idcenso=" + idcenso;
}