// Get the logged in user info
axios.get('/user?ID=12345')
    .then(function (response) {
        // handle success
        console.log(response);
    })
    .catch(function (error) {
        // handle error
        console.log(error);
    })
    .finally(function () {
        // always executed
    });

// Open Web Socket and subscribe alarm
let socket = new SockJS("http://127.0.0.1:8080/portfolio");
let stompClient = Stomp.over(socket);

let connectCallback = () => {
    stompClient.subscribe("/topic/alarm/3", function (message) {
        console.log(message);
    })
};

stompClient.connect({}, connectCallback);