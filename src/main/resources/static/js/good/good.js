const POST_GOOD_URL = (postId) => `/api/posts/${postId}/good`
const COMMENT_GOOD_URL = (postId, commentId) => `/api/posts/${postId}/comments/${commentId}/good/`

const postGood = (event) => {
    const goodButtonContainer = event.target.closest('button')
    const targetPost = event.target.closest('.card')
    const targetPostId = targetPost.dataset.id;
    const showPostGood = targetPost.querySelector('ul.feed-action li span.show-post-good')
    if (goodButtonContainer != null && goodButtonContainer.classList.contains('good')) {
        api.POST(POST_GOOD_URL(targetPostId))
            .then(res => res.json())
            .then(good => {
                const {totalGood} = good
                showPostGood.innerText = totalGood
                return good.gooded
            })
            .then(isGood => {
                if (isGood) {
                    event.target.classList.add('good-active')
                } else {
                    event.target.classList.remove('good-active')
                }
            })
            .catch(error => console.error(error))
    }
}

const commentGood = (event) => {
    if (event.target.classList.contains('comment-good')) {
        const targetPost = event.target.closest('.card')
        const postId = targetPost.dataset.id;
        const commentItem = event.target.closest('.comment-item');
        const commentId = commentItem.dataset.id
        const commentTotalGood = commentItem.querySelector('.comment-total-good')

        api.POST(COMMENT_GOOD_URL(postId, commentId))
            .then(res => res.json())
            .then(good => {
                const {totalGood} = good
                commentTotalGood.innerText = totalGood
                return good.gooded
            })
            .then(isGood => {
                if (isGood) {
                    event.target.classList.add('comment-good-active')
                } else {
                    event.target.classList.remove('comment-good-active')
                }
            })
            .catch(error => console.error(error))
    }
}

posts.addEventListener('click', postGood)
posts.addEventListener('click', commentGood)