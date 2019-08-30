const addVideoDetailTemplate = function (data) {
    const videoDetailTemplate =
        `<div class="d-flex flex-column align-items-center">
            <iframe width="913" height="514"
                src="https://www.youtube.com/embed/${data.youtubeId}" frameborder="0"
                allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
                allowfullscreen></iframe>
        </div>
        <div class="form-group row">
            <div class="col-md-12">
                <h1 class="mt-4">${data.title}</h1>
                <h4>조회수 ${data.viewCount}회</h4>
                <div>
                    <button id="btn-edit" type="button" class="btn btn-rounded btn-icon bg-transparent"><i class="ti-pencil"></i></button>
                    <button id="btn-delete" type="button" class="btn btn-rounded btn-icon bg-transparent"><i class="ti-trash"></i></button>
                </div>
            </div>
        </div>
        <hr>
        <div class="form-group row">
            <div class="col-md-2">
            <a href="/video-channel.html?id=${data.creator.id}">
                <img class="banner-owner-img img-circle relative"
                    src="./images/default/eastjun_big.jpg" alt="">
             </a>
            </div>
            <div class="col-md-10">
                <span class="text-bold">${data.creator.name}</span><br>
                <span class="d-block mt-2 mb-4">게시일 : ${moment.utc(data.createDate).local().format('YYYY. MM. DD.')}</span>
                <span>${data.contents}</span>
            </div>
        </div>
        <hr>`

    const videoArea = document.getElementById('video-area')
    videoArea.insertAdjacentHTML('beforeend', videoDetailTemplate)
}
