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
            if (target.closest('li[data-btn="reaction-good-btn"]')) {
                const article = target.closest('div[data-object="article"]');
                const articleId = article.getAttribute('data-article-id');
                const data = document.getElementById(`good-count-${articleId}`).innerText;

                reactionApi.clickGood(Number(data), articleId)
                    .then(response => response.json())
                    .then(data => {
                        document.getElementById(`good-count-${articleId}`)
                            .innerText = data.numberOfGood;
                        console.log(data);

                        if (data.hasGood) {
                            document.getElementById(`good-btn-icon-${articleId}`)
                                .setAttribute('class', 'fa fa-thumbs-up font-size-16');
                        } else {
                            document.getElementById(`good-btn-icon-${articleId}`)
                                .setAttribute('class', 'fa fa-thumbs-o-up font-size-16');
                        }
                    });
            }
        };

        const showGoodCount = (articleId) => {
            reactionApi.showGoodCount(articleId)
                .then(response => response.json())
                .then(data => {
                    document.getElementById(`good-count-${articleId}`)
                        .innerText = data.numberOfGood;
                });
        };

        return {
            clickGood: clickGood,
            showGoodCount: showGoodCount,
        }
    };

    const ReactionApi = function() {
        const clickGood = (data, articleId) => {
            return Api.post(`/api/articles/${articleId}/good`, data)
        };

        const showGoodCount = (articleId) => {
            return Api.get(`/api/articles/${articleId}/good`);
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
