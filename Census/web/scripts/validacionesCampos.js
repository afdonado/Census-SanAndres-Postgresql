
var r = {'special': /[^A-Za-z1234567890\s\t]/g,
    'nombre': /[´ªº¨]|['¡¿?!1234567890]|[^A-Za-z'áéíóúÁÉÍÓÚàèìòùÀÈÌÒÙâêîôûÂÊÎÔÛÑñäëïöüÄËÏÖÜ\s\t]/g}

function valida_caracteres(o, w) {
    o.value = o.value.replace(r[w], '');
}


function validarLetras(e) {
    tecla = (document.all) ? e.keyCode : e.which;

    if (tecla == 8)
        return true; // backspace
    if (e.keyCode == 9)
        return true; // tabulation
    if (tecla == 32)
        return true; // espacio
    if (e.ctrlKey && tecla == 86) {
        return true;
    } //Ctrl
    if (e.ctrlKey && tecla == 67) {
        return true;
    } //Ctrl
    if (e.ctrlKey && tecla == 88) {
        return true;
    } //Ctrl

    patron = /[a-zA-Z]/; //patron
    te = String.fromCharCode(tecla);
    return patron.test(te); // prueba de patron
}


function validarNumeros(e) { // 1
    var key = window.Event ? e.which : e.keyCode
    return ((key >= 48 && key <= 57) || (key == 8) || (e.keyCode == 9))

}


function validateMail(txtcorreo)
{
    var campo = document.getElementById(txtcorreo).value;
    if (campo.length > 0) {
        //Creamos un objeto
        object = document.getElementById(txtcorreo);
        valueForm = object.value;

        // Patron para el correo
        var patron = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,4})+$/;
        if (valueForm.search(patron) == 0)
        {
            //Mail correcto
            object.style.color = "#000";
            return;
        }
        //Mail incorrecto
        object.style.color = "#f00";
        alert("Digite un correo valido");
        document.getElementById("txtmail").value = "";
    }
}