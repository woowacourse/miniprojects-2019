class Follow {
    constructor(status, targetId) {
        this.status = status;
        this.request = new Request(`/api/users/${targetId}/follows`);
    }

    getStatus = () => {
        return this.status;
    };

    toggleStatus = () => {
        this.status = !this.status;
    };

    addFollow = () => {
        return this.request.post('/')
    };

    deleteFollow = () => {
        return this.request.delete('/')
    };

    followersNum = (callback) => {
        return this.request.get('/followers/num', callback)
    };

    followingsNum = (callback) => {
        return this.request.get('/followings/num', callback)
    }
}

class Like {
    constructor() {
        this.request = new Request('api/articles');
    }

    addLike = (articleId) => {
        return this.request.post('/' + articleId + '/likes', null)

    };
    deleteLike = (articleId) => {
        return this.request.delete('/' + articleId + '/likes')
    }
}

if (window.location.host.indexOf('localhost:8080') < 0) {
    console.log = function () {
    };
}