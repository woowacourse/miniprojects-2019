const POST_GOOD_URL = (postId) => `/api/posts/${postId}/good`

const postGood = (event) => {
    const goodButtonContainer = event.target.closest('button')
    const targetPost = event.target.closest('.card')
    const targetPostId = targetPost.dataset.id;
    const showPostGood = targetPost.querySelector('ul.feed-action li span.show-post-good')
    if (goodButtonContainer != null && goodButtonContainer.classList.contains('good')) {
        api.GET(POST_GOOD_URL(targetPostId))
            .then(res => res.json())
            .then(good => {
                const { totalGood } = good
                showPostGood.innerText = totalGood
                return good.good
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

posts.addEventListener('click', postGood)
