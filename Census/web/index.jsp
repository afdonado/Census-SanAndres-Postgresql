<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Inicio Sesion - Census</title>
        
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="dist/css/sb-admin-2.css" rel="stylesheet">
        
        <script src="scripts/iniciosesion.js" type="text/javascript"></script>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <center>
                    <img src="vendor/imagenes/lagit.jpeg" alt="Lagit" class="visible-xs" style="margin-top: 60px; width: 270px; height: 92px">
                    <img src="vendor/imagenes/lagit.jpeg" alt="Lagit" class="visible-sm" style="margin-top: 60px; width: 590px; height: 180px">
                    <img src="vendor/imagenes/lagit.jpeg" alt="Lagit" class="visible-md" style="margin-top: 60px; width: 280px; height: 92px">
                    <img src="vendor/imagenes/lagit.jpeg" alt="Lagit" class="visible-lg" style="margin-top: 100px; margin-left: 20px; width: 400px; height: 120px;">
                    <h2 class="title">Census</h2>
                </center>

                <div class="col-md-4 col-md-offset-4">
                    <div class="login-panel panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Inicio de Sesion</h3>
                        </div>
                        <div class="panel-body">
                            <form role="form" id="frminiciosesion" action="inicioSesion">
                                <fieldset>
                                    <div class="form-group">
                                        <input class="form-control" type="text" id="txtloginusuario" name="txtloginusuario" placeholder="Usuario" style="text-transform: uppercase" autofocus>
                                    </div>
                                    <div class="form-group">
                                        <input class="form-control" type="password"  id="txtloginpassword" name="txtloginpassword" placeholder="Contraseña">
                                    </div>
                                    <button type="button" class="btn btn-lg btn-success btn-block" name="btniniciar" id="btniniciar">Iniciar</button>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
