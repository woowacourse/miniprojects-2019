function logout() {
	const logoutConfirm = confirm('로그아웃 하시겠습니까?')

	if(logoutConfirm) {
        $.ajax({
            url:"/logout",
            type:"POST",
            async:false,
            dataType:'text',
            error:function(request,status,error){
            	alert('로그인 상태가 아닙니다.')
            },
            success:function(data){
            	alert('로그아웃 되었습니다.')
            }
        })

        window.location.href="/users/form"
	}
}