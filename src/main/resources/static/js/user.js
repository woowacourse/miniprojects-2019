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

    const Connector = function () {
        const POST = 'POST';
        const PUT = 'PUT';

        const fetchTemplate = function (requestUrl, method, body, redirectUrl) {
            fetch(requestUrl, {
                method: method,
                headers: {'Content-Type': 'application/json; charset=UTF-8'},
                body: JSON.stringify(body)
            }).then(response => {
                if (response.status === 200) {
                    window.location.href = redirectUrl;
                    return;
                }
                if (response.status === 400) {
                    throw response;
                }
            }).catch(error => {
                error.json()
                    .then(exception => {
                        alert(exception.message)
                    });
            });
        };

        return {
            POST: POST,
            PUT: PUT,
            fetchTemplate: fetchTemplate,
        }
    };

    const UserService = function () {
        const connector = new Connector();

        const email = document.getElementById('email');
        const nickName = document.getElementById('nickName');
        const userName = document.getElementById('userName');
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

            connector.fetchTemplate('/api/users', Connector.POST, userBasicInfo, '/login');
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

            // TODO redirect SLOWS~
            connector.fetchTemplate('/api/users/' + userId.value,
                Connector.PUT,
                userDto,
                '/users/' + userId.value + '/edit');
        };

        const login = function (event) {
            event.preventDefault();

            let userBasicInfo = {
                email: email.value,
                password: password.value,
            };

            connector.fetchTemplate('/api/users/login',
                Connector.POST,
                userBasicInfo,
                '/');
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
