const UsersApp = (() => {
    const UserController = function () {
        const userService = new UserService();

        const update = () => {
            const userUpdateBtn = document.getElementById('user-update-btn');
            userUpdateBtn.addEventListener('click', userService.update);
        };

        const showUpdateModal = () => {
            const userUpdateModalBtn = document.getElementById('user-update-form-btn');
            userUpdateModalBtn.addEventListener('click', userService.showUserUpdateModal);
        }

        const init = () => {
            showUpdateModal();
            update();
        };

        return {
            init: init,
        };
    };

    const UserService = function () {
        const userApi = new UserApi();

        const showUserUpdateModal = () => {
            const email = document.getElementById('user-update-email');
            const lastName = document.getElementById('user-update-last-name');
            const firstName = document.getElementById('user-update-first-name');

            const loginUser = AppStorage.get('login-user');
            email.value = loginUser.userEmail.email;
            lastName.value = loginUser.userName.name;
            firstName.value = loginUser.userName.name;

            const showModalBtn = document.getElementById('show-user-update-modal-btn')
            showModalBtn.click();
        };

        const update = (event) => {
            event.preventDefault();

            const storageKeyName = 'user-update-btn-run';
            if(AppStorage.check(storageKeyName)) return;
            AppStorage.set(storageKeyName, true);

            const nowPassword = document.getElementById('user-update-now-password');
            const email = document.getElementById('user-update-email');
            const lastName = document.getElementById('user-update-last-name');
            const firstName = document.getElementById('user-update-first-name');
            const changePassword = document.getElementById('user-update-change-password');

            const data = {
                userName: lastName.value + firstName.value,
                userEmail: email.value,
                userPassword: nowPassword.value,
                changePassword: changePassword.value
            };

            console.log('회원 정보 수정 요청 ::', data);
            userApi.update(data)
                .then(response => {
                    return response.json();
                }).then(json => {
                    if (json.hasOwnProperty('errorMessage')) {
                        alert(json.errorMessage);
                    } else {
                        console.log('회원 정보 수정 응답 ::', json);
                        nowPassword.value = "";
                        email.value = "";
                        lastName.value = "";
                        firstName.value = "";
                        changePassword.value = "";

                        HeaderApp.reRender();

                        alert('회원 정보를 수정 했습니다.');
                    }
                    AppStorage.set(storageKeyName, false);
                })
        };

        return {
            showUserUpdateModal: showUserUpdateModal,
            update: update,
        };
    };

    const UserApi = function () {
        const update = (data) => {
            return Api.put('/api/users', data);
        };

        return {
            update: update,
        }
    };

    const init = () => {
        const userController = new UserController();
        userController.init();
    };

    return {
        init: init,
    };
})();

UsersApp.init();