const Api = {
    get: function (url) {
        return fetch(url, {
            method: 'GET',
        });
    },
    post: function (url, data) {
        return fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });
    },
    put: function (url, data) {
        return fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });
    },
    delete: function (url) {
        return fetch(url, {
            method: 'DELETE',
        });
    },
};