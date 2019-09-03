moment.locale('ko')

const wootubeCtx = {
    util: {
        getUrlParams: function (name, url) {
            if (!url) url = window.location.href;
            name = name.replace(/[\[\]]/g, '\\$&');
            const regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
                results = regex.exec(url);
            if (!results) return null;
            if (!results[2]) return '';
            return decodeURIComponent(results[2].replace(/\+/g, ' '));
        },
        calculateDate: function (responseDate) {
            const localResponseDate = moment.utc(responseDate).local()
            return localResponseDate.fromNow();
        },
        unescapeHtml: (escaped) => {
            const elm = document.createElement('div')
            elm.innerHTML = escaped
            return elm.childNodes.length == 0 ? '' : elm.childNodes[0].nodeValue
        }
    },
    constants: {
        videoPageSize: 6,
        videoChannelPageSize: 18
    }
}

const Api = function () {
    const defaultHeader = {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    }

    const baseUrl = '/api'

    const request = (url, method, body) => {
        return fetch(url, {
            method: method,
            headers: defaultHeader,
            body: body,
            credentials: 'include'
        })
    }

    const requestWithoutBody = (url, method) => {
        return fetch(url, {
            method: method,
            headers: defaultHeader,
            credentials: 'include'
        })
    }

    const requestVideos = (page, size, sort) => {
        return requestWithoutBody(`${baseUrl}/v1/videos?page=${page}&size=${size}&sort=${sort},DESC`, 'GET')
    }

    const requestMyChannelVideos = (userId) => {
        return requestWithoutBody(`${baseUrl}/v1/videos/creators/${userId}`, 'GET')
    }

    const requestVideo = (videoId) => {
        return requestWithoutBody(`${baseUrl}/v1/videos/${videoId}`, 'GET')
    }

    const saveVideo = (dataBody) => {
        return request(`${baseUrl}/v1/videos`, 'POST', dataBody)
    }

    const updateVideo = (dataBody, vidoeId) => {
        return request(`${baseUrl}/v1/videos/${vidoeId}`, 'PUT', dataBody)
    }

    const deleteVideo = (videoId) => {
        return requestWithoutBody(`${baseUrl}/v1/videos/${videoId}`, 'DELETE')
    }

    const postLogin = (dataBody) => {
        return request(`${baseUrl}/v1/login`, 'POST', dataBody)
    }

    const postLogout = () => {
        return request(`${baseUrl}/v1/logout`, 'POST')
    }

    const signup = (dataBody) => {
        return request(`${baseUrl}/v1/users`, 'POST', dataBody)
    }

    const saveComment = (dataBody, videoId) => {
        return request(`${baseUrl}/v1/videos/${videoId}/comments`, 'POST', dataBody)
    }

    const editComment = (dataBody, videoId, commentId) => {
        return request(`${baseUrl}/v1/videos/${videoId}/comments/${commentId}`, 'PUT', dataBody)
    }

    const deleteComment = (videoId, commentId) => {
        return requestWithoutBody(`${baseUrl}/v1/videos/${videoId}/comments/${commentId}`, 'DELETE')
    }

    const retrieveComments = (videoId) => {
        return requestWithoutBody(`${baseUrl}/v1/videos/${videoId}/comments`, 'GET')
    }

    const requestUser = (id) => {
        return request(`${baseUrl}/v1/users/${id}`, 'GET');
    }

    const updateUser = (id, body) => {
        return request(`${baseUrl}/v1/users/${id}`, 'PUT', body)
    }

    const deleteUser = (id) => {
        return requestWithoutBody(`${baseUrl}/v1/users/${id}`, 'DELETE')
    }

    const retrieveLoginInfo = () => {
        return requestWithoutBody(`${baseUrl}/v1/login/users`, 'GET')
    }

    const requestSubscribed = (userId) => {
        return requestWithoutBody(`${baseUrl}/v1/users/${userId}/subscribed`, 'GET')
    }

    const subscribe = (channelId) => {
        return requestWithoutBody(`${baseUrl}/v1/users/${channelId}/subscribe`, 'POST')
    }

    const cancelSubscribe = (channelId) => {
        return requestWithoutBody(`${baseUrl}/v1/users/${channelId}/subscribe`, 'DELETE')
    }

    return {
        requestVideos,
        requestVideo,
        saveVideo,
        updateVideo,
        deleteVideo,
        postLogin,
        postLogout,
        signup,
        saveComment,
        editComment,
        deleteComment,
        retrieveComments,
        requestUser,
        updateUser,
        retrieveLoginInfo,
        deleteUser,
        requestMyChannelVideos,
        requestSubscribed,
        subscribe,
        cancelSubscribe
    }
}

const api = new Api()
