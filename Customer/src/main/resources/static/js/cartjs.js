
let carts = [];
const addPRD = $('.addPrd');
const span_quantity = $('#quantity').attr('num');
var allcoast = 0;

$(function(){
    $("#Cart-box").hide();
    $("#turnon").click(function(){
        $("#Cart-box").show();
    });

    $("#turnoff").click(function(){
        $("#Cart-box").hide(0,function(){
        });
    });
});

$('.addPrd').click(function(){
    var giaTien =$(this).siblings().children("span").attr('data-value');
    var img = $(this).parents().children('.product').children("img").attr('src');
    var name = $(this).parents().siblings('.product-title').text();
    var quantity = 1;
    var total = parseFloat(quantity) * giaTien;
    var information = new Array(img, name, giaTien, quantity, total);
    allcoast += parseFloat(total)

    var add = true;

    for(let i = 0; i < carts.length; i++){
        if(name === carts[i][1]){
            add = false;
            carts[i][3] += 1;

            carts[i][4] = parseFloat(carts[i][3] * carts[i][2]);
            break;
        }
    }
    if(add){
        carts.push(information);
    }
    sessionStorage.setItem("carts", JSON.stringify(carts));

    showItem();
    totalItem();
    addQuantity();
})


function totalItem(){
    $('.miniCart-footer .miniCart-total .miniCart-price').text(allcoast);
}

// function showItem() 
// {   
//     var html1 = '';
//     var img = $(this).parents().children('.product').children("img").attr('src');
//     for(let i = 0; i < carts.length; i++) 
//     {
//         html1 =`
//                 <div class="miniCart-item">
//                     <div class="cartItem-img">
//                         <img src="${carts[i][0]}" alt="">
//                     </div>
//                     <div class="cartItem-box">
//                         <div class="cartItem-boxInfo">
//                             <h3 class="cartItem-infoName">${carts[i][1]}</h3>
//                             <div class="cartItem-infoPrice">
//                                 <p class="cartItem-newPrice" data-value="1000">${carts[i][2]} đ</p>
//                             </div>
//                         </div>
//                         <div class="cartItem-itemOption">
//                             <div class="cartItem-btnQuantity">  
//                                 <button class="minus"><i class="fa-solid fa-minus"></i></button>
//                                 <p class="quantity-text"> SL: ${carts[i][3]}</p>
//                                 <button class="add"><i class="fa-solid fa-plus"></i></button>
//                             </div>
//                             <button class="delete">Xóa</button>
//                         </div>
//                     </div>
//                 </div>`
//     }
//     $('.miniCart-body').append(html1);
//     $('.miniCart').show();
// }

function showItem()
{
    var carts = JSON.parse(sessionStorage.getItem("carts"))
    if(carts)
    {
        var showout = document.querySelector('.miniCart-body .miniCart-item');
        var t = "";

        for(let i = 0; i < carts.length; i++) 
        {
            if(showout)
            {
                t += `<div class="miniCart-item">
                                <div class="cartItem-img">
                                    <img src="${carts[i][0]}" alt="">
                                </div>
                                <div class="cartItem-box">
                                    <div class="cartItem-boxInfo">
                                        <h3 class="cartItem-infoName">${carts[i][1]}</h3>
                                        <div class="cartItem-infoPrice">
                                            <p class="cartItem-newPrice" data-value="1000">${carts[i][2]} đ</p>
                                        </div>
                                    </div>
                                    <div class="cartItem-itemOption">
                                        <div class="cartItem-btnQuantity">  
                                            <button class="minus"><i class="fa-solid fa-minus"></i></button>
                                            <p class="quantity-text"> SL: ${carts[i][3]} </p>
                                            <button class="add"><i class="fa-solid fa-plus"></i></button>
                                        </div>
                                        <button class="delete">Xóa</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    `    
            }
        }
        showout.innerHTML = t;
    }
}

$(document).on('click', '.delete', function(){
    let name = $(this).parent().siblings('.cartItem-boxInfo').children("h3").text();
    console.log(name);
    for(let i = 0; i < carts.length; i++)
    {
        if(name == carts[i][1]){
            allcoast -= carts[i][4];
            carts.splice(i, 1)
            sessionStorage.setItem("carts", JSON.stringify(carts))
            totalItem()
            showItem()
            addQuantity()
        }
    }
});

function addQuantity()
{
    document.getElementById('quantity').innerText = carts.length;
}


$(document).on('click', '.add', function(){
    let name = $(this).parent().parent().siblings('.cartItem-boxInfo').children("h3").text();
    var totalPrice = 0;
    for(let i = 0; i < carts.length; i++){
        if(name === carts[i][1])
        {
            carts[i][3]++;
            $(this).siblings('.quantity-text').val(carts[i][3])

            carts[i][4] = parseFloat(carts[i][3] * carts[i][2]);
            totalPrice = carts[i][4];
            // $(this).parent().parent().parent().parent().siblings().children('.miniCart-price').val(carts[i][4])

            sessionStorage.setItem("carts", JSON.stringify(carts))
            break;
        }
    }
    $('.miniCart-footer .miniCart-total .miniCart-price').text(totalPrice);
    // totalItem()
    showItem()
    addQuantity()       

})

$(document).on('click', '.minus', function(){
    let name = $(this).parent().parent().siblings('.cartItem-boxInfo').children("h3").text();
    for(let i = 0; i < carts.length; i++){
        if(name === carts[i][1])
        {
            carts[i][3]--;
            $(this).siblings('.quantity-text').val(carts[i][3])

            carts[i][4] = parseFloat(carts[i][3] * carts[i][2]);
            $(this).parent().parent().parent().parent().siblings().children('.miniCart-price').val(carts[i][4])

            sessionStorage.setItem("carts", JSON.stringify(carts))
            break;
        }
    }
    totalItem()
    showItem()
    addQuantity() 
})