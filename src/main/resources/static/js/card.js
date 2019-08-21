const editArticle = function (cardContents, articleId) {
    const header = {
        'Content-Type': 'application/json; charset=UTF-8',
        'Accept': 'application/json'
    };
    const articleInfo = {
        contents: cardContents.children[1].children[0].value
        // todo: hashtag 추가하셈
    };
    const connector = FETCH_APP.fetchApi();

    connector.fetchTemplate('/api/articles/' + articleId,
        connector.PUT,
        header,
        JSON.stringify(articleInfo),
        () => window.location.reload());
};

document.getElementById('cards').addEventListener('click', event => {
    let target = event.target;
    if (target.tagName === 'I' || target.tagName === 'SPAN') {
        target = target.parentNode;
    }
    const articleId = target.getAttribute('data-id');

    if (target.classList.contains('article-edit')) {
        const cardContents = document.getElementById('article-contents-' + articleId);
        cardContents.children[0].style.display = 'none';
        cardContents.children[1].style.display = 'block';
        cardContents.children[1].children[1].addEventListener('click', function() { editArticle(cardContents, articleId) }, false);
    }
});

document.getElementById('cards').addEventListener('click', event => {
    let target = event.target;
    if (target.tagName === 'I' || target.tagName === 'SPAN') {
        target = target.parentNode;
    }
    const articleId = target.getAttribute('data-id');

    if (target.classList.contains('article-delete')) {
        if (confirm("삭제하시겠습니까?") === true) {
        const connector = FETCH_APP.fetchApi();
        connector.fetchTemplateWithoutBody('/api/articles/' + articleId,
            connector.DELETE,
            () => window.location.href = '/');
        }
    }
});