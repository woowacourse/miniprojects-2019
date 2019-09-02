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
        .then(data => detailVideo(data))
        .then(data => {
            const btnEdit = document.querySelector('#btn-edit')
            const btnDelete = document.querySelector('#btn-delete')

            if (btnEdit) {
                btnEdit.addEventListener('click', () => {
                    location.href = `/video-edit.html?id=${wootubeCtx.util.getUrlParams('id')}`
                })
            }

            if (btnDelete) {
                btnDelete.addEventListener('click', () => {
                    api.deleteVideo(wootubeCtx.util.getUrlParams('id'))
                        .then(response => deleteVideo(response))
                })
            }
        })
}

const detailVideo = function(data) {
    return new Promise((resolve) => {
        api.retrieveLoginInfo()
            .then(res => {
                if (res.status === 200) {
                    return res.json()
                }
            })
            .then(json => {
                if (json) {
                    addVideoDetailTemplate(data, data.creator.id === json.id)
                    return resolve()
                }
                addVideoDetailTemplate(data, false)
                return resolve()
            })
    })
}

detailView()

const deleteVideo = function(data) {
    if (data.status === 404) {
        alert(data.message)
        location.href = '/index.html'
        return false
    }

    location.href = '/index.html'
}