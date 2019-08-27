const ReactionApp = (function () {

    const ReactionController = function () {
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

    const ReactionService = function () {
        const reactionApi = new ReactionApi();

        const clickGood = (event) => {
            const target = event.target;
            if (target.closest('li[data-btn="article-reaction-good-btn"]')) {
                clickArticleGood(target);
            } else if (target.closest('span[data-btn="comment-reaction-good-btn"]')) {
                clickCommentGood(target);
            }
        };

        const clickArticleGood = (target) => {
            const article = target.closest('div[data-object="article"]');
            const articleId = article.getAttribute('data-article-id');
            const data = document.getElementById(`article-good-count-${articleId}`).innerText;
            const goodBtn = document.getElementById(`article-good-btn-icon-${articleId}`);

            if (goodBtn.className === 'fa fa-thumbs-o-up font-size-16') {
                reactionApi.addGood(Number(data), 'articles', articleId)
                    .then(response => response.json())
                    .then(data => saveGoodData(articleId, data));
            } else if (goodBtn.className === 'fa fa-thumbs-up text-info font-size-16') {
                reactionApi.deleteGood('articles', articleId)
                    .then(response => response.json())
                    .then(data => saveGoodData(articleId, data));
            }
        };

        const saveGoodData = (articleId, good) => {
            document.getElementById(`article-good-count-${articleId}`)
                .innerText = good.numberOfGood;
            showGoodBtn('article', articleId, good.hasGood);
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

        const showGoodCount = (objectName, objectId) => {
            reactionApi.showGoodCount(`${objectName}s`, objectId)
                .then(response => response.json())
                .then(data => {
                    document.getElementById(`${objectName}-good-count-${objectId}`)
                        .innerText = data.numberOfGood;
                    showGoodBtn(objectName, objectId, data.hasGood);
                });
        };

        const showGoodBtn = (objectName, objectId, hasGood) => {
            if (hasGood) {
                document.getElementById(`${objectName}-good-btn-icon-${objectId}`)
                    .setAttribute('class', 'fa fa-thumbs-up text-info font-size-16');
            } else {
                if (objectName === 'article') {
                    document.getElementById(`${objectName}-good-btn-icon-${objectId}`)
                        .setAttribute('class', 'fa fa-thumbs-o-up font-size-16');
                } else if (objectName === 'comment') {
                    document.getElementById(`${objectName}-good-btn-icon-${objectId}`)
                        .setAttribute('class', 'fa fa-thumbs-o-up text-info font-size-16');
                }
            }
        };

        return {
            clickGood: clickGood,
            showGoodCount: showGoodCount,
        }
    };

    const ReactionApi = function () {
        const clickGood = (data, objectName, objectId) => {
            return Api.post(`/api/${objectName}/${objectId}/good`, data)
        };

        const addGood = (data, objectName, objectId) => {
            return Api.post(`/api/${objectName}/${objectId}/good`, data)
        };

        const deleteGood = (objectName, objectId) => {
            return Api.delete(`/api/${objectName}/${objectId}/good`);
        }

        const showGoodCount = (objectName, objectId) => {
            return Api.get(`/api/${objectName}/${objectId}/good`);
        };

        return {
            clickGood: clickGood,
            addGood: addGood,
            deleteGood: deleteGood,
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
