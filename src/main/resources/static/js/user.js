const USER_APP = (() => {
    'use strict';

    const UserController = function () {
        const userService = new UserService();

        const signUp = () => {
            const signUpButton = document.getElementById('signup');
            signUpButton.addEventListener('click', userService.saveUser);
        };

        const init = () => {
            signUp();
        };

        return {
            init: init
        }
    };

    const UserService = function () {

        const email = document.getElementById('email');
        const nickName = document.getElementById('nickName');
        const userName = document.getElementById('userName');
        const password = document.getElementById('password');

        const saveUser = function (event) {
            event.preventDefault();

            let userBasicInfo = {
                email: email.value,
                nickName: nickName.value,
                userName: userName.value,
                password: password.value,
            };

            fetch('/api/users', {
                method: 'POST',
                headers: {'Content-Type': 'application/json; charset=UTF-8'},
                body: JSON.stringify(userBasicInfo)
            })
                .then(response => {
                    if (response.status === 200) {
                        window.location.href = '/login';
                    }
                    if (response.status === 400) {
                        throw response;
                    }
                })
                .catch(error => {
                    error.json()
                        .then(exception => {
                            alert(exception.message)
                        });
                });
        };

        return {
            saveUser: saveUser,
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
