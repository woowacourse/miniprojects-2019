document.getElementById('login-btn').addEventListener('click', login);
document.getElementById('signup-btn').addEventListener('click', signup);

function login() {
    const email = document.getElementById('login-email').value;
    const password = document.getElementById('login-password').value;

    fetch('/api/signin' , {
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        },
        method: 'POST',
        body: JSON.stringify({
            userEmail: email,
            userPassword: password
        })
    }).then(response => {
        return response.json();
    }).then(json => {
        if (json.hasOwnProperty('errorMessage')) {
            alert(json.errorMessage);
        } else {
            location.href='/sunbook';
        }
    })
}

function signup() {
    const firstName = document.getElementById('signup-first-name');
    const lastName = document.getElementById('signup-last-name');
    const email = document.getElementById('signup-email');
    const password = document.getElementById('signup-password');

    fetch('/api/signup', {
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        },
        method: 'POST',
        body: JSON.stringify({
            userName: lastName.value + firstName.value,
            userEmail: email.value,
            userPassword: password.value
        })
    }).then(response => {
        return response.json();
    }).then(json => {
        console.log(json);
        if (json.hasOwnProperty('errorMessage')) {
            alert(json.errorMessage);
        } else {
            firstName.value = "";
            lastName.value = "";
            email.value = "";
            password.value = "";
            alert('가입을 완료했습니다. 로그인 하세요.');
        }
    })
}