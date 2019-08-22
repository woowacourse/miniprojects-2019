const User = (function () {
    const UserController = function () {
        const userService = new UserService();

        const modalButton = () => {
            const button = document.querySelector('.modal-btn');
            button.addEventListener('click', userService.modalActive);
        };

        const imageButton = () => {
            const button = document.querySelector('.choose-image-btn');
            button.addEventListener('click', userService.chooseImage);
        };

        const defaultImageButton = () => {
            const button = document.querySelector('.default-image-btn');
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
            const image = document.querySelector('#original-image');
            image.setAttribute("value", "https://woowahan-crews.s3.ap-northeast-2.amazonaws.com/default_profile_image.jpg");
        };

        const uploadImageFile = () => {
            const request = new Request("/api/users");
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