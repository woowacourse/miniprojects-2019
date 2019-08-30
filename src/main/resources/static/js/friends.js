const FriendsApp = (() => {
    const friendTemplate = Handlebars.compile(template.friendShowCard);

    const templateRequested = Handlebars.compile(template.friends.usersBtn.requested);
    const templateFriend = Handlebars.compile(template.friends.usersBtn.friend);

    const FriendController = function () {
        const friendService = new FriendService();

        const showFriendList = () => {
          friendService.showFriendList();
        };

        const init = () => {
            showFriendList();
            requested();
        };

        const requested = () => {
            const friendListForm = document.getElementById('friend-list-form');
            friendListForm.addEventListener('click', friendService.btnOk);
            friendListForm.addEventListener('click', friendService.btnNo);
            friendListForm.addEventListener('click', friendService.btnFriend);
        };

        return {
            init: init,
        }
    };

    const FriendService = function () {
        const friendApi = new FriendApi();

        const showFriendList = () => {
            const friendListForm = document.getElementById('friend-list-form');
            friendApi.render().friend
                .then(response => response.json())
            .then(data => {
                data.forEach(friendCards => {
                    friendListForm.insertAdjacentHTML('afterbegin', friendTemplate({
                        "userName": friendCards.userName.name,
                        "btn": templateFriend({'userId': friendCards.id}),
                    }))
                })
            });

            friendApi.render().requestedFriend
                .then(response => response.json())
                .then(data => {
                    data.forEach(friendCards => {
                        friendListForm.insertAdjacentHTML('afterbegin', friendTemplate({
                            "userName": friendCards.userName.name,
                            "btn": templateRequested({'userId': friendCards.id}),
                        }))
                    })
                });
        };

        const btnOk = (event) => {
            event.stopPropagation();

            const target = event.target;
            const btnType = target.getAttribute('data-btn');
            const btnGroup = target.closest('div[data-object="event-btn-group"]');

            if (btnType === 'friends-ok') {
                target.closest('li');

                const toId = target.getAttribute('data-friend-id');
                friendApi.ok(toId)
                    .then(
                        btnGroup.innerHTML = templateFriend({'userId': toId})
                    )
            }
        };

        const btnFriend = (event) => {
            event.stopPropagation();

            const target = event.target;
            const friendForm = target.closest("li");
            const btnType = target.getAttribute('data-btn');

            if (btnType === 'friends-friends') {
                const response = confirm('친구를 삭제 하시겠습니까?');
                if (response === true){
                    target.closest('li');
                    const toId = target.getAttribute('data-friend-id');
                    friendApi.no(toId).then(
                        friendForm.innerHTML = ""
                    )
                }
            }
        };

        const btnNo = (event) => {
            event.stopPropagation();

            const target = event.target;
            const friendForm = target.closest("li");
            const btnType = target.getAttribute('data-btn');

            if (btnType === 'friends-no') {
                target.closest('li')
                const toId = target.getAttribute('data-friend-id');
                friendApi.no(toId).then(
                    friendForm.innerHTML = ""
                )
            }
        };

        return {
            showFriendList: showFriendList,
            btnOk: btnOk,
            btnNo: btnNo,
            btnFriend: btnFriend,
        }
    };



    const FriendApi = function () {
        const no = (toId) => {
            return Api.delete(`/api/friends/${toId}`);
        };

        const ok = (toId) => {
            return Api.put(`/api/friends/${toId}`);
        };

        const add = (toId) => {
            return Api.post(`/api/friends/${toId}`);
        };

        const remove = (toId) => {
            return Api.delete(`/api/friends/${toId}`);
        };

        const getRelation = (toId) => {
            return Api.get(`/api/friends/${toId}`)
                .then(response => response.json());
        };

        const render = () => {
            return {
                friend: Api.get(`/api/friends/friends`),
                requestedFriend: Api.get(`/api/friends/friends/requested`),
            };
        };

        return {
            no: no,
            ok: ok,
            add: add,
            remove: remove,
            render: render,
            relation: getRelation,
        };
    };

    const init = () => {
        const friendController = new FriendController();
        friendController.init();
    };

    return {
        init: init,
        api: new FriendApi(),
    }
})();

FriendsApp.init();