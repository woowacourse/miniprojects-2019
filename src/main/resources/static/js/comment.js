const CommentApp = (() => {
    const commentTemplate = Handlebars.compile(template.comment);

    const CommentController = function () {
        const commentService = new CommentService();

        const add = () => {
            const saveBtn = document.getElementById('article-list');
            saveBtn.addEventListener('keydown', commentService.add);
        };

        const remove = () => {
            const articleList = document.getElementById('article-list');
            articleList.addEventListener('click', commentService.remove);
        };

        const read = () => {
            commentService.read();
        };

        const init = () => {
            add();
            remove();
            read();
        };

        return {
            init: init,
        }
    };

    const CommentService = function () {
        const commentApi = new CommentApi();

        const read = () => {
            const commentList = document.getElementById('article-list');

            commentApi.render()
                .then(response => response.json())
                .then(data => {
                    data.forEach(comment => {
                        commentList
                            .insertAdjacentHTML('afterbegin', commentTemplate({
                                "id": comment.id,
                                "user-name": comment.authorName,
                                "comment-contents": comment.commentFeature.contents,
                                "updatedTime": comment.updatedTime,
                            }));
                    })
                })
                .catch(error => console.log("error: " + error));
        };

        const add = (event) => {
            const target = event.target;
            const article = target.closest('div[data-object="article"]');
            const articleId = article.getAttribute('data-article-id');
            const contents = document.getElementById("comment-contents");

            if (event.which === 13 && contents.value !== '' && event.target.classList.contains('comment-save')) {
                if (!event.shiftKey) {
                    const data = {
                        contents: contents.value,
                    };

                    commentApi.add(articleId, data)
                        .then(response => response.json())
                        .then((comment) => {
                            console.log(comment);
                            document.getElementById('comment-list')
                                .insertAdjacentHTML('beforeend', commentTemplate({
                                    "id": comment.id,
                                    "user-name": comment.authorName,
                                    "comment-contents": comment.commentFeature.contents,
                                    "updatedTime": comment.updatedTime,
                                }));
                        })
                        .then(() => {
                            contents.value = "";
                        });
                }
            }
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
                commentApi.remove(articleId)
                    .then(() => {
                        article.remove();
                    });
            }
        };

        return {
            add: add,
            read: read,
            update: update,
            remove: remove,
        }
    };

    const CommentApi = function () {
        const add = (articleId, data) => {
            return Api.post(`/api/articles/${articleId}/comments`, data)
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
        const commentController = new CommentController();
        commentController.init();
    };

    return {
        init: init,
    }
})();

CommentApp.init();