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
            const articleList = document.getElementById('article-list');
            articleList.addEventListener('click', commentService.read);
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

        const read = (event) => {
            const target = event.target;
            const article = target.closest('div[data-object="article"]');
            const articleId = article.getAttribute('data-article-id');
            const commentList = document.getElementById('comment-list-' + articleId);

            if (target.closest('li[data-list="comment-list"]')) {
                if (commentList.hasChildNodes()) {
                    while (commentList.hasChildNodes()) {
                        commentList.removeChild(commentList.firstChild);
                    }
                    return;
                }

                commentApi.render(articleId)
                    .then(response => response.json())
                    .then(data => {
                        data.forEach(comment => {
                            commentList
                                .insertAdjacentHTML('beforeend', commentTemplate({
                                    "id": comment.id,
                                    "user-name": comment.authorName,
                                    "comment-contents": comment.commentFeature.contents,
                                    "updatedTime": comment.updatedTime,
                                }));
                        })
                    })
                    .catch(error => console.log("error: " + error));
            }
        };

        const add = (event) => {
            const target = event.target;
            const article = target.closest('div[data-object="article"]');
            const articleId = article.getAttribute('data-article-id');
            const commentList = document.getElementById('comment-list-' + articleId);
            const contents = document.getElementById('comment-contents-' + articleId);

            if (event.which === 13 && contents.value !== '' && event.target.classList.contains('comment-save')) {
                if (!event.shiftKey) {
                    const data = {
                        contents: contents.value,
                    };

                    commentApi.add(articleId, data)
                        .then(() => {
                            if (commentList.hasChildNodes()) {
                                while (commentList.hasChildNodes()) {
                                    commentList.removeChild(commentList.firstChild);
                                }
                            }

                            commentApi.render(articleId)
                                .then(response => response.json())
                                .then(data => {
                                    data.forEach(comment => {
                                        commentList
                                            .insertAdjacentHTML('beforeend', commentTemplate({
                                                "id": comment.id,
                                                "user-name": comment.authorName,
                                                "comment-contents": comment.commentFeature.contents,
                                                "updatedTime": comment.updatedTime,
                                            }));
                                    })
                                })
                                .catch(error => console.log("error: " + error));
                        })
                        .then(() => {
                            contents.value = "";
                            contents.blur();
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
            if (target.closest('li[data-btn="comment-delete"]')) {
                const article = target.closest('div[data-object="article"]');
                const articleId = article.getAttribute('data-article-id');
                const comment = target.closest('li[data-object="comment"');
                const commentId = comment.getAttribute('data-comment-id');

                commentApi.remove(articleId, commentId)
                    .then(() => {
                        comment.remove();
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

        const remove = (articleId, commentId) => {
            return Api.delete(`/api/articles/${articleId}/comments/${commentId}`);
        };

        const render = (articleId) => {
            return Api.get(`/api/articles/${articleId}/comments`);
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