const ArticleApp = (() => {
    const articleTemplate = Handlebars.compile(template.article);

    const ArticleController = function () {
        const articleService = new ArticleService();

        const add = () => {
            const saveBtn = document.getElementById('article-save-btn');
            saveBtn.addEventListener('click', articleService.add);
        };

        const showUpdateModal = () => {
            const updateBtn = document.getElementById('update-btn');
            updateBtn.addEventListener('click', articleService.showModal);
        };

        const remove = () => {
            const articleList = document.getElementById('article-list');
            articleList.addEventListener('click', articleService.remove);
        };

        const read = () => {
            articleService.read();
        };

        const init = () => {
            add();
            showUpdateModal();
            remove();
            read();
        };

        return {
            init: init,
        }
    };

    const ArticleService = function () {
        const articleApi = new ArticleApi();

        const read = () => {
            const articleList = document.getElementById('article-list');

            articleApi.render()
                .then(response => response.json())
                .then(data => {
                    data.forEach(article => {
                        articleList
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
        };

        const add = () => {
            const contents = document.getElementById("article-contents");
            const data = {
                contents: contents.value,
                imageUrl: "",
                videoUrl: "",
            };

            articleApi.add(data)
                .then(response => response.json())
                .then((article) => {
                    document.getElementById('article-list')
                        .insertAdjacentHTML('afterbegin', articleTemplate({
                            "id": article.id,
                            "updatedTime": article.updatedTime,
                            "article-contents": article.articleFeature.contents,
                            "article-videoUrl": "https://www.youtube.com/embed/rA_2B7Yj4QE",
                            "article-imageUrl": "https://i.pinimg.com/originals/e5/64/d6/e564d613befe30dfcef2d22a4498fc70.png"
                        }));
                });
        };

        const update = (event) => {
            const target = event.target;
            if (target.closest('li[data-btn="update"]')) {
                const article = target.closest('div[data-object="article"]');
                showModal(article);
            }
        };

        const remove = (event) => {
            const target = event.target;
            if (target.closest('li[data-btn="delete"]')) {
                const article = target.closest('div[data-object="article"]');
                const articleId = article.getAttribute('data-article-id');
                articleApi.remove(articleId)
                    .then(() => {
                        article.remove();
                    });
            }
        };

        const showModal = (event) => {
            const target = event.target;
            const article = target.closest('div[data-object="article"]');
            const updateArea = document.getElementById('article-update-contents');
            const articleId = article.getAttribute('data-article-id');
            updateArea.innerText = article.querySelector('span[data-object="article-contents"]').innerText;
        };

        return {
            add: add,
            read: read,
            update: update,
            remove: remove,
            showModal: showModal,
        }
    };

    const ArticleApi = function () {
        const add = (data) => {
            return Api.post(`/api/articles`, data)
        };

        const remove = (articleId) => {
            return Api.delete(`/api/articles/${articleId}`);
        };

        const render = () => {
            return Api.get(`/api/articles`);
        };

        return {
            add: add,
            remove: remove,
            render: render,
        };
    };

    const init = () => {
        const articleController = new ArticleController();
        articleController.init();
    };

    return {
        init: init,
    }
})();

ArticleApp.init();