const addHeader = function (headerElement) {

    const setHeaderEvent = () => {
        const sideNavToggle = function (event) {
            event.preventDefault()
            document.querySelector('.app').classList.toggle('is-collapsed')
        }

        document.querySelector('.side-nav-toggle').addEventListener('click', sideNavToggle)
    }

    const signedInHeaderTemplate =
        `<header id="header" class="header navbar shadow-sm">
        <div class="header-container">
            <ul class="nav-left mrg-left-0">
                <li>
                    <a class="side-nav-toggle" href="javascript:void(0);">
                        <i class="material-icons">menu</i>
                    </a>
                </li>
                <li>
                    <a href="/" class="d-flex flex-row align-items-center"><img class="logo" src="./images/logo/youtube-logo.png"></a>
                </li>
            </ul>
            <ul class="nav-right padding-8">
                <li>
                    <a href="video-create.html" class="pointer">
                        <i class="material-icons">video_call</i>
                    </a>
                </li>
                <li>
                    <a class="pointer">
                        <i class="material-icons">apps</i>
                    </a>
                </li>
                <li>
                    <a class="pointer">
                        <i class="material-icons">notifications</i>
                    </a>
                </li>
                <li class="user-profile">
                    <button type="button" class="btn btn-outline-primary btn-logout">로그아웃</button>
                </li>
            </ul>
        </div>
    </header>`


    const notSignedInHeaderTemplate =
        `<header id="header" class="header navbar shadow-sm">
        <div class="header-container">
            <ul class="nav-left mrg-left-0">
                <li>
                    <a class="side-nav-toggle" href="javascript:void(0);">
                        <i class="material-icons">menu</i>
                    </a>
                </li>
                <li>
                <a href="/" class="d-flex flex-row align-items-center"><img class="logo" src="./images/logo/youtube-logo.png"></a>
                </li>
            </ul>
            <ul class="nav-right padding-8">

                <li>
                    <a href="/login.html" class="pointer">
                        <i class="material-icons">video_call</i>
                    </a>
                </li>
                <li>
                    <a class="pointer">
                        <i class="material-icons">apps</i>
                    </a>
                </li>
                <li>
                    <a class="pointer">
                        <i class="material-icons">notifications</i>
                    </a>
                </li>
                <li class="user-profile">
                    <a href="/login.html">
                        <button type="button" class="btn btn-outline-primary">로그인</button>
                    </a>
                </li>
            </ul>
        </div>
    </header>`

    const scriptTemplate = `<script src="./js/logout.js"></script>`

    const handleLogoutEvent = function () {

        api.postLogout()
            .then(res => {
                window.location.reload();
            })
    }

    api.retrieveLoginInfo()
        .then(res => {
            if (res.status === 200) {
                headerElement.insertAdjacentHTML('afterbegin', signedInHeaderTemplate)
                document.querySelector('body').insertAdjacentHTML('beforeend', scriptTemplate)
                setHeaderEvent();
                document.querySelector('.btn-logout')
                    .addEventListener('click', handleLogoutEvent)
                return;
            }
        })
        .catch(res => {
            headerElement.insertAdjacentHTML('afterbegin', notSignedInHeaderTemplate)
            setHeaderEvent();
        })
}

addHeader(document.querySelector('.header-wrapper'));
