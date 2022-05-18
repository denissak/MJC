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
        removeCartItem.textContent = "delete";
        const cartItem = document.createElement('div');
        cartItem.classList.add("cart-box");
        cartItem.append(cartItemImage, cartDetail, removeCartItem);
        cartItemTitle.textContent = this.title;
        cartItemImage.src = this.imgSrc;
        cartItemPrice.textContent = this.price;
        cartItemId.textContent = this.id
        cartItem.addEventListener('click', this.onClick);
        return cartItem;
    }

    onClick({currentTarget, target}) {
        const cards = JSON.parse(localStorage.getItem('cards'));
        if (!target.classList.contains('cart-remove-icon')) {
            return;
        }

        const cartItemId = currentTarget.querySelector(".cart-product-id").textContent;
        console.log(currentTarget)
        console.log(cartItemId)
        if (cards.length) {
            const filteredCards = cards.filter((cartData) => {
                return cartData.id !== cartItemId;
            })
            console.log(1)
            localStorage.setItem('cards', JSON.stringify([...filteredCards]));
            updateTotal();
        }

        filCart(cart);
    }
}

// click handler add cart
const cart = document.querySelector('.cart-content');
const cartIcon = document.body.querySelector(".shopping-cart")
const filCart = (cart) => {
    if (cart.hasChildNodes()) {
        [...cart.children].forEach((child) => child.remove());
    }
    const cards = JSON.parse(localStorage.getItem('cards'));
    if (cards.length) {
        cards.forEach((cartData) => {
            const cartItem = new CartItem(cartData.title, cartData.price, cartData.image, cartData.id).init();
            cart.append(cartItem);
            updateTotal();
        })
    }
}
const cartClickHandler = () => {
    filCart(cart);
}
if (cartIcon) {
    cartIcon.addEventListener('click', cartClickHandler);
}

//click handler delete card in cart
// const deleteCard = document.body.querySelector(".cart-box");


// const cards = JSON.parse(localStorage.getItem('cards'));
// deleteCard.onclick =  ({currentTarget, target}, event) => {
//     console.log(event)
//     const cards = JSON.parse(localStorage.getItem('cards'));
//     if (!target.classList.contains('cart-remove-icon')) {
//         return;
//     }
//
//     const cartItemId = currentTarget.querySelector(".cart-product-id").textContent;
//     console.log(currentTarget)
//     console.log(cartItemId)
//     if (cards.length) {
//         const filteredCards = cards.filter((cartData) => {
//             return cartData.id !== cartItemId;
//         })
//         console.log(1)
//         localStorage.setItem('cards', JSON.stringify([...filteredCards]));
//         updateTotal();
//     }
// };


// document.body.querySelector(".cart-box").onclick = function ({currentTarget, target}, event){
//     console.log(event)
//     const cards = JSON.parse(localStorage.getItem('cards'));
//     if(!target.classList.contains('cart-remove-icon')){
//         return;
//     }
//
//     const cartItemId = currentTarget.querySelector(".cart-product-id").textContent;
//     console.log(currentTarget)
//     console.log(cartItemId)
//     if (cards.length){
//         const filteredCards = cards.filter ((cartData) => {
//             return cartData.id !== cartItemId;
//         })
//         console.log(1)
//         localStorage.setItem('cards', JSON.stringify([...filteredCards]));
//         updateTotal();
//     }
// };

// const deleteCartClickHandler = ({currentTarget, target}, event) => {
//     console.log(event);
//     const cards = JSON.parse(localStorage.getItem('cards'));
//     if(!target.classList.contains('cart-remove-icon')){
//         return;
//     }
//
//     const cartItemId = currentTarget.querySelector(".cart-product-id").textContent;
//     console.log(currentTarget)
//     console.log(cartItemId)
//     if (cards.length){
//         const filteredCards = cards.filter ((cartData) => {
//             return cartData.id !== cartItemId;
//         })
// console.log(1)
//         localStorage.setItem('cards', JSON.stringify([...filteredCards]));
//         updateTotal();
//     }
// }
//
// if (deleteCard){
//     deleteCard.addEventListener('click', deleteCartClickHandler);
// }
// if (deleteCard){
//     deleteCard.addEventListener('click',event =>{
//         deleteCartClickHandler({},event);
//     } );
// }


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

// //Remove Items From Cart
// function removeCartItem(event) {
//     var buttonClicked = event.target;
//     buttonClicked.parentElement.remove();
//     updateTotal();
// }
//
// //Remove Items From Cart
// function remove() {
//     var removeCartButtons = document.querySelectorAll(".cart-remove-icon");
//     console.log(removeCartButtons);
//     for (let i = 0; i < removeCartButtons.length; i++) {
//         let button = removeCartButtons[i];
//         button.addEventListener("click", removeCartItem);
//     }
// }

const clickHandlerSend = () => {

    const certificates = document.body.querySelectorAll(".cart-box")
    let certificateIds = [];
    for (let i = 0; i < certificates.length; i++) {
        const certificate = certificates[i].querySelector(".cart-product-id");
        if (certificate) {
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
    localStorage.clear();
    document.querySelector(".total-price").innerText = "$" + 0;
}

const sendOrder = document.body.querySelector(".btn-buy")

if (sendOrder) {
    sendOrder.addEventListener('click', clickHandlerSend);
}
