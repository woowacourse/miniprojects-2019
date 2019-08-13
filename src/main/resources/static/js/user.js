

document.getElementById('signup').addEventListener('click', function (event) {
    event.preventDefault();
    const userBasicInfo = {
        email: document.getElementById('email').value,
        nickName: document.getElementById('nickName').value,
        userName: document.getElementById('userName').value,
        password: document.getElementById('password').value
    };
    postData('/api/users', userBasicInfo)
})

function postData(url, data) {
    return fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json; charset=UTF-8'},
        body: JSON.stringify(data)
    })
        .then(response => {
            if(response.status === 200) {
                window.location.href = "/login"
            }
        })
        .catch(error => alert(error))
}

