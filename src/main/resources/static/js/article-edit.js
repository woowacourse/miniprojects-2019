

const saveButton = document.getElementById("save-button");

const save = function () {
    let file = document.getElementById("file").value;

    if (file === '') {
        alert("파일은 필수입니다");
        return;
    }

    let postForm = document.getElementById("saveForm");
    let formData  = new FormData(postForm);

    fetch ('/articles', {
        method: "POST",
        body: formData
    }).then(response => {
        console.log(response);
    })


}

saveButton.addEventListener('click',save);
