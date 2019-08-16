document.querySelector('.search-toggle').addEventListener('click', toggleSearchInput)
document.querySelector(".search-input input").addEventListener("keyup", showSearchedList)

function toggleSearchInput(event) {
    event.preventDefault();
    document.querySelector('.search-box').classList.toggle('active')
    document.querySelector(".search-input").classList.toggle("active")
    document.querySelector(".search-input input").focus()
}

function showSearchedList(event) {
    if(event.target.value.length > 0) {
        document.querySelector(".advanced-search").classList.add("active")
    } else {
        document.querySelector(".advanced-search").classList.remove("active")
        $('.advanced-search').removeClass("active");
    }
}

