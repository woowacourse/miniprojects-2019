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

        const deleteUser = () => {
            const deleteButton = document.getElementById('deleteButton');
            deleteButton ? deleteButton.addEventListener('click', userService.deleteUser) : undefined;
        };

        const login = () => {
            const loginButton = document.getElementById('login-button');
            loginButton ? loginButton.addEventListener('click', userService.login) : undefined;
        };

        const changeImageJustOnFront = () => {
            const imageInput = document.getElementById("img-upload");
            imageInput ? imageInput.addEventListener("change", userService.changeImageJustOnFront) : undefined;
        };

        const showThumbnailImage = () => {
            const userProfileImage = document.getElementById('profile-image');
            userProfileImage ? userService.showThumbnail(userProfileImage) : undefined;
        };

        const init = () => {
            signUp();
            editUser();
            login();
            changeImageJustOnFront();
            deleteUser();
            showThumbnailImage();
        };

        return {
            init: init
        }
    };

    const UserService = function () {
        const connector = FETCH_APP.FetchApi();
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

            const ifSucceed = () => window.location.href = '/login';

            connector.fetchTemplate('/api/users',
                connector.POST,
                header,
                JSON.stringify(userBasicInfo),
                ifSucceed);
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

            const ifSucceed = () => window.location.href = '/';

            connector.fetchTemplate(`/api/users/${userId.value}`,
                connector.PUT,
                {},
                formData,
                ifSucceed);
        };

        const deleteUser = function (event) {
            event.preventDefault();
            connector.fetchTemplateWithoutBody(`/api/users/${userId.value}`,
                connector.DELETE,
                () => window.location.href = '/login')
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

        const changeImageJustOnFront = function (event) {
            const file = event.target.files[0];
            const reader = new FileReader();
            // it's onload event and you forgot (parameters)
            reader.onload = function (readEvent) {
                profileImage.src = readEvent.target.result;
            };
            // you have to declare the file loading
            reader.readAsDataURL(file);

        };

        const showThumbnail = function (userProfileImage) {
            const fileLoadService = FILE_LOAD_APP.FileLoadService();
            const connector = FETCH_APP.FetchApi();

            const getImage = response => {
                response.arrayBuffer().then(function (buffer) {
                    let bytes = new Uint8Array(buffer);
                    let binary = '';
                    bytes.forEach(b => binary += String.fromCharCode(b));

                    const blob = fileLoadService.b64StringToBlob(binary);
                    const blobUrl = URL.createObjectURL(blob);

                    userProfileImage.style.display = 'block';
                    userProfileImage.src = blobUrl;
                });
            };

            connector.fetchTemplateWithoutBody(`/api/users/${userId.value}/image`, connector.GET, getImage);
        };

        // TODO I want to PRIVATE method
        const setUrl = (userProfileImage, url) => {
            userProfileImage.style.display = 'block';
            userProfileImage.src = url;
        };

        return {
            saveUser: saveUser,
            updateUser: updateUser,
            deleteUser: deleteUser,
            login: login,
            changeImageJustOnFront: changeImageJustOnFront,
            showThumbnail: showThumbnail
        }
    };

    const init = () => {
        const userController = new UserController();
        userController.init();
    };

    return {
        init: init,
    }
})();

USER_APP.init();
