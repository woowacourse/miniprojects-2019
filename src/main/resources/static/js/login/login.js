async function login() {
	const header = {
		"Content-Type": "application/json; charset=UTF-8"
	}

	const form_data = $("#login_form").serializeObject()

    const res = await api.POST("/login", form_data)

    if (res.status == 200) {
		alert('로그인되었습니다.')
		window.location.href="/"
    } else if (res.status == 400) {
        const error = await res.json()
    	alert(error.message)
    }
}