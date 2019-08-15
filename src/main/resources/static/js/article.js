const template = `<div class="card widget-feed" data-object="article" data-article-id="{{id}}">
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
                                        <li data-btn="update">
                                            <a class="pointer">
                                                <i class="ti-pencil pdd-right-10 text-dark"></i>
                                                <span id="article-update-{{id}}" data-toggle="modal" data-target="#default-modal">게시글 수정</span>
                                            </a>
                                        </li>
                                        <li data-btn="delete">
                                            <a class="pointer">
                                                <i class="ti-trash pdd-right-10 text-dark"></i>
                                                <span id="article-delete-{{id}}">게시글 삭제</span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="feed-body no-pdd">
                        <p>
                            <span data-object="article-contents">{{article-contents}}</span> <br>
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

const api = {
    get: function (url) {
        return fetch(url, {
            method: 'GET',
        });
    },
    post: function (url, data) {
        return fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });
    },
    put: function (url, data) {
        return fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });
    },
    delete: function (url) {
        return fetch(url, {
            method: 'DELETE',
        });
    },
};

function addArticle(url, data) {
    return api.post(url, data)
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
        });
}

function saveArticle() {
    const contents = document.getElementById("article-contents");
    if (contents) {
        addArticle("/articles", {
            contents: contents.value,
            imageUrl: "",
            videoUrl: "",
        })
    } else {
        alert('내용을 입력해주세요.');
    }
}

function showModal(article) {
    const updateArea = document.getElementById('article-update-contents');
    const articleId = article.getAttribute('data-article-id');
    updateArea.innerText = article.querySelector('span[data-object="article-contents"]').innerText;
    api.put("/articles/" + articleId,)
}


const saveBtn = document.getElementById('article-save-btn');
saveBtn.addEventListener('click', saveArticle);

const articleList = document.getElementById('article-list');
articleList.addEventListener('click', function (event) {
    const target = event.target;
    if (target.closest('li[data-btn="delete"]')) {
        const article = target.closest('div[data-object="article"]');
        const articleId = article.getAttribute('data-article-id');
        api.delete('/articles/' + articleId);
        article.remove();
    }

    if (target.closest('li[data-btn="update"]')) {
        const article = target.closest('div[data-object="article"]');
        showModal(article);
    }
});

const updateBtn = document.getElementById('update-btn');

window.addEventListener('DOMContentLoaded', function () {
    return api.get("/articles")
        .then(response => response.json())
        .then(data => {
            data.forEach(article => {
                document.getElementById('article-list')
                    .insertAdjacentHTML('afterbegin', articleTemplate({
                        "id": article.id,
                        "updatedTime": article.updatedTime,
                        "article-contents": article.articleFeature.contents,
                        "article-videoUrl": "https://www.youtube.com/embed/rA_2B7Yj4QE",
                        "article-imageUrl": "https://i.pinimg.com/originals/e5/64/d6/e564d613befe30dfcef2d22a4498fc70.png"
                    }));
            })
        })
        .catch(error => console.log("error: " + error));
});