let loginUser = "";

const clickHandlerLogin = () => {

    const username = document.querySelector(".username").value
    const password = document.querySelector(".password").value
    const params = new URLSearchParams({
        username: username,
        password: password
    })
    fetch(
        '/signin?' + params,
        {
            method: 'POST',
        }).then((response) => response.ok ? Promise.resolve(response) : Promise.reject()).
    then((() => {loginUser = username})).then(() => {
        localStorage.setItem('userId', username)
    }).catch(console.error);
}

const loginBtn = document.querySelector(".btn-login")

if (loginBtn) {
    loginBtn.addEventListener('click', clickHandlerLogin);
}
