class CartItem {
    constructor(title, price, imgSrc) {
        this.title = title;
        this.price = price;
        this.imgSrc = imgSrc;
    }

    init() {
        // cartItem.classList.add("cart-box", "")
        const cartItemTitle = document.createElement('div');
        cartItemTitle.classList.add("cart-product-title");
        const cartItemPrice = document.createElement('div');
        cartItemPrice.classList.add("cart-price");
        const cartPriceTotal = document.createElement('input');
        cartPriceTotal.classList.add("cart-quantity");
        const cartDetail = document.createElement('div');
        cartDetail.classList.add("detail-box");
        cartDetail.append(cartItemTitle, cartItemPrice, cartPriceTotal);

        const cartItemImage = document.createElement('img');
        cartItemImage.classList.add("cart-image");
        const removeCartItem = document.createElement('span');
        removeCartItem.classList.add("material-icons", "cart-remove-icon");
        const cartBox = document.createElement('div');
        cartBox.classList.add("cart-box");
        cartBox.append(cartItemImage, cartDetail, removeCartItem);

        const cartItem = document.createElement('div');
        cartItem.classList.add("cart-content");
        cartItem.append(cartBox);

        cartItemTitle.textContent = this.title;
        cartItemImage.src = this.imgSrc;
        cartItemPrice.textContent = this.price;

        // cartItem.append(cartItemTitle, cartItemImage, cartItemPrice);
        return cartItem;
    }

}

const cartContainer = document.body.querySelector(".cart-form");

const clickHandler = ({currentTarget}) => {
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

    // store.orders.push({title, price, imgSrc});
    //
    // const cart = document.querySelector(".cart-form");
    //
    // const cartItem = document.createElement('div');
    // const cartItemTitle = document.createElement('div');
    // const cartItemImage = document.createElement('src');
    // const cartItemPrice = document.createElement('div');
    //
    // cartItemTitle.textContent = store.orders[0].title;
    // cartItemImage.src = store.orders[0].imgSrc;
    // cartItemPrice.textContent = store.orders[0].price;
    //
    // cartItem.append(cartItemTitle, cartItemImage, cartItemPrice);
    //
    // cart.append(cartItem);
}
const card = document.body.querySelector(".product-box");

if (card) {
    card.addEventListener('click', clickHandler);

}
