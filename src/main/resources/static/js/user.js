const User = (function () {
    const modalButton =
        `<button class="create-modify-btn" tabindex="0">사진 선택</button>
        <button class="contents-remove-btn delete-btn font-cap" type="button" tabindex="0">기본 사진</button>`;
    const modal = new Modal(modalButton);
    modal.init();

    const UserController = function () {
        const userService = new UserService();

        const modalButton = () => {
            const button = document.querySelector('.modal-btn');
            button.addEventListener('click', userService.modalActive);
        };

        const imageButton = () => {
            const button = document.querySelector('.create-modify-btn');
            button.addEventListener('click', userService.chooseImage);
        };

        const defaultImageButton = () => {
            const button = document.querySelector('.contents-remove-btn');
            button.addEventListener('click', userService.defaultImage);
        };

        const imageUpload = () => {
            const image = document.querySelector('#img-upload');
            image.addEventListener('change', userService.uploadImageFile)
        };

        const init = function () {
            modalButton();
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
            modal.active()
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
            // const image = document.querySelector('#original-image');
            // const profileImage = image.value;
            // const data ={'profileImage':profileImage};
            request.delete("/",
                () => {
                    const profileImage = document.querySelector("#profile-image");
                    profileImage.setAttribute("src", defaultProfileImage);
                });
        };

        const uploadImageFile = () => {
            const formData = new FormData();

            const imageFile = document.querySelector("#img-upload").files[0];
            const originalImageFile = document.querySelector("#original-image").value;
            formData.append("imageFile", imageFile);
            formData.append("originalImageFile", originalImageFile);

            request.post("/", formData,
                (status, data) => {
                    const profileImage = document.querySelector("#profile-image");
                    profileImage.setAttribute("src", data);
                    const originalImage = document.querySelector('#original-image');
                    originalImage.setAttribute("value", data);
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
}());

User.init();