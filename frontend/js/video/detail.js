const detailView = function() {
    const videoId = wootubeCtx.util.getUrlParams('id');
    api.requestVideo(videoId)
    .then(response => {
        if (response.status !== 200) {
            response.json()
            .then(err => {
                alert('해당 동영상이 없습니다.');
                window.location.href = "/"
            })
        } else {
            return response.json()
        }
    })
    .then(data => {
        detailVideo(data)
        document.querySelector('#btn-edit').addEventListener('click', () => {
            location.href = `/video-edit.html?id=${wootubeCtx.util.getUrlParams('id')}`
        })
        
        document.querySelector('#btn-delete').addEventListener('click', () => {
            api.deleteVideo(wootubeCtx.util.getUrlParams('id'))
                .then(response => deleteVideo(response))
        });

    })
}

const detailVideo = function(data) {
    addVideoDetailTemplate(data)
}

detailView();

const deleteVideo = function(data) {
    if (data.status === 404) {
        alert(data.message)
        location.href = '/index.html'
        return false
    }

    location.href = '/index.html'
}