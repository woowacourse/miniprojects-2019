const FOLLOW_APP = (() => {

    const FollowController = function () {
        const followService = new FollowService();

        const follow = () => {
            const followButton = document.getElementById ('follow');
            followButton ? followButton.addEventListener('click', followService.follow) : undefined;

        }

        const unfollow = () => {
            const followButton = document.getElementById('unfollow');
            followButton ? followButton.addEventListener('click', followService.unfollow) : undefined;
        };

        const followers = () => {
            const followButton = document.getElementById('followers');
            followButton ? followButton.addEventListener('click', followService.followers) : undefined;
        };

        const init = () => {
            follow();
            unfollow();
            followers();

        };

        return {
            init: init
        }
    };

    const FollowService = function () {
        const connector = FETCH_APP.FetchApi();
        const header = {
            'Content-Type': 'application/json; charset=UTF-8',
            'Accept': 'application/json'
        };

        //todo 아직 프론트에서 바꾸는거 안
        // const followRelation = document.getElementById('follow').innerText;
        const formData = {
            fromNickName: document.getElementById('guest').value,
            toNickName: document.getElementById('feedOwner').innerText,
        };

        const follow = event =>{
            event.preventDefault();
            const ifSucceed = () => window.location.href = '/user/'+document.getElementById('feedOwner').innerText;

            connector.fetchTemplate('/follow', connector.POST,header,JSON.stringify(formData),ifSucceed)
        }

        const unfollow = event =>{
            event.preventDefault();
            const ifSucceed = () => window.location.href = '/user/'+document.getElementById('feedOwner').innerText;

            connector.fetchTemplate('/unfollow', connector.DELETE,header,JSON.stringify(formData),ifSucceed)
        }

        const followers = event => {
            event.preventDefault();
            const ifSucceed = (response) => {
                const modalHead = document.getElementById('modal-head');
                const body = document.getElementsByClassName('modal-body');
                for(let i=0; i<body.length; i++) {
                    body[i].innerHTML="";
                }
                let followerList = '<div id="follower-info" class="modal-body">팔로워 정보'
                for(let i = 0; i<response.length; i++) {
                    followerList = followerList + `<li><div id="nickName-${i}"> ${response[i].nickName}</div>
                                                       <div id="userName-${i}"> ${response[i].userName}</div></li>`
                }
                followerList = followerList + '</div>';
                modalHead.insertAdjacentHTML('afterend', followerList);
            }
            document.getElementById('followers')
            const nickName = document.querySelector('#feedOwner').innerHTML;
            connector.fetchTemplateWithoutBody('/followers/' + nickName, connector.GET, ifSucceed);
        }

        return {
            follow: follow,
            unfollow: unfollow,
            followers:followers,
        }
    };

    const init = () => {
        const followController = new FollowController();
        followController.init();
    };

    return {
        init: init
    }
})();

FOLLOW_APP.init();
