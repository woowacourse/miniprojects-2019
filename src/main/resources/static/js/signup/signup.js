async function signup() {
	const header = {
		"Content-Type": "application/json; charset=UTF-8"
	}

	const form_data = $("#reg").serializeObject()

    const res = await api.POST("/api/users", form_data)

    if(res.status == 201) {
		alert('가입이 완료되었습니다. 로그인 해주세요.')
		clear()
    } else if (res.status == 400) {
        const error = await res.json()
    	alert(error.message)
    }
}

const clear = async e => {
	document.getElementById("name-form").value = ""
	document.getElementById("email-form").value = ""
	document.getElementById("password-form").value = ""
}