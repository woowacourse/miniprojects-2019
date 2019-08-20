const ARTICLE_EDIT_APP = (() => {
    const articleSaveButton = document.getElementById("save-button");

    const save = function () {
        const file = document.getElementById("file").value;

        if (file === '') {
            alert("파일은 필수입니다");
            return;
        }

        const postForm = document.getElementById("save-form");
        const formData  = new FormData(postForm);

        const connector = FETCH_APP.fetchApi();
        const header = {};
        const redirectToArticlePage = response => {
            response.json().then(article => window.location.href = `/articles/${article.id}`)
        };
        connector.fetchTemplate('/api/articles', connector.POST, header, formData, redirectToArticlePage);
    };

    const init = () => {
        articleSaveButton.addEventListener('click',save);
    };

    return {
        init: init
    }
})();

ARTICLE_EDIT_APP.init();