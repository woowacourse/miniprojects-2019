function addVideoCardTemplate(data, number, categorie) {
    const dateDifference = wootubeCtx.util.calculateDate(data[number].createDate)
    const videoCardTemplate = 
    `<div class="col-lg-2 padding-2">
        <div class="card bg-transparent no-border">
            <div class="card-media">
                <a href="video-detail.html?id=${data[number].id}"> 
                    <img class="img-responsive" src="http://img.youtube.com/vi/${data[number].youtubeId}/0.jpg" alt="">
                </a>
            </div>
            <div class="card-block padding-10">
                <h5 class="mrg-btm-10 no-mrg-top text-bold font-size-14 ls-0">${data[number].title}</h5>
                <span class="font-size-13">로비</span>
                <div class="font-size-13">
                <span>조회수</span>
                <span> · </span>
                <span>${dateDifference}</span>
                </div>
            </div>
        </div>
    </div>`
    const videoCards = document.getElementById(categorie)
    videoCards.insertAdjacentHTML('beforeend', videoCardTemplate)
}

function addVideoCardTemplates(data, categorie) {
    for(let i =0; i < Math.min(6, data.content.length); i++) {
        addVideoCardTemplate(data.content, i, categorie)
    }
}

