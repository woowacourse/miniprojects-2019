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

    addLike = (articleId, liking) => {
        if (liking == "false") {
            return this.request.post('/' + articleId + '/likes', null)
        }
        return false
    };
    deleteLike = (articleId, liking) => {
        if (liking == "true") {
            this.request.delete('/' + articleId + '/likes',
                () => {
                    return true;
                })
        }
        return false
    }
}