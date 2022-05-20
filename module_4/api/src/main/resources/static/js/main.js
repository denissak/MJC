let cards = [];

const cardsContainer = document.querySelector('.product-container');

fetch('/certificates').then((response) => response.json()).then((response) => {
    const {_embedded: {certificateDtoList}} = response;
    cards = certificateDtoList;
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

        const cartData = {
            title,
            price,
            image: imgSrc,
            id
        };
        const cardsKeyJsonString = localStorage.getItem('userId');
        const cards = JSON.parse(localStorage.getItem(cardsKeyJsonString)) || [];
        cards.push(cartData);
        localStorage.setItem(cardsKeyJsonString, JSON.stringify([...cards]));
        updateTotal();
        filCart(cart);
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
        certificateLink.textContent = "View";

        const cardBox = document.createElement('div');
        cardBox.classList.add("product-box");
        cardBox.append(cardImageWrapper, cardDetails, certificateLink);


        certificateName.textContent = this.name;
        cartItemImage.src = "/img/certificates/" + this.image;
        certificatePrice.textContent = this.price + "$";
        certificateId.textContent = this.id
        certificateLink.href = "/certificate/" + this.id


        cardBox.addEventListener('click', this.onClick)
        return cardBox;
    }
}

//TAG
const tagsContainer = document.querySelector('.cs-hidden');

fetch('/tags').then((response) => response.json()).then((response) => {
    const {_embedded: {tagDtoList}} = response;
    tagDtoList.forEach((tag) => {
        const tagElement = new Tag(tag).init();
        tagsContainer.append(tagElement);
    })
}).catch(console.error);

class Tag {
    constructor({name, image, id}) {
        this.name = name;
        this.image = image;
        this.id = id;
    }

    onClick({currentTarget, target}) {
        if (!target.classList.contains('tag-img')) {
            return;
        }
        const tagName = currentTarget.querySelector(".tag-name")
        const params = new URLSearchParams({
            tagValue: tagName.textContent
        })

        window.removeEventListener('scroll', debounceScrollHandler)
        fetch(
            '/certificates/search?' + params,
            {
                method: 'GET',
            }
        ).then((response) => response.json()).then(
            (response) => {
                const {_embedded: {certificateDtoList}} = response;
                cardsContainer.innerHTML = "";
                certificateDtoList.forEach((card) => {
                    const cardElement = new Card(card).init();
                    cardsContainer.append(cardElement);
                })
            }
        ).catch(console.error);
    }

    init() {
        const tagImage = document.createElement('img');
        tagImage.classList.add("tag-img");
        const tagId = document.createElement('div');
        tagId.classList.add("tag-id");

        const tagLink = document.createElement('a');
        tagLink.classList.add("tag-link");
        tagLink.append(tagImage, tagId);

        const categoryTagBox = document.createElement('div');
        categoryTagBox.classList.add("category-box");
        categoryTagBox.append(tagLink);

        const tagName = document.createElement('span');
        tagName.classList.add("tag-name");

        const tagItem = document.createElement('li');
        tagItem.classList.add("item");
        tagItem.append(categoryTagBox, tagName);

        const tag = document.createElement('div');
        tag.classList.add("tag");
        tag.append(tagItem);

        tagName.textContent = this.name;
        tagImage.src = "/img/tags/" + this.image;
        tagId.textContent = this.id;

        tag.addEventListener('click', this.onClick)
        return tag;
    }
}

// SCROLL
let page = 0;

const scrollHandler = () => {
    const documentRect = document.documentElement.getBoundingClientRect();
    if (document.body.scrollTop > 15 || document.documentElement.scrollTop > 15) {
        buttonTop.style.display = "block";
    } else if (document.body.scrollTop === 0 || document.documentElement.scrollTop === 0) {
        buttonTop.style.display = "none";
    }
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
}

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

const buttonTop = document.querySelector("#myBtn");
const buttonBottom = document.querySelector("#myBtn-bottom");

let savePosition = 0;

function topFunction() {
    savePosition = window.scrollY;
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
    buttonBottom.style.display = "block";
    console.log(savePosition);
}

function bottomFunction() {
    console.log(savePosition);
    window.scrollTo(0,savePosition);
    buttonBottom.style.display = "none";
    buttonTop.style.display = "block";
}
