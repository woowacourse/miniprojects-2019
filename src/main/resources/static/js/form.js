const fileInput = () => {
    const input = document.querySelector("#file-input");
    input.addEventListener('change', function(){previewImage(this)})
}

const previewImage = (input) => {
    if(input.files){
        const reader = new FileReader();
        reader.onload = function (e) {
            DomUtil.inactive(".form-image-label")
            DomUtil.active(".file-preview")
            console.log(e.target);
            document.querySelector(".file-preview").src = e.target.result;
        }
        reader.readAsDataURL(input.files[0]);
    }
}
fileInput();