$(function () {
    $('input[type="number"]').on('keypress', function (e) {
        var charCode = (e.which) ? e.which : e.keyCode;
        if (charCode < 48 || charCode > 57) {
            e.preventDefault(); // Prevenir la entrada si no es un número
        }
    });

    $('.solo-letras').on('keypress', function (e) {
        var charCode = (e.which) ? e.which : e.keyCode;
        if (!((charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <= 122))) {
            e.preventDefault(); // Prevenir entrada si no es una letra
        }
    });

    $('.email-class').each(function () {
        var $campo = $(this); // Hace referencia al campo actual
        var valueForm = $campo.val(); // Obtiene el valor del campo

        if (valueForm.length > 0) {
            // Patrón para el correo
            var patron = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,4})+$/;

            if (patron.test(valueForm)) { // Comprueba si el valor cumple con el patrón
                // Mail correcto
                $campo.css('color', '#000');
            } else {
                // Mail incorrecto
                $campo.css('color', '#f00');
                alert("Digite un correo válido");
                $campo.val(''); // Limpia el campo
            }
        }
    });

});