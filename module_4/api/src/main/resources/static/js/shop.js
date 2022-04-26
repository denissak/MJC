import {store} from './store'
import {CartItem} from "./cart";

const cart = document.body.querySelector(".cart-form");

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
    cart.append(cardItem);

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
const card = document.body.querySelector(".product-container");

if (card) {
    card.addEventListener('click', clickHandler);

}
