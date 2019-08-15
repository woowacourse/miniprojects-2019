function addVideoDetailTemplate(data) {
    const videoDetailTemplate = `
                                <iframe width="560" height="315" src="https://www.youtube.com/embed/${data.youtubeId}"
                                    frameborder="0"
                                    allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
                                    allowfullscreen></iframe>
                                <form class="form-horizontal mrg-top-40 pdd-right-30">

                                    <div class="form-group row">
                                        <div class="col-md-10">
                                            <h1> ${data.title} </h1>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-md-10">
                                            ${data.contents}
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-md-10">
                                            사람입니다.
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-md-10">
                                            ${data.createDate}
                                        </div>
                                    </div>
                                </form>
                                `

    const videoArea = document.getElementById("video-area")
    videoArea.insertAdjacentHTML("beforeend", videoDetailTemplate)
}
