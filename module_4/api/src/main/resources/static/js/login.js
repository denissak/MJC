// const loginBtn = document.querySelector(".btn-login")
let login = "";

const clickHandlerLogin = (login) => {

    const username = document.querySelector(".username").value
    const password = document.querySelector(".password").value
    console.log("!!!")
    console.log(username)
    console.log(password)
    const params = new URLSearchParams({
        username: username,
        password: password
    })
    let response = fetch(
        '/signin?' + params,
        {
            // headers: {'Content-Type': 'application/json'},
            method: 'POST',
        }).then((response) => response.ok)
        .then(login = username)
        .catch(console.error);
    // console.log(response)
    // login = username;
    console.log(login);

}
const loginBtn = document.querySelector(".btn-login")

if (loginBtn) {
    loginBtn.addEventListener('click', clickHandlerLogin);
}
// const login = "";
console.log("//////////////////")
console.log(login)
