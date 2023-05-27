$(document).ready(function(){
    $(".image-slider").slick({
        slidesToShow: 4,
        prevArrow:"<button type='button' class='slick-prev pull-left  slick-arrow'><i class='fa fa-angle-left' aria-hidden='true'></i></button>",
                nextArrow:"<button type='button' class='slick-next pull-right  slick-arrow'><i class='fa fa-angle-right ' aria-hidden='true'></i></button>"
    });
});


$(document).ready(function(){
    $('.partner-slider').slick({
        slidesToShow: 6,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 2000,
    });
});
