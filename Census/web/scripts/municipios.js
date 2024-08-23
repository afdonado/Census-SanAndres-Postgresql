
function consultarMunicipiosByDepto(namecmbdepartamento, namecmbmunicipio) {
    
    combo = document.getElementById(namecmbmunicipio);
    iddep = document.getElementById(namecmbdepartamento).options[document.getElementById(namecmbdepartamento).selectedIndex].value;
    ajax = new nuevoAjax();
    ajax.open("POST", "../Gets/getMunicipios.jsp", true);
    ajax.onreadystatechange = function () {
        if (ajax.readyState == 4) {
            combo.innerHTML = ajax.responseText;
        }
    }
    ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    ajax.send("iddep=" + iddep + "&nameCombo=" + combo);
}