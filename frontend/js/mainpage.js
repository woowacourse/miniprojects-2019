loadVideoCards = function (filter) {
    api.requestVideos(filter)
    .then(response => response.json())
    .then(json => addVideoCardTemplates(json,'dateVideoCard') )
    }
    loadVideoCards('createDate')

