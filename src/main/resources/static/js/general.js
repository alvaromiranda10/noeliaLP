$(document).ready(function () {

    if (screen.width < 1025) {
        // NAV
        $('.hamburger').click(function () {
            if ($("body").css('overflow') === "hidden") {
                $("body").css('overflow', 'auto');
            } else {
                $("body").css('overflow', 'hidden');
            }
            this.classList.toggle('is-active');
            $('.menuMobile').animate({
                width: 'toggle'}, {
                duration: 150
            });
        });

        //MENU DESPLEGABLE
        $('nav .menuMobile > .childSubMenu .down').click(function () {
            this.offsetParent.classList.toggle('active');
            $(this.previousElementSibling).animate({
                height: 'toggle'}, {
                duration: 100
            });
        });

    }
});



//OBTIENE PARAMETRO DE LA URL
function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)");
    results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}


//FUNCION AJAX
function ajaxHeader(metodo, url) {
    if (window.XMLHttpRequest) {
        xhr = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        xhr = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if (xhr) {
        xhr.open(metodo, url);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    }
    return xhr;
}
