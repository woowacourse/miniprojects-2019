const Comment = (function () {
    const CommentController = function () {
        const commentService = new CommentService();

        commentService.read();

        const createButton = () => {
            const button = document.querySelector(".comment-create-btn");
            button.addEventListener('click', commentService.create);
        }

        const removeButton = () => {
            const button = document.querySelector(".comment-list");
            button.addEventListener('click', commentService.remove);
        }

        const init = function () {
            createButton()
            removeButton()
        }
        return {
            init: init
        }
    }

    const CommentService = function () {
        const request = new Request("/api/articles/" + articleId+"/comments");

        const commentTemplate =
            `<div class="contents-inner">
                <div class="profile">
                    <img src={{userInfoDto.userContentsDto.profile}}>
                    <div class="profile-text">
                        <span class="profile-name">{{userInfoDto.userContentsDto.userName}}</span>
                        <span class="contents-para">{{contents}}</span>
                    </div>
                    <button class="comment-delete" data-id={{id}}>삭제</button>
                </div>
            </div>`

        const commentItemTemplate = Handlebars.compile(commentTemplate);

        const read = () => {
            request.get('/', (status, data) => {
                document.querySelector('.comment-list').innerHTML = "";

                data.forEach(e => {
                    document.querySelector('.comment-list').insertAdjacentHTML('beforeend', commentItemTemplate(e))
                });
            })
        }

        const create = () => {
            const commentInput = document.querySelector(".comment-input");
            let contents = commentInput.value;

            if (contents.length === 0) {
                return false;
            }

            request.post('/', {
                contents : contents
            }, (status, data) => {
                commentInput.value = "";
                read();
            })
        }

        const remove = (event) => {
            const commentId = event.target.getAttribute("data-id");

            request.delete('/' + commentId, (status, data) => {
                console.log(data);
                read();
            })
        }

        return {
            create : create,
            read : read,
            remove : remove
        }
    }
    const init = () => {
        const commentController = new CommentController();
        commentController.init();
    };
    return {
        init: init
    }
}());

Comment.init();