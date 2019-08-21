const ArticleApp = (() => {
    const articleTemplate = Handlebars.compile(template.article);

    const ArticleController = function () {
        const articleService = new ArticleService();

        const add = () => {
            const saveBtn = document.getElementById('article-save-btn');
            saveBtn.addEventListener('click', articleService.add);
        };

        const showUpdateModal = () => {
            const articleList = document.getElementById('article-list');
            articleList.addEventListener('click', articleService.showModal);
        };

        const remove = () => {
            const articleList = document.getElementById('article-list');
            articleList.addEventListener('click', articleService.remove);
        };

        const update = () => {
            const updateBtn = document.getElementById('update-btn');
            updateBtn.addEventListener('click', articleService.update);
        };

        const read = () => {
            articleService.read();
        };

        const temp = () => {
            const photoVideoBtn = document.getElementById('photo-video-btn');
            photoVideoBtn.addEventListener('click', articleService.temp);
        };

        const init = () => {
            add();
            showUpdateModal();
            remove();
            read();
            update();
            temp();
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
            contents.value = "";
        };

        const update = (event) => {
            const target = event.target;
            const updateArea = document.getElementById('article-update-contents');
            const article = target.closest('div[data-object="article"]');
            console.log(article);
            const articleId = article.getAttribute('data-article-id');
            const data = {
                contents: updateArea.value,
                imageUrl: "",
                videoUrl: "",
            };
            articleApi.update(data, articleId)
                .then(() => {
                    read();
                });
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
            if (target.closest('li[data-btn="update"]')) {
                const article = target.closest('div[data-object="article"]');
                const updateArea = document.getElementById('article-update-contents');
                const articleId = article.getAttribute('data-article-id');
                updateArea.value = article.querySelector('span[data-object="article-contents"]').innerText;
            }
        };

        const temp = (event) => {
            const target = event.target;
            const inputTag = document.getElementById("temptemp");
            inputTag.click();
        };

        return {
            add: add,
            read: read,
            update: update,
            remove: remove,
            showModal: showModal,
            temp: temp,
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

        const update = (data, articleId) => {
            console.log("request!!");
            return Api.put(`/api/articles/${articleId}`, data);
        };

        return {
            add: add,
            remove: remove,
            render: render,
            update: update,
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