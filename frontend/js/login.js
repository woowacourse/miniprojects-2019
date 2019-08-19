const handleSignInEvent = function (event) {
    const emailElm = document.querySelector('input[type=email]');
    const passwordElm = document.querySelector('input[type=password]');
    // TODO: Validate and Request login with emailElm.value and passwordElm.value
}

document.querySelector('.btn-signin')
.addEventListener('click', handleSignInEvent);
