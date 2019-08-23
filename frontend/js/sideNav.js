const sideNavToggle = function (event) {
    event.preventDefault()
    document.querySelector('.app').classList.toggle('is-collapsed')
}

document.querySelector('.side-nav-toggle').addEventListener('click', sideNavToggle)