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

        const signout = () => {
            const signoutBtn = document.getElementById('signout-btn');
            signoutBtn.addEventListener('click', headerService.signout);
        };

        const init = () => {
            renderHeader();
            showFriendList();
            signout();
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
                        AppStorage.set('login-user', json);
                        loginUserName.innerText = json.userName.fullName;
                        document.getElementById("user-url").setAttribute("href", '/users/' + json.id);
                    }
                });
        };

        const signout = () => {
            headerApi.signout()
                .then(() => {
                    location.href="/";
                });
        };

        return {
            renderLoginUser: renderLoginUser,
            showFriendList: showFriendList,
            loginUser: loginUser,
            signout: signout,
        };
    };

    const HeaderApi = function () {
        const getLoginUser = () => {
            return Api.get('/api/users')
                .then(response => {
                    return response.json();
                });
        };

        const signout = () => {
            return Api.delete('/signout')
        };

        return {
            getLoginUser: getLoginUser,
            signout: signout,
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