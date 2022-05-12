const cardsContainer = document.querySelector('.product-container');

fetch('/certificates').then((response) => response.json()).then((response) => {
    const {_embedded: {certificateDtoList}} = response;
    certificateDtoList.forEach((card) => {
        const cardElement = new Card(card).init();
        cardsContainer.append(cardElement);
    })
}).catch(console.error);

class Card {
    constructor({name, price, image, id}) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.id = id;
    }

    onClick({currentTarget, target}) {
        if (!target.classList.contains('shopping-cart')) {
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

    init() {
        const shoppingCartIcon = document.createElement('span');
        shoppingCartIcon.classList.add("material-icons", "shopping-cart");
        shoppingCartIcon.textContent = "shopping_cart";

        const addCart = document.createElement('a');
        addCart.classList.add("add-cart");
        addCart.append(shoppingCartIcon);

        const cartItemImage = document.createElement('img');
        cartItemImage.classList.add("product-picture");

        const cardImage = document.createElement('div');
        cardImage.classList.add("product-img");
        cardImage.append(addCart, cartItemImage);

        const cardImageWrapper = document.createElement('div');
        cardImageWrapper.classList.add("product-wrapper");
        cardImageWrapper.append(cardImage);

        //product-details

        const certificateName = document.createElement('a');
        certificateName.classList.add("p-name");
        const certificatePrice = document.createElement('span');
        certificatePrice.classList.add("p-price");
        const certificateId = document.createElement('span');
        certificateId.classList.add("certificate-id");

        const cardDetails = document.createElement('div');
        cardDetails.classList.add("product-details");
        cardDetails.append(certificateName, certificatePrice, certificateId);

        const certificateLink = document.createElement('a');
        certificateLink.classList.add("certificate-id-page");

        const cardBox = document.createElement('div');
        cardBox.classList.add("product-box");
        cardBox.append(cardImageWrapper, cardDetails, certificateLink);


        certificateName.textContent = this.name;
        cartItemImage.src = "/img/certificates/" + this.image;
        certificatePrice.textContent = this.price;
        certificateId.textContent = this.id

        cardBox.addEventListener('click', this.onClick)
        return cardBox;
    }
}


// SCROLL
let page = 0;

const scrollHandler = () => {
    const documentRect = document.documentElement.getBoundingClientRect();
    if (documentRect.bottom < document.documentElement.clientHeight + 150) {
        page += 1;
        const params = new URLSearchParams({
            page: page
        })
        fetch(
            '/certificates?' + params,
            {
                method: 'GET',
            }
        ).then((response) => response.json()).then(
            (response) => {
                const {_embedded: {certificateDtoList}} = response;
                certificateDtoList.forEach((card) => {
                    const cardElement = new Card(card).init();
                    cardsContainer.append(cardElement);
                })
            }
        ).catch(console.error);
    }
};

//Add timeout to scroll

const debounce = (func, timeout, ...args) => {
    let timer;
    return (...funcArgs) => {
        clearTimeout(timer);
        timer = setTimeout(() => {
            func(...args, ...funcArgs);
        }, timeout);
    };
};

const debounceScrollHandler = debounce(scrollHandler, 50)
window.addEventListener('scroll', debounceScrollHandler);


//
// // FILTER
// $('cs-hidden .item').click(function (){
//     let filter = $(this).attr('data-filter')
//     if (filter == 'man'){
//         $('cs-hidden .item').not('.' + filter).hide(200);
//         $('cs-hidden .item').filter('.' + filter).show(400);
//     }
// })
//


// let cartIcon = document.querySelector(".cart");
// let cart = document.querySelector(".cart-form");
// let closeCart = document.querySelector(".cart-cancel");


// cartIcon.onclick = () => {
//     cart.classList.add("active");
// };
//
// closeCart.onclick = () => {
//     cart.classList.remove("active");
// };
//
// if (document.readyState == 'loading') {
//     document.addEventListener("DOMContentLoaded", ready);
// } else {
//     ready();
// }
