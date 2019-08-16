const wootubeCtx = {
    constants: {
        HEADER: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        BASE_URL: '/api'
    },
    util: {
        api: {
            get: function (url) {
                return fetch(wootubeCtx.constants.BASE_URL + url, {
                    method: 'GET',
                    headers: wootubeCtx.constants.HEADER,
                })
            },
            post: function (url, data) {
                return fetch(wootubeCtx.constants.BASE_URL + url, {
                    method: 'POST',
                    headers: wootubeCtx.constants.HEADER,
                    body: data
                })
            },
            put: function (url, data) {
                return fetch(wootubeCtx.constants.BASE_URL + url, {
                    method: 'PUT',
                    headers: wootubeCtx.constants.HEADER,
                    body: data
                })
            },
            videoDelete: function (url) {
                return fetch(wootubeCtx.constants.BASE_URL + url, {
                    method: 'DELETE',
                    headers: wootubeCtx.constants.HEADER,
                })
            }
        },
        getUrlParams: function () {
            const params = {};
            window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (str, key, value) { params[key] = value; });
            return params;
        },
        calculateDate: function (responseDate) {
            const videoDate = new Date(responseDate.substr(0,4), responseDate.substr(4,2), responseDate.substr(6,2), responseDate.substr(8,2))
            const currentDate = new Date()
            const yearDifference = currentDate.getFullYear() - videoDate.getFullYear()
            const monthDifference = currentDate.getMonth() + 1 - videoDate.getMonth()
            const dayDifference = currentDate.getDate() - videoDate.getDate()
            const hourDifference = currentDate.getHours() - videoDate.getHours()
            if(yearDifference != 0) {
                return yearDifference + '년전'
            }else if(monthDifference != 0) {
                return monthDifference + '달전'
            }else if(dayDifference != 0) {
                return dayDifference + '일전'
            }else if(hourDifference != 0) {
                return hourDifference + '시간전'
            }else{
                return '방금전'
            }
        },
    },
}
