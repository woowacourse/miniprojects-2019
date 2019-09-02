const Article = (() => {

    const modalButton =
        `<button class="create-modify-btn" tabindex="0">수정하기</button>
         <button class="contents-remove-btn delete-btn font-cap" type="button" tabindex="0">삭제하기</button>`;
    const modal = new Modal(modalButton);
    modal.init();

    const ArticleController = function () {
        const articleService = new ArticleService();
        const modalButton = () => {
            const button = document.querySelectorAll('.modal-btn');
            Array.from(button).map(x => x.addEventListener('click', articleService.modalActive));
        };
        const modifyFormButton = () => {
            const button = document.querySelector(".create-modify-btn");
            button.addEventListener('click', articleService.createModifyInput);
        };
        const modifyButton = () => {
            const button = document.querySelector(".modify-btn");
            button.addEventListener('click', articleService.modify);
        };
        const removeButton = () => {
            const button = document.querySelector(".contents-remove-btn");
            button.addEventListener('click', articleService.remove);
        };
        const initContent = () => {
            articleService.loadContent();
        };

        const init = () => {
            modifyButton();
            modifyFormButton();
            initContent();
            removeButton();
            modalButton()
        };
        return {
            init: init
        }
    };

    const ArticleService = function () {
        const request = new Request("/api/articles");
        const loadContent = () =>
            request.get("/" + articleId,
                (status, data) => {
                    loadImageProcess(data.imageUrl).then((img) => {
                            document.getElementById("pic").src = img.src;
                            imageResize(img)
                            document.querySelector(".pic-con").style.backgroundImage = "none";
                        }
                    );

                    document.querySelectorAll(".article-profile-img").forEach(
                        (element) => {
                            element.setAttribute('src', data.userInfoDto.profile);
                        }
                    );

                    document.querySelectorAll(".profile-name").forEach(
                        (element) => {
                            element.innerHTML = `<a href="/${data.userInfoDto.userContentsDto.userName}">${data.userInfoDto.userContentsDto.userName}</a>`;
                        }
                    );
                    document.querySelector(".contents-para").innerHTML = hashTagAddLink(htmlToStringParse(data.contents));
                });


        const loadImageProcess = (src) => {
            return new Promise((resolve, reject) => {
                const img = new Image();
                img.onload = () => resolve(img);
                img.onerror = reject;
                img.src = src
            })
        };


        const modalActive = () => {
            modal.active()
        };
        const createModifyInput = () => {
            DomUtil.inactive(".contents-section");
            DomUtil.active(".modify-input");
            DomUtil.active(".modify-btn");
            modal.inactive();
        };

        const modify = () => {
            const contents = document.querySelector(".modify-input").value;
            request.put('/', {
                id: articleId,
                contents: contents
            }, (status, data) => {
                DomUtil.active(".contents-section");
                DomUtil.inactive(".modify-input");
                DomUtil.inactive(".modify-btn");
                document.querySelector(".contents-para").innerText = contents;
            })
        };
        const remove = () => {
            const form = document.createElement('form');
            form.method = 'post';
            form.action = '/articles/' + articleId;
            const deleteMethod = document.createElement('input');
            deleteMethod.name = '_method';
            deleteMethod.value = 'delete';
            document.body.appendChild(form);
            form.appendChild(deleteMethod);
            form.style.display = "none";
            form.submit();
        };

        const imageResize = (img) => {
            const element = document.querySelector("#pic");
            const width = img.naturalWidth;
            const height = img.naturalHeight;
            addResizeImageClass(element, width, height);
        };

        const addResizeImageClass = (element, width, height) => {
            if (width >= height) {
                return element.classList.add("img-parallel")
            }
            element.classList.add("img-vertical");
        };


        return {
            loadContent: loadContent,
            createModifyInput: createModifyInput,
            remove: remove,
            modify: modify,
            modalActive: modalActive,
            imageResize: imageResize,
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

Article.init();