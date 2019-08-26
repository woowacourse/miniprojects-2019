const FOLLOW_APP = (() => {

    const FollowController = function () {
        const followService = new FollowService();

        const follow = () => {
            const followButton = document.getElementById('follow');
            followButton ? followButton.addEventListener('click', followService.follow) : undefined;
        }

        const unfollow = () => {
            const followButton = document.getElementById('unfollow');
            followButton ? followButton.addEventListener('click', followService.unfollow) : undefined;
        };

        const init = () => {
            follow();
            unfollow();
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

        return {
            follow: follow,
            unfollow: unfollow,
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
