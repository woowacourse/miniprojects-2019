const FriendsApp = (() => {
    const FriendController = function () {
        const friendService = new FriendService();

        const init = () => {

        };

        return {
            init: init,
        }
    };

    const FriendService = function () {
        const friendApi = new FriendApi();
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
            return Api.get(`/api/friends`);
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