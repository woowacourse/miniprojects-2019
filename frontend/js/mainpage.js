loadDateVideoCards = function () {
    wootubeCtx.util.api.get('/v1/videos?filter=date&page=0&limit=6')
    .then(response => response.json())
    .then(json => addVideoCardTemplates(json,'dateVideoCard') )
    }
    loadDateVideoCards()

