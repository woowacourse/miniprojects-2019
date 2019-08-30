const HeaderApp = (() => {
    const HeaderController = function () {
        const headerService = new HeaderService();

        const renderHeader = () => {
            headerService.renderLoginUser();
        };

        const showFriendList = () => {
            const friendListBtn = document.getElementById('friend-list-btn');
            friendListBtn.addEventListener('click', headerService.showFriendList);
        };

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
            const showModalBtn = document.getElementById('show-friend-modal-btn');
            showModalBtn.click();
        };

        const loginUser = () => {
            return headerApi.getLoginUser();
        };

        const renderLoginUser = () => {
            const loginUserName = document.getElementById('login-user-name');

            headerApi.getLoginUser()
                .then(json => {
                    if (json.hasOwnProperty('errorMessage')) {
                        alert(json.errorMessage);
                    } else {
                        console.log('header login user 요청', json);
                        AppStorage.set('login-user', json);
                        loginUserName.innerText = json.userName.name;
                        document.getElementById("user-url").setAttribute("href", '/users/' + json.id);
                    }
                });
        };

        return {
            renderLoginUser: renderLoginUser,
            showFriendList: showFriendList,
            loginUser: loginUser,
        };
    };

    const HeaderApi = function () {
        const getLoginUser = () => {
            return Api.get('/api/users')
                .then(response => {
                    return response.json();
                });
        };

        return {
            getLoginUser: getLoginUser,
        };
    };

    const init = () => {
        const headerController = new HeaderController();
        headerController.init();
    };

    const reRender = () => {
        const headerController = new HeaderController();
        headerController.reRender();
    };

    return {
        init: init,
        reRender: reRender,
        loginUser: new HeaderService().loginUser,
    };
})();

HeaderApp.init();