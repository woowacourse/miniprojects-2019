const carouselTemplate = (post) => `
<div id="postCarouselControls-${post.id}" class="carousel slide" data-ride="carousel" data-interval="false">
    <ol class="carousel-indicators">
        ${convertNumToIndicatorTemplate(post.id, post.files.length)}
    </ol>
    <div class="carousel-inner">
        ${convertFilesToItemTemplate(post.files)}
    </div>
    <a class="carousel-control-prev" href="#postCarouselControls-${post.id}" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon carousel-icon-color" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#postCarouselControls-${post.id}" role="button" data-slide="next">
        <span class="carousel-control-next-icon carousel-icon-color" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>
`

const convertNumToIndicatorTemplate = (postId, num) => {
    let indicatorTemplates = [];
    for (let i = 0; i < num; i++) {
        indicatorTemplates.push(indicatorTemplate(postId, i));
    }

    return indicatorTemplates.join('')
}

const convertFilesToItemTemplate = (files) => {
    const itemTemplates = files.map((file, idx) => carouselItemTemplate(file, idx === 0))

    return itemTemplates.join('')
}

const indicatorTemplate = (postId, index) => `
<li data-target="#postCarouselControls-${postId}" data-slide-to="${index}" class="${index === 0 ? "active" : ""}"></li>
`

const carouselItemTemplate = (file, isFirst) => `
<div class="carousel-item ${isFirst ? "active" : ""}">
    <img src="${file.fileFeature.path}" width="100%" class="d-block w-100" alt="${file.fileFeature.originalName}">
</div>
`