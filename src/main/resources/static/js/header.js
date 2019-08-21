const HeaderApp = (() => {
    const HeaderController = function () {
        const headerService = new HeaderService();

        const renderHeader = () => {
            headerService.renderLoginUser();
        };

        const showFriendList = () => {
            const friendListBtn = document.getElementById('friend-list-btn');
            friendListBtn.addEventListener('click', headerService.showFriendList);
        }

        const init = () => {
            renderHeader();
            showFriendList();
        };

        return {
            init: init,
            reRender: renderHeader,
        };
    };

    const HeaderService = function () {
        const headerApi = new HeaderApi();

        const showFriendList = () => {
            const showModalBtn = document.getElementById('show-friend-modal-btn')
            showModalBtn.click();
        };

        const renderLoginUser = () => {
            const loginUserName = document.getElementById('login-user-name');

            headerApi.getLoginUser()
                .then(response => {
                    return response.json();
                }).then(json => {
                if (json.hasOwnProperty('errorMessage')) {
                    alert(json.errorMessage);
                } else {
                    console.log('header login user 요청', json);
                    AppStorage.set('login-user', json);
                    loginUserName.innerText = json.userName.name;
                }
            })
        };

        return {
            renderLoginUser: renderLoginUser,
            showFriendList: showFriendList,
        };
    };

    const HeaderApi = function () {
        const getLoginUser = (data) => {
            return Api.get('/api/users');
        };

        return {
            getLoginUser: getLoginUser,
        }
    };

    const init = () => {
        const headerController = new HeaderController();
        headerController.init();
    };

    const reRender = () => {
        const headerController = new HeaderController();
        headerController.reRender();
    }

    return {
        init: init,
        reRender: reRender,
    };
})();

HeaderApp.init();