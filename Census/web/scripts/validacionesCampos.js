$(function () {
    $('.solo-letras').on('keypress', function (e) {
        var charCode = (e.which) ? e.which : e.keyCode;
        if (!((charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <= 122))) {
            e.preventDefault(); // Prevenir entrada si no es una letra
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
    
    
    // Evento permitir solo números
    $(".solo-numeros").on("keydown", function(event) {
        // Permitir solo números y teclas de control
        if (
            (event.keyCode < 48 || event.keyCode > 57) && // Números en teclado principal
            (event.keyCode < 96 || event.keyCode > 105) && // Números en teclado numérico
            event.keyCode !== 8 && // Backspace
            event.keyCode !== 9 && // Tab
            event.keyCode !== 37 && // Flecha izquierda
            event.keyCode !== 39 && // Flecha derecha
            event.keyCode !== 46 // Delete
        ) {
            event.preventDefault(); // Bloquear cualquier otra tecla
        }
    });
    
    var yaLimpiado = false;

    // Evento para limpiar el campo y permitir solo números
    $(".solo-numeros-censo").on("keydown", function(event) {
        // Si el campo no se ha limpiado aún, limpiarlo en la primera pulsación de tecla
        if (!yaLimpiado) {
            $(this).val(''); // Limpiar el campo de texto
            yaLimpiado = true; // Marcar que ya se ha limpiado
        }

        // Permitir solo números y teclas de control
        if (
            (event.keyCode < 48 || event.keyCode > 57) && // Números en teclado principal
            (event.keyCode < 96 || event.keyCode > 105) && // Números en teclado numérico
            event.keyCode !== 8 && // Backspace
            event.keyCode !== 9 && // Tab
            event.keyCode !== 37 && // Flecha izquierda
            event.keyCode !== 39 && // Flecha derecha
            event.keyCode !== 46 // Delete
        ) {
            event.preventDefault(); // Bloquear cualquier otra tecla
        }
    });

    // Evento para asegurarse de que solo hay números en caso de pegado o entrada no controlada
    $(".solo-numeros").on("input", function() {
        // Si el campo no se ha limpiado aún, limpiarlo en el primer cambio
        if (!yaLimpiado) {
            $(this).val(''); // Limpiar el campo de texto
            yaLimpiado = true; // Marcar que ya se ha limpiado
        }

        // Mantener solo los caracteres numéricos en el campo
        this.value = this.value.replace(/[^0-9]/g, ''); // Reemplazar cualquier carácter que no sea un número
    });

});