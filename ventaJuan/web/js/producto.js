$(document).ready(function () {
    
    $('#filtro ul > li >  a').click(function (event) {
        event.preventDefault();
            this.parentElement.classList.toggle('active');
            $(this.nextElementSibling).animate({
                height: 'toggle'}, {
                duration: 150
            });
    });
    
$('#filtro h3').click(function () {
            this.classList.toggle('active');
            $(this.nextElementSibling).animate({
                height: 'toggle'}, {
                duration: 150
            });
    });
        
//SWIPER 
        new Swiper('.slider-img .container-article .swiper-container', {
            pagination: {
                el: '.slider-img .container-article .swiper-container .swiper-pagination',
                dynamicBullets: true
            }
        });
//SWIPER FIN

//MODAL
        var modal = document.getElementById('myModal');
        var modalImg = document.getElementById("img01");
        $("#galeria .row2 .slider-img img").click(function () {
            document.querySelector(".contenedor").style.display = "none";
            modal.style.display = "block";
            modalImg.src = this.src;
        });

        var span = document.getElementsByClassName("close")[0];
        span.addEventListener("click", function () {
            document.querySelector(".contenedor").style.display = "grid";
            modal.style.display = "none";
        }, false);
//MODAL FIN
});



