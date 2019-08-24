const ReactionApp = (function() {

    const ReactionController = function() {
        const reactionService = new ReactionService();

        const clickGood = () => {
            const articleList = document.getElementById('article-list');
            articleList.addEventListener('click', reactionService.clickGood);
        };

        const init = () => {
            clickGood();
        };

        return {
            init: init,
        }
    };

    const ReactionService = function() {
        const reactionApi = new ReactionApi();

        const clickGood = (event) => {
            const target = event.target;
            if (target.closest('li[data-btn="article-reaction-good-btn"]')) {
                clickArticleGood(target);
            }
            else if (target.closest('span[data-btn="comment-reaction-good-btn"]')) {
                clickCommentGood(target);
            }
        };

        const clickArticleGood = (target) => {
            const article = target.closest('div[data-object="article"]');
            const articleId = article.getAttribute('data-article-id');
            const data = document.getElementById(`article-good-count-${articleId}`).innerText;

            reactionApi.clickGood(Number(data), 'articles', articleId)
                .then(response => response.json())
                .then(data => {
                    document.getElementById(`article-good-count-${articleId}`)
                        .innerText = data.numberOfGood;
                    console.log(data);
                    showGoodBtn('article', articleId, data.hasGood);
                });
        };

        const clickCommentGood = (target) => {
            const comment = target.closest('li[data-object="comment"]');
            const commentId = comment.getAttribute('data-comment-id');
            const data = document.getElementById(`comment-good-count-${commentId}`).innerText;

            reactionApi.clickGood(Number(data), 'comments', commentId)
                .then(response => response.json())
                .then(reactionComment => {
                    document.getElementById(`comment-good-count-${commentId}`)
                        .innerText = reactionComment.numberOfGood;
                    showGoodBtn('comment', commentId, reactionComment.hasGood);
                })
        };

        const showGoodCount = (object, objectId) => {
            reactionApi.showGoodCount(`${object}s`, objectId)
                .then(response => response.json())
                .then(data => {
                    document.getElementById(`${object}-good-count-${objectId}`)
                        .innerText = data.numberOfGood;
                    showGoodBtn(object, objectId, data.hasGood);
                });
        };

        const showGoodBtn = (object, objectId, hasGood) => {
            if (hasGood) {
                document.getElementById(`${object}-good-btn-icon-${objectId}`)
                    .setAttribute('class', 'fa fa-thumbs-up text-info font-size-16');
            } else {
                if (object === 'article') {
                    document.getElementById(`${object}-good-btn-icon-${objectId}`)
                        .setAttribute('class', 'fa fa-thumbs-o-up font-size-16');
                }
                else if(object === 'comment') {
                    document.getElementById(`${object}-good-btn-icon-${objectId}`)
                        .setAttribute('class', 'fa fa-thumbs-o-up text-info font-size-16');
                }
            }
        };

        return {
            clickGood: clickGood,
            showGoodCount: showGoodCount,
        }
    };

    const ReactionApi = function() {
        const clickGood = (data, object, objectId) => {
            return Api.post(`/api/${object}/${objectId}/good`, data)
        };

        const showGoodCount = (object, objectId) => {
            return Api.get(`/api/${object}/${objectId}/good`);
        };

        return {
            clickGood: clickGood,
            showGoodCount: showGoodCount,
        }
    };

    const init = function () {
        const reactionController = new ReactionController();
        reactionController.init();
    };

    return {
        init: init,
        service: ReactionService,
        api: ReactionApi,
    }
})();

ReactionApp.init();
