function addVideoDetailTemplate(data) {
    const videoDetailTemplate =
        `<iframe width="913" height="514"
            src="https://www.youtube.com/embed/${data.youtubeId}" frameborder="0"
            allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
            allowfullscreen></iframe>
        <br><br>
        <div class="form-group row">
            <div class="col-md-12">
                <h1 class="text-bold">${data.title}</h1>
                <h4>조회수 1,000회</h4>
            </div>
        </div>
        <hr>
        <div class="form-group row">
            <div class="col-md-2">
            <a href="/video-channel.html">
                <img class="banner-owner-img img-circle relative"
                    src="./images/default/eastjun_big.jpg" alt="">
             </a>
            </div>
            <div class="col-md-10">
                <span class="text-bold">로비로비</span><br>
                <span>게시일 : ${data.createDate}</span><br><br>
                <span>${data.contents}</span>
            </div>
        </div>
        <hr>`

    const videoArea = document.getElementById('video-area')
    videoArea.insertAdjacentHTML('beforeend', videoDetailTemplate)
}
