const ArticleApp = (() => {
    const articleTemplate = Handlebars.compile(template.article);

    const ArticleController = function () {
        const articleService = new ArticleService();

        const add = () => {
            const saveBtn = document.getElementById('article-save-btn');
            saveBtn.addEventListener('click', articleService.add);
        };

        const showUpdateModal = () => {
            const articleList = document.getElementById('article-list');
            articleList.addEventListener('click', articleService.showModal);
        };

        const remove = () => {
            const articleList = document.getElementById('article-list');
            articleList.addEventListener('click', articleService.remove);
        };

        const update = () => {
            const updateBtn = document.getElementById('update-btn');
            updateBtn.addEventListener('click', articleService.update);
        };

        const read = () => {
            articleService.read();
        };

        const photoAndVideo = () => {
            const photoVideoBtn = document.getElementById('photo-video-btn');
            photoVideoBtn.addEventListener('click', articleService.hideFileInputTag);
            const photoVideoInputTag = document.getElementById('photo-video-input');
            photoVideoInputTag.addEventListener('change', articleService.showThumbnail)
        };

        const init = () => {
            add();
            showUpdateModal();
            remove();
            read();
            update();
            photoAndVideo();
        };

        return {
            init: init,
        }
    };

    const ArticleService = function () {
        const articleApi = new ArticleApi();

        const read = () => {
            const articleList = document.getElementById('article-list');
            const targetHTML = articleList.getAttribute('data-target');
            const pageUserId = targetHTML === 'users' ? window.location.pathname.split('/')[2] : 0;
            articleList.innerHTML = "";

            articleApi.render(targetHTML, pageUserId)
                .then(response => response.json())
                .then(data => {
                    data.forEach(article => {
                        articleList.insertAdjacentHTML('afterbegin', articleTemplate({
                            "id": article.id,
                            "updatedTime": TimeApi.pretty(article.updatedTime),
                            "article-contents": article.articleFeature.contents.contents,
                            "article-videoUrl": article.articleFeature.videoUrl.fileUrl,
                            "article-imageUrl": article.articleFeature.imageUrl.fileUrl,
                            "authorId": article.authorId,
                            "authorName": article.authorName.fullName,
                        }));
                        ReactionApp.service().showGoodCount('article', article.id);
                        addRangeIcon(article.id, article.openRange);
                        CommentApp.service().showCommentCount(article.id);
                        checkBlank();
                    })
                })
                .catch(error => console.log("error: " + error));
        };

        const add = () => {
            const contents = document.getElementById("article-contents");
            const selectBtn = document.getElementById('article-range');
            const selectedRange = selectBtn.options[selectBtn.selectedIndex].value;

            if (AppStorage.check('article-add-run')) {
                return;
            }
            AppStorage.set('article-add-run', true);

            upload(contents, translateSelectedRange(selectedRange)).then(data => {
                articleApi.add(data)
                    .then(response => response.json())
                    .then(article => {
                        document.getElementById('article-list')
                            .insertAdjacentHTML('afterbegin', articleTemplate({
                                "id": article.id,
                                "updatedTime": TimeApi.pretty(article.updatedTime),
                                "article-contents": article.articleFeature.contents.contents,
                                "article-videoUrl": article.articleFeature.videoUrl.fileUrl,
                                "article-imageUrl": article.articleFeature.imageUrl.fileUrl,
                                "authorId": article.authorId,
                                "authorName": article.authorName.fullName,
                            }));
                        ReactionApp.service().showGoodCount('article', article.id);
                        addRangeIcon(article.id, article.openRange);
                        CommentApp.service().showCommentCount(article.id);
                        const videoTag = document.querySelector('video[data-object="article-video"]');
                        const imageTag = document.querySelector('img[data-object="article-image"]');
                        if (videoTag.getAttribute('src') === "") {
                            videoTag.setAttribute('style', 'display:none');
                        }
                        if (imageTag.getAttribute('src') === "") {
                            imageTag.setAttribute('style', 'display:none');
                        }
                        AppStorage.set('article-add-run', false);
                    });
                contents.value = "";
                document.querySelector('#preview').src = "";
            });
        };

        const translateSelectedRange = (range) => {
            const RANGE_STATUS = {
                PUBLIC: '모두 공개',
                ONLY_FRIEND: '친구 공개',
                NONE: '비공개',
            };

            if (range === RANGE_STATUS.PUBLIC) {
                return '0';
            } else if (range === RANGE_STATUS.ONLY_FRIEND) {
                return '1';
            } else if (range === RANGE_STATUS.NONE) {
                return '2';
            }
        };

        const addRangeIcon = (articleId, range) => {
            const rangeIcon = document.getElementById(`range-icon-${articleId}`);

            if (range === 'ALL') {
                rangeIcon.setAttribute('class', '');
            } else if (range === 'ONLY_FRIEND') {
                rangeIcon.setAttribute('class', 'friend-btn font-size-20');
            } else if (range === 'NONE') {
                rangeIcon.setAttribute('class', 'ti-lock font-size-20');
            }
        };

        const update = () => {
            const updateArea = document.getElementById('article-update-contents');
            const articleId = updateArea.getAttribute('data-update-article-id');
            const article = document.querySelector(`div[data-article-id="${articleId}"]`);
            const imageUrl = article.querySelector('img[data-object="article-image"]');
            const videoUrl = article.querySelector('video[data-object="article-video"]');
            const currentUrl = String(window.location);

            const data = {
                contents: updateArea.value,
                imageUrl: (imageUrl.src === currentUrl) ? "" : imageUrl.src,
                videoUrl: (videoUrl.src === currentUrl) ? "" : videoUrl.src,
            };

            articleApi.update(data, articleId)
                .then(response => response.json())
                .then((error) => {
                    if (error.hasOwnProperty('errorMessage')) {
                        alert(error.errorMessage);
                    } else {
                        read();
                    }
                })
                .catch(error => console.log("error: " + error));
        };

        const remove = (event) => {
            const target = event.target;
            if (target.closest('li[data-btn="delete"]')) {
                const article = target.closest('div[data-object="article"]');
                const articleId = article.getAttribute('data-article-id');
                articleApi.remove(articleId)
                    .then(response => response.json())
                    .then((error) => {
                        if (error.hasOwnProperty('errorMessage')) {
                            alert(error.errorMessage);
                        } else {
                            article.remove();
                        }
                    })
                    .catch(error => console.log("error: " + error));
            }
        };

        const showModal = (event) => {
            const target = event.target;
            if (target.closest('li[data-btn="update"]')) {
                const article = target.closest('div[data-object="article"]');
                const updateArea = document.getElementById('article-update-contents');
                const articleId = article.getAttribute('data-article-id');
                updateArea.value = article.querySelector('span[data-object="article-contents"]').innerText;

                const showModalBtn = document.getElementById('show-article-modal-btn');
                updateArea.setAttribute('data-update-article-id', articleId);

                showModalBtn.click();
            }
        };

        const hideFileInputTag = () => {
            const inputTag = document.getElementById("photo-video-input");
            inputTag.click();
        };

        const showThumbnail = () => {
            const file = document.querySelector('#photo-video-input');
            let files = file.files;

            let allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif|\.mov|\.mp4)$/i;
            if(!allowedExtensions.exec(files[0].name)){
                alert('Please upload file having extensions .jpeg/.jpg/.png/.gif/.mov/.mp4 only.');
                file.value = '';
                return false;
            } else {
                let reader = new FileReader();
                reader.readAsDataURL(files[0]);
                reader.onload = function  () {
                    document.querySelector('#preview').src = reader.result ;
                };
            }
        };

        const upload = (contents, range) => {
            const files = document.querySelector('#photo-video-input');
            const file = files.files[0];
            const formData = new FormData();
            formData.append('data', file);

            if (!file && !contents.value) {
                AppStorage.set('article-add-run', false);
                alert("뭐라도 쓰세요");
                throw new Error();
            }
            else if (!file) {
                return new Promise((resolve) => {
                    resolve({
                        contents: contents.value,
                        imageUrl: "",
                        videoUrl: "",
                        openRange: range,
                    });
                });
            }

            LoadingApi.loading();
            return Api.postImage('/upload', formData)
                .then(res => res.json())
                .then(fileUrl => {
                let imgExtension = /(\.jpg|\.jpeg|\.png|\.gif)$/i;
                let videoExtension = /(\.mov|\.mp4)$/i;
                let data;
                files.value = null;

                const res = fileUrl.fileUrl;
                if (imgExtension.exec(res)) {
                    data = {
                        contents: contents.value,
                        imageUrl: res,
                        videoUrl: "",
                        openRange: range,
                    };
                } else if (videoExtension.exec(res)) {
                    data = {
                        contents: contents.value,
                        imageUrl: "",
                        videoUrl: res,
                        openRange: range,
                    };
                } else {
                    data = {
                        contents: contents.value,
                        imageUrl: "",
                        videoUrl: "",
                        openRange: range,
                    };
                }
                LoadingApi.loadingDone();
                return data;
            });
        };

        const checkBlank = () => {
            const videoTag = document.querySelector('video[data-object="article-video"]');
            const imageTag = document.querySelector('img[data-object="article-image"]');
            if (videoTag.getAttribute('src') === "") {
                videoTag.setAttribute('style', 'display:none');
            }
            if (imageTag.getAttribute('src') === "") {
                imageTag.setAttribute('style', 'display:none');
            }
        };

        return {
            add: add,
            read: read,
            update: update,
            remove: remove,
            showModal: showModal,
            hideFileInputTag: hideFileInputTag,
            showThumbnail: showThumbnail,
            upload: upload,
        }
    };

    const ArticleApi = function () {
        const add = (data) => {
            return Api.post(`/api/articles`, data)
        };

        const remove = (articleId) => {
            return Api.delete(`/api/articles/${articleId}`);
        };

        const render = (targetHTML, pageUserId) => {
            return Api.get(`/api/articles?target=${targetHTML}&pageUserId=${pageUserId}`);
        };

        const update = (data, articleId) => {
            return Api.put(`/api/articles/${articleId}`, data);
        };

        const showGood = (articleId) => {
            return Api.get(`/api/articles/${articleId}/good`);
        };

        const upload = (data) => {
            return Api.postImage(`/upload`, data);
        };

        return {
            add: add,
            remove: remove,
            render: render,
            update: update,
            showGood: showGood,
            upload: upload,
        };
    };

    const init = () => {
        const articleController = new ArticleController();
        articleController.init();
    };

    return {
        init: init,
    };

})();

ArticleApp.init();