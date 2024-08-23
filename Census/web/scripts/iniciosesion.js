
window.addEventListener('load', function(){
        document.getElementById('btniniciar').addEventListener('click', function(){
            
            var usuario = document.getElementById('txtloginusuario').value.toString().toUpperCase();
            var password = document.getElementById('txtloginpassword').value;
            
            if(usuario.length > 0 && password.length >0){
                if(usuario.length>0 && password.length >5){
                    document.getElementById('frminiciosesion').submit();
                }else{
                    alert('Debe ingresar un usuario y contraseña validos');
                }
            }else{
                alert('Debe ingresar su usuario y contraseña');
            }
    });
});