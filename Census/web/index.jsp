<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        
        <title>Inicio Sesion - Census</title>
        
        <!-- Custom fonts for this template-->
        <link href="template/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="template/css/fonts-google.css" rel="stylesheet" type="text/css"/>

        <!-- Custom styles for this template-->
        <link href="template/css/sb-admin-2.min.css" rel="stylesheet">

        
    </head>
    <body>
        <div class="container">

            <div class="row justify-content-center">

                <div class="col-xl-10 col-lg-12 col-md-9">

                    <div class="card o-hidden border-0 shadow-lg my-5">
                        <div class="card-body p-0">
                            <div class="row">
                                <div class="d-lg-none d-md-block">
                                    <img src="template/logo/movisai-logo.jpg" alt="MOVISAI" style="margin-top: 60px; width: 280px; height: 92px">
                                </div>
                                <div class="col-lg-6 d-none d-lg-block bg-login-image">
                                    <img src="template/logo/movisai-logo.jpg" alt="MOVISAI" style="margin-top: 100px; margin-left: 20px; width: 400px; height: 120px;">
                                </div>
                                <div class="col-lg-6">
                                    <div class="p-5">
                                        <div class="text-center">
                                            <h1 class="h4 text-gray-900 mb-4">Census</h1>
                                        </div>
                                        <form class="user" id="frminiciosesion" method="post">
                                            <div class="form-group">
                                                <input class="form-control form-control-user" type="text" id="txtloginusuario" name="txtloginusuario" placeholder="Nombre de usuario" style="text-transform: uppercase" autofocus>
                                            </div>
                                            <div class="form-group">
                                                <input class="form-control form-control-user" type="password"  id="txtloginpassword" name="txtloginpassword" placeholder="CONTRASEÑA">
                                            </div>
                                            <button type="button" class="btn btn-primary btn-user btn-block" name="btniniciar" id="btniniciar">Iniciar</button>
                                            <hr>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                </div>
                
            </div>
            
        </div>
        
        <!-- Bootstrap core JavaScript-->
        <script src="template/vendor/jquery/jquery.min.js"></script>
        <script src="template/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="template/vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="template/js/sb-admin-2.min.js"></script>
        
        <script src="scripts/iniciosesion.js" type="text/javascript"></script>
    </body>
</html>
