loadDateVideoCards = function () {
    api.get("/api/v1/videos?filter=date&page=0&limit=6")
    .then(response => response.json())
    .then(json => addVideoCardTemplates(json,"dateVideoCard") )
    }
    loadDateVideoCards()

