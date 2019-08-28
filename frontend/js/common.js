const wootubeCtx = {
    util: {
        getUrlParams: function (name, url) {
            if (!url) url = window.location.href;
            name = name.replace(/[\[\]]/g, '\\$&');
            var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
                results = regex.exec(url);
            if (!results) return null;
            if (!results[2]) return '';
            return decodeURIComponent(results[2].replace(/\+/g, ' '));
        },
        calculateDate: function (responseDate) {
            const localResponseDate = moment.utc(responseDate,'YYYYMMDDHH').local().format('YYYYMMDDHH')
            const videoDate = new Date(localResponseDate.substr(0, 4), localResponseDate.substr(4, 2), localResponseDate.substr(6, 2), localResponseDate.substr(8, 2))
            const currentDate = new Date()
            const yearDifference = currentDate.getFullYear() - videoDate.getFullYear()
            const monthDifference = currentDate.getMonth() + 1 - videoDate.getMonth()
            const dayDifference = currentDate.getDate() - videoDate.getDate()
            const hourDifference = currentDate.getHours() - videoDate.getHours()
            if (yearDifference != 0) {
                return yearDifference + '년전'
            } else if (monthDifference != 0) {
                return monthDifference + '달전'
            } else if (dayDifference != 0) {
                return dayDifference + '일전'
            } else if (hourDifference != 0) {
                return hourDifference + '시간전'
            } else {
                return '방금전'
            }
        }
    },
    constants : {
        videoPageSize : 6,
        videoChannelPageSize : 18
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
        return requestWithoutBody(`${baseUrl}/v1/videos?page=${page}&size=${size}&sort=${sort},DESC`,'GET')
    }

    const requestMyChannelVideos = (userId) => {
        return requestWithoutBody(`${baseUrl}/v1/users/${userId}/videos`,'GET')
    }

    const requestVideo = (videoId) => {
        return requestWithoutBody(`${baseUrl}/v1/videos/${videoId}`,'GET')
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

    return {
        requestVideos,
        requestVideo,
        saveVideo,
        updateVideo,
        deleteVideo,
        postLogin,
        postLogout,
        signup,
        requestUser,
        updateUser,
        retrieveLoginInfo,
        deleteUser,
        requestMyChannelVideos,
        requestSubscribed
    }
}

const api = new Api()
