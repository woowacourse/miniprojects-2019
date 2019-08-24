const CommentApp = (() => {
    const commentTemplate = Handlebars.compile(template.comment);

    const CommentController = function () {
        const commentService = new CommentService();

        const add = () => {
            const saveBtn = document.getElementById('article-list');
            saveBtn.addEventListener('keydown', commentService.add);
        };

        const showModal = () => {
            const articleList = document.getElementById('article-list');
            articleList.addEventListener('click', commentService.showModal);
        };

        const update = () => {
            const commentUpdateBtn = document.getElementById('comment-update-btn');
            commentUpdateBtn.addEventListener('click', commentService.update);
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
            showModal();
            update();
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
                            ReactionApp.service().showGoodCount('comment', comment.id);
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
            const contentsValue = document.getElementById('comment-value-' + articleId);

            if (event.which === 13 && contentsValue.value !== '' && event.target.classList.contains('comment-save')) {
                if (!event.shiftKey) {
                    const data = {
                        contents: contentsValue.value,
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
                                        ReactionApp.service().showGoodCount('comment', comment.id);
                                    })
                                })
                                .catch(error => console.log("error: " + error));
                        })
                        .then(() => {
                            contentsValue.value = "";
                            contentsValue.blur();
                        });
                }
            }
        };

        const showModal = (event) => {
            const target = event.target;
            if (target.closest('li[data-btn="comment-update"]')) {
                const article = target.closest('div[data-object="article"]');
                const articleId = article.getAttribute('data-article-id');
                const comment = target.closest('li[data-object="comment"]');
                const commentId = comment.getAttribute('data-comment-id');
                const updateArea = document.getElementById('comment-update-contents');
                const showModalBtn = document.getElementById('show-comment-modal-btn');
                const articleHiddenId  = document.getElementById('hidden-article-id');
                const commentHiddenId  = document.getElementById('hidden-comment-id');

                updateArea.value = comment.querySelector('span[data-object="comment-contents"]').innerText;
                articleHiddenId.value = articleId;
                commentHiddenId.value = commentId;

                showModalBtn.click();
            }
        };

        const update = () => {
            const articleHiddenId  = document.getElementById('hidden-article-id');
            const commentHiddenId  = document.getElementById('hidden-comment-id');
            const updateArea = document.getElementById('comment-update-contents');
            const contents = document.getElementById('comment-contents-' + commentHiddenId.value);
            const data = {
                contents: updateArea.value,
            };

            commentApi.update(articleHiddenId.value, commentHiddenId.value, data)
                .then(response => response.json())
                .then(comment => {
                    contents.innerText = comment.commentFeature.contents;
                });
        };

        const remove = (event) => {
            const target = event.target;
            if (target.closest('li[data-btn="comment-delete"]')) {
                const article = target.closest('div[data-object="article"]');
                const articleId = article.getAttribute('data-article-id');
                const comment = target.closest('li[data-object="comment"]');
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
            showModal: showModal,
            remove: remove,
        }
    };

    const CommentApi = function () {
        const add = (articleId, data) => {
            return Api.post(`/api/articles/${articleId}/comments`, data)
        };

        const update = (articleId, commentId, data) => {
            return Api.put(`/api/articles/${articleId}/comments/${commentId}`, data);
        };

        const remove = (articleId, commentId) => {
            return Api.delete(`/api/articles/${articleId}/comments/${commentId}`);
        };

        const render = (articleId) => {
            return Api.get(`/api/articles/${articleId}/comments`);
        };

        return {
            add: add,
            update: update,
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