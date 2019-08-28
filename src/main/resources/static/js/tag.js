const pageSize = 12;
const pageNum = 0;

const container = document.querySelector('.article-card');

const searchRequest = new Request('/api/tags/hash');
searchRequest.get(`/${tagName}?page=${pageNum}&size=${pageSize}&sort=id,DESC`, (status, data) => {
    let rowDiv = (pages) => {
        return `<div class="article-row-card">${pages}</div>`
    };

    let rowNum = Math.ceil(data.content.length / 3);

    for (let i = 0; i <= rowNum; i++) {
        let row = "";
        for (let j = 0; j < 3; j++) {
            if ((i * 3 + j) < data.content.length) {
                row += getMypageArticleTemplate(data.content[i * 3 + j].article.id, data.content[i * 3 + j].article.imageUrl);
            }
        }
        container.insertAdjacentHTML('beforeend', rowDiv(row));
    }

    return data.last;
}).then((last) => {
    if (last === false) {
        const cardList = document.querySelectorAll('.article-row-card');
        const target = cardList[cardList.length - 1];
        console.log(target);
        lazyLoad(target, pageNum);
    }
});

const lazyLoad = (target, pageNum) => {
    const io = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                getPageData(pageNum + 1);
                observer.disconnect();
            }
        })
    });
    io.observe(target)
};