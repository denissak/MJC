let cartIcon = document.querySelector(".cart");
let cart = document.querySelector(".cart-form");
let closeCart = document.querySelector(".cart-cancel");

cartIcon.onclick = () => {
    cart.classList.add("active");
};

closeCart.onclick = () => {
    cart.classList.remove("active");
};

if (document.readyState == 'loading'){
    document.addEventListener("DOMContentLoaded", ready);
}else{
    ready();
}

function ready() {
    //Remove Items From Cart
    var removeCartButtons = document.getElementsByClassName("cart-remove-icon");
    console.log(removeCartButtons);
    for (var i = 0; i < removeCartButtons.length; i++){
        var button = removeCartButtons[i];
        button.addEventListener("click", removeCartItem);
    }
    //Quantity Changes
    var quantityInputs = document.getElementsByClassName("cart-quantity");
    for (var i = 0; i < quantityInputs.length; i++){
        var input = quantityInputs[i];
        input.addEventListener("change", quantityChanged);
    }
    // Add To Cart
    var addCart = document.getElementsByClassName("shopping-cart")
    for (var i = 0; i < addCart.length; i++){
        var button = addCart[i];
        button.addEventListener("click", addCartClicked);
    }
}

//Remove Items From Cart
function removeCartItem(event) {
    var buttonClicked = event.target;
    buttonClicked.parentElement.remove();
    updatetotal();
}

//Quantity changes
function quantityChanged(event){
    var input = event.target;
    if (isNaN(input.value) || input.value <= 0){
        input.value = 1;
    }
    updatetotal()
}

//Add To Cart
function addCartClicked(event) {
    var button = event.target;
    var shopProducts = button.parentElement;
    var title = shopProducts.getElementsByClassName("p-name")[0].innerText;
    var price = shopProducts.getElementsByClassName("p-price")[0].innerText;
    var productImage = shopProducts.getElementsByClassName("product-picture")[0].src;
    addProductToCart(title, price, productImage);
    updatetotal();
}

function addProductToCart(title, price, productImage) {
    var cartShopBox = document.createElement("div");
    cartShopBox.classList.add("product-img");
    var cartItems = document.getElementsByClassName("product-box")[0];
    var cartItemsNames = document.getElementsByClassName("p-name")[0];
    for (var i = 0; i < cartItemsNames.length; i++) {
        if (cartItemsNames[i].innerText == title) {
            alert("You have already add this item to cart");
            return;
        }
    }

var cartBoxContent =
            `<img th:src="@{/images/feature_2.jpg}" alt="" class="cart-image">
            <div class="detail-box">
                <div class="cart-product-title">
                    Certificate 1
                </div>
                <div class="cart-price">
                    $10.12
                </div>
                <input type="number" value="1" class="cart-quantity">
            </div>
            <!--Remove cart-->
                <span class="material-icons cart-remove-icon">delete</span>`;
cartShopBox.innerHTML = cartBoxContent;
cartItems.append(cartShopBox);
cartShopBox.getElementsByClassName("cart-remove-icon")[0].addEventListener("click", removeCartItem);
cartShopBox.getElementsByClassName("cart-quantity")[0].addEventListener("change", quantityChanged);
}
//Update Total
function updatetotal(){
    var cartContent = document.getElementsByClassName("cart-content")[0];
    var cartBoxes = cartContent.getElementsByClassName("cart-box");
    var total = 0;
    for (var i = 0; i < cartBoxes.length; i++){
        var cartBox = cartBoxes[i];
        var priceElement = cartBox.getElementsByClassName("cart-price")[0];
        var quantityElement = cartBox.getElementsByClassName("cart-quantity")[0];
        var price = parseFloat(priceElement.innerText.replace("$", ""))
        var quantity = quantityElement.value;
        total = total + (price * quantity);
        //If price Contains Cents
        total = Math.round(total * 100) / 100;

        document.getElementsByClassName("total-price")[0].innerText = "$" + total;
    }
}