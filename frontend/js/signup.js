const handleSignUpEvent = function () {
    const nameElm = document.querySelector('input[type=name]')
    const emailElm = document.querySelector('input[type=email]')
    const passwordElm = document.querySelector('input[type=password]')
    const passwordComElm = document.querySelector('input[name=password-confirm]')

    const body = JSON.stringify({
        name : nameElm.value,
        email : emailElm.value,
        password : passwordElm.value,
        passwordConfirm : passwordComElm.value
    })
    api.signup(body)
    .then(res => {
        if (res.status === 201) {
            window.location.href = '/login.html'
            return
        }

        return res.json()
    })
    .then(json => {
        if (json.message) {
            const alertElm = document.querySelector('.alert-danger')
            alertElm.innerText = json.message
            alertElm.classList.remove('d-none')
        }
    })
}

document.querySelector('.btn-signup')
.addEventListener('click', handleSignUpEvent)
