export class CartItem {
    constructor(title, price, imgSrc) {
        this.title = title;
        this.price = price;
        this.imgSrc = imgSrc;
    }

    init() {
        const cartItem = document.createElement('div');
        const cartItemTitle = document.createElement('div');
        const cartItemImage = document.createElement('src');
        const cartItemPrice = document.createElement('div');

        cartItemTitle.textContent = this.title;
        cartItemImage.src = this.imgSrc;
        cartItemPrice.textContent = this.price;

        cartItem.append(cartItemTitle, cartItemImage, cartItemPrice);
    }

}