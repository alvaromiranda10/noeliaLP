function eliminarCompra(e) {
    let xhr = ajaxHeader("DELETE", "./carrito?idCompra=" + e);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            if (xhr.responseText === "ok") {
                window.location = "./carrito";
            }
        }
    };
    xhr.send();
}

window.onload = function () {

    sessionStorage.removeItem("producto");
    sessionStorage.removeItem("cantidad");
    sessionStorage.removeItem("color");
    sessionStorage.removeItem("talle");
};