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
            const button = document.querySelector(".modify-btn");
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
                    const img = new Image();
                    img.src = data.imageUrl;
                    document.getElementById("pic").src =  img.src;
                    img.onload=imageResize(img);
                    document.querySelectorAll(".profile-name").forEach(
                        (element)=>{
                            element.innerText = data.userInfoDto.userContentsDto.userName;
                        }
                    )
                    document.querySelector(".contents-para").innerText = data.contents;
                })
        const modalActive = () => {
            modal.active()
        }
        const createModifyInput = () => {
            DomUtil.inactive(".contents-section")
            DomUtil.active(".modify-input")
            DomUtil.active(".modify-btn")
            modal.inactive();
        }

        const modify = () => {
            let contents = document.querySelector(".modify-input").value;
            request.put('/', {
                id: articleId,
                contents: contents
            },(status, data)=>{
                DomUtil.active(".contents-section")
                DomUtil.inactive(".modify-input");
                DomUtil.inactive(".modify-btn");
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

        const imageResize = (img)=>{
            const element = document.querySelector("#pic");
            const width = img.naturalWidth;
            const height = img.naturalHeight;
            if(width>=height){
                return element.classList.add("img-parallel")
            }
            element.classList.add("img-vertical");
        }

        return {
            loadContent: loadContent,
            createModifyInput: createModifyInput,
            remove: remove,
            modify: modify,
            modalActive: modalActive,
            imageResize:imageResize
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
