// const defaultProfileImage = "https://woowahan-crews.s3.ap-northeast-2.amazonaws.com/default_profile_image.jpg";
// const profileImage = document.querySelector('#profile-image');
// const originalImage = document.querySelector('#original-image');
// profileImage.setAttribute("src", profileUrl);
//
// if (profileUrl === null) {
//     profileImage.setAttribute("src", defaultProfileImage);
//     originalImage.setAttribute("value", defaultProfileImage);
// }

const mypage = (function () {
    // const urlParams = new URLSearchParams (location.search);
    // const targetId = urlParams.get('userId');

    const MypageController = function () {
        const mypageService = new MypageService();


        const init = function () {
            mypageService.getFollowers();
            mypageService.getFollowings();
        }
        
        return {
            init : init
        }
    }

    const MypageService = function () {
        const request = new Request(`/api/users/${targetId}/follows`);

        const getProfile = () => {

        }

        const getFollowers = () => {
            request.get('/followers/num', (status, data) => {
                const followers = document.querySelector('.followers');
                followers.innerHTML = "";

                console.log(data);

                followers.insertAdjacentHTML('beforeend', `<strong>${data}</strong>`);
            })
        }

        const getFollowings = () => {
            request.get('/followings/num', (status, data) => {
                const followers = document.querySelector('.followings');
                followers.innerHTML = "";
                console.log(data);
                followers.insertAdjacentHTML('beforeend', `<strong>${data}</strong>`);
            })
        }

        return {
            getProfile : getProfile,
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

