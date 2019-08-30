const loadVideoCards = function (page, size, sort, cardElmId) {
    api.requestVideos(page,size,sort)
    .then(response => response.json())
    .then(json => addVideoCardTemplates(json.content, cardElmId) )
}
loadVideoCards(0,6,'createDate', 'dateVideoCard')
loadVideoCards(0,6,'viewCount', 'popularVideoCard')
