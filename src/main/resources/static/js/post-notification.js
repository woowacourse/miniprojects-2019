(function() {
    const postScrollbar = document.getElementById('post-notification-scroll-bar');
    const friendRequestNotification = document.getElementById('post-notification');

    const addNotification = (scrollbar, json) => {
        const li = `<li>
                        <img class="noti-profile" src="${json.publisherProfile}" />
                        <span>${json.publisherName}님이 게시글 작성</span>
                    </li>`;

        scrollbar.insertAdjacentHTML('beforeend', li);
    };

    const markNotification = (array) => {
        if (array.length > 0) {
            const span = `<span class="dot mrg-vertical-10 mrg-horizon-15"></span>`;
            friendRequestNotification.insertAdjacentHTML('beforeend', span);
        }
    };

    const addElementsToScrollBar = (scrollbar, url, callback) => {
        Api.get(url)
            .then(res => res.json())
            .then(array => {
                array.map(json => callback(scrollbar, json));
                markNotification(array);
            });
    };

    const init = () => {
        if (postScrollbar) {
            addElementsToScrollBar(postScrollbar, '/notifications', addNotification);
        }
    }
    init();
})();