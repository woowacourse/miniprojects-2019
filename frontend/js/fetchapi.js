const api= {
    get: function(url) {
        return fetch(url,{
            method: "GET",
            headers: header,
        })
    },
    post: function(url, data) {
        return fetch(url,{
            method: "POST",
            headers: header,
            body : data
        })
    },
    put: function(url, data) {
        return fetch(url,{
            method: "PUT",
            headers: header,
            body : data
        })
    },
    videoDelete: function(url) {
        return fetch(url,{
            method: "DELETE",
            headers: header,
        })
    }
};