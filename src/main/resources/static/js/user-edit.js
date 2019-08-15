const USER_EDIT_APP = (() => {
    'use strict';

    const UserController = function () {
        const userService = new UserService();

        const editUser = () => {
            const editButton = document.getElementById('editButton');
            editButton.addEventListener('click', userService.updateUser);
        };

        const init = () => {
            editUser();
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

        const updateUser = function (event) {
            event.preventDefault();
            const userId = document.getElementById('userId');
            const webSite = document.getElementById('webSite');
            const password = document.getElementById('password');
            const intro = document.getElementById('intro');

            const userDto = {
                nickName: nickName.value,
                userName: userName.value,
                userId: userId.value,
                webSite: webSite.value,
                password: password.value,
                intro: intro.value,
            };

            fetch('/api/users/' + userId.value, {
                method: 'PUT',
                headers: {'Content-Type': 'application/json; charset=UTF-8'},
                body: JSON.stringify(userDto)
            }).then(response => {
                if (response.status === 200) {
                    // TODO 슬로스꺼ㄹ로 이동
                    window.location.href = '/users/' + userId.value + '/edit';
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
            updateUser: updateUser
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

USER_EDIT_APP.init();