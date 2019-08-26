const mypage = (function () {
    const modalButton =
        `<button class="create-modify-btn" tabindex="0"  onclick="location.href='/users/mypage-edit/form'">프로필 수정</button>
         <button class="contents-remove-btn delete-btn font-cap" type="button" tabindex="0" onclick="location.href='/users/logout'">로그아웃</button>`;
    const modal = new Modal(modalButton);
    modal.init();

    const MypageController = function () {
        const mypageService = new MypageService();

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
            mypageService.getFollowers();
            mypageService.getFollowings();
        };
        
        return {
            init : init
        }
    };

    const MypageService = function () {
        const follow = new Follow(following, targetId);

        const getProfile = () => {

        };

        const modalActive = () => {
            modal.active()
        };

        const sendFollow = () => {
            const followingButton = document.querySelector('.following');

            if(follow.getStatus()) {
                follow.deleteFollow().then(()=>{
                    followingButton.innerHTML = "팔로우";
                    getFollowers();
                });
                follow.toggleStatus();
            } else {
                follow.addFollow().then(()=>{
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
        }

        return {
            getProfile : getProfile,
            sendFollow : sendFollow,
            modalActive: modalActive,
            getFollowers : getFollowers,
            getFollowings : getFollowings
        }
    }

    const init = () => {
        const mypageController = new MypageController();
        mypageController.init();
    };

    return {
        init: init
    }

} ());

mypage.init();

