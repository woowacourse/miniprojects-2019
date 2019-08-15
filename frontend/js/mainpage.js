loadDateVideoCards = function () {
    api.get("/api/v1/videos?filter=view&page=1&limit=6")
        .then(response => response.json())
        .then(json => addVideoCardTemplates(json, "dateVideoCard"))
}
loadDateVideoCards()

