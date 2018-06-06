window.onload = function () {
    if (getParameterByName("producto") !== "" && getParameterByName("cantidad") !== "" && getParameterByName("color") !== "" && getParameterByName("talle") !== "") {
        sessionStorage.setItem("producto", getParameterByName("producto"));
        sessionStorage.setItem("cantidad", getParameterByName("cantidad"));
        sessionStorage.setItem("color", getParameterByName("color"));
        sessionStorage.setItem("talle", getParameterByName("talle"));
        location = "./inicio";
    }
};

//SELECTOR
var btnIngresar = document.querySelector("#ingresar");

//FUNCION
function ingresar() {
    if (sessionStorage.getItem("producto") !== null && sessionStorage.getItem("cantidad") !== null && sessionStorage.getItem("color") !== null && sessionStorage.getItem("talle") !== null) {
        document.querySelector("input[name=producto]").value = sessionStorage.getItem("producto");
        document.querySelector("input[name=cantidad]").value = sessionStorage.getItem("cantidad");
        document.querySelector("input[name=color]").value = sessionStorage.getItem("color");
        document.querySelector("input[name=talle]").value = sessionStorage.getItem("talle");

        document.getElementById("ingreso").submit();
    } else {
        document.getElementById("ingreso").submit();
    }
};

//EVENTO
btnIngresar.addEventListener("click", ingresar, false);
    
