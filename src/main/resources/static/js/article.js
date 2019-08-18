const Article = (function () {
    const ArticleController = function () {
        const articleService = new ArticleService();
        const modalButton = () => {
            const button = document.querySelector('.modal-btn');
            button.addEventListener('click', articleService.modalActive);
        }
        const modifyFormButton = () => {
            const button = document.querySelector(".create-modify-btn")
            button.addEventListener('click', articleService.createModifyInput);
        }
        const modifyButton = () => {
            const button = document.querySelector(".contents-modify-btn");
            button.addEventListener('click', articleService.modify);
        }
        const removeButton = () => {
            const button = document.querySelector(".contents-remove-btn");
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
        const request = new Request("/api/articles");

        const loadContent = () =>
            request.get("/"+ articleId,
                (status,data) => {
                    document.getElementById("pic").src =  data.imageUrl;
                    document.querySelector(".contents-para").innerText = data.contents;
                })
        const modalActive = () => {
            modal.active()
        }
        const createModifyInput = () => {
            DomUtil.inactive(".contents-para")
            DomUtil.active(".contents-input")
            DomUtil.active(".contents-modify-btn")
            modal.inactive();
        }

        const modify = () => {
            let contents = document.querySelector(".contents-input").value;
            request.put('/', {
                id: articleId,
                contents: contents
            },(status, data)=>{
                DomUtil.active(".contents-para")
                DomUtil.inactive(".contents-input");
                DomUtil.inactive(".contents-modify-btn");
                document.querySelector(".contents-para").innerText = contents;
            })
        }
        const remove    = () => {
            const form = document.createElement('form');
            form.method = 'post';
            form.action = '/articles/'+ articleId;
            const deleteMethod = document.createElement('input');
            deleteMethod.name = '_method';
            deleteMethod.value = 'delete';
            document.body.appendChild(form);
            form.appendChild(deleteMethod);
            form.style.display = "none";
            form.submit();
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
