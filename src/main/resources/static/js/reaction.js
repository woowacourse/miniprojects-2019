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
            if (target.closest('li.article-reaction-good-btn')) {
                clickArticleGood(target);
            } else if (target.closest('span.comment-reaction-good-btn')) {
                clickCommentGood(target);
            }
        };

        const clickArticleGood = (target) => {
            const article = target.closest('div[data-object="article"]');
            const articleId = article.getAttribute('data-article-id');
            const data = document.getElementById(`article-good-count-${articleId}`).innerText;
            const goodBtn = document.getElementById(`article-good-btn-icon-${articleId}`);
            const doesNotClickGoodBtnClassNames = ['fa', 'fa-thumbs-o-up', 'font-size-16'];
            const clickedGoodBtnClassNames = ['fa', 'fa-thumbs-up', 'text-info', 'font-size-16'];

            if (isContainedClassNames(goodBtn, doesNotClickGoodBtnClassNames)) {
                reactionApi.addGood(Number(data), 'articles', articleId)
                    .then(response => response.json())
                    .then(data => saveGoodData('article', articleId, data));
            } else if (isContainedClassNames(goodBtn, clickedGoodBtnClassNames)) {
                reactionApi.deleteGood('articles', articleId)
                    .then(response => response.json())
                    .then(data => saveGoodData('article', articleId, data));
            }
        };

        const clickCommentGood = (target) => {
            const comment = target.closest('li[data-object="comment"]');
            const commentId = comment.getAttribute('data-comment-id');
            const data = document.getElementById(`comment-good-count-${commentId}`).innerText;
            const goodIcon = document.getElementById(`comment-good-btn-icon-${commentId}`);
            const doesNotClickGoodIconClassNames = ['fa', 'fa-thumbs-o-up', 'text-info', 'font-size-16'];
            const clickedGoodIconClassNames = ['fa', 'fa-thumbs-up', 'text-info', 'font-size-16'];

            if (isContainedClassNames(goodIcon, doesNotClickGoodIconClassNames)) {
                reactionApi.addGood(Number(data), 'comments', commentId)
                    .then(response => response.json())
                    .then(data => saveGoodData('comment', commentId, data));
            } else if (isContainedClassNames(goodIcon, clickedGoodIconClassNames)) {
                reactionApi.deleteGood('comments', commentId)
                    .then(response => response.json())
                    .then(data => saveGoodData('comment', commentId, data));
            }
        };

        const isContainedClassNames = (el, classNames) => {
            return classNames.every(className => el.classList.contains(className));
        };

        const saveGoodData = (objectName, objectId, good) => {
            document.getElementById(`${objectName}-good-count-${objectId}`)
                .innerText = good.numberOfGood;
            showGoodBtn(objectName, objectId, good.hasGood);
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
            const goodObject = document.getElementById(`${objectName}-good-btn-icon-${objectId}`);
            if (hasGood) {
                goodObject.classList.remove('fa-thumbs-o-up');
                goodObject.classList.add('fa-thumbs-up', 'text-info');
            } else {
                if (objectName === 'article') {
                    goodObject.classList.remove('fa-thumbs-up', 'text-info');
                    goodObject.classList.add('fa-thumbs-o-up');
                } else if (objectName === 'comment') {
                    goodObject.classList.remove('fa-thumbs-up');
                    goodObject.classList.add('fa-thumbs-o-up');
                }
            }
        };

        return {
            clickGood: clickGood,
            showGoodCount: showGoodCount,
        }
    };

    const ReactionApi = function () {
        const addGood = (data, objectName, objectId) => {
            return Api.post(reactionUri(objectName, objectId), data)
        };

        const deleteGood = (objectName, objectId) => {
            return Api.delete(reactionUri(objectName, objectId));
        };

        const showGoodCount = (objectName, objectId) => {
            return Api.get(reactionUri(objectName, objectId));
        };

        const reactionUri = (objectName, objectId) => {
            return `/api/${objectName}/${objectId}/good`;
        };

        return {
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
