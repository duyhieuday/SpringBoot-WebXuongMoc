
let carts = [];
let dataCartStr = localStorage.getItem("cart");
if(dataCartStr){
    carts = JSON.parse(dataCartStr);
}

$(function(){
    // # Lấy đối tượng có id = Cart-box trong jQuery
    $("#Cart-box").hide();
    $("#turnon").click(function(){
        $("#Cart-box").show();
    });

    $("#turnoff").click(function(){
        $("#Cart-box").hide(0,function(){
        });
    });
});

$('.addPrd').click(function() {
    
    let giaTien =$(this).siblings().children("span").attr('data-value');
    let anh = $(this).parents().children('.product').children("img").attr('src');
    let ten = $(this).parents().siblings('.product-title').text();
    let sl = 1;
    let tong = parseFloat(sl) * giaTien;
    let products = new Array(anh, ten, giaTien, sl, tong);

    $('.miniCart').show();

    let name = $(this).parents().siblings('.product-title').text();

    let check = false;
    for(let i = 0; i < carts.length; i++) {
        if(carts[i][1] == name) {
            carts[i][3]++;
            check = true;
            break;
        }
    }

    if(!check)
    {   
        let prod = {};
        for(let i = 0; i < carts.length; i++){
            if(carts[i][1] == name){
                prod = carts[i];
                carts[i][3] = 1;
                break; 
            }
        }
        carts.push(products);
    }

    displayCart();
});

function displayCart(){
    let cartStr = JSON.stringify(carts);
    localStorage.setItem("carts", cartStr); 

    let viewCart = ''; 

    for(let i = 0; i < carts.length; i++){
         
        let row = `
                        <div class="miniCart-item">
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
                                        <p class="quantity-text"> ${carts[i][3]} </p>
                                        <button class="add"><i class="fa-solid fa-plus"></i></button>
                                    </div>
                                    <button class="delete">Xóa</button>
                                </div>
                            </div>
                        </div>
                `

                viewCart += row;
    }

    $('.miniCart-body').html(viewCart);

    let totalPrice = 0;
    // tinh tong tien
    for(let i = 0; i < carts.length; i++) {
        totalPrice += (carts[i][2] * carts[i][3])
    }
    document.getElementById('quantity').innerText = carts.length;
    $('.miniCart-price').text(totalPrice + "đ")
}

// tăng số lượng
$(document).on('click', '.add', function(){
    let name = $(this).parent().parent().siblings('.cartItem-boxInfo').children(".cartItem-infoName").text();
    console.log("Iris");
    for(let i = 0; i < carts.length; i++){
        if(carts[i][1] == name)
        {
            carts[i][3]++;
            break;
        }
    }
    displayCart();
});

// giảm số lượng
$(document).on('click', '.minus', function(){
    let name = $(this).parent().parent().siblings('.cartItem-boxInfo').children('.cartItem-infoName').text();
    console.log("Iris");
    for(let i = 0; i < carts.length; i++){
        if(carts[i][1] == name)
        {
            carts[i][3]--;
            if(carts[i][3] < 1)
            {
                carts[i][3] = 1;
            }
            break;
        }
    }
    displayCart()
});

$(document).on('click', '.delete', function(){
    let name = $(this).parent().siblings('.cartItem-boxInfo').children("h3").text();

    let index = -1;
    for(let i = 0; i < carts.length; i++) {
        if(name == carts[i][1]) {
            index = i;
            break;
        }
    }

    let leftArr =  carts.slice(0, index)
    let rightArr = carts.slice(index+1)
    carts = leftArr.concat(rightArr);

    displayCart();
})
