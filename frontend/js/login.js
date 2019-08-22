const handleSignInEvent = function (event) {
    const emailElm = document.querySelector('input[type=email]')
    const passwordElm = document.querySelector('input[type=password]')
    
    const data = {
        email : emailElm.value,
        password : passwordElm.value
    }
    const body = JSON.stringify(data)
    api.postLogin(body)
    .then(res => {
        if (res.status === 200) {
            window.location.href = '/index.html'
            return;
        }
        return res.json()
    })
    .then(json => {
        if (res.message) {
            alert(res.message)
            const alertElm = document.querySelector('.alert-danger');
            alertElm.innerText = json.message
            alertElm.classList.remove('d-none');
        }
    });
}

document.querySelector('.btn-signin')
.addEventListener('click', handleSignInEvent)
