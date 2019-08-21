const USER_APP = (() => {
    'use strict';

    const UserController = function () {
        const userService = new UserService();

        const signUp = () => {
            const signUpButton = document.getElementById('signup');
            signUpButton ? signUpButton.addEventListener('click', userService.saveUser) : undefined;
        };

        const editUser = () => {
            const editButton = document.getElementById('editButton');
            editButton ? editButton.addEventListener('click', userService.updateUser) : undefined;
        };

        const login = () => {
            const loginButton = document.getElementById('login-button');
            loginButton ? loginButton.addEventListener('click', userService.login) : undefined;
        };

        const init = () => {
            signUp();
            editUser();
            login();
        };

        return {
            init: init
        }
    };

    const UserService = function () {
        const connector = FETCH_APP.fetchApi();
        const header = {
            'Content-Type': 'application/json; charset=UTF-8',
            'Accept': 'application/json'
        };

        const email = document.getElementById('email');
        const nickName = document.getElementById('nick-name');
        const userName = document.getElementById('user-name');
        const password = document.getElementById('password');
        const userId = document.getElementById('userId');
        const webSite = document.getElementById('webSite');
        const intro = document.getElementById('intro');

        const saveUser = function (event) {
            event.preventDefault();

            let userBasicInfo = {
                email: email.value,
                nickName: nickName.value,
                userName: userName.value,
                password: password.value,
            };

            const ifSucceed = () => window.location.href = '/login';

            connector.fetchTemplate('/api/users',
                connector.POST,
                header,
                JSON.stringify(userBasicInfo),
                ifSucceed);
        };

        const updateUser = function (event) {
            event.preventDefault();

            const userDto = {
                nickName: nickName.value,
                userName: userName.value,
                userId: userId.value,
                webSite: webSite.value,
                password: password.value,
                intro: intro.value,
            };

            const ifSucceed = () => window.location.href = '/';

            connector.fetchTemplate('/api/users/' + userId.value,
                connector.PUT,
                header,
                JSON.stringify(userDto),
                ifSucceed);
        };

        const login = function (event) {
            event.preventDefault();

            let userBasicInfo = {
                email: email.value,
                password: password.value,
            };

            const ifSucceed = () => window.location.href = '/';

            connector.fetchTemplate('/api/users/login',
                connector.POST,
                header,
                JSON.stringify(userBasicInfo),
                ifSucceed);
        };

        return {
            saveUser: saveUser,
            updateUser: updateUser,
            login: login,
        }
    };

    const init = () => {
        const userController = new UserController();
        userController.init();
    };

    return {
        init: init
    }
})();

USER_APP.init();
