const detailView = function() {
    params = wootubeCtx.util.getUrlParams();

    const videoId = params.id;
    api.requestVideo(videoId)
    .then(response => response.json())
    .then(data => detailVideo(data))
}

const detailVideo = function(data) {
    if (data.result) {
        alert('해당 동영상이 없습니다.');
        return false;
        // location.href = '/index.html';
    }
    addVideoDetailTemplate(data)
}

detailView();