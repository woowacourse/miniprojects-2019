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

        const changeImageJustOnFront = () => {
            const imageInput = document.getElementById("img-upload");
            imageInput ? imageInput.addEventListener("change", userService.changeImageJustOnFront) : undefined;

        }

        const init = () => {
            signUp();
            editUser();
            login();
            changeImageJustOnFront();
        };

        return {
            init: init
        }
    };

    const FetchApi = function () {
        const POST = 'POST';
        const PUT = 'PUT';

        const fetchTemplate = function (requestUrl, method, body, ifSucceed) {
            fetch(requestUrl, {
                method: method,
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(body)
            }).then(response => {
                if (response.status === 200) {
                    ifSucceed();
                    return;
                }
                if (response.status === 400) {
                    errorHandler(response);
                }
            });
        };

        const fetchWithFileTemplate = function (requestUrl, method, body, ifSucceed) {
            fetch(requestUrl, {
                method: method,
                body: body
            }).then(response => {
                if (response.status === 200) {
                    ifSucceed();
                    return;
                }
                if (response.status === 400) {
                    errorHandler(response);
                }
            });
        }

        const errorHandler = function(error) {
            error.json()
                .then(exception => {
                    alert(exception.message)
                });
        };

        return {
            POST: POST,
            PUT: PUT,
            fetchTemplate: fetchTemplate,
            fetchWithFileTemplate: fetchWithFileTemplate,
        }
    };

    const UserService = function () {
        const connector = new FetchApi();

        const email = document.getElementById('email');
        const nickName = document.getElementById('nickName');
        const userName = document.getElementById('userName');
        const password = document.getElementById('password');
        const userId = document.getElementById('userId');
        const webSite = document.getElementById('webSite');
        const intro = document.getElementById('intro');
        const imageInput = document.getElementById("img-upload");
        const profileImage = document.getElementById('profile-image');

        const saveUser = function (event) {
            event.preventDefault();

            let userBasicInfo = {
                email: email.value,
                nickName: nickName.value,
                userName: userName.value,
                password: password.value,
            };

            const goLogin = () => window.location.href = '/login';

            connector.fetchTemplate('/api/users',
                connector.POST,
                userBasicInfo,
                goLogin);
        };

        const updateUser = function (event) {
            event.preventDefault();

            const formData = new FormData();

            formData.append("nickName", nickName.value);
            formData.append("userName", userName.value);
            formData.append("intro", intro.value);

            if (imageInput.files.length !== 0) {
                formData.append("file", imageInput.files[0]);
            }

            formData.append("webSite", webSite.value);
            formData.append("id", userId.value);
            formData.append("password", password.value);

            const userDto = {
                nickName: nickName.value,
                userName: userName.value,
                userId: userId.value,
                webSite: webSite.value,
                password: password.value,
                intro: intro.value,
                file: imageInput.files[0],
            };


            const goIndex = () => window.location.href = '/';

            connector.fetchWithFileTemplate('/api/users/' + userId.value,
                connector.PUT,
                formData,
                goIndex);
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
                userBasicInfo,
                ifSucceed);
        };

        const changeImageJustOnFront = function (event) {
            const file = event.target.files[0];
            const reader = new FileReader();
            // it's onload event and you forgot (parameters)
            reader.onload = function (readEvent) {
                profileImage.src = readEvent.target.result;

            }
            // you have to declare the file loading
            reader.readAsDataURL(file);

        }

        return {
            saveUser: saveUser,
            updateUser: updateUser,
            login: login,
            changeImageJustOnFront: changeImageJustOnFront
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
