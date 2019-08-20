const searchbox = document.getElementById('searchbox')
const searchDropdown = document.getElementById('search-dropdown')
const dropdownMenu = document.getElementById('dropdown-menu')

const search = function (event) {
    const keyword = event.target.value
    console.log(keyword)
    const items = document.getElementById('dropdown-items');

    if(keyword.length ===0){
        searchDropdown.classList.remove('show')
        return
    }

    if(items!==null) {
        dropdownMenu.removeChild(items)
    }
    api.GET(`/api/users/${keyword}/search`)
        .then(res=>res.json())
        .then(users => {
            searchDropdown.classList.add('show')

            const items =document.createElement('div')
            items.id = 'dropdown-items'

            users.forEach(user=>{
                const aTag = getATag('#',searchResultTemplate(user))
                items.appendChild(aTag)
            })
            items.appendChild(getATag('#', `<p style="color: blue">${keyword}로 전체 검색</p>`))
            dropdownMenu.appendChild(items)
        })
        .catch(error=> console.error(error))
}

const getATag = function(location, html){
    const aTag = document.createElement('a')
    aTag.classList.add('dropdown-item')
    aTag.href = location
    aTag.innerHTML = html
    return aTag
}

searchbox.addEventListener('keyup', search)
