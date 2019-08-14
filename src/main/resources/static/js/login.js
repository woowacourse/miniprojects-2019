const LOGIN_APP = (() => {
    'use strict';

    const UserController = function () {
        const userService = new UserService();

        const login = () => {
            const loginButton = document.getElementById('login-button');
            loginButton.addEventListener('click', userService.login);
        };

        const init = () => {
            login();
        };

        return {
            init: init
        }
    };

    const UserService = function () {

        const email = document.getElementById('email');
        const password = document.getElementById('password');

        const login = function (event) {
            event.preventDefault();

            let userBasicInfo = {
                email: email.value,
                password: password.value,
            };

            fetch('/api/users/login', {
                method: 'POST',
                headers: {'Content-Type': 'application/json; charset=UTF-8'},
                body: JSON.stringify(userBasicInfo)
            }).then(response => {
                if (response.status === 200) {
                    window.location.href = '/';
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

LOGIN_APP.init();
