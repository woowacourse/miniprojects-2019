const videoApp = (function () {
    const VideoEvent = function() {
        const videoService = new VideoService();

        const save = function() {
            const saveBtn = document.getElementById('save-btn');
            saveBtn.addEventListener('click', videoService.save);
        }

        const init = function() {
            save();
        }

        return {
            init: init
        }
    }

    const VideoService = function() {
        const save = function() {
            const body = {};
            body.youtubeId = document.getElementById('youtube-id').value;
            body.title = document.getElementById('title').value;
            body.contents = document.getElementById('contents').value;
            const dataBody = JSON.stringify(body);
        
            api.saveVideo(dataBody)
            .then(response => response.json())
            .then(data => saveVideo(data))
        }

        const saveVideo = function (data) {
            if (data.result) {
                // error
                alert(data.message);
                return false;
            }

            location.href = './video-detail.html?id=' + data.id;
        }

        return {
            save:save
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
