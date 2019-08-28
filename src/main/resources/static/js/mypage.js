const mypage = (function () {
    const modalButton =
        `<button class="create-modify-btn" tabindex="0"  onclick="location.href='/users/mypage-edit/form'">프로필 수정</button>
         <button class="contents-remove-btn delete-btn font-cap" type="button" tabindex="0" onclick="location.href='/users/logout'">로그아웃</button>`;
    const modal = new Modal(modalButton);
    modal.init();

    const MypageController = function () {
        const mypageService = new MypageService();

        const loadInit = function () {
            mypageService.getFollowers();
            mypageService.getFollowings();
            mypageService.getPageData(0);
        };

        const modalButton = () => {
            if (mine) {
                const button = document.querySelector('.user-modal-btn');
                button.addEventListener('click', mypageService.modalActive);
            }
        };

        const followButton = () => {
            if (!mine) {
                const button = document.querySelector('.following');
                button.addEventListener('click', mypageService.sendFollow);
            }
        };

        const init = function () {
            followButton();
            modalButton();
            loadInit();
        };

        return {
            init: init
        }
    };

    const MypageService = function () {
        const pageSize = 12;

        const userName = document.querySelector('.user-name').innerHTML;
        const mypageRequest = new Request(`/api/mypage/users/${userName}`);
        const follow = new Follow(following, targetId)

        const getProfile = () => {

        };

        const getPageData = (pageNum) => {
            const container = document.querySelector('.article-card');

            mypageRequest.get('?page=' + pageNum + "&size=" + pageSize + "&sort=id,DESC"
                , (status, data) => {
                    document.querySelector('.article-num').innerHTML = `<strong>${data.totalElements}</strong>`;

                    let rowDiv = (pages) => {
                        return `<div class="article-row-card">${pages}</div>`
                    };
                    let rowNum = Math.ceil(data.content.length / 3);

                    for (let i = 0; i <= rowNum; i++) {
                        let row = "";
                        for (let j = 0; j < 3; j++) {
                            if ((i * 3 + j) < data.content.length) {
                                row += getMypageArticleTemplate(data.content[i * 3 + j].article.id, data.content[i * 3 + j].article.imageUrl);
                            }
                        }
                        container.insertAdjacentHTML('beforeend', rowDiv(row));
                    }

                    return data.last;
                }).then((last) => {
                if (last === false) {
                    const cardList = document.querySelectorAll('.article-row-card');
                    const target = cardList[cardList.length - 1];
                    console.log(target);
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

        const modalActive = () => {
            modal.active()
        };

        const sendFollow = () => {
            const followingButton = document.querySelector('.following');

            if (follow.getStatus()) {
                follow.deleteFollow().then(() => {
                    followingButton.innerHTML = "팔로우";
                    getFollowers();
                });
                follow.toggleStatus();
            } else {
                follow.addFollow().then(() => {
                    followingButton.innerHTML = "팔로우 취소";
                    getFollowers();
                });
                follow.toggleStatus();
            }
        };

        const getFollowers = () => {
            follow.followersNum((status, data) => {
                const followers = document.querySelector('.followers');
                followers.innerHTML = "";
                followers.insertAdjacentHTML('beforeend', `<strong>${data}</strong>`);
            })
        };

        const getFollowings = () => {
            follow.followingsNum((status, data) => {
                const followers = document.querySelector('.followings');
                followers.innerHTML = "";
                followers.insertAdjacentHTML('beforeend', `<strong>${data}</strong>`);
            })
        };

        return {
            getPageData: getPageData,
            getProfile: getProfile,
            sendFollow: sendFollow,
            modalActive: modalActive,
            getFollowers: getFollowers,
            getFollowings: getFollowings
        }
    };

    const init = () => {
        const mypageController = new MypageController();
        mypageController.init();
    };

    return {
        init: init
    }

}());

mypage.init();

