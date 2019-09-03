const editView = function() {
    const videoId = wootubeCtx.util.getUrlParams('id');
    api.requestVideo(videoId)
    .then(response => response.json())
    .then(json => editVideo(json));
}

const editVideo = function(json) {
    if (json.result) {
        alert("해당 동영상이 없습니다!");
        location.herf = '/index.html'
        return false;
    }
    insertValuesIntoTemplate(json);
}

const insertValuesIntoTemplate = function(json) {
    const youtubeId = document.getElementById('youtube-id')
    const title = document.getElementById('title')
    const contents = document.getElementById('contents')

    youtubeId.value = json.youtubeId
    title.value = wootubeCtx.util.unescapeHtml(json.title)
    contents.value = wootubeCtx.util.unescapeHtml(json.contents)
}

editView();

const videoApp = (function () {
    const VideoEvent = function() {
        const videoService = new VideoService();

        const update = function() {
            const saveBtn = document.getElementById('save-btn');
            saveBtn.addEventListener('click', videoService.update);
        }

        const init = function() {
            update();
        }

        return {
            init: init
        }
    }

    const VideoService = function() {
        const update = function() {
            const body = {};
            body.youtubeId = document.getElementById('youtube-id').value;
            body.title = document.getElementById('title').value;
            body.contents = document.getElementById('contents').value;
            const dataBody = JSON.stringify(body);
        
            api.updateVideo(dataBody, wootubeCtx.util.getUrlParams('id'))
            .then(response => response.json())
            .then(data => updateVidoe(data))
        }

        const updateVidoe = function (data) {
            if (data.result) {
                // error
                alert(data.message);
                location.herf = '/index.html'
                return false;
            }

            location.href = '/video-detail.html?id=' + data.id;
        }

        return {
            update:update
        }
    }

    const init = function() {
        const videoEvent = new VideoEvent();
        videoEvent.init();
    }

    return {
        init:init
    }
})();

videoApp.init();