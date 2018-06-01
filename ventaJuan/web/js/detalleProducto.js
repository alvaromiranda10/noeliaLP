$(document).ready(function () {
//    MOBILE
//MODAL
    var modal = document.getElementById('myModal');
    var modalImg = document.getElementById("img01");
    $("#slider img").click(function () {
        document.querySelector(".contenedor").style.display = "none";
        modal.style.display = "block";
        modalImg.src = this.src;
    });
    //BOTON CLOSE
    var span = document.getElementsByClassName("close")[0];
    span.addEventListener("click", function () {
        document.querySelector(".contenedor").style.display = "grid";
        modal.style.display = "none";
    }, false);
    //MODAL FIN
    new Swiper('#slider .swiper-container', {
        pagination: {
            el: '#slider .swiper-pagination',
            clickable: true,
            renderBullet: function (index, className) {
                return '<span class="' + className + '">' + (index + 1) + '</span>';
            }
        }
    });
//                FIN MOBILE

//PC
    new Swiper('.Thumbs', {
        direction: 'vertical',
        slidesPerView: 'auto',
        spaceBetween: 15,
        autoHeight: true,
        freeMode: true,
        freeModeSticky: true,
        grabCursor: true,
        pagination: {
            el: '.Thumbs .swiper-pagination',
            clickable: true
        }
    });

    $('#ex1').zoom();

    $(".Thumbs .swiper-slide").on("click", function () {
        $(".img-principal").attr("src", $(this.children[0]).attr('src'));
        $('#ex1').zoom().trigger('zoom.destroy');
        $(".zoomImg").attr("src", $(this.children[0]).attr('src'));
        $('#ex1').zoom();
    });

//FIN PC    

    recuperarProductoId();
    recuperarProductoIdMobile();
    
    
    $(".descripcionProducto h3").click(function(){
        this.classList.toggle('active');
        $(this.nextElementSibling).animate({
                height: 'toggle'}, {
                duration: 100
            });
    });
});

function recuperarProductoIdMobile() {

    let radio = document.querySelector(".colores input[type=radio]:first-child");
    radio.checked = true;
    let hijos = document.querySelectorAll("#slider > .swiper-container:not(:first-of-type)");
    for (let i = 0; i < hijos.length; i++) {
        hijos[i].style.display = "none";
    }
}

function recuperarProductoId() {

    let radio = document.querySelector(".colores input[type=radio]:first-child");
    radio.checked = true;

    let hijos = document.querySelectorAll("#zoom .swiper-wrapper > .swiper-slide");
    let color = document.querySelector(".colores  label");
    for (let i = 0; i < hijos.length; i++) {
        if (color.htmlFor !== hijos[i].dataset.color) {
            hijos[i].style.display = "none";

        }
    }

}


function habilitarCantidad(number) {
    let select = document.querySelector(".cantidad select");
    let output = "";
    output += `<option  selected disabled>0</option>`;
    for (let i = 1; i <= number; i++) {
        output += `<option >${i}</option>`;
    }
    select.innerHTML = output;

}

function cambiarFoto(e) {
    
    if($( window ).width() >1023){
        
    let imagenes = document.querySelectorAll(".swiper-wrapper > .swiper-slide");
    for (let i = 0; i < imagenes.length; i++) {
        if (e.htmlFor === imagenes[i].dataset.color) {
            imagenes[i].style.display = "initial";
        } else {
            imagenes[i].style.display = "none";
        }
    }
    let stock = e.dataset.stock.split(",");
    let talles = document.querySelectorAll(".talles li > label");

    var ele = document.querySelector(".talles input:checked");
    if (ele) {
        ele.checked = false;
    }

    for (let i = 0; i < talles.length; i++) {
        talles[i].setAttribute("onclick", `habilitarCantidad(${stock[i]})`);
    }
    let select = document.querySelector(".cantidad select");
    let output = `<option  selected disabled>0</option>`;
    select.innerHTML = output;
    }else{
        
        let imagenes = document.querySelectorAll("#slider > .swiper-container");
        for (let i = 0; i < imagenes.length; i++) {
            if (e.htmlFor === imagenes[i].dataset.color) {
                imagenes[i].style.display = "inherit";
            } else {
                imagenes[i].style.display = "none";
            }
        }

        let stock = e.dataset.stock.split(",");
        let talles = document.querySelectorAll(".talles li > label");

        var ele = document.querySelector(".talles input:checked");
        if (ele) {
            ele.checked = false;
        }
        for (let i = 0; i < talles.length; i++) {
            talles[i].setAttribute("onclick", `habilitarCantidad(${stock[i]})`);
        }
        let select = document.querySelector(".cantidad select");
        let output = `<option  selected disabled>0</option>`;
        select.innerHTML = output;
    }
    

}

function comprar() {
    let idProducto =getParameterByName("producto");
    let color = document.querySelector(".colores input:checked").value;
    let talle = document.querySelector(".talles input:checked");
    let cantidad = document.querySelector(".cantidad select ");
    let cantidadSeleccionada = cantidad.options[cantidad.selectedIndex].value;
    if(idProducto === null || color === null || talle === null || cantidadSeleccionada < 1){
        
        alert("complete el formulario corectamente");
    }else{
        document.querySelector("input[name='idProducto']").value = idProducto;
        document.getElementById("detallesProducto").submit();
    }
  
}

