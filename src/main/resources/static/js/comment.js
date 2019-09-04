'use strict';

const CommentApp = (function() {
    const commentTemplate = function(comment) {
        return `<li class="comment-item" data-comment-id="${comment.commentId}">
                <img class="thumb-img img-circle" src="${comment.profile}" alt="">
                <div class="info">
                    <div class="bg-lightgray border-radius-18 padding-10 max-width-100">
                        <a href="/posts?author=${comment.authorId}" class="title text-bold inline-block text-link-color">
                            ${comment.authorName}
                        </a>
                        <span class="comment-contents">${comment.contents}</span>
                    </div>
                    <a aria-expanded="false" class="pointer absolute top-0 right-0" data-toggle="dropdown">
                    <span class="btn-icon text-dark">
                        <i class="ti-more font-size-16"></i>
                    </span>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a class="pointer comment-edit-btn">
                                <i class="ti-pencil pdd-right-10 text-dark"></i>
                                <span class="">댓글 수정</span>
                            </a>
                        </li>
                        <li>
                            <a class="pointer comment-delete-btn">
                                <i class="ti-trash pdd-right-10 text-dark"></i>
                                <span class="">댓글 삭제</span>
                            </a>
                        </li>
                    </ul>
                    <div class="font-size-12 pdd-left-10 pdd-top-5">
                        <time datetime="${comment.createdDateTime}">${comment.createdDateTime}</time>
                    </div>
                </div>
                <div class="comment-input bg-lightgray border-radius-18 padding-10 max-width-100" style="display: none;">
                    <input type="text" />
                    <span class="edit-pointer pointer text-link-color">수정</span>
                    <span class="cancel-pointer pointer text-link-color">취소</span>
                </div>
            </li>`
    };

    const CommentController = function() {
        const commentService = new CommentService();
        const commentsItems = document.getElementsByClassName('comment-items');

        const initComments = function() {
            const posts = document.getElementsByClassName('post');
            for (let post of posts) {
                let postId = post.dataset.postId;
                const postCount = post.querySelector(".comment-count");

                Api.get(`/posts/${postId}/comments`)
                    .then(res => res.json())
                    .then(fetchedComments => {
                        const commentItems = post.querySelector('.comment-items');
                        for (let comment of fetchedComments) {
                            commentItems.insertAdjacentHTML('beforeend', commentTemplate(comment));
                        }
                        postCount.innerText = fetchedComments.length;
                        updateTimeStrings();
                    })
            }
        };

        const addComment = function() {
            const commentAddBtns = document.getElementsByClassName('comment-add-btn');

            Array.from(commentAddBtns)
                .map(commentAddBtn => commentAddBtn.addEventListener('click', commentService.addComment));
        };

        const editComment = function() {
            Array.from(commentsItems)
                .map(commentItem => commentItem.addEventListener('click', commentService.editMode));
            Array.from(commentsItems)
                .map(commentItem => commentItem.addEventListener('click', commentService.cancelEditMode));
            Array.from(commentsItems)
                .map(commentItem => commentItem.addEventListener('click', commentService.editComment));
        };

        const deleteComment = function() {
            Array.from(commentsItems)
                .map(commentItem => commentItem.addEventListener('click', commentService.deleteComment));
        };

        const init = function() {
            initComments();
            addComment();
            editComment();
            deleteComment();
        };

        return { init };
    };

    const CommentService = function() {
        const addComment = function(event) {
            event.stopPropagation();

            const parentPost = event.target.closest(".card");
            const postId = parentPost.dataset.postId;
            const commentArea = event.target.closest(".add-comment");
            const commentTextArea = commentArea.querySelector("textarea");
            const contents = commentTextArea.value;

            Api.post(`posts/${postId}/comments`, {contents})
                .then(res => res.json())
                .then(createdComment => {
                    const comments = event.target.closest(".comment").querySelector(".comment-items");
                    const commentCount = parentPost.querySelector(".comment-count");
                    commentCount.innerText = parseInt(commentCount.innerText) + 1;
                    comments.insertAdjacentHTML('beforeend', commentTemplate(createdComment));
                    commentTextArea.value = "";
                    updateTimeStrings();
                });
        };

        const editMode = function(event) {
            const clicked = event.target.closest('a');
            if (clicked && clicked.classList.contains('comment-edit-btn')) {
                const commentInfo = clicked.closest('.info');
                const commentInputArea = clicked.closest('.comment-item').querySelector('.comment-input');
                const commentInput = commentInputArea.querySelector('input');

                commentInput.value = commentInfo.querySelector('.comment-contents').innerHTML;
                commentInfo.setAttribute('style', 'display: none');
                commentInputArea.removeAttribute('style');
            }
        };

        const cancelEditMode = function(event) {
            if (event.target.classList.contains('cancel-pointer')) {
                cancelEditModeBy(event.target);
            }
        };

        const cancelEditModeBy = function(eventTarget) {
            const commentInfo = eventTarget.closest('.comment-item').querySelector('.info');
            const commentInput = eventTarget.closest('.comment-item').querySelector('.comment-input');

            commentInfo.removeAttribute('style');
            commentInput.setAttribute('style', 'display: none');
        };

        const editComment = function(event) {
            if (event.target.classList.contains('edit-pointer')) {
                const postId = event.target.closest('.post').dataset.postId;
                const commentItem = event.target.closest('.comment-item');
                const commentId = commentItem.dataset.commentId;
                const commentInput = event.target.closest('.comment-input').querySelector('input');
                const contents = commentInput.value;

                Api.put(`posts/${postId}/comments/${commentId}`, {contents})
                   .then(function(res) {
                       if (res.ok) {
                           return res.json()
                       }
                   })
                   .then(updatedComment => {
                       const commentContents = commentItem.querySelector('.comment-contents');
                       commentContents.innerText = updatedComment.contents;
                       cancelEditModeBy(event.target);
                   }).catch(function(error) {
                    alert("해당 댓글은 수정할 수 없습니다!");
                    cancelEditModeBy(event.target);
                })
            }
        };

        const deleteComment = function(event) {
            const parentPost = event.target.closest('.post');
            const postId = parentPost.dataset.postId;
            const commentItem = event.target.closest('.comment-item');
            const commentId = commentItem.dataset.commentId;

            const clicked = event.target.closest('a');
            if (clicked && clicked.classList.contains('comment-delete-btn')) {
                Api.delete(`posts/${postId}/comments/${commentId}`)
                    .then(res => {
                        if (res.ok) {
                            commentItem.remove();
                            const commentCount = parentPost.querySelector(".comment-count");
                            commentCount.innerText = parseInt(commentCount.innerText) - 1;
                        }
                    })
            }
        };

        return {
            addComment,
            editMode,
            cancelEditMode,
            editComment,
            deleteComment
        };
    };

    const init = function() {
        const commentController = new CommentController();
        commentController.init();
    };

    return {init};
})();

CommentApp.init();
