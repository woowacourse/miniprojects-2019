const commentApp = (function() {
    const insertComment = function (data) {
        const commentSection = document.getElementById('comment-section');
        const commentTemplate = ` 
        <li class="comment-item border bottom mrg-btm-30" id="${data.id}">
            <img class="thumb-img img-circle" src="https://avatars3.githubusercontent.com/u/50367798?v=4" alt="">
            <div class="info">
                <span href="" class="text-bold inline-block">${data.author.name}</span>
        
                <p class="width-80" id="contents-${data.id}"> ${data.contents}</p>
                <span class="text-bold inline-block"> 생성날짜:${data.createDate}</span>
                <button type="button" id="edit-btn-${data.id}" class="btn btn-danger">
                    <i class="ti-pencil text-dark font-size-16 pdd-horizontal-5"></i>
                </button>
                <button id="edit-confirm-btn-${data.id}" class="float-right pointer btn btn-icon" style="visibility:hidden">저장확인</button>
                <button type="button" id="delete-btn-${data.id}" class="btn btn-danger">
                    <i class="ti-trash text-dark font-size-16 pdd-horizontal-5"></i>
                </button>
                <input type="text" id="edit-text-${data.id}" style="visibility:hidden">
            </div>
        </li>
        `
        commentSection.insertAdjacentHTML("afterbegin", commentTemplate)
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

        const finalizeEditComment = function() {
            const commentSection = document.getElementById('comment-section')
            commentSection.addEventListener('click', commentService.finalizeEdit)
        }

        const deleteComment = function() {
            const commentSection = document.getElementById('comment-section')
            commentSection.addEventListener('click', commentService.deleteComment)
        }

        const loadComments = function() {
            api.retrieveComments(wootubeCtx.util.getUrlParams('id'))
                .then(response => response.json())
                .then(data => insertComments(data))
        }

        const insertComments = function(data) {
            for (let i = 0; i < data.length; i++) {
                insertComment(data[i])
            }
        }

        const init = function() {
            saveComment();
            prepareEditComment();
            finalizeEditComment();
            deleteComment();
            loadComments();
        }

        return {
            init: init
        }
    }

    const CommentService = function() {
        const save = function() {
            
            const body = {};
            body.contents = document.getElementById('comment-input').value;
            const dataBody = JSON.stringify(body);
            api.saveComment(dataBody, wootubeCtx.util.getUrlParams('id'))
                .then(response => response.json())
                .then(data => insertComment(data))
        }

        const prepareEdit = function(event) {            
            const iconElement = event.target.querySelector('i');
            if(iconElement) {
                if(iconElement.classList.contains("ti-pencil")) {
                    const liElement = event.target.closest('li');
                    const commentId = liElement.id;
                    const editConfirmBtn = document.getElementById('edit-confirm-btn-' + commentId);
                    const editText = document.getElementById('edit-text-' + commentId);
                    const contents = document.getElementById('contents-' + commentId);
                    editText.value = contents.textContent;
                    editConfirmBtn.style.visibility = 'visible'
                    editText.style.visibility = 'visible'
                }
            }
        }
        
        const finalizeEdit = function(event) {
            if(event.target.classList.contains('btn-icon')) {
                const liElement = event.target.closest('li');
                const commentId = liElement.id;
                const body = {};
                body.contents = document.getElementById('edit-text-' + commentId).value;
                const dataBody = JSON.stringify(body);
                api.editComment(dataBody, wootubeCtx.util.getUrlParams('id'), commentId)
                .then(response => response.json())
                .then(json => updateTemplate(json, commentId));
            }
        }

        const updateTemplate = function(json, commentId) {
            if (json.result) {
                alert(json.message);
                return false;
            }
            const contents = document.getElementById('contents-'+commentId);
            contents.innerText = json.contents;
        }

        const deleteComment = function(event) {
            const iconElement = event.target.querySelector('i');
            if(iconElement) {
                if(iconElement.classList.contains('ti-trash')) {
                    const liElement = event.target.closest('li');
                    const commentId = liElement.id;
                    api.deleteComment(wootubeCtx.util.getUrlParams('id'), commentId)
                    .then(response => {
                        if (response.status !== 204) {
                            response.json().then(res => alert(res.message))
                        } else {
                            deleteCommentFromTemplate(commentId)
                        }
                    })
                }
            }
        }

        const deleteCommentFromTemplate = function(commentId) {
            const litagToDelete = document.getElementById(''+commentId);
            litagToDelete.innerHTML = '';
        }

        return {
            save: save,
            prepareEdit: prepareEdit,
            finalizeEdit: finalizeEdit,
            deleteComment: deleteComment
        }
    }

    const init = function() {
        const commentEvent = new CommentEvent();
        commentEvent.init();
    }

    return {
        init: init
    }
})();

commentApp.init();
