async function logout() {
	const logoutConfirm = confirm('로그아웃 하시겠습니까?')

	if(logoutConfirm) {
        const res = await api.POST("/logout")

        if (res.status == 200) {
            alert('로그아웃 되었습니다.')
        }
        else {
            alert('로그인 상태가 아닙니다.')
        }

        window.location.href="/users/form"
	}
}