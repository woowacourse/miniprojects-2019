const Index = (() => {

    const pageSize = 10;

    const IndexController = function () {
        const indexService = new IndexService();
        const articleList = document.querySelector('.article-card-con');
        const loadInit = () => {
            indexService.getPageData(0)
        };

        const likeButton = () => {
            articleList.addEventListener('click', (event) => {
                if (event.target.classList.contains('like-btn')) {
                    const articleId = event.target.closest('.article-card').dataset.articleId;
                    const isLike = event.target.dataset.liking;
                    indexService.eventLike(articleId, isLike, event.target);
                }
            })
        };

        const commentSubmitButton = () => {
            articleList.addEventListener('click', (event) => {
                if (event.target.classList.contains('comment-btn')) {
                    const articleId = event.target.closest('.article-card').dataset.articleId;
                    const commentsArea = event.target.closest('.add-comment').querySelector('textarea');
                    const comments = commentsArea.value;
                    indexService.createComment(articleId, comments, event.target);
                    commentsArea.value = "";
                }
            })
        };

        const commentSubmitKey = () => {
            articleList.addEventListener('keydown', (event) => {
                if (event.target.classList.contains("comment-textarea") && event.keyCode == 13) {
                    event.preventDefault();
                    const articleId = event.target.closest('.article-card').dataset.articleId;
                    const comments = event.target.value;
                    indexService.createComment(articleId, comments, event.target);
                    event.target.value = "";
                }
            })
        };

        const copyButton = () => {
            articleList.addEventListener('click', (event) => {
                if (event.target.classList.contains("copy-btn")) {
                    const articleId = event.target.closest('.article-card').dataset.articleId;
                    indexService.copyArticleLink(articleId);
                }
            })
        };

        const init = () => {
            loadInit();
            likeButton();
            commentSubmitButton();
            commentSubmitKey();
            copyButton();
        };

        return {
            init: init
        }
    };


    const IndexService = function () {
        const indexRequest = new Request("/api/main");
        const commentRequest = new Request("/api/articles/");
        const userRequest = new Request("/api/users");
        const like = new Like();
        const getPageData = (pageNum) => {
            return indexRequest.get(`?page=${pageNum}&size=${pageSize}&sort=id,DESC`
                , (status, data) => {
                    let pagesHtml = "";
                    for (let i = 0; i < data.content.length; i++) {
                        pagesHtml += parsingPage(data.content[i]);
                    }
                    const container = document.querySelector(".article-card-con");
                    container.insertAdjacentHTML('beforeend', pagesHtml);
                    return data.last;
                }).then((last) => {
                if (last === false) {
                    const cardList = document.querySelectorAll('.article-card');
                    const target = cardList[cardList.length - 1];
                    lazyLoad(target, pageNum);
                }
            });
        };

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

        const getLoggedInData = () => {
            return userRequest.get("/loggedin", (status, data) => {
                return data;
            })
        };

        const eventLike = (articleId, isLike, element) => {
            if (isLike == 'false') {
                like.addLike(articleId)
                    .then(() => {
                        element.childNodes[1].classList.add("fa-heart");
                        element.childNodes[1].classList.remove("fa-heart-o");
                        element.dataset.liking = "true";
                        const likeNumElement = element.closest(".article-card").querySelector(".like-num");
                        const likeNum = parseInt(likeNumElement.innerText) + 1;
                        likeNumElement.innerText = likeNum;
                    });

            } else {
                like.deleteLike(articleId)
                    .then(() => {
                        element.childNodes[1].classList.add("fa-heart-o");
                        element.childNodes[1].classList.remove("fa-heart");
                        element.dataset.liking = "false";
                        const likeNumElement = element.closest(".article-card").querySelector(".like-num");
                        const likeNum = parseInt(likeNumElement.innerText) - 1;
                        likeNumElement.innerText = likeNum;
                    });
            }
        };

        const parsingPage = (pageData) => {
            const article = pageData.article;
            const user = pageData.article.userInfoDto;
            const comments = parsingComments(pageData.comments);
            if (user.profile === null) {
                user.profile = "https://woowahan-crews.s3.ap-northeast-2.amazonaws.com/default_profile_image.jpg";
            }
            return getArticleTemplate(
                article.id,
                user.id,
                user.userContentsDto.userName,
                article.imageUrl,
                user.profile,
                article.contents,
                comments,
                pageData.likes,
                pageData.liking,
                getTime(article.created)
            )
        };

        const parsingComments = (commentsData) => {
            let commentsHtml = "";
            commentsData.forEach((data) => {
                commentsHtml += getCommentTemplate(
                    data.id,
                    data.userInfoDto.userContentsDto.userName,
                    data.contents
                )
            });
            return commentsHtml;
        };

        const createComment = (articleId, value, target) => {
            commentRequest.post(`${articleId}/comments`, {contents: value}, (status, data) => {
                const commentList = target.closest('.comment').querySelector('.comment-list');
                const commentHTML = getCommentTemplate(data.id, data.userInfoDto.userContentsDto.userName, data.contents);
                commentList.insertAdjacentHTML('beforeend', commentHTML)
            })
        };

        const copyArticleLink = (articleId) => {
            const temp = document.createElement("textarea");
            document.body.appendChild(temp);
            temp.value = `${window.location.host}/articles/${articleId}`;
            temp.select();
            document.execCommand('copy');
            document.body.removeChild(temp);
            new Alert("게시글 URL이 복사되었습니다.")
        };

        const getTime = (createdTime) => {
            const created = {
                year: createdTime.split("-")[0],
                month: createdTime.split("-")[1],
                day: createdTime.split("-")[2].split("T")[0],
                hour: createdTime.split("T")[1].split(":")[0]
            }
            for (key in created) {
                created[key] = parseInt(created[key]);
            }
            const date = new Date();
            const current = {
                year: date.getFullYear(),
                month: date.getMonth() + 1,
                day: date.getDate(),
                hour: date.getHours()
            }
            if (created.year != current.year || created.month != current.month) {
                return `${created.year}년 ${created.month}월 ${created.day}일`
            }
            const pastTime = 24 * (current.day - created.day) + current.day - created.day;
            if (pastTime === 0) {
                return `방금 전`
            }
            return `${pastTime}시간 전`
        };

        return {
            getPageData: getPageData,
            eventLike: eventLike,
            createComment: createComment,
            copyArticleLink: copyArticleLink
        }
    };

    const init = () => {
        const indexController = new IndexController();
        indexController.init();
    };

    return {
        init: init
    }

})();

Index.init();