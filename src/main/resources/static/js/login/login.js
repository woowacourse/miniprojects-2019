function login() {
	const header = {
		"Content-Type": "application/json; charset=UTF-8"
	}

	const form_data = JSON.stringify($("#login_form").serializeObject())

    let res = $.ajax({
        url:"/login",
        type:"POST",
        async: false,
        data: form_data,
        headers: header
    })

    response = res

    console.log(res)

    if (res.status == 200) {
		alert('로그인되었습니다.')
		window.location.href="/"
    } else if (res.status == 400) {
    	alert(res.responseJSON.message)
    }
}