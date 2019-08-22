const getArticleTemplate = function (articleId, userId, userName, imageUrl, profileUrl, contents, comments, likeNum, liking) {
    return `<div class="article-card card widget-feed no-pdd mrg-btm-70" data-article-id = ${articleId} data-user-id = ${userId}>
                            <div class="feed-header padding-15">
                                <ul class="list-unstyled list-info">
                                    <li>
                                        <img class="thumb-img img-circle" src=${profileUrl}
                                             alt="">
                                        <div class="info">
                                            <a href=""
                                               class="title no-pdd-vertical text-bold inline-block font-size-15">${userName}</a>
                                        </div>
                                    </li>
                                    <a class="pointer absolute top-10 right-0" data-toggle="dropdown"
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
                                </ul>
                            </div>
                            <div class="feed-body no-pdd">
                                <img class="img-fluid" src=${imageUrl} alt="">
                            </div>
                            <ul class="feed-action pdd-horizon-15 pdd-top-5">
                                <li>
                                    <a class="like-btn" data-liking=${liking}>
                                        <i class="fa ${liking ? 'fa-heart' : 'fa-heart-o'} font-size-25"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="">
                                        <i class="ti-comment font-size-22"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="">
                                        <i class="ti-export font-size-22"></i>
                                    </a>
                                </li>
                                <li class="float-right">
                                    <a href="" class="pd-right-0">
                                        <i class="fa fa-bookmark-o font-size-25"></i>
                                    </a>
                                </li>
                            </ul>
                            <div class="feedback-status-container pdd-horizon-15">
                                <p class="no-mrg pdd-left-5 d-inline-block">
                                    <span class="text-bold">${likeNum} Likes<span class="text-bold">
                                </p>
                            </div>
                            <div class="feed-footer">
                                <div class="comment">      
                                <ul class="list-unstyled list-info pdd-horizon-5">
                                        <li class="comment-item no-pdd">
                                            <div class = "comment-list">
                                                <div class="info pdd-left-15 pdd-vertical-5">
                                                   <a href=""
                                                   class="title no-pdd-vertical text-bold inline-block font-size-15">${userName}</a>
                                                   <span class="font-size-14">${contents}</span>
                                                </div>
                                                ${comments}
                                            </div>
                                           <time class="font-size-8 text-gray d-block">12시간 전</time>                           
                                     </li>
                                    </ul>
                                    <div class="add-comment relative">
                                        <textarea rows="1" class="form-control text-dark padding-15"
                                                  placeholder="댓글 달기..."></textarea>
                                        <div class="absolute top-5 right-0">
                                            <button class="btn btn-default no-border text-gray">게시</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>`
};

const getCommentTemplate = (commentId, userName, contents) => {
    return `
    <div class="info pdd-left-15 pdd-vertical-5" data-comment-id = ${commentId} >
       <a href=""
       class="title no-pdd-vertical text-bold inline-block font-size-15">${userName}</a>
       <span class="font-size-14">${contents}</span>
    </div>
      `;
};
const Index = (function () {

    const pageSize = 10;

    const IndexController = function () {
        const indexService = new IndexService();
        const articleList = document.querySelector('.article-card-con');

        const loadInit = function () {
            indexService.getPageData(0);
        };

        const likeButton = () => {
            articleList.addEventListener('click', function (event) {
                console.log(event.target.classList);
                if (event.target.classList.contains('like-btn')) {
                    const articleId = event.target.closest('.article-card').dataset.articleId;
                    const isLike = event.target.dataset.liking;
                    indexService.addLike(articleId, isLike, event.target);
                }
            })

        };

        const init = function () {
            loadInit();
            likeButton()
        };

        return {
            init: init
        }
    };


    const IndexService = function () {
        const indexRequest = new Request("/api/main");
        const like = new Like();
        const getPageData = (pageNum) => {
            indexRequest.get('?page=' + pageNum + "&size=" + pageSize + "&sort=id,DESC"
                , (status, data) => {
                    let pagesHtml = "";
                    for (let i = 0; i < data.content.length; i++) {
                        pagesHtml += parsingPage(data.content[i]);
                    }
                    const container = document.querySelector(".article-card-con");
                    container.insertAdjacentHTML('beforeend', pagesHtml);
                })
        };

        const addLike = (articleId, isLike, e) => {
            console.log(like.addLike(articleId, isLike));
            if(isLike=="false") {
                e.childNodes[1].classList.add("fa-heart");
                e.childNodes[1].classList.remove("fa-heart-o");
            }
        };

        const parsingPage = (pageData) => {
            const article = pageData.article;
            const user = pageData.article.userInfoDto;
            const comments = parsingComments(pageData.comments);
            return getArticleTemplate(
                article.id,
                user.id,
                user.userContentsDto.userName,
                article.imageUrl,
                user.userContentsDto.profile,
                article.contents,
                comments,
                pageData.likes,
                pageData.liking
            )
        };

        const parsingComments = (commentsData) => {
            let commentsHtml = "";
            commentsData.forEach((data) => {
                commentsHtml += getCommentTemplate(
                    data.id,
                    data.userInfoDto.userContentsDto.userName,
                    data.contents
                )
            });
            return commentsHtml;
        };

        return {
            getPageData: getPageData,
            addLike: addLike
        }
    };

    const init = () => {
        const indexController = new IndexController();
        indexController.init();
    };

    return {
        init: init
    }

}());

Index.init();