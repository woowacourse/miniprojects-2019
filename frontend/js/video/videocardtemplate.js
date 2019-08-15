function addVideoCardTemplate(data, number, categorie) {
    const dateDifference = calculateDate(data[number].createDate)
    const vidioCardTemplate = `<div class="col-lg-2 padding-2">
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
    videoCards.insertAdjacentHTML("beforeend", vidioCardTemplate)
}

function addVideoCardTemplates(data, categorie) {
    for(let i =0; i < 6; i++) {
        addVideoCardTemplate(data, i, categorie)
    }
}

function calculateDate(responseDate) {
    let videoDate = new Date(responseDate.substr(0,4), responseDate.substr(4,2), responseDate.substr(6,2), responseDate.substr(8,2))
    let currentDate = new Date()
    let yearDifference = currentDate.getFullYear() - videoDate.getFullYear()
    let monthDifference = currentDate.getMonth()+1 - videoDate.getMonth()
    let dayDifference = currentDate.getDate() - videoDate.getDate()
    let hourDifference = currentDate.getHours() - videoDate.getHours()
    if(yearDifference != 0) {
        return yearDifference+"년전"
    }else if(monthDifference != 0) {
        return monthDifference+"달전"
    }else if(dayDifference != 0) {
        return dayDifference+"일전"
    }else if(hourDifference != 0) {
        return hourDifference+"시간전"
    }else{
        return "방금전"
    }
}

