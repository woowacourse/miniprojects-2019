function login() {
	const loginRequest = $("#login_form").serializeObject()

    api.POST("/login", loginRequest)
        .then(res => {
            if (res.ok) {
                return res.json();
            } else {
                throw res
            }
        })
        .then(loginUser => {
            localStorage.loginUserId = loginUser.id
            localStorage.loginUserName = loginUser.name
            localStorage.loginUserEmail = loginUser.email
            alert('로그인되었습니다.')
            window.location.href="/"
        })
        .catch(error => {
            error.json().then(errorMessage =>
                alert(errorMessage.message))
        })
}