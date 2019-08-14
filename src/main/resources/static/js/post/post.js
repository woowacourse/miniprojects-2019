const POST_URL = '/api/posts'

const posts = document.getElementById('contents')

const writeBtn = document.getElementById('write-btn')
const writeArea = document.getElementById('write-area')

let totalPage;
let currentPageNumber = 1;
writeBtn.addEventListener('click', (event) => {
    const contents = writeArea.value
    if (contents) {
        api.POST(POST_URL, contents)
            .then(res => res.json())
            .then(post => posts.prepend(createPostDOM(post)))
            .catch(error => console.error(error))
    }

    writeArea.value = ''
})

const initLoad = async () => {
    await api.GET(POST_URL)
        .then(res => res.json())
        .then(postPage => {
            totalPage = postPage.totalPages;
            postPage.content.forEach(post => {
                posts.appendChild(createPostDOM(post))
            })
        })
        .catch(error => console.error(error))
}

const infinityScroll = (event) => {
    if (totalPage === currentPageNumber) {
        return false
    }

    const currentScrollPercentage = (window.scrollY + window.innerHeight) / document.body.scrollHeight * 100

    if (currentScrollPercentage > 90) {
        api.GET(POST_URL + `?page=${currentPageNumber++}`)
            .then(res => res.json())
            .then(postPage => {
                  totalPage = postPage.totalPages;
                  postPage.content.forEach(post => {
                    posts.appendChild(createPostDOM(post))
                  })
            })
            .catch(error => console.error(error))
    }
}

const createPostDOM = (post) => {
    const div = document.createElement('div');
    div.innerHTML = postTemplate(post)
    return div
}

window.onload = initLoad
document.addEventListener('scroll', infinityScroll);

