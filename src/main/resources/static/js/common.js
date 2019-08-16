document.querySelector('.search-toggle').addEventListener('click', toggleSearchInput)

function toggleSearchInput(event) {
    event.preventDefault();
    document.querySelector('.search-box').classList.toggle('active')
    document.querySelector(".search-input").classList.toggle("active")
    document.querySelector(".search-input input").focus()
}


