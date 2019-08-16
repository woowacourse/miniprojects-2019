const Article = (function () {
    const ArticleController = function () {
        const articleService = new ArticleService();
        const modalButton = ()=>{
            const button=document.querySelector('#modal-btn');
            button.addEventListener('click',articleService.modalActive);
        }
        const modifyFormButton = () => {
            const button = document.querySelector("#create-modify-btn")
            button.addEventListener('click', articleService.createModifyInput);
        }
        const modifyButton = () => {
            const button = document.querySelector("#contents-modify-btn");
            button.addEventListener('click', articleService.modify);
        }
        const removeButton = () => {
            const button = document.querySelector("#contents-remove-btn");
            button.addEventListener('click', articleService.remove);
        }
        const initContent = () => {
            articleService.loadContent();
        }
        const init = function () {
            modifyButton()
            modifyFormButton()
            initContent()
            removeButton()
            modalButton()
        }
        return {
            init: init
        }
    }

    const ArticleService = function () {
        const loadContent = () =>
            request.get('/api/articles/' + articleId)
                .then(response => {
                    console.log(response);
                    document.getElementById("pic").src = '/uploads/' + response.data.imageUrl;
                    document.getElementById("contents-para").innerText = response.data.contents;
                })

        const modalActive= ()=>{
            modal.active()
        }
        const createModifyInput = () => {
            document.getElementById("contents-para").classList.add("pace-inactive");
            document.getElementById("contents-input").classList.remove("pace-inactive");
            document.getElementById("contents-modify-btn").classList.remove("pace-inactive");
            modal.inactive();
        }

        const modify = () => {
            let contents = document.getElementById("contents-input").value;
            request.put('/api/articles', {
                id: articleId,
                contents: contents
            }).then(() => {
                document.getElementById("contents-para").classList.remove("pace-inactive");
                document.getElementById("contents-input").classList.add("pace-inactive");
                document.getElementById("contents-modify-btn").classList.add("pace-inactive");
                document.getElementById("contents-para").innerText = contents;
            })
        }
        const remove = () => {
            request.delete('/articles/' + articleId);
        }

        return {
            loadContent: loadContent,
            createModifyInput: createModifyInput,
            remove: remove,
            modify: modify,
            modalActive: modalActive
        }
    }
    const init = () => {
        const articleController = new ArticleController();
        articleController.init();
    };

    return {
        init: init
    }
}());

Article.init();
