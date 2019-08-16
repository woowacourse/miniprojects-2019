const Article = (function () {
    const ArticleController = function(){
        const articleService = new ArticleService();
        const modifyFormButton = ()=>{
            const button = document.getElementById("modal-btn")
            button.addEventListener('click',articleService.createModifyInput);
        }
        const modifyButton = ()=>{
            const button = document.getElementById("contents-modify-btn");
            button.addEventListener('click',articleService.modify);
        }
        const removeButton = ()=>{
            const button = document.getElementById("contents-remove-btn");
            button.addEventListener('click',articleService.remove);
        }
        const initContent = ()=>{
            articleService.createContents();
        }
        const init = function () {
            modifyButton()
            modifyFormButton()
            initContent()
            removeButton()
        }
        return {
            init: init
        }
    }

    const ArticleService = function(){
        const createContents = () =>
            axios.get('/api/articles/' + articleId)
                .then(response => {
                    console.log(response);
                    document.getElementById("pic").src = '/uploads/'+response.data.imageUrl;
                    document.getElementById("contents-para").innerText = response.data.contents;
                })
        const createModifyInput = () => {
            document.getElementById("contents-para").classList.add("pace-inactive");
            document.getElementById("contents-input").classList.remove("pace-inactive");
            document.getElementById("contents-modify-btn").classList.remove("pace-inactive");
        }

        const modify = ()=>{
            let contents = document.getElementById("contents-input").value;
            axios.put('/api/articles',{
                id: articleId,
                contents:contents
            })
            .then(()=>{
                document.getElementById("contents-para").classList.remove("pace-inactive");
                document.getElementById("contents-input").classList.add("pace-inactive");
                document.getElementById("contents-modify-btn").classList.add("pace-inactive");
                document.getElementById("contents-para").innerText = contents;
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
            createContents: createContents,
            createModifyInput : createModifyInput,
            remove: remove,
            modify: modify
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

window.onload = function () {
    Article.init();
}