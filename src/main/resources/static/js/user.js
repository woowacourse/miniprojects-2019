const User = (() => {
    const profileImageModalButton =
        `<button id="select-image" class="create-modify-btn" tabindex="0">사진 선택</button>
        <button id="select-default-image" class="contents-remove-btn delete-btn font-cap" type="button" tabindex="0">기본 사진</button>`;
    const profileImageModal = new Modal(profileImageModalButton, 1);
    profileImageModal.init();

    const UserController = function () {
        const userService = new UserService();

        const profileImageModalButton = () => {
            const button = document.querySelector('#profile-image-btn');
            button.addEventListener('click', userService.modalActive);
        };

        const imageButton = () => {
            const button = document.querySelector('#select-image');
            button.addEventListener('click', userService.chooseImage);
        };

        const defaultImageButton = () => {
            const button = document.querySelector('#select-default-image');
            button.addEventListener('click', userService.defaultImage);
        };

        const imageUpload = () => {
            const image = document.querySelector('#img-upload');
            image.addEventListener('change', userService.uploadImageFile)
        };

        const init = () => {
            profileImageModalButton();
            imageButton();
            defaultImageButton();
            imageUpload();
        };

        return {
            init: init
        }
    };

    const UserService = function () {
        const request = new Request("/api/users");

        const modalActive = () => {
            if (loggedinUserEmail === mypageUserEmail) {
                profileImageModal.active()
            }
        };

        const imageResize = (img) => {
            const element = document.querySelector("#pic");
            const width = img.naturalWidth;
            const height = img.naturalHeight;
            if (width >= height) {
                return element.classList.add("img-parallel")
            }
            element.classList.add("img-vertical");
        };

        const chooseImage = () => {
            const image = document.querySelector('#img-upload');
            image.click();
        };

        const defaultImage = () => {
            request.delete("/",
                (status, data) => {
                    const profileImage = document.querySelector("#profile-image");
                    profileImage.setAttribute("src", data);
                });
        };

        const uploadImageFile = () => {
            const formData = new FormData();
            const imageFile = document.querySelector("#img-upload").files[0];
            formData.append("imageFile", imageFile);

            request.post("/", formData,
                (status, data) => {
                    const profileImage = document.querySelector("#profile-image");
                    profileImage.setAttribute("src", data);
                });
        };

        return {
            modalActive: modalActive,
            imageResize: imageResize,
            chooseImage: chooseImage,
            defaultImage: defaultImage,
            uploadImageFile: uploadImageFile
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

User.init();