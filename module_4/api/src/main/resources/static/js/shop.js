class CartItem {
    constructor(title, price, imgSrc, id) {
        this.title = title;
        this.price = price;
        this.imgSrc = imgSrc;
        this.id = id;
    }

    init() {
        const cartItemTitle = document.createElement('div');
        cartItemTitle.classList.add("cart-product-title");
        const cartItemPrice = document.createElement('div');
        cartItemPrice.classList.add("cart-price");
        const cartItemId = document.createElement('div');
        cartItemId.classList.add("cart-product-id")
        const cartDetail = document.createElement('div');
        cartDetail.classList.add("detail-box");
        cartDetail.append(cartItemTitle, cartItemPrice, cartItemId);
        const cartItemImage = document.createElement('img');
        cartItemImage.classList.add("cart-image");
        const removeCartItem = document.createElement('span');
        removeCartItem.classList.add("material-icons", "cart-remove-icon");
        const cartItem = document.createElement('div');
        cartItem.classList.add("cart-box");
        cartItem.append(cartItemImage, cartDetail, removeCartItem);
        cartItemTitle.textContent = this.title;
        cartItemImage.src = this.imgSrc;
        cartItemPrice.textContent = this.price;
        cartItemId.textContent = this.id
        return cartItem;
    }

}

const cartContainer = document.body.querySelector(".cart-content");

const clickHandler = ({currentTarget, target}) => {
    if (!target.classList.contains('shopping-cart')){
        return;
    }
    console.log(target)
    const titleDiv = currentTarget.querySelector(".p-name");
    const priceDiv = currentTarget.querySelector(".p-price");
    const imageDiv = currentTarget.querySelector(".product-picture");

    const idDiv = currentTarget.querySelector(".certificate-id")

    let title;
    let price;
    let imgSrc;

    let id;

    if (titleDiv) {
        title = titleDiv.textContent;
    }
    if (priceDiv) {
        price = priceDiv.textContent;
    }
    if (imageDiv) {
        imgSrc = imageDiv.src;
    }

    if (idDiv) {
        id = idDiv.textContent;
    }

    const cardItem = new CartItem(title, price, imgSrc, id).init();
    console.log(cardItem)
    cartContainer.append(cardItem);
    updateTotal();
}

const cards = document.body.querySelectorAll(".product-box");

if (cards) {
    for (var i = 0; i < cards.length; i++) {
        var card = cards[i];
        card.addEventListener('click', clickHandler);
    }
}

function updateTotal() {
    var cartContent = document.querySelector(".cart-content");
    var cartBoxes = cartContent.querySelectorAll(".cart-box");
    var total = 0;
    for (var i = 0; i < cartBoxes.length; i++) {
        var cartBox = cartBoxes[i];
        var priceElement = cartBox.querySelector(".cart-price");
        var price = parseFloat(priceElement.innerText.replace("$", ""));
        total = total + price;

        total = Math.round(total * 100) / 100;
        document.querySelector(".total-price").innerText = "$" + total;
    }
}

//Remove Items From Cart
function removeCartItem(event) {
    var buttonClicked = event.target;
    buttonClicked.parentElement.remove();
    updateTotal();
}


//Remove Items From Cart
function remove() {
    var removeCartButtons = document.querySelectorAll(".cart-remove-icon");
    console.log(removeCartButtons);
    for (let i = 0; i < removeCartButtons.length; i++) {
        let button = removeCartButtons[i];
        button.addEventListener("click", removeCartItem);
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


const clickHandlerSend = () => {

    const certificates = document.body.querySelectorAll(".cart-box")
    let certificateIds = [];

    for (let i = 0; i < certificates.length; i++){
        const certificate = certificates[i].querySelector(".cart-product-id");
        if (certificate){
            certificateIds.push(certificate.textContent);
        }
    }
    fetch(
        '/orders',
        {
            headers: {'Content-Type': 'application/json'},
            method: 'POST',
            body: JSON.stringify(certificateIds)
        }
    ).catch(console.error);
}

const sendOrder = document.body.querySelector(".btn-buy")

if (sendOrder) {
    sendOrder.addEventListener('click', clickHandlerSend);
}

