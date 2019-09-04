(() => {
    const loggedInRequest = new Request('/api/users/loggedin');
    loggedInRequest.get("", (status, data) => {
        const userId = data.id;
        openSocket(userId);
    }).catch((error) => {
        // handle error
        console.log(error);
    })


    function openSocket(userId) {
        const socket = new SockJS("/portfolio");
        const stompClient = Stomp.over(socket);

        const connectCallback = () => {
            stompClient.subscribe(`/topic/alarm/likes/${userId}`, (message) => {
                const json = JSON.parse(message.body);
                const messages = document.querySelector('.dropdown-con>.dropdown-menu');
                messages.insertAdjacentHTML('afterbegin', `<a class="dropdown-item" href="/articles/${json.articleId}">${json.message}</a>`);
            });

            stompClient.subscribe(`/topic/alarm/follows/${userId}`, (message) => {
                const json = JSON.parse(message.body);
                const messages = document.querySelector('.dropdown-con>.dropdown-menu');
                messages.insertAdjacentHTML('afterbegin', `<a class="dropdown-item" href="/${json.targetName}">${json.message}</a>`);
            });
        };

        stompClient.connect({}, connectCallback);
    }
})();