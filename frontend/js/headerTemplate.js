function addHeader(headerElement){
    const headerTemplate = 
    `<header id="header" class="header navbar shadow-sm">
        <div class="header-container">
            <ul class="nav-left mrg-left-0 padding-8">
                <li>
                    <a class="side-nav-toggle" href="javascript:void(0);">
                        <i class="material-icons">menu</i>
                    </a>
                </li>
                <li>
                    <a href="/"><img class="logo" src="./images/logo/youtube-logo.png"></a>
                </li>
            </ul>
            <ul class="nav-right padding-8">

                <li class="">
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
                    <a href="/video-channel.html">
                        <img class="profile-img img-fluid" src="./images/default/eastjun_profile.jpg" alt="">
                    </a>
                </li>
            </ul>
        </div>
    </header>`
    headerElement.insertAdjacentHTML('afterbegin', headerTemplate)
}
