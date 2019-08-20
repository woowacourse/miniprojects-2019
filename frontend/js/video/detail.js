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

document.querySelector('#btn-edit').addEventListener('click', function(event) {
    location.href = '/video-edit.html?id=' + wootubeCtx.util.getUrlParams().id
})

document.querySelector('#btn-delete').addEventListener('click', function(event) {
    api.deleteVideo(wootubeCtx.util.getUrlParams().id)
        .then(response => deleteVideo(response))
});

const deleteVideo = function(data) {
    if (data.status === 404) {
        alert(data.message)
        location.href = "/index.html"
        return false
    }

    location.href = "/index.html"
}