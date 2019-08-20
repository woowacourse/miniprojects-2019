loadVideoCards = function (page,size,sort) {
    api.requestVideos(page,size,sort)
    .then(response => response.json())
    .then(json => addVideoCardTemplates(json.content,'dateVideoCard') )
}
loadVideoCards(0,6,'createDate')

