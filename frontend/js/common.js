const wootubeCtx = {
    util: {
        getUrlParams: function () {
            const params = {};
            window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (str, key, value) { params[key] = value; });
            return params;
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
            body: body
        })
    }

    const requestWithoutBody = (url, method) => {
        return fetch(url, {
            method: method,
            headers: defaultHeader,
        })
    }
    
    const requestVideos = (page, size, sort) => {
        return requestWithoutBody(`${baseUrl}/v1/videos?page=${page}&size=${size}&sort=${sort},DESC`,'GET')
    }

    const requestVideo = (videoId) => {
        return requestWithoutBody(`${baseUrl}/v1/videos/${videoId}`,'GET')
    }

    const saveVideo = (dataBody) => {
        return request(`${baseUrl}/v1/videos`, 'POST', dataBody)
    }

    return {
        requestVideos: requestVideos,
        requestVideo: requestVideo,
        saveVideo: saveVideo
    }

}
const api = new Api()
