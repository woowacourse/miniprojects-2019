const Index = (function () {

    const pageSize = 10;

    const IndexController = function () {
        const indexService = new IndexService();
        const articleList = document.querySelector('.article-card-con');

        const loadInit = function () {
            indexService.getPageData(0);
            document.querySelector("i").addEventListener("click", function (e) {
                e.stopPropagation();
                e.preventDefault();
            });
        };

        const likeButton = () => {
            articleList.addEventListener('click', function (event) {
                if (event.target.classList.contains('like-btn')) {
                    const articleId = event.target.closest('.article-card').dataset.articleId;
                    const isLike = event.target.dataset.liking;
                    indexService.eventLike(articleId, isLike, event.target);
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

        const eventLike = (articleId, isLike, e) => {
            if (isLike == 'false') {
                like.addLike(articleId)
                    .then(() => {
                        e.childNodes[1].classList.add("fa-heart");
                        e.childNodes[1].classList.remove("fa-heart-o");
                        e.dataset.liking= "true";
                        const likeNumElement = e.closest(".article-card").querySelector(".like-num")
                        const likeNum = parseInt(likeNumElement.innerText)+1;
                        likeNumElement.innerText=likeNum;
                    });

            } else {
                like.deleteLike(articleId)
                    .then(() => {
                        e.childNodes[1].classList.add("fa-heart-o");
                        e.childNodes[1].classList.remove("fa-heart");
                        e.dataset.liking= "false"
                        const likeNumElement = e.closest(".article-card").querySelector(".like-num")
                        const likeNum = parseInt(likeNumElement.innerText)-1;
                        likeNumElement.innerText=likeNum;
                    });
            }
        };

        const parsingPage = (pageData) => {
            const article = pageData.article;
            const user = pageData.article.userInfoDto;
            const comments = parsingComments(pageData.comments);
            if (user.profile === null) {
                user.profile = "https://woowahan-crews.s3.ap-northeast-2.amazonaws.com/default_profile_image.jpg";
            }
            return getArticleTemplate(
                article.id,
                user.id,
                user.userContentsDto.userName,
                article.imageUrl,
                user.profile,
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
            eventLike: eventLike
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