const ARTICLE_APP = (() => {
    'use strict';

    const ArticleController = function () {
        const articleService = new ArticleService();

        const saveArticle = () => {
            const articleSaveButton = document.getElementById('save-button');
            articleSaveButton ? articleSaveButton.addEventListener('click', articleService.save) : undefined;
        };

        const writeArticle = () => {
            const writeArticleButton = document.getElementById('write-article-button');
            writeArticleButton ? writeArticleButton.addEventListener('click', articleService.writeArticle) : undefined;
        };

        const init = () => {
            saveArticle();
            writeArticle();
        };

        return {
            init: init,
        }
    };

    const ArticleService = function () {
        const save = () => {
            const file = document.getElementById('file').value;

            if (file === '') {
                alert('파일은 필수입니다');
                return;
            }

            const postForm = document.getElementById('save-form');
            const formData = new FormData(postForm);

            const connector = FETCH_APP.FetchApi();
            const redirectToArticlePage = response => {
                response.json().then(article => window.location.href = `/articles/${article.id}`);
            };
            connector.fetchTemplate('/api/articles', connector.POST, {}, formData, redirectToArticlePage);
        };

        const writeArticle = () => {
            location.href = "/articles/writing";
        };

        // TODO 슬로스의 유작... 여기로 옮겨지다. (사용되지 않고 있음)
        const copyUrl = articleId => {
            const copiedUrl = window.location.host + `/articles/${articleId}`;
            const copyTarget = document.createElement('textarea');

            document.body.appendChild(copyTarget);
            copyTarget.value = copiedUrl;
            copyTarget.select();
            document.execCommand('copy');
            document.body.removeChild(copyTarget);

            alert(`링크가 복사되었습니다. ${copiedUrl}`);
        };

        return {
            save: save,
            writeArticle: writeArticle
        }
    };

    const init = () => {
        const articleController = new ArticleController();
        articleController.init();
    };

    return {
        init: init
    }
})();

ARTICLE_APP.init();