// Get the logged in user info
axios.get('/api/users/loggedin')
    .then(function (response) {
        // handle success
        // console.log(response.data.id);
        const userId = response.data.id;
        openSocket(userId);
    })
    .catch(function (error) {
        // handle error
        console.log(error);
    })
    .finally(function () {
        // always executed
    });

function openSocket(userId) {
    let socket = new SockJS("http://127.0.0.1:8080/portfolio");
    let stompClient = Stomp.over(socket);

    let connectCallback = () => {
        stompClient.subscribe("/topic/alarm/likes/" + userId, function (message) {
            let json = JSON.parse(message.body);
            const messages = document.querySelector('.dropdown-con>.dropdown-menu');
            messages.insertAdjacentHTML('afterbegin', `<a class="dropdown-item" href="/articles/${json.articleId}">${json.message}</a>`);
        });

        stompClient.subscribe("/topic/alarm/follows/" + userId, function(message) {
            let json = JSON.parse(message.body);
            const messages = document.querySelector('.dropdown-con>.dropdown-menu');
            messages.insertAdjacentHTML('afterbegin', `<a class="dropdown-item" href="/${json.targetName}">${json.message}</a>`);
        });
    };

    stompClient.connect({}, connectCallback);
}