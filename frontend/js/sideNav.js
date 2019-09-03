const addSideNavi = function (sideNaviElement) {
    const sideNaviTemplate =
        `<ul class="side-nav-menu overflow-y-auto">
            <li class="nav-item">
                <a class="mrg-top-30" href="/">
                    <span class="icon-holder">
                        <i class="material-icons">home</i>
                    </span>
                    <span class="title relative bottom-5">홈</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="" href="/">
                    <span class="icon-holder">
                        <i class="material-icons">hearing</i>
                    </span>
                    <span class="title relative bottom-5">인기</span>
                </a>
            </li>
            <li class="nav-item" id="navi-subcribe">
                <a class="">
                    <span class="icon-holder">
                        <i class="material-icons">subscriptions</i>
                    </span>
                    <span class="title relative bottom-5">구독</span>
                </a>
            </li>
            <hr>
            <li class="nav-item" id="navi-library">
                <a class="" href="/login.html">
                    <span class="icon-holder">
                        <i class="material-icons">folder</i>
                    </span>
                    <span class="title relative bottom-5">라이브러리</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="">
                    <span class="icon-holder">
                        <i class="material-icons">restore</i>
                    </span>
                    <span class="title relative bottom-5">최근 본 동영상</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="">
                    <span class="icon-holder">
                        <i class="material-icons">access_time</i>
                    </span>
                    <span class="title relative bottom-5">나중에 볼 동영상</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="">
                    <span class="icon-holder">
                        <i class="material-icons">thumb_up</i>
                    </span>
                    <span class="title relative bottom-5">좋아요 표시한 동영상</span>
                </a>
            </li>
        </ul>`

    sideNaviElement.insertAdjacentHTML('beforeEnd', sideNaviTemplate)

    api.retrieveLoginInfo()
    .then(response => {
        if (response.status !== 200) {
        } else {
            return response.json()
        }
    })
    .then(response => {
        if (response) {
            addLoginArea(response)
        }
    })
}

const addLoginArea = function(response) {
    addSubscribe(response.id)
    changeLibraryUrl(response.name)
}

const addSubscribe = function (id) {
    api.requestSubscribed(id)
    .then(response => {
        if (response.status !== 200) {
            return false
        }
        return response.json()
    })
    .then(response => addSubscribeTemplates(response))
}

const addSubscribeTemplates = function (data) {
    const addSubscribeTemplate = function (data) {
        const subscribeTemplate =
            `<li class="nav-item subscription">
                <a class="" href="/video-channel.html?id=${data.id}">
                    <span class="icon-holder">
                        <img class="profile-img img-fluid" src="./images/default/eastjun_profile.jpg" alt="">
                    </span>
                    <span class="title relative bottom-5">${data.name}</span>
                </a>
            </li>`
    
        const subscribeNextElem = document.querySelector('#navi-subcribe').nextElementSibling
        subscribeNextElem.insertAdjacentHTML('beforebegin', subscribeTemplate)
    }
    

    document.querySelectorAll('.subscription').forEach(elem => elem.parentNode.removeChild(elem))
    for (let i = 0; i < data.length; i++) {
        addSubscribeTemplate(data[i])
    }
}

const changeLibraryUrl = function (name) {
    const libraryElem = document.querySelector('#navi-library')
    const libAElem = libraryElem.querySelector('a')
    libAElem.setAttribute('href', `/video-channel.html`)
    const titleElem = libAElem.querySelector('.title')
    titleElem.innerHTML = name
}

addSideNavi(document.querySelector('.side-nav-inner'))

const sideNavToggle = function (event) {
    event.preventDefault()
    document.querySelector('.app').classList.toggle('is-collapsed')
}
