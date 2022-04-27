class CartItem {
    constructor(title, price, imgSrc) {
        this.title = title;
        this.price = price;
        this.imgSrc = imgSrc;
    }

    init() {
        const cartItemTitle = document.createElement('div');
        cartItemTitle.classList.add("cart-product-title");
        const cartItemPrice = document.createElement('div');
        cartItemPrice.classList.add("cart-price");
        // const cartItemsTotal = document.createElement('input');
        // cartItemsTotal.classList.add("cart-quantity");
        const cartDetail = document.createElement('div');
        cartDetail.classList.add("detail-box");
        cartDetail.append(cartItemTitle, cartItemPrice/*, cartItemsTotal*/);

        const cartItemImage = document.createElement('img');
        cartItemImage.classList.add("cart-image");
        const removeCartItem = document.createElement('span');
        removeCartItem.classList.add("material-icons", "cart-remove-icon");
        const cartItem = document.createElement('div');
        cartItem.classList.add("cart-box");
        cartItem.append(cartItemImage, cartDetail, removeCartItem);


        // const cartItem = document.createElement('div');
        // cartItem.classList.add("cart-content");
        // cartItem.append(cartBox);

        cartItemTitle.textContent = this.title;
        cartItemImage.src = this.imgSrc;
        cartItemPrice.textContent = this.price;

        return cartItem;
    }

}

const cartContainer = document.body.querySelector(".cart-content");

const clickHandler = ({currentTarget}) => {
    console.log(currentTarget);
    const titleDiv = currentTarget.querySelector(".p-name");
    const priceDiv = currentTarget.querySelector(".p-price");
    const imageDiv = currentTarget.querySelector(".product-picture");

    let title;
    let price;
    let imgSrc;

    // [titleDiv, priceDiv, imageDiv].forEach((elem) => {
    //     if (elem){
    //
    //     }
    // });

    if (titleDiv) {
        title = titleDiv.textContent;
    }
    if (priceDiv) {
        price = priceDiv.textContent;
    }
    if (imageDiv) {
        imgSrc = imageDiv.src;
    }

    const cardItem = new CartItem(title, price, imgSrc).init();
    console.log(cardItem)
    cartContainer.append(cardItem);
    updateTotal();
}
const card = document.body.querySelector(".product-box");

if (card) {
    card.addEventListener('click', clickHandler);
}

function updateTotal() {
    var cartContent = document.querySelector(".cart-content");
    var cartBoxes = cartContent.querySelectorAll(".cart-box");
    var total = 0;
    for (var i = 0; i < cartBoxes.length; i++){
        var cartBox = cartBoxes[i];
        var priceElement = cartBox.querySelector(".cart-price");
        // var quantityElement = cartBox.querySelector(".cart-quantity");
        var price = parseFloat(priceElement.innerText.replace("$", ""));
        // var quantity = 1 /*quantityElement.value*/;
        total = total + price /*(price * quantity)*/;

        total = Math.round(total * 100) / 100;
        document.querySelector(".total-price").innerText = "$" + total;
    }
}

//Quantity changes
// function quantityChanged(event) {
//     var input = event.target;
//     if (isNaN(input.value) || input.value <= 0) {
//         input.value = 1;
//     }
//     updateTotal()
// }

// //Remove Items From Cart
// function removeCartItem(event) {
//     var buttonClicked = event.target;
//     buttonClicked.parentElement.remove();
//     updateTotal();
// }

// function ready() {
//     //Remove Items From Cart
//     var removeCartButtons = document.querySelectorAll(".cart-remove-icon");
//     console.log(removeCartButtons);
//     for (var i = 0; i < removeCartButtons.length; i++) {
//         var button = removeCartButtons[i];
//         button.addEventListener("click", removeCartItem);
//     }
//     //Quantity Changes
//     var quantityInputs = document.querySelectorAll(".cart-quantity");
//     for (var i = 0; i < quantityInputs.length; i++) {
//         var input = quantityInputs[i];
//         input.addEventListener("change", quantityChanged);
//     }
// }

