$(function () {
    
    $('.solo-letras').on('keypress', function (e) {
    var charCode = (e.which) ? e.which : e.keyCode;
    // Permitir letras mayúsculas, minúsculas, la ñ (mayúscula y minúscula), y espacios
    if (!((charCode >= 65 && charCode <= 90) || 
          (charCode >= 97 && charCode <= 122) || 
          charCode === 241 || // ñ minúscula
          charCode === 209 || // Ñ mayúscula
          charCode === 32)) { // espacio
        e.preventDefault(); // Prevenir entrada si no es válido
    }
});


    $('.validar-email').on('blur change', function () {
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
                alert("Correo electronico no valido");
                $campo.val(''); // Limpia el campo
            }
        }
    });

    $('.solo-numeros').on('input', function () {
        var value = $(this).val();

        // Validar que el valor solo contenga números
        if (!/^\d*$/.test(value)) {
            // Si hay letras, se limpia el campo
            $(this).val('');
        }
    });

});