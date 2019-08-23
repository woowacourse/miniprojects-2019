class Follow {
    constructor(loginId) {
        this.loginId = loginId;
        this.request = new Request('/api/follows');
    }

    addFollow = (targetId) => {
        this.request.post('/' + targetId)
    };
    deleteFollow = (targetId) => {
        this.request.delete('/' + targetId)
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