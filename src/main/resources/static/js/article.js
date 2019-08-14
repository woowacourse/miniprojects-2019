const template = `<div class="card widget-feed" id="article-{{id}}">
                    <div class="feed-header">
                        <ul class="list-unstyled list-info">
                            <li>
                                <img class="thumb-img img-circle" src="/images/default/eastjun_profile.jpg" alt="">
                                <div class="info">
                                    <a href="" class="title no-pdd-vertical text-semibold inline-block">eastjun</a>
                                    <span class="sub-title">{{updatedTime}}</span>
                                    <a class="pointer absolute top-0 right-0" data-toggle="dropdown"
                                       aria-expanded="false">
                                        <span class="btn-icon text-dark">
                                            <i class="ti-more font-size-16"></i>
                                        </span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a class="pointer">
                                                <i class="ti-trash pdd-right-10 text-dark"></i>
                                                <span class="">게시글 삭제</span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="feed-body no-pdd">
                        <p>
                            <span>{{article-contents}}</span> <br>
                            <iframe src="{{article-videoUrl}}" height="380"
                                    allowfullscreen></iframe>
                            <img src="{{article-imageUrl}}" height="100" width="100"/>
                        </p>
                    </div>
                    <ul class="feed-action pdd-btm-5 border bottom">
                        <li>
                            <i class="fa fa-thumbs-o-up text-info font-size-16 mrg-left-5"></i>
                            <span class="font-size-14 lh-2-1">67</span>
                        </li>
                        <li class="float-right">
                            <span class="font-size-13">공유 78회</span>
                        </li>
                        <li class="float-right mrg-right-15">
                            <span class="font-size-13">댓글 2개</span>
                        </li>
                    </ul>
                    <ul class="feed-action border bottom d-flex">
                        <li class="text-center flex-grow-1">
                            <button class="btn btn-default no-border pdd-vertical-0 no-mrg width-100">
                                <i class="fa fa-thumbs-o-up font-size-16"></i>
                                <span class="font-size-13">좋아요</span>
                            </button>
                        </li>
                        <li class="text-center flex-grow-1">
                            <button class="btn btn-default no-border pdd-vertical-0 no-mrg width-100">
                                <i class="fa fa-comment-o font-size-16"></i>
                                <span class="font-size-13">댓글</span>
                            </button>
                        </li>
                        <li class="text-center flex-grow-1">
                            <button class="btn btn-default no-border pdd-vertical-0 no-mrg width-100">
                                <i class="fa fa-share font-size-16"></i>
                                <span class="font-size-13">공유하기</span>
                            </button>
                        </li>
                    </ul>
                    <div class="feed-footer">
                        <div class="comment">
                            <ul class="list-unstyled list-info">
                                <li class="comment-item">
                                    <img class="thumb-img img-circle" src="/images/default/eastjun_profile.jpg" alt="">
                                    <div class="info">
                                        <div class="bg-lightgray border-radius-18 padding-10 max-width-100">
                                            <a href="" class="title text-bold inline-block text-link-color">eastjun</a>
                                            <span>크 멋져요. MVC패턴을 직접 프로젝트에 적용해봤나요?</span>
                                        </div>
                                        <div class="font-size-12 pdd-left-10 pdd-top-5">
                                            <span class="pointer text-link-color">좋아요</span>
                                            <span>·</span>
                                            <span class="pointer text-link-color">답글 달기</span>
                                            <span>·</span>
                                            <span class="pointer">2시간</span>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                            <div class="add-comment">
                                <textarea rows="1" class="form-control" placeholder="댓글을 입력하세요.."></textarea>
                            </div>
                        </div>
                    </div>
                </div>`;

const host = 'http://' + window.location.host;

const articleTemplate = Handlebars.compile(template);

function putData(method, url, data) {
    return fetch(url, {
        method: method, // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, cors, *same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: 'same-origin', // include, *same-origin, omit
        headers: {
            'Content-Type': 'application/json',
        },
        redirect: 'follow', // manual, *follow, error
        referrer: 'no-referrer', // no-referrer, *client
        body: JSON.stringify(data), // body data type must match "Content-Type" header
    })
        .then(response => response.json())
        .then(function (article) {
            document.getElementById('article-list')
                .insertAdjacentHTML('afterbegin', articleTemplate({
                    "id": article.id,
                    "updatedTime": article.updatedTime,
                    "article-contents": article.articleFeature.contents,
                    "article-videoUrl": "https://www.youtube.com/embed/rA_2B7Yj4QE",
                    "article-imageUrl": "https://i.pinimg.com/originals/e5/64/d6/e564d613befe30dfcef2d22a4498fc70.png"
                }));
            console.log(article);
        })
        ; // parses JSON response into native JavaScript objects
}

function saveArticle() {
    const contents = document.getElementById("article-contents");
    if (contents) {
        putData("post", "/articles", {
            contents: contents.value,
            imageUrl: "",
            videoUrl: "",
        })
    } else {
        alert('내용을 입력해주세요.');
    }
}


const saveBtn = document.getElementById('article-save-btn');
saveBtn.addEventListener('click', saveArticle);