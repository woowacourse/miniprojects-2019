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

    api.isLogin()
    .then(response => {
        if (response.status !== 200) {
            console.log(`first then : ${response}`)
            return response.json().then(err => {throw new Error(err)})
        }
        return response.json()
    })
    .then(response => addLoginArea(response))
    .catch(err => {
        console.log(`in err : ${err}, ${err.message}`)
    })
}

const addLoginArea = function(response) {
    // addSubcribe(response.id) // REAL
    console.log(`in addLoginArea : ${response}`)
    addSubscribe(response.id)
    changeHref(response.id)
    // addSubcribeTemplates(testData) // ONLY LOCAL TEST
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
            `<li class="nav-item">
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
    
    for (let i = 0; i < data.length; i++) {
        addSubscribeTemplate(data[i])
    }
}

const changeHref = function (userId) {
    const libraryElem = document.querySelector('#navi-library')
    const libAElem = libraryElem.querySelector('a')
    libAElem.setAttribute('href', `/video-channel.html?id=${userId}`)
}

addSideNavi(document.querySelector('.side-nav-inner'))

const sideNavToggle = function (event) {
    event.preventDefault()
    document.querySelector('.app').classList.toggle('is-collapsed')
}

document.querySelector('.side-nav-toggle').addEventListener('click', sideNavToggle)
