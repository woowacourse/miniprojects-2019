const commentCtx = {
    state: {
        currentCommentId: null,
        currentUserId: null,
    }
}

const commentApp = (function() {
    const insertComment = function (data) {
        const { currentUserId } = commentCtx.state
        const commentSection = document.getElementById('comment-section');
        const commentTemplate = ` 
        <li class="comment-item border bottom mrg-btm-30" data-id="${data.id}">
            <img class="thumb-img img-circle" src="https://avatars3.githubusercontent.com/u/50367798?v=4" alt="">
            <div class="info">
                <div class="d-flex flex-row align-items-center">
                    <span class="text-bold inline-block">${data.author.name}</span> 
                    <span class="d-inline-block ml-2 mr-1">|</span>
                    <span class="inline-block">${moment.utc(data.createDate).local().format('YYYY. MM. DD. HH:mm')}</span>
                    ${currentUserId === data.author.id ? 
                        `<a class="pointer btn-update-comment ml-3 line-height-100" data-toggle="modal" data-target="#comment-update-modal"><small>수정</small></a>
                        <a class="pointer btn-delete-comment ml-2 line-height-100"><small>삭제</small></a>` : ''}
                </div>
                <p class="width-80 comment-contents"> ${data.contents}</p>
                
            </div>
        </li>
        `
        commentSection.insertAdjacentHTML("beforeend", commentTemplate)
    }
    
    const CommentEvent = function() {
        const commentService = new CommentService();

        const saveComment = function() {
            const commentSaveBtn = document.getElementById('save-comment-btn')
            commentSaveBtn.addEventListener('click', commentService.save)
        }

        const prepareEditComment = function() {
            const commentSection = document.getElementById('comment-section')
            commentSection.addEventListener('click', commentService.prepareEdit)
        }

        const submitEditComment = function() {
            const btnSubmitUpdate = document.querySelector('.modal .btn-update-submit')
            btnSubmitUpdate.addEventListener('click', commentService.submitEdit)
        }

        const deleteComment = function() {
            const commentSection = document.getElementById('comment-section')
            commentSection.addEventListener('click', commentService.deleteComment)
        }

        const loadComments = function() {
            api.retrieveLoginInfo()
                .then(res => res.json())
                .then(json => {
                    if (json) {
                        commentCtx.state.currentUserId = json.id
                        return api.retrieveComments(wootubeCtx.util.getUrlParams('id'))
                    }
                    return Promise.reject()
                })
                .then(response => response.json())
                .then(data => insertComments(data))
        }

        const insertComments = function(data) {
            for (let i = 0; i < data.length; i++) {
                insertComment(data[i])
            }
        }

        const init = function() {
            saveComment()
            prepareEditComment()
            submitEditComment()
            deleteComment()
            loadComments()
            commentService.refreshCommentCount()
        }

        return {
            init: init
        }
    }

    const CommentService = function() {
        const save = function() {
            const inputElm = document.getElementById('comment-input')
            const body = {};
            body.contents = inputElm.value
            inputElm.value = '';
            const dataBody = JSON.stringify(body);
            api.saveComment(dataBody, wootubeCtx.util.getUrlParams('id'))
                .then(response => response.json())
                .then(data => {
                    if (data.message) {
                        alert(data.message)
                        return
                    }
                    insertComment(data)
                    refreshCommentCount()
                })
        }

        const prepareEdit = function(event) {
            const {target} = event
            if(target.classList.contains('btn-update-comment') ||
                target.closest('.btn-update-comment')) {
                const itemContainer = event.target.closest('li')
                const contentsElm = itemContainer.querySelector('.comment-contents')
                commentCtx.state.currentCommentId = itemContainer.dataset.id
                document.querySelector('.modal .input-comment-update').value = contentsElm.innerText;
            }
        }
        
        const submitEdit = function() {
            const itemContainer = event.target.closest('.modal')
            const commentId = commentCtx.state.currentCommentId
            const body = {
                contents: itemContainer.querySelector('.input-comment-update').value
            };
            const dataBody = JSON.stringify(body);
            api.editComment(dataBody, wootubeCtx.util.getUrlParams('id'), commentId)
            .then(response => response.json())
            .then(json => updateTemplate(json, commentId));
        }

        const updateTemplate = function(json, commentId) {
            if (json.result) {
                alert(json.message);
                return false;
            }
            const contents = document.querySelector(`li[data-id="${commentId}"] .comment-contents`)
            contents.innerText = wootubeCtx.util.unescapeHtml(json.contents);
        }

        const refreshCommentCount = function() {
            document.querySelector('.comment-count')
            .innerText = document.querySelectorAll('#comment-section li').length;
        }

        const deleteComment = function(event) {
            const {target} = event;
            if(target.classList.contains('btn-delete-comment') ||
                target.closest('.btn-delete-comment')) {
                const itemContainer = target.closest('li')
                const commentId = itemContainer.dataset.id
                api.deleteComment(wootubeCtx.util.getUrlParams('id'), commentId)
                .then(response => {
                    if (response.status !== 204) {
                        response.json().then(res => alert(res.message))
                    } else {
                        deleteCommentFromTemplate(commentId)
                    }
                    refreshCommentCount()
                })
            }
        }

        const deleteCommentFromTemplate = function(commentId) {
            document.querySelector(`li[data-id="${commentId}"]`).remove()
        }

        return {
            save: save,
            prepareEdit: prepareEdit,
            submitEdit: submitEdit,
            deleteComment: deleteComment,
            refreshCommentCount: refreshCommentCount,
        }
    }

    const init = function() {
        const commentEvent = new CommentEvent()
        commentEvent.init();
    }

    return {
        init: init
    }
})();

commentApp.init();
