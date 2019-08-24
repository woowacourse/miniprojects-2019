const setupNavBarUserName = () => {
    const currentUserName = document.getElementById("current-user-name")
    currentUserName.innerHTML = localStorage.loginUserName
}

setupNavBarUserName()