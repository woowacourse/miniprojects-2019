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

        const clickGood = () => {
            const articleList = document.getElementById('article-list');
            articleList.addEventListener('click', articleService.clickGood);
        };

        const init = () => {
            add();
            showUpdateModal();
            remove();
            read();
            update();
            temp();
            clickGood();
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
                        articleApi.showGood(article.id)
                            .then(response => response.json())
                            .then(data => showArticleList(data.numberOfGood));

                        const showArticleList = function (numberOfGoods) {
                            articleList
                                .insertAdjacentHTML('afterbegin', articleTemplate({
                                    "id": article.id,
                                    "updatedTime": article.updatedTime,
                                    "article-contents": article.articleFeature.contents,
                                    "article-videoUrl": "https://www.youtube.com/embed/rA_2B7Yj4QE",
                                    "article-imageUrl": "https://i.pinimg.com/originals/e5/64/d6/e564d613befe30dfcef2d22a4498fc70.png",
                                    "numberOfGood": numberOfGoods,
                                    "authorName": article.authorName.name,
                                }));
                        }
                    })
                })
                .catch(error => console.log("error: " + error));
        };

        const add = (event) => {
            const contents = document.getElementById("article-contents");

            if (AppStorage.check('article-add-run')) {
                return;
            }
            AppStorage.set('article-add-run', true);

            articleApi.add({
                contents: contents.value,
                imageUrl: "",
                videoUrl: "",
            })
                .then(response => response.json())
                .then((article) => {
                    articleApi.showGood(article.id)
                        .then(response => response.json())
                        .then(data => showArticle(data.numberOfGood));

                    const showArticle = function (numberOfGoods) {
                        document.getElementById('article-list')
                            .insertAdjacentHTML('afterbegin', articleTemplate({
                                "id": article.id,
                                "updatedTime": article.updatedTime,
                                "article-contents": article.articleFeature.contents,
                                "article-videoUrl": "https://www.youtube.com/embed/rA_2B7Yj4QE",
                                "article-imageUrl": "https://i.pinimg.com/originals/e5/64/d6/e564d613befe30dfcef2d22a4498fc70.png",
                                "numberOfGood": numberOfGoods,
                                "authorName": article.authorName.name,
                            }));
                    };

                    AppStorage.set('article-add-run', false);
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

                const showModalBtn = document.getElementById('show-article-modal-btn');
                showModalBtn.click();
            }
        };

        const temp = (event) => {
            const target = event.target;
            const inputTag = document.getElementById("temptemp");
            inputTag.click();
        };


        const clickGood = (event) => {
            const target = event.target;
            if (target.closest('li[data-btn="reaction-good-btn"]')) {
                const article = target.closest('div[data-object="article"]');
                const articleId = article.getAttribute('data-article-id');
                const data = document.getElementById(`good-count-${articleId}`).innerText;

                articleApi.clickGood(Number(data), articleId)
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

        return {
            add: add,
            read: read,
            update: update,
            remove: remove,
            showModal: showModal,
            temp: temp,
            clickGood: clickGood,
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

        const clickGood = (data, articleId) => {
            return Api.post(`/api/articles/${articleId}/good`, data)
        };

        const showGood = (articleId) => {
            return Api.get(`/api/articles/${articleId}/good`);
        };

        return {
            add: add,
            remove: remove,
            render: render,
            update: update,
            clickGood: clickGood,
            showGood: showGood,
        };
    };

    const init = () => {
        const articleController = new ArticleController();
        articleController.init();
    };

    return {
        init: init,
    };

})();

ArticleApp.init();