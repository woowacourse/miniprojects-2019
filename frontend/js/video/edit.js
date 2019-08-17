const editView = function() {
    params = wootubeCtx.util.getUrlParams();
    const videoId = params.id;
    wootubeCtx.util.api.get(`/v1/videos/${videoId}`)
    .then(response => response.json())
    .then(json => editVideo(json));
}

const editVideo = function(json) {
    if (json.result) {
        alert("해당 동영상이 없습니다!");
        return false;
    }
    insertValuesIntoTemplate(json);
}

const insertValuesIntoTemplate = function(json) {
    const youtubeId = document.getElementById('youtube-id');
    const title = document.getElementById('title');
    const contents = document.getElementById('contents');

    youtubeId.value = json.youtubeId;
    title.value = json.title;
    contents.value = json.contents;
}

editView();