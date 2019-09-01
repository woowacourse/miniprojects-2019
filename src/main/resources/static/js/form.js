const fileInput = () => {
    const input = document.querySelector("#file-input");
    input.addEventListener('change', previewImage)
};

const previewImage = (input) => {
    // console.log(input);
    if (input.target.files) {
        const reader = new FileReader();
        reader.onload = (e) => {
            DomUtil.inactive(".form-image-label");
            DomUtil.active(".file-preview");
            // console.log(e.target);
            document.querySelector(".file-preview").src = e.target.result;
        };
        reader.readAsDataURL(input.target.files[0]);
    }
};
fileInput();